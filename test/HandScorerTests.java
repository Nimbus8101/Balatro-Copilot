package test;

import java.util.Vector;

import data.card.Card;
import data.player.DefaultPlayer;
import data.pokerHand.PokerHand;
import data.pokerHand.PokerHandTable;
import game.scoring.HandScorer;
import game.scoring.PlayedHand;

public class HandScorerTests {

    static PokerHandTable pokerHandTable;

    public static void main(String[] args) {
        pokerHandTable = DefaultPlayer.createDefaultPlayer().getPokerHandTable();
        boolean allPassed = true;

        allPassed &= runTest("Pair", 48.0, new Card[] {
            new Card(7, Card.CLUBS), new Card(7, Card.HEARTS), new Card(3, Card.HEARTS), new Card(2, Card.CLUBS), new Card(4, Card.CLUBS)
        });

        allPassed &= runTest("Three of a Kind", 153.0, new Card[] {
        	new Card(7, Card.CLUBS), new Card(7, Card.HEARTS), new Card(7, Card.HEARTS), new Card(2, Card.CLUBS), new Card(4, Card.CLUBS)
        });

        allPassed &= runTest("Four of a Kind", 616.0, new Card[] {
        	new Card(7, Card.CLUBS), new Card(7, Card.HEARTS), new Card(7, Card.HEARTS), new Card(7, Card.CLUBS), new Card(4, Card.CLUBS)
        });

        allPassed &= runTest("Five of a Kind", 1860.0, new Card[] {
        	new Card(7, Card.CLUBS), new Card(7, Card.HEARTS), new Card(7, Card.HEARTS), new Card(7, Card.CLUBS), new Card(7, Card.CLUBS)
        });

        System.out.println("\nAll tests " + (allPassed ? "PASSED" : "FAILED"));
    }

    private static boolean runTest(String testName, double expectedScore, Card[] playedCardData) {
    	// Converts the array into a vector
        Vector<Card> playedCards = new Vector<>();
        for (Card card : playedCardData) {
            playedCards.add(card);
        }

        // Puts a card in hand
        Vector<Card> handCards = new Vector<>();
        handCards.add(new Card(1, Card.SPADES));

        // Scores the hand
        PlayedHand playedHand = new PlayedHand(playedCards, handCards);
        double actualScore = HandScorer.scoreHand(playedHand, pokerHandTable);

        // Print statements
        boolean passed = actualScore == expectedScore;
        System.out.print(padString("[" + testName + "] ", 20) + (passed ? "PASSED" : "FAILED"));
        System.out.printf("   %s Score: %.1f | Expected: %.1f\n", testName, actualScore, expectedScore);
        return passed;
    }
    
	private static String padString(String string, int totalLength) {
		int difference = totalLength - string.length();
		String pad1;
		String pad2;
		
		pad1 = generatePadding(difference / 2);

		if(difference % 2 == 1) {
			pad2 = generatePadding((difference / 2) + 1);
		}else {
			pad2 = generatePadding(difference / 2);
		}
		
		return pad1 + string + pad2;
	}
	
	private static String generatePadding(int length) {
		String result = "";
		for(int i = 0; i < length; i++) {
			result += " ";
		}
		return result;
	}
}
