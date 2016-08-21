package panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import main.Main;

public class Frame extends JFrame implements MouseListener, ActionListener {

	//backreference because helpful!
	public Main main;
	
	//keep all our panels in one place
	private gamePanel gp;
	private dicePanel dp;
	private handPanel hp;
	private optionPanel op;
	private textPanel tp;
	public CharacterSelect cs;
	
	JButton confirm;

	public Frame(Main m){
		super ("Cluedo Game");
		
		main = m;
		
		gp = new gamePanel(this);
		dp = new dicePanel(this);
		hp = new handPanel(this, main.getGame());
		cs = new CharacterSelect(this, main.getGame());

		
		setLayout(new BorderLayout());
		//setting positions 
		add(gp, BorderLayout.WEST);
		add(dp, BorderLayout.CENTER);
		add(hp, BorderLayout.SOUTH);
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		addMouseListener(this);
		setResizable(false);
		setVisible(true);
	}


	public void characterSelect(){
		while (!cs.finished)
			cs.characterSelect();
		System.out.println("done......");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// System.out.println("click");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getX() < 450 && e.getY() < 495) {
			// System.out.println("x: " + e.getX());
			// System.out.println("y: " + e.getY());
			int x = e.getX();
			int y = e.getY();
			gp.findSquare(x, y);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// System.out.println("release");
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// System.out.println("enter");
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// System.out.println("exit");
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
	}
}