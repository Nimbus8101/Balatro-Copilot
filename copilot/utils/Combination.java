package copilot.utils;

import java.util.Vector;

import data.card.Card;
import data.card.CardPrinter;

public class Combination {
	public Vector<Vector<Card>> allCombinations;
	public Vector<Vector<Integer>> allIndexCombinations;
	
	public Combination() {
		
	}
	
	public Combination(String args[]) {
		Card[] cardArray = new Card[5];
    	CardPrinter cardPrinter = new CardPrinter();
    	
    	for(int i = 0; i < cardArray.length; i++) {
    		cardArray[i] = cardPrinter.printRandomBasicCard();
    		System.out.println(cardArray[i].printCard());
    	}
    	
    	//n C r -> with n card take every Combination of r
        int r = 3;
        int n = cardArray.length;
        storeCombinations(cardArray, n, r);
	}
	
	public Vector<Vector<Integer>> generateAllIndexCombinations(int n, int r) {
		allIndexCombinations = new Vector<Vector<Integer>>(0);
		
		storeIndexCombinations(generateIndexArray(n), n, r);
		
		return allIndexCombinations;
	}
	
	private int[] generateIndexArray(int numIndexes) {
		int[] array = new int[numIndexes];
		
		for(int i = 0; i < numIndexes; i++) {
			array[i] = i;
		}
		return array;
	}
	
	private void storeIndexCombinations(int arr[], int n, int r) {
		//System.out.println("In storeIndexCombinations function");
        int data[] = new int[r];
        indexCombinationUtil(arr, data, 0, n-1, 0, r);
    }
	
	private void indexCombinationUtil(int arr[], int data[], int start, int end, int index, int r){
        // Current combination is ready to be printed, print it
        if (index == r){
        	Vector<Integer> combination = new Vector<Integer>(0);
            for (int j = 0; j < r; j++) {
            	combination.add(data[j]);
            	//System.out.println("Combination found!");
            }
            allIndexCombinations.add(combination);
            return;
        }
        for (int i = start; i <= end && end - i + 1 >= r - index; i++){
            data[index] = arr[i];
            indexCombinationUtil(arr, data, i+1, end, index+1, r);
        }
	}   
	
	public Vector<Vector<Card>> findAllCombinations(Vector<Card> cards, int r){   
		allCombinations = new Vector<Vector<Card>>(0);
		
    	//n C r -> with n card take every Combination of r
        int n = cards.size();
        storeCombinations(convertCardVectorToArray(cards), n, r);
        return allCombinations;
    }
	
	// The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    private void storeCombinations(Card arr[], int n, int r) {
        Card data[] = new Card[r];
        combinationUtil(arr, data, 0, n-1, 0, r);
    }
	
	 public static Card[] convertCardVectorToArray(Vector<Card> cards) {
    	Card[] cardArray = new Card[cards.size()];
    	
    	for(int i = 0; i < cards.size(); i++) {
    		cardArray[i] = cards.get(i);
    	}
    	
    	return cardArray;
    }
	
    /* arr[]  ---> Input Array
    data[] ---> Temporary array to store current combination
    start & end ---> Starting and Ending indexes in arr[]
    index  ---> Current index in data[]
    r ---> Size of a combination to be printed */
    private void combinationUtil(Card arr[], Card data[], int start, int end, int index, int r)
    {
        // Current combination is ready to be printed, print it
        if (index == r){
        	Vector<Card> combination = new Vector<Card>(0);
            for (int j = 0; j < r; j++) {
            	combination.add(data[j]);
                //System.out.print(data[j].printValueAndSuit() + " ");
            }
            allCombinations.add(combination);
            //System.out.println("");
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i = start; i <= end && end - i + 1 >= r - index; i++)
        {
            data[index] = arr[i];
            combinationUtil(arr, data, i+1, end, index+1, r);
        }
    }
}
