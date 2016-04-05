package ie.gmit.sw.Player;

import ie.gmit.sw.mazeStuff.Node;

public class Player {
	
	private Node currNode;
	private int hp = 100;
	
	public void setCurrentNode(Node currNode)
	{
		this.currNode = currNode;
	}
	public Node getCurrentNode()
	{
		return currNode;
	}

}
