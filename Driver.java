
import data.player.DefaultPlayer;
import data.player.Player;
import game.GameState;

public class Driver {
	public static void main(String[] args) {
		Player testPlayer = DefaultPlayer.createDefaultPlayer();
		
		GameState game = new GameState(testPlayer, 600);
		
		System.out.println(game.printState());
		
		
	}
	
}
