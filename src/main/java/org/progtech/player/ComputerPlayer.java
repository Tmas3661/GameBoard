package org.progtech.player;

import java.util.Random;

public class ComputerPlayer extends Player{
    public ComputerPlayer(char symbol) {
        super(symbol);
    }

    @Override
    public int getMove(char[][] board) {
        Random random = new Random();
        int column;
        do {
            column = random.nextInt(board[0].length);
        } while (board[0][column] != '.');
        return column;
    }
}
