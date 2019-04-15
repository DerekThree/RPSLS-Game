package sample;

import java.io.Serializable;
import java.util.function.Consumer;

import static sample.Main.*;


// Updates a gameState based on the rules of the game
public class Game {

    int numPlayers = 0;
    private static String played[] = new String[]{"nothing", "nothing"};
    private int score[] = new int[]{0, 0};
    int winner = 0;
    boolean wantsAgain[] = new boolean[]{false, false};

    public void accept(String param, int playerNumber){

        // display who sent it and what they sent
        GUI.display("Player " + playerNumber + " sent " + param);
//        GUI.display(numPlayers + " player(s) connected to the server");

        // if player hasn't made the choice yet
        // then check if this is the choice
        if (played[playerNumber-1].equals("nothing")){
            if (param.equals("rock")) played[playerNumber-1] = "rock";
            if (param.equals("paper")) played[playerNumber-1] = "paper";
            if (param.equals("scissors")) played[playerNumber-1] = "scissors";
            if (param.equals("lizard")) played[playerNumber-1] = "lizard";
            if (param.equals("spock")) played[playerNumber-1] = "spock";
        }

        // if both players made the choice then compare them
        if (!played[0].equals("nothing") && !played[1].equals("nothing"))
            comparePlays();

        // if player wants to play again then store it
        if (param.equals("again")){
            wantsAgain[playerNumber-1] = true;
            GUI.display("\n Player " + playerNumber + " wants to play again");
        }

        // if both players want to play again then reset the score
        if (wantsAgain[0] && wantsAgain[1]){
            score[0] = 0;
            score[1] = 0;
            wantsAgain[0] = false;
            wantsAgain[1] = false;
            server.send("");
            server.send("NEW GAME");
            server.send("player 1 score: " + score[0]);
            server.send("player 2 score: " + score[1]);
            server.send("Make your choice.");
        }
    }

    void comparePlays(){

        // send info who played what
        server.send("");
        server.send("Player 1 played " + played[0]);
        server.send("Player 2 played " + played[1]);

        // check who won the hand
        switch(played[0]){
            case "rock":
                if (played[1].equals("rock")) winner = 0;
                if (played[1].equals("paper")) winner = 2;
                if (played[1].equals("scissors")) winner = 1;
                if (played[1].equals("lizard")) winner = 1;
                if (played[1].equals("spock")) winner = 2;
                break;
            case "paper":
                if (played[1].equals("rock")) winner = 1;
                if (played[1].equals("paper")) winner = 0;
                if (played[1].equals("scissors")) winner = 2;
                if (played[1].equals("lizard")) winner = 2;
                if (played[1].equals("spock")) winner = 1;
                break;
            case "scissors":
                if (played[1].equals("rock")) winner = 2;
                if (played[1].equals("paper")) winner = 1;
                if (played[1].equals("scissors")) winner = 0;
                if (played[1].equals("lizard")) winner = 1;
                if (played[1].equals("spock")) winner = 2;
                break;
            case "lizard":
                if (played[1].equals("rock")) winner = 2;
                if (played[1].equals("paper")) winner = 1;
                if (played[1].equals("scissors")) winner = 2;
                if (played[1].equals("lizard")) winner = 0;
                if (played[1].equals("spock")) winner = 1;
                break;
            case "spock":
                if (played[1].equals("rock")) winner = 1;
                if (played[1].equals("paper")) winner = 2;
                if (played[1].equals("scissors")) winner = 1;
                if (played[1].equals("lizard")) winner = 2;
                if (played[1].equals("spock")) winner = 0;
                break;
        }

        // send info who won the hand and update score
        switch(winner) {
            case 0:
                server.send("It's a tie. No points");
                break;
            case 1:
                server.send("Player 1 gets a point.");
                score[0]++;
                break;
            case 2:
                server.send("Player 2 gets a point.");
                score[1]++;
                break;

        }

        //send score
        GUI.display("Player 1 score: " + score[0]);
        GUI.display("Player 2 score: " + score[1]);
        GUI.display("");
        server.send("player 1 score: " + score[0]);
        server.send("player 2 score: " + score[1]);

        // reset who played what
        played[0] = "nothing";
        played[1] = "nothing";

        // check if someone won the game
        if (score[0] >= 3) {
            GUI.display("PLAYER 1 WINS THE GAME");
            server.send("PLAYER 1 WINS THE GAME\n");
            server.send("Play again?");
            score[0] = 0;
            score[1] = 0;
        }
        else if (score[1] >= 3) {
            GUI.display("PLAYER 2 WINS THE GAME");
            server.send("PLAYER 2 WINS THE GAME");
            server.send("Play again?");
            score[0] = 0;
            score[1] = 0;
        }
        else{
            server.send("Make your choice.");
        }


    }

}

