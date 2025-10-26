package game.UI.Copilot;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import copilot.deckAnalysis.PokerHandProbability;
import copilot.deckAnalysis.PokerHandProbabilityTable;

public class PokerHandProbabilityLabel extends JPanel{	
	public static final int SMALL_COL = 6;
	public static final int MED_COL = 10;
	public static final int LARGE_COL = 20;
	
	PokerHandProbability data;
	JLabel[] labels = new JLabel[6];
	double chance = 0.00;

	
	public PokerHandProbabilityLabel(PokerHandProbability handData) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		setData(handData);
		
		for(JLabel label : labels) {
			label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			add(label);
		}
	}
	
	
	public void setData(PokerHandProbability data) {
		this.data = data;
		rebuildLabels();
	}
	
	public void rebuildLabels() {
		labels[0] = new JLabel(padString(data.getName(), LARGE_COL));
		labels[1] = new JLabel(padString(Integer.toString(data.getCount()), MED_COL));
		labels[2] = new JLabel(padString(String.format("%.2f", chance), MED_COL));//Chance is handled by the PokerHandProbabilityTable
		labels[3] = new JLabel(padString(String.format("%.2f", data.getAverageScore()), MED_COL));
		labels[4] = new JLabel(padString(String.format("%.2f", data.getHighScore()), MED_COL));
		labels[5] = new JLabel(padString(String.format("%.2f", data.getLowScore()),MED_COL));
	}
	
	public void createAndAddHeader(JPanel headerPanel, String name, int columnSize) {
		JLabel label = new JLabel(padString(name, columnSize));
		
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		headerPanel.add(label);
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
