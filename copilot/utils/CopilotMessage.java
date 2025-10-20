package copilot.utils;

import java.util.Vector;

import data.card.Card;
import data.card.PlayingCard;
import data.deck.DeckUtils;
import game.scoring.PlayedHand;


/**
 * Class for printing messages from the copilot
 * 
 * @author Elijah
 */
public interface CopilotMessage {
	
	public static String printPlayableHands(Vector<PlayingCard> hand, Vector<PlayedHand> playableHands) {
		String result = "";
		result += "[Copilot] - Given a hand of: " + DeckUtils.printCardVector(hand, "") + "\n";
		result += "[Copilot] - You can play: \n";
		for(int i = 0; i < playableHands.size(); i++) {
			result += "   " + padString(Integer.toString(i + 1) + ". ", 5);
			result += padString(playableHands.get(i).getHandType(), 16);
			result += " = " + padString(String.format("%.2f", playableHands.get(i).getScore()) + " pts. ", 10);
			result += "[ " + DeckUtils.printCardVector(playableHands.get(i).getPlayedCards(), "") + "]";
			result += "\n";
		}
		return result;
	}
	
	
	/**
	 * 
	 * @param hand
	 * @param probabilityTable
	 * @return
	 */
	public static String printPotentialHandProbabilityTable(Vector<PlayingCard> hand, String probabilityTable) {
		String result = "";
		result += "[Copilot] - Given a hand of: " + DeckUtils.printCardVector(hand, "") + " and ONE discard\n";
		result += "[Copilot] - Here are the probabilities of obtaining your poker hands: \n";
		result += probabilityTable + "\n";
		return result;
	}
	
	private static String padString(String string, int totalLength) {
		int difference = totalLength - string.length();
		String pad1;
		String pad2;
		
		pad1 = generatePadding(difference / 2);

		if(difference % 2 == 1) {
			pad2 = generatePadding((difference / 2) + 1);
		}else {
			pad2 = generatePadding(difference / 2);
		}
		
		return pad1 + string + pad2;
	}
	
	private static String generatePadding(int length) {
		String result = "";
		for(int i = 0; i < length; i++) {
			result += " ";
		}
		return result;
	}
}
