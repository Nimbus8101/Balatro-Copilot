package game.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import data.card.Card;

public class CardLayeredPane extends JPanel implements CardSelectionListener{
	JLayeredPane layeredPane;
	
	protected final int CARD_WIDTH = 150;
    protected final int CARD_HEIGHT = 210;
    protected final int OVERLAP = 50;
    
    String resourcePath;
    String borderName = "";
    int cardY = 0;
    
    List<Card> cards;
    
    public CardLayeredPane() {
    	// Sets up the Layered Pane
    	layeredPane = new JLayeredPane();
    	
    	// Sets up the Panel
    	setLayout(new BorderLayout());
    	setPreferredSize(new Dimension(800, 300));
        this.add(layeredPane, BorderLayout.CENTER);
        setBorder(BorderFactory.createTitledBorder(borderName));

        if (this.cards == null || cards.size() == 0) {
            add(new JLabel("Empty"));
            return;
        }
    }
    
    public CardLayeredPane(String borderName, String resourcePath) {
    	this.resourcePath = resourcePath;
    	this.borderName = borderName;
    	
    	// Sets up the Layered Pane
    	layeredPane = new JLayeredPane();
    	
    	// Sets up the Panel
    	setLayout(new BorderLayout());
        add(layeredPane, BorderLayout.CENTER);
        setBorder(BorderFactory.createTitledBorder(borderName));
    }
    
    public void setBorderTitle(String borderTitle) {
		setBorder(BorderFactory.createTitledBorder(borderTitle));
	}
    
    public void setCardVector(List<Card> cards) {
    	this.cards = cards;
    }
	
	public void rebuildLayeredPane() {
		layeredPane.removeAll();

	    if (cards == null || cards.size() == 0) {
	        return;
	    }

	    int numCards = cards.size();
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
	        CardLabel card = loadCard(cards.get(i).cardPathName(), i);
	        int x = startX + i * spacing;

	        card.setBounds(x, y, CARD_WIDTH, CARD_HEIGHT);
	        card.setBorder(new LineBorder(Color.BLACK, 2));

	        if (cards.get(i).isSelected) {
	            card.liftCard();
	        }

	        layeredPane.add(card, Integer.valueOf(i));
	    }

	    layeredPane.revalidate();
	    layeredPane.repaint();
	}
	
	public CardLabel loadCard(String cardPathName, int index) {
		String path = resourcePath + cardPathName;
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image scaledImage = icon.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
        
        CardLabel card = new CardLabel(new ImageIcon(scaledImage), this);
        card.indexOfCard = index;
        
        return card;
	}
	
	@Override
	public void doLayout() {
	    super.doLayout();
	    layeredPane.setBounds(0, 0, getWidth(), getHeight());
	    rebuildLayeredPane();
	}

	@Override
	public boolean onCardSelected(CardLabel card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCardDeselected(CardLabel card) {
		// TODO Auto-generated method stub
		
	}
}
