/* Mastermind (GUI) Main.java
 * EE422C Project 6 submission by
 * Replace <...> with your actual data.
 * Charles Wang
 * cdw2594
 * 16335
 * Nicholas Metzger
 * npm392
 * 16350
 * Slip days used total: <1>
 * Fall 2015
 */
package application;

/**
 * This class is to keep track of the guess, reply, and the result of each attempt to guess
 * the unknown code. This is used to return the history to the user and to store the history
 * in guesshistory.
 *
 */
public class History {
	private String guess;
	private String reply;
	private CheckResults pegs;
	
	public History(String guess, String reply, CheckResults pegs) {
		super();
		this.guess = guess;
		this.reply = reply;
		this.pegs = pegs;
	}
	public History() {
		super();
	}
	public String getGuess() {
		return guess;
	}
	public void setGuess(String guess) {
		this.guess = guess;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public CheckResults getPegs() {
		return pegs;
	}
	public void setPegs(CheckResults pegs) {
		this.pegs = pegs;
	}
	@Override
	public String toString() {
		return "guess = " + guess + ", reply = " + reply;
	}
	
	
	
	
}
