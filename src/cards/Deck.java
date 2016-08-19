package cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import cards.Card.CardType;
import game.CluedoGame;
import game.Player;

public class Deck {
	
	List<Card> deck = new ArrayList<>();
	public List<Card> characters;
	public List<Card> weapons;
	public List<Card> locations;
	
	//reference back to the 'parent' game class
	CluedoGame game;

	public Deck(CluedoGame game) {
		deck = new ArrayList<Card>();
		game = this.game;
		
		//fill the deck and create a solution before shuffling
		
		this.fill();
	}
	
	/**
	 * fill the sub-lists of the deck with cards in order to generate a solution
	 * before shuffling the cards together into one deck.
	 */
	
	private void fill(){
		//generate empty sub-groups in order to fill.
		
		characters = new ArrayList<Card>();
		weapons = new ArrayList<Card>();
		locations = new ArrayList<Card>();
		
		//fill the characters
		
		characters.add(new Card("Miss Scarlett", CardType.CHARACTER));
		characters.add(new Card("Professor Plum", CardType.CHARACTER));
		characters.add(new Card("Mrs. Peacock", CardType.CHARACTER));
		characters.add(new Card("Reverend Green", CardType.CHARACTER));
		characters.add(new Card("Colonel Mustard", CardType.CHARACTER));
		characters.add(new Card("Mrs. White", CardType.CHARACTER));
		
		//fill the weapons
		
		weapons.add(new Card("Candlestick", CardType.WEAPON));
		weapons.add(new Card("Knife", CardType.WEAPON));
		weapons.add(new Card("Lead Pipe", CardType.WEAPON));
		weapons.add(new Card("Revolver", CardType.WEAPON));
		weapons.add(new Card("Rope", CardType.WEAPON));
		weapons.add(new Card("Wrench", CardType.WEAPON));
		
		//fill the locations
		
		locations.add(new Card("Kitchen", CardType.LOCATION));
		locations.add(new Card("Ballroom", CardType.LOCATION));
		locations.add(new Card("Conservatory", CardType.LOCATION));
		locations.add(new Card("Dining Room", CardType.LOCATION));
		locations.add(new Card("Billiard Room", CardType.LOCATION));
		locations.add(new Card("Library", CardType.LOCATION));
		locations.add(new Card("Lounge", CardType.LOCATION));
		locations.add(new Card("Hall", CardType.LOCATION));
		locations.add(new Card("Study", CardType.LOCATION));
	}
	
	/**
	 * Takes a fresh set of sub-groups and generates a solution to
	 * be guessed in the game 
	 * 
	 * @return solution
	 */
	
	public List<Card> generateSoln(){
		ArrayList<Card> soln = new ArrayList<Card>();
		Random random = new Random();
		
		//pick one card from each sub-group to be a solution component
		//and then remove it from the cards then dealt to the players.
		
		Card character = characters.get(random.nextInt(characters.size()));
		characters.remove(character);
		soln.add(character);
		
		Card weapon = weapons.get(random.nextInt(weapons.size()));
		weapons.remove(weapons);
		soln.add(weapon);
		
		Card location = locations.get(random.nextInt(locations.size()));
		locations.remove(location);
		soln.add(location);
		
		return soln;
	}
	
	/**
	 * Shuffle the deck and it's contents.
	 */
	
	public void shuffle(){
		//combine all the sub-groups into the one game deck
		
		
		deck.addAll(characters);
		deck.addAll(weapons);
		deck.addAll(locations);
		
		//finally, shuffle the deck
		Collections.shuffle(deck);
	}
	
	/**
	 * deal the cards out to the players in the game.
	 * this method uses the players as a linked list 
	 * using the fields in the Player
	 */
	
	public void deal(Player start){

		Player toDeal = start;
		while (!deck.isEmpty()){
			toDeal.deal(deck.remove(0));
			toDeal = toDeal.next();
		}
		
		System.out.println("Cards dealt.");
	}

}
