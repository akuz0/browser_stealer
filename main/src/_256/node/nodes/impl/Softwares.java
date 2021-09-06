package _256.node.nodes.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import _256.node.Category;
import _256.node.Node;
import _256.utils.software.ListAppsUtil;
import _256.utils.software.SoftwareUtil;

public class Softwares extends Node {

	public Softwares() {
		super(Category.FILE);
	}

	@Override
	public void createNode() {
		try {
			FileWriter writer = new FileWriter(getFolder() + "\\" + "Softwares.txt", true);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			Map<String, SoftwareUtil> list = ListAppsUtil.getInstalledApps(false);
			for (SoftwareUtil soft : list.values()) {
				bufferedWriter.write(soft.getDisplayName() + ": " + soft.getPublisher() + "\n");
			}
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
