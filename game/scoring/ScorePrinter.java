package game.scoring;

import java.util.Vector;

public interface ScorePrinter {
	public static final int NUM_BINS = 20;
	
	static boolean DEBUG = false;
	
	public static String graphScores(Vector<PlayedHand> scores) {
		
		scores = sortByScore(scores);
		
		int[] partitionValues = generatePartitionValues(scores);
		int[] scoreCounts = countScoresInEachPartition(scores, partitionValues);
		
		String result = "Range -- Score Count\n";
		for(int i = 0; i < partitionValues.length - 1; i++) {
			result += Integer.toString(partitionValues[i]) + " <= x < " + Integer.toString(partitionValues[i + 1]) + " -- " + Integer.toString(scoreCounts[i]) + "\n";
		}	
		result += "Counted " + countScores(scoreCounts) + " out of " + scores.size() + " scores.\n";		
		return result;
	}
	
	public static Vector<PlayedHand> sortByScore(Vector<PlayedHand> scores) {
		Vector<PlayedHand> sortedScores = new Vector<PlayedHand>(0);
		int lowIndex;
		
		
		//FIXME could try a faster sorting algorithm
		while(scores.size() > 0) {
			lowIndex = 0;
			double lowScore = scores.get(0).getScore();
			for(int i = 1; i < scores.size(); i++) {
				if(scores.get(i).getScore() < lowScore) {
					lowScore = scores.get(i).getScore();
					lowIndex = i;
				}
			}
			sortedScores.add(scores.remove(lowIndex));
		}
		
		if(DEBUG) {
			System.out.println("Sorted Scores: ");
			for(int i = 0; i < sortedScores.size(); i++){
				System.out.println(sortedScores.get(i).getScore());
			}
		}
		
		return sortedScores;
	}
	
	public static int[] generatePartitionValues(Vector<PlayedHand> scores) {
		double lowScore = scores.get(0).getScore();
		double highScore = scores.get(scores.size() - 1).getScore();
		double increment = (highScore - lowScore) / NUM_BINS;
		int[] partitionValues = new int[NUM_BINS + 1];
		
		for(int i = 0; i < partitionValues.length; i++) {
			partitionValues[i] = (int) (lowScore + Math.round(i * increment));
		}
		
		if(DEBUG) {
			System.out.println("Increment: " + increment);
			for(int i = 0; i < partitionValues.length; i++) {
				System.out.println("Partition " + Integer.toString(i) + " = " + Integer.toString(partitionValues[i]));
			}
		}
		
		return partitionValues;
	}
	
	public static int[] countScoresInEachPartition(Vector<PlayedHand> scores, int[] partitionValues) {
		int[] scoreCount = new int[partitionValues.length - 1];
		
		int beginningIndex = 0;
		for(int i = 0; i < partitionValues.length - 2; i++) {
			int endingIndex = findIndexOfFirstValueWithinPartition(partitionValues[i + 1], scores, beginningIndex);	
			//if(DEBUG) System.out.println(endingIndex + " " + beginningIndex);
			scoreCount[i] = endingIndex - beginningIndex;
			beginningIndex = endingIndex;
		}
		
		int endingIndex = findIndexOfLastValueWithinPartition(partitionValues[partitionValues.length - 1], scores, beginningIndex);
		//if(DEBUG) System.out.println(endingIndex + " " + beginningIndex);
		scoreCount[partitionValues.length - 2] = endingIndex - beginningIndex;		
		return scoreCount;
	}
	
	
	
	public static int findIndexOfFirstValueWithinPartition(int value, Vector<PlayedHand> scores, int beginningIndex) {
		beginningIndex = binarySearch(scores, value, beginningIndex, scores.size() - 1);
		while(beginningIndex - 1 > 0 && scores.get(beginningIndex - 1).getScore() > value) {
			beginningIndex -= 1;
		}
		return beginningIndex;
	}
	
	public static int findIndexOfLastValueWithinPartition(int value, Vector<PlayedHand> scores, int beginningIndex) {
		beginningIndex = binarySearch(scores, value, beginningIndex, scores.size() - 1);
		while(beginningIndex < scores.size() && scores.get(beginningIndex).getScore() <= value) {
			beginningIndex += 1;
		}
		return beginningIndex;
	}
	
	public static int binarySearch(Vector<PlayedHand> scores, int partitionValue, int low, int high) {
		int mid = (low + high) / 2;
		
		if(DEBUG) System.out.println(scores.get(mid).getScore() + ".." + partitionValue + ": " + low + " " + high + " " + mid);
		
		if(partitionValue == scores.get(mid).getScore()) {
			return mid;
		}else if(partitionValue > scores.get(mid).getScore()) {
			return binarySearch(scores, partitionValue, mid + 1, high);
		}else if(partitionValue < scores.get(mid).getScore()){
			return binarySearch(scores, partitionValue, low, mid - 1);
		}else {
			System.out.println("There was an error in the findIndexOfSplit function in ScorePrinter.java");
			return -1;
		}
	}
	
	private static int countScores(int[] scoreCounts) {
		int sum = 0;
		for(int i = 0; i < scoreCounts.length; i++) {
			sum += scoreCounts[i];
		}
		return sum;
	}
}

