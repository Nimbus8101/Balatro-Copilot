package game.scoring;

import java.util.Vector;

import data.pokerHand.PokerHand;


/**
 * Utility class for operating on a Vector of PlayedHand objects
 * Functionality:
 * - Sorting by Score and Hand Type
 * 
 * @author Elijah Reyna
 */
public interface PlayedHandVectorUtils {
	String[] handTypeOrder = {PokerHand.HIGH_CARD, PokerHand.PAIR, PokerHand.TWO_PAIR, PokerHand.THREE_OF_A_KIND,
			  PokerHand.FLUSH, PokerHand.STRAIGHT, PokerHand.FULL_HOUSE, PokerHand.FOUR_OF_A_KIND, 
			  PokerHand.STRAIGHT_FLUSH, PokerHand.ROYAL_FLUSH, PokerHand.FLUSH_HOUSE, PokerHand.FLUSH_FIVE};
	
	public static Vector<PlayedHand> sortByScore(Vector<PlayedHand> scores) {
		Vector<PlayedHand> sortedScores = new Vector<PlayedHand>(0);
		int lowIndex;
		
		//FIXME could try a faster sorting algorithm
		while(scores.size() > 0) {
			lowIndex = 0;
			double lowScore = scores.get(0).getScore();
			for(int i = 1; i < scores.size(); i++) {
				if(scores.get(i).getScore() < lowScore) {
					lowScore = scores.get(i).getScore();
					lowIndex = i;
				}
			}
			sortedScores.add(scores.remove(lowIndex));
		}
		
		return sortedScores;
	}
	
	public static Vector<PlayedHand> sortByHandType(Vector<PlayedHand> scores) {
		Vector<PlayedHand> sortedScores = new Vector<PlayedHand>(0);
		
		for(int i = 0; i < handTypeOrder.length; i++) {
			for(int j = 0; j < scores.size(); j++) {
				if(scores.get(j).getHandType().equals(handTypeOrder[i])) {
					sortedScores.add(scores.remove(j));
				}
			}
		}
		return sortedScores;
	}
}
