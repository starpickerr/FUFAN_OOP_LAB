public class ReversiBoard extends Board {
    public ReversiBoard() {
        super();
    }

    @Override
    public int isValidMove(int row, int col, Player currentPlayer) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return 1; // 越界错误码1
        } else if (chessBoard[row][col] != 0) {
            return 2; // 已有棋子错误码2
        }

        int[] dr = { -1, -1, -1, 0, 1, 1, 1, 0 }; // 方向数组
        int[] dc = { -1, 0, 1, 1, 1, 0, -1, -1 };
        int opponent = (currentPlayer.getPlayerID() == 1) ? 2 : 1; // 对手的ID

        for (int d = 0; d < 8; d++) {
            int r = row + dr[d], c = col + dc[d];
            boolean legalLocation = false;

            while (r >= 0 && r < SIZE && c >= 0 && c < SIZE && chessBoard[r][c] == opponent) {
                legalLocation = true; // 如果有对方棋子
                r += dr[d];
                c += dc[d];
            }

            if (legalLocation && r >= 0 && r < SIZE && c >= 0 && c < SIZE
                    && chessBoard[r][c] == currentPlayer.getPlayerID()) {
                return 0; // 如果在此方向上有对方棋子，并且后面是自己的棋子，则合法
            }
        }
        return 3; // 该位置不合法错误码3
    }

    public int calculateScore(Player player) { // 计算玩家的分数
        int score = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (chessBoard[i][j] == player.getPlayerID()) {
                    score++;
                }
            }
        }
        return score;
    }

    @Override
    public void printBoard(Player player1, Player player2, Player currentPlayer) {
        clearScreen();
        System.out.print("  A B C D E F G H");
        for (int i = 0; i < SIZE; i++) {
            System.out.print("\n" + (i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                if (chessBoard[i][j] == 0) {
                    // 在合法位置上显示 '+'
                    if (isValidMove(i, j, currentPlayer) == 0) {
                        System.out.print("+ ");
                    } else {
                        System.out.print("\u00b7 "); // 空位置
                    }
                } else if (chessBoard[i][j] == 1) {
                    System.out.print(player1.getSymbol() + " ");
                } else if (chessBoard[i][j] == 2) {
                    System.out.print(player2.getSymbol() + " ");
                }
            }
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
            if (i > 4) {
                System.out.print("            "); // 添加空格以对齐棋盘列表
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
                System.out.print("         " + (j + 1) + "." + boardType);
            }
        }
    }

    public void flipPieces(int row, int col, Player currentPlayer) {
        int opponent = (currentPlayer.getPlayerID() == 1) ? 2 : 1;

        // 检查八个方向并翻转棋子
        for (int dirRow = -1; dirRow <= 1; dirRow++) {
            for (int dirCol = -1; dirCol <= 1; dirCol++) {
                if (dirRow == 0 && dirCol == 0)
                    continue;

                int i = row + dirRow;
                int j = col + dirCol;
                boolean hasOpponentBetween = false;

                // 移动到合法范围内并翻转对方棋子
                while (i >= 0 && i < SIZE && j >= 0 && j < SIZE) {
                    if (chessBoard[i][j] == opponent) {
                        hasOpponentBetween = true;
                    } else if (chessBoard[i][j] == currentPlayer.getPlayerID()) {
                        if (hasOpponentBetween) {
                            // 执行翻转
                            int k = row + dirRow;
                            int l = col + dirCol;
                            while (k != i || l != j) {
                                chessBoard[k][l] = currentPlayer.getPlayerID();
                                k += dirRow;
                                l += dirCol;
                            }
                        }
                        break;
                    } else {
                        break;
                    }
                    i += dirRow;
                    j += dirCol;
                }
            }
        }
    }

    @Override
    public boolean isGameOver(Player player1, Player player2, Player currentPlayer) { // 检查游戏是否结束
        Player opponentPlayer = (currentPlayer.getPlayerID() == 1) ? player2 : player1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isValidMove(i, j, currentPlayer) == 0 && isValidMove(i, j, opponentPlayer) == 0) { // 检查当前位置是否合法
                    return false; // 有合法位置，游戏未结束
                }
                if (chessBoard[i][j] == 0) { // 检查当前位置是否空着
                    return false; // 有空位置，游戏未结束
                }
            }
        }
        return true; // 没有合法位置，游戏结束
    }
}
