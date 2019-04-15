package sample;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application {


    static NetworkThread networkThread = null;
    static int port = 5555;
    static String ip = "127.0.0.1";
    static Game game = new Game();
    static UI GUI;


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Client");
        GUI = new UI();
        primaryStage.setScene(GUI.getScene());
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (networkThread != null)
            if (networkThread.socket != null)
                networkThread.socket.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
