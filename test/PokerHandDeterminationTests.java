package test;

import java.util.Vector;

import data.card.PlayingCard;
import data.pokerHand.PokerHand;
import game.scoring.HandScorer;
import data.pokerHand.PokerHandIdentifier;

public class PokerHandDeterminationTests {
	public static void runTests() {
		System.out.println("Running Poker Hand Determiner tests...");
		if(testHighPlayingCard()
			&testPair()
			&testTwoPair()
			&testThreeOfAKind()
			&testFourOfAKind() 
			&testFiveOfAKind()
			&testFullHouse()
			&testStraight() 
			&testFlush() 
			&testStraightFlush()
			&testFlushHouse()
			&testFlushFive())
		{
			System.out.println("Poker Hand Determiner tests all passed!");
		}else {
			System.out.println("Poker Hand Determiner tests complete, errors found");
		}
		
	}
	
	public static boolean testHighPlayingCard() {
		Vector<PlayingCard> cards = new Vector<PlayingCard>(0);
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(6, PlayingCard.CLUBS));
		cards.add(new PlayingCard(3, PlayingCard.HEARTS));
		cards.add(new PlayingCard(2, PlayingCard.CLUBS));
		cards.add(new PlayingCard(4, PlayingCard.CLUBS));
		
		String handType = PokerHandIdentifier.determineHandType(cards);
		
		if(handType.equals(PokerHand.HIGH_CARD)) {
			System.out.println("   " + PokerHand.HIGH_CARD + " test passed");
			return true;
		}else {
			System.out.print("   " + PokerHand.HIGH_CARD + " test failed, got " + handType);
		}
		return false;
	}
	
	public static boolean testPair() {
		Vector<PlayingCard> cards = new Vector<PlayingCard>(0);
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(3, PlayingCard.HEARTS));
		cards.add(new PlayingCard(2, PlayingCard.CLUBS));
		cards.add(new PlayingCard(4, PlayingCard.CLUBS));
		
		String handType = PokerHandIdentifier.determineHandType(cards);
		
		if(handType.equals(PokerHand.PAIR)) {
			System.out.println("   " + PokerHand.PAIR + " test passed");
			return true;
		}
		System.out.println("   " + PokerHand.PAIR + " test failed, got " + handType);
		return false;
	}
	
	public static boolean testTwoPair() {
		Vector<PlayingCard> cards = new Vector<PlayingCard>(0);
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(3, PlayingCard.HEARTS));
		cards.add(new PlayingCard(3, PlayingCard.CLUBS));
		cards.add(new PlayingCard(4, PlayingCard.CLUBS));
		
		String handType = PokerHandIdentifier.determineHandType(cards);
		
		if(handType.equals(PokerHand.TWO_PAIR)) {
			System.out.println("   " + PokerHand.TWO_PAIR + " test passed");
			return true;
		}
		System.out.println("   " + PokerHand.TWO_PAIR + " test failed, got " + handType);
		return false;
	}
	
	public static boolean testThreeOfAKind() {
		Vector<PlayingCard> cards = new Vector<PlayingCard>(0);
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(7, PlayingCard.HEARTS));
		cards.add(new PlayingCard(2, PlayingCard.CLUBS));
		cards.add(new PlayingCard(4, PlayingCard.CLUBS));
		
		String handType = PokerHandIdentifier.determineHandType(cards);
		
		if(handType.equals(PokerHand.THREE_OF_A_KIND)) {
			System.out.println("   " + PokerHand.THREE_OF_A_KIND + " test passed");
			return true;
		}
		System.out.println("   " + PokerHand.THREE_OF_A_KIND + " test failed, got " + handType);
		return false;
	}
	
	public static boolean testFourOfAKind() {
		Vector<PlayingCard> cards = new Vector<PlayingCard>(0);
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(7, PlayingCard.HEARTS));
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(4, PlayingCard.CLUBS));
		
		String handType = PokerHandIdentifier.determineHandType(cards);
		
		if(handType.equals(PokerHand.FOUR_OF_A_KIND)) {
			System.out.println("   " + PokerHand.FOUR_OF_A_KIND + " test passed");
			return true;
		}
		System.out.println("   " + PokerHand.FOUR_OF_A_KIND + " test failed, got " + handType);
		return false;
	}
	
	public static boolean testFiveOfAKind() {
		Vector<PlayingCard> cards = new Vector<PlayingCard>(0);
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(7, PlayingCard.HEARTS));
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		
		String handType = PokerHandIdentifier.determineHandType(cards);
		
		if(handType.equals(PokerHand.FIVE_OF_A_KIND)) {
			System.out.println("   " + PokerHand.FIVE_OF_A_KIND + " test passed");
			return true;
		}
		System.out.println("   " + PokerHand.FIVE_OF_A_KIND + " test failed, got " + handType);
		return false;
	}
	
	public static boolean testStraight() {
		Vector<PlayingCard> cards = new Vector<PlayingCard>(0);
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(6, PlayingCard.CLUBS));
		cards.add(new PlayingCard(3, PlayingCard.HEARTS));
		cards.add(new PlayingCard(5, PlayingCard.CLUBS));
		cards.add(new PlayingCard(4, PlayingCard.CLUBS));
		
		String handType = PokerHandIdentifier.determineHandType(cards);
		
		if(handType.equals(PokerHand.STRAIGHT)) {
			System.out.println("   " + PokerHand.STRAIGHT + " test passed");
			return true;
		}
		System.out.println("   " + PokerHand.STRAIGHT + " test failed, got " + handType);
		return false;
	}
	
	public static boolean testFlush() {
		//Flush overrides high card, pair, two pair, three of a kind, and four of a kind,
		Vector<PlayingCard> cards = new Vector<PlayingCard>(0);
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(6, PlayingCard.CLUBS));
		cards.add(new PlayingCard(3, PlayingCard.CLUBS));
		cards.add(new PlayingCard(2, PlayingCard.CLUBS));
		cards.add(new PlayingCard(4, PlayingCard.CLUBS));
		
		String handType = PokerHandIdentifier.determineHandType(cards);
		
		if(!handType.equals(PokerHand.FLUSH)) {
			System.out.println("   " + PokerHand.FLUSH + " test [High PlayingCard] failed, got " + handType);
			return false;
		} 
		
		cards.set(1, new PlayingCard(7, PlayingCard.CLUBS));
		handType = PokerHandIdentifier.determineHandType(cards);
		if(!handType.equals(PokerHand.FLUSH)) {
			System.out.println("   " + PokerHand.FLUSH + " test [Pair] failed, got " + handType);
			return false;
		}

		cards.set(3, new PlayingCard(3, PlayingCard.CLUBS));
		handType = PokerHandIdentifier.determineHandType(cards);
		if(!handType.equals(PokerHand.FLUSH)) {
			System.out.println("   " + PokerHand.STRAIGHT + " test [Two Pair] failed, got " + handType);
			return false;
		}

		cards.set(3, new PlayingCard(7, PlayingCard.CLUBS));
		handType = PokerHandIdentifier.determineHandType(cards);
		if(!handType.equals(PokerHand.FLUSH)) {
			System.out.println("   " + PokerHand.FLUSH + " test [Three of a Kind] failed, got " + handType);
			return false;
		}

		cards.set(2, new PlayingCard(7, PlayingCard.CLUBS));
		handType = PokerHandIdentifier.determineHandType(cards);
		if(!handType.equals(PokerHand.FLUSH)) {
			System.out.println("   " + PokerHand.FLUSH + " test [Four of a Kind] failed, got " + handType);
			return false;
		}
		
		System.out.println("   " + PokerHand.FLUSH + " tests passed");
		return true;
	}
	
	public static boolean testStraightFlush() {
		Vector<PlayingCard> cards = new Vector<PlayingCard>(0);
		cards.add(new PlayingCard(6, PlayingCard.CLUBS));
		cards.add(new PlayingCard(5, PlayingCard.CLUBS));
		cards.add(new PlayingCard(4, PlayingCard.CLUBS));
		cards.add(new PlayingCard(3, PlayingCard.CLUBS));
		cards.add(new PlayingCard(2, PlayingCard.CLUBS));
		
		String handType = PokerHandIdentifier.determineHandType(cards);
		
		if(handType.equals(PokerHand.STRAIGHT_FLUSH)) {
			System.out.println("   " + PokerHand.STRAIGHT_FLUSH + " test passed");
			return true;
		}
		System.out.println("   " + PokerHand.STRAIGHT_FLUSH + " test failed, got " + handType);
		return false;
	}
	
	public static boolean testFlushFive() {
		Vector<PlayingCard> cards = new Vector<PlayingCard>(0);
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		
		String handType = PokerHandIdentifier.determineHandType(cards);
		
		if(handType.equals(PokerHand.FLUSH_FIVE)) {
			System.out.println("   " + PokerHand.FLUSH_FIVE + " test passed");
			return true;
		}
		System.out.println("   " + PokerHand.FLUSH_FIVE + " test failed, got " + handType);
		return false;
	}
	
	public static boolean testFlushHouse() {
		Vector<PlayingCard> cards = new Vector<PlayingCard>(0);
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(2, PlayingCard.CLUBS));
		cards.add(new PlayingCard(2, PlayingCard.CLUBS));
		
		String handType = PokerHandIdentifier.determineHandType(cards);
		
		if(handType.equals(PokerHand.FLUSH_HOUSE)) {
			System.out.println("   " + PokerHand.FLUSH_HOUSE + " test passed");
			return true;
		}
		System.out.println("   " + PokerHand.FLUSH_HOUSE + " test failed, got " + handType);
		return false;
	}
	
	public static boolean testFullHouse() {
		Vector<PlayingCard> cards = new Vector<PlayingCard>(0);
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(7, PlayingCard.CLUBS));
		cards.add(new PlayingCard(7, PlayingCard.HEARTS));
		cards.add(new PlayingCard(2, PlayingCard.CLUBS));
		cards.add(new PlayingCard(2, PlayingCard.CLUBS));
		
		String handType = PokerHandIdentifier.determineHandType(cards);
		
		if(handType.equals(PokerHand.FULL_HOUSE)) {
			System.out.println("   " + PokerHand.FULL_HOUSE + " test passed");
			return true;
		}
		System.out.println("   " + PokerHand.FULL_HOUSE + " test failed, got " + handType);
		return false;
	}
	
	
}
