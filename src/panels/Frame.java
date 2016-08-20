package panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Frame extends JFrame implements MouseListener, ActionListener {

	gamePanel gp;
	dicePanel dp;
	handPanel hp;
	optionPanel op;
	textPanel tp;
	
	JButton confirm;

	public Frame(){
		super ("Cluedo Game");
		gp = new gamePanel();
		dp = new dicePanel();
		hp = new handPanel();
		op = new optionPanel();
		tp = new textPanel();
		setLayout(new BorderLayout());
		add(gp, BorderLayout.NORTH);
		add(dp, BorderLayout.WEST);
		add(hp, BorderLayout.CENTER);
		add(op, BorderLayout.EAST);
		add(tp, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		addMouseListener(this);
		setResizable(false);
		setVisible(true);
	}

	public Character characterSelect(List<String> available){


		return null;
	}

	public static void main(String[] args) {
		new Frame();
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