package data.deck;

import java.util.Random;
import java.util.Vector;

import data.card.Card;

public interface DeckUtils {
	public static Vector<Card> shuffleCards(Vector<Card> cards){
		Vector<Card> shuffledDeck = new Vector<Card>(0);
		
		Random rand = new Random();
		int randInt;
		
		while(cards.size() > 0) {
			randInt = rand.nextInt(cards.size());
			shuffledDeck.add(cards.remove(randInt));
		}
		
        return shuffledDeck;
	}
	
	public static Vector<Card> draw(int numDraw, Deck deck){
		Vector<Card> hand = new Vector<Card>(0);
		
		for(int i = 0; i < numDraw && deck.hasNext(); i++) {
			hand.add(deck.drawNext());
		}
		return hand;
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
}
