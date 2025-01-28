package data.pokerHand;

import java.util.Vector;

import data.card.Card;
import game.scoring.PlayedHand;
import game.scoring.ValueCount;
import game.scoring.ValueCountUtils;

public interface PokerHandIdentifier {
	public static PlayedHand identifyPlayedHand(Vector<Card> cards) {
		PlayedHand playedHand = new PlayedHand();
		playedHand.setPlayedCards(cards);
		playedHand.setFlush(PokerHandIdentifier.determineFlush(cards));
		playedHand.setPokerHand(PokerHandIdentifier.determineHandType(cards));
		return playedHand;
	}
	
	public static String determineHandType(Vector<Card> cards) {
		Boolean DEBUG = false;
		
		String pokerHand = PokerHand.HIGH_CARD;
		
		Vector<ValueCount> valueCounts = ValueCountUtils.countValues(cards);
		ValueCountUtils.sortByValue(valueCounts);
		
		if(DEBUG) {
			System.out.print("\nCards: ");
			for(int i = 0; i < cards.size(); i++) {
				System.out.print(Integer.toString(cards.get(i).getValue()) + cards.get(i).getSuit() + " ");
			}
			System.out.println();
			System.out.println("Value Counts: ");
			for(int i = 0; i < valueCounts.size(); i++) {
				System.out.print(valueCounts.get(i).getValue() + ", " + valueCounts.get(i).getCount() + "  ");
			}
			System.out.println();
		}
		
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
			if(DEBUG) {System.out.print("Has no matches... ");}
			if(ValueCountUtils.hasStraight(valueCounts)) {
				if(DEBUG) {System.out.println("Is a straight.");}
				pokerHand = PokerHand.STRAIGHT;
			}else {
				if(DEBUG) System.out.println("isn't a straight... High Card");
				pokerHand = PokerHand.HIGH_CARD;
			}
		}
		
		if(isFlush(cards)) {
			if(pokerHand.equals(PokerHand.STRAIGHT)) {
				if(DEBUG) System.out.println("Straight Flush!");
				pokerHand = PokerHand.STRAIGHT_FLUSH;
			}else if(pokerHand.equals(PokerHand.FIVE_OF_A_KIND)) {
				if(DEBUG) System.out.println("Flush Five!");
				pokerHand = PokerHand.FLUSH_FIVE;
			}else if(pokerHand.equals(PokerHand.FULL_HOUSE)) {
				if(DEBUG) System.out.println("Flush House!");
				pokerHand = PokerHand.FLUSH_HOUSE;
			}else {
				if(DEBUG) System.out.println("Just a Flush!");
				pokerHand = PokerHand.FLUSH;
			}
		}
		
		return pokerHand;
	}
	
	public static boolean isFlush(Vector<Card> cards) {
		if(cards.size() < 5) {
			System.out.println("Less than five cards");
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
		//System.out.println("Is a flush!");
		return true;
	}
	
	public static char determineFlush(Vector<Card> cards) {
		if(isFlush(cards)) {
			return cards.get(0).getSuit();
		}
		return 'N';
	}
}
