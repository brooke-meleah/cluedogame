package panels;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame implements MouseListener {

	gamePanel gp;
	dicePanel dp;
	handPanel hp;
	optionPanel op;
	textPanel tp;

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

	public static void main(String[] args) {
		new Frame();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// System.out.println("click");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getX() < 100 && e.getY() > 470 && e.getY() < 630) {
			// GamePanel.diceRoll();
		}

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
}