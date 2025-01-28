package data.deck;

import java.util.Vector;

import data.card.Card;

public class Deck implements DeckUtils {
	Vector<Card> cards;
	
	public Deck(Vector<Card> cards) {
		this.cards = cards;
	}
	
	public void shuffle() {
        cards = DeckUtils.shuffleCards(cards);
	}
	
	public boolean hasNext() {
		if(cards.size() > 0) {
			return true;
		}else{
			return false;
		}
	}
	
	public Card drawNext() {
		return cards.remove(0);
	}
	
	public String printDeck(String buffer) {
		return DeckUtils.printCardVector(cards, buffer + "   ");
	}

	public int size() {
		return cards.size();
	}

	public Vector<Card> cards() {
		return cards;
	}
}
