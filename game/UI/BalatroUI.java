package game.UI;

import data.card.Joker;
import data.card.JokerCard;
import data.card.PlayingCard;
import data.deck.DeckUtils;
import data.player.Player;
import game.BlindType;
import game.GameState;
import game.scoring.HandScorer;
import game.scoring.PlayedHand;

import javax.swing.*;

import copilot.Copilot;
import copilot.deckAnalysis.PokerHandProbabilityTable;

import java.awt.*;
import java.util.Vector;

public class BalatroUI extends JFrame implements HandScorer, AnteSelectListener, ButtonPanelListener, CopilotButtonListener{	
	int ante = 1;
	int round = 0;
	String currBlind = "None";
	
	// The various panels that will be used
	Container pane;
	LeftPanel leftPanel;
	ConsolePanel consolePanel;
	JokerPanel jokerPanel;
	ConsumablePanel consumablePanel;
	HandPanel handPanel;
	AnteSelect anteSelect;
	JPanel buttonPanel;
	CopilotPanel copilotPanel;
	
	Copilot copilot = new Copilot();
	GameState gamestate;
	Player player;
	
	private SwingWorker<Object, Void> analysisWorker;

    public BalatroUI() {
    	// ===== Player Setup ===== //
    	player = new Player();
    	//Player.initializeDefaultPlayer();
    	Player.addJoker(new JokerCard(Joker.JOKER.getName(), JokerCard.BASE));
    	
    	// ===== UI Setup ===== //
        setTitle("Balatro Clone UI");
        //setSize(1000, 700);
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
    	gamestate = new GameState(player, 0);
    	
    	pane.removeAll();
        pane.setLayout(new GridBagLayout());
        
        // ==== COPILOT PANEL ====
        copilotPanel = new CopilotPanel(this, copilot);
        pane.add(copilotPanel, CopilotPanel.getGBC());
        
        // ==== TOP LEFT ABOVE Game Stats: Console Panel ====
        consolePanel = copilotPanel.copilotMessage;
        //pane.add(consolePanel, ConsolePanel.getGBC());
        
        // ==== LEFT PANEL (Game Stats) ====
        leftPanel = new LeftPanel(currBlind, 0, Player.getNumHands(), Player.getNumDiscards(), Player.money);
        pane.add(leftPanel, LeftPanel.getGBC());

        
        // ==== TOP LEFT: Joker Panel ====
        jokerPanel = new JokerPanel();
        jokerPanel.setCardVector(Player.getJokersAsCards());
        pane.add(jokerPanel, JokerPanel.getGBC());

        
        // ==== TOP RIGHT: Consumable Panel ====
        consumablePanel = new ConsumablePanel();
        pane.add(consumablePanel, ConsumablePanel.getGBC());


        // ==== CENTER PANEL (Ante Select and Hand Cards) ====
        anteSelect = new AnteSelect(this, Player.getBaseChips(ante));
        pane.add(anteSelect, AnteSelect.getGBC());
        //playArea = anteSelect;
        //playArea.setBorder(BorderFactory.createTitledBorder("Ante Select"));
        //pane.add(playArea, PlayArea.getGBC());

        
        // ==== BOTTOM PANEL (Action Buttons) ====
        buttonPanel = new ButtonPanel(this);
        pane.add(buttonPanel, ButtonPanel.getGBC());
        
        pane.revalidate();
        pane.repaint();
    }
    
    private void switchHandPanelAnteSelect(String panelToSet) {
    	if(panelToSet.equals("HAND")) {
    		pane.remove(anteSelect);
    		handPanel = new HandPanel();
    		pane.add(handPanel, HandPanel.getGBC());
    	}else if(panelToSet.equals("ANTE")) {
    		pane.remove(handPanel);
    		 pane.add(anteSelect, AnteSelect.getGBC());
    	}else {
    		
    	}
    }

	@Override
	public void onBlindSelected(String blindName) {
		consolePanel.appendText("Blind selected: " + blindName);
		
		leftPanel.currBlind = blindName;
		if(blindName.equals("Small Blind")) {
			leftPanel.scoreRequired = Player.getBaseChips(ante);
		}else if(blindName.equals("Big Blind")){
			leftPanel.scoreRequired = (int) (Player.getBaseChips(ante) * 1.5);
		}else {
			leftPanel.scoreRequired = (int) (Player.getBaseChips(ante) * BlindType.getMultiplierByName(blindName));
		}
		
		gamestate.minimumScore = leftPanel.scoreRequired;
		leftPanel.updateLabels();
		
		switchHandPanelAnteSelect("HAND");
		
		startBlind();
		
		consolePanel.appendText("Hand updated");
		
		pane.revalidate();
		pane.repaint();
	}

	public void startBlind() {
		Player.deck.shuffle();
		Player.deck.draw(8);
		DeckUtils.sortCardVector(Player.deck.drawnCards, DeckUtils.SORT_RANK);
		handPanel.deck = Player.deck;
		
		handPanel.setBorderTitle("Your Hand: " + Player.deck.size() + " / " + Player.deck.totalCards());
	}
	
	public void updateGameStatsPanel() {
		leftPanel.currBlind = currBlind;
		leftPanel.numHands = Player.numHands;
		leftPanel.numDiscards = Player.numDiscards;
	}

	@Override
	public void onBlindSkipped() {
		System.out.println("Blind skipped.");
		pane.revalidate();
		pane.repaint();
	}

	@Override
	public void playHandPressed() {
		if(leftPanel.currBlind.equals("None")) return;
		
		Vector<PlayingCard> selectedCards = new Vector<PlayingCard>(0);
		Vector<PlayingCard> heldCards = new Vector<PlayingCard>(0);
		
		for(int i = 0; i < Player.deck.drawnCards.size(); i++) {
			if(Player.deck.drawnCards.get(i).isSelected) {
				Player.deck.drawnCards.get(i).isSelected = false;
				selectedCards.add(Player.deck.drawnCards.get(i));
				Player.deck.drawnCards.remove(i);
				i--;
			}else {
				heldCards.add(Player.deck.drawnCards.get(i));
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
		HandScorer.scoreHand(playedHand, Player.getJokersAsJokers(), Player.getPokerHandTable());
		
		consolePanel.appendText(playedHand.printChanges());
		consolePanel.appendText("Final Score: " + playedHand.score());
		
		Player.deck.discardedCards.addAll(selectedCards);
		handPanel.numSelected = 0;
		Player.deck.drawTo(8);
		DeckUtils.sortCardVector(Player.deck.drawnCards, DeckUtils.SORT_RANK);
		
		handPanel.setBorderTitle("Your Hand: " + Player.deck.size() + " / " + Player.deck.totalCards());
		handPanel.rebuildLayeredPane();
		
		leftPanel.useHand(playedHand.getScore());
		if(leftPanel.scoreReached()) {
			successfulBlind();
		} else if(leftPanel.outOfHands()) {
			consolePanel.appendText("Game Over!");
			resetDeck();
			homeScreen();
		}
		currBlind = "None";
	}
	
	public void successfulBlind() {
		consolePanel.appendText("Score Reached!");
		anteSelect.blindSuccess();
		anteSelect.incrementBlinds();
		
		if(!currBlind.equals("Big Blind") && !currBlind.equals("Small Blind")) {
			// If the current blind is not small or big, then it must be the Boss Blind. In that case, we need to generate a new set of blinds
			ante += 1;
			anteSelect = new AnteSelect(this, Player.getBaseChips(ante));
		}
		
		currBlind = "None";
		leftPanel.updateInfo(currBlind, 0, 0.0, Player.getNumHands(), Player.getNumDiscards(), Player.money);
		switchHandPanelAnteSelect("ANTE");
		resetDeck();
	}
	
	public void resetDeck() {
		for(int i = 0; i < Player.deck.drawnCards.size(); i++) {
			Player.deck.drawnCards.get(i).isSelected = false;	
		}
		Player.deck.resetDeck();
	}

	@Override
	public void discardHandPressed() {
		if(leftPanel.currBlind.equals("None")) return;
		
		if(leftPanel.numDiscards <= 0) {
			consolePanel.appendText("No discards remaining!");
			return;
		}
		
		// Pulls the selected cards
		Vector<PlayingCard> selectedCards = new Vector<PlayingCard>(0);
		for(int i = 0; i < Player.deck.drawnCards.size(); i++) {
			if(Player.deck.drawnCards.get(i).isSelected) {
				Player.deck.drawnCards.get(i).isSelected = false;
				selectedCards.add(Player.deck.drawnCards.get(i));
				Player.deck.drawnCards.remove(i);
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
		Player.deck.discardedCards.addAll(selectedCards);
		handPanel.numSelected = 0;
		Player.deck.drawTo(8);
		DeckUtils.sortCardVector(Player.deck.drawnCards, DeckUtils.SORT_RANK);
		
		// Updates some information
		handPanel.setBorderTitle("Your Hand: " + Player.deck.size() + " / " + Player.deck.totalCards());
		handPanel.rebuildLayeredPane();
		leftPanel.useDiscard();
	}
	
	@Override
	public void sortByRankPressed() {
		if(leftPanel.currBlind.equals("None")) return;
		DeckUtils.sortCardVector(Player.deck.drawnCards, DeckUtils.SORT_RANK);
		handPanel.rebuildLayeredPane();
	}
	
	@Override
	public void sortBySuitPressed() {
		if(leftPanel.currBlind.equals("None")) return;
		DeckUtils.sortCardVector(Player.deck.drawnCards, DeckUtils.SORT_SUIT);
		handPanel.rebuildLayeredPane();
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(BalatroUI::new);
    }


	@Override
	public void analyzeButtonPressed() {
		// If the button is pressed outside of a blind, perform deck analysis
		System.out.println(leftPanel.currBlind);
		if(leftPanel.currBlind.equals(AnteSelect.NONE)) {
		    copilotPanel.copilotMessage.appendText("Analyzing Deck");
		    
		    analysisWorker = new SwingWorker<>() {
		        @Override
		        protected Object doInBackground() throws Exception {
		            // Background work
		        	PokerHandProbabilityTable table = copilot.analyzeDeck(gamestate.getCurrDeck(), player.getPokerHandTable());
		            copilotPanel.addTab(table, "Deck Analysis");
		            return table;
		        }

		        @Override
		        protected void done() {
		        	 try {
		                 if (isCancelled()) {
		                     copilotPanel.copilotMessage.appendText("Analysis canceled.");
		                     return;
		                 }
		                 var result = get();
		                 copilotPanel.copilotMessage.appendText("Analysis complete!");
		             } catch (Exception e) {
		                 copilotPanel.copilotMessage.appendText("Error during analysis: " + e.getMessage());
		                 e.printStackTrace();
		             }
		        }
		    };
		}else {
		    copilotPanel.copilotMessage.appendText("Analyzing hand...");
		    
			analysisWorker = new SwingWorker<>() {
		        @Override
		        protected Object doInBackground() throws Exception {
		            // Background work
		            return copilot.analyzeGameState(gamestate);
		        }

		        @Override
		        protected void done() {
		        	 try {
		                 if (isCancelled()) {
		                     copilotPanel.copilotMessage.appendText("Analysis canceled.");
		                     return;
		                 }
		                 var result = get();
		                 copilotPanel.copilotMessage.appendText("Analysis complete!");
		             } catch (Exception e) {
		                 copilotPanel.copilotMessage.appendText("Error during analysis: " + e.getMessage());
		                 e.printStackTrace();
		             }
		        }
		    };
		}

	    analysisWorker.execute();
	}

	@Override
	public void stopAnalysis() {
		if (analysisWorker != null && !analysisWorker.isDone()) {
	        analysisWorker.cancel(true); // tries to interrupt the background thread
	        copilotPanel.copilotMessage.appendText("Stopping analysis...");
	    }
	}
}

