package game.scoring;

import java.util.Vector;

import data.card.Card;
import data.player.HandInfo;

public class HandScore {
	HandInfo handType;
	char flush;
	Vector<Card> cards;
	
	int score;
	
	public HandScore(Vector<Card> cards) {
		this.cards = cards;
	}
	
	public void setHandType(HandInfo handType) {
		this.handType = handType;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void setFlush(char flushChar) {
		flush = flushChar;
	}
	
	public String getHandType() {
		return handType.getName();
	}
	
	public void score(HandInfo handInfo) {
		score = handInfo.getChips() * handInfo.getMult();
	}
}
