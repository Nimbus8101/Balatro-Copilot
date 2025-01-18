package game;

import java.util.Random;
import java.util.Vector;

import data.card.Card;
import data.deck.Deck;
import data.player.DefaultPlayer;
import data.pokerHand.PokerHand;
import data.pokerHand.PokerHandTable;
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
	
	public static HandScore scoreHand(Vector<Card> cards, PokerHandTable pokerHandTable) {
		HandScore handScore = new HandScore();
		handScore.setHand(cards);
		handScore.setFlush(determineFlush(cards));
		handScore.setPokerHand(pokerHandTable.getPokerHand(determineHandType(cards)));
		handScore.score();
		return handScore;
	}
	
	public static String determineHandType(Vector<Card> cards) {
		Boolean DEBUG = false;
		
		String pokerHand = PokerHand.HIGH_CARD;
		
		Vector<ValueCount> valueCounts = ValueCountUtils.countValues(cards);
		
		if(ValueCountUtils.hasMatches(valueCounts)) {
			if(DEBUG) System.out.print("Has matches... ");
			switch(ValueCountUtils.highestCount(valueCounts)) {
				case 2:
					if(DEBUG) System.out.print("Has a pair... ");
					switch(ValueCountUtils.countPairs(valueCounts)) {
						case 1: 
							if(DEBUG) System.out.println("Has only one pair... Pair");
							pokerHand = PokerHand.PAIR;
							break;
						case 2: 
							if(DEBUG) System.out.println("Has two pairs... Two pair");
							pokerHand = PokerHand.TWO_PAIR;
							break;
					}
					break;
				case 3:
					if(DEBUG) System.out.print("Has 3 of a kind... ");
					switch(ValueCountUtils.countPairs(valueCounts)) {
						case 0: 
							if(DEBUG) System.out.println("Has only a three of a kind... Three of a kind");
							pokerHand = PokerHand.THREE_OF_A_KIND;
							break;
						case 1: 
							if(DEBUG) System.out.println("Has a pair too... Full House");
							pokerHand = PokerHand.FULL_HOUSE;
							break;
					}
					break;
				case 4:	
					if(DEBUG) System.out.println("Has 4 of a kind.");
					pokerHand = PokerHand.FOUR_OF_A_KIND;
					break;
				case 5: 
					if(DEBUG) System.out.println("Has 5 of a kind.");
					pokerHand = PokerHand.FIVE_OF_A_KIND;
					break;
			}
		}else {
			if(DEBUG) System.out.print("Has no matches... ");
			if(ValueCountUtils.hasStraight(valueCounts)) {
				if(DEBUG) System.out.println("Is a straight.");
				pokerHand = PokerHand.STRAIGHT;
			}else {
				if(DEBUG) System.out.println("isn't a straight... High Card");
				pokerHand = PokerHand.HIGH_CARD;
			}
		}
		
		if(isFlush(cards)) {
			if(pokerHand.equals(PokerHand.STRAIGHT)) {
				if(ValueCountUtils.highestValue(valueCounts) == DefaultPlayer.MAX_DECK_VALUE) {
					pokerHand = PokerHand.ROYAL_FLUSH;
				}else {
					pokerHand = PokerHand.STRAIGHT_FLUSH;
				}
			}else if(pokerHand.equals(PokerHand.FIVE_OF_A_KIND)) {
				pokerHand = PokerHand.FLUSH_FIVE;
			}else if(pokerHand.equals(PokerHand.FULL_HOUSE)) {
				pokerHand = PokerHand.FLUSH_HOUSE;
			}else {
				pokerHand = PokerHand.FLUSH;
			}
		}
		
		return pokerHand;
	}
	
	public static boolean isFlush(Vector<Card> cards) {
		if(cards.size() < 5) {
			return false;
		}
		
		char flushType = cards.get(0).getSuit();
		//System.out.print(flushType + "...");
		for(int i = 0; i < cards.size(); i++) {
			//System.out.print(cards.get(i).getSuit() + ".");
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
	
	public static PokerHand getPokerHand(String pokerHand, Vector<PokerHand> handInfo) {
		for(int i = 0; i < handInfo.size(); i++) {
			if(handInfo.get(i).getName().equals(pokerHand)) {
				return handInfo.get(i);
			}
		}
		return null;
	}
	
}
