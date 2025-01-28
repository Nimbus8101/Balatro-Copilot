package game.scoring;

import java.util.Vector;

import data.card.Card;
import data.player.DefaultPlayer;
import data.pokerHand.PokerHand;
import data.pokerHand.PokerHandIdentifier;
import data.pokerHand.PokerHandTable;

public interface HandScorer extends ValueCountUtils{
	
	/**
	 * Function to score a hand of 5
	 * @param cards Cards that were played
	 * @param pokerHandTable
	 * @return
	 */
	public static PlayedHand scorePlayedHand(Vector<Card> cards, PokerHandTable pokerHandTable) {
		PlayedHand playedHand = PokerHandIdentifier.identifyPlayedHand(cards);
		scoreHand(playedHand, pokerHandTable);
		return playedHand;
	}
	
	public static double scoreHand(PlayedHand playedHand, PokerHandTable pokerHandTable) {
		String handType = playedHand.getHandType();
		playedHand.addChips(pokerHandTable.getPokerHand(handType).getChips());
		playedHand.addMult(pokerHandTable.getPokerHand(handType).getMult());
		
		//Perform other chip and mult changes
		
		playedHand.score();
		return playedHand.getScore();
	}
	
}
