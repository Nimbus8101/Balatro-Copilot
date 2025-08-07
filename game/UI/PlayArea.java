package game.UI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import data.deck.Deck;

public class PlayArea extends JPanel{
	public Deck deck;

	public PlayArea() {
		 super();
	}
	
	public void setBorderTitle(String borderTitle) {
		
	}
	
	public void rebuildLayeredPane() {}
	
	public static GridBagConstraints getGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
	}
}
