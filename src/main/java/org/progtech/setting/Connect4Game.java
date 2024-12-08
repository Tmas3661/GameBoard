package org.progtech.setting;

import org.progtech.db.DBManager;
import org.progtech.player.ComputerPlayer;
import org.progtech.player.HumanPlayer;
import org.progtech.player.Player;

import java.util.Scanner;


public class Connect4Game {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final char EMPTY = '.';
    private static final char PLAYER_SYMBOL = 'Y';
    private static final char COMPUTER_SYMBOL = 'R';
    private char[][] board;
    private Player humanPlayer;
    private Player computerPlayer;
    private boolean playerTurn;
    private String playerName;
    public DBManager dbManager;

    public Connect4Game() {
        board = new char[ROWS][COLUMNS];
        dbManager = new DBManager();
        initializeBoard();
    }

    public void mainMenu() {
        System.out.println("Entering Main Menu...");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Connect 4 ===");
            System.out.println("1. Show Top Winners");
            System.out.println("2. Start Game");
            System.out.println("Enter your choice (1 or 2): ");

            String choice = scanner.nextLine();
            System.out.println("User choice: " + choice); // Debug line

            if (choice.equals("1")) {
                System.out.println("Showing Top Winners..."); // Debug line
                showTopWinners();
            } else if (choice.equals("2")) {
                System.out.println("Starting the game..."); // Debug line
                start();
                break;
            } else {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        }
    }

    public void showTopWinners() {
        System.out.println("=== Top 3 Winners ===");
        dbManager.getTopWinners();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Press any key to return to the main menu...");
        scanner.nextLine();
    }

    public void start() {
        askPlayerName();
        printBoard();

        humanPlayer = new HumanPlayer(PLAYER_SYMBOL);
        computerPlayer = new ComputerPlayer(COMPUTER_SYMBOL);
        playerTurn = true;

        boolean gameWon = false;

        while (!gameWon) {
            if (playerTurn) {
                System.out.println(playerName + "'s turn! Enter column (a-g): ");
                int column = humanPlayer.getMove(board);
                if (isValidMove(column)) {
                    makeMove(column, PLAYER_SYMBOL);
                    playerTurn = false;
                } else {
                    System.out.println("Invalid move. Try again.");
                    continue;
                }
            } else {
                System.out.println("Computer's turn...");
                int column = computerPlayer.getMove(board);
                System.out.println("Computer chooses column: " + (char) ('a' + column));
                makeMove(column, COMPUTER_SYMBOL);
                playerTurn = true;
            }

            printBoard();
            gameWon = checkWin();

            if (gameWon) {
                if (playerTurn) {
                    System.out.println("Computer won!");
                } else {
                    System.out.println(playerName + " won!");
                    dbManager.saveWin(playerName); // Save player's win to the database
                }
            }
        }
    }

    private void askPlayerName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        playerName = scanner.nextLine();
    }

    public void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    public void printBoard() {
        System.out.println(" a b c d e f g");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                System.out.print(" " + board[i][j]);
            }
            System.out.println();
        }
    }

    public boolean isValidMove(int column) {
        return column >= 0 && column < COLUMNS && board[0][column] == EMPTY;
    }

    public void makeMove(int column, char player) {
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][column] == EMPTY) {
                board[i][column] = player;
                break;
            }
        }
    }

    public boolean checkWin() {
        return checkHorizontalWin() || checkVerticalWin() || checkDiagonalWin();
    }

    private boolean checkHorizontalWin() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS - 3; j++) {
                if (board[i][j] != EMPTY && board[i][j] == board[i][j + 1] &&
                        board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVerticalWin() {
        for (int j = 0; j < COLUMNS; j++) {
            for (int i = 0; i < ROWS - 3; i++) {
                if (board[i][j] != EMPTY && board[i][j] == board[i + 1][j] &&
                        board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonalWin() {
        for (int i = 3; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS - 3; j++) {
                if (board[i][j] != EMPTY && board[i][j] == board[i - 1][j + 1] &&
                        board[i][j] == board[i - 2][j + 2] && board[i][j] == board[i - 3][j + 3]) {
                    return true;
                }
            }
        }
        for (int i = 0; i < ROWS - 3; i++) {
            for (int j = 0; j < COLUMNS - 3; j++) {
                if (board[i][j] != EMPTY && board[i][j] == board[i + 1][j + 1] &&
                        board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3]) {
                    return true;
                }
            }
        }
        return false;
    }

    // Get the current game board
    public char[][] getBoard() {
        return board;
    }
}