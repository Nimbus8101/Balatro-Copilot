package game.UI;

import data.card.Card;
import data.player.Player;
import game.GameState;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class BalatroUI extends JFrame implements AnteSelectListener, ButtonPanelListener{
	
	Player player;
	int ante = 1;
	int round = 0;
	
	// The various panels that will be used
	Container pane;
	LeftPanel leftPanel;
	JokerPanel jokerPanel;
	ConsumablePanel consumablePanel;
	PlayArea playArea;
	JPanel buttonPanel;
	CopilotPanel copilotPanel;

    public BalatroUI() {
        setTitle("Balatro Clone UI - GridBagLayout");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximize window
        setUndecorated(false); // Set to true if you want fullscreen with no title bar

        pane = getContentPane();
        pane.setLayout(new GridBagLayout());
        
        // ==== LEFT PANEL (Game Stats) ====
        leftPanel = new LeftPanel();
        pane.add(leftPanel, LeftPanel.getGBC());

        
        // ==== TOP LEFT: Joker Panel ====
        jokerPanel = new JokerPanel();
        pane.add(jokerPanel, JokerPanel.getGBC());

        
        // ==== TOP RIGHT: Consumable Panel ====
        consumablePanel = new ConsumablePanel();
        pane.add(consumablePanel, ConsumablePanel.getGBC());


        // ==== CENTER PANEL (Hand Cards) ====
        playArea = new AnteSelect(this, ante);
        playArea.setBorder(BorderFactory.createTitledBorder("Ante Select"));
        pane.add(playArea, PlayArea.getGBC());

        
        // ==== BOTTOM PANEL (Action Buttons) ====
        buttonPanel = new ButtonPanel(this);
        pane.add(buttonPanel, ButtonPanel.getGBC());
        
        
        // ==== RIGHT PANEL PLACEHOLDER (Future Content) ====
        copilotPanel = new CopilotPanel();
        pane.add(copilotPanel, CopilotPanel.getGBC());

        setVisible(true);
        
        player = new Player();
    }

	@Override
	public void onBlindSelected(String blindName) {
		System.out.println("Blind selected: " + blindName);
		
		pane.remove(playArea);
		playArea = new HandPanel();
		pane.add(playArea, PlayArea.getGBC());
		
		startBlind();
		
		getContentPane().revalidate();
		getContentPane().repaint();
		System.out.println("Hand updated");
	}

	public void startBlind() {
		player.deck.shuffle();
		player.deck.draw(8);
		playArea.deck = player.deck;
		//playArea.handPanel.updateHand();
	}

	@Override
	public void onBlindSkipped() {
		System.out.println("Blind skipped.");
		
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(BalatroUI::new);
    }

	@Override
	public void playHandPressed() {
		Vector<Card> selectedCards = new Vector<Card>(0);
		
		for(int i = 0; i < player.deck.drawnCards.size(); i++) {
			if(player.deck.drawnCards.get(i).isSelected) {
				selectedCards.add(player.deck.drawnCards.get(i));
				// FIXME When cards re selected they should go to discard pile and more should be drawn
			}
		}
		
		System.out.println("Selected Cards:");
		for(int i = 0; i < selectedCards.size(); i++) {
			System.out.println(selectedCards.get(i).printCard());
		}		
	}

	@Override
	public void discardHandPressed() {
		Vector<Card> selectedCards = new Vector<Card>(0);
		
		for(int i = 0; i < player.deck.drawnCards.size(); i++) {
			if(player.deck.drawnCards.get(i).isSelected) {
				selectedCards.add(player.deck.drawnCards.get(i));
				// FIXME When cards re selected they should go to discard pile and more should be drawn
			}
		}
		
		System.out.println("Selected Cards:");
		for(int i = 0; i < selectedCards.size(); i++) {
			System.out.println(selectedCards.get(i).printCard());
		}		
	}
}

