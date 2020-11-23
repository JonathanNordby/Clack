package main;

import data.ClackData;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;



public class Controller {

    @FXML private TextArea messageHistoryArea;
    @FXML private TextArea userArea;
    @FXML private TextField sendMessage;
    @FXML private Button messageButton;
    @FXML private Button fileButton;

    private ClackClient client;
    private boolean messageServiceStarted = false;
    private boolean fileServiceStarted = false;
    private Service messageService;
    private Service fileService;



    public void messageButtonClicked(ActionEvent event) {
        if (!messageServiceStarted) {
            messageService.start();
            messageServiceStarted = true;
        } else {
            messageService.restart();
        }

        event.consume();
    }

    public void fileButtonClicked(ActionEvent event) {
        if (!fileServiceStarted) {
            fileService.start();
            fileServiceStarted = true;
        } else {
            fileService.restart();
        }
        event.consume();
    }

    public void initialize(ClackClient client) {

        this.client = client;

        messageService = new Service() {
            @Override
            protected Task<Object> createTask() {
                Task<Object> task = new Task<Object>() {
                    @Override protected Object call() throws Exception {
                        client.sendData(client.createMessage(sendMessage.getText(), ClackData.CONSTANT_SENDMESSAGE));
                        sendMessage.clear();
                        return null;
                    }
                };
                return task;
            }
        };

        fileService = new Service() {
            @Override
            protected Task createTask() {
                Task<Object> task = new Task<Object>() {
                    @Override protected Object call() throws Exception {
                        client.sendData(client.createMessage(sendMessage.getText(), ClackData.CONSTANT_SENDFILE));
                        sendMessage.clear();
                        return null;
                    }
                };
                return task;
            }
        };
    }

    public void addClient(ClackClient client) {
        this.client = client;
    }
}
