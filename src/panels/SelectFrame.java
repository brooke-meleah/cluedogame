
package panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Arrays;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;


import game.CluedoGame;


@SuppressWarnings("serial")
public class SelectFrame extends JFrame implements ActionListener{

	private CluedoGame game;

	private JButton confirm;
	public boolean accuse;
	private JDialog selector;
	
	//character related fields
	private String[] charList = {"Miss Scarlett", "Professor Plum", "Mrs. Peacock",
			"Reverend Green", "Colonel Mustard", "Mrs. White"};
	JComboBox<String> pickChar;
	JLabel labelChar;
	
	//weapon related fields
	private String[] weapList = {"Revolver", "Lead Pipe", "Rope", "Knife", "Wrench", "Candlestick"};
	JComboBox<String> pickWeap;
	JLabel labelWeap;
	
	//location related fields
	private String[] locList = {"Ballroom", "Hall", "Billiard Room", "Library", "Study",
			"Lounge", "Conservatory", "Dining Room", "Kitchen"};
	JComboBox<String> pickLoc;
	JLabel labelLoc;
	
	//the final selection
	public List<String> selection;

	public SelectFrame(JFrame parent, CluedoGame g, boolean accuse){
		game = g;
		this.accuse = accuse;
		setVisible(true);
	}

	public void createSelection(){
		System.out.println("yolo");
		createAndShowGUI();
	}

	private void createAndShowGUI()
	{
		if (accuse)
			setTitle("Create Accusation.");
		else
			setTitle("Create Suggestion.");

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());

		// A dialog with current frame as parent
		// a given title, and modal
		selector = new JDialog(this, "CharacterSelect", true);

		// Set size
		selector.setSize(400,150);

		// Set some layout
		selector.setLayout(new FlowLayout());

		//add components
		JComboBox<String> pickChar = new JComboBox<String>(charList);
		pickChar.setSelectedIndex(0);
		pickChar.addActionListener(this);

		labelChar = new JLabel("Select Character");

		JComboBox<String> pickWeap = new JComboBox<String>(weapList);
		pickWeap.setSelectedIndex(0);
		pickWeap.addActionListener(this);

		labelWeap = new JLabel("Select Weapon");

		JComboBox<String> pickLoc = new JComboBox<String>(locList);
		pickLoc.setSelectedIndex(0);
		pickLoc.addActionListener(this);

		labelLoc = new JLabel("Select Location");

		confirm = new JButton ("Confirm");
		confirm.addActionListener(this);
		
		//add components
		selector.add(labelChar);
		selector.add(pickChar);
		selector.add(labelWeap);
		selector.add(pickWeap);
		selector.add(labelLoc);
		selector.add(pickLoc);
		selector.add(confirm);

		setSize(400,150);
		setVisible(true);

		// Like JFrame, JDialog isn't visible til you make it
		selector.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		if (src.equals(pickChar)){
			String c = (String)pickChar.getSelectedItem();
			System.out.println(c);
			labelChar.setText("Selected: " + c);
		}
		if (src.equals(pickWeap)){
			String w = (String)pickChar.getSelectedItem();
			System.out.println(w);
			labelWeap.setText("Selected: " + w);
		}
		if (src.equals(pickLoc)){
			String l = (String)pickChar.getSelectedItem();
			System.out.println(l);
			labelLoc.setText("Selected: " + l);
		}
		if (src.equals(confirm)){
			selection = Arrays.asList((String)pickChar.getSelectedItem(), (String)pickChar.getSelectedItem(),
					(String)pickChar.getSelectedItem());
			System.out.println("Selection: " + selection.toString());
		}
	}

}


