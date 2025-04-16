class Player {
    private String name;
    private char symbol;
    private int playerID;

    public Player(String name, char symbol, int playerID) {
        this.name = name;
        this.symbol = symbol;
        this.playerID = playerID;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }
    
    public int getPlayerID() {
        return playerID; 
    }
}