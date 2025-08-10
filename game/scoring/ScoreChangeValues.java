package game.scoring;

public class ScoreChangeValues {
	public int chips;
	public int mult;
	double multiplier;
		
	public ScoreChangeValues() {
		this.chips = 0;
		this.mult = 0;
		this.multiplier = 1.0;
	}
	
	public ScoreChangeValues(int chips, int mult, double multiplier) {
		this.chips = chips;
		this.mult = mult;
		this.multiplier = multiplier;
	}
	
	public void addChips(int chips) {
		this.chips += chips;
	}
	
	public void addMult(int mult) {
		this.mult += mult;
	}
	
	public void addMultiplier(double multiplier) {
		this.multiplier *= multiplier;
	}
}
