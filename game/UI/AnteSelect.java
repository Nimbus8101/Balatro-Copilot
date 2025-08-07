package game.UI;

import javax.swing.*;

import game.BlindType;

import java.awt.*;
import java.util.Random;

public class AnteSelect extends PlayArea implements BlindCardListener{
	private AnteSelectListener listener;
	
	BlindCard[] blinds = new BlindCard[3];
	int blindSelected;
	
    public AnteSelect(AnteSelectListener listener, int baseChips) {
    	this.listener = listener;
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        setBorder(BorderFactory.createTitledBorder("Choose Your Blind"));
        
        blindSelected = 0;
        
        blinds[0] = new BlindCard(this, "Small Blind", "Score at least " + baseChips, "$$$$", "selected", true);
        
        blinds[1] = new BlindCard(this, "Big Blind", "Score at least " + (int) (baseChips * 1.5), "$$$$", "upcoming", true);
        
        BlindType bossBlind = getRandomBlind();
        blinds[2] = new BlindCard(this, bossBlind.getBlindName(), bossBlind.getDescription(), "$".repeat(bossBlind.getCost()), "upcoming", false);

        // Create each blind card as a JPanel
        //JPanel smallBlindCard = createBlindCard("Small Blind", "Score at least " + baseChips, "$$$$", true, true);

        //JPanel bigBlindCard = createBlindCard("Big Blind", "Score at least " + (int) (baseChips * 1.5), "$$$$", false, false);

        //JPanel bossCard = createBlindCard("The Hook", "Discards 2 random cards per hand played\nScore at least 1600", "$$$$$", false, false);

        // Add cards to this panel
        add(blinds[0]);
        add(blinds[1]);
        add(blinds[2]);
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
                		card.setBackground(new Color(230, 230, 230));
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
    
    public static BlindType getRandomBlind() {
    	Random random = new Random();
        BlindType[] values = BlindType.values();
        return values[random.nextInt(values.length)];
    }
    
    @Override
	public void doLayout() {
	    super.doLayout();
	}

	@Override
	public void blindSelected() {
		listener.onBlindSelected(blinds[blindSelected].titleLabel.getText());
	}

	@Override
	public void blindSkipped() {
		blindSelected++;
		if(blindSelected >= 3) {
			blindSelected = 0;
		}else {
			blinds[blindSelected].incrementState();
			blinds[blindSelected - 1].incrementState();
		}
		listener.onBlindSkipped();
	}
}

