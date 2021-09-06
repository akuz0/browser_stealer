package _256.main;

import _256.node.NodeManager;

public enum _256 {

	INSTANCE;

	public NodeManager nodeManager;

	public void initialize() {
		nodeManager = new NodeManager();
	}

	public void launchNodes() throws InterruptedException {
		nodeManager.launch();
	}
}
