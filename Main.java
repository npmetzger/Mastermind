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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * This is the Main of the JavaFX Application. This creates the stage, scene, and grid for the buttons and other UI devices.
 * This calls methods and data structures from advancedChecker.java and CompMethods.java. This also uses the History and CheckResults classes.
 *
 */
public class Main extends Application{
	private String intro = "Welcome to Mastermind. Here are the rules.\n"
			+ "\n"
			+ "This is a text version of the classic board game Mastermind.\n"
			+ "The computer will tink of a secret code. The code consists of\n"
			+ "4 colored pegs.\n"
			+ "The pegs MUST be one of six colors: blue, green, orange, \n"
			+ "purple, red, or yellow. A color may appear more than once in \n"
			+ "the code. You try to guess what colored pegs are in the code \n"
			+ "and what order they are in. After you make a valid guess the \n"
			+ "result (feedback) will be displayed.\n"
			+ "\n"
			+ "The result consist of a black peg for each peg you have\n"
			+ "guessed exactly correct (color and position) in your guess.\n"
			+ "For each peg in the guess that is the correct color, but is out\n"
			+ "of position, you get a white peg. For each peg that is fully\n"
			+ "incorrect, you get no feedback.\n"
			+ "\n"
			+ "Only the first letter of the color is displayed. B for Blue, R\n"
			+ "for Red, and so forth.\n"
			+ "When entering guesses you only need to Enter the first\n"
			+ "character of each color as a capital or lowercase letter.\n"
			+ "\n"
			+ "You have 12 guesses to figure out the secret code or you lose\n"
			+ "the game.";
	private static String choice = "Would you like to make the code or guess the code? (M/G): ";
	public static GridPane primarygrid = new GridPane();
	private static int attempt = 0;
	private static String userguess = "";
	public static GridPane secondarygrid = new GridPane();
	private int numberofdots = 0;
	private List<ImageView> c = new ArrayList<ImageView>();
	private static int makedifficulty = 0;
	private static String compguess = "";
	private static boolean finished = false;

	
//	public static ArrayList<History> guesshistory = new ArrayList<History>();
	
	/**
	 * The start method creates the stage and scene for the inital instructions to show.
	 * It also contains each of the game modes to be called.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		try{			
			primaryStage.setTitle("Welcome to MasterMind");
			Scene primaryScene = new Scene(primarygrid, 500, 750);
			primaryStage.setScene(primaryScene);
			primaryStage.show();		
			

			// Add a grid pane to lay out the buttons and text fields.
			primarygrid.setAlignment(Pos.TOP_LEFT);
			primarygrid.setHgap(10);
			primarygrid.setVgap(10);
			primarygrid.setPadding(new Insets(25, 25, 25, 25));
			
			/*
			 * Introduction Print Out
			 */
			int row = 0;
			int column = 0;
			Label introlabel = new Label(intro);
			primarygrid.add(introlabel, column, row);
			row+=2;
			Label readylabel = new Label("Click ready when you are ready to begin playing. Good luck.");
			primarygrid.add(readylabel, column, row);
			row+=1;

			Button readyBtn = new Button("Ready");
			HBox hbreadyBtn = new HBox(10);
			hbreadyBtn.setAlignment(Pos.CENTER_RIGHT);
			hbreadyBtn.getChildren().add(readyBtn);
			primarygrid.add(hbreadyBtn, column, row);
			row+=2;
			
			/**
			 * The ready button, if pressed, shows the next two options of game modes: 
			 * either making the code to be guessed, or guessing the code that has been made
			 */
			readyBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Label choicelabel = new Label("Would you like to make the code (Make) or"
							+ " guess the code (Guess).");
					primarygrid.add(choicelabel, column, 5);
					insertColors();
				
					//Make Button
					Button makeBtn = new Button("Make");
					HBox hbmakeBtn = new HBox(10);
					hbmakeBtn.setAlignment(Pos.CENTER_RIGHT);
					hbmakeBtn.getChildren().add(makeBtn);
					makeBtn.setMaxSize(150, 150);
					primarygrid.add(hbmakeBtn, 0, 7);
					
					/**
					 * The make button clears the gridpane and makes the buttons for each of the colors to be made into a four
					 * color code for the computer to guess. It will not accept an invalid code. It also includes a clear button that 
					 * clears the current code to start over.
					 */
					makeBtn.setOnAction(new EventHandler<ActionEvent>(){
						@Override
						public void handle(ActionEvent event){
							//System.out.println("Make");
							primarygrid.getChildren().clear();
							primaryStage.setTitle("Make a Code!");
							int makerow = 0;
							int makecol = 0;
							
							//Add place for user to enter code
							//Colors for user to press to input code
							//Copy + paste code for colorbuttons + actions here
							//         Add text that prints out whatever user guess is so user can know what they are putting
							//         ^ Put this feature in guess as well
							
							Label usermakecode = new Label("Press the colors to make a " + Params.CODE_LENGTH + " color long code.");
							primarygrid.add(usermakecode, makecol, makerow);
							makerow++;
							
							// COLORED DOTS
							/**
							 * This set of initialization creates the colored dot graphics for each buttons to use
							 */
							Image blue = new Image(getClass().getResourceAsStream("blueDot.png"));
							Image green = new Image(getClass().getResourceAsStream("greenDot.png"));
							Image orange = new Image(getClass().getResourceAsStream("orangeDot.png"));
							Image purple = new Image(getClass().getResourceAsStream("purpleDot.png"));
							Image red = new Image(getClass().getResourceAsStream("redDot.png"));
							Image yellow = new Image(getClass().getResourceAsStream("yellowDot.png"));
							
							
							makerow+=1;
							
							// COLORED BUTTONS 
							/**
							 * This set of calls creates the buttons for the user to press. 
							 */
							Button blueBtn = new Button("Blue");
							blueBtn.setGraphic(new ImageView(blue));
							HBox hbblueBtn = new HBox(10);
							hbblueBtn.setAlignment(Pos.CENTER_LEFT);
							hbblueBtn.getChildren().add(blueBtn);
							blueBtn.setMaxSize(100, 30);
							blueBtn.setMinSize(100, 30);
							primarygrid.add(hbblueBtn, makecol, makerow);
							makerow+=1;	
							Button greenBtn = new Button("Green");
							greenBtn.setGraphic(new ImageView(green));
							HBox hbgreenBtn = new HBox(10);
							hbgreenBtn.setAlignment(Pos.CENTER_LEFT);
							hbgreenBtn.getChildren().add(greenBtn);
							greenBtn.setMaxSize(100, 30);
							greenBtn.setMinSize(100, 30);
							primarygrid.add(hbgreenBtn, makecol, makerow);
							makerow+=1;
							Button orangeBtn = new Button("Orange");
							orangeBtn.setGraphic(new ImageView(orange));
							HBox hborangeBtn = new HBox(10);
							hborangeBtn.setAlignment(Pos.CENTER_LEFT);
							hborangeBtn.getChildren().add(orangeBtn);
							orangeBtn.setMaxSize(100, 30);
							orangeBtn.setMinSize(100, 30);
							primarygrid.add(hborangeBtn, makecol, makerow);
							makerow+=1;	
							Button purpleBtn = new Button("Purple");
							purpleBtn.setGraphic(new ImageView(purple));
							HBox hbpurpleBtn = new HBox(10);
							hbpurpleBtn.setAlignment(Pos.CENTER_LEFT);
							hbpurpleBtn.getChildren().add(purpleBtn);
							purpleBtn.setMaxSize(100, 30);
							purpleBtn.setMinSize(100, 30);
							primarygrid.add(hbpurpleBtn, makecol, makerow);
							makerow+=1;
							Button redBtn = new Button("Red");
							redBtn.setGraphic(new ImageView(red));
							HBox hbredBtn = new HBox(10);
							hbredBtn.setAlignment(Pos.CENTER_LEFT);
							hbredBtn.getChildren().add(redBtn);
							redBtn.setMaxSize(100, 30);
							redBtn.setMinSize(100, 30);
							primarygrid.add(hbredBtn, makecol, makerow);
							makerow+=1;
							Button yellowBtn = new Button("Yellow");
							yellowBtn.setGraphic(new ImageView(yellow));
							HBox hbyellowBtn = new HBox(10);
							hbyellowBtn.setAlignment(Pos.CENTER_LEFT);
							hbyellowBtn.getChildren().add(yellowBtn);
							yellowBtn.setMaxSize(100, 30);
							yellowBtn.setMinSize(100, 30);
							primarygrid.add(hbyellowBtn, makecol, makerow);
							makerow+=1;
							
							//Other buttons
							Button clearBtn = new Button("Clear Code");
							HBox hbclearBtn = new HBox(10);
							hbclearBtn.setAlignment(Pos.CENTER_LEFT);
							hbclearBtn.getChildren().add(clearBtn);
							clearBtn.setMaxSize(75, 30);
							clearBtn.setMinSize(75, 30);
							primarygrid.add(hbclearBtn, makecol, makerow);
							makerow++;
							
							Text displaymakeCode = new Text();
							primarygrid.add(displaymakeCode, makecol, makerow);
							makerow++;
							Text submitcode = new Text();
							primarygrid.add(submitcode, makecol, 14);
//							System.out.println(guessrow);
							
							
							/**
							 * These are the actions that the buttons take.
							 * The colored buttons add a letter to a String to be the code and make the color appear on the screen.
							 * The clear button clears the current code.
							 */
							blueBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									final ImageView Dot = new ImageView(blue);
									if (numberofdots == 0) {
										primarygrid.add(Dot, 4, 3);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("B");
										System.out.println("B");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
									else if (numberofdots == 1) {
										primarygrid.add(Dot, 4, 4);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("B");
										System.out.println("B");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
									else if (numberofdots == 2) {
										primarygrid.add(Dot, 4, 5);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("B");
										System.out.println("B");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
									else if (numberofdots == 3) {
										primarygrid.add(Dot, 4, 6);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("B");
										System.out.println("B");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
								}
							});	
							
							
							
							greenBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									final ImageView Dot = new ImageView(green);
									if (numberofdots == 0) {
										primarygrid.add(Dot, 4, 3);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("G");
										System.out.println("G");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
									else if (numberofdots == 1) {
										primarygrid.add(Dot, 4, 4);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("G");
										System.out.println("G");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
									else if (numberofdots == 2) {
										primarygrid.add(Dot, 4, 5);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("G");
										System.out.println("G");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
									else if (numberofdots == 3) {
										primarygrid.add(Dot, 4, 6);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("G");
										System.out.println("G");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
								}
							});							
							orangeBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									final ImageView Dot = new ImageView(orange);
									if (numberofdots == 0) {
										primarygrid.add(Dot, 4, 3);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("O");
										System.out.println("O");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
									else if (numberofdots == 1) {
										primarygrid.add(Dot, 4, 4);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("O");
										System.out.println("O");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
									else if (numberofdots == 2) {
										primarygrid.add(Dot, 4, 5);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("O");
										System.out.println("O");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
									else if (numberofdots == 3) {
										primarygrid.add(Dot, 4, 6);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("O");
										System.out.println("O");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
								}
							});							
							purpleBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									final ImageView Dot = new ImageView(purple);
									if (numberofdots == 0) {
										primarygrid.add(Dot, 4, 3);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("P");
										System.out.println("P");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
									else if (numberofdots == 1) {
										primarygrid.add(Dot, 4, 4);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("P");
										System.out.println("P");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
									else if (numberofdots == 2) {
										primarygrid.add(Dot, 4, 5);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("P");
										System.out.println("P");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
									else if (numberofdots == 3) {
										primarygrid.add(Dot, 4, 6);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("P");
										System.out.println("P");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
								}
							});							
							redBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									final ImageView Dot = new ImageView(red);
									if (numberofdots == 0) {
										primarygrid.add(Dot, 4, 3);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("R");
										System.out.println("R");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
									else if (numberofdots == 1) {
										primarygrid.add(Dot, 4, 4);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("R");
										System.out.println("R");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
									else if (numberofdots == 2) {
										primarygrid.add(Dot, 4, 5);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("R");
										System.out.println("R");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
									else if (numberofdots == 3) {
										primarygrid.add(Dot, 4, 6);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("R");
										System.out.println("R");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
								}
							});							
							yellowBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									final ImageView Dot = new ImageView(yellow);
									if (numberofdots == 0) {
										primarygrid.add(Dot, 4, 3);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("Y");
										System.out.println("Y");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
									else if (numberofdots == 1) {
										primarygrid.add(Dot, 4, 4);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("Y");
										System.out.println("Y");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
									else if (numberofdots == 2) {
										primarygrid.add(Dot, 4, 5);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("Y");
										System.out.println("Y");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
									else if (numberofdots == 3) {
										primarygrid.add(Dot, 4, 6);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("Y");
										System.out.println("Y");
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText(userguess);
									}
								}
							});	
							
							//Option to delete a previous entry? (Add a button to clear, too hard to do just previous)
							clearBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									userguess = "";	
									System.out.println("Cleared");
									primarygrid.getChildren().removeAll(c);
									c.clear();
									numberofdots = 0;
								}
							});
							
							//Add AI diffiulty level >> Select AI difficulty level (Baby, Teenager, Genius)
							/**
							 * This next set of buttons sets the AI difficulty level. Baby is easy, Teenager is medium, Genius is insanely hard.
							 * each of the buttons causes the screen to clear and to be reset for the game mode.
							 */
							Label difficulty = new Label("Select the AI's intelligence.");
							primarygrid.add(difficulty, makecol, makerow);
							makerow++;
							
							Button babyBtn = new Button("Baby");
							HBox hbbabyBtn = new HBox(10);
							hbbabyBtn.setAlignment(Pos.CENTER_LEFT);
							hbbabyBtn.getChildren().add(babyBtn);
							babyBtn.setMaxSize(75, 30);
							babyBtn.setMinSize(75, 30);
							primarygrid.add(hbbabyBtn, makecol, makerow);
							makerow++;		
							Button teenageBtn = new Button("Teenage");
							HBox hbteenageBtn = new HBox(10);
							hbteenageBtn.setAlignment(Pos.CENTER_LEFT);
							hbteenageBtn.getChildren().add(teenageBtn);
							teenageBtn.setMaxSize(75, 30);
							teenageBtn.setMinSize(75, 30);
							primarygrid.add(hbteenageBtn, makecol, makerow);
							makerow++;			
							Button geniusBtn = new Button("Genius");
							HBox hbgeniusBtn = new HBox(10);
							hbgeniusBtn.setAlignment(Pos.CENTER_LEFT);
							hbgeniusBtn.getChildren().add(geniusBtn);
							geniusBtn.setMaxSize(75, 30);
							geniusBtn.setMinSize(75, 30);
							primarygrid.add(hbgeniusBtn, makecol, makerow);
							makerow++;
							
							/**
							 * This is the Baby (easy) mode. The GUI asks for the number of correct and correct color but wrong placement.
							 * The submit button pushes those answers to the computer to make another guess. It only allows for valid number inputs.
							 * If the user cheats by putting in the wrong number for either choice, the computer automatically wins.
							 * In any case of the game ending, the computer asks if the user wants to play another game.
							 */
							babyBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {								
									if(makecodevalid()){
										makedifficulty = 0;	
										//makecontinuation(primaryStage);
										primarygrid.getChildren().clear();
										//primaryStage.setTitle("MasterMind Make Mode");
										int makerow = 0;
										int makecol = 0;
										
										Text comptext = new Text();
										primarygrid.add(comptext, makecol, makerow);
										makerow+=2;
									
										// User input how many correct position (black), correct color (white)
										Label blackpeg = new Label("# of correct color + position (black pegs):");
										primarygrid.add(blackpeg, makecol, makerow);
										makerow+=2;
										TextField blackpegField = new TextField();
										primarygrid.add(blackpegField, makecol, makerow);
										makerow+=3;
										
										Label whitepeg = new Label("# of only correct color (white pegs):");
										primarygrid.add(whitepeg, makecol, makerow);
										makerow+=2;
										TextField whitepegField = new TextField();
										primarygrid.add(whitepegField, makecol, makerow);
										makerow+=3;
										
										// Submit button
										Button submitBtn = new Button("Submit!");
										HBox hbsubmitBtn = new HBox(10);
										hbsubmitBtn.setAlignment(Pos.CENTER_LEFT);
										hbsubmitBtn.getChildren().add(submitBtn);
										submitBtn.setMaxSize(75, 30);
										submitBtn.setMinSize(75, 30);
										primarygrid.add(hbsubmitBtn, 3, makerow);
										makerow++;
										
										Text submitTarget = new Text();
										primarygrid.add(submitTarget, 3, makerow);
										
										
										// Have place for computer to display his guess (use AI difficulty level to make guess)
										//For right now, could just have it print out something >> later make spheres in another display	
										compguess = CompMethods.compMakeGuess(advancedChecker.guesshistory, Params.colorpalette, userguess, makedifficulty);
										comptext.setFill(Color.FIREBRICK);
										comptext.setText("The computer's guess is: " + compguess + ". Attempt #" + attempt + " out of " + Params.MAX_ATTEMPT);
										//checks stuff in fields, see if valid inputs
											// Computer checks for lying
											// Computer checks to see if they win (user put 4 black AND code actually matches), or if have more guesses
										submitBtn.setOnAction(new EventHandler<ActionEvent>() {
											@Override
											public void handle(ActionEvent event) {
												String blackString = blackpegField.getText();
												String whiteString = whitepegField.getText();
												
												try{
													int blacknum = Integer.parseInt(blackString);
													int whitenum = Integer.parseInt(whiteString);
													
													if(blacknum < 0 || blacknum > Params.CODE_LENGTH || whitenum < 0 || whitenum > Params.CODE_LENGTH){
														submitTarget.setFill(Color.FIREBRICK);
														submitTarget.setText("Input valid numbers.");
													}
													else{
														System.out.println(compguess + userguess);
														//call method that takes in #white pegs and black pegs and checks to see if lying
														CheckResults cheatCheck = advancedChecker.checker(compguess, userguess);
														CheckResults check = new CheckResults(blacknum, Params.CODE_LENGTH - blacknum, whitenum);
														History hist = new History(compguess,null,check);
														advancedChecker.guesshistory.add(hist);
														
														if(attempt == Params.MAX_ATTEMPT){
															primarygrid.getChildren().clear();
															Text cheatTarget = new Text();
															primarygrid.add(cheatTarget, 0,0);
															cheatTarget.setFill(Color.FIREBRICK);
															cheatTarget.setText("Aw shucks. I lost. I would like to try again if you dare! \nPlay Again?");
															
															Button yesBtn = new Button("Yes");
															HBox hbyesBtn = new HBox(10);
															hbyesBtn.setAlignment(Pos.CENTER_LEFT);
															hbyesBtn.getChildren().add(yesBtn);
															yesBtn.setMaxSize(75, 30);
															yesBtn.setMinSize(75, 30);
															primarygrid.add(hbyesBtn, 0, 4); 
															Button noBtn = new Button("No");
															HBox hbnoBtn = new HBox(10);
															hbnoBtn.setAlignment(Pos.CENTER_LEFT);
															hbnoBtn.getChildren().add(noBtn);
															noBtn.setMaxSize(75, 30);
															noBtn.setMinSize(75, 30);
															primarygrid.add(hbnoBtn, 0, 5);
															
															yesBtn.setOnAction(new EventHandler<ActionEvent>() {
																@Override
																public void handle(ActionEvent event) {
																	try{
																		userguess = "";
																		numberofdots = 0;
																		attempt = 0;
																		primaryStage.close();
																		primarygrid = new GridPane();
																		start(new Stage());
																		finished = true;
																	}catch(Exception e){
																		e.printStackTrace();
																	}
																}
															});	
															noBtn.setOnAction(new EventHandler<ActionEvent>() {
																@Override
																public void handle(ActionEvent event) {
																	Platform.exit();
																}
															});
															//askrestart(primaryStage);
															
														}
														else if (cheatCheck.getCorrect() != blacknum || cheatCheck.getWrongPos() != whitenum) {
															primarygrid.getChildren().clear();
															Text cheatTarget = new Text();
															primarygrid.add(cheatTarget, 0,0);
															cheatTarget.setFill(Color.FIREBRICK);
															cheatTarget.setText("YOU GOT CAUGHT CHEATING! THEREFORE I WIN BY DEFAULT. \nCheater Cheater Pumpkin Eater. \nPlay Again?");
															
															Button yesBtn = new Button("Yes");
															HBox hbyesBtn = new HBox(10);
															hbyesBtn.setAlignment(Pos.CENTER_LEFT);
															hbyesBtn.getChildren().add(yesBtn);
															yesBtn.setMaxSize(75, 30);
															yesBtn.setMinSize(75, 30);
															primarygrid.add(hbyesBtn, 0, 4); 
															Button noBtn = new Button("No");
															HBox hbnoBtn = new HBox(10);
															hbnoBtn.setAlignment(Pos.CENTER_LEFT);
															hbnoBtn.getChildren().add(noBtn);
															noBtn.setMaxSize(75, 30);
															noBtn.setMinSize(75, 30);
															primarygrid.add(hbnoBtn, 0, 5);
															
															yesBtn.setOnAction(new EventHandler<ActionEvent>() {
																@Override
																public void handle(ActionEvent event) {
																	try{
																		userguess = "";
																		numberofdots = 0;
																		attempt = 0;
																		primaryStage.close();
																		primarygrid = new GridPane();
																		start(new Stage());
																		finished = true;
																	}catch(Exception e){
																		e.printStackTrace();
																	}
																}
															});	
															noBtn.setOnAction(new EventHandler<ActionEvent>() {
																@Override
																public void handle(ActionEvent event) {
																	Platform.exit();
																}
															});
															//askrestart(primaryStage);
															
														}
														else if (blacknum == Params.CODE_LENGTH) {
															primarygrid.getChildren().clear();		
															Text cheatTarget = new Text();
															primarygrid.add(cheatTarget, 0,0);
															cheatTarget.setFill(Color.FIREBRICK);
															cheatTarget.setText("I WIN! HAH-HAH. You suck, you can't outsmart me! \nPlay Again?");
															
															Button yesBtn = new Button("Yes");
															HBox hbyesBtn = new HBox(10);
															hbyesBtn.setAlignment(Pos.CENTER_LEFT);
															hbyesBtn.getChildren().add(yesBtn);
															yesBtn.setMaxSize(75, 30);
															yesBtn.setMinSize(75, 30);
															primarygrid.add(hbyesBtn, 0, 4); 
															Button noBtn = new Button("No");
															HBox hbnoBtn = new HBox(10);
															hbnoBtn.setAlignment(Pos.CENTER_LEFT);
															hbnoBtn.getChildren().add(noBtn);
															noBtn.setMaxSize(75, 30);
															noBtn.setMinSize(75, 30);
															primarygrid.add(hbnoBtn, 0, 5);
															
															yesBtn.setOnAction(new EventHandler<ActionEvent>() {
																@Override
																public void handle(ActionEvent event) {
																	try{
																		userguess = "";
																		numberofdots = 0;
																		attempt = 0;
																		primaryStage.close();
																		primarygrid = new GridPane();
																		start(new Stage());
																		finished = true;
																	}catch(Exception e){
																		e.printStackTrace();
																	}
																}
															});	
															noBtn.setOnAction(new EventHandler<ActionEvent>() {
																@Override
																public void handle(ActionEvent event) {
																	Platform.exit();
																}
															});
															//askrestart(primaryStage);
															
														}
														else{
															attempt++;
															compguess = CompMethods.compMakeGuess(advancedChecker.guesshistory, Params.colorpalette, userguess, makedifficulty);
															comptext.setFill(Color.FIREBRICK);
															comptext.setText("The computer's guess is: " + compguess + ". Attempt #" + attempt + " out of " + Params.MAX_ATTEMPT);
														}
														//check to see if win, if not
															//print out you win
															//check to see if have more guesses
																//have more guesses >> computer immediately makes another guess
																//don't have any more guesses, print out computer loses
													}
												}catch (NumberFormatException e){
													submitTarget.setFill(Color.FIREBRICK);
													submitTarget.setText("Input valid numbers.");
												}
												

																		
											}
												
										});
										
									}
									else{
										submitcode.setFill(Color.FIREBRICK);
										submitcode.setText("Input valid code!");
										userguess = "";
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText("");
									}
								}
							});	
							
							/**
							 * This is the Teenage (medium) mode. The GUI asks for the number of correct and correct color but wrong placement.
							 * The submit button pushes those answers to the computer to make another guess. It only allows for valid number inputs.
							 * If the user cheats by putting in the wrong number for either choice, the computer automatically wins.
							 * In any case of the game ending, the computer asks if the user wants to play another game.
							 */
							teenageBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									if(makecodevalid()){
										makedifficulty = 1;	
										//makecontinuation(primaryStage);
										primarygrid.getChildren().clear();
										//primaryStage.setTitle("MasterMind Make Mode");
										int makerow = 0;
										int makecol = 0;
										
										Text comptext = new Text();
										primarygrid.add(comptext, makecol, makerow);
										makerow+=2;
									
										// User input how many correct position (black), correct color (white)
										Label blackpeg = new Label("# of correct color + position (black pegs):");
										primarygrid.add(blackpeg, makecol, makerow);
										makerow+=2;
										TextField blackpegField = new TextField();
										primarygrid.add(blackpegField, makecol, makerow);
										makerow+=3;
										
										Label whitepeg = new Label("# of only correct color (white pegs):");
										primarygrid.add(whitepeg, makecol, makerow);
										makerow+=2;
										TextField whitepegField = new TextField();
										primarygrid.add(whitepegField, makecol, makerow);
										makerow+=3;
										
										// Submit button
										Button submitBtn = new Button("Submit!");
										HBox hbsubmitBtn = new HBox(10);
										hbsubmitBtn.setAlignment(Pos.CENTER_LEFT);
										hbsubmitBtn.getChildren().add(submitBtn);
										submitBtn.setMaxSize(75, 30);
										submitBtn.setMinSize(75, 30);
										primarygrid.add(hbsubmitBtn, 3, makerow);
										makerow++;
										
										Text submitTarget = new Text();
										primarygrid.add(submitTarget, 3, makerow);
										
										
										// Have place for computer to display his guess (use AI difficulty level to make guess)
										//For right now, could just have it print out something >> later make spheres in another display	
										compguess = CompMethods.compMakeGuess(advancedChecker.guesshistory, Params.colorpalette, userguess, makedifficulty);
										comptext.setFill(Color.FIREBRICK);
										comptext.setText("The computer's guess is: " + compguess + ". Attempt #" + attempt + " out of " + Params.MAX_ATTEMPT);
										//checks stuff in fields, see if valid inputs
											// Computer checks for lying
											// Computer checks to see if they win (user put 4 black AND code actually matches), or if have more guesses
										submitBtn.setOnAction(new EventHandler<ActionEvent>() {
											@Override
											public void handle(ActionEvent event) {
												String blackString = blackpegField.getText();
												String whiteString = whitepegField.getText();
												
												try{
													int blacknum = Integer.parseInt(blackString);
													int whitenum = Integer.parseInt(whiteString);
													
													if(blacknum < 0 || blacknum > Params.CODE_LENGTH || whitenum < 0 || whitenum > Params.CODE_LENGTH){
														submitTarget.setFill(Color.FIREBRICK);
														submitTarget.setText("Input valid numbers.");
													}
													else{
														System.out.println(compguess + userguess);
														//call method that takes in #white pegs and black pegs and checks to see if lying
														CheckResults cheatCheck = advancedChecker.checker(compguess, userguess);
														CheckResults check = new CheckResults(blacknum, Params.CODE_LENGTH - blacknum, whitenum);
														History hist = new History(compguess,null,check);
														advancedChecker.guesshistory.add(hist);
														
														if(attempt == Params.MAX_ATTEMPT){
															primarygrid.getChildren().clear();
															Text cheatTarget = new Text();
															primarygrid.add(cheatTarget, 0,0);
															cheatTarget.setFill(Color.FIREBRICK);
															cheatTarget.setText("Aw shucks. I lost. I would like to try again if you dare! \nPlay Again?");
															
															Button yesBtn = new Button("Yes");
															HBox hbyesBtn = new HBox(10);
															hbyesBtn.setAlignment(Pos.CENTER_LEFT);
															hbyesBtn.getChildren().add(yesBtn);
															yesBtn.setMaxSize(75, 30);
															yesBtn.setMinSize(75, 30);
															primarygrid.add(hbyesBtn, 0, 4); 
															Button noBtn = new Button("No");
															HBox hbnoBtn = new HBox(10);
															hbnoBtn.setAlignment(Pos.CENTER_LEFT);
															hbnoBtn.getChildren().add(noBtn);
															noBtn.setMaxSize(75, 30);
															noBtn.setMinSize(75, 30);
															primarygrid.add(hbnoBtn, 0, 5);
															
															yesBtn.setOnAction(new EventHandler<ActionEvent>() {
																@Override
																public void handle(ActionEvent event) {
																	try{
																		userguess = "";
																		numberofdots = 0;
																		attempt = 0;
																		primaryStage.close();
																		primarygrid = new GridPane();
																		start(new Stage());
																		finished = true;
																	}catch(Exception e){
																		e.printStackTrace();
																	}
																}
															});	
															noBtn.setOnAction(new EventHandler<ActionEvent>() {
																@Override
																public void handle(ActionEvent event) {
																	Platform.exit();
																}
															});
															//askrestart(primaryStage);
															
														}
														else if (cheatCheck.getCorrect() != blacknum || cheatCheck.getWrongPos() != whitenum) {
															primarygrid.getChildren().clear();
															Text cheatTarget = new Text();
															primarygrid.add(cheatTarget, 0,0);
															cheatTarget.setFill(Color.FIREBRICK);
															cheatTarget.setText("YOU GOT CAUGHT CHEATING! THEREFORE I WIN BY DEFAULT. \nCheater Cheater Pumpkin Eater. \nPlay Again?");
															
															Button yesBtn = new Button("Yes");
															HBox hbyesBtn = new HBox(10);
															hbyesBtn.setAlignment(Pos.CENTER_LEFT);
															hbyesBtn.getChildren().add(yesBtn);
															yesBtn.setMaxSize(75, 30);
															yesBtn.setMinSize(75, 30);
															primarygrid.add(hbyesBtn, 0, 4); 
															Button noBtn = new Button("No");
															HBox hbnoBtn = new HBox(10);
															hbnoBtn.setAlignment(Pos.CENTER_LEFT);
															hbnoBtn.getChildren().add(noBtn);
															noBtn.setMaxSize(75, 30);
															noBtn.setMinSize(75, 30);
															primarygrid.add(hbnoBtn, 0, 5);
															
															yesBtn.setOnAction(new EventHandler<ActionEvent>() {
																@Override
																public void handle(ActionEvent event) {
																	try{
																		userguess = "";
																		numberofdots = 0;
																		attempt = 0;
																		primaryStage.close();
																		primarygrid = new GridPane();
																		start(new Stage());
																		finished = true;
																	}catch(Exception e){
																		e.printStackTrace();
																	}
																}
															});	
															noBtn.setOnAction(new EventHandler<ActionEvent>() {
																@Override
																public void handle(ActionEvent event) {
																	Platform.exit();
																}
															});
															//askrestart(primaryStage);
														}
														else if (blacknum == Params.CODE_LENGTH) {
															primarygrid.getChildren().clear();		
															Text cheatTarget = new Text();
															primarygrid.add(cheatTarget, 0,0);
															cheatTarget.setFill(Color.FIREBRICK);
															cheatTarget.setText("I WIN! HAH-HAH. You suck, you can't outsmart me! \nPlay Again?");
															
															Button yesBtn = new Button("Yes");
															HBox hbyesBtn = new HBox(10);
															hbyesBtn.setAlignment(Pos.CENTER_LEFT);
															hbyesBtn.getChildren().add(yesBtn);
															yesBtn.setMaxSize(75, 30);
															yesBtn.setMinSize(75, 30);
															primarygrid.add(hbyesBtn, 0, 4); 
															Button noBtn = new Button("No");
															HBox hbnoBtn = new HBox(10);
															hbnoBtn.setAlignment(Pos.CENTER_LEFT);
															hbnoBtn.getChildren().add(noBtn);
															noBtn.setMaxSize(75, 30);
															noBtn.setMinSize(75, 30);
															primarygrid.add(hbnoBtn, 0, 5);
															
															yesBtn.setOnAction(new EventHandler<ActionEvent>() {
																@Override
																public void handle(ActionEvent event) {
																	try{
																		userguess = "";
																		numberofdots = 0;
																		attempt = 0;
																		primaryStage.close();
																		primarygrid = new GridPane();
																		start(new Stage());
																		finished = true;
																	}catch(Exception e){
																		e.printStackTrace();
																	}
																}
															});	
															noBtn.setOnAction(new EventHandler<ActionEvent>() {
																@Override
																public void handle(ActionEvent event) {
																	Platform.exit();
																}
															});
															//askrestart(primaryStage);
															
														}
														else{
															attempt++;
															compguess = CompMethods.compMakeGuess(advancedChecker.guesshistory, Params.colorpalette, userguess, makedifficulty);
															comptext.setFill(Color.FIREBRICK);
															comptext.setText("The computer's guess is: " + compguess + ". Attempt #" + attempt + " out of " + Params.MAX_ATTEMPT);
														}
														//check to see if win, if not
															//print out you win
															//check to see if have more guesses
																//have more guesses >> computer immediately makes another guess
																//don't have any more guesses, print out computer loses
													}
												}catch (NumberFormatException e){
													submitTarget.setFill(Color.FIREBRICK);
													submitTarget.setText("Input valid numbers.");
												}
												

																		
											}
												
										});
									}
									else{
										submitcode.setFill(Color.FIREBRICK);
										submitcode.setText("Input valid code!");
										userguess = "";
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText("");
									}
								}
							});	
							
							/**
							 * This is the Genius (hard) mode. The GUI asks for the number of correct and correct color but wrong placement.
							 * The submit button pushes those answers to the computer to make another guess. It only allows for valid number inputs.
							 * If the user cheats by putting in the wrong number for either choice, the computer automatically wins.
							 * In any case of the game ending, the computer asks if the user wants to play another game.
							 */
							geniusBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									if(makecodevalid()){
										makedifficulty = 2;	
										//makecontinuation(primaryStage);
										primarygrid.getChildren().clear();
										//primaryStage.setTitle("MasterMind Make Mode");
										int makerow = 0;
										int makecol = 0;
										
										Text comptext = new Text();
										primarygrid.add(comptext, makecol, makerow);
										makerow+=2;
									
										// User input how many correct position (black), correct color (white)
										Label blackpeg = new Label("# of correct color + position (black pegs):");
										primarygrid.add(blackpeg, makecol, makerow);
										makerow+=2;
										TextField blackpegField = new TextField();
										primarygrid.add(blackpegField, makecol, makerow);
										makerow+=3;
										
										Label whitepeg = new Label("# of only correct color (white pegs):");
										primarygrid.add(whitepeg, makecol, makerow);
										makerow+=2;
										TextField whitepegField = new TextField();
										primarygrid.add(whitepegField, makecol, makerow);
										makerow+=3;
										
										// Submit button
										Button submitBtn = new Button("Submit!");
										HBox hbsubmitBtn = new HBox(10);
										hbsubmitBtn.setAlignment(Pos.CENTER_LEFT);
										hbsubmitBtn.getChildren().add(submitBtn);
										submitBtn.setMaxSize(75, 30);
										submitBtn.setMinSize(75, 30);
										primarygrid.add(hbsubmitBtn, 3, makerow);
										makerow++;
										
										Text submitTarget = new Text();
										primarygrid.add(submitTarget, 3, makerow);
										
										
										// Have place for computer to display his guess (use AI difficulty level to make guess)
										//For right now, could just have it print out something >> later make spheres in another display	
										compguess = CompMethods.compMakeGuess(advancedChecker.guesshistory, Params.colorpalette, userguess, makedifficulty);
										comptext.setFill(Color.FIREBRICK);
										comptext.setText("The computer's guess is: " + compguess + ". Attempt #" + attempt + " out of " + Params.MAX_ATTEMPT);
										//checks stuff in fields, see if valid inputs
											// Computer checks for lying
											// Computer checks to see if they win (user put 4 black AND code actually matches), or if have more guesses
										submitBtn.setOnAction(new EventHandler<ActionEvent>() {
											@Override
											public void handle(ActionEvent event) {
												String blackString = blackpegField.getText();
												String whiteString = whitepegField.getText();
												
												try{
													int blacknum = Integer.parseInt(blackString);
													int whitenum = Integer.parseInt(whiteString);
													
													if(blacknum < 0 || blacknum > Params.CODE_LENGTH || whitenum < 0 || whitenum > Params.CODE_LENGTH){
														submitTarget.setFill(Color.FIREBRICK);
														submitTarget.setText("Input valid numbers.");
													}
													else{
	//													System.out.println(compguess + userguess);
														//call method that takes in #white pegs and black pegs and checks to see if lying
														CheckResults cheatCheck = advancedChecker.checker(compguess, userguess);
														CheckResults check = new CheckResults(blacknum, Params.CODE_LENGTH - blacknum, whitenum);
														History hist = new History(compguess,null,check);
														advancedChecker.guesshistory.add(hist);
														
														if(attempt == Params.MAX_ATTEMPT){
															primarygrid.getChildren().clear();
															Text cheatTarget = new Text();
															primarygrid.add(cheatTarget, 0,0);
															cheatTarget.setFill(Color.FIREBRICK);
															cheatTarget.setText("Aw shucks. I lost. I would like to try again if you dare! \nPlay Again?");
															
															Button yesBtn = new Button("Yes");
															HBox hbyesBtn = new HBox(10);
															hbyesBtn.setAlignment(Pos.CENTER_LEFT);
															hbyesBtn.getChildren().add(yesBtn);
															yesBtn.setMaxSize(75, 30);
															yesBtn.setMinSize(75, 30);
															primarygrid.add(hbyesBtn, 0, 4); 
															Button noBtn = new Button("No");
															HBox hbnoBtn = new HBox(10);
															hbnoBtn.setAlignment(Pos.CENTER_LEFT);
															hbnoBtn.getChildren().add(noBtn);
															noBtn.setMaxSize(75, 30);
															noBtn.setMinSize(75, 30);
															primarygrid.add(hbnoBtn, 0, 5);
															
															yesBtn.setOnAction(new EventHandler<ActionEvent>() {
																@Override
																public void handle(ActionEvent event) {
																	try{
																		userguess = "";
																		numberofdots = 0;
																		attempt = 0;
																		primaryStage.close();
																		primarygrid = new GridPane();
																		start(new Stage());
																		finished = true;
																	}catch(Exception e){
																		e.printStackTrace();
																	}
																}
															});	
															noBtn.setOnAction(new EventHandler<ActionEvent>() {
																@Override
																public void handle(ActionEvent event) {
																	Platform.exit();
																}
															});
															//askrestart(primaryStage);
															
														}
														else if (cheatCheck.getCorrect() != blacknum || cheatCheck.getWrongPos() != whitenum) {
															primarygrid.getChildren().clear();
															Text cheatTarget = new Text();
															primarygrid.add(cheatTarget, 0,0);
															cheatTarget.setFill(Color.FIREBRICK);
															cheatTarget.setText("YOU GOT CAUGHT CHEATING! THEREFORE I WIN BY DEFAULT. \nCheater Cheater Pumpkin Eater. \nPlay Again?");
															
															Button yesBtn = new Button("Yes");
															HBox hbyesBtn = new HBox(10);
															hbyesBtn.setAlignment(Pos.CENTER_LEFT);
															hbyesBtn.getChildren().add(yesBtn);
															yesBtn.setMaxSize(75, 30);
															yesBtn.setMinSize(75, 30);
															primarygrid.add(hbyesBtn, 0, 4); 
															Button noBtn = new Button("No");
															HBox hbnoBtn = new HBox(10);
															hbnoBtn.setAlignment(Pos.CENTER_LEFT);
															hbnoBtn.getChildren().add(noBtn);
															noBtn.setMaxSize(75, 30);
															noBtn.setMinSize(75, 30);
															primarygrid.add(hbnoBtn, 0, 5);
															
															yesBtn.setOnAction(new EventHandler<ActionEvent>() {
																@Override
																public void handle(ActionEvent event) {
																	try{
																		userguess = "";
																		numberofdots = 0;
																		attempt = 0;
																		primaryStage.close();
																		primarygrid = new GridPane();
																		start(new Stage());
																		finished = true;
																	}catch(Exception e){
																		e.printStackTrace();
																	}
																}
															});	
															noBtn.setOnAction(new EventHandler<ActionEvent>() {
																@Override
																public void handle(ActionEvent event) {
																	Platform.exit();
																}
															});
															//askrestart(primaryStage);
															
														}
														else if (blacknum == Params.CODE_LENGTH) {
															primarygrid.getChildren().clear();		
															Text cheatTarget = new Text();
															primarygrid.add(cheatTarget, 0,0);
															cheatTarget.setFill(Color.FIREBRICK);
															cheatTarget.setText("I WIN! HAH-HAH. You suck, you can't outsmart me! \nPlay Again?");
															
															Button yesBtn = new Button("Yes");
															HBox hbyesBtn = new HBox(10);
															hbyesBtn.setAlignment(Pos.CENTER_LEFT);
															hbyesBtn.getChildren().add(yesBtn);
															yesBtn.setMaxSize(75, 30);
															yesBtn.setMinSize(75, 30);
															primarygrid.add(hbyesBtn, 0, 4); 
															Button noBtn = new Button("No");
															HBox hbnoBtn = new HBox(10);
															hbnoBtn.setAlignment(Pos.CENTER_LEFT);
															hbnoBtn.getChildren().add(noBtn);
															noBtn.setMaxSize(75, 30);
															noBtn.setMinSize(75, 30);
															primarygrid.add(hbnoBtn, 0, 5);
															
															yesBtn.setOnAction(new EventHandler<ActionEvent>() {
																@Override
																public void handle(ActionEvent event) {
																	try{
																		userguess = "";
																		numberofdots = 0;
																		attempt = 0;
																		primaryStage.close();
																		primarygrid = new GridPane();
																		start(new Stage());
																		finished = true;
																	}catch(Exception e){
																		e.printStackTrace();
																	}
																}
															});	
															noBtn.setOnAction(new EventHandler<ActionEvent>() {
																@Override
																public void handle(ActionEvent event) {
																	Platform.exit();
																}
															});
															//askrestart(primaryStage);
															
														}
														else{
															attempt++;
															compguess = CompMethods.compMakeGuess(advancedChecker.guesshistory, Params.colorpalette, userguess, makedifficulty);
															comptext.setFill(Color.FIREBRICK);
															comptext.setText("The computer's guess is: " + compguess + ". Attempt #" + attempt + " out of " + Params.MAX_ATTEMPT);
														}
														//check to see if win, if not
															//print out you win
															//check to see if have more guesses
																//have more guesses >> computer immediately makes another guess
																//don't have any more guesses, print out computer loses
													}
												}catch (NumberFormatException e){
													submitTarget.setFill(Color.FIREBRICK);
													submitTarget.setText("Input valid numbers.");
												}
												

																		
											}
												
										});
									}
									else{
										submitcode.setFill(Color.FIREBRICK);
										submitcode.setText("Input valid code!");
										userguess = "";
										displaymakeCode.setFill(Color.FIREBRICK);
										displaymakeCode.setText("");
									}
								}
							});	

								// Offer chance to play again									
							}
					
					});		
					
					//Guess Button
					Button guessBtn = new Button("Guess");
					HBox hbguessBtn = new HBox(10);
					hbguessBtn.setAlignment(Pos.CENTER_RIGHT);
					hbguessBtn.getChildren().add(guessBtn);
					guessBtn.setMaxSize(150, 150);
					primarygrid.add(hbguessBtn, 0, 8);
				
					/**
					 * The guess button clears the gridpane and makes the buttons for each of the colors to be made into a four
					 * color code for the computer to check against its own code to give back a response for how correct it is. 
					 * It will not accept an invalid code. It also includes a clear button that clears the current code to start over.
					 */
					guessBtn.setOnAction(new EventHandler<ActionEvent>(){
						@Override
						public void handle(ActionEvent event){
							//System.out.println("Guess");
							primarygrid.getChildren().clear();
							
							primaryStage.setTitle("Guess the Computer's Code!");
							int guessrow = 0;
							int guesscol = 0;
							
							//Add text introduction, saying computer is thinking of a code...
							String compCode = CompMethods.makeCode(Params.colorpalette);
							System.out.println(compCode);
							
							Text guesstext = new Text("Computer has the code. Can you guess it in " + Params.MAX_ATTEMPT + " turns?");
							primarygrid.add(guesstext, guesscol, guessrow);
							guessrow+=2;		
								
							//Add color palette to choose your guesses from
							Label colorpalette = new Label("Color Palette");
							primarygrid.add(colorpalette, guesscol, guessrow);
							
							final Text actionTarget = new Text();
							primarygrid.add(actionTarget, guesscol, 11);
							
							final Text lossTarget = new Text();
							primarygrid.add(lossTarget, 4, guessrow + 1);
							
							final Text histTarget = new Text();
							histTarget.setWrappingWidth(300.0);
							primarygrid.add(histTarget, guesscol, 10);
							
							
							// COLORED DOTS
							/**
							 * This set of initialization creates the colored dot graphics for each buttons to use
							 */
							Image blue = new Image(getClass().getResourceAsStream("blueDot.png"));
							Image green = new Image(getClass().getResourceAsStream("greenDot.png"));
							Image orange = new Image(getClass().getResourceAsStream("orangeDot.png"));
							Image purple = new Image(getClass().getResourceAsStream("purpleDot.png"));
							Image red = new Image(getClass().getResourceAsStream("redDot.png"));
							Image yellow = new Image(getClass().getResourceAsStream("yellowDot.png"));
							
							
							guessrow+=1;
							
							// COLORED BUTTONS 
							/**
							 * This set of calls creates the buttons for the user to press. 
							 */
							Button blueBtn = new Button("Blue");
							blueBtn.setGraphic(new ImageView(blue));
							HBox hbblueBtn = new HBox(10);
							hbblueBtn.setAlignment(Pos.CENTER_LEFT);
							hbblueBtn.getChildren().add(blueBtn);
							blueBtn.setMaxSize(100, 30);
							blueBtn.setMinSize(100, 30);
							primarygrid.add(hbblueBtn, guesscol, guessrow);
							guessrow+=1;	
							Button greenBtn = new Button("Green");
							greenBtn.setGraphic(new ImageView(green));
							HBox hbgreenBtn = new HBox(10);
							hbgreenBtn.setAlignment(Pos.CENTER_LEFT);
							hbgreenBtn.getChildren().add(greenBtn);
							greenBtn.setMaxSize(100, 30);
							greenBtn.setMinSize(100, 30);
							primarygrid.add(hbgreenBtn, guesscol, guessrow);
							guessrow+=1;
							Button orangeBtn = new Button("Orange");
							orangeBtn.setGraphic(new ImageView(orange));
							HBox hborangeBtn = new HBox(10);
							hborangeBtn.setAlignment(Pos.CENTER_LEFT);
							hborangeBtn.getChildren().add(orangeBtn);
							orangeBtn.setMaxSize(100, 30);
							orangeBtn.setMinSize(100, 30);
							primarygrid.add(hborangeBtn, guesscol, guessrow);
							guessrow+=1;	
							Button purpleBtn = new Button("Purple");
							purpleBtn.setGraphic(new ImageView(purple));
							HBox hbpurpleBtn = new HBox(10);
							hbpurpleBtn.setAlignment(Pos.CENTER_LEFT);
							hbpurpleBtn.getChildren().add(purpleBtn);
							purpleBtn.setMaxSize(100, 30);
							purpleBtn.setMinSize(100, 30);
							primarygrid.add(hbpurpleBtn, guesscol, guessrow);
							guessrow+=1;
							Button redBtn = new Button("Red");
							redBtn.setGraphic(new ImageView(red));
							HBox hbredBtn = new HBox(10);
							hbredBtn.setAlignment(Pos.CENTER_LEFT);
							hbredBtn.getChildren().add(redBtn);
							redBtn.setMaxSize(100, 30);
							redBtn.setMinSize(100, 30);
							primarygrid.add(hbredBtn, guesscol, guessrow);
							guessrow+=1;
							Button yellowBtn = new Button("Yellow");
							yellowBtn.setGraphic(new ImageView(yellow));
							HBox hbyellowBtn = new HBox(10);
							hbyellowBtn.setAlignment(Pos.CENTER_LEFT);
							hbyellowBtn.getChildren().add(yellowBtn);
							yellowBtn.setMaxSize(100, 30);
							yellowBtn.setMinSize(100, 30);
							primarygrid.add(hbyellowBtn, guesscol, guessrow);
							guessrow+=1;
							
							//Other buttons
							Button clearBtn = new Button("Clear Guess");
							HBox hbclearBtn = new HBox(10);
							hbclearBtn.setAlignment(Pos.CENTER_LEFT);
							hbclearBtn.getChildren().add(clearBtn);
							clearBtn.setMaxSize(75, 30);
							clearBtn.setMinSize(75, 30);
							primarygrid.add(hbclearBtn, 4, guessrow);
							guessrow+=1;
							Button historyBtn = new Button("History");
							HBox hbhistoryBtn = new HBox(10);
							hbhistoryBtn.setAlignment(Pos.CENTER_LEFT);
							hbhistoryBtn.getChildren().add(historyBtn);
							historyBtn.setMaxSize(75, 30);
							historyBtn.setMinSize(75, 30);
							primarygrid.add(hbhistoryBtn, 4, guessrow);
							guessrow+=1;
							Button submitBtn = new Button("Submit!");
							HBox hbsubmitBtn = new HBox(10);
							hbsubmitBtn.setAlignment(Pos.CENTER_LEFT);
							hbsubmitBtn.getChildren().add(submitBtn);
							submitBtn.setMaxSize(75, 30);
							submitBtn.setMinSize(75, 30);
							primarygrid.add(hbsubmitBtn, 4, guessrow);
							guessrow+=1;
//							System.out.println(guessrow);
							
							/**
							 * These are the actions that the buttons take.
							 * The colored buttons add a letter to a String to be the code and make the color appear on the screen.
							 * The clear button clears the current code.
							 * The submit button allows the computer to check the user's guess to its code, and it gives feedback.
							 * The history button pulls up a window of the previous guesses and replies.
							 * 
							 * In any case of the game ending, the game asks if the user wants to play another game and provides
							 * Yes and No buttons to restart the game or quit the game.
							 */
							blueBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									final ImageView Dot = new ImageView(blue);
									if (numberofdots == 0) {
										primarygrid.add(Dot, 4, 3);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("B");
										System.out.println("B");
									}
									else if (numberofdots == 1) {
										primarygrid.add(Dot, 4, 4);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("B");
										System.out.println("B");
									}
									else if (numberofdots == 2) {
										primarygrid.add(Dot, 4, 5);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("B");
										System.out.println("B");
									}
									else if (numberofdots == 3) {
										primarygrid.add(Dot, 4, 6);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("B");
										System.out.println("B");
									}
								}
							});	
							
							
							
							greenBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									final ImageView Dot = new ImageView(green);
									if (numberofdots == 0) {
										primarygrid.add(Dot, 4, 3);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("G");
										System.out.println("G");
									}
									else if (numberofdots == 1) {
										primarygrid.add(Dot, 4, 4);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("G");
										System.out.println("G");
									}
									else if (numberofdots == 2) {
										primarygrid.add(Dot, 4, 5);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("G");
										System.out.println("G");
									}
									else if (numberofdots == 3) {
										primarygrid.add(Dot, 4, 6);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("G");
										System.out.println("G");
									}
								}
							});							
							orangeBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									final ImageView Dot = new ImageView(orange);
									if (numberofdots == 0) {
										primarygrid.add(Dot, 4, 3);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("O");
										System.out.println("O");
									}
									else if (numberofdots == 1) {
										primarygrid.add(Dot, 4, 4);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("O");
										System.out.println("O");
									}
									else if (numberofdots == 2) {
										primarygrid.add(Dot, 4, 5);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("O");
										System.out.println("O");
									}
									else if (numberofdots == 3) {
										primarygrid.add(Dot, 4, 6);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("O");
										System.out.println("O");
									}
								}
							});							
							purpleBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									final ImageView Dot = new ImageView(purple);
									if (numberofdots == 0) {
										primarygrid.add(Dot, 4, 3);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("P");
										System.out.println("P");
									}
									else if (numberofdots == 1) {
										primarygrid.add(Dot, 4, 4);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("P");
										System.out.println("P");
									}
									else if (numberofdots == 2) {
										primarygrid.add(Dot, 4, 5);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("P");
										System.out.println("P");
									}
									else if (numberofdots == 3) {
										primarygrid.add(Dot, 4, 6);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("P");
										System.out.println("P");
									}
								}
							});							
							redBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									final ImageView Dot = new ImageView(red);
									if (numberofdots == 0) {
										primarygrid.add(Dot, 4, 3);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("R");
										System.out.println("R");
									}
									else if (numberofdots == 1) {
										primarygrid.add(Dot, 4, 4);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("R");
										System.out.println("R");
									}
									else if (numberofdots == 2) {
										primarygrid.add(Dot, 4, 5);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("R");
										System.out.println("R");
									}
									else if (numberofdots == 3) {
										primarygrid.add(Dot, 4, 6);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("R");
										System.out.println("R");
									}
								}
							});							
							yellowBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									final ImageView Dot = new ImageView(yellow);
									if (numberofdots == 0) {
										primarygrid.add(Dot, 4, 3);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("Y");
										System.out.println("Y");
									}
									else if (numberofdots == 1) {
										primarygrid.add(Dot, 4, 4);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("Y");
										System.out.println("Y");
									}
									else if (numberofdots == 2) {
										primarygrid.add(Dot, 4, 5);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("Y");
										System.out.println("Y");
									}
									else if (numberofdots == 3) {
										primarygrid.add(Dot, 4, 6);
										c.add(Dot);
										numberofdots++;
										userguess = userguess.concat("Y");
										System.out.println("Y");
									}
								}
							});	
							
							//Option to delete a previous entry? (Add a button to clear, too hard to do just previous)
							clearBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									userguess = "";	
									System.out.println("Cleared");
									primarygrid.getChildren().removeAll(c);
									c.clear();
									numberofdots = 0;
								}
							});
									
							//Add history button
							historyBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									String history = new String();
									if (advancedChecker.guesshistory.isEmpty()) {
											System.out.println("No guesses yet");
											histTarget.setFill(Color.FIREBRICK);
											histTarget.setText("No guesses yet");
											
									}else{
										
										for (History h: advancedChecker.guesshistory) {
											System.out.println(h.toString());
											history = history.concat(" - " + h.toString() + "\n");
											
										}
										final Stage dialog = new Stage();
						                dialog.initModality(Modality.APPLICATION_MODAL);
						                dialog.initOwner(primaryStage);
						                dialog.setTitle("History");
						                VBox dialogVbox = new VBox(20);
						                dialogVbox.getChildren().add(new Text(history));
						                Scene dialogScene = new Scene(dialogVbox, 450, 200);
						                dialog.setScene(dialogScene);
						                dialog.show();
										
									}									
								}
							});
							
							submitBtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									actionTarget.setFill(Color.FIREBRICK);
									actionTarget.setText(advancedChecker.GuessMode(userguess, compCode, attempt, guesstext));
									String check = "" + userguess + " -> Result: " + Params.CODE_LENGTH +" Black pegs - You Win!";
									System.out.println(actionTarget.getText().equals(check));
									if (actionTarget.getText().equals(check)) {
										actionTarget.setText("YOU WON!!! Do you want to play again?");
										Button startBtn = new Button("Yes");
										HBox hbstartBtn = new HBox(10);
										hbstartBtn.setAlignment(Pos.CENTER_LEFT);
										hbstartBtn.getChildren().add(startBtn);
										startBtn.setMaxSize(75, 30);
										startBtn.setMinSize(75, 30);
										primarygrid.add(hbstartBtn, 4, 14);
										Button quitBtn = new Button("No");
										HBox hbquitBtn = new HBox(10);
										hbquitBtn.setAlignment(Pos.CENTER_LEFT);
										hbquitBtn.getChildren().add(quitBtn);
										quitBtn.setMaxSize(75, 30);
										quitBtn.setMinSize(75, 30);
										primarygrid.add(hbquitBtn, 4, 15);

										
										startBtn.setOnAction(new EventHandler<ActionEvent>() {
											@Override
											public void handle(ActionEvent event) {
												primarygrid.getChildren().clear();
												try {
													numberofdots = 0;
													attempt = 0;
													userguess = "";
													primaryStage.close();
													primarygrid = new GridPane();
													start(new Stage());
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
										});
										quitBtn.setOnAction(new EventHandler<ActionEvent>() {
											@Override
											public void handle(ActionEvent event) {
												primarygrid.getChildren().clear();
												Platform.exit();
											}
										});
										
											
									}
									else {
										userguess = "";
										attempt++;
										primarygrid.getChildren().removeAll(c);
										c.clear();
										numberofdots = 0;
										
										if (attempt >= Params.MAX_ATTEMPT) {
											lossTarget.setFill(Color.FIREBRICK);
											lossTarget.setText("You ran out of turns! You lost!");
											actionTarget.setText("Do you want to play again?");
											Button startBtn = new Button("Yes");
											HBox hbstartBtn = new HBox(10);
											hbstartBtn.setAlignment(Pos.CENTER_LEFT);
											hbstartBtn.getChildren().add(startBtn);
											startBtn.setMaxSize(75, 30);
											startBtn.setMinSize(75, 30);
											primarygrid.add(hbstartBtn, 4, 14);
											Button quitBtn = new Button("No");
											HBox hbquitBtn = new HBox(10);
											hbquitBtn.setAlignment(Pos.CENTER_LEFT);
											hbquitBtn.getChildren().add(quitBtn);
											quitBtn.setMaxSize(75, 30);
											quitBtn.setMinSize(75, 30);
											primarygrid.add(hbquitBtn, 4, 15);

											
											startBtn.setOnAction(new EventHandler<ActionEvent>() {
												@Override
												public void handle(ActionEvent event) {
													primarygrid.getChildren().clear();
													try {
														numberofdots = 0;
														attempt = 0;
														primaryStage.close();
														primarygrid = new GridPane();
														start(new Stage());
													} catch (Exception e) {
														e.printStackTrace();
													}
												}
											});
											quitBtn.setOnAction(new EventHandler<ActionEvent>() {
												@Override
												public void handle(ActionEvent event) {
													primarygrid.getChildren().clear();
													Platform.exit();
												}
											});
										}
										
									}
								}	
							});

								//All in while loop
									//Submit code button >> Have program wait for user to press 4 buttons >> maybe fill up a string with the respective letters, wait til length = 4
										//Display this visually eventually?
										//Add valid guess to guesshistory
									//Have computer check this code against itself
									//Display results
								//Offer chance to play again

							
							
						}
						
					});	
					
					
				}
			});		
			
		} catch(Exception e) {
			e.printStackTrace();		
		}
	}
	
	/**
	 * This adds the colors into the colorpalette
	 */
	public static void insertColors(){
		Params.colorpalette.add("B"); //Blue
		Params.colorpalette.add("G"); //Green
		Params.colorpalette.add("O"); //Orange
		Params.colorpalette.add("P"); //Purple
		Params.colorpalette.add("R"); //Red
		Params.colorpalette.add("Y"); //Yellow
	}
	
	/**
	 * This makes  sures that the code is valid according to the parameters of the game
	 * @return boolean
	 * 
	 */
	public static boolean makecodevalid(){
		if(userguess.length() == Params.CODE_LENGTH){
			for(int i = 0; i < Params.CODE_LENGTH; i++){
				String temp = Character.toString(userguess.charAt(i));

				if(!(Params.colorpalette.contains(temp))){
					return false;
				}		
			}
			return true;
		}
		else{
			return false;
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
