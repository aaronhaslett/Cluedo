package cluedo.util;

/**
 * @author hardwiwill
 * Holds two dice, which can be rolled
 * Dice values can be accessed as a sum or individual dice
 */
public class TwoDice {

	private int d1;
	private int d2;

	/**
	 * generates a value with the same range and distribution as
	 * the sum of the result of two random dice values
	 */
	public void roll(){
		d1 = (int)((Math.random()*6)+1);
		d2 = (int)((Math.random()*6)+1);
	}

	public int getTotal() {
		return d1 + d2;
	}

	public int getDice1Value(){
		return d1;
	}

	public int getDice2Value(){
		return d2;
	}

}
