package panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import game.CluedoGame;
import main.Main;
import panels.Frame.MenuItemListener;

public class Frame extends JFrame implements MouseListener, ActionListener {

	// backreference because helpful!
	public Main main;

	// keep all our panels in one place
	private gamePanel gp;
	private dicePanel dp;
	private handPanel hp;
	private optionPanel op;
	private textPanel tp;
	private SelectFrame sf;
	public CharacterSelect cs;

	JButton confirm;

	JMenuBar menuBar;
	JMenu menu;

	public Frame(Main m) {
		super("Cluedo Game");

		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		menu = new JMenu("Game options");

		JMenuItem start = new JMenuItem("Start game");
		start.setMnemonic(KeyEvent.VK_E);
		start.setActionCommand("startGame");
		JMenuItem end = new JMenuItem("Exit");
		end.setActionCommand("endGame");

		MenuItemListener menuItemListener = new MenuItemListener();

		start.addActionListener(menuItemListener);
		end.addActionListener(menuItemListener);

		menu.add(start);
		menu.add(end);
		menuBar.add(menu);

		main = m;

		gp = new gamePanel(this);
		dp = new dicePanel(this);
		hp = new handPanel(this, main.getGame());
		cs = new CharacterSelect(this, main.getGame());
		sf = new SelectFrame(this, main.getGame(), false);

		setLayout(new BorderLayout());
		// setting positions
		add(gp, BorderLayout.WEST);
		add(dp, BorderLayout.CENTER);
		add(hp, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		addMouseListener(this);
		setResizable(false);
		setVisible(true);
	}

	class MenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("startGame")) {
				System.out.println("open, please work");
				main.newGame();
			}
			if (e.getActionCommand().equals("endGame")) {
				System.out.println("end the game??");
				System.exit(0);
			}

		}
	}

	public void characterSelect() {
		while (!cs.finished){
			cs.characterSelect();
		}
		repaint();
		System.out.println("done......");
	}
	
	public int diceRoll(){
		System.out.println("pre-roll");
		while (main.game.gameState == CluedoGame.State.INPUT){
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return dp.dice;
	}
	
	/**
	 * access methods for the accuse/suggest mechanics
	 */
	public void openAccuse(){
		sf.accuse = true;
		sf.createSelection();
	}
	
	public void openSuggest(){
		sf.createSelection();
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

	public JPanel getGamePanel() {
		return gp;
	}
}