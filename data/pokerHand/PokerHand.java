package data.pokerHand;


/**
 * Class for storing PokerHand information
 * 
 * @author Elijah Reyna
 */
public class PokerHand{
	// ==================== Poker Hand names ==================== //
	public static final String HIGH_CARD = "HIGH_CARD";
	public static final String PAIR = "PAIR";
	public static final String TWO_PAIR = "TWO_PAIR";
	public static final String THREE_OF_A_KIND = "THREE_OF_A_KIND";
	public static final String FOUR_OF_A_KIND = "FOUR_OF_A_KIND";
	public static final String FIVE_OF_A_KIND = "FIVE_OF_A_KIND";
	public static final String FULL_HOUSE = "FULL_HOUSE";
	public static final String STRAIGHT = "STRAIGHT";
	public static final String FLUSH = "FLUSH";
	public static final String STRAIGHT_FLUSH = "STRAIGHT_FLUSH";
	public static final String ROYAL_FLUSH = "ROYAL_FLUSH";
	public static final String FLUSH_FIVE = "FLUSH_FIVE";
	public static final String FLUSH_HOUSE = "FLUSH_HOUSE";
	
	
	// ==================== Poker Hand Variables ==================== //
	String handName;
	int chips;
	int mult;
	
	
	/**
	 * Constructor
	 * @param handName
	 * @param chips
	 * @param mult
	 */
	public PokerHand(String handName, int chips, int mult) {
		this.handName = handName;
		this.chips = chips;
		this.mult = mult;
	}
	
	// ==================== Getters and Setters ==================== //
	public String getName() {
		return handName;
	}
	
	public int getChips() {
		return chips;
	}
	
	public int getMult() {
		return mult;
	}
	
	/**
	 * Generates a String representation of the PokerHand
	 * @return
	 */
	public String printPokerHand() {
		return handName + ": " + chips + " x " + mult;
	}
}
