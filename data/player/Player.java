package data.player;

import java.util.Vector;

import data.card.Joker;
import data.deck.Deck;
import data.pokerHand.PokerHandTable;

public class Player {
	public int numDiscards = 1;
	
	Deck deck;
	PokerHandTable pokerHandTable;
	Vector<Joker> jokers;
	
	public Player() {
		
	}
	
	public Player(Deck deck, PokerHandTable pokerHandTable, Vector<Joker> jokers) {
		this.deck = deck;
		this.pokerHandTable = pokerHandTable;
		this.jokers = jokers;
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public int getNumDiscards() {
		return numDiscards;
	}
}
