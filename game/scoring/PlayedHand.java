package game.scoring;

import java.util.Vector;

import data.card.Card;
import data.deck.DeckUtils;
import data.pokerHand.PokerHand;

public class PlayedHand implements HandScorer{
	String pokerHand;
	private char flush;
	Vector<Card> playedCards;
	Vector<Card> heldCards;
	
	int chips = 0;
	double mult = 0.0;
	double finalScore;
		
	public PlayedHand() {
		
	}
	
	public void setPokerHand(String pokerHand) {
		this.pokerHand = pokerHand;
	}
	
	public void setPlayedCards(Vector<Card> cards) {
		this.playedCards = cards;
	}
	
	public Vector<Card> getPlayedCards(){
		return playedCards;
	}
	
	public void setHeldCards(Vector<Card> cards) {
		this.heldCards = cards;
	}
	
	public void setFlush(char flushChar) {
		flush = flushChar;
	}
	
	public String getHandType() {
		return pokerHand;
	}
	
	public double score() {
		finalScore = chips * mult;
		return finalScore;
	}
	
	public void setFinalScore(double finalScore) {
		this.finalScore = finalScore;
	}
	
	public void addChips(int chips) {
		this.chips += chips;
	}
	
	public void addMult(int mult) {
		this.mult += mult;
	}
	
	public void multiplyMult(double multiplyer) {
		this.mult *= multiplyer;
	}

	public String print(String buffer) {
		String result = "";
		
		result += "Cards: " + DeckUtils.printCardVector(playedCards, buffer + "   ");
		result += "Hand Type: " + pokerHand + "\n";
		result += "Score: " + String.format("%.2f", finalScore) + "\n";
		
		return result;
	}
	
	public String toString() {
		String result = "";
		String buffer = "                ";
		result += DeckUtils.printCardVector(playedCards, "");
		result += " -- " + pokerHand + buffer.substring(pokerHand.length());
		result += " = " + String.format("%.2f", finalScore) + " pts.";
		return result;
	}
	
	public double getScore() {
		return finalScore;
	}
}
