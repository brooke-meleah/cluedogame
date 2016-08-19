package cards;

import java.util.List;

/**
 * Represents the cards (character, weapon, and room) used in the cluedo game.
 * The cards include a name, a type, and a boolean that represents if they are
 * part of the game's solution.
 */

public class Card {

	public enum CardType {
		CHARACTER, WEAPON, LOCATION
	};

	public final String name;
	public CardType type;
	public boolean solution = false;

	/**
	 * Construct a card with a given name. Every card in the game has a unique
	 * name which identifies it.
	 * 
	 * @param name
	 */

	public Card(String cardname, CardType cardtype) {
		this.name = cardname;
		this.type = cardtype;
	}

	/**
	 * Get the name of this card.
	 * 
	 * @return name
	 */

	public String getName() {
		return this.name;
	};

	/**
	 * Get the type of this card.
	 * 
	 * @return name
	 */

	public CardType getType() {
		return this.type;
	};

	/**
	 * Check if this card is part of the solution.
	 * 
	 * @return solution
	 */

	public boolean isSolution() {
		return this.solution;
	};

	/**
	 * Check if this card is part of the solution.
	 * 
	 * @return solution
	 */
	public boolean isSuggestion(List<Card> suggestion) {

		//check this card against every card in the current
		//solution, by checking names.
		for (Card c : suggestion) {
			if (c.getName().equals(this.getName())) {
				return true;
			}
		}
		
		//if none, return fale
		return false;
	}
	
	public String toString(){
		return name;
	}

}
