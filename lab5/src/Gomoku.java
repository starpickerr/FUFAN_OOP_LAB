public class Gomoku extends Board {
    private int round = 0; // 记录当前回合数

    public Gomoku() {
        super(); // 调用父类构造函数，设置棋盘类型为Gomoku
        initGomokuBoard(); // 初始化棋盘
    }

    public void setRound() {
        round++; // 设置当前回合数
    }

    private void initGomokuBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                chessBoard[i][j] = 0; // 初始化棋盘为空
            }
        }
    }

    @Override
    public void printBoard(Player player1, Player player2, Player currentPlayer) {
        clearScreen();
        // 打印棋盘的列标签
        System.out.print("  A B C D E F G H");

        for (int i = 0; i < SIZE; i++) {
            System.out.print("\n" + (i + 1) + " ");

            // 打印棋盘上的每个格子
            for (int j = 0; j < SIZE; j++) {
                if (chessBoard[i][j] == 0) {
                    System.out.print("\u00b7 ");
                } else if (chessBoard[i][j] == 1) {
                    System.out.print(player1.getSymbol() + " ");
                } else if (chessBoard[i][j] == 2) {
                    System.out.print(player2.getSymbol() + " ");
                }
            }

            // 打印棋盘 ID 和棋盘列表标题
            if (i == 2) {
                System.out.print("   棋盘 " + boardID + "            Game List:");
            }
            // 打印玩家1的信息（第4行）
            if (i == 3) {
                System.out.print("   ");
                System.out.printf("%-6s", player1.getName()); // 添加空格以对齐玩家信息
                if (currentPlayer == player1) {
                    System.out.print(player1.getSymbol());
                } else {
                    System.out.print(" ");
                }
            }
            // 打印玩家2的信息（第5行）
            if (i == 4) {
                System.out.print("   ");
                System.out.printf("%-6s", player2.getName()); // 添加空格以对齐玩家信息
                if (currentPlayer == player2) {
                    System.out.print(player2.getSymbol());
                } else {
                    System.out.print(" ");
                }
            }
            if (i == 5) {
                System.out.print("Current round: ");// 添加空格以对齐棋盘列表
                System.out.printf("%2d", round); // 打印当前回合数
                System.out.print(" ");// 添加空格以对齐棋盘列表
            }
            if (i > 5) {
                System.out.print("            ");// 添加空格以对齐棋盘列表
            }
            // 打印棋盘列表
            int j = i - 3;
            if (j < allBoards.size() && j >= 0) {
                Board board = allBoards.get(j);
                String boardType = "";// 声明变量以存储棋盘类型
                if (board instanceof ReversiBoard) {
                    boardType = "Reversi";
                } else if (board instanceof PeaceBoard) {
                    boardType = "Peace";
                } else if (board instanceof Gomoku) {
                    boardType = "Gomoku";
                }
                if (j != 2) {
                    System.out.print("         " + (j + 1) + "." + boardType);
                }else if (j == 2) {
                    System.out.print("   " + (j + 1) + "." + boardType);
                }
            }
        }
    }

    public static boolean checkWin(int[][] board, int playerID) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == playerID) {
                    if (checkDirection(board, row, col, playerID, 1, 0) || // 横向
                            checkDirection(board, row, col, playerID, 0, 1) || // 纵向
                            checkDirection(board, row, col, playerID, 1, 1) || // 主对角线
                            checkDirection(board, row, col, playerID, 1, -1)) { // 副对角线
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static boolean checkDirection(int[][] board, int row, int col, int playerID, int deltaRow, int deltaCol) {
        int count = 0;
        for (int i = 0; i < 5; i++) {
            int newRow = row + i * deltaRow;
            int newCol = col + i * deltaCol;
            if (newRow >= 0 && newRow < SIZE && newCol >= 0 && newCol < SIZE && board[newRow][newCol] == playerID) {
                count++;
            } else {
                break;
            }
        }
        return count == 5;
    }

    @Override
    public boolean isGameOver(Player player1, Player player2, Player currentPlayer) { // 检查棋盘上是否还有可放置棋子的空位
        if (checkWin(chessBoard, 1) || checkWin(chessBoard, 2)) {
            return true; // 如果有玩家获胜，游戏结束
        }
        // 检查棋盘上是否还有可放置棋子的空位
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (chessBoard[i][j] == 0) {
                    return false; // 如果有空位，游戏未结束
                }
            }
        }
        return true; // 没有合法位置，游戏结束
    }
}