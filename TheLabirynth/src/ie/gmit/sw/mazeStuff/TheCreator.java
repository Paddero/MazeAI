package ie.gmit.sw.mazeStuff;

public interface TheCreator {
	
	public Node[][] getMaze();
	public void createMaze(int rows , int cols);
	public Node getGoalNode();

}
