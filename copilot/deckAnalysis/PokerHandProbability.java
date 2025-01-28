package copilot.deckAnalysis;

public class PokerHandProbability {
	private String handName;
	private double highestScore;
	private double lowestScore;
	private double averageScore;
	private int handCount;
	
	public PokerHandProbability(String handName) {
		this.handName = handName;
		highestScore = 0;
		lowestScore = 0;
		averageScore = 0.0;
		handCount = 0;
	}
	
	public String getName() {
		return handName;
	}
	
	public int getCount() {
		return handCount;
	}
	
	public double getHighScore() {
		return highestScore;
	}
	
	public double getLowScore() {
		return lowestScore;
	}
	
	public double getAverageScore() {
		return averageScore;
	}
	
	public void addScore(double score) {
		if(handCount == 0) {
			highestScore = score;
			lowestScore = score;
		}
		if(score > highestScore) {
			highestScore = score;
		}
		if(score < lowestScore) {
			lowestScore = score;
		}
		double avg = averageScore;
		double count = handCount;
		averageScore = ((avg * count) + score) / (count + 1);
		handCount += 1;
	}
}
