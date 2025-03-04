package copilot;

import java.util.Vector;

import copilot.deckAnalysis.DeckAnalysis;
import copilot.deckAnalysis.PokerHandProbabilityTable;
import copilot.handAnalysis.PlayableHandsFinder;
import copilot.handAnalysis.PotentialHandsFinder;
import copilot.utils.Combination;
import copilot.utils.CopilotMessage;
import data.card.Card;
import data.deck.Deck;
import data.pokerHand.PokerHandTable;
import game.GameState;
import game.scoring.PlayedHand;

public class Copilot implements PlayableHandsFinder{
	
	Combination combination = new Combination();
	GameState gameState;

	public Copilot() {}
	
	public void analyzeDeck(Deck deck, PokerHandTable table) {
		DeckAnalysis d = new DeckAnalysis(deck, table);
		System.out.println("[Copilot] - Your deck has the following Poker Hand probabilities:");
		System.out.println(d.printProbabilityTable());
	}
	
	public void analyzeGameState(GameState gameState) {
		this.gameState = gameState;
		//Find playable hands
		
		System.out.println(CopilotMessage.printPlayableHands(gameState.getCurrHand(), findPlayableHands(gameState.getCurrHand())));
		
		//Analyze all possible hands that could be generated by discards
		
		//System.out.println(CopilotMessage.printPotentialHandProbabilityTable(gameState.getCurrHand(), printProbabilityTableOfPotentialHands(gameState)));
		
	}
	
	public Vector<PlayedHand> findPlayableHands(Vector<Card> currHand){
		return PlayableHandsFinder.findPlayableHands(currHand);
	}
	
	public Vector<PlayedHand> findPotentialHands(GameState gameState){
		return PotentialHandsFinder.findPotentialHands(gameState);
	}
	
	public String printProbabilityTableOfPotentialHands(GameState gameState) {
		PotentialHandsFinder p = new PotentialHandsFinder(gameState);
		return p.generateProbabilityTableOfPotentialHands(gameState).printTable();
	}
}
