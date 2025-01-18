package game.scoring;

import java.util.Vector;

import data.card.Card;
import data.deck.DeckUtils;
import data.pokerHand.PokerHand;
import data.pokerHand.PokerHandTable;
import game.HandUtils;

public class HandScore implements HandUtils{
	PokerHand pokerHand;
	private char flush;
	Vector<Card> cards;
	
	int score;
	
	public HandScore() {
	}
	
	public void setPokerHand(PokerHand pokerHand) {
		this.pokerHand = pokerHand;
	}
	
	public void setHand(Vector<Card> cards) {
		this.cards = cards;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void setFlush(char flushChar) {
		flush = flushChar;
	}
	
	public String getHandType() {
		return pokerHand.getName();
	}
	
	public int score() {
		score = pokerHand.getChips() * pokerHand.getMult();
		return score;
	}

	public String print(String buffer) {
		String result = "";
		
		result += "Cards: " + DeckUtils.printCardVector(cards, buffer + "   ");
		result += "Hand Type: " + pokerHand.getName() + "\n";
		result += "Score: " + Integer.toString(score) + "\n";
		
		return result;
	}
}
