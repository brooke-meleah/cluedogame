package panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class textPanel extends JPanel{

	public textPanel(){
		Dimension size = new Dimension(250, 160);
		setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.yellow));
	}
}