package game;

import java.util.Vector;

import data.card.Card;
import data.card.PlayingCard;
import data.deck.Deck;
import data.player.Player;


/**
 * Class for generating and using a TestGameState
 * 
 * @author Elijah Reyna
 */
public class TestGameState extends GameState{

	public TestGameState(Player player, int minimumScore) {
		super(player, minimumScore);
		// TODO Auto-generated constructor stub
	}
	
	public void setCurrDeck(Deck deck) {
		this.currDeck = deck;
		currDeck.shuffle();
	}
	
	public void setCurrHand(Vector<PlayingCard> currHand) {
		this.currHand = currHand;
	}

}
