package game.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import data.card.Card;
import data.card.Joker;

public class JokerPanel extends CardLayeredPane {
	public static final int JOKER_Y_OFFSET = 50;
	
	public Vector<Joker> jokers;
	
	public JokerPanel() {
		super("Jokers", "/resources/Jokers/");
	}
	
	public JokerPanel(Vector<Card> jokers) {
		super("jokers", "/resources/Jokers/");
		cardY = JOKER_Y_OFFSET;
		setCardVector(jokers);
        rebuildLayeredPane();
	}
	
	@Override
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
	    int y = (panelHeight - CARD_HEIGHT) / 2;  // Vertically Centered

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
	
	@Override
	public boolean onCardSelected(CardLabel card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCardDeselected(CardLabel card) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public Dimension getMinimumSize() {
	    return new Dimension(600, 230); // Will prevent it from shrinking too small
	}
	
	@Override
	public Dimension getPreferredSize() {
	    return new Dimension(getParent() != null ? getParent().getWidth() : 600, 230);
	}
	
	public static GridBagConstraints getGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.8;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 0, 10);  // spacing between joker and consumable
        return gbc;
	}
}
