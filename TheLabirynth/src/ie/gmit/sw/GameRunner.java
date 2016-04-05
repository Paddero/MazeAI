package ie.gmit.sw;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import ie.gmit.sw.Player.Player;
import ie.gmit.sw.mazeStuff.AI;
import ie.gmit.sw.mazeStuff.AStarHelp;
import ie.gmit.sw.mazeStuff.Node;
import ie.gmit.sw.mazeStuff.TheCreator;
import ie.gmit.sw.mazeStuff.TheRecursiveBacktracker;
import ie.gmit.sw.Enemy.Enemy;
import ie.gmit.sw.Enemy.Enemy.SearchType;
import ie.gmit.sw.Player.*;
public class GameRunner implements KeyListener {
	
	//Setting size to 60 as you want 60x60
	private static final int MAZE_DIMENSION = 60;
	private Node[][] model;
	private int currentRow;
	private int currentCol;
	private GameView view;
	private Node endGoal;
	private Player player;
	
	public GameRunner() throws Exception
	{	
		player = new Player();
		TheCreator m = new TheRecursiveBacktracker();
		m.createMaze(MAZE_DIMENSION, MAZE_DIMENSION);
		model = m.getMaze();
		endGoal = m.getGoalNode();
    	view = new GameView(model);
    	view.initPlayer(player);
    	
    	Dimension d = new Dimension(GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
    	view.setPreferredSize(d);
    	view.setMinimumSize(d);
    	view.setMaximumSize(d);
    	
    	JFrame f = new JFrame("GMIT - B.Sc. in Computing (Software Development)");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.addKeyListener(this);
        f.getContentPane().setLayout(new FlowLayout());
        f.add(view);
        f.setSize(1000,1000);
        f.setLocation(100,100);
        f.pack();
        f.setVisible(true);
        initPlayer();
        initEnemies();
        
        
	}
	
	private void initPlayer()
	{   	
		boolean isValid = false;
		while(!isValid)
		{
			currentRow = (int) (MAZE_DIMENSION * Math.random());
			currentCol = (int) (MAZE_DIMENSION * Math.random());
			if(model[currentRow][currentCol].getNodeType() == ' ')
			{
				isValid = true;
			}
		}
    	model[currentRow][currentCol].setNodeType('E');
    	player.setCurrentNode(model[currentRow][currentCol]);
    	updateView(); 		
	}
	
	private void updateView()
	{
		view.setCurrentRow(currentRow);
		view.setCurrentCol(currentCol);
		player.setCurrentNode(model[currentRow][currentCol]);
	}
	
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentCol < MAZE_DIMENSION - 1) 
        {
        	if (isValidMove(currentRow, currentCol + 1)) 
        	{
        		currentCol++;   		
        	}
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && currentCol > 0) 
        {
        	if (isValidMove(currentRow, currentCol - 1)) 
        	{
        		currentCol--;	
        	}
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP && currentRow > 0) 
        {
        	if (isValidMove(currentRow - 1, currentCol))
    		{
    			currentRow--;
    		}
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentRow < MAZE_DIMENSION - 1) 
        {
        	if (isValidMove(currentRow + 1, currentCol)) 
    		{
    			currentRow++;        	  		
    		}
        }
        else if (e.getKeyCode() == KeyEvent.VK_Z)
        {
        	view.toggleZoom();
        }
        else
        {
        	return;
        }
        
        updateView();       
    }
    public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

    
	private boolean isValidMove(int r, int c)
	{
		if (r <= model.length - 1 && c <= model[r].length - 1 && (model[r][c].getNodeType() == ' ' ||model[r][c].getNodeType() == 'C'))
		{
			model[currentRow][currentCol].setNodeType(' ');
			model[r][c].setNodeType('E');
			return true;
		}
		else if(r <= model.length - 1 && c <= model[r].length - 1 && model[r][c].getNodeType() == '?')
		{
			model[r][c].setNodeType('X');
			Thread help = new Thread() 
			{
			    public void run() 
			    {

			        try 
			        { 
						AI helperPath = new AStarHelp(endGoal);
						helperPath.traverse(model,model[currentRow][currentCol]);
			        } 
			        catch(Exception e) 
			        {
			            System.out.println(e);
			        }
			        return;
			    } 
			    
			};
			help.start();
			return false;
		}
		else if(r <= model.length - 1 && c <= model[r].length - 1 && model[r][c].getNodeType() == 'G')
		{
			System.out.println("YOU WIN");
			System.exit(0);
			return false;
		}
		else
		{
			return false;
		}
	}
	
	
	public void initEnemies()
	{
		for(int i = 1 ; i <= 5 ; i++)
		{
			Enemy.SearchType search;
			if(i % 2 == 0)
			{
				search = SearchType.ITERDFS;
			}
			else
			{
				search = SearchType.ASTAR;
			}
			int tempRow = 2;
			int tempCol = 2;
			boolean isValid = false;
			while(!isValid)
			{
				tempRow = (int) (MAZE_DIMENSION * Math.random());
				tempCol = (int) (MAZE_DIMENSION * Math.random());
				
				if(model[tempRow][tempCol].getNodeType() == ' ')
				{
					isValid = true;
				}
			}
			int finalRow = tempRow;
			int finalCol = tempCol;
			
			Thread enemy = new Thread() 
			{
			    public void run() 
			    {
			        try 
			        { 
			        	Enemy enemy = new Enemy(player, search, model[finalRow][finalCol], model);
			        	enemy.initHunterEnemy();
			        } 
			        catch(Exception e) 
			        {
			            System.out.println(e);
			        }
			    }    
			};
			enemy.start();
		}
	}
	
	public static void main(String[] args) throws Exception 
	{
		new GameRunner();
	}
}
