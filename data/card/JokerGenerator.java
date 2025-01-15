package data.card;

public class JokerGenerator {
	
	public JokerGenerator() {
		
	}
	
	public Joker generateJoker(String jokerName) {
		switch (jokerName) {
		case "MULT_JOKER":
			
			break;
		default: 
			return createDefaultJoker();
		}
		System.out.println("There was an error in the generateJoker() function in JokerGenerator.java");
		return null;
	}
	
	public Joker createDefaultJoker(){
		return new Joker("Default","NONE");
	}
}
