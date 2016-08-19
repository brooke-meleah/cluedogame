package locations;

import game.Board;
import game.Player;

public class Square {
	public Board parent;
	public Location location;
	public int x;
	public int y;
    public Player player = null;
    
    //pathfinding fields
    public Square prev;
    public int dist;

	public Square(int x, int y, Location loc,Board parent) {
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.location = loc; 
		}
	
	public char getSymbol(){
		return ' ';
		}
	
	public String toString(){
		return x+","+y;
	}
	
	}


