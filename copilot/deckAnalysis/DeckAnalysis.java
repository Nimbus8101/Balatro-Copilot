package copilot.deckAnalysis;

import java.util.Vector;

import data.card.JokerCard;
import data.card.PlayingCard;
import data.deck.Deck;
import data.deck.DeckUtils;
import data.pokerHand.PokerHandTable;
import game.scoring.HandScorer;
import game.scoring.PlayedHand;


/**
 * Class which Finds all possible combinations of cards in a deck, scores them, and stores that information into the PokerHandProbabilityTable
 * Currently stores:
 *  - Poker Hand probabilities
 *  - their upper and lower scores
 *  - The number of times they appeared
 * 
 * @author Elijah Reyna
 */
public class DeckAnalysis {
	PokerHandProbabilityTable probabilityTable;
	PokerHandTable pokerHandTable;
	
	/**
	 * Constructor which automatically does the analysis of the deck and poker hand table passed into it
	 * @param deck
	 * @param pokerHandTable
	 */
	public DeckAnalysis(Deck deck, PokerHandTable pokerHandTable) {
		this.pokerHandTable = pokerHandTable;
		probabilityTable = new PokerHandProbabilityTable(pokerHandTable.getPokerHandNames());
		
		// Calculates and stores the probabilities
		storeProbabilities(DeckUtils.convertCardVectorToArray(deck.cards()), deck.size(), 5);
	}
	
	/**
	 * Runs the recursive combinationUtil function to reach a combination of cards
	 * @param arr
	 * @param n
	 * @param r
	 */
	private void storeProbabilities(PlayingCard arr[], int n, int r) {
        PlayingCard data[] = new PlayingCard[r];
        combinationUtil(arr, data, 0, n-1, 0, r);
    }
	
	/**
	 * Recursively finds and scores all hands of length r
	 * @param arr
	 * @param data
	 * @param start
	 * @param end
	 * @param index
	 * @param r
	 */
	private void combinationUtil(PlayingCard arr[], PlayingCard data[], int start, int end, int index, int r)
    {
        // Current combination is ready to be printed, print it
        if (index == r){
        	Vector<PlayingCard> combination = new Vector<PlayingCard>(0);
            for (int j = 0; j < r; j++) {
            	combination.add(data[j]);
                //System.out.print(data[j].printValueAndSuit() + " ");
            }
            
            PlayedHand playedHand = HandScorer.scorePlayedHand(combination, new Vector<JokerCard>(0), pokerHandTable);
            
            
            probabilityTable.addScore(playedHand.getHandType(), playedHand.getScore());
            
            //System.out.println(playedHand.print("  "));
            
            //System.out.println(playedHand.getScore());
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i = start; i <= end && end - i + 1 >= r - index; i++)
        {
            data[index] = arr[i];
            combinationUtil(arr, data, i+1, end, index+1, r);
        }
    }
	
	
	public PokerHandProbabilityTable getTable() {
		return probabilityTable;
	}
	
	public String printProbabilityTable() {
		return probabilityTable.printTable();
	}
}
