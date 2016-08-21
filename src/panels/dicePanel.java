package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class dicePanel extends JPanel implements ActionListener {

	// number images
	private static Image oneImg;
	private static Image twoImg;
	private static Image threeImg;
	private static Image fourImg;
	private static Image fiveImg;
	private static Image sixImg;
	
	private static Image clueNum;
	
	//the backreference
	private Frame frame;

	// buttons
	private JButton roll;
	private JButton accuse;
	private JButton suggest;
	private JButton endTurn;
	private JButton newGame;

	// the image that actually gets drawn4
	public Image drawnImg;

	/**
	 * Constructor for dicePanel , reads in all the images of the numbers has
	 * buttons for the suggestion, accuse ,dice roll and end turn
	 * 
	 */
	public dicePanel(Frame p) {
		frame = p;
		try {
			// reading in number images
			oneImg = ImageIO.read(new File("../cluedo2/images/one.png"));
			twoImg = ImageIO.read(new File("../cluedo2/images/two.png"));
			threeImg = ImageIO.read(new File("../cluedo2/images/three.png"));
			fourImg = ImageIO.read(new File("../cluedo2/images/four.png"));
			fiveImg = ImageIO.read(new File("../cluedo2/images/five.jpg"));
			sixImg = ImageIO.read(new File("../cluedo2/images/six.jpg"));
			clueNum = ImageIO.read(new File("../cluedo2/images/cluenumber.png"));
			drawnImg = clueNum;
		} catch (IOException e) {
			System.out.println("file error.dice" + e.getMessage());
		}
		Dimension size = new Dimension(150, 470);
		setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.black));

		// setting the dimension used for the buttons
		Dimension button = new Dimension(140, 29);

		
		roll = new JButton("Roll Dice");
		accuse = new JButton("Accuse");
		suggest = new JButton("Suggest");
		endTurn = new JButton("End Turn");
		newGame = new JButton("New Game");

		newGame.setPreferredSize(button);
		newGame.addActionListener(this);
		add(newGame, BorderLayout.SOUTH);

		accuse.setPreferredSize(button);
		accuse.addActionListener(this);
		add(accuse, BorderLayout.SOUTH);

		suggest.setPreferredSize(button);
		suggest.addActionListener(this);
		add(suggest, BorderLayout.SOUTH);

		endTurn.setPreferredSize(button);
		endTurn.addActionListener(this);
		add(endTurn, BorderLayout.SOUTH);

		roll.setPreferredSize(button);
		roll.addActionListener(this);
		add(roll, BorderLayout.NORTH);

	}

	/**
	 * Works out what button has be pushed and performs the appropriate action
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		if (src == roll) {
			rollDice();
		} else if (src == accuse) {
			// do the accuse thing
			System.out.println("ACCUSE");
		} else if (src == suggest) {
			// do the suggest thing
			System.out.println("SUGGEST");

		} else if (src == endTurn) {
			// do the end turn thing
			System.out.println("YOU HAVE ENDED");
		}
		else if (src == newGame){
			frame.main.newGame();
		}
	}
	/**
	 * the diceRoll method returns a number between 0 and 7, like rolling a dice
	 * then updates the drawn number so that it shows what number was rolled
	 * 
	 */
	private void rollDice() {

		double d = 6 * Math.random() + 1;
		int i = (int) d;
		if (i == 1) {
			drawnImg = oneImg;
		} else if (i == 2) {
			drawnImg = twoImg;
		} else if (i == 3) {
			drawnImg = threeImg;
		} else if (i == 4) {
			drawnImg = fourImg;
		} else if (i == 5) {
			drawnImg = fiveImg;
		} else {
			drawnImg = sixImg;
		}
		repaint();

	}

	// @Override
	// public Dimension getPreferredSize() {
	// return new Dimension(150, 450);
	// }

	/**
	 * draws the current number that has been rolled 
	 * 
	 */
	@Override
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		gr.drawImage(drawnImg, 10, 180, null, null);
	}

}
