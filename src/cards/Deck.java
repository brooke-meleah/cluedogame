package cards;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import cards.Card.CardType;
import game.CluedoGame;
import game.Player;

public class Deck {

	List<Card> deck = new ArrayList<>();
	public List<Card> characters;
	public List<Card> weapons;
	public List<Card> locations;

	// Weapon images
	private static Image candle;
	private static Image knife;
	private static Image rope;
	private static Image wrench;
	private static Image revolver;
	private static Image pipe;

	// character images
	private static Image white;
	private static Image scarlet;
	private static Image green;
	private static Image plum;
	private static Image mustard;
	private static Image peacock;

	// room images
	private static Image ballroom;
	private static Image billiardroom;
	private static Image conservatory;
	private static Image diningroom;
	private static Image hall;
	private static Image kitchen;
	private static Image library;
	private static Image study;
	private static Image lounge;

	// placeholder image
	private static Image clueclue;

	// reference back to the 'parent' game class
	CluedoGame game;

	public Deck(CluedoGame game) {
		
		try {
			// reading in weapon images
			candle = ImageIO.read(new File("../cluedo2/images/candle.png"));
			knife = ImageIO.read(new File("../cluedo2/images/knife.png"));
			rope = ImageIO.read(new File("../cluedo2/images/rope.png"));
			wrench = ImageIO.read(new File("../cluedo2/images/wrench.png"));
			revolver = ImageIO.read(new File("../cluedo2/images/revolver.png"));
			pipe = ImageIO.read(new File("../cluedo2/images/pipe.png"));

			// reading in room images
			ballroom = ImageIO.read(new File("../cluedo2/images/ballroom.png"));
			billiardroom = ImageIO.read(new File("../cluedo2/images/billiardroom.png"));
			conservatory = ImageIO.read(new File("../cluedo2/images/conservatory.png"));
			diningroom = ImageIO.read(new File("../cluedo2/images/diningroom.png"));
			hall = ImageIO.read(new File("../cluedo2/images/hall.png"));
			kitchen = ImageIO.read(new File("../cluedo2/images/kitchen.png"));
			library = ImageIO.read(new File("../cluedo2/images/library.png"));
			study = ImageIO.read(new File("../cluedo2/images/study.png"));
			lounge = ImageIO.read(new File("../cluedo2/images/lounge.png"));

			// reading in character images
			white = ImageIO.read(new File("../cluedo2/images/white.png"));
			scarlet = ImageIO.read(new File("../cluedo2/images/scarlet.png"));
			green = ImageIO.read(new File("../cluedo2/images/green.png"));
			plum = ImageIO.read(new File("../cluedo2/images/plum.png"));
			mustard = ImageIO.read(new File("../cluedo2/images/mustard.png"));
			peacock = ImageIO.read(new File("../cluedo2/images/peacock.png"));

			// random clue card images
			clueclue = ImageIO.read(new File("../cluedo2/images/clueclue.png"));

		} catch (IOException e) {
			System.out.println("file error.cards" + e.getMessage());

		}
		
		
		deck = new ArrayList<Card>();
		game = this.game;

		// fill the deck and create a solution before shuffling

		this.fill();
	}

	/**
	 * fill the sub-lists of the deck with cards in order to generate a solution
	 * before shuffling the cards together into one deck.
	 */

	private void fill() {
		// generate empty sub-groups in order to fill.

		characters = new ArrayList<Card>();
		weapons = new ArrayList<Card>();
		locations = new ArrayList<Card>();

		// fill the characters

		characters.add(new Card("Miss Scarlett", CardType.CHARACTER,scarlet));
		characters.add(new Card("Professor Plum", CardType.CHARACTER,plum));
		characters.add(new Card("Mrs. Peacock", CardType.CHARACTER,peacock));
		characters.add(new Card("Reverend Green", CardType.CHARACTER,green));
		characters.add(new Card("Colonel Mustard", CardType.CHARACTER,mustard));
		characters.add(new Card("Mrs. White", CardType.CHARACTER,white));

		// fill the weapons

		weapons.add(new Card("Candlestick", CardType.WEAPON,candle));
		weapons.add(new Card("Knife", CardType.WEAPON,knife));
		weapons.add(new Card("Lead Pipe", CardType.WEAPON,pipe));
		weapons.add(new Card("Revolver", CardType.WEAPON,revolver));
		weapons.add(new Card("Rope", CardType.WEAPON,rope));
		weapons.add(new Card("Wrench", CardType.WEAPON,wrench));

		// fill the locations

		locations.add(new Card("Kitchen", CardType.LOCATION,kitchen));
		locations.add(new Card("Ballroom", CardType.LOCATION,ballroom));
		locations.add(new Card("Conservatory", CardType.LOCATION,conservatory));
		locations.add(new Card("Dining Room", CardType.LOCATION,diningroom));
		locations.add(new Card("Billiard Room", CardType.LOCATION,billiardroom));
		locations.add(new Card("Library", CardType.LOCATION,library));
		locations.add(new Card("Lounge", CardType.LOCATION,lounge));
		locations.add(new Card("Hall", CardType.LOCATION,hall));
		locations.add(new Card("Study", CardType.LOCATION,study));
	}

	/**
	 * Takes a fresh set of sub-groups and generates a solution to be guessed in
	 * the game
	 * 
	 * @return solution
	 */

	public List<Card> generateSoln() {
		ArrayList<Card> soln = new ArrayList<Card>();
		Random random = new Random();

		// pick one card from each sub-group to be a solution component
		// and then remove it from the cards then dealt to the players.

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

	public void shuffle() {
		// combine all the sub-groups into the one game deck

		deck.addAll(characters);
		deck.addAll(weapons);
		deck.addAll(locations);

		// finally, shuffle the deck
		Collections.shuffle(deck);
	}

	/**
	 * deal the cards out to the players in the game. this method uses the
	 * players as a linked list using the fields in the Player
	 */

	public void deal(Player start) {

		Player toDeal = start;
		while (!deck.isEmpty()) {
			toDeal.deal(deck.remove(0));
			toDeal = toDeal.next();
		}

		System.out.println("Cards dealt.");
	}

}
