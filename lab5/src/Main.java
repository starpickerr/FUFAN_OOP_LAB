import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //创建玩家信息
        System.out.print("请输入玩家一的名字: ");
        Player player1 = new Player(scanner.nextLine(), '○', 1);
        System.out.print("请输入玩家二的名字: ");
        Player player2 = new Player(scanner.nextLine(), '●', 2);
        
        //初始化游戏
        Game game1 = new Game("peace", player1, player2);
        Game game2 = new Game("reversi", player1, player2);
        Game currentGame = game1; // 默认游戏模式为Reversi

        currentGame.playGame(); // 开始游戏
        scanner.close(); // 关闭扫描器
    }
}
