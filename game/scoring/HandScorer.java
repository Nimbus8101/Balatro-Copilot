package game.scoring;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import data.card.Card;
import data.card.Joker;
import data.card.JokerCard;
import data.card.PlayingCard;
import data.player.DefaultPlayer;
import data.pokerHand.PokerHand;
import data.pokerHand.PokerHandIdentifier;
import data.pokerHand.PokerHandTable;

public interface HandScorer extends ValueCountUtils{
	static final List<JokerCard> NO_JOKERS = List.of();

	
	/**
	 * Function to score a hand of 5
	 * @param cards Cards that were played
	 * @param pokerHandTable
	 * @return
	 */
	public static PlayedHand scorePlayedHand(List<PlayingCard> cards, List<JokerCard> jokers, PokerHandTable pokerHandTable) {
		PlayedHand playedHand = PokerHandIdentifier.identifyPlayedHand(cards);
		scoreHand(playedHand, jokers, pokerHandTable);
		return playedHand;
	}
	
	public static double scoreHand(PlayedHand playedHand, List<JokerCard> jokers, PokerHandTable pokerHandTable) {
		String handType = playedHand.getHandType();
		playedHand.setStartingChips(pokerHandTable.getPokerHand(handType).getChips());
		playedHand.setStartingMult(pokerHandTable.getPokerHand(handType).getMult());
				
		// Pulls the cards that need to be scored and loops through the vector, scoring them
		for(PlayingCard card : pullScoredCards(playedHand.getPlayedCards(), handType)) {
			scoreCard(playedHand, card);
		}
		
		List<ScoreChangeValues> changes = playedHand.getChanges();
		
		// Runs through the jokers and performs scoring (if applicable)
		for(JokerCard joker : jokers) {
			ScoreChangeValues change = new ScoreChangeValues();
			if(joker.type() == Joker.NO_TYPE) {
				change.addChips(joker.getChips());
				change.addMult(joker.getMult());
				change.addMultiplier(joker.getMultiplier());
				
				changes.add(change);
			}
		}
		
		/**
		for(ScoreChangeValues change : changes) {
			System.out.println(change.chips + " " + change.mult);
		}
		*/
		
		playedHand.score();
		
		return playedHand.getScore();
	}
	
	public static void scoreCard(PlayedHand playedHand, PlayingCard card) {
		ScoreChangeValues change = new ScoreChangeValues();
		
		change.addChips(card.getValue());
		
		switch(card.getEdition()) {
		case PlayingCard.BONUS:
			change.addChips(30);
			break;
		case PlayingCard.MULT:
			change.addMult(4);
			break;
		}
		
		switch(card.getEnhancement()) {
		case PlayingCard.FOIL:
			change.addChips(50);
			break;
		case PlayingCard.HOLOGRAPHIC:
			change.addMult(10);
			break;
		case PlayingCard.POLYCHROME:
			change.addMultiplier(1.5);
			break;
		}
		
		// FIXME: Any other chip or mult additions (such as for joker abilities, like greedy joker or hiker)
		
		// Adds the changes to the list of changes
		playedHand.addChange(change);
	}
	
	public static List<PlayingCard> pullScoredCards(List<PlayingCard> playedCards, String handType){
		List<PlayingCard> scoredCards = new ArrayList<>(0);
		
		switch(handType) {
		case PokerHand.HIGH_CARD:
			scoredCards = pullHighCard(playedCards);
			break;
		case PokerHand.PAIR:
			scoredCards = pullMatches(playedCards, 2);
			break;
		case PokerHand.THREE_OF_A_KIND:
			scoredCards = pullMatches(playedCards, 3);
			break;
		case PokerHand.TWO_PAIR:
			scoredCards = pullTwoPair(playedCards);
			break;
		case PokerHand.FOUR_OF_A_KIND:
			scoredCards = pullMatches(playedCards, 4);
			break;
		case PokerHand.FIVE_OF_A_KIND:
			// From this point, all hand types use all five cards, so no check is really necessary
		case PokerHand.FLUSH:
		case PokerHand.FULL_HOUSE:
		case PokerHand.STRAIGHT_FLUSH:
		case PokerHand.ROYAL_FLUSH:
		case PokerHand.FLUSH_HOUSE:
		case PokerHand.FLUSH_FIVE:
		default:
			return playedCards;
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
	 * @return List<PlayingCard> of matching cards
	 */
	public static List<PlayingCard> pullMatches(List<PlayingCard> cards, int numMatches) {
		int[] counts = new int[15];
		int targetValue = -1;
		for (PlayingCard c : cards) {
			counts[c.getValue()]++;
			
			if(counts[c.getValue()] == numMatches) {
	    		targetValue = c.getValue();
	            break;
	    	}
		}
	    
	    if (targetValue == -1) return cards; // fallback

	    List<PlayingCard> matches = new ArrayList<>(numMatches);
	    for (PlayingCard c : cards) {
	        if (c.getValue() == targetValue)
	            matches.add(c);
	    }
	    return matches;
	}
	
	
	
	/**
	 * Recursive function which finds the cards which match to each other, given how many matches are needed
	 * 
	 * NOTE: The function changes the matches vector, so returning a List<PlayingCard> isn't strictly necessary, but helps readibility
	 * 
	 * @param cards The cards to search
	 * @param matches Card vector of cards that match in value
	 * @param numMatches The number of matches required
	 * @param currIndex The current index in the cards vector
	 * @return Matches vector, either one of length == numMatches, or length of 0 (indicating another round is necessary
	 */
	public static List<PlayingCard> matchUtil(List<PlayingCard> cards, List<PlayingCard> matches, int numMatches, int currIndex){
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
	
	public static List<PlayingCard> pullHighCard(List<PlayingCard> cards) {
		List<PlayingCard> scoredCards = new ArrayList<>(0);
		int max = cards.get(0).getValue();
		int index = 0;
		
		for(int i = 0; i < cards.size(); i++) {
			if(cards.get(i).getValue() > max) {
				max = cards.get(i).getValue();
				index = i;
			}
		}
		scoredCards.add(cards.get(index));
		
		return scoredCards;
	}
	
	public static List<PlayingCard> pullTwoPair(List<PlayingCard> cards){
		List<PlayingCard> scoredCards = new ArrayList<>(0);
		int unmatchedIndex = -1;

		for (int i = 0; i < cards.size(); i++) {
		    int value = cards.get(i).getValue();
		    boolean isMatched = false;

		    for (int j = 0; j < cards.size(); j++) {
		        if (i != j && cards.get(j).getValue() == value) {
		            // Found a match for cards[i]
		            isMatched = true;
		            break;
		        }
		    }

		    if (!isMatched) {
		        // Found the unmatched card
		        unmatchedIndex = i;
		        break; // No need to continue; we only expect one unmatched card
		    }
		}
		
		for(int i = 0; i < cards.size(); i++) {
			if(i != unmatchedIndex) {
				scoredCards.add(cards.get(i));
			}
		}
		
		return scoredCards;
	}
}
