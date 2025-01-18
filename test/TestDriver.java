package test;

import java.util.Vector;

import data.player.DefaultPlayer;
import data.pokerHand.PokerHandTable;
import game.HandUtils;
import game.scoring.HandScore;
import game.scoring.ScorePrinter;

public class TestDriver {
	public static void main(String[] args) {
		generateAndScoreHands(10);
		testScorePrinter();
	}
	
	public static void testScorePrinter() {
		Vector<HandScore> scores = new Vector<HandScore>(0);
		for(int i = 0; i < 100; i++) {
			HandScore a = new HandScore();
			a.setScore(i);
			scores.add(a);
		}
		System.out.println(ScorePrinter.graphScores(scores));
		
		scores = new Vector<HandScore>(0);
		for(int i = 0; i < 100; i++) {
			HandScore a = new HandScore();
			a.setScore(i);
			scores.add(a);
			for(int j = i / 10; j > 0; j--) {
				a = new HandScore();
				a.setScore(i);
				scores.add(a);
			}
		}
		
		System.out.println(ScorePrinter.graphScores(scores));
	}
	
	public static boolean generateAndScoreHands(int numHands) {
		HandScore handScore;
		PokerHandTable pokerHandTable = new PokerHandTable(DefaultPlayer.createDefaultPokerHandVector());
		
		for(int i = 0; i < numHands; i++) {
			handScore = HandUtils.scoreHand(ScoringTesterUtils.generateRandomHand(5), pokerHandTable);
			System.out.println(handScore.print(""));
		}
		
		return false;
	}
	
}
