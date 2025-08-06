package data.player;

import java.util.Vector;

import data.card.Joker;
import data.deck.Deck;
import data.deck.DeckBuilder;
import data.pokerHand.PokerHandTable;

public class Player {
	public int numDiscards = 1;
	
	public Deck deck;
	PokerHandTable pokerHandTable;
	Vector<Joker> jokers;
	
	public Player() {
		deck = DeckBuilder.buildDeck(DeckBuilder.DEFAULT_DECK);
		pokerHandTable = new PokerHandTable(DefaultPlayer.createDefaultPokerHandVector());
	}
	
	public Player(Deck deck, PokerHandTable pokerHandTable, Vector<Joker> jokers) {
		this.deck = deck;
		this.pokerHandTable = pokerHandTable;
		this.jokers = jokers;
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
	
}
