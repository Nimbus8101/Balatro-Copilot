package game.UI;

import javax.swing.*;

import game.BlindType;

import java.awt.*;
import java.util.Random;

public class AnteSelect extends PlayArea implements BlindCardListener{
	private AnteSelectListener listener;
	
	BlindCard[] blinds = new BlindCard[3];
	int blindSelected;
	
    public AnteSelect(AnteSelectListener listener, int baseChips, int blindsDone) {
    	this.listener = listener;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Ante Select - Choose Your Blind"));
        
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10); // spacing between cards
        gbc.gridy = 0;

        for (int i = 0; i < blinds.length; i++) {
            gbc.gridx = i;
            add(blinds[i], gbc);
        }
    }
    
    public static BlindType getRandomBlind() {
    	Random random = new Random();
        BlindType[] values = BlindType.values();
        return values[random.nextInt(values.length)];
    }
    
    public void incrementBlinds() {
    	blinds[blindSelected].incrementState();
    	
    	if(blindSelected < 2) {
    		blinds[blindSelected + 1].incrementState();
    	}
    	
    	blindSelected++;
    }
    
    public void blindSuccess() {
    	blinds[blindSelected].state = "completed";
	}
    
    public String getCurrentBlind() {
    	return blinds[blindSelected].getName();
    }
    
    @Override
    public void rebuildLayeredPane() {
    	
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

