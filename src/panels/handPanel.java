package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cards.Card;
import game.CluedoGame;
import game.Player;

public class handPanel extends JPanel {



	private JFrame fr;
	private CluedoGame cl;

	private static Image clueclue;

	private Player currentPlayer;
	private List<Card> hand;

	/**
	 * Reads in all the possible images to have in a hand
	 * 
	 */
	public handPanel(JFrame parent, CluedoGame g) {

		try {
			// clue image for placeholder
			clueclue = ImageIO.read(new File("../cluedo2/images/clueclue.png"));

		} catch (IOException e) {
			System.out.println("file error.cards" + e.getMessage());

		}
		Dimension size = new Dimension(600, 140);
		setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.black));
	}

	/**
	 * draws the hand of the current player with the overridden painComponent
	 * method
	 * 
	 */
	@Override
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		if (currentPlayer != null){
			hand = currentPlayer.hand();
			// this may or may not actually work but should draw the hand 
			for (int i = 0; i < 6; i++) {
				if (i < hand.size()) {
					gr.drawImage(hand.get(i).getImage(), i * 100, 0, null, null);
				} else
					gr.drawImage(clueclue, i * 100, 0, null, null);
			}
		}
		else { //no current - no game yet.
			for (int i = 0; i < 6; i++) {
				gr.drawImage(clueclue, i * 100, 0, null, null);
			}

		}
	}
}
