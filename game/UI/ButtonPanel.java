package game.UI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel{
	ButtonPanelListener listener;
	
	public ButtonPanel(ButtonPanelListener listener) {
		this.listener = listener;
		
		this.setLayout(new FlowLayout());
        setBorder(BorderFactory.createTitledBorder("Buttons"));
        setPreferredSize(new Dimension(200, 0)); // You can adjust width
        
        JButton playHand = new JButton("Play Hand");
        playHand.addActionListener(e -> listener.playHandPressed());
        
        JButton discardHand = new JButton("Discard Hand");
        discardHand.addActionListener(e -> listener.discardHandPressed());
        
        JButton sortByRank = new JButton("Sort By Rank");
        sortByRank.addActionListener(e -> listener.sortByRankPressed());
        
        JButton sortBySuit = new JButton("Sort By Suit");
        sortBySuit.addActionListener(e -> listener.sortBySuitPressed());
        
        add(playHand);
        add(discardHand);
        add(sortByRank);
        add(sortBySuit);
	}
	
	public static GridBagConstraints getGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1; 
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 0.25;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        return gbc;
	}
}
