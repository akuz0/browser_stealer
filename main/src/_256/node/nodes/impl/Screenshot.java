package _256.node.nodes.impl;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import _256.node.Category;
import _256.node.Node;

public class Screenshot extends Node {

	public Screenshot() {
		super(Category.FILE);
	}

	@Override
	public void createNode() throws AWTException {
		Robot robot = new Robot();
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
		try {
			if (getFolder().exists()) {
				ImageIO.write(screenFullImage, "png", new File(getFolder() + "\\" + "Screenshot.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}