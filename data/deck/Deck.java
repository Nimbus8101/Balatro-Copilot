package data.deck;

import java.util.Collections;
import java.util.Vector;

import data.card.Card;
import data.card.PlayingCard;

public class Deck implements DeckUtils {
	public Vector<PlayingCard> cards;
	public Vector<PlayingCard> drawnCards;
	public Vector<PlayingCard> discardedCards;
	
	public Deck(Vector<PlayingCard> cards) {
		this.cards = cards;
		drawnCards = new Vector<PlayingCard>(0);
		discardedCards = new Vector<PlayingCard>(0);
	}
	
	public void resetDeck() {
		cards.addAll(drawnCards);
		cards.addAll(discardedCards);
		drawnCards = new Vector<PlayingCard>(0);
		discardedCards = new Vector<PlayingCard>(0);
	}
	
	
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	public boolean hasNext() {
		if(cards.size() > 0) {
			return true;
		}else{
			return false;
		}
	}
	
	public PlayingCard drawNext() {
		return cards.remove(0);
	}
	
	public void draw(int numDraw) {
		for(int i = 0; i < numDraw; i++) drawnCards.add(cards.remove(0));
	}
	
	public void drawTo(int max) {
		while(drawnCards.size() < max) {
			drawnCards.add(drawNext());
		}
	}
	
	public void sortCards(String sortType) {
		
	}
	
	public String printDeck(String buffer) {
		return DeckUtils.printCardVector(cards, buffer + "   ");
	}

	public int size() {
		return cards.size();
	}
	
	public int totalCards() {
		return cards.size() + drawnCards.size() + discardedCards.size();
	}

	public Vector<PlayingCard> cards() {
		return cards;
	}
}
