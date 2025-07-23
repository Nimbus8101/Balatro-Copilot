package test.Copilot;

import java.util.Vector;

import data.card.Joker;
import data.deck.DeckBuilder;
import data.player.Player;
import data.pokerHand.PokerHandTable;
import game.GameState;
import test.Copilot.TestPlayerGenerator;

public interface GameStateGenerator {
	
	public static GameState BasicGameState() {
		GameState gamestate = new GameState(TestPlayerGenerator.CreateTestPlayer("test_1"), 600);
		return gamestate;
	}
	
	public static GameState DefaultGameState() {
		GameState gamestate = new GameState(TestPlayerGenerator.CreateTestPlayer("default"), 600);
		return gamestate;
	}
}
