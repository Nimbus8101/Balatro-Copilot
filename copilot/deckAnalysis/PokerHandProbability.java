package copilot.deckAnalysis;


/**
 * Class which stores some data regarding a PokerHand for the PokerHandProbabilityTable
 * 
 * @author Elijah
 */
public class PokerHandProbability {
	// ============ Poker Hand Variables ============ //
	private String handName;
	private double highestScore;
	private double lowestScore;
	private double averageScore;
	private int handCount;
	
	
	/**
	 * Default constructor which initializes all fields to 0
	 * @param handName
	 */
	public PokerHandProbability(String handName) {
		this.handName = handName;
		highestScore = 0;
		lowestScore = 0;
		averageScore = 0.0;
		handCount = 0;
	}
	
	
	// ======= Basic Setters and Getters ======= //
	public String getName() {
		return handName;
	}
	
	public int getCount() {
		return handCount;
	}
	
	public void setCount(int count) {
		handCount += count;
	}
	
	public double getHighScore() {
		return highestScore;
	}
	
	public void setHighScore(double newScore) {
		highestScore = newScore;
	}
	
	public double getLowScore() {
		return lowestScore;
	}
	public void setLowScore(double newScore) {
		lowestScore = newScore;
	}
	
	public double getAverageScore() {
		return averageScore;
	}
	
	public void combineAvg(double newAvg, int newCount) {		
		averageScore = ((averageScore * handCount) + (newAvg * newCount)) / (handCount + newCount);
	}
	
	
	/**
	 * Adds a score to the stored data
	 * @param score (double) Score to add
	 */
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
