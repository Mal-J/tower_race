package Main;

public class Main {

    public static void main(String[] args) {
        //Take names input

        //Initiate game with names, default settings instance (tower width, height)
        Game game = new Game(5, 8);
		game.begin();
    }
}
