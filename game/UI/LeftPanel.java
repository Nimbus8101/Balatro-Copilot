package game.UI;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LeftPanel extends JPanel{
	public LeftPanel() {
		new JPanel();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Game Stats"));

        add(new JLabel("Big Blind: 1200"));
        add(new JLabel("Score: 0"));
        add(new JLabel("Hands: 4"));
        add(new JLabel("Discards: 6"));
        add(new JLabel("Money: $13"));
        add(Box.createVerticalStrut(10));
        add(new JButton("Run Info"));
        add(new JButton("Options"));
        
	}
	
	public static GridBagConstraints getGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        return gbc;
	}
}
