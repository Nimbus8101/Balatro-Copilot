package copilot.analysis;

import java.util.Vector;

<<<<<<< Updated upstream
import data.pokerHand.PokerHand;
=======
>>>>>>> Stashed changes
import game.scoring.HandScore;

public class ScoreData {
	String[] handTypeOrder = {PokerHand.HIGH_CARD, PokerHand.PAIR, PokerHand.TWO_PAIR, PokerHand.THREE_OF_A_KIND,
			  PokerHand.FLUSH, PokerHand.STRAIGHT, PokerHand.FULL_HOUSE, PokerHand.FOUR_OF_A_KIND, 
			  PokerHand.STRAIGHT_FLUSH, PokerHand.ROYAL_FLUSH, PokerHand.FLUSH_HOUSE, PokerHand.FLUSH_FIVE};
	
	int[] handTypeCount = new int[handTypeOrder.length];
	Vector<HandScore> scores = new Vector<HandScore>(0);
	
	public ScoreData() {
		
	}
	
	public void addScore(HandScore score) {
		scores.add(score);
	}
	
	public void sortByScore() {
		Vector<HandScore> sortedScores = new Vector<HandScore>(0);
		int lowIndex;
		
		while(scores.size() > 0) {
			lowIndex = 0;
			int lowScore = scores.get(0).getScore();
			for(int i = 1; i < scores.size(); i++) {
				if(scores.get(i).getScore() < lowScore) {
					lowScore = scores.get(i).getScore();
					lowIndex = i;
				}
			}
			sortedScores.add(scores.remove(lowIndex));
		}
		
		scores = sortedScores;
	}
	
	public void sortByHandType() {
		Vector<HandScore> sortedScores = new Vector<HandScore>(0);
		
		for(int i = 0; i < handTypeOrder.length; i++) {
			handTypeCount[i] = 0;
			for(int j = 0; j < scores.size(); j++) {
				if(scores.get(j).getHandType().equals(handTypeOrder[i])) {
					sortedScores.add(scores.remove(j));
					handTypeCount[i] += 1;
				}
			}
		}
		scores = sortedScores;
	}
}
