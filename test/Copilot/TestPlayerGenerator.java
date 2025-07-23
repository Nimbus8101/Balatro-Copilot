package test.Copilot;

import java.util.Vector;

import data.card.Joker;
import data.deck.DeckBuilder;
import data.player.Player;
import data.pokerHand.PokerHandTable;
import data.player.DefaultPlayer;

public interface TestPlayerGenerator{
	public static Player CreateTestPlayer(String name) {
		if(name == "test_1") {
			return createTestPlayer();
		}else {
			return DefaultPlayer.createDefaultPlayer();
		}
	}
	
	public static Player createTestPlayer() {
		int[] values = {2, 3, 4, 5, 6, 7, 8};
		char[] suit = {'S', 'C'};
		return new Player(DeckBuilder.generateDeck(values, suit), new PokerHandTable(DefaultPlayer.createDefaultPokerHandVector()), new Vector<Joker>(0));
	}
}
