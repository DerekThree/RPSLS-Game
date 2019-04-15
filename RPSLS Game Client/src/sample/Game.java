package sample;

import static sample.Main.GUI;

// Updates a gameState based on the rules of the game
public class Game {

    public void accept(String param){

        if (param.equals("Make your choice."))
            GUI.showChoice();

        if (param.equals("Play again?"));
            GUI.showAgain();

        GUI.display(param);

    }


}

