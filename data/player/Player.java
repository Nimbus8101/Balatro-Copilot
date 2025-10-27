package data.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import data.card.Card;
import data.card.Joker;
import data.card.JokerCard;
import data.deck.Deck;
import data.deck.DeckBuilder;
import data.pokerHand.PokerHandTable;


/**
 * Class for storing variables regarding the Player
 * 
 * @author Elijah Reyna
 */
public class Player {
	// ============ Player Variables ============ //
	private static int[] BASE_CHIPS;
	
	public int numDiscards = 3;
	public int numHands = 4;
	public int money;
	
	public Deck deck;
	public PokerHandTable pokerHandTable;
	public List<JokerCard> jokers;
	
	
	/**
	 * Default constructor
	 */
	public Player() {
		deck = DeckBuilder.buildDeck(DeckBuilder.DEFAULT_DECK);
		pokerHandTable = new PokerHandTable(DefaultPlayer.createDefaultPokerHandVector());
		BASE_CHIPS = DefaultPlayer.defaultChips();
		jokers = new ArrayList<>(0);
	}
	
	/**
	 * Parameterized constructor or creating a Player instance
	 * @param deck
	 * @param pokerHandTable
	 * @param jokers
	 */
	public Player(Deck deck, PokerHandTable pokerHandTable, Vector<JokerCard> jokers) {
		this.deck = deck;
		this.pokerHandTable = pokerHandTable;
		this.jokers = jokers;
		BASE_CHIPS = DefaultPlayer.defaultChips();
	}
	
	
	/**
	 * Method for setting the variables to those of a Default Player
	 */
	public void initializeDefaultPlayer() {
		deck = DeckBuilder.buildDeck(DeckBuilder.DEFAULT_DECK);
		pokerHandTable = new PokerHandTable(DefaultPlayer.createDefaultPokerHandVector());
		BASE_CHIPS = DefaultPlayer.defaultChips();
		jokers = new ArrayList<>(0);
	}
	
	
	// ==================== Utility Functions ==================== //
	public void shuffleDeck() {
		deck.shuffle();
	}
	
	
	// ==================== Adders and Removers ==================== //
	public void addJoker(JokerCard joker) {
		jokers.add(joker);
	}
	
	
	// ==================== Getters and Setters ==================== //
	public PokerHandTable getPokerHandTable() {
		return pokerHandTable;
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public int getNumDiscards() {
		return numDiscards;
	}

	public int getBaseChips(int index) {
		return BASE_CHIPS[index];
	}

	public int getNumHands() {
		return numHands;
	}
	
	public List<Card> getJokersAsCards(){
		List<Card> newJokers = new ArrayList<>(0);
		
		for(JokerCard joker : jokers) {
			newJokers.add(joker);
		}
		
		return newJokers;
	}
	
	public List<JokerCard> getJokersAsJokers(){
		return jokers;
	}
}
