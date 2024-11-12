package Projects;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RunOUDSGame2 {
	/*
		Created by Gavin Liddell
		Created on 10-04-2024
		Last modified on 10-06-2024
	*/
	
	public static void main(String[] args) {
		// Instantiate objects
		Game game = new Game();
		Session session = new Session();
		Scanner scan = new Scanner(System.in);
		Random rand = new Random();
		
		// Print welcome and rules
		session.prtWelcome();
		
		// Prompt for and read-in name
		session.setName(scan);
		
		// Start main do-while loop
		do {
			game.setRounds(scan);
			
			// Begin round for loop
			for (int roundCounter = 1; roundCounter <= game.getRounds(); roundCounter++) {
				System.out.printf("%n******* Round %d *******%n", roundCounter);
				
				// Set points to 20 to start.
				if (roundCounter == 1) game.setPoints(20);
				
				// Initiate first roll
				do {
					game.setRollInit(scan);
					
					if (game.getRollInit()) {
						game.setRoll1(rand);
						game.prtRoll1(game.getRoll1());
					}
					else System.out.println("No worries! Take your time!\n");
				
				} while (!game.getRollInit()); // End of roll1 do-while loop
				
				// Prompt for and read-in bet and wager
				game.setBet(scan);
				game.setWager(scan);
				
				// Initiate second roll
				do {
					game.setRollInit(scan);
					
					if (game.getRollInit()) {
						game.setRoll2(rand);
					}
					else System.out.println("No worries! Take your time!\n");
				
				} while (!game.getRollInit()); // End of roll2 do-while loop
				
				// Determine outcome along with bet/wager
				game.evalBetO(game.getRoll1(),game.getRoll2());
				game.evalBetU(game.getRoll1(),game.getRoll2());
				game.evalBetD(game.getRoll1(),game.getRoll2());
				game.evalBetS(game.getRoll1(),game.getRoll2());
				
				// Print round results
				game.prtEndOfRound(roundCounter, game.getPoints());
				game.prtWL(game.getWL());
				game.setBankrupt();
				
				// Go to end-of-game functions if bankrupt = true
				if (game.getBankrupt()) {
					System.out.println("\nUh oh! You went bankrupt!");
					break;
				}
			} // End of for loop
		
			// End of game functions
			game.prtEndOfGame(scan);
			
			// Prompt for playAgain
			game.setPlayAgain(scan);
			
		} while (game.getPlayAgain()); // End main do-while loop
		
		// Print final message of session
		session.prtEndOfSession();
		scan.close();
		
	} // End of main method (driver)

} // End of OUDSGame2

// Game class
class Game {	// Associated with primary game functions
	
	// Declare vars
	private int rounds;					// Number of rounds the player wants to play in a single game
	private String rollInit; 			// Looking for "R" as value
	private int[] roll1 = new int[3];	// Contains r1d1, r1d2. and sum of two rolls
	private int[] roll2 = new int[3];	// Contains r2d1, r2d2. and sum of two rolls
	private int wager;					// This is the amount associated with the bet
	private String bet;					// Initialize to “X”; correct value will be either O or U 
	private String outcome;  			// One of O, U, D, S
	private boolean win;  				// Either true or false
	private int points;					// Number of points the player has. Resets to 20 every game
	private String playAgain;			// Looking for "Y" as value
	private ArrayList<String> history = new ArrayList<String>();	// Array for storing history of rounds.
	private boolean bankrupt = false;	// Either true or false
	
	// Define methods
	
	// setPoints
	void setPoints(int points) {	// Assigns points to private var
		this.points = points;
	}
	
	// getPoints
	int getPoints() {	// Fetches points from private var
		return points;
	}
	
	// setRounds
	void setRounds(Scanner scan) {	// Establishes number of rounds
		System.out.print("Now, please enter the number of rounds you'd like to play: ");
		rounds = scan.nextInt();
		System.out.println("Excellent! You've decided to play " + rounds + " round(s). Let's begin!");
	}
	
	// getRounds
	int getRounds() {	// Fetches number of rounds
		return rounds;
	}
	
	// setRollInit
	void setRollInit(Scanner scan) {	// Confirms if player is ready to roll
			rollInit = "X";
			System.out.print("Enter the letter 'R', then press ENTER to roll the dice: ");
			rollInit = scan.next().trim().toUpperCase();
	}
	
	// getRollInit
	boolean getRollInit() {		// Fetches confirmation
		if (rollInit.equals("R")) return true;
		else return false;
	}
	
	// setRoll1
	void setRoll1(Random rand) {	// Rolls first set of dice
		for (int i = 0; i < (roll1.length - 1); i++) roll1[i] = rand.nextInt(6) + 1;
//		roll1[0] = 1;
//		roll1[1] = 3;
		roll1[2] = roll1[0] + roll1[1];
	}
	
	// getRoll1
	int[] getRoll1() {	// Fetches results from first set of dice
		return roll1;
	}
	
	// prtRoll1
	void prtRoll1(int[] rollArray) {	// Prints results of first set of dice
		System.out.println(
				  "\n----------------\n"
				+   " Roll 1 Results"
				+ "\n----------------" 
				+ "\n Dice 1:      " + rollArray[0]
				+ "\n Dice 2:      " + rollArray[1]
				+ "\n Total:       " + rollArray[2]
				+ "\n");
	}
	
	// setRoll2
	void setRoll2(Random rand) {	// Rolls second set of dice
		for (int i = 0; i < (roll2.length - 1); i++) roll2[i] = rand.nextInt(6) + 1;
//		roll2[0] = 1;
//		roll2[1] = 3;
		roll2[2] = roll2[0] + roll2[1];
	}
	
	// getRoll2
	int[] getRoll2() {	// Fetches results from second set of dice
		return roll2;
	}
	
	// setBet
	void setBet(Scanner scan) {		// Prompts for and reads-in bet
		System.out.print("Do you think the next total will be +" + roll1[2] + " or -" + roll1[2] + "?\n"
				+ "Please enter your answer using 'O' for OVER or 'U' for UNDER: ");
		bet = scan.next();
		bet = bet.toUpperCase();
		if (bet.equals("O")) bet = "OVER";
		if (bet.equals("U")) bet = "UNDER";
	}
	
	// setWager
	void setWager(Scanner scan) {	// Prompts for and reads-in wager
		System.out.print("Please enter how many points you'd like to wager (Current points: " + points + "): ");
		wager = scan.nextInt();
	}
	
	// evalBetO
	void evalBetO(int[] roll1, int[] roll2) {	// Evaluates roll and/or bet is over
		if (roll1[2] < roll2[2] && roll2[0] != roll2[1]) {
			outcome = "OVER";
			if (bet.equals("OVER")) {
				setPoints(points + wager);
				win = true;
			}
			else {
				setPoints(points - wager);
				win = false;
			}
		}
	}
	
	// evalBetU
	void evalBetU(int[] roll1, int[] roll2) {	// Evaluates roll and/or bet is under
		if(roll1[2] > roll2[2] && roll2[0] != roll2[1]) {
			outcome = "UNDER";
			if (bet.equals("UNDER")) {
				setPoints(points + wager);
				win = true;
			}
			else {
				setPoints(points - wager);
				win = false;
			}
		}
	}
	
	// evalBetD
	void evalBetD(int[] roll1, int[] roll2) {	// Evaluates roll contains a double
		if (roll2[0] == roll2[1]) {
			outcome = "DOUBLE";
			win = false;
			setPoints(points - wager);
		}
	}
	
	// evalBetS
	void evalBetS(int[] roll1, int[] roll2) {	// Evaluates roll contains the same sum as the first roll
		if (roll1[2] == roll2[2] && roll2[0] != roll2[1]) {
			outcome = "SAME";
			win = false;
			setPoints(points - wager);
		}
	}
	
	// getWL
	boolean getWL() {	// Fetches win/loss status
		return win;
	}
	
	// prtWL
	void prtWL(boolean win) {	// Prints out a message depending on if the player won or lost the round.
		if (win) System.out.println("\nCongratulations, you won this round!");
		else System.out.println("\nSorry, the house won this round.");
	}
	
	// prtEndOfRound
	void prtEndOfRound(int round, int currentPoints) {	// Print outcome of the round and stores it in an array
		System.out.println(
				  "\n-----------------------\n"
				+   "        Summary        "
				+ "\n-----------------------" 
				+ "\nRoll 1:          " + roll1[0] + "," + roll1[1]
				+ "\nRoll 1 Total:    " + roll1[2]
				+ "\nBet:             " + bet
				+ "\nWager:           " + wager
				+ "\nRoll 2:          " + roll2[0] + "," + roll2[1]
				+ "\nRoll 2 Total:    " + roll2[2]
				+ "\nResult:          " + outcome
				+ "\nNew Point Total: " + currentPoints
				+ "\n-----------------------\n");
		
		history.add(
				  "\n-----------------------\n"
				+ "        Round " + round
				+ "\n-----------------------" 
				+ "\nRoll 1:          " + roll1[0] + "," + roll1[1]
				+ "\nRoll 1 Total:    " + roll1[2]
				+ "\nBet:             " + bet
				+ "\nWager:           " + wager
				+ "\nRoll 2:          " + roll2[0] + "," + roll2[1]
				+ "\nRoll 2 Total:    " + roll2[2]
				+ "\nResult:          " + outcome
				+ "\nNew Point Total: " + currentPoints
				+ "\n-----------------------\n");
	}
	
	// setBankrupt
	void setBankrupt() {	// Perform check for bankruptcy 
		if (points <= 0) bankrupt = true;
		else bankrupt = false;
	}
	
	// getBankrupt
	boolean getBankrupt() {	// Fetch bankruptcy status
		return bankrupt;
	}
	
	// prtEndOfGame
	void prtEndOfGame(Scanner scan) {	// Print end of game information
		// Print game summary
		System.out.println("\nAaaaaaaand that's the end of the game!");
		System.out.println("\nFinal Points: " + points);
		
		// Prompt for history
		System.out.print("\nWould you like to see your history of rounds for this game? (Y/N): ");
		String seeHistory = scan.next().trim().toUpperCase();
		
		// Check if user wants to see history. Print if answer is yes.
		if (seeHistory.equals("Y")) {
			System.out.println("\nHere is your history of rounds for this game:");
			for (String z : history) System.out.printf(z);
		}
		history.clear();
	}
	
	// setPlayAgain
	void setPlayAgain(Scanner scan) {	// Prompt and read-in play again status
		System.out.print("\nWould you like to play again? (Y/N): ");
		playAgain = scan.next().trim().toUpperCase();
	}
	
	// getPlayAgain
	boolean getPlayAgain() {	// Fetch play again status
		if (playAgain.equals("Y")) return true;
		else return false;
	}
	
} // End of Game class

// Session class
class Session {		// Associated with beginning and ending of a session
	
	// Declare variable
	private String name;	// Name of player
	
	// Implement methods
	
	// prtWelcome
	void prtWelcome() {		// Prints welcome page
		System.out.println("Welcome to the Official ITSS 3312 Game of 'OVER UNDER DOUBLE SAME!'\n\n"
				+ "- The game is played by rolling 2 dice twice.\n"
				+ "- Every player begins the game with 20 points.\n"
				+ "- The object of the game is to accumulate more points.\n"
				+ "- If your point balance falls to zero or less, the game automatically ends.\n"
				+ "\n"
				+ "******* Game Instructions and Rules *******\n"
				+ "- When the first roll occurs, the Roll_1 dice values and sum are set.\n"
				+ "- After Roll 1, the player decides if the Roll 2 sum is likely to be over or under the Roll 1 sum.\n"
				+ "- The player makes a wager based on their decision of over or under.\n"
				+ "- Wagers can be from 0 to 10 points.\n"
				+ "- If the player bets OVER or UNDER correctly, the player wins the amount of the wager,\n"
				+ "  and that value is added to their point total.\n"
				+ "- If the player bets OVER or UNDER incorrectly, the player loses points in the amount of the wager,\n"
				+ "  and that value is subtracted from the player's point total.\n"
				+ "\n"
				+ "*** Special Cases ***\n"
				+ "- If Roll 2 yields a double (e.g., 2,2 or 5,5), OR Roll 2 sum is the same as the Roll 1 sum,\n"
				+ "  the round is lost and the amount of the wager is subtracted from the player's point total.\n"
				+ "- Note that the round is lost irrespective of the outcome of the over/under bet.\n"
				+ "\n"
				+ "And that's the name of the game - OVER UNDER DOUBLE SAME!\n");
	}
	
	// setName
	void setName(Scanner scan) {	// Fetches player's name
		// Prompt and read-in user input
		System.out.print("Before we start, I need to get your name. Please enter it here: ");
		name = scan.next();
		System.out.println("Hi " + name + "! It's a pleasure to meet you!\n"
				+ "Let's get this wild and crazy game underway!\n");
	}
	
	// prtEndOfSession
	void prtEndOfSession() {	// Thanks player for playing the game
		System.out.printf("\nThanks for playing, " + name + "! Have a wonderful day!");
	}
	
} // End of Session class