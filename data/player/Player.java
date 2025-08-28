package data.player;

import java.util.Vector;

import data.card.Card;
import data.card.Joker;
import data.card.JokerCard;
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
	public Vector<JokerCard> jokers;
	
	public Player() {
		deck = DeckBuilder.buildDeck(DeckBuilder.DEFAULT_DECK);
		pokerHandTable = new PokerHandTable(DefaultPlayer.createDefaultPokerHandVector());
		BASE_CHIPS = DefaultPlayer.defaultChips();
		jokers = new Vector<JokerCard>(0);
	}
	
	public Player(Deck deck, PokerHandTable pokerHandTable, Vector<JokerCard> jokers) {
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
	
	public void addJoker(JokerCard joker) {
		jokers.add(joker);
	}
	
	public Vector<Card> getJokersAsCards(){
		Vector<Card> newJokers = new Vector<Card>(0);
		
		for(JokerCard joker : jokers) {
			newJokers.add(joker);
		}
		
		return newJokers;
	}
	
	public Vector<JokerCard> getJokersAsJokers(){
		return jokers;
	}
}
