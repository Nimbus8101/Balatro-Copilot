package game.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import copilot.Copilot;
import copilot.deckAnalysis.PokerHandProbabilityTable;
import game.UI.Copilot.PokerHandProbabilityTableUI;

public class CopilotPanel extends JPanel{
	public Copilot copilot;
	CopilotButtonListener listener;
	//PokerHandProbabilityTableUI table = new PokerHandProbabilityTableUI
	JTabbedPane tabs = new JTabbedPane();
	List<PokerHandProbabilityTableUI> probabilityTables = new ArrayList<>();
	
	public ConsolePanel copilotMessage = new ConsolePanel();
	JScrollPane scrollPane = new JScrollPane(copilotMessage);
    private JButton analyzeSelectedButton = new JButton("Analyze");
    private JButton stopAnalysisButton = new JButton("Stop Analysis");
	
	
	public CopilotPanel(CopilotButtonListener listener, Copilot copilot) {
		this.listener = listener;
		this.copilot = copilot;
		copilot.copilotPanel = this;
		copilot.updateCopilotPanel = true;
		setLayout(new BorderLayout(5, 5)); // margin spacing
        setBorder(BorderFactory.createTitledBorder("Copilot"));
        //setMinimumSize(new Dimension(400, 250));
        setPreferredSize(new Dimension(400, 250));

        JScrollPane scrollPane = new JScrollPane(copilotMessage);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // Buttony at bottom
        analyzeSelectedButton.addActionListener(e -> listener.analyzeButtonPressed());
        stopAnalysisButton.addActionListener(e -> listener.stopAnalysis());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(analyzeSelectedButton);
        buttonPanel.add(stopAnalysisButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        
        // Tabbed Pane
        tabs.setMinimumSize(new Dimension(400, 300));
        tabs.setPreferredSize(new Dimension(400, 300));
        add(tabs, BorderLayout.NORTH);
    }
	
	
	public void resetTabs() {
		tabs.removeAll();
	}
	
	public void addTab(PokerHandProbabilityTable table, String name) {
		PokerHandProbabilityTableUI p = new PokerHandProbabilityTableUI(table);
		
		probabilityTables.add(p);
				
		//System.out.println("in addTab()");
		
		tabs.addTab(name, p);
		
		repaint();
	}
	
	public void addTables(List<PokerHandProbabilityTable> tables) {
		for(PokerHandProbabilityTable table : tables) {
			addTab(table, table.name);
		}
	}
	
	public static GridBagConstraints getGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 3; // Spans all vertical space
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        return gbc;
	}
}
