package data.card;

import java.util.Random;


/**
 * Class for creating PlayingCard instances
 * 
 * Functionality:
 * - Generate a random Playing Card
 * 
 * Will likely expand to generate cards with random modifiers and seals as well
 * Potentially including weights for generation
 * 
 * @author Elijah Reyna
 */
public class CardPrinter {
	public static final int[] VALUES_ARRAY = {PlayingCard.ACE, PlayingCard.TWO, PlayingCard.THREE, PlayingCard.FOUR, PlayingCard.FIVE, PlayingCard.SIX, PlayingCard.SEVEN, 
											  PlayingCard.EIGHT, PlayingCard.NINE, PlayingCard.TEN, PlayingCard.JACK, PlayingCard.QUEEN, PlayingCard.KING};
	public static final char[] SUIT_ARRAY = {PlayingCard.SPADES, PlayingCard.HEARTS, PlayingCard.DIAMONDS, PlayingCard.CLUBS};
	Random rand;
	
	public CardPrinter() {
		rand = new Random();
	}
	
	public PlayingCard printRandomBasicCard() {
		return new PlayingCard(generateRandomValue(rand), generateRandomSuit(rand));
	}
	
	private int generateRandomValue(Random rand) {
		return VALUES_ARRAY[rand.nextInt() % VALUES_ARRAY.length];
	}
	
	private char generateRandomSuit(Random rand) {
		return SUIT_ARRAY[rand.nextInt() % SUIT_ARRAY.length];
	}
}
