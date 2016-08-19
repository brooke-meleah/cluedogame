package locations;

import game.Board;

public class StartSquare extends Square{

	public StartSquare(int x, int y, Location loc, Board parent) {
		super(x, y, loc, parent);
	
	}
	public char getSymbol() {
		if (this.player != null){
			if (this.player == parent.game.current)
				return player.getSymbol();
			else
				return player.getSymbol();
		}
		return 'X';
	}
}
