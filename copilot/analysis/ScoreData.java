package copilot.analysis;

import java.util.Vector;

import game.scoring.PlayedHand;
import game.scoring.PlayedHandVectorUtils;

public class ScoreData {
	
	//int[] handTypeCount = new int[handTypeOrder.length];
	Vector<PlayedHand> scores = new Vector<PlayedHand>(0);
	
	public ScoreData() {
		
	}
	
	public void addScore(PlayedHand score) {
		scores.add(score);
	}
	
	public void sortByScore() {
		scores = PlayedHandVectorUtils.sortByScore(scores);
	}
	
	public void sortByHandType() {
		scores = PlayedHandVectorUtils.sortByHandType(scores);
	}
}
