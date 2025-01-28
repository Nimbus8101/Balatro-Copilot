package data.card;

import java.util.Random;

public class CardPrinter {
	public static final int[] VALUES_ARRAY = {Card.ACE, Card.TWO, Card.THREE, Card.FOUR, Card.FIVE, Card.SIX, Card.SEVEN, 
											  Card.EIGHT, Card.NINE, Card.TEN, Card.JACK, Card.QUEEN, Card.KING};
	public static final char[] SUIT_ARRAY = {Card.SPADES, Card.HEARTS, Card.DIAMONDS, Card.CLUBS};
	Random rand;
	
	public CardPrinter() {
		rand = new Random();
	}
	public Card printRandomBasicCard() {
		return new Card(generateRandomValue(rand), generateRandomSuit(rand));
	}
	
	private int generateRandomValue(Random rand) {
		return VALUES_ARRAY[rand.nextInt() % VALUES_ARRAY.length];
	}
	
	private char generateRandomSuit(Random rand) {
		return SUIT_ARRAY[rand.nextInt() % SUIT_ARRAY.length];
	}
}
