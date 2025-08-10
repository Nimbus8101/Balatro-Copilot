package game.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;

import data.card.Card;
import data.deck.Deck;

public class HandPanel extends PlayArea implements CardSelectionListener{
	JLayeredPane layeredPane;
	private final int MAX_SELECTION = 5;
	int numSelected = 0;
	
	private final int CARD_Y = 200;
	private final int CARD_WIDTH = 150;
    private final int CARD_HEIGHT = 210;
    private final int OVERLAP = 50;
    
    //public Deck deck;
	
	public HandPanel() {
		setLayout(new BorderLayout());
		layeredPane = new JLayeredPane();	
		layeredPane.setPreferredSize(new Dimension(800, 300));
        //layeredPane.setBorder(BorderFactory.createTitledBorder("Your Hand:"));
        this.add(layeredPane, BorderLayout.CENTER);
	}
	
	//public void updateHand() {
		//rebuildLayeredPane();
	//}
	
	@Override
	public void setBorderTitle(String borderTitle) {
		layeredPane.setBorder(BorderFactory.createTitledBorder(borderTitle));
	}
	
	@Override
	public void rebuildLayeredPane() {
		layeredPane.removeAll();
		numSelected = 0;
		
        if(this.deck == null) return;
        
		int numCards = deck.drawnCards.size();
		int panelWidth = getWidth();
		// Compute maximum possible overlap so all cards fit
	    int maxCardSpread = panelWidth - CARD_WIDTH;
	    int overlap = (numCards > 1) ? CARD_WIDTH - (maxCardSpread / (numCards - 1)) : 0;
	    
		if(panelWidth == 0) return;
		
	    // Clamp overlap so it’s never negative or excessive
	    overlap = Math.max(0, Math.min(CARD_WIDTH - 30, overlap));

        for (int i = 0; i < numCards; i++) {
            CardLabel card = loadCard(deck.drawnCards.get(i).cardPathName(), i);

            // position cards so they overlap
            int x = i * (CARD_WIDTH - overlap);
            
            card.setBounds(x, CARD_Y, CARD_WIDTH, CARD_HEIGHT);
            card.setBorder(new LineBorder(Color.BLACK, 2));
            
            if(deck.drawnCards.get(i).isSelected) {
            	 card.liftCard();
            	 numSelected++;
            }
            

            layeredPane.add(card, Integer.valueOf(i));
        }
        
        layeredPane.revalidate();
        layeredPane.repaint();
	}
	
	public CardLabel loadCard(String cardPathName, int index) {
		String path = "/resources/Cards/" + cardPathName;
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image scaledImage = icon.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
        
        CardLabel card = new CardLabel(new ImageIcon(scaledImage), this);
        card.indexOfCard = index;
        
        return card;
	}
	
	@Override
	public void doLayout() {
	    super.doLayout();
	    rebuildLayeredPane();
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
}
