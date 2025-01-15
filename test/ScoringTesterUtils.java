package test;

import java.util.Random;
import java.util.Vector;

import data.card.Card;

public interface ScoringTesterUtils {
	public static final int LOW_VALUE = 2;
	public static final int HIGH_VALUE = 7;
	
	
	public static Vector<Card> generateRandomHand(int numCards){
		Vector<Card> cards = new Vector<Card>(0);
		
		Random rand = new Random();
		int randInt;
		
		
	}
	
	public static int generateRandomValue(Random rand) {
		return rand.nextInt(LOW_VALUE, HIGH_VALUE);
	}
	
	public static char generateRandomSuit(Random rand) {
		int suitValue = rand.nextInt(4);
		
	}
}
