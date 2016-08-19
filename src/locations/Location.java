package locations;

import java.util.ArrayList;
import java.util.List;

import game.Player;

public class Location {

	public List<Square> tiles = new ArrayList<Square>();
	public List<Player> players = new ArrayList<Player>();
	public List<Door> doors = new ArrayList<Door>();
	public String name;
	public SecretPassage secretpassage;

	public Location(String type) {
		this.name = type;
	}

	public String getName(){return name;}

	public void addSquare(Square s){
		tiles.add(s); 
		if (s instanceof Door){
			addDoor((Door)s);
		}
		if (s instanceof SecretPassage){
			secretpassage = (SecretPassage)s;
		}
	}
	
	public boolean contains(Square sq){
		for (Square tile : tiles){
			if (tile.x == sq.x && tile.y == sq.y) return true;
		}
		return false;
	}

	public void addDoor(Door s){
		doors.add(s); 
	}
	
	public void printRoom(){
		String info = "You are in " + name + ", you can suggest or exit";
			info = info + ("\n Your doors are: " + doors.toString());
		if (secretpassage != null){
			info = info + ("\nThere is a passage to: " + secretpassage.getEnd().name);
		}
		System.out.println(info);
	}
	
	public boolean hasPassage(){
		if (secretpassage != null)
			return true;
		return false;
	}
	/**
	 * returns if location is a room
	 * @return
	 */
	public boolean isRoom(){
		switch(name){
		case "empty": return false;
		case "ballroom": return true;
		case "conservatory": return true;
		case "billiardRoom": return true;
		case "library": return true;
		case "study": return true;
		case "hall": return true;
		case "lounge": return true;
		case "diningRoom": return true;
		case "kitchen": return true;
		case "hallway": return false;
		case "start": return false;
		default: return false;
		}
	}
	
	public List<Door> getDoors(){
		return doors;
	}
}



