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

	private static Image candle;
	private static Image knife;
	private static Image rope;
	private static Image wrench;
	private static Image revolver;
	private static Image pipe;

	public handPanel() {
		try {
			candle = ImageIO.read(new File("../cluedo2/images/cluedoCandle.png"));
			knife = ImageIO.read(new File("../cluedo2/images/cluedoStabStab.png"));
			rope = ImageIO.read(new File("../cluedo2/images/cluedoRope.png"));
			wrench = ImageIO.read(new File("../cluedo2/images/cluedoWrench.png"));
			revolver = ImageIO.read(new File("../cluedo2/images/cluedoRevolver.png"));
			pipe = ImageIO.read(new File("../cluedo2/images/cluedoPipe.png"));

		} catch (IOException e) {
			System.out.println("file error.cards");

			
		}
		Dimension size = new Dimension(450, 150);
		setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.black));
	}

	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		gr.drawImage(knife, 0, 0, null, null);
		gr.drawImage(rope, 75, 0, null, null);
		gr.drawImage(pipe, 150, 0, null, null);
		gr.drawImage(revolver, 225, 0, null, null);
		gr.drawImage(candle, 300, 0, null, null);
		gr.drawImage(wrench, 375, 0, null, null);
	}
}
