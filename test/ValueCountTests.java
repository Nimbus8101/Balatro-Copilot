package test;

import java.util.Vector;

import data.card.PlayingCard;
import game.scoring.ValueCount;
import game.scoring.ValueCountUtils;

public class ValueCountTests {
	public static void runTests() {
		System.out.println("Beginning ValueCount tests... ");
		if(testGetters()
		 &&testIncrement()
		 &&testAddValue()
		 &&testMatchCounting()
		 &&testHighestCount()
		){
			System.out.println("all tests passed!");
		}
	}
	
	private static boolean testGetters() {
		ValueCount test = new ValueCount(1, 1);
		if(test.getValue() != 1) {
			System.out.println("ValueCount.getValue() test failed!");
			return false;
		}else if(test.getCount() != 1) {
			System.out.println("ValueCount.getCount() test failed!");
			return false;
		}
		return true;
	}
	
	private static boolean testSetters() {
		System.out.println("ValueCountTests testSetters() function unimplemented, ValueCount set functions not implemented yet");
		return false;
	}
	
	private static boolean testIncrement() {
		ValueCount test = new ValueCount(1, 1);
		test.increment(1);
		
		if(test.getCount() != 2) {
			System.out.println("ValueCount.increment() test failed!");
			return false;
		}
		return true;
	}
	
	private static boolean testAddValue() {
		Vector<ValueCount> arr = new Vector<ValueCount>(0);
		
		ValueCountUtils.addCount(arr, 2);
		if(arr.size() != 1 || arr.get(0).getValue() != 2 || arr.get(0).getCount() != 1) {
			System.out.println("ValueCountUtils.addCount() test failed!");
			return false;
		}
		
		ValueCountUtils.addCount(arr, 2);
		if(arr.size() != 1 || arr.get(0).getValue() != 2 || arr.get(0).getCount() != 2) {
			System.out.println("ValueCountUtils.addCount() test failed!");
			return false;
		}
		
		ValueCountUtils.addCount(arr, 3);
		if(arr.size() != 2 || arr.get(0).getValue() != 2 || arr.get(0).getCount() != 2 ||
		   arr.get(1).getValue() != 3 || arr.get(1).getCount() != 1) {
			System.out.println("ValueCountUtils.addCount() test failed!");
			return false;
		}
		
		return true;
	}
	
	public static boolean testCountValues() {
		Vector<PlayingCard> cards = new Vector<PlayingCard>(0);
		cards.add(new PlayingCard(2, PlayingCard.CLUBS));
		cards.add(new PlayingCard(3, PlayingCard.CLUBS));
		cards.add(new PlayingCard(4, PlayingCard.CLUBS));
		cards.add(new PlayingCard(5, PlayingCard.CLUBS));
		cards.add(new PlayingCard(5, PlayingCard.CLUBS));
		
		Vector<ValueCount> counts = ValueCountUtils.countValues(cards);
		
		if(counts.size() != 4) {
			System.out.println("ValueCountUtils.countValues() test failed");
			return false;
		}
		
		return true;
	}
	
	public static boolean testMatchCounting() {
		Vector<PlayingCard> cards = new Vector<PlayingCard>(0);
		cards.add(new PlayingCard(2, PlayingCard.CLUBS));
		cards.add(new PlayingCard(3, PlayingCard.CLUBS));
		cards.add(new PlayingCard(4, PlayingCard.CLUBS));
		cards.add(new PlayingCard(5, PlayingCard.CLUBS));
		cards.add(new PlayingCard(5, PlayingCard.CLUBS));
		
		Vector<ValueCount> counts = ValueCountUtils.countValues(cards);
		
		if(!ValueCountUtils.hasMatches(counts)) {
			System.out.println("ValueCountUtils.hasMatches() test failed");
			return false;
		}
		
		cards.set(0, new PlayingCard(5, PlayingCard.CLUBS));
		counts = ValueCountUtils.countValues(cards);
		if(!ValueCountUtils.hasMoreThanThreeMatches(counts)) {
			System.out.println("ValueCountUtils.hasMoreThanThreeMatches() test failed");
			return false;
		}
		
		cards.set(0, new PlayingCard(3, PlayingCard.CLUBS));
		counts = ValueCountUtils.countValues(cards);
		if(ValueCountUtils.countPairs(counts) != 2) {
			System.out.println("ValueCountUtils.countPairs() test failed");
			return false;
		}
		
		return true;
	}
	
	private static boolean testHighestCount() {
		Vector<PlayingCard> cards = new Vector<PlayingCard>(0);
		cards.add(new PlayingCard(2, PlayingCard.CLUBS));
		cards.add(new PlayingCard(3, PlayingCard.CLUBS));
		cards.add(new PlayingCard(4, PlayingCard.CLUBS));
		cards.add(new PlayingCard(5, PlayingCard.CLUBS));
		cards.add(new PlayingCard(5, PlayingCard.CLUBS));
		
		Vector<ValueCount> counts = ValueCountUtils.countValues(cards);
		
		if(ValueCountUtils.highestCount(counts) != 2) {
			System.out.println("ValueCountUtils.highestCount() test [2] failed");
			return false;
		}
		
		cards.set(0, new PlayingCard(5, PlayingCard.CLUBS));
		counts = ValueCountUtils.countValues(cards);
		if(ValueCountUtils.highestCount(counts) != 3) {
			System.out.println("ValueCountUtils.highestCount() test [3] failed");
			return false;
		}
		
		cards.set(1, new PlayingCard(5, PlayingCard.CLUBS));
		counts = ValueCountUtils.countValues(cards);
		if(ValueCountUtils.highestCount(counts) != 4) {
			System.out.println("ValueCountUtils.highestCount() test [4] failed");
			return false;
		}
		
		return true;
	}
}
