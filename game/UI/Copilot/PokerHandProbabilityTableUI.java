package game.UI.Copilot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import copilot.deckAnalysis.PokerHandProbability;
import copilot.deckAnalysis.PokerHandProbabilityTable;
import copilot.handAnalysis.PotentialHandsFinder;
import data.deck.Deck;
import data.deck.DeckBuilder;
import data.player.Player;
import data.pokerHand.PokerHandTable;
import game.GameState;

public class PokerHandProbabilityTableUI extends JPanel{
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		
        frame.setTitle("Poker Hand Probability Table Test");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximize window
        frame.setUndecorated(false); // Set to true if you want fullscreen with no title bar

        PokerHandProbabilityTableUI table = new PokerHandProbabilityTableUI("");
        frame.add(table);
        
        frame.setVisible(true);
	}
	
	
	public static final int SMALL_COL = 6;
	public static final int MED_COL = 10;
	public static final int LARGE_COL = 15;
	
	PokerHandProbabilityTable table;
	String[] columnNames = {"Poker Hand", "Count", "x.x%", "Avg", "High", "Low"};
	Object[][] rows = new Object[13][6];
	JTable dataTable;

	
	public PokerHandProbabilityTableUI(String name) {
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//setBorder(BorderFactory.createTitledBorder("Poker Hand Probability Table - " + table.name));
		
				
		Player player = new Player();
		Deck deck = DeckBuilder.buildDeck(DeckBuilder.DEFAULT_DECK);
		player.deck = deck;
		GameState gamestate = new GameState(player, 0);
		deck.shuffle();
		deck.draw(8);
		PotentialHandsFinder p = new PotentialHandsFinder(gamestate);
		setTable(p.generateProbabilityTableOfPotentialHands(gamestate, 2));
		
		
		JTable dataTable = new JTable(rows, columnNames);
		add(new JScrollPane(dataTable));
		
		TableColumn column = null;
		for(int i = 0; i < 6; i++) {
			column = dataTable.getColumnModel().getColumn(i);
			if(i == 0) {
				column.setPreferredWidth(100);
			}else {
				column.setPreferredWidth(25);
			}
		}
		
		//System.out.println(table.printTable());
	}
	
	public PokerHandProbabilityTableUI(PokerHandProbabilityTable table) {
		setTable(table);
		
		JTable dataTable = new JTable(rows, columnNames);
		add(new JScrollPane(dataTable));
		
		TableColumn column = null;
		for(int i = 0; i < 6; i++) {
			column = dataTable.getColumnModel().getColumn(i);
			if(i == 0) {
				column.setPreferredWidth(100);
			}else {
				column.setPreferredWidth(25);
			}
		}
		
		//System.out.println(table.printTable());
	}
	
	
	public void setTable(PokerHandProbabilityTable newTable) {
		table = newTable;
		
		System.out.println("TableUI [setTable()] - \n" + newTable.printTable());
		
		Vector<PokerHandProbability> data = table.getPokerHandVector();
		//System.out.println(data.size());
		for(int i = 0; i < data.size(); i++) {
			//System.out.println("Looping - " + data.get(i).getName());
			rows[i] = generateRowFromData(data.get(i), table.sumTotalCount());
		}
	}
	
	
	
	public Object[] generateRowFromData(PokerHandProbability p, int totalCount) {
		Object[] row = new Object[6];
		
		row[0] = p.getName();
		row[1] = Integer.toString(p.getCount());
		row[2] = String.format("%.4f", ((double)p.getCount() / (double) totalCount * 100));
		row[3] = String.format("%.2f", p.getAverageScore());
		row[4] = String.format("%.2f", p.getHighScore());
		row[5] = String.format("%.2f", p.getLowScore());
		
		//System.out.println(row[0]);
		return row;
	}
}
