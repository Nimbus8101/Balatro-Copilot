package game.scoring;

import java.util.Vector;

import data.card.Card;
import data.card.PlayingCard;
import data.deck.DeckUtils;
import data.pokerHand.PokerHand;
import data.pokerHand.PokerHandIdentifier;

public class PlayedHand implements HandScorer, PokerHandIdentifier{
	String pokerHand;
	private char flush;
	Vector<PlayingCard> playedCards;
	Vector<PlayingCard> heldCards;
	Vector<ScoreChangeValues> scoreChanges;
	
	int startingChips;
	int startingMult;
	
	int chips = 0;
	double mult = 0.0;
	double finalScore;
		
	public PlayedHand() {
		scoreChanges = new Vector<ScoreChangeValues>(0);
	}
	
	public PlayedHand(Vector<PlayingCard> playedCards, Vector<PlayingCard> heldCards) {
		this.playedCards = playedCards;
		flush = PokerHandIdentifier.determineFlush(playedCards);
		pokerHand = PokerHandIdentifier.determineHandType(playedCards);
		
		this.heldCards = heldCards;
		scoreChanges = new Vector<ScoreChangeValues>(0);
	}
	
	public void setStartingChips(int chips) {
		startingChips = chips;
	}
	
	public void setStartingMult(int mult) {
		startingMult = mult;
	}
	
	public void setPokerHand(String pokerHand) {
		this.pokerHand = pokerHand;
	}
	
	public void setPlayedCards(Vector<PlayingCard> cards) {
		this.playedCards = cards;
	}
	
	public Vector<PlayingCard> getPlayedCards(){
		return playedCards;
	}
	
	public void setHeldCards(Vector<PlayingCard> cards) {
		this.heldCards = cards;
	}
	
	public void setFlush(char flushChar) {
		flush = flushChar;
	}
	
	public String getHandType() {
		return pokerHand;
	}
	
	public double score() {
		this.chips = startingChips;
		this.mult = startingMult;
		applyChanges();
		finalScore = chips * mult;
		return finalScore;
	}
	
	public void applyChanges() {
		for(ScoreChangeValues change : scoreChanges) {
			this.chips += change.chips;
			this.mult += change.mult;
			this.mult *= change.multiplier;
		}
	}
	
	public void setFinalScore(double finalScore) {
		this.finalScore = finalScore;
	}
	
	public void addChips(int chips) {
		this.chips += chips;
	}
	
	public int getChips() {
		return this.chips;
	}
	
	public void addMult(int mult) {
		this.mult += mult;
	}
	
	public double getMult() {
		return this.mult;
	}
	
	public void multiplyMult(double multiplyer) {
		this.mult *= multiplyer;
	}
	
	public void addChange(ScoreChangeValues change) {
		System.out.println("Adding change");
		scoreChanges.add(change);
	}
	
	public Vector<ScoreChangeValues> getChanges(){
		return scoreChanges;
	}
	
	public String printChanges() {
		StringBuilder result = new StringBuilder();
		int chips = startingChips;
		int mult = startingMult;
		
		result.append("Hand Type: " + pokerHand + "\n");
		result.append("Starting Score: " + startingChips + " X " + startingMult + "\n");
		
		for(ScoreChangeValues change : scoreChanges) {
			chips += change.chips;
			mult += change.mult;
			mult *= change.multiplier;
			
			result.append("chips: +" + change.chips + " , mult: +" + change.mult + " , mult: x" + change.multiplier + " => ");
			result.append(chips + " X " + mult + "\n");
		}
		
		return result.toString();
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
