package main;

import game.*;
import cards.*;
import locations.*;
import panels.*;

/**
 * This class serves to link the game and the gui, 
 * creating a functioning model for the cluedo game.
 * 
 * @author Brooke
 *
 */
public class Main {

	private CluedoGame game;
	private Board board;
	private Frame frame;
	
	public Main(){
		frame = new Frame();
		game = new CluedoGame();
		board = game.board;
	}
	
	/**
	 * This method creates and manages the game.
	 */
	public void newGame(){
		game.newGame();
	}
	
	public static void main(String[] args){
		new Main();
	}
	
}
