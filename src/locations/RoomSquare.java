package locations;

import game.Board;

public class RoomSquare extends Square{

	public RoomSquare(int x, int y, Location loc, Board parent) {
		super(x, y, loc, parent);
	}

	@Override
	public char getSymbol() {
		if (this.player != null){
			if (this.player == parent.game.current)
				return '@';
			else
				return '*';
		}
		return '#';
	}

}
