package data.card;

public class Joker {
	public static final String BASE = "BASE";
	public static final String FOIL = "FOIL";
	public static final String HOLOGRAPHIC = "HOLOGRAPHIC";
	public static final String POLYCHROME = "POLYCHROME";
	public static final String NEGATIVE = "NEGATIVE";
	
	String name;
	String edition;
	
	public Joker(String name, String edition) {
		this.name = name;
		this.edition = edition;
	}
}
