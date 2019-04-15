package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

import static sample.Main.*;

public class UI {

    private VBox theRoot;

    private TextArea messages = new TextArea();
    private Button rockBtn = new Button ();
    private Button paperBtn = new Button ();
    private Button scissorsBtn = new Button ();
    private Button lizardBtn = new Button ();
    private Button spockBtn = new Button ();
    private Button againBtn = new Button("Play Again");
    Button connectBtn = new Button("Connect");

    private TextField input = new TextField();
    private TextField portNumberField = new TextField();
    private TextField ipAddressField = new TextField();


    UI() {
        // set images for buttons
        rockBtn.setGraphic(new ImageView(new Image("rock.png")));
        paperBtn.setGraphic(new ImageView(new Image("paper.png")));
        scissorsBtn.setGraphic(new ImageView(new Image("scissors.png")));
        lizardBtn.setGraphic(new ImageView(new Image("lizard.png")));
        spockBtn.setGraphic(new ImageView(new Image("spock.png")));

        // disable some buttons
        hideChoice();

        Text enterIPText = new Text("Enter IP address (default 127.0.0.1):");
        Text enterPortText = new Text("Enter port number (default 5555):");
        HBox btnHBox = new HBox(rockBtn, paperBtn, scissorsBtn, lizardBtn, spockBtn);
        Button quitBtn = new Button("Quit");
        HBox againQuitHBox = new HBox(againBtn, quitBtn);
        theRoot = new VBox(20, messages, input, enterIPText, ipAddressField, enterPortText, portNumberField, connectBtn, btnHBox, againQuitHBox);
        theRoot.setPrefSize(600, 600);
        messages.setPrefHeight(550);

        // event handlers
        input.setOnAction(event -> {
            String message =  "Client2: ";
            message += input.getText();
            input.clear();

            messages.appendText(message + "\n");

            networkThread.send(message);
        });
        connectBtn.setOnAction(e -> {

            networkThread  = new NetworkThread(ip, port);
            networkThread.start();
        });
        ipAddressField.setOnAction(e -> {
            ip = ipAddressField.getText();
            ipAddressField.clear();
            messages.appendText("IP address changed to " + ip + "\n");
        });
        portNumberField.setOnAction(e -> {
            port = Integer.parseInt(portNumberField.getText());
            portNumberField.clear();
            messages.appendText("Port number changed to " + Integer.toString(port) + "\n");
        });
        rockBtn.setOnAction(e -> {
            networkThread.send("rock");
            hideChoice();
        });
        paperBtn.setOnAction(e -> {
            networkThread.send("paper");
            hideChoice();
        });
        scissorsBtn.setOnAction(e -> {
            networkThread.send("scissors");
            hideChoice();
        });
        lizardBtn.setOnAction(e -> {
            networkThread.send("lizard");
            hideChoice();
        });
        spockBtn.setOnAction(e -> {
            networkThread.send("spock");
            hideChoice();
        });
        againBtn.setOnAction(e -> {
            networkThread.send("again");
            againBtn.setDisable(true);
        });
        quitBtn.setOnAction(e -> {
            if (networkThread != null)
                if (networkThread.socket != null)
                    try {networkThread.socket.close();}
                    catch (IOException ex){}
        });
    }

    Scene getScene(){
        return new Scene(theRoot);
    }

    // unlocks the choice buttons
    void showChoice(){
        rockBtn.setDisable(false);
        paperBtn.setDisable(false);
        scissorsBtn.setDisable(false);
        lizardBtn.setDisable(false);
        spockBtn.setDisable(false);
    }

    // fades out the choice buttons
    void hideChoice(){
        rockBtn.setDisable(true);
        paperBtn.setDisable(true);
        scissorsBtn.setDisable(true);
        lizardBtn.setDisable(true);
        spockBtn.setDisable(true);
        againBtn.setDisable(true);
    }

    // unlock the "play again" button
    void showAgain(){
        againBtn.setDisable(false);
    }

    void display (String message){
        messages.appendText(message + "\n");
    }
}
