package data.deck;

import java.util.Vector;

import copilot.deckAnalysis.PokerHandProbability;
import data.card.Card;
import data.card.PlayingCard;


/**
 * Class for displaying deck information as a table
 * 
 * @author Elijah Reyna
 */
public class DeckTable {
	private Vector<PlayingCard> cards;
	
	char[] suits = DeckBuilder.DEFAULT_SUITS;
	int[] values = DeckBuilder.DEFAULT_VALUES;
	int[][] counts = new int[4][13];
	
	
	/**
	 * Constructor for building the table
	 * @param cards Cards to generate a table from
	 */
	public DeckTable(Vector<PlayingCard> cards) {
		this.cards = cards;
		for(int[] row : counts) {
			for(int column : row) {
				column = 0;
			}
		}
		
		countCards(cards);
	}
	
	
	/**
	 * Counts the cards
	 * @param cards Vector<PlayingCard> Cards to count
	 */
	public void countCards(Vector<PlayingCard> cards) {
		for(PlayingCard card : cards) {
			int suitIndex = getIndexOfSuit(card.getSuit());
			int valueIndex = card.getValue() - 1;
			
			counts[suitIndex][valueIndex] += 1;
		}
	}
	
	
	/**
	 * Gets the index of the given suit (for putting it in the correct array location
	 * @param currSuit char Suit to find the index of
	 * @return int The index of the suit in the suits array
	 */
	public int getIndexOfSuit(char currSuit) {
		for(int i = 0; i < suits.length; i++) {
			if(suits[i] == currSuit) {
				return i;
			}
		}
		return -1;
	}
	
	
	/**
	 * Generates a String representation of the Deck Table
	 * @return String representing the Deck Table
	 */
	public String printDeckTable() {
		int columnPad = 3;
		
		String result = "|-----------------------Deck Table-----------------------|\n";
		result += "|   ";
		for(int i = 0; i < values.length; i++) {
			result += "|" + padString(Integer.toString(values[i]), columnPad);
		}
		result += "|\n";
		
		for(int i = 0; i < suits.length; i++) {
			result += 		"|" + padString(Character.toString(suits[i]), 3);
			for(int column : counts[i]) {
				result += "|" + padString(Integer.toString(column), columnPad);
			}
			result += "|\n";
		}
		result +=		"|-------------------------------------------------------|\n";
		return result;
	}
	
	/**
	 * Utility function for padding a String 
	 * @param string
	 * @param totalLength
	 * @return
	 */
	private String padString(String string, int totalLength) {
		int difference = totalLength - string.length();
		String pad1;
		String pad2;
		
		pad1 = generatePadding(difference / 2);

		if(difference % 2 == 1) {
			pad2 = generatePadding((difference / 2) + 1);
		}else {
			pad2 = generatePadding(difference / 2);
		}
		
		return pad1 + string + pad2;
	}
	
	/**
	 * Utility function for generating padding for the padString() method
	 * @param length
	 * @return
	 */
	private String generatePadding(int length) {
		String result = "";
		for(int i = 0; i < length; i++) {
			result += " ";
		}
		return result;
	}
}
