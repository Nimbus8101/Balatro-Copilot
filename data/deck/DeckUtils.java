package data.deck;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import data.card.Card;

public interface DeckUtils {
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
		int RANK = 0;
		int SUIT = 1;
		
		if(typeOfSort == RANK) {
			int bottomRange = 0;
			int rank = cards.get(0).getValue();
			for(int i = 1; i < cards.size(); i++) {
				if(cards.get(i).getValue() == rank) {
					
				}
				else if(cards.get(i).getValue() > rank) {
					
				}
			}
		}
	}
}
