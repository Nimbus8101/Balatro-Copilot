package test;

import data.player.DefaultPlayer;
import data.pokerHand.PokerHandTable;
import game.HandUtils;
import game.scoring.HandScore;

public class TestDriver {
	public static void main(String[] args) {
		generateAndScoreHands(10);
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
