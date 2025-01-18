package game.scoring;

import java.util.Vector;

public interface ScorePrinter {
	public static final int NUM_BINS = 20;
	
	static boolean DEBUG = true;
	
	public static String graphScores(Vector<HandScore> scores) {
		
		scores = sortByScore(scores);
		
		int[] partitionValues = generatePartitionValues(scores);
		int[] scoreCounts = countScoresInEachPartition(scores, partitionValues);
		
		String result = "";
		for(int i = 0; i < partitionValues.length - 1; i++) {
			result += Integer.toString(i + 1) + ". " + Integer.toString(partitionValues[i]) + " <= x < " + Integer.toString(partitionValues[i + 1]) + "   " + Integer.toString(scoreCounts[i]) + "\n";
		}		
		result += "Calculated Total = ";
		
		int sum = 0;
		for(int i = 0; i < scoreCounts.length; i++) {
			sum += scoreCounts[i];
		}
		result += Integer.toString(sum) + "\n";
		
		result += "Total: " + scores.size() + "\n";
		
		for(int i = 0; i < scores.size(); i++) {
			if(scores.get(i).counted == false) {
				System.out.print(scores.get(i).getScore() + " ");
			}
		}
		
		return result;
	}
	
	public static Vector<HandScore> sortByScore(Vector<HandScore> scores) {
		Vector<HandScore> sortedScores = new Vector<HandScore>(0);
		int lowIndex;
		
		while(scores.size() > 0) {
			lowIndex = 0;
			int lowScore = scores.get(0).getScore();
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
	
	public static int[] generatePartitionValues(Vector<HandScore> scores) {
		int lowScore = scores.get(0).getScore();
		int highScore = scores.get(scores.size() - 1).getScore();
		
		int[] partitionValues = new int[NUM_BINS + 1];
		System.out.println(highScore + "    " + lowScore);
		double increment = ((double)(highScore - lowScore)) / NUM_BINS;
		
		System.out.println("Increment: " + increment);
		
		for(int i = 0; i < partitionValues.length; i++) {
			partitionValues[i] = (int) (lowScore + Math.round(i * increment));
		}
		
		if(DEBUG) {
			for(int i = 0; i < partitionValues.length; i++) {
				System.out.println("Partition " + Integer.toString(i) + " = " + Integer.toString(partitionValues[i]));
			}
		}
		return partitionValues;
	}
	
	public static int[] countScoresInEachPartition(Vector<HandScore> scores, int[] partitionValues) {
		int[] scoreCount = new int[partitionValues.length - 1];
		
		int beginningIndex = 0;
		
		for(int i = 0; i < partitionValues.length - 1; i++) {
			int endingIndex = findFirstOccurenceOfValueInVector(partitionValues[i + 1], scores, beginningIndex);
			
			if(DEBUG) System.out.println(endingIndex + " " + beginningIndex);
			
			for(int j = beginningIndex; j < endingIndex; j++) {
				scores.get(j).counted = true;
			}
			
			scoreCount[i] = endingIndex - beginningIndex;
			beginningIndex = endingIndex;
		}
		//scoreCount[NUM_BINS - 1] = scores.size() - 1 - beginingIndex;
		
		return scoreCount;
	}
	
	public static int findFirstOccurenceOfValueInVector(int value, Vector<HandScore> scores, int beginningIndex) {
		while(beginningIndex - 1 > 0 && scores.get(beginningIndex - 1).getScore() > value) {
			beginningIndex -= 1;
		}
		return beginningIndex;
	}
	
	/**
	public static int findFirstOccurenceOfValueInVector(int value, Vector<HandScore> scores, int beginningIndex) {
		beginningIndex = binarySearch(scores, value, beginningIndex, scores.size() - 1);
		while(beginningIndex - 1 > 0 && scores.get(beginningIndex - 1).getScore() > value) {
			beginningIndex -= 1;
		}
		return beginningIndex;
	}
	
	public static int findLastOccurenceOfValueInVector(int value, Vector<HandScore> scores, int beginningIndex) {
		beginningIndex = binarySearch(scores, value, beginningIndex, scores.size() - 1);
		while(beginningIndex + 1 < scores.size() && scores.get(beginningIndex + 1).getScore() <= value) {
			beginningIndex += 1;
		}
		return beginningIndex;
	}
	*/
	
	public static int binarySearch(Vector<HandScore> scores, int partitionValue, int low, int high) {
		int mid = (low + high) / 2;
		
		if(DEBUG) System.out.println(scores.get(mid).getScore() + ".." + partitionValue + ": " + low + " " + high + " " + mid);
		
		if(partitionValue == scores.get(mid).getScore()) {
			return findFirstOccurenceOfValueInVector(partitionValue, scores, mid);
		}else if(partitionValue > scores.get(mid).getScore()) {
			return binarySearch(scores, partitionValue, mid + 1, high);
		}else if(partitionValue < scores.get(mid).getScore()){
			return binarySearch(scores, partitionValue, low, mid - 1);
		}else {
			System.out.println("There was an error in the findIndexOfSplit function in ScorePrinter.java");
			return -1;
		}
	}
}
