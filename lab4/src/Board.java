import java.io.IOException;
import java.util.ArrayList;

public class Board {
    static ArrayList<Board> allBoards = new ArrayList<>(); // 用于存储所有创建的 Game 对象

    final static int SIZE = 8;
    int chessBoard[][];
    static int chessBoardCount = 0; // 记录棋盘的数量，初始值为0，表示当前棋盘为第一个棋盘;
    int boardID = 0; // 记录当前棋盘的ID，初始值为0，表示当前棋盘为第一个棋盘;

    public Board() {
        chessBoard = new int[SIZE][SIZE];
        initBoard();
        boardID = ++chessBoardCount; // 每次创建棋盘时，棋盘数量加1，并设置当前棋盘的ID为棋盘数量
        allBoards.add(this); // 将当前棋盘添加到列表中
    }

    public void initBoard() {
        chessBoard[3][3] = 2;
        chessBoard[3][4] = 1;
        chessBoard[4][3] = 1;
        chessBoard[4][4] = 2;
    }

    public int calculateScore(Player player){
        return 0; // 计算玩家的分数
    };

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
            System.out.print("   棋盘 " + boardID + "   Game List:");
        } 
        // 打印玩家1的信息（第4行）
        if (i == 3) {
            System.out.print("   " + player1.getName() + " " + player1.getSymbol());
        }
        // 打印玩家2的信息（第5行）
        if (i == 4) {
            System.out.print("   " + player2.getName() + " " + player2.getSymbol());
        }
        if( i > 4){
            System.out.print("         ");// 添加空格以对齐棋盘列表
        }
        // 打印棋盘列表
        int j = i - 3; 
        if(j < allBoards.size() && j >= 0) {
            Board board = allBoards.get(j);
            String boardType = (board instanceof ReversiBoard) ? "reversi" : "peace";
            System.out.print("   " + (j + 1) + "." + boardType);
        }
    }
}

    public void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public int isValidMove(int row, int col, Player currentPlayer) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return 1; // 越界错误码1
        } else if (chessBoard[row][col] != 0) {
            return 2; // 已有棋子错误码2
        }
        return 0; // 合法落子返回值为0
    }

    public boolean isGameOver(Player player1, Player player2, Player currentPlayer) { // 检查棋盘上是否还有可放置棋子的空位
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (chessBoard[i][j] == 0) { // 检查当前位置是否空着
                    return false; // 有空位置，游戏未结束
                }
            }
        }
        return true; // 没有合法位置，游戏结束
    }

    public boolean hasInvalidMove(Player currentPlayer) { // 检查当前玩家是否有合法的移动
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isValidMove(i, j, currentPlayer) == 0) { // 检查当前位置是否有合法的移动
                    return true; // 有合法移动，返回 false
                } 
            }  
        }
        return false; // 没有合法移动，返回 true
    }
}