package game.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import data.card.Card;
import data.deck.Deck;

public class HandPanel extends CardLayeredPane implements CardSelectionListener {
	private final int MAX_SELECTION = 5;
	int numSelected = 0;
	
	private final int PLAYINGCARD_Y_OFFSET = 200;
    
    public Deck deck;
	
	public HandPanel() {
		super("Your Hand", "/resources/Cards/");
	}
	
	public HandPanel(Vector<Card> cards) {
		super("Your Hand", "/resources/Cards/");
		cardY = PLAYINGCARD_Y_OFFSET;
		setCardVector(cards);
        rebuildLayeredPane();
	}
	
	@Override
	public void rebuildLayeredPane() {
		layeredPane.removeAll();

	    if (this.deck == null || deck.drawnCards.size() == 0) {
	        return;
	    }

	    int numCards = deck.drawnCards.size();
	    int panelWidth = getWidth();
	    int panelHeight = getHeight();

	    if (panelWidth == 0 || panelHeight == 0) return;

	    // Calculate spacing to fit cards with some overlap
	    int spacing = 0;
	    if (numCards > 1) {
	        int maxSpread = panelWidth - CARD_WIDTH;
	        spacing = Math.min(CARD_WIDTH - 30, maxSpread / (numCards - 1));
	    }

	    // Calculate total width of all cards + spacing
	    int totalWidth = (numCards - 1) * spacing + CARD_WIDTH;

	    // Center the row of cards
	    int startX = (panelWidth - totalWidth) / 2;

	    // Place cards near the bottom of the panel
	    int y = panelHeight - CARD_HEIGHT - 20;  // 20 px padding from bottom

	    for (int i = 0; i < numCards; i++) {
	        CardLabel card = loadCard(deck.drawnCards.get(i).cardPathName(), i);
	        int x = startX + i * spacing;

	        card.setBounds(x, y, CARD_WIDTH, CARD_HEIGHT);
	        card.setBorder(new LineBorder(Color.BLACK, 2));

	        if (deck.drawnCards.get(i).isSelected) {
	            card.liftCard();
	        }

	        layeredPane.add(card, Integer.valueOf(i));
	    }

	    layeredPane.revalidate();
	    layeredPane.repaint();
	}

	@Override
    public boolean onCardSelected(CardLabel card) {
        if (numSelected < MAX_SELECTION) {
            numSelected++;
            deck.drawnCards.get(card.indexOfCard).isSelected = true;
            System.out.println(card.indexOfCard + " " + deck.drawnCards.get(card.indexOfCard).isSelected + " / " + numSelected);
            return true;
        } else {
            System.out.println("You can only select up to 5 cards.");
            return false;
        }
    }

    @Override
    public void onCardDeselected(CardLabel card) {
        deck.drawnCards.get(card.indexOfCard).isSelected = false;
        numSelected--;
        System.out.println(card.indexOfCard + " " + deck.drawnCards.get(card.indexOfCard).isSelected + " / " + numSelected);
    }
    
    @Override
	public Dimension getMinimumSize() {
	    return new Dimension(600, 250); // Will prevent it from shrinking too small
	}
	
	@Override
	public Dimension getPreferredSize() {
	    return new Dimension(getParent() != null ? getParent().getWidth() : 800, 300);
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
