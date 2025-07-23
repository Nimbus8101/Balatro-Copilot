package data.deck;

import java.util.Vector;

import copilot.deckAnalysis.PokerHandProbability;
import data.card.Card;

public class DeckTable {
	private Vector<Card> cards;
	
	char[] suits = DeckBuilder.DEFAULT_SUITS;
	int[] values = DeckBuilder.DEFAULT_VALUES;
	int[][] counts = new int[4][13];
	
	public DeckTable(Vector<Card> cards) {
		this.cards = cards;
		for(int[] row : counts) {
			for(int column : row) {
				column = 0;
			}
		}
		
		countCards(cards);
	}
	
	public void countCards(Vector<Card> cards) {
		for(Card card : cards) {
			int suitIndex = getIndexOfSuit(card.getSuit());
			int valueIndex = card.getValue() - 1;
			
			counts[suitIndex][valueIndex] += 1;
		}
	}
	
	public int getIndexOfSuit(char currSuit) {
		for(int i = 0; i < suits.length; i++) {
			if(suits[i] == currSuit) {
				return i;
			}
		}
		return -1;
	}
	
	
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
	
	private String generatePadding(int length) {
		String result = "";
		for(int i = 0; i < length; i++) {
			result += " ";
		}
		return result;
	}
}
