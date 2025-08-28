package game.UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class BlindCard extends JPanel{
	BlindCardListener listener;
	boolean isSelected;
	boolean canSkip;
	
	String state = "None";
	
	JLabel titleLabel;
	JLabel descLabel;
	JLabel rewardLabel;
	
	JButton selectButton;
	JButton skipButton;
	
	JLabel stateLabel;
	
	public BlindCard(BlindCardListener listener, String title, String description, String reward, String state, boolean canSkip) {
		this.listener = listener;
		this.canSkip = canSkip;
		this.state = state;
		
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 250));
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        
        if(state.equals("selected")) {
        	setBackground(new Color(200, 230, 255));
        }else {
        	setBackground(new Color(230, 230, 230));
        }

        titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        descLabel = new JLabel("<html><div style='text-align: center;'>" + description + "</div></html>", SwingConstants.CENTER);
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        rewardLabel = new JLabel("Reward: " + reward);
        rewardLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        rewardLabel.setForeground(new Color(34, 139, 34));
        rewardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        stateLabel = new JLabel();
        stateLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        stateLabel.setForeground(Color.DARK_GRAY);
        stateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        add(Box.createVerticalStrut(10));

        add(Box.createVerticalStrut(10));
        add(titleLabel);
        add(Box.createVerticalStrut(10));
        add(descLabel);
        add(Box.createVerticalStrut(10));
        add(rewardLabel);
        add(Box.createVerticalStrut(20));
        
        if(state.equals("selected")) {
        	initializeSelected();
        }else if(state.equals("upcoming")) {
        	setBackground(new Color(230, 230, 230));
    		stateLabel.setText("Upcoming");
            add(stateLabel);
        }
	}
	
	public void incrementState() {
		if(state.equals("upcoming")) {
			state = "selected";
			remove(stateLabel);
			
			initializeSelected();
		}else if(state.equals("selected")) {
			state = "skipped";
			remove(selectButton);
			remove(skipButton);
			
			setBackground(new Color(230, 230, 230));	
			stateLabel.setText("Skipped");
	        add(stateLabel);
		}else if(state.equals("completed")) {
			remove(selectButton);
			remove(skipButton);
			
			setBackground(new Color(230, 230, 230));	
			stateLabel.setText("Completed");
	        add(stateLabel);
		}
	}
	
	public void initializeSelected() {
		setBackground(new Color(200, 230, 255));
		selectButton = new JButton("Select");
        selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectButton.addActionListener(e -> {
        	if(listener != null) {
        		 listener.blindSelected();
        	}
        });
        add(selectButton);

        if (canSkip) {
            skipButton = new JButton("Skip Blind");
            skipButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            skipButton.addActionListener(e -> {
            	if(listener != null) {
            		 listener.blindSkipped();
            	}
            });
            add(Box.createVerticalStrut(5));
            add(skipButton);
        }
        
        System.out.println(this.titleLabel.getText() + " - initializing selected");
	}
}
