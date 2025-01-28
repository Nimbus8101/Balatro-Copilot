package data.card;

public class Card {
	public static final int ACE = 1;
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
	
	public static final char SPADES = 'S';
	public static final char HEARTS = 'H';
	public static final char CLUBS = 'C';
	public static final char DIAMONDS = 'D';
	
	public static final String BONUS = "BONUS";
	public static final String MULT = "MULT";
	public static final String WILD = "WILD";
	public static final String GLASS = "GLASS";
	public static final String STEEL = "STEEL";
	public static final String STONE = "STONE";
	public static final String GOLD = "GOLD";
	public static final String LUCKY = "LUCKY";
	
	public static final String BASE = "BASE";
	public static final String FOIL = "FOIL";
	public static final String HOLOGRAPHIC = "HOLOGRAPHIC";
	public static final String POLYCHROME = "POLYCHROME";
	public static final String NEGATIVE = "NEGATIVE";
	
	public static final String GOLD_SEAL = "GOLD_SEAL";
	public static final String RED_SEAL = "RED_SEAL";
	public static final String BLUE_SEAL = "BLUE_SEAL";
	public static final String PURPLE_SEAL = "PURPLE_SEAL";
	
	public static final String NONE = "NONE";
	
	
	private int value;
	private char suit;
	
	private String enhancement;
	private String edition;
	private String seal;
	
	private int chips;
	
	public Card(int value, char suit) {
		this.value = value;
		this.suit = suit;
		enhancement = NONE;
		edition = NONE;
		seal = NONE;
	}

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
	
	public String printCard() {
		return Integer.toString(value) + " of " + suit + " " + enhancement + " " + edition + " " + seal;
	}
	
	public String printValueAndSuit() {
		return Integer.toString(value) + suit;
	}
	
}
