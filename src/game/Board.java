package game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

import locations.Door;
import locations.HallSquare;
import locations.Location;
import locations.RoomSquare;
import locations.SecretPassage;
import locations.Square;
import locations.StartSquare;
import panels.gamePanel;

public class Board {

	public CluedoGame game;
	public Square[][] squares = new Square[24][25];
	public List<Location> roomList = new ArrayList<>();

	public Location empty = new Location("empty");
	public Location ballroom = new Location("ballroom");
	public Location conservatory = new Location("conservatory");
	public Location billiardRoom = new Location("billiardRoom");
	public Location library = new Location("library");
	public Location study = new Location("study");
	public Location hall = new Location("hall");
	public Location lounge = new Location("lounge");
	public Location diningRoom = new Location("diningRoom");
	public Location kitchen = new Location("kitchen");
	public Location hallway = new Location("hallway");
	public Location start = new Location("start");

	public Board(CluedoGame parent) {
		game = parent;
		makeBoard();

	}

	// --------- BOARD MANIPULATION METHODS ---------------

	/**
	 * A method to move the player, generating eligible squares for the player
	 * to move into, and then accepting input to move them.
	 * 
	 * @return if the move was successful
	 */
	public boolean movePlayer(int diceroll, Player player) {
		
		if (player.location.isRoom()){
			player.location.printRoom();
			exitRoom(player);
		}

		List<Square> available = new ArrayList<Square>();
		List<Square> near = new ArrayList<Square>();
		int centerX = player.position()[0];
		int centerY = player.position()[1];
		Square startsq = squares[centerX][centerY];
		
		//first, load them all into a list assuming no obstacles
		for (int x = -diceroll; x < diceroll; x++) {
			for (int y = -diceroll; y < diceroll; y++) {
				if (isValidSquare(centerX + x, centerY + y)) {
					//System.out.println((x) + "," + (y));
					Square temp = squares[centerX + x][centerY + y];
					if (Math.abs(x) + Math.abs(y) < diceroll + 1) {
						near.add(temp);

					}
				}
			}
		}
		
		//use dijkstras algo to test the shortest path to each square
		for (Square s : near){
			if (squarePath(startsq, s, near) <= diceroll){
				available.add(s);
			}
		}
		

		// print where everything is and highlight the current player with
		// the current player icon - setup temp fields to hold the new
		// movement.
		//printBoard();
		boolean moved = false;
		int x = 0;
		int y = 0;

		System.out.println("You rolled a: " + diceroll);
		System.out.println("You are at " + centerX + "," + centerY);
		System.out.println("Available Moves: " + available.toString());
		
		
		while (!moved) {
			System.out.println("Where do you move? (x y)");
			Scanner read = new Scanner(System.in);
			// read the input and cut off any brackets or commas.
			if (!read.hasNextInt())
				System.out.println(read.next());
			else
				x = read.nextInt();

			while (!read.hasNextInt() && read.hasNext()) {
				read.next();
			}
			y = read.nextInt();

			// check if move is valid
			if (isValidSquare(x, y)) {
				for (Square sq : available) {
					//change the players location to the available square they moved to
					// as well as updating the references to the player
					if (sq.x == x && sq.y == y) {
						squares[centerX][centerY].player = null;
						squares[x][y].player = player;
						player.x = x;
						player.y = y;
						Location loc = squares[x][y].location;
						player.location = loc;
						//print success
						System.out.println("Move successful!");
						
						//if a player moved onto a door - move them into a room.
						if (squares[x][y] instanceof Door){
							loc.players.add(player);
							squares[x][y].player = null;
							
							//place the player on a random room tile to draw
							Random r = new Random();
							for (Square rs : loc.tiles){
								if (rs.player == null && rs instanceof RoomSquare && r.nextInt(10) > 5){
									rs.player = player;
									squares[rs.x][rs.y].player = player;
									System.out.println("You are in " + loc.name);
									//printBoard();
									return true;
								}
							}
						}
						//print updated board
						//printBoard();
						return true; // move is fine, finish and break loop
					}
				}
			}
			System.out.println("Move invalid - please try again!");
		}
		return false;
	}
	
	/**
	 * Called inside the movePlayer() method, this handles which door to exit from.
	 * @param p
	 * @return
	 */
	public void exitRoom(Player p){
		System.out.println("Which exit would you like to take? [x y] (or command)");
		Scanner read = new Scanner (System.in);
		
		if (!read.hasNextInt() && read.nextLine().contains("/")){
			game.parseCommand(read.nextLine());
			return;
		}
		
		//read in the location as "x y"
		int x = 0; int y = 0;
		if (!read.hasNextInt())
			System.out.println(read.next());
		else
			x = read.nextInt();

		while (!read.hasNextInt() && read.hasNext()) {
			read.next();
		}
		y = read.nextInt();
		
		//iterate through the doors and print out their door options.
		for (Door d : p.location.getDoors()){
			if (d.x == x && d.y == y){
				squares[p.x][p.y].player = null;
				p.setPosition(x, y);
				squares[x][y].player = p;
				System.out.println("Using " + d.direction + "door.");
				return;
			}
		}
		
		//no matches - invalid door
		System.out.println("Invalid door: Please use format 'x y'");
		exitRoom(p);
	}
	
	/**
	 * This handles moving through a secret passage from one room to the other, 
	 * and is called when a player uses the /passage command.
	 * 
	 * @param p
	 */
	public void usePassage(Player p){
		//makin sure we have a package
		if (!p.location.hasPassage()){
			System.out.println("You must be in the room to use the passage!");
			return;
		}
		else {
			//find the right end to the start of the passage used.
			SecretPassage sp = p.location.secretpassage;
			System.out.println("Going to " + sp.getEnd().name);
			p.location = sp.getEnd();
			switch (p.location.getName()){
			case "Lounge":
				p.setPosition(2, 19);
				squares[2][19].player = p;
				break;
			case "Study": 
				p.setPosition(23, 20);
				squares[23][20].player = p;
				break;
			case "Kitchen":
				p.setPosition(4, 1);
				squares[4][1].player = p;
				break;
			case "Conservatory":
				p.setPosition(21, 5);
				squares[21][5].player = p;
				break;
			default:
				System.out.println("Wrong room!");
				break;
			}
		}
	}
	
	/**
	 * Using an application of Dikjstras algorithm in order to prune the 
	 * 'nearby' list of squares that are out of reach due to obstacles/rooms 
	 * and create an 'available' list for the movePlayer() method.
	 */
	public int squarePath(Square start, Square end, List<Square> near) {
		Queue<Square> fringe = new ArrayDeque<Square>();
		
		for (Square s : near){
			s.dist = 10;     //some arbitrary distance always larger than diceroll.
		}
		//setup the start of the loop
		start.dist = 0;
		fringe.add(start);
		while(!fringe.isEmpty()){
			Square s = fringe.poll();
			//found the path length - return
			if (s.equals(end)){
				return end.dist;
			}
			
			//check neighbours
			if (isValidSquare(s.x, s.y+1)&& squares[s.x][s.y+1].dist > s.dist){
				squares[s.x][s.y+1].dist = s.dist+1;
				fringe.add(squares[s.x][s.y+1]);}
			if (isValidSquare(s.x, s.y-1)&& squares[s.x][s.y-1].dist > s.dist){
				squares[s.x][s.y-1].dist = s.dist+1;
				fringe.add(squares[s.x][s.y-1]);}
			if (isValidSquare(s.x+1, s.y)&& squares[s.x+1][s.y].dist > s.dist){
				squares[s.x+1][s.y].dist = s.dist+1;
				fringe.add(squares[s.x+1][s.y]);}
			if (isValidSquare(s.x-1, s.y) && squares[s.x-1][s.y].dist > s.dist){
				squares[s.x-1][s.y].dist = s.dist+1;
				fringe.add(squares[s.x-1][s.y]);}
			
			fringe.remove(start);
		} 
		//should be unreachable - return 0 for compile;
		return 0;
	}
	/**
	 * Helper method to check if a square positioning is before the square is
	 * called from the board.
	 * 
	 */
	public boolean isValidSquare(int x, int y) {
		//System.out.println(x + "," + y);
		if (x > 23 || x < 0) {
			//x out of bounds
			return false;
		}
		if (y > 24 || y < 0) {
			//y out of bounds
			return false;
		}
		if (squares[x][y].player != null) {
			//cant move onto an occupied square
			return false;
		}
		if (squares[x][y] instanceof RoomSquare) {
			//cant move onto a room square
			return false;
		}
		if (squares[x][y].location == empty) {
			//cant move onto an 'empty' (functionally null) square
			return false;
		}
		return true;
	}

	// --------- BOARD CONSTRUCTION METHODS ---------------
	/**
	 * Calls all the maker methods to make the board. This calls on a series
	 * of helper/sub-methods, one for each specific room location.
	 */
	public void makeBoard() {

		makeempty();
		makeballroom();
		makeconservatory();
		makebilliardRoom();
		makelibrary();
		makestudy();
		makehall();
		makelounge();
		makediningRoom();
		makekitchen();
		makeDoor();
		makeSecretPassage();
		//makestart();
		makehallWay();

	}

	/**
	 * This method creates the empty space by creating squares that have a
	 * parameter "empty". This method creates the empty space around the edge of
	 * the room and for the rectangle in the middle of the room.
	 */
	public void makeempty() {
		// empty squares for around the edges
		for (int i = 0; i < 9; i++) {
			squares[i][0] = new Square(i, 0, empty, this);
			empty.addSquare(squares[i][0]);
		}
		for (int i = 015; i < 24; i++) {
			squares[i][0] = new Square(i, 0, empty, this);
			empty.addSquare(squares[i][0]);
		}
		squares[0][6] = new Square(0, 6, empty, this);
		empty.addSquare(squares[0][6]);
		squares[0][8] = new Square(0, 8, empty, this);
		empty.addSquare(squares[0][8]);
		squares[0][16] = new Square(0, 16, empty, this);
		empty.addSquare(squares[0][16]);
		squares[0][18] = new Square(0, 18, empty, this);
		empty.addSquare(squares[0][18]);
		squares[6][1] = new Square(6, 1, empty, this);
		empty.addSquare(squares[6][1]);
		squares[17][1] = new Square(17, 1, empty, this);
		empty.addSquare(squares[17][1]);
		squares[23][5] = new Square(23, 5, empty, this);
		empty.addSquare(squares[23][5]);
		squares[23][7] = new Square(23, 7, empty, this);
		empty.addSquare(squares[23][7]);
		squares[23][13] = new Square(23, 13, empty, this);
		empty.addSquare(squares[23][13]);
		squares[23][14] = new Square(23, 14, empty, this);
		empty.addSquare(squares[23][14]);
		squares[23][18] = new Square(23, 18, empty, this);
		empty.addSquare(squares[23][18]);
		squares[23][20] = new Square(23, 20, empty, this);
		empty.addSquare(squares[23][20]);
		squares[6][24] = new Square(6, 24, empty, this);
		empty.addSquare(squares[6][24]);
		squares[8][24] = new Square(8, 24, empty, this);
		empty.addSquare(squares[8][24]);
		squares[15][24] = new Square(15, 24, empty, this);
		empty.addSquare(squares[15][24]);
		squares[17][24] = new Square(17, 24, empty, this);
		empty.addSquare(squares[17][24]);

		// Empty Squares for the middle bit
		for (int i = 10; i < 15; i++) {
			for (int j = 10; j < 17; j++) {
				squares[i][j] = new Square(i, j, empty, this);
				empty.addSquare(squares[i][j]);
			}
		}

	}

	/**
	 * This method creates the ballroom by creating roomsquares that have a
	 * parameter ballroom
	 */
	public void makeballroom() {
		for (int i = 10; i < 14; i++) {
			for (int j = 0; j < 2; j++) {
				squares[i][j] = new RoomSquare(i, j, ballroom, this);
				ballroom.addSquare(squares[i][j]);
			}
		}
		for (int i = 8; i < 16; i++) {
			for (int j = 2; j < 5; j++) {
				squares[i][j] = new RoomSquare(i, j, ballroom, this);
				ballroom.addSquare(squares[i][j]);
			}
		}
		for (int i = 9; i < 15; i++) {
			int j = 5;
			squares[i][j] = new RoomSquare(i, j, ballroom, this);
			ballroom.addSquare(squares[i][j]);
		}
		for (int i = 8; i < 16; i++) {
			int j = 6;
			squares[i][j] = new RoomSquare(i, j, ballroom, this);
			ballroom.addSquare(squares[i][j]);
		}
		for (int i = 10; i < 14; i++) {
			int j = 7;
			squares[i][j] = new RoomSquare(i, j, ballroom, this);
			ballroom.addSquare(squares[i][j]);

		}
		squares[8][7] = new RoomSquare(8, 7, ballroom, this);
		ballroom.addSquare(squares[8][7]);
		squares[15][7] = new RoomSquare(15, 7, ballroom, this);
		ballroom.addSquare(squares[15][7]);
	}

	/**
	 * This method creates the conservatory by creating roomsquares that have a
	 * parameter conservatory
	 */
	public void makeconservatory() {
		for (int i = 18; i < 24; i++) {
			for (int j = 1; j < 4; j++) {
				squares[i][j] = new RoomSquare(i, j, conservatory, this);
				conservatory.addSquare(squares[i][j]);
			}
		}
		for (int i = 19; i < 24; i++) {
			int j = 4;
			squares[i][j] = new RoomSquare(i, j, conservatory, this);
			conservatory.addSquare(squares[i][j]);
		}
		for (int i = 19; i < 22; i++) {
			int j = 5;
			squares[i][j] = new RoomSquare(i, j, conservatory, this);
			conservatory.addSquare(squares[i][j]);
		}
	}

	/**
	 * This method creates the billiardroom by creating roomsquares that have a
	 * parameter billiardroom
	 */
	public void makebilliardRoom() {
		for (int i = 19; i < 24; i++) {
			for (int j = 8; j < 12; j++) {
				squares[i][j] = new RoomSquare(i, j, billiardRoom, this);
				billiardRoom.addSquare(squares[i][j]);
			}
		}
		for (int i = 18; i < 22; i++) {
			int j = 12;
			squares[i][j] = new RoomSquare(i, j, billiardRoom, this);
			billiardRoom.addSquare(squares[i][j]);

		}
		for (int j = 10; j < 13; j++) {
			int i = 18;
			squares[i][j] = new RoomSquare(i, j, billiardRoom, this);
			billiardRoom.addSquare(squares[i][j]);

		}
		squares[18][8] = new RoomSquare(18, 8, billiardRoom, this);
		billiardRoom.addSquare(squares[18][8]);
		squares[23][12] = new RoomSquare(23, 12, billiardRoom, this);
		billiardRoom.addSquare(squares[23][12]);
	}

	/**
	 * This method creates the library by creating roomsquares that have a
	 * parameter lirbary
	 */
	public void makelibrary() {

		for (int i = 18; i < 24; i++) {
			for (int j = 15; j < 18; j++) {
				squares[i][j] = new RoomSquare(i, j, library, this);
				library.addSquare(squares[i][j]);
			}
		}
		for (int i = 18; i < 23; i++) {
			int j = 18;
			squares[i][j] = new RoomSquare(i, j, library, this);
			library.addSquare(squares[i][j]);
		}
		squares[18][14] = new RoomSquare(18, 14, library, this);
		library.addSquare(squares[18][14]);
		squares[19][14] = new RoomSquare(19, 14, library, this);
		library.addSquare(squares[19][14]);
		squares[21][14] = new RoomSquare(21, 14, library, this);
		library.addSquare(squares[21][14]);
		squares[22][14] = new RoomSquare(22, 14, library, this);
		library.addSquare(squares[22][14]);
		squares[17][15] = new RoomSquare(17, 15, library, this);
		library.addSquare(squares[17][15]);
		squares[17][17] = new RoomSquare(17, 17, library, this);
		library.addSquare(squares[17][17]);

	}

	/**
	 * This method creates the study by creating roomsquares that have a
	 * parameter study
	 */
	public void makestudy() {
		for (int i = 18; i < 24; i++) {
			for (int j = 22; j < 25; j++) {
				squares[i][j] = new RoomSquare(i, j, study, this);
				study.addSquare(squares[i][j]);
			}
		}

		for (int i = 18; i < 23; i++) {
			int j = 21;
			squares[i][j] = new RoomSquare(i, j, study, this);
			study.addSquare(squares[i][j]);
		}

		squares[17][22] = new RoomSquare(17, 22, study, this);
		study.addSquare(squares[17][22]);
		squares[17][23] = new RoomSquare(17, 23, study, this);
		study.addSquare(squares[17][23]);

	}

	/**
	 * This method creates the hall by creating roomsquares that have a
	 * parameter hall
	 */
	public void makehall() {
		for (int i = 9; i < 14; i++) {
			for (int j = 19; j < 25; j++) {
				squares[i][j] = new RoomSquare(i, j, hall, this);
				hall.addSquare(squares[i][j]);
			}
			squares[9][18] = new RoomSquare(9, 18, hall, this);
			hall.addSquare(squares[9][18]);
			squares[10][18] = new RoomSquare(10, 18, hall, this);
			hall.addSquare(squares[18][10]);
			squares[13][18] = new RoomSquare(13, 18, hall, this);
			hall.addSquare(squares[13][18]);
			squares[14][18] = new RoomSquare(14, 18, hall, this);
			hall.addSquare(squares[14][18]);
			squares[14][19] = new RoomSquare(14, 19, hall, this);
			hall.addSquare(squares[19][14]);
			squares[14][21] = new RoomSquare(14, 21, hall, this);
			hall.addSquare(squares[14][21]);
			squares[14][22] = new RoomSquare(14, 22, hall, this);
			hall.addSquare(squares[14][22]);
			squares[14][23] = new RoomSquare(14, 23, hall, this);
			hall.addSquare(squares[23][14]);
			squares[14][24] = new RoomSquare(14, 24, hall, this);
			hall.addSquare(squares[14][24]);
		}
	}

	public void makelounge() {
		for (int i = 0; i < 6; i++) {
			for (int j = 20; j < 25; j++) {
				squares[i][j] = new RoomSquare(i, j, lounge, this);
				lounge.addSquare(squares[i][j]);
			}
		}
		for (int i = 1; i < 6; i++) {
			int j = 19;
			squares[i][j] = new RoomSquare(i, j, lounge, this);
			lounge.addSquare(squares[i][j]);
		}
		for (int j = 20; j < 24; j++) {
			int i = 6;
			squares[i][j] = new RoomSquare(i, j, lounge, this);
			lounge.addSquare(squares[i][j]);
		}
	}

	/**
	 * This method creates the diningroom by creating roomsquares that have a
	 * parameter diningroom
	 */
	public void makediningRoom() {
		for (int i = 0; i < 7; i++) {
			for (int j = 10; j < 15; j++) {
				squares[i][j] = new RoomSquare(i, j, diningRoom, this);
				diningRoom.addSquare(squares[i][j]);
			}
		}
		for (int i = 0; i < 5; i++) {
			int j = 9;
			squares[i][j] = new RoomSquare(i, j, diningRoom, this);
			diningRoom.addSquare(squares[i][j]);
		}
		for (int i = 0; i < 6; i++) {
			int j = 15;
			squares[i][j] = new RoomSquare(i, j, diningRoom, this);
			diningRoom.addSquare(squares[i][j]);
		}
		int i = 7;
		squares[i][10] = new RoomSquare(i, 10, diningRoom, this);
		diningRoom.addSquare(squares[i][10]);
		squares[i][11] = new RoomSquare(i, 11, diningRoom, this);
		diningRoom.addSquare(squares[i][11]);
		squares[i][13] = new RoomSquare(i, 13, diningRoom, this);
		diningRoom.addSquare(squares[i][13]);
		squares[i][14] = new RoomSquare(i, 14, diningRoom, this);
		diningRoom.addSquare(squares[i][14]);
		squares[i][15] = new RoomSquare(i, 15, diningRoom, this);
		diningRoom.addSquare(squares[i][15]);

	}

	/**
	 * This method creates the kitchen by creating roomsquares that have a
	 * parameter kitchen
	 */
	public void makekitchen() {
		for (int i = 0; i < 6; i++) {
			for (int j = 2; j < 6; j++) {
				squares[i][j] = new RoomSquare(i, j, kitchen, this);
				kitchen.addSquare(squares[i][j]);
			}
		}

		for (int i = 0; i < 5; i++) {
			int j = 1;
			squares[i][j] = new RoomSquare(i, j, kitchen, this);
			kitchen.addSquare(squares[i][j]);
		}
		for (int i = 1; i < 4; i++) {
			int j = 6;
			squares[i][j] = new RoomSquare(i, j, kitchen, this);
			kitchen.addSquare(squares[i][j]);
		}
		squares[5][6] = new RoomSquare(5, 6, kitchen, this);
		kitchen.addSquare(squares[5][6]);

	}

	/**
	 * This method creates the Start squares by creating startSquares that have
	 * a parameter start and the character that they start on. if the player is
	 * not in the game it will not create the start square
	 * 
	 */
	public void placePlayer(Player p) {
		if (p.getCharacter().equals("Mrs. White")) {
			System.out.println("make mrs white square");
			squares[9][0] = new StartSquare(9, 0, start, this);
			start.addSquare(squares[9][0]);
			squares[9][0].player = p;
			p.x = 9;
			p.y = 0;
			p.location = start;
		}

		// Mr Green start square
		if (p.getCharacter().equals("Reverend Green")) {
			squares[14][0] = new StartSquare(14, 0, start, this);
			start.addSquare(squares[14][0]);
			squares[14][0].player = p;
			p.x = 14;
			p.y = 10;
			p.location = start;
		}

		// Mrs Peacock start square
		if (p.getCharacter().equals("Mrs. Peacock")) {
			squares[23][6] = new StartSquare(23, 6, start, this);
			start.addSquare(squares[23][6]);
			squares[23][6].player = p;
			p.x = 23;
			p.y = 6;
			p.location = start;
		}

		// Prof Plum start square
		if (p.getCharacter().equals("Professor Plum")) {
			squares[23][19] = new StartSquare(23, 19, start, this);
			start.addSquare(squares[23][19]);
			squares[23][19].player = p;
			p.x = 23;
			p.y = 19;
			p.location = start;
		}

		// Miss Scarlett start square
		if (p.getCharacter().equals("Miss Scarlett")) {
			squares[7][24] = new StartSquare(7, 24, start, this);
			start.addSquare(squares[7][24]);
			squares[7][24].player = p;
			p.x = 7;
			p.y = 24;
			p.location = start;
		}

		// Col Mustard start square
		if (p.getCharacter().equals("Colonel Mustard")) {
			squares[0][17] = new StartSquare(0, 17, start, this);
			start.addSquare(squares[0][17]);
			squares[0][17].player = p;
			p.x = 0;
			p.y = 17;
			p.location = start;
		}
	}

	/**
	 * This method creates the secret passages by adding a secretPassage to each
	 * of the rooms that are linked together
	 */
	public void makeSecretPassage() {

		// Passage from lounge to conservatory
		squares[22][5] = new SecretPassage(22, 5, conservatory, lounge, this);
		lounge.addSquare(squares[22][5]);
		conservatory.secretpassage = (SecretPassage)squares[22][5];
		conservatory.addSquare(squares[22][5]);
		squares[0][19] = new SecretPassage(0, 19, lounge, conservatory, this);
		conservatory.addSquare(squares[23][21]);
		lounge.secretpassage = (SecretPassage)squares[0][19];
		lounge.addSquare(squares[0][19]);

		// Passage from study and kitchen
		squares[5][1] = new SecretPassage(5, 1, kitchen, study, this);
		kitchen.addSquare(squares[5][1]);
		kitchen.secretpassage = (SecretPassage)squares[5][1];
		study.addSquare(squares[5][1]);
		squares[23][21] = new SecretPassage(23, 21, study, kitchen, this);
		kitchen.addSquare(squares[23][21]);
		study.secretpassage = (SecretPassage) squares[23][21];
		study.addSquare(squares[23][21]);
	}

	/**
	 * This method creates the Start squares by creating startSquares that have
	 * a parameter start
	 * 
	 * 
	 */
	public void makeDoor() {

		// Doors for kitchen
		squares[4][6] = new Door(4, 6, kitchen, this);
		kitchen.addSquare(squares[4][6]);

		// Doors for ballroom
		squares[8][5] = new Door(8, 5, ballroom, "W", this);
		ballroom.addSquare(squares[8][5]);
		squares[15][5] = new Door(15, 5, ballroom, "E", this);
		ballroom.addSquare(squares[15][5]);
		squares[9][7] = new Door(9, 7, ballroom, "SW", this);
		ballroom.addSquare(squares[9][7]);
		squares[14][7] = new Door(14, 7, ballroom, "SE", this);
		ballroom.addSquare(squares[14][7]);

		// Doors for conservatory
		squares[18][4] = new Door(18, 4, conservatory, this);
		conservatory.addSquare(squares[18][4]);

		// Doors for Billiard Room
		squares[18][9] = new Door(18, 9, billiardRoom, "W", this);
		billiardRoom.addSquare(squares[18][9]);
		squares[22][12] = new Door(22, 12, billiardRoom, "S", this);
		billiardRoom.addSquare(squares[22][12]);

		// Doors for library
		squares[20][14] = new Door(20, 14, library, "N", this);
		library.addSquare(squares[20][14]);
		;
		squares[17][16] = new Door(17, 16, library, "W", this);
		library.addSquare(squares[17][16]);

		// Doors for study
		squares[17][21] = new Door(17, 21, study, this);
		study.addSquare(squares[17][21]);

		// Doors for hall
		squares[14][20] = new Door(14, 20, hall, "N", this);
		hall.addSquare(squares[14][20]);
		squares[11][18] = new Door(11, 18, hall, "N", this);
		hall.addSquare(squares[11][18]);
		squares[12][18] = new Door(12, 18, hall, "E", this);
		hall.addSquare(squares[12][18]);

		// Doors for lounge
		squares[6][19] = new Door(6, 19, lounge, this);
		lounge.addSquare(squares[6][19]);

		// Doors for diningRoom
		squares[7][12] = new Door(7, 12, diningRoom, "E", this);
		diningRoom.addSquare(squares[7][12]);
		squares[6][15] = new Door(6, 15, diningRoom, "S", this);
		diningRoom.addSquare(squares[6][16]);

		// Doors for kitchen
		squares[4][6] = new Door(4, 6, kitchen, this);
		kitchen.addSquare(squares[4][6]);
	}

	/**
	 * Fills all remaining squares with HallSquares
	 */

	public void makehallWay() {

		// fills rest of space with hallway.
		for (int i = 0; i < 24; i++) {
			for (int j = 0; j < 25; j++) {
				if (squares[i][j] == null) {
					squares[i][j] = new HallSquare(i, j, hallway, this);
					hallway.addSquare(squares[i][j]);

				}
			}
		}
	}

}
