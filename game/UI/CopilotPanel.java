package game.UI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class CopilotPanel extends JPanel{
	public CopilotPanel() {
		new JPanel();
        setBorder(BorderFactory.createTitledBorder("Reserved"));
        setPreferredSize(new Dimension(200, 0)); // You can adjust width
	}
	
	public static GridBagConstraints getGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridheight = 3; // Spans all vertical space
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        return gbc;
	}
}
