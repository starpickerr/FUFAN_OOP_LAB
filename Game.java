import java.util.Scanner;

class Game {
    private Board board;
    private Player player1;
    private Player player2;
    private Scanner scanner;
    private int round;

    public Game() {
        board = new Board();
        scanner = new Scanner(System.in);
        round = 0;
    }

    public void initialize() {
        System.out.print("请输入玩家一的姓名:");
        player1 = new Player(scanner.nextLine(), '\u25cf');
        System.out.print("请输入玩家二的姓名:");
        player2 = new Player(scanner.nextLine(), '\u25cb');
    }

    public void play() {
        while (true) {
            board.display(player1, player2);

            if (round == 60) {
                System.out.println("\n棋盘已满!\n游戏结束!");
                break;
            }

            Player currentPlayer = (round % 2 == 0) ? player1 : player2;
            if (makeMove(currentPlayer)) {
                round++;
            }
        }
    }

    private boolean makeMove(Player player) {
        while (true) {
            System.out.print("\n请玩家[" + player.getName() + "]输入落子位置:");
            String input = scanner.nextLine();

            if (input.matches("^\\d[a-z]$")) {
                int x = input.charAt(0) - 49;
                int y = input.charAt(1) - 97;

                if (board.isValidMove(x, y)) {
                    board.makeMove(x, y, (player == player1) ? 1 : 2);
                    return true;
                } else {
                    System.out.print("落子位置有误，请重新输入");
                }
            } else {
                System.out.print("输入格式错误，请确保是'数字+字母(小写)'的格式！");
            }
        }
    }

    public void end() {
        scanner.close();
    }
}