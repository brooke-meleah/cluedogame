package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cards.Card;
import cards.Deck;
import locations.Location;


/**
 * SUGGESTION:
 * This class handles the creation and refuting of suggestions through the turn
 * order with a series of sub methods and interaction with the players.
 * 
 * @author Brooke
 *
 */
public class Selection {

	public Player player;
	public CluedoGame game;
	public List<String> suggestion;
	public Scanner read;
	public Deck reference;
	private boolean accuse;

	/**
	 * This class handles the suggestion and the refuting of each theory.
	 * Used more often than accusations, suggestions are refuted by cycling
	 * through the player order and ending only when a card is presented to 
	 * refute the claim, or if no cards are shown.
	 * 
	 * This builds the suggestion and passes it to another method to handle
	 * the actual refuting of the suggestion.
	 */
	public Selection(Player p, CluedoGame g, boolean accusation) {
		this.player = p;
		this.game = g;
		suggestion = new ArrayList<String>();
		reference = game.reference;
		accuse = accusation;
	}

	public List<String> selection(){
		suggestion.add(selectPlayer());
		suggestion.add(selectWeapon());
		suggestion.add(selectRoom());
		if (!accuse && player.location.name.equalsIgnoreCase("hallway")){
			System.out.println("You must be in a room to suggest!");
			return new ArrayList<String>();
		}
		return suggestion;
	}

	/**
	 * gain a valid player suggestion
	 * 
	 * @return suggested player
	 */
	public String selectPlayer(){

		System.out.println("Suggested character? [e.g. 'miss scarlett']");
		read = new Scanner(System.in);
		String in = read.nextLine();

		if (in.contains("/"))                           //a slash comes before every command input
			game.parseCommand(read.nextLine());  

		for (Card c : reference.characters){
			if (c.getName().equalsIgnoreCase(in)){
				return in;
			}
		}

		//no matches - invalid player entered
		System.out.println("Invalid character");
		selectPlayer();

		//unreachable - it should cycle back through invalid players
		//until a positive result is returned.
		return null;
	}

	/**
	 * Gain a valid weapon suggestion 
	 * 
	 * @return suggested weapon
	 */
	public String selectWeapon(){

		System.out.println("Suggested weapon? [e.g. 'rope']");
		read = new Scanner(System.in);
		String in = read.nextLine();

		if (in.contains("/"))                           //a slash comes before every command input
			game.parseCommand(read.nextLine());  

		for (Card c : reference.weapons){
			if (c.getName().equalsIgnoreCase(in)){
				return in;
			}
		}

		//no matches - invalid player entered
		System.out.println("Invalid weapon");
		selectWeapon();

		//unreachable - it should cycle back through invalid players
		//until a positive result is returned.
		return null;
	}

	/**
	 * Gain a valid room suggestion 
	 * 
	 * @return suggested room
	 */
	public String selectRoom(){

		System.out.println("Suggested room? [hint: the one you're in]");
		read = new Scanner(System.in);
		String in = read.nextLine();

		if (in.contains("/"))                           //a slash comes before every command input
			game.parseCommand(read.nextLine());  

		//a suggestion must be in the room the player is in.
		if (!accuse){
			if (in.equalsIgnoreCase(player.location.getName())){
				return in;
			}
			//no matches - invalid player entered
			System.out.println("Invalid location: It must be " + player.location.getName());
			selectRoom();
		}
		
		//an accusation can be anywhere
		else {
			for (Card c : reference.locations){
				if (c.getName().equalsIgnoreCase(in)){
					return in;
				}
			}
		}

		//unreachable - it should cycle back through invalid players
		//until a positive result is returned.
		return null;

	}
}
