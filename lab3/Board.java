import java.io.IOException;

class Board {
    private int[][] board;
    int round = 0;
    private static final int SIZE = 8;

    public Board() {
        board = new int[SIZE][SIZE];
        initializeBoard();
    }

    private void initializeBoard() {
        board[3][3] = 2;
        board[3][4] = 1;
        board[4][3] = 1;
        board[4][4] = 2;
    }

    public boolean isValidMove(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE && board[x][y] != 1 && board[x][y] != 2;
    }

    public void makeMove(int x, int y, int playerNumber) {
        board[x][y] = playerNumber;
    }

    public void display(Player player1, Player player2, int boardNumber) {
        clearScreen();
        System.out.print("  A B C D E F G H");
        for (int i = 0; i < SIZE; i++) {
            System.out.print("\n" + (i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    System.out.print("\u00b7 ");
                } else if (board[i][j] == 1) {
                    System.out.print("\u25cf ");
                } else if (board[i][j] == 2) {
                    System.out.print("\u25cb ");
                }
            }
            if (i == 2) {
                System.out.print("   " + "棋盘 " + ++boardNumber);
            } else if (i == 3) {
                System.out.print("   " + player1.getName() + " \u25cf");
            } else if (i == 4) {
                System.out.print("   " + player2.getName() + " \u25cb");
            }
        }
    }

    private void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}