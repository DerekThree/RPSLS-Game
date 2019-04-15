package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static sample.Main.*;

public class UI {

    private VBox theRoot;
    static TextArea messages = new TextArea();

    private TextField input = new TextField();
    private TextField portNumberField = new TextField();
    private Button startServerBtn = new Button("Start Server");
    private Button stopServerBtn = new Button("Stop Server");
    private Text enterPortText = new Text("Enter port number to start the server (default 5555):");


    public UI() {
        messages.setPrefHeight(550);
        theRoot = new VBox(20, messages, input, enterPortText, portNumberField, startServerBtn, stopServerBtn);
        theRoot.setPrefSize(600, 600);

        input.setOnAction(event -> {
            String message =  "Server: ";
            message += input.getText();
            input.clear();

            messages.appendText(message + "\n");
            server.send(message);
        });

        startServerBtn.setOnAction(e -> {
            messages.appendText("Server On. Listening to port " + Integer.toString(port) + "\n");
            server = new Server(port);
            server.start();
            startServerBtn.setDisable(true);
            stopServerBtn.setDisable(false);
        });

        stopServerBtn.setOnAction((e -> {
            server.close();
            stopServerBtn.setDisable(true);
            startServerBtn.setDisable(false);
            messages.appendText("Server off\n");
        }));

        portNumberField.setOnAction(e -> {
            port = Integer.parseInt(portNumberField.getText());
            portNumberField.clear();
            messages.appendText("Port number changed to " + Integer.toString(port) + "\n");
        });

        stopServerBtn.setDisable(true);
    }

    Scene getScene(){
        return new Scene(theRoot);
    }

    void display (String message){
        messages.appendText(message + "\n");
    }

}
