package application;

import static org.junit.Assert.*;

import org.junit.Test;

public class MainTest {

	@Test
	public void validcolorinput() {
		Main.insertColors();
		assert(Params.colorpalette.contains("B") && Params.colorpalette.contains("G") && Params.colorpalette.contains("O") &&
				Params.colorpalette.contains("Y") && Params.colorpalette.contains("R") && Params.colorpalette.contains("P") && !Params.colorpalette.contains("Z"));
	}
	
	@Test
	public void testcorrectblackallfour() {
		String userguess = "RRRR";
		String compcode = "RRRR";
		
		CheckResults test = advancedChecker.checker(userguess, compcode);
		assert(test.getCorrect() == 4);
	}
	@Test
	public void testcorrectblackone(){
		String userguess = "BORG";
		String compcode = "RRRR";
		
		CheckResults test = advancedChecker.checker(userguess, compcode);
		assert(test.getCorrect() == 1);		
	}
	@Test
	public void testcorrectnoblack(){
		String userguess = "BBBB";
		String compcode = "RRRR";
		
		CheckResults test = advancedChecker.checker(userguess, compcode);
		assert(test.getCorrect() == 0);
	}
	
	@Test
	public void testcorrectallwhite() {
		String userguess = "BORG";
		String compcode = "GROB";
		
		CheckResults test = advancedChecker.checker(userguess, compcode);
		assert(test.getWrongPos() == 4);		
	}
	@Test
	public void testnowhite(){
		String userguess = "BBBB";
		String compcode = "RRRR";
		
		CheckResults test = advancedChecker.checker(userguess, compcode);
		assert(test.getWrongPos() == 0);		
	}
	
	@Test
	public void testmaxguesslength() {
		boolean maxworks = true;
		Main.insertColors();
		for(int i = 0; i<100; i++){
			if(CompMethods.makeCode(Params.colorpalette).length() != Params.CODE_LENGTH){
				maxworks = false;
			}
		}
		assert(maxworks);
	}
	
	
	@Test
	public void cheatingcheck() {
		String inputData = "user input data";
		System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
	}
	
	@Test
	public void addhistoryworks() {
		CheckResults check = new CheckResults();
		History hist = new History("RRRR", "This is the reply", check);
		advancedChecker.guesshistory.add(hist);
		
		assert(advancedChecker.guesshistory.contains(hist));
		
	}

}
