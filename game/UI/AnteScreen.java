package game.UI;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.GameState;

public class AnteScreen extends JFrame{
	GameState gameState;
	
	JPanel jokerPanel;
	JPanel handPanel;
	JPanel playingArea;
	JPanel scorePanel;
	
	
	public AnteScreen(GameState gameState) {
		this.gameState = gameState;
		
		initializeScreen();
	}
	
	public void initializeScreen() {
		// FIXME Initialize the screen
	}
}
