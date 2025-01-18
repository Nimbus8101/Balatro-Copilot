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
	
	public static boolean hasMoreThanThreeMatch(Vector<ValueCount> valueCounts) {
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
		
		for(int i = 0; i < valueCounts.size() - 1; i++) {
			if(valueCounts.get(0) != valueCounts.get(i + 1)) {
				return false;
			}
		}
		return true;
	}
	
	public static void sortByValue(Vector<ValueCount> valueCounts) {
		Vector<ValueCount> newValueCounts = new Vector<ValueCount>(0);
		
		while(valueCounts.size() > 0) {
			int lowIndex = 0;
			for(int i = 0; i < valueCounts.size(); i++) {
				if(valueCounts.get(i).getValue() > valueCounts.get(lowIndex).getValue()) {
					lowIndex = i;
				}
			}
			newValueCounts.add(valueCounts.remove(lowIndex));
		}
		
		valueCounts = newValueCounts;
	}
	
	public static int highestValue(Vector<ValueCount> valueCounts) {
		return valueCounts.get(valueCounts.size() - 1).getValue();
	}
}
