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

public class gamePanel extends JPanel {

	private static Image boardImg;

	/**
	 * constructor for the gamePanel, sets the size and gets the image for board
	 * 
	 */
	public gamePanel() {
		try {
			boardImg = ImageIO.read(new File("../cluedo2/images/clueboard.png"));

		} catch (IOException e) {
			System.out.println("file error.board");
		}
		Dimension size = new Dimension(450, 470);
		setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.black));
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(450, 470);
	}

	/**
	 * Overrides the paintcomponent method and uses it to draw the board
	 * 
	 */
	@Override
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		gr.drawImage(boardImg, 0, 0, null, null);
		
	}

	/**
	 * This method is passed an x,y from the mouseClicked event
	 * if the mouse is clicked on a board, and then it will 
	 * return the dimensions of the square clicked.
	 * @param x
	 * @param y
	 */
	public void findSquare(int x, int y) {
		int a = x / 20 + 1;
		int b = (y - 25) / 20 + 1;
		System.out.println("Square[" + a + "," + b + "]");

	}


	
	


}
