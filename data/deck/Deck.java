package data.deck;

import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import data.card.Card;
import data.card.PlayingCard;


/**
 * Class to hold information regarding a collection of PlayingCard objects
 * 
 * 
 * @author Elijah Reyna
 */
public class Deck implements DeckUtils {
	// ========== Deck Information ========== //
	public Vector<PlayingCard> cards;
	public Vector<PlayingCard> drawnCards;
	public Vector<PlayingCard> discardedCards;
	
	public static final int DRAW_MAP = 0;
	public static final int HAND_MAP = 1;
	public static final int DISCARD_MAP = 2;
	private BitSet drawBitmap;
	private BitSet handBitmap;
	private BitSet discardBitmap;
	private Map<PlayingCard, Integer> localIndices = new HashMap<>();
	private Map<Integer, PlayingCard> indexToCard = new HashMap<>();
	
	public Deck(Vector<PlayingCard> cards) {
		this.cards = cards;
		drawnCards = new Vector<PlayingCard>(0);
		discardedCards = new Vector<PlayingCard>(0);
		
		rebuildBitmap();
	}
	
	 public void rebuildBitmap() {
        localIndices = new HashMap<>();
        drawBitmap = new BitSet(cards.size());
        handBitmap = new BitSet(cards.size());
        discardBitmap = new BitSet(cards.size());
        
        for (int i = 0; i < cards.size(); i++) {
            localIndices.put(cards.get(i), i);
            indexToCard.put(i, cards.get(i));
            drawBitmap.set(i);
        }
        
        
    }
	 
	 public PlayingCard getCardByIndex(int index) {
	    return indexToCard.get(index);
	}
	 
	 public BitSet getBitmap(int bitMap) {
        return (BitSet) drawBitmap.clone();
    }
	
	/**
	 * Resets the deck to empty
	 */
	public void resetDeck() {
		cards.addAll(drawnCards);
		cards.addAll(discardedCards);
		drawnCards.clear();
		discardedCards.clear();
	    rebuildBitmap();
	}
	
	
	/**
	 * Shuffles the cards
	 */
	public void shuffle() {
		Collections.shuffle(cards);
		rebuildBitmap();
	}
	
	
	/**
	 * Checks if the cards vector has more than one card
	 * @return
	 */
	public boolean hasNext() {
		if(cards.size() > 0) {
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * Method for drawing a single card
	 * @return PlayingCard drawn card
	 */
	public PlayingCard drawNext() {
		Integer idx = localIndices.get(cards.get(0));
        if (idx != null) {
        	drawBitmap.clear(idx);
        	handBitmap.set(idx);
        }
        
		return cards.remove(0);
	}
	
	
	/**
	 * Draws a specificed number of cards
	 * NOTE: Does not return anything -- Moves the cards to drawnCards
	 * @param numDraw Number of cards to draw
	 */
	public void draw(int numDraw) {
		for(int i = 0; i < numDraw; i++) {
			if(!cards.isEmpty()) {
				Integer idx = localIndices.get(cards.get(0));
				if (idx != null) {
		        	drawBitmap.clear(idx);
		        	handBitmap.set(idx);
		        }
		        drawnCards.add(cards.remove(0));
			}
		} 
	}
	
	
	/**
	 * Draws cards until a specified amount has been reached
	 * @param max Maximum number of cards in hand
	 */
	public void drawTo(int max) {
		while(drawnCards.size() < max) {
			drawnCards.add(drawNext());
		}
	}
	
	
	public void sortCards(String sortType) {
		
	}
	
	/**
	 * Generates a string representation of the deck
	 * @param buffer String offset
	 * @return String representing the cards in the deck
	 */
	public String printDeck(String buffer) {
		return DeckUtils.printCardVector(cards, buffer + "   ");
	}

	
	/**
	 * Returns the size of the deck
	 * @return
	 */
	public int size() {
		return cards.size();
	}
	
	/**
	 * Returns the number of cards in the original deck
	 * @return
	 */
	public int totalCards() {
		return cards.size() + drawnCards.size() + discardedCards.size();
	}
	
	
	public void removeCard(PlayingCard c) {
        Integer index = localIndices.get(c);
        if (index != null) drawBitmap.clear(index);
    }

	
	/**
	 * Gets the cards vector
	 * @return
	 */
	public Vector<PlayingCard> cards() {
		return cards;
	}
}
