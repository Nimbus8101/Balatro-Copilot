package game.UI;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LeftPanel extends JPanel{
	public String currBlind;
	public int scoreRequired;
	public double currScore;
	public int numHands;
	public int numDiscards;
	public int money;
	
	JLabel currBlindLabel = new JLabel();
	JLabel scoreLabel = new JLabel();
	JLabel handsLabel = new JLabel();
	JLabel discardsLabel = new JLabel();
	JLabel moneyLabel = new JLabel();
	JButton runInfo;
	JButton options;
	
	public LeftPanel() {
		new JPanel();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Game Stats"));

        currBlindLabel.setText("None");
        scoreLabel.setText("Score: 0");
        handsLabel.setText("Hands: 4");
        discardsLabel.setText("Discards: 6");
        moneyLabel.setText("Money: $13");
        
        runInfo = new JButton("Run Info");
        options = new JButton("Options");     
        
        add(currBlindLabel);
        add(scoreLabel);
        add(handsLabel);
        add(discardsLabel);
        add(moneyLabel);
        add(Box.createVerticalStrut(10));
        add(runInfo);
        add(options);
	}
	
	public LeftPanel(String currBlind, int scoreRequired, int numHands, int numDiscards, int money) {
		this.currBlind = currBlind;
		this.scoreRequired = scoreRequired;
		this.currScore = 0;
		this.numHands = numHands;
		this.numDiscards = numDiscards;
		this.money = money;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("Game Stats"));
	        
		updateLabels();
		 
        runInfo = new JButton("Run Info");
        options = new JButton("Options");     
        
        add(currBlindLabel);
        add(scoreLabel);
        add(handsLabel);
        add(discardsLabel);
        add(moneyLabel);
        add(Box.createVerticalStrut(10));
        add(runInfo);
        add(options);
	}
	
	public void updateInfo(String currBlind, int requiredScore, double currScore, int numHands, int numDiscards, int money) {
		this.currBlind = currBlind;
		this.scoreRequired = requiredScore;
		this.currScore = currScore;
		this.numHands = numHands;
		this.numDiscards = numDiscards;
		this.money = money;
		
		updateLabels();
	}
	
	public void updateLabels() {
		currBlindLabel.setText(currBlind + ": " + scoreRequired);
        scoreLabel.setText("Score: " + currScore);
        handsLabel.setText("Hands: " + numHands);
        discardsLabel.setText("Discards: " + numDiscards);
        moneyLabel.setText("Money: $" + money);
	}
	
	
	public boolean useDiscard() {
		if(numDiscards > 0) {
			numDiscards--;
			discardsLabel.setText("Discards: " + numDiscards);
			return true;
		}
		
		return false;
	}
	
	public void useHand(double score) {
		numHands--;
		handsLabel.setText("Hands: " + numHands);
		
		currScore += score;
		updateLabels();
	}
	
	public boolean scoreReached() {
		return currScore >= scoreRequired ? true : false;
	}
	
	public boolean outOfHands() {
		return numHands <= 0 ? true : false;
	}
	
	public static GridBagConstraints getGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.8;
        gbc.weighty = 0.8;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        return gbc;
	}
}
