package game;

import java.util.Vector;

import data.card.Card;
import data.deck.Deck;
import data.player.Player;

public class TestGameState extends GameState{

	public TestGameState(Player player, int minimumScore) {
		super(player, minimumScore);
		// TODO Auto-generated constructor stub
	}
	
	public void setCurrDeck(Deck deck) {
		this.currDeck = deck;
		currDeck.shuffle();
	}
	
	public void setCurrHand(Vector<Card> currHand) {
		this.currHand = currHand;
	}

}
