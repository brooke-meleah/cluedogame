package main;

import game.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	public CluedoGame game;
	private Board board;
	public Frame frame;
	
	public Main(){
		frame = new Frame(this);
		game = new CluedoGame(this);
		board = game.board;
		characterSelect();
	}
	
	/**
	 * This method creates and manages the game.
	 */
	public void newGame(){
		game.newGame();
	}
	
	/**
	 * Select the characters using the UI - specifically the CharacterSelect 
	 * panel through the Frame.
	 */
	public void characterSelect(){
		frame.characterSelect();
	}
	
	public CluedoGame getGame(){
		return game;
	}
	
	public static void main(String[] args){
		new Main();
	}
	
}
