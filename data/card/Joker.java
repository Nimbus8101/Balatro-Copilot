package data.card;


/**
 * Class handling variables for a Joker
 * 
 * @author Elijah Reyna
 */
public enum Joker {
	// ========== Joker List ========== //
	JOKER("Joker", 1, 0, 4, 1.0, 0);
	
	private final String name;
	private final int num;
	private final int baseChips;
	private final int baseMult;
	private final double baseMultiplier;
	private final int type;
	
	
	// ===== Joker Types ===== //
	// Used by an evaluation algorithm (which isn't currently implemented)
	public static int NO_TYPE = 0;
	public static int SCALING = 3;
	
	Joker(String string, int i, int baseChips, int baseMult, double baseMultiplier, int type) {
		this.name = string;
		this.num = i;
		this.baseChips = baseChips;
		this.baseMult = baseMult;
		this.baseMultiplier = baseMultiplier;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNum() {
		return num;
	}
	
	public int baseChips() {
		return baseChips;
	}
	
	public int baseMult() {
		return baseMult;
	}
	
	public double baseMultiplier() {
		return baseMultiplier;
	}
	
	public static Joker fromName(String name) {
        for (Joker joker : values()) {
            if (joker.name.equalsIgnoreCase(name)) {
                return joker;
            }
        }
        throw new IllegalArgumentException("Invalid name: " + name);
	}

	int getType() {
		return type;
	}
}
