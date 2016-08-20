package panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import game.CluedoGame;
import game.Player;

public class CharacterSelect extends JPanel implements ActionListener{
	
	private CluedoGame game;
	
	private JButton confirm;
	private JButton done;
	public boolean finished = false;
	private JFrame frame;
	private Player newPlayer;
	private List<Player> chars;
	private List<String> available;
	private List<JRadioButton> buttons;
	private JDialog selector;
	private JTextField tf;
	private List<String> charList = Arrays.asList("Miss Scarlett", "Professor Plum", "Mrs. Peacock",
				"Reverend Green", "Colonel Mustard", "Mrs. White");
	
	public CharacterSelect(JFrame parent, CluedoGame g){
		game = g;
		frame = parent;
		chars = new ArrayList<Player>();
		available = charList;
		setVisible(true);
	}
	
	public void inputChar(){
		//set up our JDialog
		JPanel selectPan = new JPanel();
		selectPan.setLayout(new FlowLayout());
		ButtonGroup options = new ButtonGroup();
		
		//create buttons for each AVAILABLE character
		for (String charString : available){
			JRadioButton b1 = new JRadioButton(charString);
			b1.addActionListener(this);
			options.add(b1);
			add(b1);
			options.add(b1);
		}
		
		//create other parts of it - the name field and the button to confirm it.
	    tf = new JTextField("[Player Name Here]", 20);
	    add(tf);
	    confirm = new JButton ("Confirm");
	    add(confirm);
	    
	    //a button to let the selector know if all players are assigned before all charcaters.
	    done = new JButton ("Done");
	    add(done);

	    //put it all together.
		selector = new JDialog(frame, "CharacterSelect");
		selector.add(selectPan);
	}
	
	/**
	 * This method is called when the confirm button is pushed - it checks that the inputs are 
	 * valid before creating a Character object with the selected options and passing it back 
	 * to the game.
	 */
	public void createPlayer(){
		//initialise/create the variables.
		String character = "";
		String player;
		boolean valid = true;
		
		//pull the info from the confirmed options.
		for (JRadioButton but : buttons){
			if (but.isSelected()){
				character = but.getText();
			}
		}
		player = tf.getText();
		
		//check for valid input
		if (!charList.contains(character) || character.equals("")){
			JOptionPane.showMessageDialog(frame,
				    "Please choose a valid character and confirm again.");
			valid = false;
		}
		if (player.equals(new String())){
			JOptionPane.showMessageDialog(frame,
					"Please choose a valid name and confirm again.");
			valid = false;
		}
		
		Player newChar = null;
		
		//valid inputs -> new character created.
		if (valid){
			newChar = new Player (player, character, null);
			available.remove(character);
			chars.add(newChar);
		}
		
		if (newChar == null){
			System.out.println("null character");
			throw new RuntimeException();
		}
		
		//add the player to the list and link them up. once the players are done being assigned, 
		//send the completed playerlist back up the pipeline.
		if (finished){
			chars.add(newChar);
			Player st = chars.get(0);
			Player end = chars.get(chars.size()-1);
			end.setNext(st);
			game.allocatePlayers(chars);
		}
		else if (!finished && game.start == null){
			chars.add(newChar);
		}
		else{
			chars.add(newChar);
			Player cur = chars.get(chars.size()-1);
			Player prev = chars.get(chars.size()-2);
			prev.setNext(cur);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		if (src.equals(confirm)){
			createPlayer();
		}
		if (src.equals(done) || available.size() == 1){
			finished = true;
			createPlayer();
		}
	}

}
