package game;

import java.util.Random;
import java.util.Vector;

import data.card.Card;
import data.card.Deck;
import data.player.DefaultPlayer;
import data.player.HandInfo;
import game.scoring.HandScore;
import game.scoring.ValueCount;
import game.scoring.ValueCountUtils;

public interface HandUtils extends ValueCountUtils{
	public static Vector<Card> draw(int numDraw, Deck deck){
		Vector<Card> hand = new Vector<Card>(0);
		
		for(int i = 0; i < numDraw && deck.hasNext(); i++) {
			hand.add(deck.drawNext());
		}
		return hand;
	}
	
	public static HandScore scoreHand(Vector<Card> cards, Vector<HandInfo> handTable) {
		HandScore handScore = new HandScore(cards);
		handScore.setFlush(determineFlush(cards));
		handScore.score(getHandInfo(determineHandType(cards), handTable));
		return handScore;
	}
	
	public static String determineHandType(Vector<Card> cards) {
		String handType = HandInfo.HIGH_CARD;
		
		Vector<ValueCount> valueCounts = ValueCountUtils.countValues(cards);
		
		if(ValueCountUtils.hasMatches(valueCounts)) {
			switch(ValueCountUtils.highestCount(valueCounts)) {
				case 2:
					switch(ValueCountUtils.countPairs(valueCounts)) {
						case 1: handType = HandInfo.PAIR;
						case 2: handType = HandInfo.TWO_PAIR;
					}
				case 3:
					switch(ValueCountUtils.countPairs(valueCounts)) {
						case 0: handType = HandInfo.THREE_OF_A_KIND;
						case 1: handType = HandInfo.FULL_HOUSE;
					}
				case 4:	handType = HandInfo.FOUR_OF_A_KIND;
				case 5: handType = HandInfo.FIVE_OF_A_KIND;
			}
		}else {
			if(ValueCountUtils.hasStraight(valueCounts)) {
				handType = HandInfo.STRAIGHT;
			}else {
				handType = HandInfo.HIGH_CARD;
			}
		}
		
		if(isFlush(cards)) {
			if(handType.equals(HandInfo.STRAIGHT)) {
				if(ValueCountUtils.highestValue(valueCounts) == DefaultPlayer.MAX_DECK_VALUE) {
					handType = HandInfo.ROYAL_FLUSH;
				}else {
					handType = HandInfo.STRAIGHT_FLUSH;
				}
			}else if(handType.equals(HandInfo.FIVE_OF_A_KIND)) {
				handType = HandInfo.FLUSH_FIVE;
			}else if(handType.equals(HandInfo.FULL_HOUSE)) {
				handType = HandInfo.FLUSH_HOUSE;
			}
		}
		
		return handType;
	}
	
	public static boolean isFlush(Vector<Card> cards) {
		char flushType = cards.get(0).getSuit();
		for(int i = 0; i < cards.size(); i++) {
			if(!(cards.get(i).getSuit() == flushType)) {
				return false;
			}
		}
		return true;
	}
	
	public static char determineFlush(Vector<Card> cards) {
		if(isFlush(cards)) {
			return cards.get(0).getSuit();
		}
		return 'N';
	}
	
	public static HandInfo getHandInfo(String handType, Vector<HandInfo> handInfo) {
		for(int i = 0; i < handInfo.size(); i++) {
			if(handInfo.get(i).getName().equals(handType)) {
				return handInfo.get(i);
			}
		}
		return null;
	}
	
}
