package copilot.handAnalysis;

import java.util.Vector;

import copilot.utils.Combination;
import data.card.Card;
import data.card.JokerCard;
import data.card.PlayingCard;
import data.deck.DeckUtils;
import data.pokerHand.PokerHandTable;
import game.scoring.HandScorer;
import game.scoring.PlayedHand;


/**
 * Class which finds the hands that can be played from a given Vector of Cards, returning the highest scoring one
 * 
 * @author Elijah
 */
public interface PlayableHandsFinder {	
	/**
	 * Given a vector of Cards, finds the playable hands and scores them, keeping track of the highest one while scoring
	 * 
	 * @param cardsInHand
	 * @param pokerHandTable
	 * @return
	 */
	public static PlayedHand findHighestScoringPlayableHand(Vector<PlayingCard> cardsInHand, PokerHandTable pokerHandTable) {
		System.out.print("-");
	    int n = cardsInHand.size();
	    int r = 5;
	    if (n < r) return null;

	    double bestScore = Double.NEGATIVE_INFINITY;
	    PlayedHand bestHand = null;

	    int mask = (1 << r) - 1;
	    while (mask < (1 << n)) {
	        Vector<PlayingCard> played = new Vector<>(r);
	        Vector<PlayingCard> held = new Vector<>(n - r);
	        for (int i = 0; i < n; i++) {
	            if ((mask & (1 << i)) != 0)
	                played.add(cardsInHand.get(i));
	            else
	                held.add(cardsInHand.get(i));
	        }

	        PlayedHand hand = new PlayedHand(played, held);
	        HandScorer.scoreHand(hand, new Vector<JokerCard>(0), pokerHandTable);
	        
	        if (hand.getScore() > bestScore) {
	            bestScore = hand.getScore();
	            bestHand = hand;
	        }

	        // Next combination
	        int c = mask & -mask;
	        int rmask = mask + c;
	        mask = (((rmask ^ mask) >>> 2) / c) | rmask;
	        System.out.print("*");
	    }

	    return bestHand;
	}

	
	public static Vector<PlayedHand> findPlayableHands(Vector<PlayingCard> cardsInHand) {
	    Vector<PlayedHand> possibleHands = new Vector<>();
	    int n = cardsInHand.size();
	    int r = 5;

	    if (n < r) return possibleHands;

	    // Start with first r bits set
	    int mask = (1 << r) - 1;

	    while (mask < (1 << n)) {
	        Vector<PlayingCard> played = new Vector<>(r);
	        Vector<PlayingCard> held = new Vector<>(n - r);

	        for (int i = 0; i < n; i++) {
	            if ((mask & (1 << i)) != 0)
	                played.add(cardsInHand.get(i));
	            else
	                held.add(cardsInHand.get(i));
	        }

	        possibleHands.add(new PlayedHand(played, held));

	        // Generate next combination (Gosper’s hack)
	        int c = mask & -mask;
	        int rmask = mask + c;
	        mask = (((rmask ^ mask) >>> 2) / c) | rmask;
	    }

	    return possibleHands;
	}
	
	
	/** 
	 * Converts the indexCombinations and Card vectors into a "possibleHands" vector, to be scored later
	 * @param indexCombinations
	 * @param cardsInHand
	 * @return
	 */
	private static Vector<PlayedHand> convertIndexCombinationsToPlayedHands(Vector<Vector<Integer>> indexCombinations, Vector<PlayingCard> cardsInHand){
		Vector<PlayedHand> possibleHands = new Vector<PlayedHand>(0);
		
		for(int i = 0; i < indexCombinations.size(); i++) {
			// Copies the cards from the played hand and held hand
			Vector<PlayingCard> tempHand = DeckUtils.copyCardVector(cardsInHand);
			Vector<PlayingCard> playedCards = pullCardsFromVector(tempHand, indexCombinations.get(i));
			
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
	public static Vector<PlayingCard> pullCardsFromVector(Vector<PlayingCard> cards, Vector<Integer> indexes){
		//System.out.println("-- " + cards.size() + " - " + indexes.size());
		Vector<PlayingCard> pulledCards = new Vector<PlayingCard>(0);
			for(int i = indexes.size() - 1; i >= 0; i--) {
				//System.out.println((int) indexes.get(i));
				pulledCards.add(cards.remove((int) indexes.get(i)));
			}
		return pulledCards;
	}
}
