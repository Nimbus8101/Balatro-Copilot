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
	
	public static int numDiscards = 3;
	public static int numHands = 4;
	public static int money;
	
	public static Deck deck;
	public static PokerHandTable pokerHandTable;
	public static List<JokerCard> jokers;
	
	
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
		Player.deck = deck;
		Player.pokerHandTable = pokerHandTable;
		Player.jokers = jokers;
		BASE_CHIPS = DefaultPlayer.defaultChips();
	}
	
	
	/**
	 * Method for setting the variables to those of a Default Player
	 */
	public static void initializeDefaultPlayer() {
		deck = DeckBuilder.buildDeck(DeckBuilder.DEFAULT_DECK);
		pokerHandTable = new PokerHandTable(DefaultPlayer.createDefaultPokerHandVector());
		BASE_CHIPS = DefaultPlayer.defaultChips();
		jokers = new ArrayList<>(0);
	}
	
	
	// ==================== Utility Functions ==================== //
	public static void shuffleDeck() {
		deck.shuffle();
	}
	
	
	// ==================== Adders and Removers ==================== //
	public static void addJoker(JokerCard joker) {
		jokers.add(joker);
	}
	
	
	// ==================== Getters and Setters ==================== //
	public static PokerHandTable getPokerHandTable() {
		return pokerHandTable;
	}
	
	public static Deck getDeck() {
		return deck;
	}
	
	public static int getNumDiscards() {
		return numDiscards;
	}

	public static int getBaseChips(int index) {
		return BASE_CHIPS[index];
	}

	public static int getNumHands() {
		return numHands;
	}
	
	public static List<Card> getJokersAsCards(){
		List<Card> newJokers = new ArrayList<>(0);
		
		for(JokerCard joker : jokers) {
			newJokers.add(joker);
		}
		
		return newJokers;
	}
	
	public static List<JokerCard> getJokersAsJokers(){
		return jokers;
	}
}
