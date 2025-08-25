package game.UI;

import data.card.Card;
import data.deck.DeckUtils;
import data.player.Player;
import game.BlindType;
import game.scoring.HandScorer;
import game.scoring.PlayedHand;
import game.scoring.ScoreChangeValues;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class BalatroUI extends JFrame implements HandScorer, AnteSelectListener, ButtonPanelListener{
	Player player;
	
	int ante = 1;
	int round = 0;
	String currBlind = "None";
	
	// The various panels that will be used
	Container pane;
	LeftPanel leftPanel;
	ConsolePanel consolePanel;
	JokerPanel jokerPanel;
	ConsumablePanel consumablePanel;
	PlayArea playArea;
	AnteSelect anteSelect;
	JPanel buttonPanel;
	CopilotPanel copilotPanel;

    public BalatroUI() {
    	// ===== Player Setup ===== //
    	player = new Player();
    	
    	// ===== UI Setup ===== //
        setTitle("Balatro Clone UI");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximize window
        setUndecorated(false); // Set to true if you want fullscreen with no title bar

        pane = getContentPane();
        
        homeScreen();

        setVisible(true);
    }
    
    public void homeScreen() {
    	pane.removeAll();
        pane.setLayout(new GridBagLayout());
        
        JButton startGame = new JButton("Start Game");
        startGame.addActionListener(e -> startGame());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);

        pane.add(startGame, gbc);
        
        pane.revalidate();
        pane.repaint();
    }
    
    public void startGame() {
    	pane.removeAll();
        pane.setLayout(new GridBagLayout());
        
        // ==== TOP LEFT ABOVE Game Stats: Console Panel ====
        consolePanel = new ConsolePanel();
        pane.add(consolePanel, ConsolePanel.getGBC());
        
        // ==== LEFT PANEL (Game Stats) ====
        leftPanel = new LeftPanel(currBlind, 0, player.getNumHands(), player.getNumDiscards(), player.money);
        pane.add(leftPanel, LeftPanel.getGBC());

        
        // ==== TOP LEFT: Joker Panel ====
        jokerPanel = new JokerPanel();
        pane.add(jokerPanel, JokerPanel.getGBC());

        
        // ==== TOP RIGHT: Consumable Panel ====
        consumablePanel = new ConsumablePanel();
        pane.add(consumablePanel, ConsumablePanel.getGBC());


        // ==== CENTER PANEL (Hand Cards) ====
        anteSelect = new AnteSelect(this, player.getBaseChips(ante), 1);
        playArea = anteSelect;
        playArea.setBorder(BorderFactory.createTitledBorder("Ante Select"));
        pane.add(playArea, PlayArea.getGBC());

        
        // ==== BOTTOM PANEL (Action Buttons) ====
        buttonPanel = new ButtonPanel(this);
        pane.add(buttonPanel, ButtonPanel.getGBC());
        
        
        // ==== RIGHT PANEL PLACEHOLDER (Future Content) ====
        copilotPanel = new CopilotPanel();
        pane.add(copilotPanel, CopilotPanel.getGBC());
        
        pane.revalidate();
        pane.repaint();
    }

	@Override
	public void onBlindSelected(String blindName) {
		consolePanel.appendText("Blind selected: " + blindName);
		
		leftPanel.currBlind = blindName;
		if(blindName.equals("Small Blind")) {
			leftPanel.scoreRequired = player.getBaseChips(ante);
		}else if(blindName.equals("Big Blind")){
			leftPanel.scoreRequired = (int) (player.getBaseChips(ante) * 1.5);
		}else {
			leftPanel.scoreRequired = (int) (player.getBaseChips(ante) * BlindType.getMultiplierByName(blindName));
		}
		
		leftPanel.updateLabels();
		
		setPlayArea(new HandPanel());
		
		startBlind();
		
		consolePanel.appendText("Hand updated");
		
		pane.revalidate();
		pane.repaint();
	}

	public void startBlind() {
		player.deck.shuffle();
		player.deck.draw(8);
		DeckUtils.sortCardVector(player.deck.drawnCards, DeckUtils.SORT_RANK);
		playArea.deck = player.deck;
		
		playArea.setBorderTitle("Your Hand: " + player.deck.size() + " / " + player.deck.totalCards());
	}
	
	public void updateGameStatsPanel() {
		leftPanel.currBlind = currBlind;
		leftPanel.numHands = player.numHands;
		leftPanel.numDiscards = player.numDiscards;
	}
	
	private void setPlayArea(JPanel newPlayArea) {
	    if (playArea != null) {
	        pane.remove(playArea);
	    }
	    playArea = (PlayArea) newPlayArea;
	    pane.add(playArea, PlayArea.getGBC());
	    pane.revalidate();
	    pane.repaint();
	}

	@Override
	public void onBlindSkipped() {
		System.out.println("Blind skipped.");
		pane.revalidate();
		pane.repaint();
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(BalatroUI::new);
    }

	@Override
	public void playHandPressed() {
		if(leftPanel.currBlind.equals("None")) return;
		
		Vector<Card> selectedCards = new Vector<Card>(0);
		Vector<Card> heldCards = new Vector<Card>(0);
		
		for(int i = 0; i < player.deck.drawnCards.size(); i++) {
			if(player.deck.drawnCards.get(i).isSelected) {
				player.deck.drawnCards.get(i).isSelected = false;
				selectedCards.add(player.deck.drawnCards.get(i));
				player.deck.drawnCards.remove(i);
				i--;
			}else {
				heldCards.add(player.deck.drawnCards.get(i));
			}
		}
		
		if(selectedCards.size() == 0) {
			consolePanel.appendText("No cards selected to play");
			return;
		}
		
		consolePanel.appendText("Playing Cards:");
		for(int i = 0; i < selectedCards.size(); i++) {
			consolePanel.appendText(" - " + selectedCards.get(i).printCard());
		}
				
		PlayedHand playedHand = new PlayedHand(selectedCards, heldCards);
		HandScorer.scoreHand(playedHand, player.getPokerHandTable());
		
		consolePanel.appendText(playedHand.printChanges());
		consolePanel.appendText("Final Score: " + playedHand.score());
		
		player.deck.discardedCards.addAll(selectedCards);
		player.deck.drawTo(8);
		DeckUtils.sortCardVector(player.deck.drawnCards, DeckUtils.SORT_RANK);
		
		playArea.setBorderTitle("Your Hand: " + player.deck.size() + " / " + player.deck.totalCards());
		playArea.rebuildLayeredPane();
		
		leftPanel.useHand(playedHand.getScore());
		if(leftPanel.scoreReached()) {
			consolePanel.appendText("Score Reached!");
			leftPanel = new LeftPanel(currBlind, 0, player.getNumHands(), player.getNumDiscards(), player.money);
			anteSelect.incrementBlinds();
			setPlayArea(anteSelect);
			resetDeck();
		} else if(leftPanel.outOfHands()) {
			consolePanel.appendText("Game Over!");
			resetDeck();
			homeScreen();
		}
		currBlind = "None";
	}
	
	public void resetDeck() {
		for(int i = 0; i < player.deck.drawnCards.size(); i++) {
			player.deck.drawnCards.get(i).isSelected = false;	
		}
		player.deck.resetDeck();
	}

	@Override
	public void discardHandPressed() {
		if(leftPanel.currBlind.equals("None")) return;
		
		if(leftPanel.numDiscards <= 0) {
			consolePanel.appendText("No discards remaining!");
			return;
		}
		
		// Pulls the selected cards
		Vector<Card> selectedCards = new Vector<Card>(0);
		for(int i = 0; i < player.deck.drawnCards.size(); i++) {
			if(player.deck.drawnCards.get(i).isSelected) {
				player.deck.drawnCards.get(i).isSelected = false;
				selectedCards.add(player.deck.drawnCards.get(i));
				player.deck.drawnCards.remove(i);
				i--;
			}
		}
		
		if(selectedCards.size() == 0) {
			consolePanel.appendText("No cards selected to discard");
			return;
		}
		
		// Logs the change to the console
		consolePanel.appendText("Discarding Cards:");
		for(int i = 0; i < selectedCards.size(); i++) {
			consolePanel.appendText(" - " + selectedCards.get(i).printCard());
		}
		
		// Replenishes the player's hand
		player.deck.discardedCards.addAll(selectedCards);
		player.deck.drawTo(8);
		DeckUtils.sortCardVector(player.deck.drawnCards, DeckUtils.SORT_RANK);
		
		// Updates some information
		playArea.setBorderTitle("Your Hand: " + player.deck.size() + " / " + player.deck.totalCards());
		playArea.rebuildLayeredPane();
		leftPanel.useDiscard();
	}
	
	@Override
	public void sortByRankPressed() {
		if(leftPanel.currBlind.equals("None")) return;
		DeckUtils.sortCardVector(player.deck.drawnCards, DeckUtils.SORT_RANK);
		playArea.rebuildLayeredPane();
	}
	
	@Override
	public void sortBySuitPressed() {
		if(leftPanel.currBlind.equals("None")) return;
		DeckUtils.sortCardVector(player.deck.drawnCards, DeckUtils.SORT_SUIT);
		playArea.rebuildLayeredPane();
	}
}

