package data.card;

/**
 * Class for holding Joker information as well as modifications that can be made to it
 * @author Elijah
 *
 */
public class JokerCard extends Card {
	public static final String JOKER = "JOKER";
	
	public static final String BASE = "BASE";
	public static final String FOIL = "FOIL";
	public static final String HOLOGRAPHIC = "HOLOGRAPHIC";
	public static final String POLYCHROME = "POLYCHROME";
	public static final String NEGATIVE = "NEGATIVE";
	
	Joker joker;
	String edition;
	
	int chips;
	int mult;
	double multiplier;
	
	public boolean isSelected = false;
	
	public JokerCard(String name, String edition) {
		this.joker = Joker.fromName(name);
		this.edition = edition;
		
		chips = joker.baseChips();
		mult = joker.baseMult();
		multiplier = joker.baseMultiplier();
	}
	
	@Override
	public String cardPathName() {
		//System.out.println("-- joker_" + name + ".png");
		return "joker_" + joker.getName() + ".png";
	}

	public int getChips() {
		return chips;
	}

	public int getMult() {
		return mult;
	}

	public double getMultiplier() {
		return multiplier;
	}
	
	public int type() {
		return joker.getType();
	}
}
