package test;

import java.util.Random;
import java.util.Vector;

import data.card.Card;
import data.deck.DeckUtils;

public interface ScoringTesterUtils {
	public static final int LOW_VALUE = 2;
	public static final int HIGH_VALUE = 7;
	
	
	public static Vector<Card> generateRandomHand(int numCards){
		Vector<Card> cards = new Vector<Card>(0);
		
		Random rand = new Random();
		
		for(int i = 0; i < numCards; i++) {
			cards.add(new Card(generateRandomValue(rand), generateRandomSuit(rand)));
		}
		DeckUtils.printCardVector(cards, "   ");
		
		return cards;
	}
	
	public static int generateRandomValue(Random rand) {
		return rand.nextInt(LOW_VALUE, HIGH_VALUE);
	}
	
	public static char generateRandomSuit(Random rand) {
		switch(rand.nextInt(4)) {
		case 0:
			return 'S';
		case 1:
			return 'H';
		case 2:
			return 'C';
		case 3:
			return 'D';
		default:
			return 'N';
		}
	}
}
