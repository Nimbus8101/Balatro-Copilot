package data.player;

import java.util.Vector;

import data.card.Joker;
import data.deck.Deck;
import data.deck.DeckBuilder;
import data.pokerHand.PokerHandTable;

public class Player {
	private final int[] BASE_CHIPS;
	
	public int numDiscards = 1;
	public int numHands = 4;
	public int money;
	
	public Deck deck;
	PokerHandTable pokerHandTable;
	Vector<Joker> jokers;
	
	public Player() {
		deck = DeckBuilder.buildDeck(DeckBuilder.DEFAULT_DECK);
		pokerHandTable = new PokerHandTable(DefaultPlayer.createDefaultPokerHandVector());
		BASE_CHIPS = DefaultPlayer.defaultChips();
	}
	
	public Player(Deck deck, PokerHandTable pokerHandTable, Vector<Joker> jokers) {
		this.deck = deck;
		this.pokerHandTable = pokerHandTable;
		this.jokers = jokers;
		BASE_CHIPS = DefaultPlayer.defaultChips();
	}
	
	public PokerHandTable getPokerHandTable() {
		return pokerHandTable;
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public int getNumDiscards() {
		return numDiscards;
	}
	
	public void shuffleDeck() {
		this.deck.shuffle();
	}

	public int getBaseChips(int index) {
		return BASE_CHIPS[index];
	}

	public int getNumHands() {
		return numHands;
	}
	
}
