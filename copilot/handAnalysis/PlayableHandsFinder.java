package copilot.handAnalysis;

import java.util.Vector;

import copilot.utils.Combination;
import data.card.Card;
import data.deck.DeckUtils;
import data.pokerHand.PokerHandIdentifier;
import game.GameState;
import game.scoring.HandScorer;
import game.scoring.PlayedHand;

public interface PlayableHandsFinder {	
	/**
	 * Finds the playable hands from the cards in hand
	 * @param cardsInHand cardsInHand
	 * @return vector of PlayedHand representing all the possible playable hands
	 */
	public static Vector<PlayedHand> findPlayableHands(Vector<Card> cardsInHand){
		Combination combination = new Combination();
		boolean DEBUG = false;
		
		//FIXME the initial solution to find playable hands is to try every combination of 5.
		Vector<Vector<Integer>> indexCombinations = combination.generateAllIndexCombinations(cardsInHand.size(), 5);
		
		if(DEBUG) {
			System.out.println("indexCombinations = ");
			for(int i = 0 ; i < indexCombinations.size(); i++) {
				Vector<Integer> indexCombinationVector = indexCombinations.get(i);
				
				for(int j = 0; j < indexCombinationVector.size(); j++) {
					System.out.print(indexCombinationVector.get(j) + " ");
				}
				System.out.println();
			}
		}
		
		return convertIndexCombinationsToPlayedHands(indexCombinations, cardsInHand);
	}
	
	private static Vector<PlayedHand> convertIndexCombinationsToPlayedHands(Vector<Vector<Integer>> indexCombinations, Vector<Card> cardsInHand){
		Vector<PlayedHand> possibleHands = new Vector<PlayedHand>(0);
		
		for(int i = 0; i < indexCombinations.size(); i++) {
			Vector<Card> tempHand = DeckUtils.copyCardVector(cardsInHand);
			Vector<Card> playedCards = pullCardsFromVector(tempHand, indexCombinations.get(i));
			PlayedHand hand = PokerHandIdentifier.identifyPlayedHand(playedCards);
			hand.setHeldCards(tempHand);
			
			possibleHands.add(hand);
		}
		
		return possibleHands;
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
}
