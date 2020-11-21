package main;

import data.ClackData;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.ClackClient;

import java.util.Vector;


public class ClackGUI extends Application {

    private ClackClient client;
    private Vector<ClackData> history;
    private TextArea messageArea;


    public ClackGUI(ClackClient client) {
        this.client = client;
    }

    public void initialize(String[] args) {
        launch(args);
    }

    public void updateMessageList(ClackData message) {
        history.add(message);
        String messageHistory = "";
        for (ClackData m : history) {
            messageHistory += m.toString() + '\n';
        }
        messageArea.setText(messageHistory);
    }

    @Override
    public void start(Stage primaryStage){

        System.out.println("Generating GUI");

        Group root = new Group();

        messageArea = new TextArea("There are no messages yet...");

        TextArea userArea = new TextArea("Debug: No Users");

        TextField sendMessage = new TextField();
        sendMessage.setPromptText("Send Message...");

        Button messageButton = new Button("Send Message");

        Button fileButton = new Button("Send File");

        root.getChildren().add(messageArea);
        root.getChildren().add(userArea);
        root.getChildren().add(sendMessage);
        root.getChildren().add(messageButton);
        root.getChildren().add(fileButton);
        messageArea.setPrefSize(500,500);
        userArea.setPrefSize(100,500);
        sendMessage.setPrefSize(400,100);
        messageButton.setPrefSize(100,100);
        fileButton.setPrefSize(100,100);
        messageArea.setLayoutX(0);
        messageArea.setLayoutY(0);
        userArea.setLayoutX(500);
        userArea.setLayoutY(0);
        messageButton.setLayoutX(400);
        messageButton.setLayoutY(500);
        fileButton.setLayoutX(500);
        fileButton.setLayoutY(500);
        sendMessage.setLayoutX(0);
        sendMessage.setLayoutY(500);

        messageButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                client.sendData(client.createMessage(sendMessage.getText(), ClackData.CONSTANT_SENDMESSAGE));
                sendMessage.clear();

            }
        });

        fileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                client.sendData(client.createMessage(sendMessage.getText(), ClackData.CONSTANT_SENDFILE));
                sendMessage.clear();
            }
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                client.sendData(client.createMessage("DONE", ClackData.CONSTANT_LOGOUT));
            }
        });

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Clack");
        primaryStage.setScene(scene);
        primaryStage.show();

//        Parent root = FXMLLoader.load(getClass().getResource("ClackGUI.fxml"));
//        Scene scene =  new Scene(root, 600, 600);
//        primaryStage.setTitle("Clack");
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }


}