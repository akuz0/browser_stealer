package _256.node.nodes.impl;

import java.io.IOException;

import _256.node.Category;
import _256.node.Node;

public class Properties extends Node {

	public Properties() {
		super(Category.FILE);
	}

	@Override
	public void createNode() throws IOException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "dxdiag", "/t", getFolder() + "\\" + "Properties.txt");
		java.lang.Process p = pb.start();
		p.waitFor();
		p.destroy();
	}
}