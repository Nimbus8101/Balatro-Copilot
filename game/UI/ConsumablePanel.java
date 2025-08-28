package game.UI;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConsumablePanel extends CardLayeredPane {
	public ConsumablePanel() {
		new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setBorder(BorderFactory.createTitledBorder("Consumables"));

        add(new JLabel("📋 To-Do"));
        add(new JLabel("🌞 Sun"));
        add(new JLabel("⚖️ Justice"));
	}
	
	public static GridBagConstraints getGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 0, 0);
        return gbc;
	}
}
