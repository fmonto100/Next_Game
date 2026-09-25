package nextGame;

import java.util.Random;
import java.util.Scanner;

public class NextGame {

	private int[] targetSequence;
	private String[] highScoreNames;
	private int[] highScoresTurns;
	private int scoreCount;

	public NextGame() {
		targetSequence = new int[5];
		highScoreNames = new String[5];
		highScoresTurns = new int[5];
		
		highScoreNames[0] = "Joe";
		highScoreNames[1] = "Master";
		highScoreNames[2] = "Joe";
		
		highScoresTurns[0] = 3;
		highScoresTurns[1] = 10;
		highScoresTurns[2] = 12;
		
		scoreCount = 3;
	}

	public void generateSequence() {
		int i = 0;
		for (i = 0; i < 5; i++) {
			targetSequence[i] = i + 1;

		}
		Random rand = new Random();
		for (i = 4; i > 0; i--) {
			int num = rand.nextInt(i + 1);
			int temp = targetSequence[i];
			targetSequence[i] = targetSequence[num];
			targetSequence[num] = temp;
		}
	}

	public void play(Scanner scanner) {
		generateSequence();
		int turns = 0;
		boolean won = false;

		System.out.println("\nGame: Who's Next");
		System.out.println("Objective: Identify the Sequence of 5 number between 1 and 5 follow by a space using the fewest turns.");
		System.out.println("If you wish to quit guessing and give up, enter a ZERO for one of your guesses and the game \nwill display the solution and quit.");
		System.out.println("GOOD LUCK!!!\n");

		while (!won) {
			turns++;
			System.out.print("== Turn " + turns + " ==");
			
			int [] guess = new int[5];
			boolean quit = false;
			boolean validLine = false;
			int emptyCount = 0;
			
			
			while(!validLine) {
				System.out.print("Number Sequence: ");
				String inputLine = scanner.nextLine().trim();
				
				if(inputLine.isEmpty()) {
					emptyCount++;
					if(emptyCount >= 2) {
						System.out.println("\nYou pressed enter twice in a row. You chose to quit\n");
						System.out.println("The correct sequence was: \n");
						printTargetSequence();
						return;
					}		
					continue;
				}else {
					emptyCount = 0;
				}
				String[] tokens = inputLine.split("\\s+");
				
				if(tokens.length != 5) {
					System.out.println("Error: You must enter exactly 5 items. You provided " + tokens.length + ".");
					System.out.print("Please try again.\n");
					continue;
				}
			
				boolean lineHasError = false;
				int i = 0;
				for (i = 0; i < 5; i++) {
					try {
						int inputNum = Integer.parseInt(tokens[i]);
						guess[i] = inputNum;
					
						if(inputNum == 0) {
							System.out.println("You chose to quit. The correct sequence was: \n");
							printTargetSequence();
							return;
						}
						
						if (inputNum < 1 || inputNum > 5) {
							System.out.println("Error: '" + tokens[i] + "' is out of range. Numbers must be between 1 and 5 (or 0 to quit).");
							lineHasError = true;
							break;
						}
					}catch (NumberFormatException e) {
							System.out.println("Error: '" + tokens[i] + "' is an invalid character. You must only enter numbers.");
							lineHasError = true;
							break;
					}
				}
					
			if(!lineHasError) {
				
				boolean hasDuplicate = false;
				
				for(int num = 0; num < guess.length; num++) {
						for(int var = num + 1; var < guess.length; var++) {
							if(guess[num] == guess[var]) {
								hasDuplicate = true;
								break;
							}
						}
					}
					if(hasDuplicate) {
						System.out.println("Error: Numbers cannot be repeated. Please re-enter your numbers.");
						continue;
					}else {
						validLine = true;
					}
					
				}else {
				System.out.println("Please enter a completely fresh line. \n");
				}
			}
	
			if (quit) {
				System.out.println("You chose to quit: The correct sequence was: " + targetSequence);
				return;
			}
			won = checkGuess(guess);
			System.out.println();

		}
		System.out.println("You guesses the sequence in " + turns + " turns\n");
		printTargetSequence();
		System.out.print("Enter your name for the leaderboard: ");

		String name = scanner.nextLine();

		addHighScore(turns, name);
		displayHighScores();
	}

	public boolean checkGuess(int[] guess) {
		int correctCount = 0;
		for (int i = 0; i < 5; i++) {
			if (guess[i] == targetSequence[i]) {
				correctCount++;
			}
		}
		if(correctCount < 5) {
		System.out.println("You have " + correctCount + " numbers correct");
		}
		return correctCount == 5;

	}

	public void addHighScore(int turns, String name) {
		int insertIndex = -1;
		for (int i = 0; i < scoreCount; i++) {
			if (turns < highScoresTurns[i]) {
				insertIndex = i;
				break;
			}
		}
		if (insertIndex == -1 && scoreCount < 5) {
			insertIndex = scoreCount;
		}
		if (insertIndex != -1) {
			for (int i = 4; i > insertIndex; i--) {
				highScoresTurns[i] = highScoresTurns[i - 1];
				highScoreNames[i] = highScoreNames[i - 1];
			}
			highScoresTurns[insertIndex] = turns;
			highScoreNames[insertIndex] = name;

			if (scoreCount < 5) {
				scoreCount++;
			}
		}
	}

	public void displayHighScores() {
		System.out.println("\n======== HIGH SCORES =========");
		if (scoreCount == 0) {
			System.out.println("No high scores recorded yet.");
		} else {
			for (int i = 0; i < scoreCount; i++) {
				System.out.println( highScoresTurns[i] + " - " + highScoreNames[i]);
			}
		}
	}
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		int i = 0;
		for(i = 0; i < targetSequence.length; i++) {
			sb.append(targetSequence[i]);
			if(i < targetSequence.length - 1) {
				sb.append(", ");
			}	
		}
		sb.append("]");
		return sb.toString();
	}
	public void printTargetSequence () {
		System.out.println("Game Number Sequence");
		System.out.println("---------------------");
		System.out.print("| ");
		for (int i = 0; i < targetSequence.length; i++) {
			System.out.print(targetSequence[i] + " | ");
		}
		System.out.println();
		System.out.println("---------------------\n");
	}
}
