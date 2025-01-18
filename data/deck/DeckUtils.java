package data.deck;

import java.util.Random;
import java.util.Vector;

import data.card.Card;

public interface DeckUtils {
	public static Vector<Card> shuffleCards(Vector<Card> cards){
		Vector<Card> shuffledDeck = new Vector<Card>(0);
		
		Random rand = new Random();
		int randInt;
		
		while(cards.size() > 0) {
			randInt = rand.nextInt(cards.size());
			shuffledDeck.add(cards.remove(randInt));
		}
		
        return shuffledDeck;
	}
	
	public static String printCardVector(Vector<Card> cards, String buffer) {
		String result = "";
		for(int i = 0; i < cards.size(); i++) {
			result += cards.get(i).printValueAndSuit() + " ";
		}
		result += "\n";
		return result;
	}
}
