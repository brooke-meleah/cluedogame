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

import game.Player;

public class CharacterSelect extends JPanel implements ActionListener{
	
	private JButton confirm;
	private JButton done;
	private JFrame frame;
	private List<Player> chars;
	private List<JRadioButton> buttons;
	private JDialog selector;
	private JTextField tf;
	private List<String> charList = Arrays.asList("Miss Scarlett", "Professor Plum", "Mrs. Peacock",
				"Reverend Green", "Colonel Mustard", "Mrs. White");
	
	public CharacterSelect(JFrame parent){
		frame = parent;
		chars = new ArrayList<Player>();
	}
	
	public void findChar(List<String> available){
		//set up our JDialog
		JPanel selectPan = new JPanel();
		selectPan.setLayout(new FlowLayout());
		ButtonGroup options = new ButtonGroup();
		
		//create buttons for each AVAILABLE character
		for (String charString : available){
			JRadioButton b1 = new JRadioButton(charString);
			b1.addActionListener(this);
			buttons.add(b1);
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
		
		//valid inputs -> new character created.
		if (valid){
			Player newChar = new Player (player, character, null);
			chars.add(newChar);
		}
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		if (src.equals(confirm)){
			createPlayer();
		}
	}

}
