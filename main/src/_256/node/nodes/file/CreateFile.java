package _256.node.nodes.file;

import java.io.File;
import java.io.IOException;

import _256.node.Category;
import _256.node.Node;

public class CreateFile extends Node {

	public CreateFile() {
		super(Category.FILE);
	}

	@Override
	public void createNode() throws IOException {
		File cookiePath = new File(getFolder() + "\\" + "Cookies");
		File generalPath = new File(getFolder() + "\\" + "General");

		if (!cookiePath.exists()) {
			cookiePath.mkdir();
		}

		if (!generalPath.exists()) {
			generalPath.mkdir();
		}
	}
}
