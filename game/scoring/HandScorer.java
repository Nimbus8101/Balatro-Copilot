package game.scoring;

import java.util.Vector;

import data.card.Card;
import data.player.DefaultPlayer;
import data.pokerHand.PokerHand;
import data.pokerHand.PokerHandIdentifier;
import data.pokerHand.PokerHandTable;

public interface HandScorer extends ValueCountUtils{
	
	/**
	 * Function to score a hand of 5
	 * @param cards Cards that were played
	 * @param pokerHandTable
	 * @return
	 */
	public static PlayedHand scorePlayedHand(Vector<Card> cards, PokerHandTable pokerHandTable) {
		PlayedHand playedHand = PokerHandIdentifier.identifyPlayedHand(cards);
		scoreHand(playedHand, pokerHandTable);
		return playedHand;
	}
	
	public static double scoreHand(PlayedHand playedHand, PokerHandTable pokerHandTable) {
		String handType = playedHand.getHandType();
		playedHand.addChips(pokerHandTable.getPokerHand(handType).getChips());
		playedHand.addMult(pokerHandTable.getPokerHand(handType).getMult());
		
		// Pulls the cards that need to be scored and loops through the vector, scoring them
		for(Card card : pullScoredCards(playedHand.getPlayedCards(), handType)) {
			scoreCard(playedHand, card);
		}
		
		// Runs through the cards in hand and performs scoring (if applicable)
		
		// Runs through the jokers and performs scoring (if applicable)
		
		
		
		playedHand.score();
		return playedHand.getScore();
	}
	
	public static void scoreCard(PlayedHand playedHand, Card card) {
		playedHand.addChips(card.getValue());
		
		switch(card.getEdition()) {
		case Card.BONUS:
			playedHand.addChips(30);
		case Card.MULT:
			playedHand.addMult(4);
		}
		
		switch(card.getEnhancement()) {
		case Card.FOIL:
			playedHand.addChips(50);
		case Card.HOLOGRAPHIC:
			playedHand.addMult(10);
		case Card.POLYCHROME:
			playedHand.multiplyMult(1.5);
		}
		
		// FIXME: Any other chip or mult additions (such as for joker abilities, like greedy joker or hiker)
	}
	
	public static Vector<Card> pullScoredCards(Vector<Card> playedCards, String handType){
		Vector<Card> scoredCards = new Vector<Card>(0);
		
		if(handType == PokerHand.PAIR) {
			scoredCards = pullMatches(playedCards, 2);
		}else if(handType == PokerHand.THREE_OF_A_KIND){
			scoredCards = pullMatches(playedCards, 3);
		}else if(handType == PokerHand.FOUR_OF_A_KIND){
			scoredCards = pullMatches(playedCards, 4);	
		}else if(handType == PokerHand.FIVE_OF_A_KIND){
			return playedCards;
		}
		// The rest require five cards, so no check is really needed.
		else if(handType == PokerHand.FLUSH_FIVE) {
			return playedCards;
		}else if(handType == PokerHand.FLUSH_HOUSE) {
			return playedCards;
		}else if(handType == PokerHand.FULL_HOUSE) {
			return playedCards;
		}else if(handType == PokerHand.ROYAL_FLUSH) {
			return playedCards;
		}else {
			
		}
		
		return scoredCards;
	}
	
	/**
	 * Finds the matches in the given card vector, up to numMatches
	 * 
	 * NOTE: This function assumes that the cards will have the correct number of matches, given by numMatches.
	 * If the true number of matches is lower or higher, it won't work as expected
	 * 
	 * @param cards The cards to search
	 * @param numMatches The number of matches required
	 * @return Vector<Card> of matching cards
	 */
	public static Vector<Card> pullMatches(Vector<Card> cards, int numMatches){
		Vector<Card> matches = new Vector<Card>(0);
		for(int i = 0; i < cards.size(); i++) {
			matches.add(cards.get(i));
			
			matchUtil(cards, matches, numMatches - 1, i + 1);
			
			if(matches.size() == numMatches) {
				return matches;
			}
		}
		
		return cards;
	}
	
	/**
	 * Recursive function which finds the cards which match to eachother, given how many matches are needed
	 * 
	 * NOTE: The function changes the matches vector, so returning a Vector<Card> isn't strictly necessary, but helps readibility
	 * 
	 * @param cards The cards to search
	 * @param matches Card vector of cards that match in value
	 * @param numMatches The number of matches required
	 * @param currIndex The current index in the cards vector
	 * @return Matches vector, either one of length == numMatches, or length of 0 (indicating another round is necessary
	 */
	public static Vector<Card> matchUtil(Vector<Card> cards, Vector<Card> matches, int numMatches, int currIndex){
		// The function ticks numMatches down by one each time a match is found. If this function is called with numMatches == 0
		// All the matches have been found, and the function returns the matches vector
		if(numMatches == 0) {
			return matches;
		}
		
		// If there is no way to get the number of matches required from the current index, return nothing
		if(currIndex + numMatches - matches.size() >= cards.size()) {
			matches.clear();
			return matches;
		}
		// Otherwise if the current index matches, add it to the matches vector, lowering the number of remaining matches
		else if(cards.get(currIndex).getValue() == matches.get(0).getValue()) {
			numMatches -= 1;
			matches.add(cards.get(currIndex));
		} 
		// Returns another recursion of matchUtil, with the same or lower numMatches, and with the next index
		return matchUtil(cards, matches, numMatches, currIndex + 1);
	}
	
}
