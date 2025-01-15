package data.player;

import java.util.Vector;

import data.card.Card;
import data.card.Deck;
import data.card.Joker;

public interface DefaultPlayer {
	public static final int MAX_DECK_VALUE = 7;
	
	public static Player createDefaultPlayer() {
		return new Player(createDefaultDeck(), createDefaultHandInfoTable(), new Vector<Joker>(0));
	}
	
	public static Deck createDefaultDeck() {
		Vector<Card> cards = new Vector<Card>(0);
		for(int i = 2; i <= MAX_DECK_VALUE; i++) {
			cards.add(new Card(i, 'H'));
			cards.add(new Card(i, 'C'));
			cards.add(new Card(i, 'D'));
			cards.add(new Card(i, 'S'));
		}
		return new Deck(cards);
	}
	
	public static Vector<HandInfo> createDefaultHandInfoTable(){
		Vector<HandInfo> handInfo = new Vector(0);
		handInfo.add(new HandInfo(HandInfo.FLUSH_FIVE, 160, 16));
		handInfo.add(new HandInfo(HandInfo.FLUSH_HOUSE, 140, 14));
		handInfo.add(new HandInfo(HandInfo.FIVE_OF_A_KIND, 120, 12));
		handInfo.add(new HandInfo(HandInfo.ROYAL_FLUSH, 100, 8));
		handInfo.add(new HandInfo(HandInfo.STRAIGHT_FLUSH, 100, 8));
		handInfo.add(new HandInfo(HandInfo.FOUR_OF_A_KIND, 60, 7));
		handInfo.add(new HandInfo(HandInfo.FULL_HOUSE, 40, 4));
		handInfo.add(new HandInfo(HandInfo.FLUSH, 35, 4));
		handInfo.add(new HandInfo(HandInfo.STRAIGHT, 30, 4));
		handInfo.add(new HandInfo(HandInfo.THREE_OF_A_KIND, 30, 3));
		handInfo.add(new HandInfo(HandInfo.TWO_PAIR, 20, 2));
		handInfo.add(new HandInfo(HandInfo.PAIR, 10, 2));
		handInfo.add(new HandInfo(HandInfo.HIGH_CARD, 5, 1));
		
		return handInfo;
	}
}
