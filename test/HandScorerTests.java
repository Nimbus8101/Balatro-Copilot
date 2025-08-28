package test;

import java.util.Vector;

import data.card.JokerCard;
import data.card.PlayingCard;
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

        allPassed &= runTest("Pair", 48.0, new PlayingCard[] {
            new PlayingCard(7, PlayingCard.CLUBS), new PlayingCard(7, PlayingCard.HEARTS), new PlayingCard(3, PlayingCard.HEARTS), new PlayingCard(2, PlayingCard.CLUBS), new PlayingCard(4, PlayingCard.CLUBS)
        });

        allPassed &= runTest("Three of a Kind", 153.0, new PlayingCard[] {
        	new PlayingCard(7, PlayingCard.CLUBS), new PlayingCard(7, PlayingCard.HEARTS), new PlayingCard(7, PlayingCard.HEARTS), new PlayingCard(2, PlayingCard.CLUBS), new PlayingCard(4, PlayingCard.CLUBS)
        });

        allPassed &= runTest("Four of a Kind", 616.0, new PlayingCard[] {
        	new PlayingCard(7, PlayingCard.CLUBS), new PlayingCard(7, PlayingCard.HEARTS), new PlayingCard(7, PlayingCard.HEARTS), new PlayingCard(7, PlayingCard.CLUBS), new PlayingCard(4, PlayingCard.CLUBS)
        });

        allPassed &= runTest("Five of a Kind", 1860.0, new PlayingCard[] {
        	new PlayingCard(7, PlayingCard.CLUBS), new PlayingCard(7, PlayingCard.HEARTS), new PlayingCard(7, PlayingCard.HEARTS), new PlayingCard(7, PlayingCard.CLUBS), new PlayingCard(7, PlayingCard.CLUBS)
        });

        System.out.println("\nAll tests " + (allPassed ? "PASSED" : "FAILED"));
    }

    private static boolean runTest(String testName, double expectedScore, PlayingCard[] playedPlayingCardData) {
    	// Converts the array into a vector
        Vector<PlayingCard> playedPlayingCards = new Vector<>();
        for (PlayingCard card : playedPlayingCardData) {
            playedPlayingCards.add(card);
        }

        // Puts a card in hand
        Vector<PlayingCard> handPlayingCards = new Vector<>();
        handPlayingCards.add(new PlayingCard(1, PlayingCard.SPADES));

        // Scores the hand
        PlayedHand playedHand = new PlayedHand(playedPlayingCards, handPlayingCards);
        double actualScore = HandScorer.scoreHand(playedHand, new Vector<JokerCard>(0), pokerHandTable);

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
