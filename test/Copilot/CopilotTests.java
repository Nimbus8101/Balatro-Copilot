package test.Copilot;

import copilot.Copilot;
import data.deck.DeckTable;
import game.GameState;

public class CopilotTests {
	
	public static void main(String[] args) {
		Copilot copilot = new Copilot();
		GameState gamestate = GameStateGenerator.BasicGameState();
		
		DeckTable d = new DeckTable(gamestate.getCurrDeck().cards());
		//System.out.println(d.printDeckTable());
		
		//System.out.println(gamestate.getCurrDeck().printDeck(""));
		//copilot.analyzeDeck(gamestate.getCurrDeck(), gamestate.getPlayer().getPokerHandTable());
		
		gamestate.draw();
		
		copilot.analyzeGameState(gamestate);
	}
	
	public static void deckAnalysis() {
		
		
	}
}
