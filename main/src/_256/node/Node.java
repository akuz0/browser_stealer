package _256.node;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;

public abstract class Node {

	protected final File nodePath;
	protected Category cat;

	public Node(Category cat) {
		nodePath = getFolder();
		this.cat = cat;
		try {
			createNode();
		} catch (IOException | InterruptedException | AWTException e) {
			e.printStackTrace();
		} catch (Exception e) {
		}
	}

	public abstract void createNode() throws IOException, InterruptedException, AWTException, Exception;

	public File getFolder() {
		File folder = new File(System.getProperty("user.home"), System.getProperty("user.name"));
		if (!folder.exists()) {
			folder.mkdirs();
		}
		return folder;
	}

	public File mergePath(File file, String file0) {
		String secondFile = "";
		String firstFile = "";
		if (file.getAbsolutePath().endsWith("\\")) {
			firstFile.substring(0, file.getAbsolutePath().length() - 1);
		} else {
			firstFile = file.getAbsolutePath();
		}
		if (file0.startsWith("\\")) {
			secondFile = file0.substring(1);
		} else {
			secondFile = file0;
		}
		return new File(firstFile + "\\" + secondFile);
	}
}
