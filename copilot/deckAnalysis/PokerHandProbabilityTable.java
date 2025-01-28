package copilot.deckAnalysis;

import java.util.Vector;

public class PokerHandProbabilityTable {
	Vector<PokerHandProbability> probabilityTable;
	int totalCount = 0;
	
	public PokerHandProbabilityTable(Vector<String> pokerHandNames){
		probabilityTable = new Vector<PokerHandProbability>(0);
		for(int i = 0; i < pokerHandNames.size(); i++) {
			probabilityTable.add(new PokerHandProbability(pokerHandNames.get(i)));
		}
	}
	
	public void addScore(String handName, double score){
		for(int i = 0; i < probabilityTable.size(); i++) {
			if(probabilityTable.get(i).getName().equals(handName)) {
				probabilityTable.get(i).addScore(score);
				return;
			}
		}
	}
	
	public int sumTotalCount() {
		int sum = 0;
		for(int i = 0; i < probabilityTable.size(); i++) {
			sum += probabilityTable.get(i).getCount();
		}
		return sum;
	}

	public String printTable() {
		int HandLength = 20;
		int scoreLength = 11;
		String buffer = "                    ";  //length 20
		int sum = sumTotalCount();
		
		String result = "|------------------------------Probability Table-------------------------------|\n";
		result += 		"|   Poker Hand   |   Count   |    x.x%    | Avg Score | High Score | Low Score |\n";
		result +=		"|------------------------------------------------------------------------------|\n";
		for(int i = 0; i < probabilityTable.size(); i++) {
			PokerHandProbability p = probabilityTable.get(i);
			result += "|" + padString(p.getName(), 16);
			String percentage = String.format("%.6f", 100 * (double) p.getCount() / (double) sum);
			result += "|" + padString(Integer.toString(p.getCount()), 11);
			result += "|" + padString(percentage, 12);
			result += "|" + padString(Double.toString(p.getAverageScore()), 11);
			result += "|" + padString(Double.toString(p.getHighScore()), 12);
			result += "|" + padString(Double.toString(p.getLowScore()), 11);
			result += "|\n";
			
		}
		result += "|Total: " + sum + "\n";
		return result;
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
