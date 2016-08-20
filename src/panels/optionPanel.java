package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class optionPanel extends JPanel implements ActionListener{
	
	private JButton accuse;
	private JButton suggest;
	private JButton endTurn;

	public optionPanel(){
		Dimension size = new Dimension(100, 160);
		setPreferredSize(size);
		//setBorder(BorderFactory.createLineBorder(Color.black));
		
		Dimension button = new Dimension(100, 50);
		accuse = new JButton("Accuse");
		suggest = new JButton("Suggest");
		endTurn = new JButton("End Turn");
		accuse.setPreferredSize(button);
		accuse.addActionListener(this);
		add(accuse,BorderLayout.NORTH);
		suggest.setPreferredSize(button);
		suggest.addActionListener(this);
		add(suggest,BorderLayout.NORTH);
		endTurn.setPreferredSize(button);
		endTurn.addActionListener(this);
		add(endTurn,BorderLayout.NORTH);
		
	}
	@Override
	public void actionPerformed(ActionEvent evt) {
	    Object src = evt.getSource();
	    if (src == accuse) {
	  //do the accuse thing
	    } 
	    else if (src == suggest) {
	  	  //do the suggest thing
	  	    }
	    else if (src == endTurn) {
	  	  //do the end turn thing
	  	    }
	}
	
}
