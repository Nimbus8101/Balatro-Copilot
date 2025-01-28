package test;

import java.util.Random;
import java.util.Vector;

import data.card.Card;
import data.card.CardPrinter;
import data.deck.DeckUtils;

public interface ScoringTesterUtils {
		
	public static Vector<Card> generateRandomHand(int numCards){
		Vector<Card> cards = new Vector<Card>(0);
		
		CardPrinter cardPrinter = new CardPrinter();
		
		for(int i = 0; i < numCards; i++) {
			cards.add(cardPrinter.printRandomBasicCard());
		}
		
		DeckUtils.printCardVector(cards, "   ");
		
		return cards;
	}
}
