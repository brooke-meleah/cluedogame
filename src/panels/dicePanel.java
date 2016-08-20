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

public class dicePanel extends JPanel implements ActionListener{
	private static Image oneImg;
	private static Image twoImg;
	private static Image threeImg;
	private static Image fourImg;
	private static Image fiveImg;
	private static Image sixImg;

	private JButton roll; 
	
	public Image drawnImg;


	public dicePanel() {
		try {
			oneImg = ImageIO.read(new File("../cluedo2/images/cluedo1.jpg"));
			twoImg = ImageIO.read(new File("../cluedo2/images/cluedo2.png"));
			threeImg = ImageIO.read(new File("../cluedo2/images/cluedo3.png"));
			fourImg = ImageIO.read(new File("../cluedo2/images/cluedo4.jpg"));
			fiveImg = ImageIO.read(new File("../cluedo2/images/cluedo5.jpg"));
			sixImg = ImageIO.read(new File("../cluedo2/images/cluedo6.jpg"));
			drawnImg = oneImg;
		} catch (IOException e) {
			System.out.println("file error.dice");
		}
		Dimension size = new Dimension(100, 160);
		setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.black));
		Dimension button = new Dimension(100, 30);
		roll = new JButton("Roll Dice");
		roll.setPreferredSize(button);
		roll.addActionListener(this);
		add(roll,BorderLayout.NORTH);
		
	}
	@Override
	public void actionPerformed(ActionEvent evt) {
	    Object src = evt.getSource();
	    if (src == roll) {
	  rollDice();
	    } 
	}
	
	private void rollDice() {
		
		
		double d = 6*Math.random()+1;
		int i = (int)d;
		if(i==1){
			drawnImg=oneImg;
		}
		else if(i==2){
			drawnImg=twoImg;
		}
		else if(i==3){
			drawnImg=threeImg;
		}
		else if(i==4){
			drawnImg=fourImg;
		}
		else if(i==5){
			drawnImg=fiveImg;
		}
		else{
			drawnImg = sixImg;
		}
		repaint();
		
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(100, 160);
	}
	@Override
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		gr.drawImage(drawnImg, 10, 60, null, null);
	}

   
}
