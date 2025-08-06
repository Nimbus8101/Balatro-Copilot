package game.UI;

import javax.swing.*;

import java.awt.*;

public class AnteSelect extends PlayArea {
	private AnteSelectListener listener;
	
    public AnteSelect(AnteSelectListener listener, int ante) {
    	this.listener = listener;
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        setBorder(BorderFactory.createTitledBorder("Choose Your Blind"));

        // Create each blind card as a JPanel
        JPanel smallBlindCard = createBlindCard("Small Blind", "Score at least 800", "$$$$", true, true);

        JPanel bigBlindCard = createBlindCard("Big Blind", "Score at least 1200", "$$$$", false, false);

        JPanel hookCard = createBlindCard("The Hook", "Discards 2 random cards per hand played\nScore at least 1600", "$$$$$", false, false);

        // Add cards to this panel
        add(smallBlindCard);
        add(bigBlindCard);
        add(hookCard);
    }

    private JPanel createBlindCard(String title, String description, String reward, boolean isActive, boolean canSkip) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(200, 250));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        card.setBackground(isActive ? new Color(200, 230, 255) : new Color(230, 230, 230));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = new JLabel("<html><div style='text-align: center;'>" + description + "</div></html>");
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel rewardLabel = new JLabel("Reward: " + reward);
        rewardLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        rewardLabel.setForeground(new Color(34, 139, 34));
        rewardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalStrut(10));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(descLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(rewardLabel);
        card.add(Box.createVerticalStrut(20));

        if (isActive) {
            JButton selectButton = new JButton("Select");
            selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            selectButton.addActionListener(e -> {
            	if(listener != null) {
            		 listener.onBlindSelected(title);
            	}
            });
            card.add(selectButton);

            if (canSkip) {
                JButton skipButton = new JButton("Skip Blind");
                skipButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                skipButton.addActionListener(e -> {
                	if(listener != null) {
                		 listener.onBlindSkipped();
                	}
                });
                card.add(Box.createVerticalStrut(5));
                card.add(skipButton);
            }
        } else {
            JLabel upcomingLabel = new JLabel("Upcoming");
            upcomingLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
            upcomingLabel.setForeground(Color.DARK_GRAY);
            upcomingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(Box.createVerticalStrut(10));
            card.add(upcomingLabel);
        }

        return card;
    }
    
    @Override
	public void doLayout() {
	    super.doLayout();
	}
}

