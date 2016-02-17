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

import java.util.ArrayList;

public class CompMethods {
	
	/**
	 * Takes in an integer as the max for finding a random integer between 0 and the max
	 */
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	/**
	 * This method is for the computer making a guess against the user's secret code.
	 * 
	 * @param guesshistory holds all the guesses
	 * @param colorpalette full of the colors
	 * @param solution the correct answer
	 * @param diff difficulty
	 * @return result the computer's Guess
	 */
	public static String compMakeGuess (ArrayList<History> guesshistory, ArrayList<String> colorpalette, String solution, int diff) {
		String result = new String();
		
		// if is empty, make random initial guess
		if (guesshistory.isEmpty()) {
			result = makeCode(colorpalette);
		}
		
		// make guess according to previous guess
		else if (diff == 2) { // HARD
			result = makeCode(colorpalette);
			while (!goodGuess(guesshistory, colorpalette, result, solution)) {
				result = makeCode(colorpalette);
			}
		}
		else if (diff == 0) { // EASY
			result = makeCode(colorpalette);
		}
		else { // MEDIUM
			result = bestMove(guesshistory, colorpalette, solution);
		}
		
		
		
		return result;
		
	}
	
	
	/**
	 * This makes a code of completely random colors using the getRandomInt method.
	 * 
	 * @param colorpalette list of colors
	 * @return result String
	 */
	public static String makeCode (ArrayList<String> colorpalette) {
		int [] init = new int[Params.CODE_LENGTH];
		String result = new String();
		for (int i = 0; i < Params.CODE_LENGTH; i++) {
			init[i] = getRandomInt(Params.NUMBER_COLORS);
			result = result.concat(colorpalette.get(init[i]));
		}
		return result;
	}
	
	/**
	 * This is for the medium difficulty mode. It makes a guess based on the number of the most previously correct pegs.
	 * It finds the String with the most correct. Takes the difference between the code length and the most correct and changes
	 * that many pegs to random colors.
	 * 
	 * @param guesshistory holds all guesses
	 * @param colorpalette list of colors
	 * @param solution the correct answer
	 * @return result the computer's guess
	 */
	private static String bestMove(ArrayList<History> guesshistory, ArrayList<String> colorpalette, String solution) {
		// TODO this method finds the best move according to pegs returned for previous move
		String result = new String();
		int max = 0;
		int maxPos = 0;
		int maxInd = -1;
		int maxPosInd = -1;
		for (int i = 0; i < guesshistory.size(); i++) {
			if (guesshistory.get(i).getPegs().getCorrect() >= max) {
				max = guesshistory.get(i).getPegs().getCorrect();
				maxInd = i;
			}
			if (guesshistory.get(i).getPegs().getWrongPos() >= max) {
				maxPos = guesshistory.get(i).getPegs().getCorrect();
				maxPosInd = i;
			}
		}
//		System.out.println(max);
		
		String before = guesshistory.get(maxInd).getGuess();
//		System.out.println(before);
		
		if (max == 0) {
			if (maxPos == 0) {
				result = makeCode(colorpalette);
				return result;
			}
			else {
				before = guesshistory.get(maxPosInd).getGuess();
			}
		}
		
		while (before.equals(result) || result.isEmpty()) {
			result = "";
			int change = Params.CODE_LENGTH - max;
			int [] array = new int[Params.CODE_LENGTH];
			while (change != 0) {
				int indChange = getRandomInt(Params.CODE_LENGTH);
				if (array[indChange] == 0) {
					array[indChange] = getRandomInt(Params.NUMBER_COLORS);
					change--;
				}
			}
			for (int i = 0; i < Params.CODE_LENGTH; i++) {
				if (array[i] == 0) {
					result = result.concat(Character.toString(before.charAt(i)));
	//				System.out.println("1");
				}
				else {
					result = result.concat(colorpalette.get(array[i]));
		//			System.out.println("2");
				}
			//	System.out.println(result);
			}
			for (int i = 0; i < guesshistory.size(); i++) {
				if (result.equals(guesshistory.get(i).getGuess())) {
					result = "";
					break;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * This is for the hard mode. It takes in a random code of colors and tests it against the history and returns true if it is a better guess
	 * than the previous guess.
	 * 
	 *@param guesshistory holds all guesses
	 * @param colorpalette list of colors
	 * @param result the passd in guess
	 * @param solution the correct answer
	 * @return result the computer's guess
	 */
	private static boolean goodGuess (ArrayList<History> guesshistory, ArrayList<String> colorpalette, String result, String solution) {
		for (History hist : guesshistory) {
	    	if (advancedChecker.checker(hist.getGuess(), result).getCorrect() != advancedChecker.checker(hist.getGuess(), solution).getCorrect() ||
	    			advancedChecker.checker(hist.getGuess(), result).getWrongPos() != advancedChecker.checker(hist.getGuess(), solution).getWrongPos()){
	    		return false;
	    	}
		}
	    return true;
	}
}
