package game;

import java.util.List;
import java.util.Vector;
import data.card.PlayingCard;
import data.deck.Deck;
import data.deck.DeckUtils;
import data.player.Player;


/**
 * Class for storing information about the game state
 * 
 * @author Elijah Reyna
 */
public class GameState {
	public int minimumScore;
	int currScore;
	
	int numDiscards;
	
	Player player;
	Deck currDeck;
	Deck discard;
	
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
	}
	
	public List<PlayingCard> getCurrHand() {
		return currDeck.drawnCards;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Deck getCurrDeck() {
		return currDeck;
	}
	
	
	public void draw() {
		currDeck.draw(8);
		//FIXME the draw should change based on the player's hand size
	}
	
	
	public String printState() {
		String result = "";
		result += Integer.toString(currScore) + " / " + Integer.toString(minimumScore) + "\n";
		result += "Hand:   ";
		result += DeckUtils.printCardVector(currDeck.drawnCards, "   ") + "\n";
		result += "Deck:   ";
		result += currDeck.printDeck("");
		
		return result;
	}
	
}
