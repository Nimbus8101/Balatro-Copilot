package data.player;

import java.util.Vector;

import data.card.Card;
import data.card.Joker;
import data.deck.Deck;
import data.deck.DeckBuilder;
import data.pokerHand.PokerHand;
import data.pokerHand.PokerHandTable;

public interface DefaultPlayer {
	public static final int MAX_DECK_VALUE = 7;
	
	public static Player createDefaultPlayer() {
		return new Player(DeckBuilder.buildDeck(DeckBuilder.DEFAULT_DECK), new PokerHandTable(createDefaultPokerHandVector()), new Vector<Joker>(0));
	}
	
	public static Player createTestPlayer() {
		int[] values = {2, 3, 4, 5, 6, 7, 8};
		char[] suit = {'S', 'C'};
		return new Player(DeckBuilder.generateDeck(values, suit), new PokerHandTable(createDefaultPokerHandVector()), new Vector<Joker>(0));
	}
	
	public static Vector<PokerHand> createDefaultPokerHandVector(){
		Vector<PokerHand> pokerHands = new Vector<PokerHand>(0);
		pokerHands.add(new PokerHand(PokerHand.FLUSH_FIVE, 160, 16));
		pokerHands.add(new PokerHand(PokerHand.FLUSH_HOUSE, 140, 14));
		pokerHands.add(new PokerHand(PokerHand.FIVE_OF_A_KIND, 120, 12));
		pokerHands.add(new PokerHand(PokerHand.ROYAL_FLUSH, 100, 8));
		pokerHands.add(new PokerHand(PokerHand.STRAIGHT_FLUSH, 100, 8));
		pokerHands.add(new PokerHand(PokerHand.FOUR_OF_A_KIND, 60, 7));
		pokerHands.add(new PokerHand(PokerHand.FULL_HOUSE, 40, 4));
		pokerHands.add(new PokerHand(PokerHand.FLUSH, 35, 4));
		pokerHands.add(new PokerHand(PokerHand.STRAIGHT, 30, 4));
		pokerHands.add(new PokerHand(PokerHand.THREE_OF_A_KIND, 30, 3));
		pokerHands.add(new PokerHand(PokerHand.TWO_PAIR, 20, 2));
		pokerHands.add(new PokerHand(PokerHand.PAIR, 10, 2));
		pokerHands.add(new PokerHand(PokerHand.HIGH_CARD, 5, 1));
		
		return pokerHands;
	}
}
