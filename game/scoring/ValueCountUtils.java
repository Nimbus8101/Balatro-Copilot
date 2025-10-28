package game.scoring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import data.card.PlayingCard;

/**
 * Utility class with methods regarding the ValueCount class
 * This class is used in identifying a list of cards hand type, int the PokerHandIdentifier class
 * @author Elijah
 *
 */
public interface ValueCountUtils {
	static final Map<Long, int[]> cache =
		    Collections.synchronizedMap(new LinkedHashMap<>(10000, 0.75f, true) {
		        protected boolean removeEldestEntry(Map.Entry<Long, int[]> eldest) {
		            return size() > 10000; // keep 10k entries max
		        }
		    });

	
	/**
	 * Counts the values of cards in a List of Card objects
	 * @param cards
	 * @return
	 */
	public static int[] countValues(List<PlayingCard> cards) {
		Long key = generateKeyHash(cards);
		
		int[] result = cache.get(key);
		if(result != null) {
			 return result.clone();
		}
		
	    result = new int[15]; // index = value
	    for (PlayingCard c : cards) result[c.getValue()]++;

	    /**
	    result = new ArrayList<>();
	    for (int v = 0; v < counts.length; v++) {
	        if (counts[v] > 0)
	            result.add(new ValueCount(v, counts[v]));
	    }
	    */
	    cache.put(key, result);
	    return result;
	}
	
	public static long generateKeyHash(List<PlayingCard> cards) {
	    long h = 1L;
	    for (int i = 0; i < cards.size(); i++) {
	        PlayingCard c = cards.get(i);
	        h = 31 * h + ((c.getValue() * 10L) + c.getSuit());
	    }
	    return h;
	}
	
	/**
	 * Increments the count of a specific value if it exists in the ValueCount list, otherwise creates a new ValueCount and adds it to the list
	 * @param valueCounts
	 * @param value
	 */
	public static void addCount(List<ValueCount> valueCounts, int value) {
		for(int i = 0; i < valueCounts.size(); i++) {
			if(valueCounts.get(i).getValue() == value) {
				valueCounts.get(i).increment(1);
				return;
			}
		}
		valueCounts.add(new ValueCount(value, 1));
	}
	
	
	/**
	 * Checks if a List of ValueCount objects has any matches
	 * @param valueCounts
	 * @return
	 */
	public static boolean hasMatches(int[] valueCounts) {
		for(int i = 0; i < valueCounts.length - 1; i++) {
			if(valueCounts[i] > 1) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Checks if a List of ValueCount objects has a match of three or more
	 * @param valueCounts
	 * @return
	 */
	public static boolean hasMoreThanThreeMatches(int[] valueCounts) {
		for(int i = 0; i < valueCounts.length; i++) {
			if(valueCounts[i] > 2) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the highest count among ValueCounts in a List of ValueCount objects
	 * @param valueCounts
	 * @return
	 */
	public static int highestCount(int[] valueCounts) {
		int high = valueCounts[0];
		for(int i = 1; i < valueCounts.length; i++) {
			if(valueCounts[i] > high) {
				high = valueCounts[i];
			}
		}
		return high;
	}
	
	/**
	 * Counts the pairs in a List of ValueCount objects
	 * @param valueCounts
	 * @return
	 */
	public static int countPairs(int[] valueCounts) {
		int pairCount = 0;
		for(int i = 0; i < valueCounts.length; i++) {
			if(valueCounts[i] == 2) {
				pairCount += 1;
			}
		}
		
		return pairCount;
	}
	
	/**
	 * Checks if a List of ValueCount objects reflects a straight
	 * FIXME: Might need to change the logic for Shortcut Joker
	 * @param valueCounts
	 * @return
	 */
	public static boolean hasStraight(int[] valueCounts) {
		int numVals = 0;
		for(int value : valueCounts) {
			numVals += value;
		}
	
		if(numVals < 5) {
			return false;
		}
		
		if(hasRoyalStraight(valueCounts)) {
			return true;
		}
		
		// Loops through the array until it reaches an index with a value of 1
		// All the following values must be 1, otherwise it is not a straight
		int consecutive = 0;
		for (int i = 0; i < valueCounts.length; i++) {
		    if (valueCounts[i] > 0) {
		        if (++consecutive == 5) return true;
		    } else {
		        consecutive = 0;
		    }
		}
		return false;
	}
	
	
	/**
	 * Checks if a List of ValueCount objects contains a royal straight
	 * @param valueCounts
	 * @return
	 */
	public static boolean hasRoyalStraight(int[] valueCounts) {
		//FIXME might need to change this logic for the Shortcut Joker
		if (valueCounts[PlayingCard.TEN - 1] == 1 &&
			valueCounts[PlayingCard.JACK - 1] == 1 &&
			valueCounts[PlayingCard.QUEEN - 1] == 1 &&
			valueCounts[PlayingCard.KING - 1] == 1 &&
			valueCounts[PlayingCard.ACE - 1] == 1){
			return true;
		}
		return false;
	}
	
	
	/**
	 * Sorts the List of Valuecount objects by the Values in the objects, lowest to highest
	 * @param valueCounts
	 */
	public static void sortByValue(List<ValueCount> valueCounts) {
	    valueCounts.sort(Comparator.comparingInt(ValueCount::getValue));
	}
}
