package copilot;

import java.util.Vector;

import copilot.utils.Combination;
import data.card.Card;
import game.GameState;
import game.scoring.HandScore;

public class Copilot {

	public Copilot() {
		
	}
	
	public void analyzeGameState(GameState gamestate) {
		//Find playable hands
		
		//Analyze all possible hands that could be generated by discards
	}
	
	public Vector<HandScore> playableHands(Vector<Card> cards){
		//FIXME the initial solution to find playable hands is to try every combination of 5.
		
		Vector<HandScore> possibleHands = new Vector<HandScore>(0);
		
		Combination.printCombination(Combination.convertCardVectorToArray(cards), cards.size(), 5);
		
		return null;
	}
	
}