package data.card;


/**
 * Class for generating Jokers
 * 
 * Currently has no functionality, but will likely be used in the future
 * 
 * @author Elijah Reyna
 */
public class JokerCardGenerator {
	
	public JokerCardGenerator() {
		
	}
	
	public JokerCard generateJoker(String jokerName) {
		switch (jokerName) {
		case "MULT_JOKER":
			
			break;
		default: 
			return createDefaultJoker();
		}
		System.out.println("There was an error in the generateJoker() function in JokerGenerator.java");
		return null;
	}
	
	public JokerCard createDefaultJoker(){
		return new JokerCard("Default","NONE");
	}
}
