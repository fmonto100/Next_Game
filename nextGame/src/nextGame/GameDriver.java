package nextGame;

import java.util.Scanner;

public class GameDriver {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		NextGame game = new NextGame();
		
		boolean keepPlaying = true;
		while(keepPlaying) {
			game.play(scanner);
			
			System.out.print("Would you like to play another game? (yes/no): ");
			String choice = scanner.next().toLowerCase();
			if(!choice.startsWith("y")) {
				keepPlaying = false;
			}
		}
		System.out.println("Thanks for playing!");
		scanner.close();
		

	}

}
