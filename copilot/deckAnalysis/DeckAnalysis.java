package copilot.deckAnalysis;

import java.util.Vector;

import data.card.Card;
import data.deck.Deck;
import data.deck.DeckUtils;
import data.pokerHand.PokerHandTable;
import game.scoring.HandScorer;
import game.scoring.PlayedHand;

public class DeckAnalysis {
	PokerHandProbabilityTable probabilityTable;
	PokerHandTable pokerHandTable;
	
	public DeckAnalysis(Deck deck, PokerHandTable pokerHandTable) {
		this.pokerHandTable = pokerHandTable;
		probabilityTable = new PokerHandProbabilityTable(pokerHandTable.getPokerHandNames());
		storeProbabilities(DeckUtils.convertCardVectorToArray(deck.cards()), deck.size(), 5);
	}
	
	public void calculatePokerHandProbabilities(Deck deck, PokerHandTable pokerHandTable) {
		storeProbabilities(DeckUtils.convertCardVectorToArray(deck.cards()), deck.size(), 5);		
	}
	
	private void storeProbabilities(Card arr[], int n, int r) {
        Card data[] = new Card[r];
        combinationUtil(arr, data, 0, n-1, 0, r);
    }
	
	private void combinationUtil(Card arr[], Card data[], int start, int end, int index, int r)
    {
        // Current combination is ready to be printed, print it
        if (index == r){
        	Vector<Card> combination = new Vector<Card>(0);
            for (int j = 0; j < r; j++) {
            	combination.add(data[j]);
                //System.out.print(data[j].printValueAndSuit() + " ");
            }
            
            PlayedHand playedHand = HandScorer.scorePlayedHand(combination, pokerHandTable);
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
	
	public String printProbabilityTable() {
		return probabilityTable.printTable();
	}
}
