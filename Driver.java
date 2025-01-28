
import copilot.Copilot;
import data.deck.Deck;
import data.deck.DeckBuilder;
import data.player.DefaultPlayer;
import data.player.Player;
import game.GameState;

public class Driver {
	public static final int ANALYZE_GAME_STATE = 0;
	public static final int COMPARE_DECK_PROBABILITIES = 1;
	public static final int FIND_PLAYABLE_HANDS = 2;
	public static final int SCAN_DISCARD_COMBINATIONS = 3;
	
	public static void main(String[] args) {
		Copilot copilot = new Copilot();
		int OPTION = SCAN_DISCARD_COMBINATIONS;
		
		if(OPTION == ANALYZE_GAME_STATE) {
			Player testPlayer = DefaultPlayer.createDefaultPlayer();
			GameState game = new GameState(testPlayer, 600);
			copilot.analyzeGameState(game);
		}else if(OPTION == COMPARE_DECK_PROBABILITIES) {
			Player testPlayer = DefaultPlayer.createDefaultPlayer();
			int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
			char[] suits = {'S', 'H', 'C', 'D'};
			Deck testDeck = DeckBuilder.generateDeck(values, suits);
			System.out.println("DEFAULT DECK");
			copilot.analyzeDeck(testDeck, testPlayer.getPokerHandTable());
			
			System.out.println("ABANDONED DECK");
			testDeck = DeckBuilder.buildDeck(DeckBuilder.ABANDONED_DECK);
			copilot.analyzeDeck(testDeck, testPlayer.getPokerHandTable());
			
			System.out.println("PAINTED DECK");
			testDeck = DeckBuilder.buildDeck(DeckBuilder.PAINTED_DECK);
			copilot.analyzeDeck(testDeck, testPlayer.getPokerHandTable());
		}else if(OPTION == FIND_PLAYABLE_HANDS) {
			Player testPlayer = DefaultPlayer.createDefaultPlayer();
			GameState game = new GameState(testPlayer, 600);
		}else if(OPTION == SCAN_DISCARD_COMBINATIONS) {
			
			Player testPlayer = DefaultPlayer.createTestPlayer();
			GameState game = new GameState(testPlayer, 600);
			copilot.analyzeGameState(game);
			
			System.out.println(copilot.printProbabilityTableOfPotentialHands(game));
		}

	}
	
}
