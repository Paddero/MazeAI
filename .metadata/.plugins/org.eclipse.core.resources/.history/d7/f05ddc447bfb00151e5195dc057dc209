package ie.gmit.sw.mazeStuff;

import java.util.ArrayList;
import java.util.List;

public class AStarHelp extends AStar {

	private List<Node> finalList;
	private List<Node> newList = new ArrayList<Node>();
	
	public AStarHelp(Node goal)
	{
		super(goal);
	}
	
	public void traverse(Node[][] maze, Node node)
	{
		super.traverse(maze, node);
		finalList = super.returnList();
		revealPath();
	}
	public void revealPath()
	{
		finalList = super.returnList();

		Node curNode = finalList.get(finalList.size()-1);
		while(curNode != null)
		{
			newList.add(curNode);
			curNode = curNode.getParent();
		}
		System.out.println(newList);
		for(int i = newList.size() -1 ; i >= 1 ; i--)
		{
			curNode = newList.get(i);
			if(curNode != null)
			{
				if(curNode.getNodeType() == 'G')
				{
					break;
				}
				curNode.setNodeType('C');
			}
		}
		try 
		{ 
			Thread.sleep(5000);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		for(int i = newList.size() -1 ; i >= 1 ; i--)
		{
			curNode = newList.get(i);
			if(curNode != null)
			{
				if(curNode.getNodeType() != 'G' && curNode.getNodeType() != 'X' && curNode.getNodeType() != 'E')
				{
					curNode.setNodeType(' ');
				}
			}
		}
		newList.clear();
	}

}
