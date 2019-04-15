package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static sample.Main.GUI;

public class NetworkThread extends  Thread{

    Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String ip;
    private int port;

    public NetworkThread(String ipAddress, int portNumber){
        ip = ipAddress;
        port = portNumber;
    }

    public void run (){
        try{
            socket = new Socket(ip, port);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            GUI.connectBtn.setDisable(true);
            GUI.display("Connected to " + ip + ":" + Integer.toString(port) + "\n");

            while(true){
                String j = (String)input.readObject();
                Main.game.accept(j);
            }
        }
        catch(Exception e){
            GUI.connectBtn.setDisable(false);
            GUI.display("Connection failed\n");
            System.out.println("Exception 1");
        }
    }

    void send(String data){
        try{
            output.writeObject(data);
        }
        catch(IOException e){
            System.out.println("Exception 2");
        }
    }
}
