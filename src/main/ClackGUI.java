package main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class ClackGUI extends Application {

    public ClackGUI(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Generating GUI");
        BorderPane root = new BorderPane();

        TextArea messageArea = new TextArea("There are no messages yet...");

        TextArea userArea = new TextArea("Debug: No Users");

        TextField sendMessage = new TextField();
        sendMessage.setPromptText("Send Message...");

        Button messageButton = new Button("Send Message");

        Button fileButton = new Button("Send File");

        VBox buttonBox = new VBox();
        buttonBox.getChildren().add(messageButton);
        buttonBox.getChildren().add(fileButton);

        HBox bottomBar = new HBox();
        bottomBar.getChildren().add(sendMessage);
        bottomBar.getChildren().add(buttonBox);

        root.getChildren().add(bottomBar);
        root.setAlignment(bottomBar, Pos.BOTTOM_CENTER);

        root.getChildren().add(messageArea);
        root.getChildren().add(userArea);
        root.setAlignment(userArea, Pos.CENTER_RIGHT);

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