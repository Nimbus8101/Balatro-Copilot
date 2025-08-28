package game;

import java.util.Vector;
import data.card.PlayingCard;
import data.deck.Deck;
import data.deck.DeckUtils;
import data.player.Player;

public class GameState {
	int minimumScore;
	int currScore;
	
	int numDiscards;
	
	Player player;
	Deck currDeck;
	Deck discard;
	Vector<PlayingCard> currHand;
	
	/**
	 * Constructor to create a "default" game state (turn 1)
	 * @param player The player data
	 * @param minimumScore The minimum score of the round
	 */
	public GameState(Player player, int minimumScore) {
		this.player = player;
		this.minimumScore = minimumScore;
		
		numDiscards = player.getNumDiscards();
		
		currDeck = player.getDeck();
		discard = new Deck(new Vector<PlayingCard>(0));
		currDeck.shuffle();
		currHand = new Vector<PlayingCard>(0);
	}
	
	public Vector<PlayingCard> getCurrHand() {
		return currHand;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Deck getCurrDeck() {
		return currDeck;
	}
	
	
	public void draw() {
		currHand = DeckUtils.draw(currHand, 8, currDeck);
		//FIXME the draw should change based on the player's hand size
	}
	
	
	
	
	public String printState() {
		String result = "";
		result += Integer.toString(currScore) + " / " + Integer.toString(minimumScore) + "\n";
		result += "Hand:   ";
		result += DeckUtils.printCardVector(currHand, "   ") + "\n";
		result += "Deck:   ";
		result += currDeck.printDeck("");
		
		return result;
	}
	
}
