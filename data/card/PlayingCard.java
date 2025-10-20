package data.card;


/**
 * Class to hold PlayingCard information
 * 
 * @author Elijah Reyna
 */
public class PlayingCard extends Card{
	// ============ Values ============ //
	public static final int ACE = 14;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	public static final int SIX = 6;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int NINE = 9;
	public static final int TEN = 10;
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;
	
	// ============ Suits ============ //
	public static final char SPADES = 'S';
	public static final char HEARTS = 'H';
	public static final char CLUBS = 'C';
	public static final char DIAMONDS = 'D';
	
	// ============ Modifiers ============ //
	public static final String BONUS = "BONUS";
	public static final String MULT = "MULT";
	public static final String WILD = "WILD";
	public static final String GLASS = "GLASS";
	public static final String STEEL = "STEEL";
	public static final String STONE = "STONE";
	public static final String GOLD = "GOLD";
	public static final String LUCKY = "LUCKY";
	
	// ============ Editions ============ //
	public static final String BASE = "BASE";
	public static final String FOIL = "FOIL";
	public static final String HOLOGRAPHIC = "HOLOGRAPHIC";
	public static final String POLYCHROME = "POLYCHROME";
	public static final String NEGATIVE = "NEGATIVE";
	
	// ============ Seals ============ //
	public static final String GOLD_SEAL = "GOLD_SEAL";
	public static final String RED_SEAL = "RED_SEAL";
	public static final String BLUE_SEAL = "BLUE_SEAL";
	public static final String PURPLE_SEAL = "PURPLE_SEAL";
	
	public static final String NONE = "NONE";
	public static int id = 000;
	
	// ======== Card Variables ======== //
	private int value;
	private char suit;
	private int cardID;
	
	private String enhancement;
	private String edition;
	private String seal;
	
	private int chips;
	
	
	/**
	 * Constructor
	 * @param value Values of the card
	 * @param suit Suit of the card
	 */
	public PlayingCard(int value, char suit) {
		this.value = value;
		this.suit = suit;
		enhancement = NONE;
		edition = NONE;
		seal = NONE;
		cardID = id++;
	}

	// ========== Getters and Setters ========== //
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public char getSuit() {
		return suit;
	}

	public void setSuit(char suit) {
		this.suit = suit;
	}

	public String getEnhancement() {
		return enhancement;
	}

	public void setEnhancement(String enhancement) {
		this.enhancement = enhancement;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getSeal() {
		return seal;
	}

	public void setSeal(String seal) {
		this.seal = seal;
	}
	
	
	// ========== Other Methods ========== //
	
	/**
	 * Generates a string representation of the card
	 * @return
	 */
	public String printCard() {
		return Integer.toString(value) + " of " + suit + " " + enhancement + " " + edition + " " + seal;
	}
	
	/**
	 * Generates a string with the value and suit of the card
	 * @return
	 */
	public String printValueAndSuit() {
		return Integer.toString(value) + suit;
	}
	
	/**
	 * Methods for getting the path name of a card, used by the GUI to get the correct images for a card
	 */
	@Override
	public String cardPathName() {
		String result = "";
		switch(value) {
			case ACE:
				result += "ace_";
				break;
			case TWO:
				result += "two_";
				break;
			case THREE:
				result += "three_";
				break;
			case FOUR:
				result += "four_";
				break;
			case FIVE:
				result += "five_";
				break;
			case SIX:
				result += "six_";
				break;
			case SEVEN:
				result += "seven_";
				break;
			case EIGHT:
				result += "eight_";
				break;
			case NINE:
				result += "nine_";
				break;
			case TEN:
				result += "ten_";
				break;
			case JACK:
				result += "jack_";
				break;
			case QUEEN:
				result += "queen_";
				break;
			case KING:
				result += "king_";
				break;
		}
		
		switch(suit) {
			case SPADES:
				result += "spades";
				break;
			case HEARTS:
				result += "hearts";
				break;
			case CLUBS:
				result += "clubs";
				break;
			case DIAMONDS:
				result += "diamonds";
				break;
		}
		
		result += ".png";
		
		return result;
	}
	
	
	/**
	 * Gets the suit of a card as an integer (For comparison between cards)
	 * @param cardSuit
	 * @return
	 */
	public static int getCardSuitAsInt(char cardSuit) {
		switch(cardSuit) {
		case SPADES:
			return 4;
		case HEARTS:
			return 3;
		case CLUBS:
			return 2;
		case DIAMONDS:
			return 1;
		default:
			return 0;
		}
	}
	
	/**
	 * Compares the given cards. To determine if a card is "greater" than another, first the rank is compared, then the suit (S > H > C > D)
	 * @param card1
	 * @param card2
	 * @return
	 */
	public static int compare(PlayingCard card1, PlayingCard card2) {
		if(card1.value < card2.value) {
			return -1;
		}
		else if(card1.value > card2.value) {
			return 1;
		}
		else {
			if(card1.suit == card2.suit) {
				return 1;
			}
			
			int card1Val = getCardSuitAsInt(card1.getSuit());
			int card2Val = getCardSuitAsInt(card2.getSuit());
			
			if(card1Val < card2Val) {
				return -1;
			}else if(card1Val > card2Val) {
				return 1;
			}else {
				return 0;
			}
		}
	}
}
