package data.deck;

import java.util.Vector;

import data.card.Card;
import data.card.PlayingCard;


/**
 * Class for building the default decks
 * 
 * 
 * @author Elijah Reyna
 */
public class DeckBuilder {
	// ======================== Decks ======================== //
	public static final String DEFAULT_DECK = "DEFAULT_DECK";
	public static final String ABANDONED_DECK = "ABANDONED_DECK";
	public static final String PAINTED_DECK = "PAINTED_DECK";
	
	// ======================== Card Value and Suit Information ======================== //
	public static int[] DEFAULT_VALUES = {PlayingCard.ACE, PlayingCard.TWO, PlayingCard.THREE, PlayingCard.FOUR, PlayingCard.FIVE, PlayingCard.SIX, PlayingCard.SEVEN, 
			  							  PlayingCard.EIGHT, PlayingCard.NINE, PlayingCard.TEN, PlayingCard.JACK, PlayingCard.QUEEN, PlayingCard.KING};
	public static char[] DEFAULT_SUITS = {PlayingCard.SPADES, PlayingCard.HEARTS, PlayingCard.DIAMONDS, PlayingCard.CLUBS};
	public static int[] NO_FACE_VALUES = {PlayingCard.ACE, PlayingCard.TWO, PlayingCard.THREE, PlayingCard.FOUR, PlayingCard.FIVE, PlayingCard.SIX, 
										  PlayingCard.SEVEN, PlayingCard.EIGHT, PlayingCard.NINE, PlayingCard.TEN};
	public static char[] PAINTED_SUITS = {PlayingCard.SPADES, PlayingCard.SPADES, PlayingCard.HEARTS, PlayingCard.HEARTS};
	
	public DeckBuilder() {
		
	}
	
	/**
	 * Builds the specified deck
	 * @param deckName String Name of deck to build
	 * @return Deck Generated deck
	 */
	public static Deck buildDeck(String deckName) {
		switch(deckName) {
		case ABANDONED_DECK:
			return generateDeck(NO_FACE_VALUES, DEFAULT_SUITS);
		case PAINTED_DECK:
			return generateDeck(DEFAULT_VALUES, PAINTED_SUITS);
		default:
			return generateDeck(DEFAULT_VALUES, DEFAULT_SUITS);
		}
	}
	
	
	/**
	 * Generates a deck for all possible combinations of values and suits given
	 * @param values int[] Values in the deck
	 * @param suits char[] Suits in the deck
	 * @return Deck Generated deck
	 */
	public static Deck generateDeck(int[] values, char[] suits) {
		Vector<PlayingCard> cards = new Vector<PlayingCard>(0);
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < suits.length; j++) {
				cards.add(new PlayingCard(values[i], suits[j]));
			}
		}
		return new Deck(cards);
	}
}
