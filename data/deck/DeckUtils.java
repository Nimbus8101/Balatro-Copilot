package data.deck;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import data.card.Card;

public interface DeckUtils {
	public static int SORT_RANK = 0;
	public static int SORT_SUIT = 1;
	
	/**
	 * Draws up to numDraw cards from deck and adds those to currHand, if there are cards in the deck to draw
	 * @param currHand
	 * @param numDraw
	 * @param deck
	 * @return
	 */
	public static Vector<Card> draw(Vector<Card> currHand, int numDraw, Deck deck){
		if(currHand == null) {
			System.out.println("Given a null currHand in DeckUtils.draw");
			currHand = new Vector<Card>(0);
		}
		
		while(currHand.size() < numDraw && deck.hasNext()) {
			currHand.add(deck.drawNext());
		}
		
		return currHand;
	}
	
	/**
	 * Pulls cards from the given vector and returns the pulled cards.
	 * NOTE This modifies the original array, and is important for the converIndexCombinationsToPlayedHands() function, 
	 * 	as the modified array is stored in the heldCards variable of playedHand
	 * @param cards
	 * @param indexes
	 * @return
	 */
	public static Vector<Card> pullCardsFromVector(Vector<Card> cards, Vector<Integer> indexes){
		//System.out.println("-- " + cards.size() + " - " + indexes.size());
		Vector<Card> pulledCards = new Vector<Card>(0);
			for(int i = indexes.size() - 1; i >= 0; i--) {
				//System.out.println((int) indexes.get(i));
				pulledCards.add(cards.remove((int) indexes.get(i)));
			}
		return pulledCards;
	}
	
	public static Card[] convertCardVectorToArray(Vector<Card> cards) {
		Card[] newCards = new Card[cards.size()];
		for(int i = 0; i < cards.size(); i++) {
			newCards[i] = cards.get(i);
		}
		return newCards;
	}
	
	public static Vector<Card> copyCardVector(Vector<Card> cards){
		Vector<Card> newVector = new Vector<Card>(0);
		for(int i = 0; i < cards.size(); i++) {
			newVector.add(cards.get(i));
		}
		return newVector;
	}
	
	public static String printCardVector(Vector<Card> cards, String buffer) {
		String result = "";
		for(int i = 0; i < cards.size(); i++) {
			result += cards.get(i).printValueAndSuit() + " ";
		}
		return result;
	}
	
	public static void sortCardVector(Vector<Card> cards, int typeOfSort) {	
		if(typeOfSort == SORT_RANK) {
			int bottomIndex = 0;
			while(bottomIndex < cards.size() - 1) {		
				for(int i = bottomIndex + 1; i < cards.size(); i++) {
					
					// If the first card is "smaller" than the second, swap the two
					if(Card.compare(cards.get(bottomIndex), cards.get(i)) < 0) {
						Collections.swap(cards, bottomIndex, i);
					}
				}
				bottomIndex++;
			}
		}
		
		if(typeOfSort == SORT_SUIT) {
			// FIXME: Algorithm doesn't work as intended
			
			// Step 1: Move all spades to front, then sort those
			int bottomRange = 0;
			int bottomIndex = 0;
			char currSuit = Card.SPADES;
			
			while(true) {
				// Group all the cards of the current suit together
				for(int i = bottomRange; i < cards.size(); i++) {
					if(cards.get(i).getSuit() == currSuit) {
						Collections.swap(cards, bottomIndex, i);
						bottomIndex++;
					}
				}
				
				// Sort them by value
				while(bottomRange < bottomIndex) {
					for(int i = bottomRange; i < bottomIndex; i++) {
						if(cards.get(bottomRange).getValue() < cards.get(i).getValue()) {
							Collections.swap(cards, bottomRange, i);
							break;
						}
					}
					bottomRange++;
				}
				
				// Change the currSuit for the next loop
				if(currSuit == Card.SPADES) currSuit = Card.HEARTS;
				else if(currSuit == Card.HEARTS) currSuit = Card.CLUBS;
				else if(currSuit == Card.CLUBS) currSuit = Card.DIAMONDS;
				else break;
			}
		}
	}
}
