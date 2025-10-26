package data.deck;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import data.card.Card;
import data.card.PlayingCard;


/**
 * Class for performing functions related to Decks (Vector<PlayingCard>)
 * 
 * @author Elijah Reyna
 */
public interface DeckUtils {
	// ========== Types of Sorts ========== //
	public static int SORT_RANK = 0;
	public static int SORT_SUIT = 1;
	
	
	/**
	 * Draws up to numDraw cards from deck and adds those to currHand, if there are cards in the deck to draw
	 * @param currHand
	 * @param numDraw
	 * @param deck
	 * @return
	 */
	public static Vector<PlayingCard> draw(Vector<PlayingCard> currHand, int numDraw, Deck deck){
		if(currHand == null) {
			System.out.println("Given a null currHand in DeckUtils.draw");
			currHand = new Vector<PlayingCard>(0);
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
	public static Vector<PlayingCard> pullCardsFromVector(Vector<PlayingCard> cards, Vector<Integer> indexes){
		//System.out.println("-- " + cards.size() + " - " + indexes.size());
		Vector<PlayingCard> pulledPlayingCards = new Vector<PlayingCard>(0);
			for(int i = indexes.size() - 1; i >= 0; i--) {
				//System.out.println((int) indexes.get(i));
				pulledPlayingCards.add(cards.remove((int) indexes.get(i)));
			}
		return pulledPlayingCards;
	}
	
	
	/**
	 * Converts cards from a vector to an array
	 * @param cards Vector<PlayingCard> Card vector to convert
	 * @return Card[] Array of Cards
	 */
	public static PlayingCard[] convertCardVectorToArray(Vector<PlayingCard> cards) {
		PlayingCard[] newCards = new PlayingCard[cards.size()];
		for(int i = 0; i < cards.size(); i++) {
			newCards[i] = cards.get(i);
		}
		return newCards;
	}
	
	
	/**
	 * Copies the given Vector
	 * @param cards Vector<PlayingCard> Cards to copy
	 * @return Vector<PlayingCard> copied vector
	 */
	public static Vector<PlayingCard> copyCardVector(Vector<PlayingCard> cards){
		Vector<PlayingCard> newVector = new Vector<PlayingCard>(0);
		for(int i = 0; i < cards.size(); i++) {
			newVector.add(cards.get(i));
		}
		return newVector;
	}
	
	
	/**
	 * Generates a String representation of given cards
	 * @param cards List<PlayingCard> Cards to represent in a String
	 * @param buffer String offset to apply to each line
	 * @return String to be printed
	 */
	public static String printCardVector(List<PlayingCard> cards, String buffer) {
		String result = "";
		for(int i = 0; i < cards.size(); i++) {
			result += cards.get(i).printValueAndSuit() + " ";
		}
		return result;
	}

	
	
	/**
	 * Sorts the cards in place
	 * @param cards Vector<PlayingCard> Cards to sort
	 * @param typeOfSort int Type of sort to perform
	 */
	public static void sortCardVector(Vector<PlayingCard> cards, int typeOfSort) {	
		if(typeOfSort == SORT_RANK) {
			int bottomIndex = 0;
			while(bottomIndex < cards.size() - 1) {		
				for(int i = bottomIndex + 1; i < cards.size(); i++) {
					
					// If the first card is "smaller" than the second, swap the two
					if(PlayingCard.compare(cards.get(bottomIndex), cards.get(i)) < 0) {
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
			char currSuit = PlayingCard.SPADES;
			
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
				if(currSuit == PlayingCard.SPADES) currSuit = PlayingCard.HEARTS;
				else if(currSuit == PlayingCard.HEARTS) currSuit = PlayingCard.CLUBS;
				else if(currSuit == PlayingCard.CLUBS) currSuit = PlayingCard.DIAMONDS;
				else break;
			}
		}
	}
}
