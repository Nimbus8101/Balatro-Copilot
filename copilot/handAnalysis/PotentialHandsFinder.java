package copilot.handAnalysis;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import copilot.deckAnalysis.PokerHandProbabilityTable;
import copilot.utils.Combination;
import data.card.Card;
import data.card.JokerCard;
import data.card.PlayingCard;
import data.deck.Deck;
import data.deck.DeckUtils;
import data.player.Player;
import data.pokerHand.PokerHand;
import data.pokerHand.PokerHandTable;
import game.GameState;
import game.scoring.HandScorer;
import game.scoring.PlayedHand;


/*
 * This class generates the possible hands that can result from discarding cards in hand.
 * Uses a bitmap combination generation process written by Chatgpt.
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

    private Deck deck;
    private int numDiscarded = 1;

    public PotentialHandsFinder(GameState gameState) {
        // Get the deck and current hand as Lists
        deck = gameState.getCurrDeck();
    }

    /**
     * Generates the probability table using BitSet-based combinations
     * Treats selected cards as DISCARDED
     */
    public PokerHandProbabilityTable generateProbabilityTableOfPotentialHands(GameState gameState, int discarded) {
    	deck = gameState.getCurrDeck();
        this.numDiscarded = discarded;
        int keepCount = deck.drawnCards.size() - numDiscarded;
        deck.rebuildBitmap();

        if (DEBUG) {
    		System.out.println("[DEBUG] - deck hash: " + System.identityHashCode(deck));
        	System.out.println("[DEBUG] - Deck (" + deck.cards.size() + ")");
            System.out.println("[DEBUG] - numDiscarded: " + numDiscarded);
            System.out.println("[DEBUG] - keepCount: " + keepCount);
        }

        // Create a new probability table for this analysis
        PokerHandTable pokerHandTable = gameState.getPlayer().getPokerHandTable();
        PokerHandProbabilityTable probabilityTable = new PokerHandProbabilityTable(pokerHandTable.getPokerHandNames());

        BitSet handBits = new BitSet(deck.drawnCards.size());
        BitSet keptBits = new BitSet(deck.drawnCards.size());
        for (int i = 0; i < deck.drawnCards.size(); i++) {
            handBits.set(i);
            if (!deck.drawnCards.get(i).isSelected) {  // Treat selected as discarded
                keptBits.set(i);
            }
        }

        // Generate combinations that INCLUDE the non-selected (kept) cards
        generateKeepCombinations(handBits, keptBits, keepCount, probabilityTable, pokerHandTable);

        return probabilityTable;
    }

    /**
     * Iterates through all combinations of "keepCount" cards that INCLUDE heldBits
     */
    private void generateKeepCombinations(BitSet handBits, BitSet heldBits, int keepCount, PokerHandProbabilityTable probabilityTable, PokerHandTable pokerHandTable) {
        int n = deck.drawnCards.size();
        int heldCount = heldBits.cardinality();
        int neededExtra = keepCount - heldCount;

        if (neededExtra < 0) {
            System.err.println("[WARN] Too many cards held: " + heldCount + " > keepCount " + keepCount);
            return;
        }

        // Collect indices of unheld cards
        List<Integer> unheldIndices = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!heldBits.get(i)) {
                unheldIndices.add(i);
            }
        }

        int limit = 1 << unheldIndices.size();
        for (int mask = 0; mask < limit; mask++) {
            if (Integer.bitCount(mask) == neededExtra) {
                List<PlayingCard> partialHand = new ArrayList<>();

                // Always include all held cards
                for (int i = heldBits.nextSetBit(0); i >= 0; i = heldBits.nextSetBit(i + 1)) {
                    partialHand.add(deck.drawnCards.get(i));
                }

                // Add selected unheld cards based on mask
                for (int j = 0; j < unheldIndices.size(); j++) {
                    if ((mask & (1 << j)) != 0) {
                        partialHand.add(deck.drawnCards.get(unheldIndices.get(j)));
                    }
                }

                if (DEBUG) {
                    System.out.print("[DEBUG] partial hand: ");
                    partialHand.forEach(c -> System.out.print(c.printValueAndSuit() + " "));
                    System.out.println();
                }

                generateDrawCombinations(partialHand, probabilityTable, pokerHandTable);
            }
        }
    }

    /**
     * Generates all possible draw combinations using the Deck bitmaps
     */
    private void generateDrawCombinations(List<PlayingCard> partialHand, PokerHandProbabilityTable probabilityTable, PokerHandTable pokerHandTable) {
        BitSet available = deck.getBitmap(Deck.DRAW_MAP);
        List<Integer> availableIndices = new ArrayList<>();

        for (int i = available.nextSetBit(0); i >= 0; i = available.nextSetBit(i + 1)) {
            availableIndices.add(i);
        }
        
        if (DEBUG) {
            System.out.println("[DEBUG] availableIndices.size(): " + availableIndices.size());
            System.out.println("[DEBUG] numDiscarded: " + numDiscarded);
            if (availableIndices.size() < numDiscarded) {
                System.err.println("[ERROR] Not enough available cards to draw " + numDiscarded + "!");
            }
        }

        int numHands = 0;
        int handsGenerated = 0;
        Long limit = 1L << availableIndices.size();
        for (long mask = 0; mask < limit; mask++) {
            if (Long.bitCount(mask) == numDiscarded) {
                List<PlayingCard> drawn = new ArrayList<>();
                for (int j = 0; j < availableIndices.size(); j++) {
                    if ((mask & (1 << j)) != 0)
                        drawn.add(deck.getCardByIndex(availableIndices.get(j)));
                }

                // Combine the kept + drawn cards
                List<PlayingCard> combination = new ArrayList<>(partialHand);
                System.out.print(".");
                
                combination.addAll(drawn);

                addPlayedHandsToProbabilityTable(PlayableHandsFinder.findPlayableHands(new Vector<>(combination)), probabilityTable, pokerHandTable);
                numHands++;
            }
        }
        
        System.out.println(numHands);
        System.out.println(handsGenerated);
    }

    /**
     * Scores the hands and adds the highest-scoring one to the table
     */
    private int addPlayedHandsToProbabilityTable(Vector<PlayedHand> hands, PokerHandProbabilityTable probabilityTable, PokerHandTable pokerHandTable) {
        double max = HandScorer.scoreHand(hands.get(0), new Vector<JokerCard>(0), pokerHandTable);
        PlayedHand bestHand = hands.get(0);

        for (int i = 1; i < hands.size(); i++) {
            double score = HandScorer.scoreHand(hands.get(i), HandScorer.NO_JOKERS, pokerHandTable);
            if (score > max) {
                max = score;
                bestHand = hands.get(i);
            }
        }

        probabilityTable.addScore(bestHand.getHandType(), bestHand.getScore());
        return hands.size();
    }
}
    
    
    /**
    
    
    /**
     * Default Constructor
     * @param gameState The game state to analyze
     *
    public PotentialHandsFinder(GameState gameState) {
        // Get the deck and current hand as Lists
        currDeck = new ArrayList<>(gameState.getCurrDeck().cards());
        currHand = new ArrayList<>(gameState.getCurrHand());

        // Pulls the poker hand table from the player variables
        pokerHandTable = Player.getPokerHandTable();

        // Creates a blank poker hand probability table from the pokerHandTable
        probabilityTable = new PokerHandProbabilityTable(pokerHandTable.getPokerHandNames());
    }

    
    /**
     * Generates the probability table based on possible discards
     * @param gameState GameState to analyze
     * @param discarded Number of cards discarded
     * @return PokerHandProbability table representing the results
     *
    public PokerHandProbabilityTable generateProbabilityTableOfPotentialHands(GameState gameState, int discarded) {
        this.numDiscarded = discarded;
        if (DEBUG) System.out.println("[DEBUG] - numDiscarded: " + numDiscarded);

        int keepCount = currHand.size() - numDiscarded;
        discardCombinationUtil(currHand, new ArrayList<>(), 0, keepCount);

        return probabilityTable;
    }

    
    /**
     * Recursive function which generates combinations of partial hands
     *
    private void discardCombinationUtil(List<PlayingCard> hand, List<PlayingCard> current, int start, int targetSize) {
        if (current.size() == targetSize) {
            if (DEBUG) {
                System.out.print("[DEBUG] - partial hand: ");
                current.forEach(c -> System.out.print(c.printValueAndSuit() + " "));
                System.out.println();
            }

            partialHand = new ArrayList<>(current);
            drawCombinationUtil(currDeck, new ArrayList<>(), 0, numDiscarded);
            return;
        }

        for (int i = start; i < hand.size(); i++) {
            current.add(hand.get(i));
            discardCombinationUtil(hand, current, i + 1, targetSize);
            current.remove(current.size() - 1);
        }
    }

    /**
     * Recursive function which generates all the possible hands from a given partial hand
     *
    private void drawCombinationUtil(List<PlayingCard> deck, List<PlayingCard> drawn, int start, int targetSize) {
        if (drawn.size() == targetSize) {
            List<PlayingCard> combination = new ArrayList<>(partialHand);
            combination.addAll(drawn);

            if (DEBUG) {
                //System.out.print("                     -> ");
                //combination.forEach(c -> System.out.print(c.printValueAndSuit() + " "));
                //System.out.println();
            }

            addPlayedHandsToProbabilityTable(PlayableHandsFinder.findPlayableHands(new Vector<>(combination)));
            return;
        }

        for (int i = start; i < deck.size(); i++) {
            drawn.add(deck.get(i));
            drawCombinationUtil(deck, drawn, i + 1, targetSize);
            drawn.remove(drawn.size() - 1);
        }
    }

    /**
     * Scores the hands and adds the highest-scoring one to the table
     *
    private void addPlayedHandsToProbabilityTable(Vector<PlayedHand> hands) {
        double max = HandScorer.scoreHand(hands.get(0), new Vector<JokerCard>(0), pokerHandTable);
        PlayedHand bestHand = hands.get(0);

        for (int i = 1; i < hands.size(); i++) {
            double score = HandScorer.scoreHand(hands.get(i), new Vector<JokerCard>(0), pokerHandTable);
            if (score > max) {
                max = score;
                bestHand = hands.get(i);
            }
        }

        probabilityTable.addScore(bestHand.getHandType(), bestHand.getScore());
    }
    
    */
    
	
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
	 
	public PokerHandProbabilityTable calculatePokerHandProbabilityBasedOnDiscardIndexes(int[] indexes) {
		probabilityTable = new PokerHandProbabilityTable(pokerHandTable.getPokerHandNames());
		
		if(DEBUG) System.out.println(currHand.size() + " - " + indexes.length);
		
		Vector<PlayingCard> partialHand = new Vector<PlayingCard>(0);
		
		// Debug statements printing the hand and cards at each index
		if(DEBUG) {
			System.out.print("Hand: ");
			for(int i = 0; i < currHand.size(); i++) {
				System.out.print(currHand.get(i).printValueAndSuit() + " ");
			}
			System.out.print("\nIndexes: ");
			for(int i = 0; i < indexes.length; i++) {
				System.out.print(indexes[i] + " ");
			}
			System.out.print("\n");
		}

		// Adds every card to the partial hand
		for(int i = 0; i < currHand.size(); i++) {
			partialHand.add(currHand.get(i));
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
		
		//this.partialHand = DeckUtils.convertCardVectorToArray(partialHand);
		
		//perform nCr on currDeck, n = currDeck.length, r = numToDraw
		//drawCombinationUtil(currDeck, new ArrayList<>(), 0, currDeck.size() - 1, 0, indexes.length);
		
		return probabilityTable;
	}
	
	/**
	 * Method for finding all the possible hands from a discard given a specific game state
	 * @param gameState GameState
	 * @return Vector<PlayedHand> representing the possible hands (assuming a hand will not be played
	 
	public static Vector<PlayedHand> findPotentialHands(GameState gameState){
		if(DEBUG) System.out.print("[DEBUG] - in findPotentialHands() function");
		
		// Takes the current hand, generates all possible combinations of discards, and generates all possible partial hands from those combinations
		Vector<PlayingCard> currHand = (Vector<PlayingCard>) gameState.getCurrHand();
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
*/