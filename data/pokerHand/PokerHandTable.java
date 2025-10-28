package data.pokerHand;

import java.util.Vector;

import data.player.DefaultPlayer;


/**
 * Table for storing PokerHand information
 * 
 * @author Elijah Reyna
 */
public class PokerHandTable {
	private Vector<PokerHand> pokerHands;
	
	public PokerHandTable() {
		pokerHands = DefaultPlayer.createDefaultPokerHandVector();
	}
	
	public PokerHandTable(Vector<PokerHand> pokerHands) {
		this.pokerHands = new Vector<PokerHand>(pokerHands.size());
		for(PokerHand pokerHand : pokerHands) {
			this.pokerHands.add(pokerHand);
		}
	}
	
	public PokerHand getPokerHand(String pokerHandName) {
		for(int i = 0; i < pokerHands.size(); i++) {
			if(pokerHands.get(i).getName().equals(pokerHandName)) {
				return pokerHands.get(i);
			}
		}
		return null;
	}
	
	public Vector<String> getPokerHandNames(){
		Vector<String> names = new Vector<String>(0);
		for(int i = 0; i < pokerHands.size(); i++) {
			names.add(pokerHands.get(i).getName());
		}
		return names;
	}
	
	public Vector<PokerHand> getPokerHandVector(){
		return pokerHands;
	}
	
	public String printPokerHandTable() {
		StringBuilder result = new StringBuilder();
		
		for(PokerHand pokerhand : pokerHands) {
			result.append(pokerhand.printPokerHand() + "\n");
		}
		
		return result.toString();
	}
}
