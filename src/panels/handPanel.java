package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class handPanel extends JPanel {

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

	private static Image clueclue;

	/**
	 * Reads in all the possible images to have in a hand
	 * 
	 */
	public handPanel() {
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

			//reading in character images
			white = ImageIO.read(new File("../cluedo2/images/white.png"));
			scarlet = ImageIO.read(new File("../cluedo2/images/scarlet.png"));
			green = ImageIO.read(new File("../cluedo2/images/green.png"));
			plum = ImageIO.read(new File("../cluedo2/images/plum.png"));
			mustard = ImageIO.read(new File("../cluedo2/images/mustard.png"));
			peacock = ImageIO.read(new File("../cluedo2/images/peacock.png"));

			//random clue card images
			clueclue = ImageIO.read(new File("../cluedo2/images/clueclue.png"));

		} catch (IOException e) {
			System.out.println("file error.cards" + e.getMessage());

		}
		Dimension size = new Dimension(600, 140);
		setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.black));
	}

	
	/**
	 * draws the hand of the current player with the overridden painComponent method
	 * 
	 */
	@Override
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		gr.drawImage(knife, 0, 0, null, null);
		gr.drawImage(white, 100, 0, null, null);
		gr.drawImage(pipe, 200, 0, null, null);
		gr.drawImage(hall, 300, 0, null, null);
		gr.drawImage(clueclue, 400, 0, null, null);
		gr.drawImage(clueclue, 500, 0, null, null);
	}
}
