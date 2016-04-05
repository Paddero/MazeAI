package ie.gmit.sw.mazeStuff;

public interface AI {
	
	public void traverse(Node[][] maze, Node start);
	public void updateGoalNode(Node goal);
	public Node returnFinalNode();

}
