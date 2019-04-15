package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static sample.Main.GUI;
import static sample.Main.game;

public class Server extends Thread{

    class PlayerThread extends Thread {

        Socket socket;
        ObjectOutputStream output;
        ObjectInputStream input;
        private int playerNumber;

        public PlayerThread(Socket newSocket) {
            socket = newSocket;

            try {
                output = new ObjectOutputStream(newSocket.getOutputStream());
                input = new ObjectInputStream(newSocket.getInputStream());
                game.numPlayers++;
                GUI.display(game.numPlayers + " player(s) connected");
            }
            catch (IOException e) {
                System.out.println("Exception 1");
            }
        }

        @Override
        public void run() {
            super.run();
            try {
                while(true) {
                    game.accept((String) input.readObject(), playerNumber);
                }
            }
            catch(Exception e) {
                System.out.println("Exception 2");
                try {
                    socket.close();
                    GUI.display("\nPlayer " + playerNumber + " closed connection");
                    game.numPlayers--;
                    GUI.display(game.numPlayers + " player(s) connected");
                    player[playerNumber-1] = null;
                }
                catch (IOException ioe) {
                    System.out.println("Exception 2.1");
                }
            }
        }
    }

    private int port;
    private ServerSocket ssocket;
    private PlayerThread player[] = new PlayerThread[2];

    public Server (int portNumber){
        port = portNumber;
    }

    public void run () {
        for (int i=0; i<2; i++) player[i] = null;
        try {
            ssocket = new ServerSocket(port);


                // connecting player 1 if null
                if (player[0] == null) {
                    player[0] = new PlayerThread(ssocket.accept());
                    player[0].playerNumber = 1;
                    player[0].start();
                    send("Welcome player 1. Let's wait for the other player...\n");
                    GUI.display("Player 1 connected");
                }
                // connecting player 2 if null
                if (player[1] == null) {
                    player[1] = new PlayerThread(ssocket.accept());
                    player[1].playerNumber = 2;
                    player[1].start();
                    send("Player 2 Connected. Let's start the game.");
                    send("Make your choice.");
                    GUI.display("Player 2 connected\n");
                }

        }
        catch (IOException e) {
            System.out.println("Exception 3");
        }
    }

    void send(String data) {
        try {
            for (PlayerThread p : player)
                if (p != null)
                    if (p.socket != null)
                        p.output.writeObject(data);
        } catch (IOException e) {
            System.out.println("Exception 4");
        }
    }

    void close(){
        try {
            for (PlayerThread p : player) if (p != null) p.socket.close();
            if (ssocket != null) ssocket.close();
        }
        catch (IOException e){
            System.out.println("Exception 5");
        }
    }

}
