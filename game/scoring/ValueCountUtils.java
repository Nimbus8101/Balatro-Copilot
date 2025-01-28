package game.scoring;

import java.util.Vector;

import data.card.Card;

public interface ValueCountUtils {
	public static Vector<ValueCount> countValues(Vector<Card> cards){
		Vector<ValueCount> valueCounts = new Vector<ValueCount>(0);
		for(int i = 0; i < cards.size(); i++) {
			addCount(valueCounts, cards.get(i).getValue());
		}
		return valueCounts;
	}
	
	public static void addCount(Vector<ValueCount> valueCounts, int value) {
		for(int i = 0; i < valueCounts.size(); i++) {
			if(valueCounts.get(i).getValue() == value) {
				valueCounts.get(i).increment(1);
				return;
			}
		}
		valueCounts.add(new ValueCount(value, 1));
	}
	
	public static boolean hasMatches(Vector<ValueCount> valueCounts) {
		for(int i = 0; i < valueCounts.size(); i++) {
			if(valueCounts.get(i).getCount() > 1) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasMoreThanThreeMatches(Vector<ValueCount> valueCounts) {
		for(int i = 0; i < valueCounts.size(); i++) {
			if(valueCounts.get(i).getCount() > 2) {
				return true;
			}
		}
		return false;
	}
	
	public static int highestCount(Vector<ValueCount> valueCounts) {
		int high = valueCounts.get(0).getCount();
		for(int i = 1; i < valueCounts.size(); i++) {
			if(valueCounts.get(i).getCount() > high) {
				high = valueCounts.get(i).getCount();
			}
		}
		return high;
	}
	
	public static int countPairs(Vector<ValueCount> valueCounts) {
		int pairCount = 0;
		for(int i = 0; i < valueCounts.size(); i++) {
			if(valueCounts.get(i).getCount() == 2) {
				pairCount += 1;
			}
		}
		
		return pairCount;
	}
	
	public static boolean hasStraight(Vector<ValueCount> valueCounts) {
		sortByValue(valueCounts);
		
		if(valueCounts.size() < 5) {
			return false;
		}
		
		if(hasRoyalStraight(valueCounts)) {
			return true;
		}
		
		for(int i = 0; i < valueCounts.size() - 1; i++) {
			if(valueCounts.get(i + 1).getValue() != valueCounts.get(i).getValue() + 1) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean hasRoyalStraight(Vector<ValueCount> valueCounts) {
		//FIXME might need to change this logic for the Shortcut Joker
		if(valueCounts.get(0).getValue() == Card.ACE) {
			//System.out.println("ACE!");
			if(valueCounts.get(1).getValue() == 10 &&
			   valueCounts.get(2).getValue() == 11 &&
			   valueCounts.get(3).getValue() == 12 &&
			   valueCounts.get(4).getValue() == 13) {
				return true;
			}
		}
		return false;
	}
	
	public static void sortByValue(Vector<ValueCount> valueCounts) {
		Vector<ValueCount> temp = new Vector<ValueCount>(0);
		
		//Copies the original array
		for(int i = 0; i < valueCounts.size(); i++) {
			temp.add(valueCounts.get(i));
		}
		
		int currIndex = 0;
		while(temp.size() > 0) {
			int lowIndex = 0;
			//Finds the smallest value in the array
			for(int i = 0; i < temp.size(); i++) {
				if(temp.get(i).getValue() < temp.get(lowIndex).getValue()) {
					lowIndex = i;
				}
			}
			
			//Sets the currIndex to the smallest value found, then moves to the next index
			valueCounts.set(currIndex, temp.remove(lowIndex));
			currIndex += 1;
		}
	}
	
	public static int highestValue(Vector<ValueCount> valueCounts) {
		//System.out.println("Size: " + valueCounts.size());
		
		return valueCounts.get(valueCounts.size() - 1).getValue();
	}
}
