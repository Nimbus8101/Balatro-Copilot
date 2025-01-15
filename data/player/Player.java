package data.player;

import java.util.Vector;

import data.card.Deck;
import data.card.Joker;

public class Player {
	public int numDiscards = 4;
	
	Deck deck;
	Vector<HandInfo> handTable;
	Vector<Joker> jokers;
	
	public Player() {
		
	}
	
	public Player(Deck deck, Vector<HandInfo> handTable, Vector<Joker> jokers) {
		this.deck = deck;
		this.handTable = handTable;
		this.jokers = jokers;
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public int getNumDiscards() {
		return numDiscards;
	}
}
