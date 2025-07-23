package test;

import java.util.Vector;

import data.player.DefaultPlayer;
import data.pokerHand.PokerHandTable;
import game.scoring.HandScorer;
import game.scoring.PlayedHand;
import game.scoring.ScorePrinter;

public class TestDriver {
	public static void main(String[] args) {
		//generateAndScoreHands(10);
		//testScorePrinter();
		//PokerHandDeterminationTests.runTests();
	}
	
	public static void testScorePrinter() {
		Vector<PlayedHand> scores = new Vector<PlayedHand>(0);
		for(int i = 0; i < 100; i++) {
			PlayedHand a = new PlayedHand();
			a.setFinalScore(i);
			scores.add(a);
		}
		System.out.println(ScorePrinter.graphScores(scores));
		
		scores = new Vector<PlayedHand>(0);
		for(int i = 0; i < 100; i++) {
			PlayedHand a = new PlayedHand();
			a.setFinalScore(i);
			scores.add(a);
			for(int j = i / 10; j > 0; j--) {
				a = new PlayedHand();
				a.setFinalScore(i);
				scores.add(a);
			}
		}
		
		System.out.println(ScorePrinter.graphScores(scores));
	}
	
	public static boolean generateAndScoreHands(int numHands) {
		PlayedHand playedHand;
		PokerHandTable pokerHandTable = new PokerHandTable(DefaultPlayer.createDefaultPokerHandVector());
		
		for(int i = 0; i < numHands; i++) {
			playedHand = HandScorer.scorePlayedHand(ScoringTesterUtils.generateRandomHand(5), pokerHandTable);
			System.out.println(playedHand.print(""));
		}
		
		return false;
	}
	
}
