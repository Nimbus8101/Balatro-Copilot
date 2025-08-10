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
    
    public static BlindType getRandomBlind() {
    	Random random = new Random();
        BlindType[] values = BlindType.values();
        return values[random.nextInt(values.length)];
    }
    
    public void incrementBlinds() {
    	for(BlindCard blind : blinds) {
    		blind.incrementState();
    	}
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
}

