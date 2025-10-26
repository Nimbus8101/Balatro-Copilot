package game.UI.Copilot;

import java.util.List;

import javax.swing.JTabbedPane;

public class TabbedPane extends JTabbedPane{
	List<PokerHandProbabilityTableUI> tables;
	
	TabbedPane(){
		super();
	}
	
	public void addTable(PokerHandProbabilityTableUI newTable, String name) {
		tables.add(newTable);
	}
}
