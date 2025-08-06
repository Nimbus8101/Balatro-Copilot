package game.UI;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.card.Card;
import data.deck.Deck;

public class HandPanel extends JPanel implements CardSelectionListener{
	Deck deck;
	private final int MAX_SELECTION = 5;
	int numSelected = 0;
	Card[] selectedCards = new Card[5];
	
	public HandPanel() {
		setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Your Hand"));
	}
	
	public void updateHand() {
		this.removeAll();
		
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(400, 10, 0, 10); // top, left, bottom, right padding
        gbc.anchor = GridBagConstraints.SOUTH;

        for (int i = 0; i < deck.drawnCards.size(); i++) {
        	String path = "/resources/Cards/" + deck.drawnCards.get(i).cardPathName();
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            CardLabel card = new CardLabel(new ImageIcon(scaledImage), gbc.insets, this);
            card.indexOfCard = i;
            gbc.gridx = i;
            add(card, (GridBagConstraints) gbc.clone());
        }
		this.revalidate();
	    this.repaint(); 
	}
	
	public static GridBagConstraints getGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
	}

	 @Override
	    public boolean onCardSelected(CardLabel card) {
	        if (numSelected < MAX_SELECTION) {
	            numSelected++;
	            deck.cards.get(card.indexOfCard).isSelected = true;
	            return true;
	        } else {
	            System.out.println("You can only select up to 5 cards.");
	            return false;
	        }
	    }

	    @Override
	    public void onCardDeselected(CardLabel card) {
	        deck.cards.get(card.indexOfCard).isSelected = false;
	        numSelected--;
	    }
}
