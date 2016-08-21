
package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import cards.*;
import main.Main;

/**
 * The CluedoGame class contains the main game.
 */
public class CluedoGame {

	public List<Card> solution;
	public Board board;
	public Deck deck;
	public Deck reference;       //A 'dummy' deck of cards to check things against.
	public Scanner read;

	public Player start;
	public Player current;
	protected Main main;
	public State gameState;

	public enum State {
		UNSTARTED, ONGOING, OVER
	};

	public CluedoGame(Main m) {
		//prep stuff
		gameState = State.UNSTARTED;
		main = m;
		deck = new Deck(this);
		solution = deck.generateSoln();
		deck.shuffle();
		reference = new Deck(this);
		System.out.println("Welcome to CludoGame!");
	}

	/**
	 * Start a new game of Cluedo, and run turns until the game is won.
	 * 
	 */
	public void newGame(){
		gameState = State.ONGOING;
		board = new Board(this);
		deck.deal(start);

		current = start;

		//while (!gameOver){
			takeTurn(current);
			//current = current.next();
		//}
	}

	/**
	 * The main method of the game - turns will be taken one after the other
	 * in turn order until the game is won or lost. takeTurn() loops back to itself
	 * at the end of every iteration with the next player by using /endturn, 
	 * continuing the cycle.
	 * 
	 * @param current Player
	 */
	public void takeTurn(Player p){
		
		//clear the console out
		for (int i = 0; i < 12; i++){
			System.out.println("");
		}
		
		//check if this player can take a turn in the first place
		current = p;

		if (!current.validPlayer()){
			System.out.println(current + "Is an invalid Player!");
			return;
		}
		//they are valid - take turn and roll the dice for them.
		System.out.println(current.getName() + "'s Turn!");
		int diceroll = (int)(Math.random() * 6+1);
		
		//pass to move player method to take care of movement.
		board.movePlayer(diceroll, current);

		//loop around offering commands
		while (true){
		//accept any commands - until /endturn
		System.out.println("Enter Command: (/accuse, /suggest, /endturn)");
		read = new Scanner(System.in);
		String line = read.nextLine();
		parseCommand(line);
		}
	}


	/**
	 * This method takes the input from a character select panel and 
	 * creates the order in the game (the players are all linked, so
	 * by setting the start you bring in all the linked players via 
	 * the Player.next fields/methods
	 */
	public void allocatePlayers(List<Player> pList){
		start = pList.get(0);
	}

	/**
	 * This builds the suggestion and cycles through each player to check
	 * for any refuting.
	 * 
	 * This method terminates by calling the next player's turn.
	 */
	public void suggest(){
		if (!current.location.isRoom()){
			System.out.println("You must be in a room to suggest!");
			return;
		}
		Selection s = new Selection(current, this, false);
		List<String> check = s.selection();
		
		if (check.equals(new ArrayList<String>())) 
			takeTurn(current.next());

		//now begin refuting.
		boolean refuted = false;
		Player p = current.next();    //start with the person next to them

		while (!p.equals(current) && !refuted){
			List<String> match = new ArrayList<String>();

			//check for the matching cards.
			for (Card c : p.hand()){
				for (String sug : check){
					if (c.getName().equalsIgnoreCase(sug)){
						match.add(c.name);
					}
				}
			}

			//if available, print the cards and ask for a selection.
			if(!match.isEmpty()){
				while (!refuted){
					System.out.println("Your matches are: " + match.toString());
					System.out.println("Please choose one to refute. [case sensitive]");
					read = new Scanner(System.in);
					String refutename = read.nextLine();
					
					if (match.contains(refutename)){
						System.out.println("You show " + current.getName() + " " + refutename);
						refuted = true;
					}
				}
			}
		}
		if (!refuted){
			System.out.println("No one could refute... Strange... NEXT TURN!");
		}
		takeTurn(current.next());
	}
	
	/**
	 * This method handles the accusations.
	 */
	public void accuse(){
		//you have to be in a room to accuse
		if (!current.location.isRoom()){
			System.out.println("You must be in a room to accuse!");
			return;
		}
		
		//check its possible to win
		//System.out.println(solution.toString());
		
		//create a selection from the user
		Selection s = new Selection (current, this, true);
		List<String> check = s.selection();
		int count = 0;
		
		for (Card c : solution){
			for (String a : check){
				if (a.equalsIgnoreCase(c.getName()))
					count++;
			}
		}
		
		if (count == 3){
			System.out.println("||---------GAME WON---------||");
			System.out.println("Winner: " + current);
			System.out.println("Solution " + check.toString());
			gameState = State.OVER;
			return;
		}
		else {
			System.out.println("Incorrect Accusation!");
			System.out.println(current + "has been eliminated!");
			current.setAccused();
			takeTurn(current.next());
		}
	}

	/**
	 * check for any of the game commands = /help /exit etc.
	 */
	public void parseCommand(String in){
		System.out.println("command!");
		String command = in.substring(1);
		command.trim();

		switch(command){
		case "help":
			printHelpMessage();
			break;
		case "exit":
			System.exit(0);
		case "hand":
			if (current.hand().isEmpty()){
				System.out.println("You have no cards!");
				break;
			}
			else {
				System.out.println(current.hand().toString());
				break;
			}
		case "suggest":
			suggest();
			break;
		case "accuse":
			accuse();
			break;
		case "endturn":
			takeTurn(current.next());
			break;
		case "key":
			printKey();
			break;
		case "passage":
			board.usePassage(current);
			break;
		default:
			System.out.println("Please enter a valid command (hint: use /help)");
		}
	}

	/**
	 * print a help message of the available commands
	 */
	public void printHelpMessage(){
		String help = " \n" +
				"Cluedo Game Help!\n" +
				"/help - prints a list of possible commands\n" + 
				"/key - prints a key for the text-based map\n" +
				"/hand - see your currrent hand\n" +
				"/passage - use a secret passage\n" +
				"/suggest - suggest a solution (must use the room you are in)\n" +
				"/accuse - create an accusation, try to win the game\n" + 
				"/endturn - ends your turn\n"+
				"/exit - ends the game, quits the window";
		System.out.println(help);
	}
	
	
	/**
	 * Print a simple key to help the user understand the map
	 */
	public void printKey(){
		String key = " \n" +
				"Cluedo Key:\n" +
				"# : Room\n" +
				"+ : Hallway\n" +
				"O : Door\n" +
				"= : Secret Passage\n" +
				"S : Miss Scarlett\n" +
				"P : Professor Plum\n" +
				"C : Mrs. Peacock\n" +
				"G : Reverend Green\n" +
				"M : Colonel Mustard\n" +
				"W : Mrs. White\n" +
				"@ : Current player\n";
		System.out.println(key);
	}

}
