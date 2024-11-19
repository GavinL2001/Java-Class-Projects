package Projects;

import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

public class OUDSGame {
	/*
		Created by Gavin Liddell
		Created on 08-29-2024
		Last modified on 08-31-2024
	*/
	
	public static void main(String[] args) {
		
		// Instantiate objects
		Scanner scan = new Scanner(System.in);
		Random rand = new Random();
		LocalDate date = LocalDate.now();
		
		// Declare vars
		String name;
		int rounds = 0;
		int points = 20;
		String r1Init = "X"; // Initialize to “X”; correct value will be R
		String r2Init = "X"; // Initialize to “X”; correct value will be R
		int r1Die1 = 0;
		int r1Die2 = 0;
		int r1Sum = 0;
		int r2Die1 = 0;
		int r2Die2 = 0;
		int r2Sum = 0;
		int wager = 0;	// This is the amount associated with the bet
		String bet = "X";	// Initialize to “X”; correct value will be either O or U 
		String outcome = "X";  // One of O, U, D, S
		String eval = "X";  // Either W or L
		String playAgain = "Y";

		
		// Print intro_1 – Instructions and Rules, and intro
		System.out.println("Current Date: " + date + "\n\n"
				+ "Welcome to the Official ITSS 3312 Game of 'OVER UNDER DOUBLE SAME!'\n\n"
				+ "- The game is played by rolling 2 dice twice.\n"
				+ "- Every player begins the game with 20 points.\n"
				+ "- The object of the game is to accumulate more points.\n"
				+ "- If your point balance falls to zero or less, the game automatically ends.\n"
				+ "\n"
				+ "******* Game Instructions and Rules *******\n"
				+ "- When the first roll occurs, the Roll_1 dice values and sum are set.\n"
				+ "- After Roll_1, the player decides if the Roll_2 sum is likely to be over or under the Roll_1 sum.\n"
				+ "- The player makes a wager based on their decision of over or under.\n"
				+ "- Wagers can be from 0 to 10 points.\n"
				+ "- If the player bets OVER or UNDER correctly, the player wins the amount of the wager,\n"
				+ "  and that value is added to their point total.\n"
				+ "- If the player bets OVER or UNDER incorrectly, the player loses points in the amount of the wager,\n"
				+ "  and that value is subtracted from the player's point total.\n"
				+ "\n"
				+ "*** Special Cases ***\n"
				+ "- If Roll_2 yields a double (e.g., 2,2 or 5,5), OR Roll_2 sum is the same as the Roll_1 sum,\n"
				+ "  the round is lost and the amount of the wager is subtracted from the player's point total.\n"
				+ "- Note that the round is lost irrespective of the outcome of the over/under bet.\n"
				+ "\n"
				+ "And that's the name of the game - OVER UNDER DOUBLE SAME!\n");
		
		// Prompt for and read-in name and then print intro_2, the welcome message by name
		System.out.print("Before we start, I need to get your name. Please enter it here: ");
		name = scan.next();
		
		System.out.println("Hi " + name + "! It's a pleasure to meet you!\n"
				+ "Let's get this wild and crazy game underway!\n");
		
		// Begin while loop for playAgain
		while (playAgain.equals("Y")) {
		
			// Prompt for and read-in the number of rounds to be played
			System.out.print("Now, please enter the number of rounds you'd like to play: ");
			rounds = scan.nextInt();
			System.out.println("Excellent! You've decided to play " + rounds + " rounds. Let's begin!");
		
			// Begin for loop for number of rounds
			for (int roundCounter = 1; roundCounter <= rounds; roundCounter++) {
				System.out.println("\n******* Round " + roundCounter + " *******");
				
				do {
					// Prompt user to confirm dice roll
					System.out.print("Enter the letter 'R' to roll the first set of dice: ");
					r1Init = scan.next().trim().toUpperCase();
					
					// Check if player is ready or not
					if (r1Init.equals("R")) {
					
						// Initiate Roll_1, print summary of Roll_1
						r1Die1 = rand.nextInt(6) + 1; // 1;
						r1Die2 = rand.nextInt(6) + 1; // 6;
						r1Sum = r1Die1 + r1Die2;
						System.out.println("\nDice 1 Result: " + r1Die1
							+ "\nDice 2 Result: " + r1Die2
							+ "\nTotal:         " + r1Sum
							+ "\n");
						break;
						
					} else {
						System.out.println("No worries! Take your time!\n");
					}
					
				} while (!r1Init.equals("R"));
				
				// Prompt for and read-in bet and wager
				System.out.print("Do you think the next total will be +" + r1Sum + " or -" + r1Sum + "?\n"
						+ "Please enter your answer using 'O' for OVER or 'U' for UNDER: ");
				bet = scan.next();
				bet = bet.toUpperCase();
				if (bet.equals("O")) bet = "OVER";
				if (bet.equals("U")) bet = "UNDER";
				// System.out.println(bet);
				
				System.out.print("Please enter how many points you'd like to wager (Current points: " + points + "): ");
				wager = scan.nextInt();
				// System.out.println(wager);
				
				// Initiate Roll_2
				do {
					// Prompt user to confirm dice roll
					System.out.print("Enter the letter 'R' to roll the second set of dice: ");
					r2Init = scan.next().trim().toUpperCase();
					
					// Check if player is ready or not
					if (r2Init.equals("R")) {
					
						// Initiate Roll_1, print summary of Roll_1
						r2Die1 = rand.nextInt(6) + 1; // 4;
						r2Die2 = rand.nextInt(6) + 1; // 4;
						r2Sum = r2Die1 + r2Die2;
						System.out.println("\nDice 1 Result: " + r2Die1
								+ "\nDice 2 Result: " + r2Die2
								+ "\nTotal:         " + r2Sum
								+ "\n");
						break;
					}
					else {
						System.out.println("No worries! Take your time!\n");
					}
					
				} while (!r2Init.equals("R"));
				
				// Evaluate and compare the rolls
				if (r1Sum == r2Sum)
					outcome = "SAME";
				else if (r2Die1 == r2Die2)
					outcome = "DOUBLE";
				else if(r1Sum > r2Sum)
					outcome = "UNDER";
				else if (r1Sum < r2Sum)
					outcome = "OVER";
				else
					System.out.println("There was an error in determining the rolls.\n");
				
				// Determine outcome based on bet.
				switch (outcome) {
				
				case ("OVER"):
					if (bet.equals("OVER"))
						eval = "W";
					else
						eval = "L";
					break;
				
				case ("UNDER"):
					if (bet.equals("UNDER"))
						eval = "W";
					else
						eval = "L";
					break;
				
				case ("DOUBLE"):
					eval = "L";
					break;
				
				case ("SAME"):
					eval = "L";
					break;
					
				default:
					System.out.println("There was an error with the logic in determining the outcome.\n");
				
				}
				
				// Perform end-of-round processing
				switch (eval) {
				
				case ("W"):
					System.out.println(outcome + "! Congratulations, you won this round!\n");
					points = points + wager;
					break;
					
				case ("L"):
					System.out.println(outcome + "! Sorry, the house won this round.\n");
					points = points - wager;
					break;
					
				default:
					System.out.println("There was an error with the logic in determining the evaluation.\n");
					
				}
		
				// Print outcome of the round
				System.out.println("Summary:" 
						+ "\n- Roll 1: " + r1Sum
						+ "\n- Roll 2: " + r2Sum
						+ "\n- Wager: " + wager
						+ "\n- Bet: " + bet
						+ "\n- Result: " + outcome
						+ "\n- New Point Total: " + points);
	
				// Perform check for bankruptcy 
				if (points <= 0) {
					System.out.println("\nUh oh! You went bankrupt!");
					break;
				}
				
			}
			
			// Perform end-of-game processing
			System.out.println("\nAaaaaaaand that's the end of the game!");
			
			// Print game summary
			System.out.println("\nFinal Points: " + points);
		
			// Prompt for playAgain
			System.out.print("\nWould you like to play again? (Y/N): ");
			playAgain = scan.next().trim().toUpperCase();
		
		}
		// Print outro
		scan.close();
		System.out.printf("\nThanks for playing, " + name + "! Have a wonderful day!");
	}

}
