package game;

import java.util.Vector;
import data.card.Card;
import data.card.Deck;
import data.card.DeckUtils;
import data.player.Player;

public class GameState {
	int minimumScore;
	int currScore;
	
	int numDiscards;
	
	Player player;
	Deck currDeck;
	Vector<Card> currHand;
	
	public GameState(Player player, int minimumScore) {
		this.player = player;
		this.minimumScore = minimumScore;
		
		numDiscards = player.getNumDiscards();
		
		currDeck = player.getDeck();
		currDeck.shuffle();
		currHand = HandUtils.draw(7, currDeck);
	}
	
	
	
	public String printState() {
		String result = "";
		result += Integer.toString(currScore) + " / " + Integer.toString(minimumScore) + "\n";
		result += "Hand: " + "\n";
		result += DeckUtils.printCardVector(currHand, "   ");
		result += "Deck: " + "\n";
		result += currDeck.printDeck("");
		
		return result;
	}
	
}
