package data.deck;

import java.util.Vector;

import data.card.Card;

public class DeckBuilder {
	public static final String DEFAULT_DECK = "DEFAULT_DECK";
	public static final String ABANDONED_DECK = "ABANDONED_DECK";
	public static final String PAINTED_DECK = "PAINTED_DECK";
	
	public static int[] DEFAULT_VALUES = {Card.ACE, Card.TWO, Card.THREE, Card.FOUR, Card.FIVE, Card.SIX, Card.SEVEN, 
			  							  Card.EIGHT, Card.NINE, Card.TEN, Card.JACK, Card.QUEEN, Card.KING};
	public static char[] DEFAULT_SUITS = {Card.SPADES, Card.HEARTS, Card.DIAMONDS, Card.CLUBS};
	public static int[] NO_FACE_VALUES = {Card.ACE, Card.TWO, Card.THREE, Card.FOUR, Card.FIVE, Card.SIX, 
										  Card.SEVEN, Card.EIGHT, Card.NINE, Card.TEN};
	public static char[] PAINTED_SUITS = {Card.SPADES, Card.SPADES, Card.HEARTS, Card.HEARTS};
	
	public DeckBuilder() {
		
	}
	
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
	
	public static Deck generateDeck(int[] values, char[] suits) {
		Vector<Card> cards = new Vector<Card>(0);
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < suits.length; j++) {
				cards.add(new Card(values[i], suits[j]));
			}
		}
		return new Deck(cards);
	}
}
