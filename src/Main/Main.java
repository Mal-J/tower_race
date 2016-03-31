package Main;

public class Main {

    public static void main(String[] args) {
        //Take names input

        //Initiate game with names, default settings instance (tower width, height)
        String names[] = {"Mal", "Cody"};
        Game game = new Game(names, 5, 10);
		game.begin();
    }
}
