import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static ArrayList<Game> allGames = new ArrayList<>(); // 用于存储所有创建的 Game 对象

    final Player player1;
    final Player player2;
    Board board;
    String mode;
    Player currentPlayer;
    private static Scanner scanner = new Scanner(System.in); // 只创建一次 Scanner

    public Game(String mode, Player player1, Player player2) {
        this.mode = mode;
        this.player1 = player1;
        this.player2 = player2;
        currentPlayer = player1; // 默认玩家1先手
        allGames.add(this); // 将当前游戏添加到列表中
        // 选择棋盘类型
        if ("reversi".equals(mode)) {
            this.board = new ReversiBoard();
        } else if ("gomoku".equals(mode)) {
            this.board = new Gomoku();
        } else if ("peace".equals(mode)) {
            this.board = new PeaceBoard();
        }
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    public void playGame() {
        while (true) {
            board.printBoard(player1, player2, currentPlayer);
            while (true) {
                int row;
                int col;
                // 检查游戏是否结束
                if (board.isGameOver(player1, player2, currentPlayer) == true) {
                    // 游戏结束，显示结果
                    if (board instanceof ReversiBoard) {
                        int score1 = board.calculateScore(player1); // 计算玩家1的分数
                        int score2 = board.calculateScore(player2); // 计算玩家2的分数
                        System.out.print("\n玩家1的分数: " + score1);
                        System.out.print("\n玩家2的分数: " + score2);
                        if (score1 > score2) {
                            System.out.print("\n玩家1胜利！");
                        } else if (score2 > score1) {
                            System.out.print("\n玩家2胜利！");
                        } else {
                            System.out.print("\n平局！");
                        }
                    } else if (board instanceof PeaceBoard) {
                        System.out.print("\n和平模式下游戏结束！");
                    } else if (board instanceof Gomoku) {
                        if (Gomoku.checkWin(board.chessBoard, 1)) {
                            System.out.print("\n玩家" + player1.getName() + "胜利！");
                        } else if (Gomoku.checkWin(board.chessBoard, 2)) {
                            System.out.print("\n玩家" + player2.getName() + "胜利！");
                        } else {
                            System.out.print("\n平局！");
                        }
                    }
                } else if (board.hasInvalidMove(currentPlayer) == false) {
                    System.out.print("\n玩家[" + currentPlayer.getName() + "]没有合法位置可放置棋子！请输入pass跳过本回合！");
                }

                // 提示用户输入
                if (board instanceof PeaceBoard) {
                    System.out.print("\n请[" + currentPlayer.getName() + "]输入落子位置(1a)/游戏(1-" + Board.chessBoardCount
                            + ")/新游戏类型(peace/reversi/gomoku)/退出程序(q): ");
                } else if (board instanceof ReversiBoard) {
                    System.out.print("\n请[" + currentPlayer.getName() + "]输入落子位置(1a)/游戏(1-" + Board.chessBoardCount
                            + ")/新游戏类型(peace/reversi/gomoku)/放弃行棋(pass)/退出程序(q): ");
                } else if (board instanceof Gomoku) {
                    System.out.print("\n请[" + currentPlayer.getName() + "]输入落子位置(1a)/游戏(1-" + Board.chessBoardCount
                            + ")/新游戏类型(peace/reversi/gomoku)/退出程序(q): ");
                }

                String input = scanner.nextLine().toLowerCase(); // 确保输入为小写字母
                if (input.matches("^[1-8][a-h]$") && input.length() == 2
                        && board.isGameOver(player1, player2, currentPlayer) == false) { // 处理落子
                    row = input.charAt(0) - '1'; // 转换行
                    col = input.charAt(1) - 'a'; // 转换列

                    if (board.isValidMove(row, col, currentPlayer) == 0) {
                        board.chessBoard[row][col] = (currentPlayer == player1) ? 1 : 2;
                        currentPlayer = (currentPlayer == player1) ? player2 : player1; // 切换玩家
                        if (board instanceof ReversiBoard) {
                            ((ReversiBoard) board).flipPieces(row, col, currentPlayer);
                            break;
                        }else if (board instanceof Gomoku) {
                            ((Gomoku)board).setRound();
                            break;
                        }else if(board instanceof PeaceBoard){
                            break;
                        }
                    } else if (board.isValidMove(row, col, currentPlayer) == 1) {
                        System.out.print("输入错误: 越界");
                    } else if (board.isValidMove(row, col, currentPlayer) == 2) {
                        System.out.print("输入错误: 已有棋子");
                    } else if (board.isValidMove(row, col, currentPlayer) == 3) {
                        System.out.print("输入错误: 非法落子位置");
                    }
                } else if (input.matches("^[1-8][a-h]$") && input.length() == 2
                        && board.isGameOver(player1, player2, currentPlayer) == true) { // 处理落子
                    System.out.print("游戏已结束，无法继续落子！");
                } else if (input.matches("\\d+")) { // 处理棋盘切换
                    int boardId = Integer.parseInt(input);
                    if (boardId > 0 && boardId <= allGames.size()) { // 确保 boardId 在有效范围内
                        Game newGame = allGames.get(boardId - 1); // 获取新的游戏实例
                        newGame.playGame(); // 切换到新棋盘并开始游戏
                        return; // 退出当前的游戏循环
                    } else {
                        System.out.print("无效的棋盘ID，请重新输入！");
                    }
                } else if (input.equals("peace")) { // 新游戏和平模式
                    Game newGame = new Game("peace", player1, player2);
                    board.printBoard(player1, player2, currentPlayer);
                } else if (input.equals("reversi")) { // 切换到Reversi模式
                    Game newGame = new Game("reversi", player1, player2);
                    board.printBoard(player1, player2, currentPlayer);
                } else if (input.matches("gomoku")) {
                    Game newGame = new Game("gomoku", player1, player2);
                    board.printBoard(player1, player2, currentPlayer);
                } else if (input.equals("pass")) {
                    if (board instanceof ReversiBoard)// 放弃行棋
                    {
                        // 放弃行棋
                        if (board.hasInvalidMove(currentPlayer) == false) {
                            currentPlayer = (currentPlayer == player1) ? player2 : player1; // 切换玩家
                            break;
                        } else {
                            // 如果棋盘上还有合法位置，则不能放弃行棋
                            System.out.print("尚有合法位置: 无法放弃行棋");
                        }
                    } else if (board instanceof PeaceBoard) {
                        System.out.print("和平模式下无法放弃行棋！");
                    } else if (board instanceof Gomoku) {
                        System.out.print("五子棋模式下无法放弃行棋！");
                    }
                } else if (input.equals("q")) { // 退出游戏
                    System.out.print("退出游戏！");
                    System.exit(0);
                } else {
                    System.out.print("输入格式有误，请重新输入！");
                }
            }
        }
    }
}