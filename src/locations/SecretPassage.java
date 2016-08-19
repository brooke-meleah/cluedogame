package locations;

import game.Board;

public class SecretPassage extends Square{
	
	private Location start;
	private Location end;

	public SecretPassage(int x, int y, Location from, Location to, Board parent) {
		super(x, y, from, parent);
		setStart(this.location);
		setEnd(to);
	}

	@Override
	public char getSymbol() {
		return '=';
	}

	public Location getStart() {
		return start;
	}

	public void setStart(Location start) {
		this.start = start;
	}

	public Location getEnd() {
		return end;
	}

	public void setEnd(Location end) {
		this.end = end;
	}

}
