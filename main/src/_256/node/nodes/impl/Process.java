package _256.node.nodes.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import _256.node.Category;
import _256.node.Node;

public class Process extends Node {

	public Process() {
		super(Category.FILE);
	}

	@Override
	public void createNode() throws InterruptedException {
		try {
			java.lang.Process process = new ProcessBuilder("tasklist.exe", "/fo", "csv", "/nh").start();
			FileWriter writer = new FileWriter(getFolder() + "\\" + "Process.txt", true);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			new Thread(() -> {
				Scanner sc = new Scanner(process.getInputStream());
				if (sc.hasNextLine())
					sc.nextLine();
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					String[] parts = line.split(",");
					String unq = parts[0].substring(1).replaceFirst(".$", "");
					String pid = parts[1].substring(1).replaceFirst(".$", "");
					try {
						bufferedWriter.write("Name: " + unq + " - PID: " + pid + "\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
			process.waitFor();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}