package data.deck;

import java.util.Collections;
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
	
	public Deck(Vector<PlayingCard> cards) {
		this.cards = cards;
		drawnCards = new Vector<PlayingCard>(0);
		discardedCards = new Vector<PlayingCard>(0);
	}
	
	
	/**
	 * Resets the deck to empty
	 */
	public void resetDeck() {
		cards.addAll(drawnCards);
		cards.addAll(discardedCards);
		drawnCards = new Vector<PlayingCard>(0);
		discardedCards = new Vector<PlayingCard>(0);
	}
	
	
	/**
	 * Shuffles the cards
	 */
	public void shuffle() {
		Collections.shuffle(cards);
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
		return cards.remove(0);
	}
	
	
	/**
	 * Draws a specificed number of cards
	 * NOTE: Does not return anything -- Moves the cards to drawnCards
	 * @param numDraw Number of cards to draw
	 */
	public void draw(int numDraw) {
		for(int i = 0; i < numDraw; i++) drawnCards.add(cards.remove(0));
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

	
	/**
	 * Gets the cards vector
	 * @return
	 */
	public Vector<PlayingCard> cards() {
		return cards;
	}
}
