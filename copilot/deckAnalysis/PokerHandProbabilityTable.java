package copilot.deckAnalysis;

import java.util.Vector;


/**
 * Class which stores PokerHand information
 * Currently stores:
 *  - Poker Hand probabilities
 *  - their upper and lower scores
 *  - The number of times they appeared
 *  
 *  
 * @author Elijah
 *
 */
public class PokerHandProbabilityTable {
	private Vector<PokerHandProbability> probabilityTable;
	int totalCount = 0;
	public String name;
	
	public PokerHandProbabilityTable(Vector<String> pokerHandNames){
		probabilityTable = new Vector<PokerHandProbability>(0);
		for(int i = 0; i < pokerHandNames.size(); i++) {
			probabilityTable.add(new PokerHandProbability(pokerHandNames.get(i)));
		}
		name = "";
	}
	
	public void addScore(String handName, double score){
		for(int i = 0; i < probabilityTable.size(); i++) {
			if(probabilityTable.get(i).getName().equals(handName)) {
				probabilityTable.get(i).addScore(score);
				return;
			}
		}
	}
	
	public Vector<PokerHandProbability> getPokerHandVector() {
		return probabilityTable;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public int sumTotalCount() {
		int sum = 0;
		for(int i = 0; i < probabilityTable.size(); i++) {
			sum += probabilityTable.get(i).getCount();
		}
		totalCount = sum;
		return sum;
	}
	
	public double calculateExpectedVal() {
		int sum = sumTotalCount();
		double expectedValue = 0.0;
		for(PokerHandProbability p : probabilityTable) {
			expectedValue += (p.getAverageScore() * p.getCount() / sum);
		}
		return expectedValue;
	}

	public String printTable() {
		int HandLength = 20;
		int scoreLength = 11;
		String buffer = " ".repeat(20);  //length 20
		int sum = sumTotalCount();
		
		StringBuilder result = new StringBuilder();
		
		result.append("|------------------------------Probability Table-------------------------------|\n");
		result.append("|   Poker Hand   |   Count   |    x.x%    | Avg Score | High Score | Low Score |\n");
		result.append("|------------------------------------------------------------------------------|\n");
		for(int i = 0; i < probabilityTable.size(); i++) {
			PokerHandProbability p = probabilityTable.get(i);
			result.append("|").append(padString(p.getName(), 16));
			
			String percentage = String.format("%.6f", 100 * (double) p.getCount() / (double) sum);
			result.append("|").append(padString(Integer.toString(p.getCount()), 11));
			
			result.append("|").append(padString(percentage, 12));
			
			String doubleString = String.format("%.4f", p.getAverageScore());
			result.append("|").append(padString(doubleString, 11));
			
			doubleString = String.format("%.4f", p.getHighScore());
			result.append("|").append(padString(doubleString, 12));
			
			doubleString = String.format("%.4f", p.getLowScore());
			result.append("|").append(padString(doubleString, 11));
			result.append("|\n");
			
		}
		result.append("|Total: ").append(sum).append("\n");
		result.append("|Expected Value: ").append(calculateExpectedVal()).append("\n");
		return result.toString();
	}
	
	private String padString(String string, int totalLength) {
		int difference = totalLength - string.length();
		String pad1;
		String pad2;
		
		pad1 = generatePadding(difference / 2);

		if(difference % 2 == 1) {
			pad2 = generatePadding((difference / 2) + 1);
		}else {
			pad2 = generatePadding(difference / 2);
		}
		
		return pad1 + string + pad2;
	}
	
	private String generatePadding(int length) {
		String result = "";
		for(int i = 0; i < length; i++) {
			result += " ";
		}
		return result;
	}
}
