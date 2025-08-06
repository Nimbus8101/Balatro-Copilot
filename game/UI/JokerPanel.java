package game.UI;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JokerPanel extends JPanel{
	public JokerPanel() {
		new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setBorder(BorderFactory.createTitledBorder("Jokers"));

        add(new JLabel("🃏 Joker 1"));
        add(new JLabel("🃏 Joker 2"));
        add(new JLabel("🃏 Joker 3"));

        // GridBag constraints for Joker panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 0, 10);  // spacing between joker and consumable
	}
	
	public static GridBagConstraints getGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 0, 10);  // spacing between joker and consumable
        return gbc;
	}
}
