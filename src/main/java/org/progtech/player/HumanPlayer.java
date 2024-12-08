package org.progtech.player;
import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(char symbol) {
        super(symbol); // Pass the symbol to the superclass constructor
    }

    @Override
    public int getMove(char[][] board) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input.toLowerCase().charAt(0) - 'a';
    }
}
