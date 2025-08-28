package test;

import java.util.Random;
import java.util.Vector;

import data.card.PlayingCard;
import data.card.CardPrinter;
import data.deck.DeckUtils;

public interface ScoringTesterUtils {
		
	public static Vector<PlayingCard> generateRandomHand(int numPlayingCards){
		Vector<PlayingCard> cards = new Vector<PlayingCard>(0);
		
		CardPrinter cardPrinter = new CardPrinter();
		
		for(int i = 0; i < numPlayingCards; i++) {
			cards.add(cardPrinter.printRandomBasicCard());
		}
		
		DeckUtils.printCardVector(cards, "   ");
		
		return cards;
	}
}
