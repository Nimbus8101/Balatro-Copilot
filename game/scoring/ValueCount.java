package game.scoring;

public class ValueCount{
	int value;
	int count;
	
	public ValueCount(int value, int count) {
		this.value = value;
		this.count = count;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getCount() {
		return count;
	}
	
	public void increment(int value) {
		count += value;
	}
}
