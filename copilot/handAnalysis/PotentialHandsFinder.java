package copilot.handAnalysis;

import java.util.Vector;

import copilot.deckAnalysis.PokerHandProbabilityTable;
import copilot.utils.Combination;
import data.card.Card;
import data.deck.DeckUtils;
import data.pokerHand.PokerHandTable;
import game.GameState;
import game.scoring.HandScorer;
import game.scoring.PlayedHand;

public class PotentialHandsFinder {	
	public static final boolean DEBUG = true;
	private Card[] currHand;
	private Card[] currDeck;
	private Card[] partialHand;
	int numDiscarded = 1;
	
	PokerHandProbabilityTable probabilityTable;
	PokerHandTable pokerHandTable;
	
	public PotentialHandsFinder(GameState gameState) {
		// Converts the deck and the current hand to arrays
		currDeck = DeckUtils.convertCardVectorToArray(gameState.getCurrDeck().cards());
		currHand = DeckUtils.convertCardVectorToArray(gameState.getCurrHand());	
		
		// Pulls the poker hand table from the player variables
		pokerHandTable  = gameState.getPlayer().getPokerHandTable();
		
		// Creates a blank poker hand probability table from the pokerHandTable
		probabilityTable = new PokerHandProbabilityTable(pokerHandTable.getPokerHandNames());
	}
	
	public PokerHandProbabilityTable generateProbabilityTableOfPotentialHands(GameState gameState) {
		if(DEBUG) System.out.print("[DEBUG] - Entering generateProbabilityTableOfPotentialHands()\n");
		for(numDiscarded = 1; numDiscarded <= 5; numDiscarded++) {
			if(DEBUG) System.out.println("[DEBUG] - numDiscarded: " + numDiscarded);
			discardCombinationUtil(currHand, new Card[currHand.length - numDiscarded], 0, currHand.length - 1, 0, currHand.length - numDiscarded);
		}
		return probabilityTable;
		/**
		Vector<PlayedHand> potentialHands = findPotentialHands(gameState);
		PokerHandTable pokerHandTable = gameState.getPlayer().getPokerHandTable();
		
		PokerHandProbabilityTable pokerHandProbabilityTable = new PokerHandProbabilityTable(pokerHandTable.getPokerHandNames());
		
		if(DEBUG) System.out.print("[DEBUG] - pokerHandProbabilityTable should be created\n");
		
		for(int i = 0; i < potentialHands.size(); i++) {
			HandScorer.scoreHand(potentialHands.get(i), gameState.getPlayer().getPokerHandTable());
			pokerHandProbabilityTable.addScore(potentialHands.get(i).getHandType(), potentialHands.get(i).getScore());
		}
		
		if(DEBUG) System.out.print("[DEBUG] - pokerHandProbabilityTable should be filled\n");
		*/
	}
	
	/**
	 * This generates combinations of partial hands from a given hand (simulates the hands after each discard)
	 * @param arr
	 * @param data
	 * @param start
	 * @param end
	 * @param index
	 * @param r
	 */
	private void discardCombinationUtil(Card arr[], Card data[], int start, int end, int index, int r){
        if (index == r){
        	Vector<Card> combination = new Vector<Card>(0);
        	
        	if(DEBUG) System.out.print("[DEBUG] - partial hand: ");
        	
            for (int j = 0; j < r; j++) {
            	combination.add(data[j]);
                if(DEBUG) System.out.print(data[j].printValueAndSuit() + " ");
            }
            if(DEBUG) System.out.print("\n");
            
            partialHand = data;
            drawCombinationUtil(currDeck, new Card[numDiscarded], 0, currDeck.length - 1, 0, numDiscarded);
            //PlayableHandsFinder.findPlayableHands(combination);
            //System.out.println("");
            return;
        }
        for (int i = start; i <= end && end - i + 1 >= r - index; i++){
            data[index] = arr[i];
            discardCombinationUtil(arr, data, i+1, end, index+1, r);
        }
    }
	
	/**
	 * Recursive function to find all the combinations of draws, taking in currDeck and some set-up variables.
	 * Automatically stores the results in the class's pokerHandProbabilityTable
	 * @param arr
	 * @param data
	 * @param start
	 * @param end
	 * @param index
	 * @param r
	 */
	private void drawCombinationUtil(Card arr[], Card data[], int start, int end, int index, int r){
		//FIXME if deck is smaller then numDiscarded, there is some weird behavior
		if(index == r){
        	Vector<Card> combination = new Vector<Card>(0);
        	
        	if(DEBUG) System.out.print("                     -> ");
        	
        	for(int i = 0; i < partialHand.length; i++) {
        		combination.add(partialHand[i]);
        		if(DEBUG) System.out.print(partialHand[i].printValueAndSuit() + " ");
        	}
        	
            for(int j = 0; j < r; j++) {
            	combination.add(data[j]);
                if(DEBUG) System.out.print(data[j].printValueAndSuit() + " ");
            }
            if(DEBUG) System.out.print("\n");

            addPlayedHandsToProbabilityTable(PlayableHandsFinder.findPlayableHands(combination));
            
            return;
        }
        for (int i = start; i <= end && end - i + 1 >= r - index; i++){
            data[index] = arr[i];
            drawCombinationUtil(arr, data, i+1, end, index+1, r);
        }
	}
	
	private void addPlayedHandsToProbabilityTable(Vector<PlayedHand> hands) {
		for(int i = 0; i < hands.size(); i++) {
			HandScorer.scoreHand(hands.get(i), pokerHandTable);
			probabilityTable.addScore(hands.get(i).getHandType(), hands.get(i).getScore());
		}
	}
	
	public PokerHandProbabilityTable calculatePokerHandProbabilityBasedOnDiscardIndexes(int[] indexes) {
		probabilityTable = new PokerHandProbabilityTable(pokerHandTable.getPokerHandNames());
		
		if(DEBUG) System.out.println(currHand.length + " - " + indexes.length);
		
		Vector<Card> partialHand = new Vector<Card>(0);
				
		if(DEBUG) {
			System.out.print("Hand: ");
			for(int i = 0; i < currHand.length; i++) {
				System.out.print(currHand[i].printValueAndSuit() + " ");
			}
			System.out.print("\nIndexes: ");
			for(int i = 0; i < indexes.length; i++) {
				System.out.print(indexes[i] + " ");
			}
			System.out.print("\n");
		}

		for(int i = 0; i < currHand.length; i++) {
			partialHand.add(currHand[i]);
		}
		//NOTE watch the order of the indexes!!!
		for(int i = 0; i < indexes.length; i++) {
			partialHand.remove(indexes[i]);
		}
		
		if(DEBUG) {
			for(int i = 0; i < partialHand.size(); i++) {
				System.out.print(partialHand.get(i).printValueAndSuit() + " ");
			}
			System.out.println();
		}
		
		this.partialHand = DeckUtils.convertCardVectorToArray(partialHand);
		
		//perform nCr on currDeck, n = currDeck.length, r = numToDraw
		drawCombinationUtil(currDeck, new Card[indexes.length], 0, currDeck.length - 1, 0, indexes.length);
		
		return probabilityTable;
	}
	
	public static Vector<PlayedHand> findPotentialHands(GameState gameState){
		if(DEBUG) System.out.print("[DEBUG] - in findPotentialHands() function");
		
		Vector<Card> currHand = gameState.getCurrHand();
		Vector<Vector<Integer>> discardCombinations = generateDiscardCombinations(currHand);
		Vector<Vector<Card>> partialHands = convertDiscardCombinationsToPartialHands(discardCombinations, currHand);
		
		if(DEBUG) System.out.print("[DEBUG] - discardCombinations and partialHands should be created\n");
		
		return generatePlayedHands(partialHands, gameState.getCurrDeck().cards());
	}
	
	private static Vector<Vector<Integer>> generateDiscardCombinations(Vector<Card> cardsInHand) {
		if(DEBUG) System.out.print("[DEBUG] - in genereateDiscardCombinations() function");
		Combination combination = new Combination();

		Vector<Vector<Integer>> indexCombinations = new Vector<Vector<Integer>>(0);
		for(int i = 0; i < 6; i++) {
			Vector<Vector<Integer>> temp = combination.generateAllIndexCombinations(cardsInHand.size(), i);
			for(int j = 0; j < temp.size(); j++) {
				indexCombinations.add(temp.get(j));
			}
		}
				
		return indexCombinations;
	}
	
	private static Vector<Vector<Card>> convertDiscardCombinationsToPartialHands(Vector<Vector<Integer>> indexCombinations, Vector<Card> cardsInHand){
		if(DEBUG) System.out.print("[DEBUG] - in convertDiscardCombinationsToPartialHands() function\n");
		Vector<Vector<Card>> partialHands = new Vector<Vector<Card>>(0);  //This vector represents possible partial hands, and are missing the drawn cards
		
		for(int i = 0; i < indexCombinations.size(); i++) {
			Vector<Card> tempHand = DeckUtils.copyCardVector(cardsInHand);
			DeckUtils.pullCardsFromVector(tempHand, indexCombinations.get(i));
			partialHands.add(tempHand);
		}
		
		return partialHands;
	}
	
	private static Vector<PlayedHand> generatePlayedHands(Vector<Vector<Card>> partialHands, Vector<Card> cardsInDeck){
		if(DEBUG) System.out.print("[DEBUG] - In generatePlayedHands() function \n");
		Vector<PlayedHand> potentialHands = new Vector<PlayedHand>(0);
		
		for(int i = 0; i < partialHands.size(); i++) {
			Vector<Card> partialHand = partialHands.get(i);
			Vector<Vector<Card>> potentialDraws = generateDrawCombinations(cardsInDeck, 7 - partialHand.size());
			
			for(int j = 0; j < potentialDraws.size(); j++) {
				Vector<Card> completeHand = new Vector<Card>(0);
				completeHand.addAll(partialHand);
				completeHand.addAll(potentialDraws.get(j));
				potentialHands.addAll(PlayableHandsFinder.findPlayableHands(completeHand));
			}
		}
		if(DEBUG) System.out.print("[DEBUG] - Leaving generatePlayedHands() function \n");
		
		return potentialHands;
	}
	
	private static Vector<Vector<Card>> generateDrawCombinations(Vector<Card> cardsInDeck, int numDraw){
		if(DEBUG) System.out.print("[DEBUG] - In generateDrawCombinations() function \n	  numDraw: " + numDraw);
		
		Combination combination = new Combination();
		
		Vector<Vector<Card>> c = combination.findAllCombinations(cardsInDeck, numDraw);
		
		for(int i = 0; i < c.size(); i++) {
			Vector<Card> cards = c.get(i);
			System.out.print("[Debug] -  [");
			for(int j = 0; j < cards.size(); j++) {
				System.out.print(Integer.toString(cards.get(j).getValue()) + cards.get(j).getSuit());
				if(j < cards.size() - 1) System.out.print(" ");
			}
			System.out.print("]\n");
		}
		
		return combination.findAllCombinations(cardsInDeck, numDraw);
	}
}
