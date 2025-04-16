import java.util.Scanner;

class Game {
    private Board[] board;
    private Board currentBoard;
    private Player player1;
    private Player player2;
    private Scanner scanner;
    private int boardNumber;

    public Game() {
        board = new Board[3];
        for (int i = 0; i < 3; i++) {
            board[i] = new Board();
        }
        scanner = new Scanner(System.in);
        boardNumber = 0;
    }

    public void initialize() {
        System.out.print("请输入玩家一的姓名:");
        player1 = new Player(scanner.nextLine(), '\u25cf');
        System.out.print("请输入玩家二的姓名:");
        player2 = new Player(scanner.nextLine(), '\u25cb');
    }

    public void play() {
        while (true) {
            currentBoard = board[boardNumber];
            currentBoard.display(player1, player2, boardNumber);

            if (currentBoard.round == 60) {
                System.out.println("\n此棋盘已满!\n此局游戏结束!");
                while(true) {
                    System.out.print("请输入1-3的数字切换棋盘:");
                    String input = scanner.nextLine();
                    if(input.matches("[1-3]")) {
                        boardNumber = Integer.parseInt(input) - 1;
                        break; 
                    }else{
                        System.out.println("输入格式错误，请确保是'数字(1-3)'的格式！");
                    }
                }
            } else if (currentBoard.round != 60) {
                while (true) {
                    Player currentPlayer = (currentBoard.round % 2 == 0) ? player1 : player2;
                    int result = makeMove(currentPlayer);
                    if (result == 1) {
                        currentBoard.round++;
                        break;
                    } else if (result == -1) {
                        break;
                    }
                }
            }
        if(gameover()) {
            System.out.println("\n所有棋盘均满\n游戏结束!");
            break; 
        }
    }    
    }

    private int makeMove(Player player) {
        System.out.print("\n请玩家[" + player.getName() + "]输入'数字(1-8)+字母(小写a-h)'落子或'数字(1-3)'的格式切换棋盘:");
        String input = scanner.nextLine();
        if (input.length() == 2) {
            if (input.matches("^[1-8][a-h]$")) {
                int x = input.charAt(0) - '1';
                int y = input.charAt(1) - 'a';

                if (currentBoard.isValidMove(x, y)) {
                    currentBoard.makeMove(x, y, (player == player1) ? 1 : 2);
                    return 1;
                } else {
                    System.out.print("落子位置有误，请重新输入");
                }
            } else {
                System.out.print("输入格式错误，请确保是'数字(1-8)+字母(小写a-h)'或'数字(1-3)'的格式！");
            }
        } else if (input.length() == 1) {
            if (input.matches("[1-3]")) {
                boardNumber = Integer.parseInt(input) - 1;
                return -1;
            } else {
                System.out.print("输入格式错误，请确保是'数字(1-8)+字母(小写a-h)'或'数字1-3'的格式！");
            }
        } else {
            System.out.print("输入格式错误，请确保是'数字(1-8)+字母(小写a-h)'或'数字1-3'的格式！");
        }
        return 0;

    }

    public boolean gameover() {
        for(int i = 0; i < 3; i++) {
            if(board[i].round != 60) {
                return false;
            } 
        }
        return true;
    }

    public void end() {
        scanner.close();
    }
}