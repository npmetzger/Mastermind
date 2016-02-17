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
import java.util.Scanner;

import javafx.scene.text.Text;

public class advancedChecker {
	
	public static ArrayList<History> guesshistory = new ArrayList<History>();
	
	/**
	 * Game mode in which the User makes the code for the Computer to guess
	 * This is for the console version of this game.
	 * It checks if it is a valid code, and calls the necessary methods for continuing the game.
	 */
	public static void MakeMode () {
		Scanner kb = new Scanner(System.in);
		String delims = "";
		String command = null;
		boolean ready = false;
		int difficulty = 0;
		
		boolean set = false;
		while (!set) {
			System.out.println("Set the computer difficulty: Easy(E), Medium(M), Hard(H):");
			command = kb.nextLine();
			if(command.length() != 1){	//not valid if not equal to required length
				System.out.println("Insert valid difficulty.");
			}
			else {
				if (command.equals("E")) {
					difficulty = 0;
					set = true;
				}
				else if (command.equals("M")) {
					difficulty = 1;
					set = true;
				}
				else if (command.equals("H")) {
					difficulty = 2;
					set = true;
				}
				else {
					System.out.println("Insert valid difficulty.");
				}
			}
		}
		
		//USER MAKES CODE >> PROMPT USER, CHECK IF VALID CODE
		while(!ready){
			System.out.println("Valid colors are: Blue(B), Green(G), Orange(O),"
					+ " Purple(P), Red(R) and Yellow(Y)");
			System.out.println("Please input " + Params.CODE_LENGTH + " colors using their symbols: ");
			
			
			command = kb.nextLine();
			if(command.length() != Params.CODE_LENGTH){	//not valid if not equal to required length
				System.out.println("Insert valid code.");
			}
			else{
				for(int i = 0; i < Params.CODE_LENGTH; i++){
					String temp = Character.toString(command.charAt(i));
					
					if(!(Params.colorpalette.contains(temp))){
						ready = false;
						System.out.println("Insert valid code.");
						break;
					}
					else{
						ready = true;
					}
					
				}
			}
			
		}
		boolean finished = false;
		boolean win = false;
		int attempt = 0;
		while (!finished && attempt < Params.MAX_ATTEMPT) {
			//COMPUTER GUESSES CODE >> DISPLAY "COMPUTER THINKING..."
			System.out.println("Computer thinking...");

			//TODO
			String guess = CompMethods.compMakeGuess(guesshistory, Params.colorpalette, command, difficulty);
			
			//DISPLAY COMPUTER GUESS
			System.out.println(guess);
			//PROMPT USER TO GIVE #BLACKPEGS, #WHITEPEGS, CORRECT GUESS
			System.out.println("How many pegs are the correct color and in the correct position? ");
			int black = kb.nextInt();
			System.out.println("How many pegs are the correct color but out of position? ");
			int white = kb.nextInt();
			
			CheckResults cheatCheck = checker(guess, command);
			CheckResults check = new CheckResults(black, Params.CODE_LENGTH - black, white);
			History hist = new History(guess, null, check);
			guesshistory.add(hist);
			if (cheatCheck.getCorrect() != black || cheatCheck.getWrongPos() != white) {
				System.out.println("You're cheating! I win!");
				finished = true;
			}
			else if (black == Params.CODE_LENGTH) {
				finished = true;
				win = true;
			}
			attempt++;
		}
		if (finished && win) {
			System.out.println("Computer found the code! You lost!");
		}
		else if (!finished) {
			System.out.println("Good code! The computer ran out of attempts! You won!");
		}
		
		
		
		
		reinitialize();
//		Main.restart();
	}
	
	
	/**
	 * Game mode in which the User guesses the code for the Computer to guess
	 * This is for the console version of this game.
	 * It checks if it is a valid code, and calls the necessary methods for continuing the game.
	 */
	public static void GuessMode () {
		
		Scanner kb = new Scanner(System.in);
		String delims = "";
		
		//CALL MAKE CODE PROGRAM FOR COMPUTER TO MAKE CODE
		String compCode = CompMethods.makeCode(Params.colorpalette);
		System.out.println(compCode);
		int attempt = 0;
		boolean ready = false;
		boolean finished = false;
		while(!finished && attempt < Params.MAX_ATTEMPT){
			//PROMPT USER FOR GUESS >> CHECK IF VALID GUESS
			ready = false;
			String guess = null;
			while(!ready){
				System.out.println("Enter your guess: ");
				guess = kb.nextLine();
				// check history
				if (guess.equals("history")) {
					if (guesshistory.isEmpty()) {
						System.out.println("No guesses yet");
					}
					for (History h: guesshistory) {
						System.out.println(h.toString());
					}
				}
				
				
				else if(guess.length() == Params.CODE_LENGTH){
					for(int i = 0; i < Params.CODE_LENGTH; i++){
						String temp = Character.toString(guess.charAt(i));

						if(!(Params.colorpalette.contains(temp))){
							ready = false;
							System.out.println("Input valid guess.");
							History hist = new History(guess, "Input valid guess.", null);
							guesshistory.add(hist);
							break;
						}
						else{
							ready = true;
							
						}
					}
				}
				else{
					System.out.println("Input valid guess.");
					History hist = new History(guess, "Input valid guess.", null);
					guesshistory.add(hist);
				}
				
			}
			
			
			
			
			//CHECK GUESS WITH CODE GENERATED BY COMPUTER
			CheckResults pegs = advancedChecker.checker(guess, compCode);
			
			
			
			//PRINT OUT BLACK/WHITE
			int black = pegs.getCorrect();
			int white = pegs.getWrongPos();
			
			String reply = new String();
			if (black + white == 0) {
				reply = "" + guess + " -> Result: No pegs";
			}
			else if (black == 0) {
				reply = "" + guess + " -> Result: " + white +" White pegs";
			}
			else if (white == 0 && black == Params.CODE_LENGTH) {
				reply = "" + guess + " -> Result: " + black +" Black pegs - You Win!";
				finished = true;
			}
			else if (white == 0) {
				reply = "" + guess + " -> Result: " + black +" Black pegs";
			}
			else {
				reply = "" + guess + " -> Result: " + black +" Black pegs and " + white + " White pegs";
			}
			System.out.println(reply);
			
			//SAVE THIS GUESS >> ARRAYLIST?
			History hist = new History(guess, reply, pegs);
			guesshistory.add(hist);
			
			// increment attempt number
			attempt++;
		}
		if (attempt >= Params.MAX_ATTEMPT && !finished) {
			System.out.println("You ran out of turns! You lost!");
		}
		reinitialize();
//		Main.restart();
	}
	
	/**
	 * Game mode in which the User guesses the code for the Computer to guess
	 * This is for the GUI version of this game.
	 * It checks if it is a valid code, and calls the necessary methods for continuing the game.
	 * 
	 * @param userguess the user's code guess
	 * @param compCode The computer solution
	 * @param attempt the number of attempts taken
	 * @param guesstext the text used for the guess to set
	 * @return String return the reply
	 * 
	 */
	public static String GuessMode (String userguess, String compCode, int attempt, Text guesstext) {
		boolean finished = false;
		String reply = "";
		System.out.println(userguess.length());
		if(userguess.length() != Params.CODE_LENGTH){
			System.out.println("Input valid guess.");
			History hist = new History(userguess, "Input valid guess.", null);
			guesshistory.add(hist);
			userguess = "";
		}
		else{
			//CHECK GUESS WITH CODE GENERATED BY COMPUTER
			CheckResults pegs = advancedChecker.checker(userguess, compCode);
			
			int black = pegs.getCorrect();
			int white = pegs.getWrongPos();
			
			
			if (black + white == 0) {
				reply = "" + userguess + " -> Result: No pegs";
			}
			else if (black == 0) {
				reply = "" + userguess + " -> Result: " + white +" White pegs";
			}
			else if (white == 0 && black == Params.CODE_LENGTH) {
				reply = "" + userguess + " -> Result: " + black +" Black pegs - You Win!";
				finished = true;
			}
			else if (white == 0) {
				reply = "" + userguess + " -> Result: " + black +" Black pegs";
			}
			else {
				reply = "" + userguess + " -> Result: " + black +" Black pegs and " + white + " White pegs";
			}
			System.out.println(reply);
			
			//SAVE THIS GUESS >> ARRAYLIST?
			History hist = new History(userguess, reply, pegs);
			guesshistory.add(hist);
			userguess = "";
			
			// increment attempt number
			attempt++;
			guesstext.setText("You have " + (Params.MAX_ATTEMPT - attempt) + " turns left.");
			
			
		}
		if (attempt >= Params.MAX_ATTEMPT && !finished) {
			System.out.println("You ran out of turns! You lost!");
			guesshistory.clear();
		}
		
		if (finished){
			guesshistory.clear();
		}
		return reply;
	}
	
	
	private static void reinitialize() {
		guesshistory.clear();
		
	}
	
	
	/**
	 * Checker method that compares the guess to the computer's code
	 * It first checks if it completely correct.
	 * Then checks if any colors are completely correct.
	 * Then checks if any colors are correct but in wrong position.
	 * Takes in both Strings and outputs a CheckResults.
	 * 
	 * @param guess user's guess
	 * @param compCode computer solution
	 * @return the results returned from checking the guess
	 */
	public static CheckResults checker (String guess, String compCode) {
		CheckResults pegs = new CheckResults();
		
		// check if it is totally correct
		if (guess.equals(compCode)) {
			pegs.setCorrect(Params.CODE_LENGTH);
			return pegs;
		}
		
		// check if any are in correct position
		boolean[] flag = new boolean[Params.CODE_LENGTH];
		int j = 0;
		for (int i = 0; i < Params.CODE_LENGTH; i++) {
			if (guess.charAt(i) != compCode.charAt(i)) {
				flag[i] = false;
			}
			else {
				flag[i] = true;
				j++;
				String result = new String();
				String coderesult = new String();
				for (int t = 0; t < Params.CODE_LENGTH; t++) {
					if (t == i) {
						result = result.concat("*");
						coderesult = coderesult.concat("_");
					}
					else {
						result = result + guess.charAt(t);
						coderesult = coderesult + compCode.charAt(t);
					}
				}
				guess = result;
				compCode = coderesult;
			}
		}
		pegs.setCorrect(j);
		
		// check if any are correct colors but in wrong position
		String check = compCode;
		int pos = 0;
		for (int i = 0; i < Params.CODE_LENGTH; i++) {
			if (!flag[i]) {
				for (int k = 0; k < Params.CODE_LENGTH; k++) {
					if (guess.charAt(i) == check.charAt(k)) {
						String result = new String();
						for (int t = 0; t < Params.CODE_LENGTH; t++) {
							if (t == k) {
								result = result.concat("-");
							}
							else {
								result = result + check.charAt(t);
							}
						}
						check = result;
						pos++;
						break;
					}
				}
			}
		}
		pegs.setWrongPos(pos);
		
		// set wrong to whatever is left
		pegs.setWrong(Params.CODE_LENGTH - (pegs.getCorrect() + pegs.getWrongPos()));
		
		
		return pegs;
	}
}
