package locations;

import game.Board;
import game.Player;

public class Door extends Square{

	public String direction = "N/A";

	/**
	 * Create the Door object. 
	 */
	public Door (int x, int y, Location loc ,Board parent){
		super(x, y, loc, parent);
	}
	
	/**
	 * An alternative constructor - for rooms with more than one door. 
	 */
	public Door (int x, int y, Location loc, String dir, Board parent){
		this(x, y, loc, parent);
		direction = dir;
	}
	
	public String toString(){
		return "[" + x + "," + y + "]";
	}

	@Override
	public char getSymbol() {
		if (this.player != null){
			if (this.player == parent.game.current)
				return '@';
			else
				return '*';
		}
		
		return 'O';
	}

}
