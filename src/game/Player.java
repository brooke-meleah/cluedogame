package game;

import java.util.ArrayList;
import java.util.List;

import cards.Card;
import locations.Location;

/**
 * 
 * The player class stores all info bout the player FIXME [more here]
 * 
 * @author Brooke
 *
 */

public class Player {

	private List<Card> hand;
	private String name; // the name of the PLAYER
	private String character; // the name of the CHARACTER played
	private Player next; // the 'next' player in the turn order
	private Player previous; // the 'previous' player in the turn order
	private String playerSymbol;
	public Location location;

	private boolean accused; // players who have accused incorrectly cannot take
								// turns

	public int x;
	public int y;

	public Player(String name, String character, Player previous) {
		this.name = name;
		this.character = character;
		this.previous = previous;
		this.accused = false;
		this.hand = new ArrayList<Card>();
	}

	/**
	 * find the player's hand - the list of cards that they hold.
	 * 
	 * @return
	 */

	public List<Card> hand() {
		return this.hand;
	}

	/**
	 * when creating the series of players, this is used to set the next player
	 * - only set after the next player has been created.
	 * 
	 */

	public void setNext(Player next) {
		this.next = next;
	}

	/**
	 * If a player has accused and been proven wrong, they cannot play but still
	 * must reveal their cards to suggestions - this is a marker that means they
	 * cannot take a turn.
	 */
	public void setAccused() {
		this.accused = true;
	}

	/**
	 * Set the player position.
	 */
	public void setPosition(int a, int b) {
		this.x = a;
		this.y = b;
	}

	/**
	 * This is a quick check whether a player has already been knocked out of
	 * the game or could be invalid for whatever reason.
	 * 
	 * @return whether a player can continue in the game
	 */
	public boolean validPlayer() {
		return !accused;
	}

	/**
	 * Return the player name
	 * 
	 * @return name
	 */

	public String getName() {
		return name;
	}

	
	/**
	 * Returns a relevant symbol for each character
	 * 
	 * @return name
	 */
	public char getSymbol() {

		switch (character) {
		case "Miss Scarlett":
			return 'S';
		case "Professor Plum":
			return 'P';
		case "Mrs. Peacock":
			return 'C';
		case "Reverend Green":
			return 'G';
		case "Colonel Mustard":
			return 'M';
		case "Mrs. White":
			return 'W';
		}
		return 'N';

	}

	/**
	 * Return the player name
	 * 
	 * @return name
	 */

	public String getCharacter() {
		return character;
	}

	/**
	 * Return the square coordinates that the player is currently on.
	 */
	public int[] position() {
		int[] position = { x, y };
		return position;
	}

	/**
	 * Returns the next player in the order - good for incrementing the player
	 * order and passing turns around the 'board'.
	 * 
	 * @return next
	 */

	public Player next() {
		return this.next;
	}

	/**
	 * Returns the previous player if needed. possibly cut this.
	 * 
	 * @return previous
	 */

	public Player prev() {
		return this.previous;
	}

	/**
	 * A card is dealt to a player - add it to their hand.
	 * 
	 */

	public void deal(Card card) {
		hand.add(card);
	}

	public String toString() {
		return "Player: " + name + " Character: " + " Hand: " + hand.toString();

	}

}
