/* Mastermind (GUI) Main.java
 * Project 6 submission by
 * Replace <...> with your actual data.
 * Charles Wang
 * 
 * 16335
 * Nicholas Metzger
 * 
 * 16350
 * Slip days used total: <1>
 * Fall 2015
 */
package application;

/**
 * This class is for storing the number of correct, wrong, and wrong positioned pegs for the guess
 * of either the computer or user.
 *
 */
public class CheckResults {

	private int correct;
	private int wrong;
	private int wrongPos;
	
	public CheckResults () {
		this.correct = 0;
		this.wrong = 0;
		this.wrongPos = 0;
	}

	public CheckResults (int correct, int wrong, int wrongPos) {
		this.correct = correct;
		this.wrong = wrong;
		this.wrongPos = wrongPos;
	}
	
	public int getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public int getWrong() {
		return wrong;
	}

	public void setWrong(int wrong) {
		this.wrong = wrong;
	}

	public int getWrongPos() {
		return wrongPos;
	}

	public void setWrongPos(int wrongPos) {
		this.wrongPos = wrongPos;
	}
	
	
	
	
}
