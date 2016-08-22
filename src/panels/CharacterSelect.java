package panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
import main.Main;

public class CharacterSelect extends JFrame implements ActionListener{
	
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
	private List<String> charList = new LinkedList<String>(Arrays.asList("Miss Scarlett", "Professor Plum", "Mrs. Peacock",
				"Reverend Green", "Colonel Mustard", "Mrs. White"));
	
	public CharacterSelect(JFrame parent, CluedoGame g){
		game = g;
		frame = parent;
		chars = new ArrayList<Player>();
		available = charList;
		setVisible(true);
	}
	
	public void characterSelect(){
		System.out.println("yolo");
		createAndShowGUI();
	}
	
	 private void createAndShowGUI()
	    {
	        setTitle("Character Select.");
	        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        setLayout(new FlowLayout());
	        buttons = new ArrayList<JRadioButton>();
	        
	        // A perfect constructor, mostly used.
	        // A dialog with current frame as parent
	        // a given title, and modal
	        selector = new JDialog(this, "CharacterSelect", true);
			
			//create buttons for each AVAILABLE character
			for (String charString : available){
				JRadioButton b1 = new JRadioButton(charString);
				b1.addActionListener(this);
				selector.add(b1);
				buttons.add(b1);
			}
			
			//create other parts of it - the name field and the button to confirm it.
		    tf = new JTextField("[Player Name Here]", 20);
		    selector.add(tf);
		    confirm = new JButton ("Confirm");
		    confirm.addActionListener(this);
		    selector.add(confirm);
		    
		    //a button to let the selector know if all players are assigned before all charcaters.
		    done = new JButton ("Done");
		    done.addActionListener(this);
		    selector.add(done);
			
			//add components 
			
	        
	        // Set size
	        selector.setSize(400,150);
	        
	        // Set some layout
	        selector.setLayout(new FlowLayout());
	        
	        
	        setSize(400,150);
	        setVisible(true);
	        
	        // Like JFrame, JDialog isn't visible, you'll
	        // have to make it visible
	        // Remember to show JDialog after its parent is
	        // shown so that its parent is visible
	        selector.setVisible(true);
	    }
	
	/**
	 * This method is called when the confirm button is pushed - it checks that the inputs are 
	 * valid before creating a Character object with the selected options and passing it back 
	 * to the game.
	 */
	public void createPlayer(){
		//System.out.println("create exists");
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
		if (!available.contains(character) || character.equals("")){
			JOptionPane.showMessageDialog(this,
				    "Please choose a valid character and confirm again.");
			valid = false;
		}
		if (player.equals(new String())){
			JOptionPane.showMessageDialog(this,
					"Please choose a valid name and confirm again.");
			valid = false;
		}
		
		Player newChar = null;
		
		//valid inputs -> new character created.
		if (valid){
			newChar = new Player (player, character, null);
			System.out.println(newChar.toString());
			available.remove(character);
			chars.add(newChar);
		}
		
		if (newChar == null){
			System.out.println("null character");
			throw new RuntimeException();
		}
		
		//add the player to the list and link them up. once the players are done being assigned, 
		//send the completed playerlist back up the pipeline.
		if (available.size() == 0 || finished == true){
			int c = chars.indexOf(newChar);
			Player cur = chars.get(c);
			Player prev = chars.get(c-1);
			prev.setNext(cur);
			Player st = chars.get(0);
			Player end = chars.get(chars.indexOf(newChar));
			end.setNext(st);
			game.board.placePlayer(newChar);
			game.allocatePlayers(chars);
		}
		else if (!finished && game.start == null){
			game.start = newChar;
			game.board.placePlayer(newChar);
		}
		else{
			int c = chars.indexOf(newChar);
			Player cur = chars.get(c);
			Player prev = chars.get(c-1);
			prev.setNext(cur);
			game.board.placePlayer(newChar);
			//System.out.println(prev.toString() + "next :" + prev.next().toString());
		
		}
		this.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		if (src.equals(confirm)){
			createPlayer();
		}
		if (src.equals(done)){
			finished = true;
			createPlayer();
		}
	}

}
