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


	public gamePanel() {
		try {
			boardImg = ImageIO.read(new File("../DiceWithJpanels/images/clueboard.png"));

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

	@Override
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		gr.drawImage(boardImg, 0, 0, null, null);
		
	}

	public void findSquare(int x, int y) {
		//System.out.println("aa");
		int a = x / 20 + 1;
		int b = (y - 25) / 20 + 1;
		System.out.println("Square[" + a + "," + b + "]");

	}


	
	


}
