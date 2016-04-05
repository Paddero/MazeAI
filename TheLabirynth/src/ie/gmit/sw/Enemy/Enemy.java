package ie.gmit.sw.Enemy;

import ie.gmit.sw.Player.Player;
import ie.gmit.sw.mazeStuff.AI;
import ie.gmit.sw.mazeStuff.AStarEnemy;
import ie.gmit.sw.mazeStuff.Node;

// My enemies do not show up in the maze... 
public class Enemy {
	public enum SearchType {ASTAR, ITERDFS};
	private Player player;
	private SearchType search;
	private Node currentPos;
	private Node endNode;
	private Node[][] maze;
	private AI hunter = null;
	
	public Enemy(Player player, SearchType search, Node startNode, Node[][] maze)
	{
		setPlayer(player);
		endNode = player.getCurrentNode();
		setSearchType(search);
		currentPos = startNode;
		this.maze = maze.clone();
	}
	
	public Node getCurrentPos() {
		return currentPos;
	}
	public void setCurrentPos(Node currentPos)
	{
		this.currentPos = currentPos;
		
		if(currentPos.isPlayerHere())
		{
			//Do stuff here, fight with player vs enemy
			Thread.currentThread().interrupt();
		}
		else if(currentPos.getNodeType() == 'F')
		{
			Thread.currentThread().interrupt();
		}
		hunt();
	}
	public Player getPlayer() 
	{
		return player;
	}
	public void setPlayer(Player playerPos) 
	{
		this.player = playerPos;
	}
	public SearchType getSearchType() 
	{
		return search;
	}
	public void setSearchType(SearchType searchType) 
	{
		this.search = searchType;
	}
	
	public void updatePlayerPos()
	{
		hunter.updateGoalNode(player.getCurrentNode());
	}
	
	public void initHunterEnemy()
	{
		if(search == SearchType.ASTAR)
		{
			hunter = new AStarEnemy(player.getCurrentNode());
		}
		hunt();
	}
	
	public void hunt()
	{
		updatePlayerPos();
		hunter.traverse(maze, currentPos);
		setCurrentPos(hunter.returnFinalNode());
	}
}
