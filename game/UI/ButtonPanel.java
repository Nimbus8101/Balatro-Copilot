package game.UI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel{
	ButtonPanelListener listener;
	
	public ButtonPanel(ButtonPanelListener listener) {
		this.listener = listener;
		
		this.setLayout(new FlowLayout());
        setBorder(BorderFactory.createTitledBorder("Reserved"));
        setPreferredSize(new Dimension(200, 0)); // You can adjust width
        
        JButton playHand = new JButton("Play Hand");
        playHand.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	listener.playHandPressed();
            }
        });
        
        JButton discardHand = new JButton("Discard Hand");
        playHand.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	listener.discardHandPressed();
            }
        });
        add(playHand);
        add(discardHand);
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
