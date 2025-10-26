package game.scoring;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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
	final Map<Long, List<ValueCount>> cache = new HashMap<>();

	
	/**
	 * Counts the values of cards in a List of Card objects
	 * @param cards
	 * @return
	 */
	public static List<ValueCount> countValues(List<PlayingCard> cards) {
		Long key = generateKeyHash(cards);
		
		List<ValueCount> result = cache.get(cards);
		if(result != null) {
			return result;
		}
		
		
	    int[] counts = new int[15]; // index = value
	    for (PlayingCard c : cards) counts[c.getValue()]++;

	    result = new ArrayList<>();
	    for (int v = 0; v < counts.length; v++) {
	        if (counts[v] > 0)
	            result.add(new ValueCount(v, counts[v]));
	    }
	    
	    cache.put(key, result);
	    return result;
	}
	
	public static long generateKeyHash(List<PlayingCard> cards) {
	    return cards.stream()
	        .sorted(Comparator
	            .comparingInt(PlayingCard::getValue)
	            .thenComparing(PlayingCard::getSuit))
	        .mapToLong(c -> (c.getValue() * 10L) + c.getSuit())
	        .reduce(1L, (a, b) -> 31 * a + b);
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
	public static boolean hasMatches(List<ValueCount> valueCounts) {
		for(int i = 0; i < valueCounts.size(); i++) {
			if(valueCounts.get(i).getCount() > 1) {
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
	public static boolean hasMoreThanThreeMatches(List<ValueCount> valueCounts) {
		for(int i = 0; i < valueCounts.size(); i++) {
			if(valueCounts.get(i).getCount() > 2) {
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
	public static int highestCount(List<ValueCount> valueCounts) {
		int high = valueCounts.get(0).getCount();
		for(int i = 1; i < valueCounts.size(); i++) {
			if(valueCounts.get(i).getCount() > high) {
				high = valueCounts.get(i).getCount();
			}
		}
		return high;
	}
	
	/**
	 * Counts the pairs in a List of ValueCount objects
	 * @param valueCounts
	 * @return
	 */
	public static int countPairs(List<ValueCount> valueCounts) {
		int pairCount = 0;
		for(int i = 0; i < valueCounts.size(); i++) {
			if(valueCounts.get(i).getCount() == 2) {
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
	public static boolean hasStraight(List<ValueCount> valueCounts) {
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
	
	
	/**
	 * Checks if a List of ValueCount objects contains a royal straight
	 * @param valueCounts
	 * @return
	 */
	public static boolean hasRoyalStraight(List<ValueCount> valueCounts) {
		//FIXME might need to change this logic for the Shortcut Joker
		if(valueCounts.get(0).getValue() == PlayingCard.TEN &&
		   valueCounts.get(1).getValue() == PlayingCard.JACK &&
		   valueCounts.get(2).getValue() == PlayingCard.QUEEN &&
		   valueCounts.get(3).getValue() == PlayingCard.KING &&
		   valueCounts.get(4).getValue() == PlayingCard.ACE){
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
