package copilot.handAnalysis;

import java.util.Vector;

import copilot.utils.Combination;
import data.card.Card;
import data.deck.DeckUtils;
import data.pokerHand.PokerHandTable;
import game.scoring.HandScorer;
import game.scoring.PlayedHand;

public interface PlayableHandsFinder {	
	/**
	 * Given a vector of Cards, finds the playable hands and scores them, keeping track of the highest one while scoring
	 * 
	 * @param cardsInHand
	 * @param pokerHandTable
	 * @return
	 */
	public static PlayedHand findHighestScoringPlayableHand(Vector<Card> cardsInHand, PokerHandTable pokerHandTable) {
		// Finds all playable hands
		Vector<PlayedHand> playableHands = findPlayableHands(cardsInHand);
		
		// Scores all hands and keeps track of the highest score
		double highScore = 0.0;
		int highScoreIndex = 0;
		for(int i = 0; i < playableHands.size(); i++) {
			// Score the hand
			HandScorer.scoreHand(playableHands.get(i), pokerHandTable);
			
			// Checks if the score is higher than the current high score, saving it if so
			if(i == 0) {
				highScore = playableHands.get(i).getScore();
			}else if(playableHands.get(i).getScore() > highScore) {
				highScore = playableHands.get(i).getScore();
				highScoreIndex = i;
			}
		}
		return playableHands.get(highScoreIndex);
	}
	
	
	/**
	 * Finds the playable hands from the cards in hand
	 * @param cardsInHand cardsInHand
	 * @return vector of PlayedHand representing all the possible playable hands
	 */
	public static Vector<PlayedHand> findPlayableHands(Vector<Card> cardsInHand){
		//FIXME i would like a similar function which finds all the potential hands from a hand of HAND_SIZE but only counts the highest scoring hand
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
	
	/** 
	 * Converts the indexCombinations and Card vectors into a "possibleHands" vector, to be scored later
	 * @param indexCombinations
	 * @param cardsInHand
	 * @return
	 */
	private static Vector<PlayedHand> convertIndexCombinationsToPlayedHands(Vector<Vector<Integer>> indexCombinations, Vector<Card> cardsInHand){
		Vector<PlayedHand> possibleHands = new Vector<PlayedHand>(0);
		
		for(int i = 0; i < indexCombinations.size(); i++) {
			// Copies the cards from the played hand and held hand
			Vector<Card> tempHand = DeckUtils.copyCardVector(cardsInHand);
			Vector<Card> playedCards = pullCardsFromVector(tempHand, indexCombinations.get(i));
			
			// Creates the PlayedHand from the copied vectors
			PlayedHand hand = new PlayedHand(playedCards, tempHand);
			
			// Adds the hand to the possibleHands vector
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
