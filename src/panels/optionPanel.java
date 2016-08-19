package panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class optionPanel extends JPanel{

	public optionPanel(){
		Dimension size = new Dimension(165, 160);
		setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.black));
	}
}
