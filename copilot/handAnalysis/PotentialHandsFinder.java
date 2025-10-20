package copilot.handAnalysis;

import java.util.Vector;

import copilot.deckAnalysis.PokerHandProbabilityTable;
import copilot.utils.Combination;
import data.card.Card;
import data.card.JokerCard;
import data.card.PlayingCard;
import data.deck.DeckUtils;
import data.player.Player;
import data.pokerHand.PokerHandTable;
import game.GameState;
import game.scoring.HandScorer;
import game.scoring.PlayedHand;


/*
 * This class generates the possible hands that can result from discarding cards in hand.
 * Process:
 * - Generates every possible combination of discarded cards, numbering from 1 to 5
 * - Generates every possible combination of drawn cards resulting from the previous discard
 * - For every resulting hand consisting of remaining and drawn cards, use the PlayableHandsFinder class to find the highest scoring hand
 * - Add that hand to the PokerHandProbabilityTable
 * 
 * In summary, this class is used to generate the potential hands resulting from a discard, 
 * taking into account the current hand and cards in the deck, and building a probability
 * table from the results. 
 * This will be used to do further analysis on the remaining hand and further discards, which
 * will be used by the copilot to determine which hand to play or which cards to discard
 * 
 * 
 * There is also some unused code at the bottom, and is kept in case multithreading is implemented later.
 * 
 * @author Elijah Reyna
 */
public class PotentialHandsFinder {	
	public static final boolean DEBUG = true;
	private PlayingCard[] currHand;
	private PlayingCard[] currDeck;
	private PlayingCard[] partialHand;
	int numDiscarded = 1;
	
	PokerHandProbabilityTable probabilityTable;
	PokerHandTable pokerHandTable;
	
	
	/**
	 * Default Constructor
	 * @param gameState The game state to analyze
	 */
	public PotentialHandsFinder(GameState gameState) {
		// Converts the deck and the current hand to arrays
		currDeck = DeckUtils.convertCardVectorToArray(gameState.getCurrDeck().cards());
		currHand = DeckUtils.convertCardVectorToArray(gameState.getCurrHand());	
		
		// Pulls the poker hand table from the player variables
		pokerHandTable  = Player.getPokerHandTable();
		
		// Creates a blank poker hand probability table from the pokerHandTable
		probabilityTable = new PokerHandProbabilityTable(pokerHandTable.getPokerHandNames());
	}
	
	
	/**
	 * Generates the probability table based on possible discards
	 * @param gameState GameState to analyze
	 * @return PokerHandProbability table representing the results
	 */
	public PokerHandProbabilityTable generateProbabilityTableOfPotentialHands(GameState gameState) {
		if(DEBUG) System.out.print("[DEBUG] - Entering generateProbabilityTableOfPotentialHands()\n");
		for(numDiscarded = 1; numDiscarded <= 5; numDiscarded++) {
			if(DEBUG) System.out.println("[DEBUG] - numDiscarded: " + numDiscarded);
			discardCombinationUtil(currHand, new PlayingCard[currHand.length - numDiscarded], 0, currHand.length - 1, 0, currHand.length - numDiscarded);
		}
		return probabilityTable;
	}
	
	/**
	 * Recursive function which generates combinations of partial hands from a given hand (simulates the hands after each discard)
	 * When it reaches a valid combination (a partial hand that matches length r (hand.length - numDiscards), it moves to the drawCombinationUtil() function
	 * 
	 * @param arr
	 * @param data
	 * @param start
	 * @param end
	 * @param index
	 * @param r int Length of the hand after discards (r = hand.length - numDiscards)
	 */
	private void discardCombinationUtil(PlayingCard arr[], PlayingCard data[], int start, int end, int index, int r){
        if (index == r){
        	Vector<PlayingCard> combination = new Vector<PlayingCard>(0);
        	
        	if(DEBUG) System.out.print("[DEBUG] - partial hand: ");
        	
            for (int j = 0; j < r; j++) {
            	combination.add(data[j]);
                if(DEBUG) System.out.print(data[j].printValueAndSuit() + " ");
            }
            if(DEBUG) System.out.print("\n");
            
            partialHand = data;
            drawCombinationUtil(currDeck, new PlayingCard[numDiscarded], 0, currDeck.length - 1, 0, numDiscarded);
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
	 * Recursive function which generates all the possible hands from a given partial hand
	 * When it reaches the desired length (8 is the default for now), it scores the combination and stores it in the class's PokerHandProbabilityTable
	 * @param arr
	 * @param data
	 * @param start
	 * @param end
	 * @param index
	 * @param r
	 */
	private void drawCombinationUtil(PlayingCard arr[], PlayingCard data[], int start, int end, int index, int r){
		//FIXME if deck is smaller then numDiscarded, there is some weird behavior
		if(index == r){
        	Vector<PlayingCard> combination = new Vector<PlayingCard>(0);
        	
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
	
	/**
	 * Scores the hands and adds them to the probability table
	 * @param hands Vector<PlayedHand> to score and store
	 */
	private void addPlayedHandsToProbabilityTable(Vector<PlayedHand> hands) {
		for(int i = 0; i < hands.size(); i++) {
			HandScorer.scoreHand(hands.get(i), new Vector<JokerCard>(0), pokerHandTable);
			probabilityTable.addScore(hands.get(i).getHandType(), hands.get(i).getScore());
		}
	}
	
	
// ============================================================ UNUSED METHODS ============================================================ //
	// The following methods are an old implementation of finding the possible hands that could be generated from all possible combinations of discards
	// from a specific hand, scoring and storing them in a PokerHandProbabilityTable
	// This old implementation created a massive amount of Vectors and Vectors of Vectors. The current implementation does away with that (above)
	// And instead performs the scoring and storing of each combination as it is generated.
	
	// I have left the old implementation down here in case I choose to implement a multithreaded approach to this class, as generating arrays of
	// cards and combinations is much easier to parallelize
	
	// The following commented code can be placed in the entry function in this class to use these unimplemented methods
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
	
	
	/**
	 * Takes in an array of indexes representing cards "discarded" from hand in a previous step, then uses drawCombinationUtil
	 * to calculate all the possible hands that could be drawn into and scores it, which is stored in the probability table
	 * @param indexes int[] representing the indexes in the currHand that were discarded
	 * @return PokerHandProbabilityTable with data from the calculation
	 */
	public PokerHandProbabilityTable calculatePokerHandProbabilityBasedOnDiscardIndexes(int[] indexes) {
		probabilityTable = new PokerHandProbabilityTable(pokerHandTable.getPokerHandNames());
		
		if(DEBUG) System.out.println(currHand.length + " - " + indexes.length);
		
		Vector<PlayingCard> partialHand = new Vector<PlayingCard>(0);
				
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
		drawCombinationUtil(currDeck, new PlayingCard[indexes.length], 0, currDeck.length - 1, 0, indexes.length);
		
		return probabilityTable;
	}
	
	/**
	 * Method for finding all the possible hands from a discard given a specific game state
	 * @param gameState GameState
	 * @return Vector<PlayedHand> representing the possible hands (assuming a hand will not be played
	 */
	public static Vector<PlayedHand> findPotentialHands(GameState gameState){
		if(DEBUG) System.out.print("[DEBUG] - in findPotentialHands() function");
		
		// Takes the current hand, generates all possible combinations of discards, and generates all possible partial hands from those combinations
		Vector<PlayingCard> currHand = gameState.getCurrHand();
		Vector<Vector<Integer>> discardCombinations = generateDiscardCombinations(currHand);
		Vector<Vector<PlayingCard>> partialHands = convertDiscardCombinationsToPartialHands(discardCombinations, currHand);
		
		if(DEBUG) System.out.print("[DEBUG] - discardCombinations and partialHands should be created\n");
		
		// Then generates the hands that could be drawn into after their respective discards, and returns it
		return generatePlayedHands(partialHands, gameState.getCurrDeck().cards());
	}
	
	
	/**
	 * Generates the possible discard combinations from a given hand
	 * @param cardsInHand Vector<PlayingCard> The cards in hand
	 * @return Vector<Vector<Integer>> List of indexes for the discards
	 */
	private static Vector<Vector<Integer>> generateDiscardCombinations(Vector<PlayingCard> cardsInHand) {
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
	
	
	/**
	 * Takes in a list of discard index combinations, and the cards in hand, to generate the partial hands resulting from each discard
	 * @param indexCombinations Vector<Vector<Integer>> List of discard index combinations
	 * @param cardsInHand Vector<PlayingCard> Cards in hand
	 * @return Vector<Vector<PlayingCard>> Partial hands generated
	 */
	private static Vector<Vector<PlayingCard>> convertDiscardCombinationsToPartialHands(Vector<Vector<Integer>> indexCombinations, Vector<PlayingCard> cardsInHand){
		if(DEBUG) System.out.print("[DEBUG] - in convertDiscardCombinationsToPartialHands() function\n");
		Vector<Vector<PlayingCard>> partialHands = new Vector<Vector<PlayingCard>>(0);  //This vector represents possible partial hands, and are missing the drawn cards
		
		for(int i = 0; i < indexCombinations.size(); i++) {
			Vector<PlayingCard> tempHand = DeckUtils.copyCardVector(cardsInHand);
			DeckUtils.pullCardsFromVector(tempHand, indexCombinations.get(i));
			partialHands.add(tempHand);
		}
		
		return partialHands;
	}
	
	
	/**
	 * Generates the potentialHands from a list of partial hands generated in a previous step and the cards in deck
	 * 
	 * @param partialHands Vector<Vector<PlayingCard>> incomplete hands of cards
	 * @param cardsInDeck Vector<PlayingCard> the cards in the deck
	 * @return Vector<PlayedHand> List of complete hands that were generated
	 */
	private static Vector<PlayedHand> generatePlayedHands(Vector<Vector<PlayingCard>> partialHands, Vector<PlayingCard> cardsInDeck){
		if(DEBUG) System.out.print("[DEBUG] - In generatePlayedHands() function \n");
		Vector<PlayedHand> potentialHands = new Vector<PlayedHand>(0);
		
		// For each partialHand
		for(int i = 0; i < partialHands.size(); i++) {
			// Selects a partial hand and generates all the possible draws from that hand
			Vector<PlayingCard> partialHand = partialHands.get(i);
			Vector<Vector<PlayingCard>> potentialDraws = generateDrawCombinations(cardsInDeck, 7 - partialHand.size());
			
			// For each potential draw, combine with the partial hand and find the highest scoring played hand, and adds it to the potentialHands vector
			for(int j = 0; j < potentialDraws.size(); j++) {
				Vector<PlayingCard> completeHand = new Vector<PlayingCard>(0);
				completeHand.addAll(partialHand);
				completeHand.addAll(potentialDraws.get(j));
				potentialHands.addAll(PlayableHandsFinder.findPlayableHands(completeHand));
			}
		}
		if(DEBUG) System.out.print("[DEBUG] - Leaving generatePlayedHands() function \n");
		
		return potentialHands;
	}
	
	
	/**
	 * Generates the possible combinations of drawn cards
	 * @param cardsInDeck Vector<PlayingCard> cards in the deck
	 * @param numDraw int Number of cards to draw
	 * @return Vector<Vector<PlayingCard>> combination of drawn cards
	 */
	private static Vector<Vector<PlayingCard>> generateDrawCombinations(Vector<PlayingCard> cardsInDeck, int numDraw){
		if(DEBUG) System.out.print("[DEBUG] - In generateDrawCombinations() function \n	  numDraw: " + numDraw);
		
		Combination combination = new Combination();
		
		Vector<Vector<PlayingCard>> c = combination.findAllCombinations(cardsInDeck, numDraw);
		
		for(int i = 0; i < c.size(); i++) {
			Vector<PlayingCard> cards = c.get(i);
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
