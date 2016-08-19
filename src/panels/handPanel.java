package panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class handPanel extends JPanel{

	public handPanel(){
		Dimension size = new Dimension(185, 160);
		setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.black));
	}
}
