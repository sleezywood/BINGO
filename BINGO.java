/*
Main.java
Create a BINGO game simulator
Sudhanya Golla
Created July 8th, 2024
Last Modified July 8th 2024
*/

// Java Setup
import java.util.*;

class BINGO {
	static Scanner in = new Scanner(System.in);
	static Random rand = new Random();

	// Main program
	public static void main(String[] args) throws InterruptedException {
	
	// Bingo simulator - Create a computer simulation of bingo
	int board[][] = new int[5][5];
	ArrayList <Integer> cardValues = new ArrayList();
	ArrayList <Integer> cardsDrawn = new ArrayList();
	boolean userWon = false;
	boolean validNum = false;
	String input = "Y";

	// Restart program as many times as user inputs
	while (input.equals("Y"))
	{
		// Generate each column of player's bingo card
		generateColumnCard(board, cardValues, 0, 1, 16);
		generateColumnCard(board, cardValues, 1, 16, 31);
		generateColumnCard(board, cardValues, 2, 31, 46);
		generateColumnCard(board, cardValues, 3, 46, 61);
		generateColumnCard(board, cardValues, 4, 61, 76);
	
		// Output bingo card
		System.out.println("Your board looks like:\nB  I  N  G  O");
		printBingo(board);
		System.out.println("");
	
		// Allow cards to be drawn until a BINGO is achieved
		while (userWon != true)
		{
			int randomDraw = rand.nextInt(1, 76);
			randomDraw = generateNumber(randomDraw, cardsDrawn);
			// Thread.sleep(1000);
			System.out.println("Number called: " + randomDraw);
			checkCards(board, randomDraw, cardValues);
			userWon = checkVictory(board, userWon);
		}
	
		// Once completed, output that game is done
		System.out.println("\nBINGO game is done.\nCards drawn in order: ");
	
		// Output cards drawn out in order
		for (int cards = 0; cards < cardsDrawn.size(); cards++)
		{
			System.out.print(cardsDrawn.get(cards) + " ");
		}
		
		System.out.println("");
		System.out.println("\nFinal board is (0 indicates any BINGO coin placed on board): ");
		printBingo(board);
	
		// Prompt user to restart or quit game 
		System.out.println("\nDo you want to restart the game?\n'Y' to restart\n'N' to stop simulation.");
		input = in.nextLine();

		// Restart or quit game based on user answer
		if (input.equals("Y"))
		{
			cardValues.clear();
			cardsDrawn.clear();
			userWon = false;
		}
		else if (input.equals("N"))
		{
			break;
		}
	}
  
}
	
	// Subprograms for BINGO Game
	// Generate values as per game for one column in the BINGO game board
	public static void generateColumnCard(int board[][],ArrayList<Integer> cardValues, int row, int start, int end)
	{			
		// Make sure values are generated for each position within the column
		for (int col = 0; col < 5; col ++)
		{
			int randomNum = rand.nextInt(start, end);
	
			// Check if number is repeated in a previous iteration
			// If so, regenerate a value again, or store it within the board if not
			if (cardValues.contains(randomNum))
			{
				randomNum = rand.nextInt(start, end);
				col--;
			}
			else
			{
				cardValues.add(randomNum);
				board[col][row] = randomNum;
			}	
		}
	}

	// Print entire bingo card for player to see
	public static void printBingo(int board[][])
	{
		// Iterate through each row and column and separate each row
		for (int rowBoard = 0; rowBoard < 5; rowBoard ++)
		{
			for (int colBoard = 0; colBoard < 5; colBoard++)
			{
				System.out.print(board[rowBoard][colBoard] + " ");
			}
			
			System.out.println("");
		}
	}

	// Generate random numbers to be checked by the BINGO board
	public static int generateNumber(int random, ArrayList<Integer> cardsDrawn)
	{
		boolean validNum = false;

		// Make sure number chosen is eventually not repeated previously
		while (validNum == false)
		{
			// Generate a randomized value
			// Validate if it was repeated previously
			if (cardsDrawn.contains(random))
			{
				random = rand.nextInt(1, 76);
			}
			else
			{
				validNum = true;
				cardsDrawn.add(random);
			}
		}

		validNum = false;
		return random;	
	}

	// Check whether randomized number is also on player's BINGO board
	public static void checkCards(int board[][], int randDraw, ArrayList<Integer> cardValues)
	{

		// Output statement determining whether number is on BINGO board or not
		if (cardValues.contains(randDraw))
		{
			System.out.println("Number is on board.");
		}
		else
		{
			System.out.println("Number is not on board.");
		}

		// If card is on board, find its position and remove its value from board
		for (int rowCard = 0; rowCard < 5; rowCard++)
		{
			for (int colCard = 0; colCard < 5; colCard++)
			{
				if (board[rowCard][colCard] == randDraw)
				{
					board[rowCard][colCard] = 0;
				}
				
			}
		}
		
	}

	// Check if user got a BINGO (they won)
	// Output statement telling user what type of row was won 
	public static boolean checkVictory(int board[][], boolean won)
	{

		for (int inBoard = 0; inBoard < 5; inBoard++)
		{
			// Check if user achieved a win horizontally (full horizontal row)
			// Output that user won horizontally
			if (board[inBoard][0] == board[inBoard][1] && board[inBoard][1] == board[inBoard][2] && board[inBoard][2] == board[inBoard][3] && board[inBoard][3] == board[inBoard][4])
			{
				won = true;
				System.out.println("User won through a horizontal row. ");
			}
			
			// Check if user achieved a win vertically (full vertical row)
			// Output that user won vertically
			if (board[0][inBoard] == board[1][inBoard] && board[1][inBoard] == board[2][inBoard] && board[2][inBoard] == board[3][inBoard] && board[3][inBoard] == board[4][inBoard])
			{
				won = true;
				System.out.println("User won through a vertical row. ");
			}
		}

		// Check if user won diagonally (full diagonal rows)
		// Output statement
		if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[2][2] == board[3][3] && board[3][3] == board[4][4])
		{
			won = true;
			System.out.println("User won through a diagonal row. ");
		}
		else if (board[0][4] == board[1][3] && board[1][3] == board[2][2] && board[2][2] == board[3][1] && board[3][1] == board[4][0])
		{
			won = true;
			System.out.println("User won through a diagonal row. ");
		}
		
		return won;
	}
	
}