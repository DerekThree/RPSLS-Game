package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.ArrayList;

public class Main extends Application {


    static Server server = null;
    static int port = 5555;
    static Game game = new Game();
    static UI GUI;


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("RPSLS Server");
        GUI = new UI();
        primaryStage.setScene(GUI.getScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void stop() throws Exception {
        super.stop();
        if (server != null) server.close();
    }

}

