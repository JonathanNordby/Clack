package main;

import data.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.util.Vector;


public class Controller {

    @FXML private ScrollPane messageHistoryPane;
    @FXML private TextArea userArea;
    @FXML private TextField sendMessage;
    @FXML private Button messageButton;
    @FXML private Button fileButton;
    @FXML private VBox messageHistory;
    @FXML private GridPane root;
    @FXML private Parent parent;

    private ClackClient client;
    private boolean messageServiceStarted = false;
    private boolean fileServiceStarted = false;
    private boolean updateServiceStarted = false;
    private Service messageService;
    private Service fileService;
    private Service updateService;



    public void messageButtonClicked(ActionEvent event) {
        if (!messageServiceStarted) {
            messageService.start();
            System.out.println("Message Service started");
            messageServiceStarted = true;
        } else {
            messageService.restart();
        }

        event.consume();
    }

    public void fileButtonClicked(ActionEvent event) {
        if (!fileServiceStarted) {
            fileService.start();
            System.out.println("File Service started");
            fileServiceStarted = true;
        } else {
            fileService.restart();
        }
        event.consume();
    }

    public synchronized void initialize() {

        parent = root.getParent();


        messageService = new Service() {
            @Override
            protected Task<Object> createTask() {
                return new Task<Object>() {
                    @Override protected Object call() throws Exception {
                        client.sendData(client.createMessage(sendMessage.getText(), ClackData.CONSTANT_SENDMESSAGE));
                        sendMessage.clear();
                        return null;
                    }
                };
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

        updateService = new Service() {

            @Override
            protected Task createTask() {
                Task<Vector<ClackData>> task = new Task<Vector<ClackData>>() {
                    @Override
                    protected Vector<ClackData> call() throws Exception {
                        System.out.println("History: " + client.getHistory());
                        messageHistory.getChildren().removeAll();
                        for (ClackData message : client.getHistory()) {
                            Node messageToDisplay;

                            if (message instanceof MessageClackData || message instanceof FileClackData) {
                                if (message.getType() != ClackData.CONSTANT_NEWUSER) {
                                    messageToDisplay = new TextArea(client.getData(message));
                                    ((TextArea) messageToDisplay).setEditable(false);
                                } else if (userArea == null) {
                                } else {
                                    userArea.setText(userArea.getText().equals("Debug: No Users") ? message.getUserName() : userArea.getText() + message.getUserName());
                                }
                            } else if (message instanceof ImageClackData) {
                                messageToDisplay = new ImageView(((ImageClackData) message).getImage());
                                messageHistory.getChildren().add(messageToDisplay);
                            } else if (message instanceof VideoClackData) {
                                MediaPlayer player = new MediaPlayer(((VideoClackData) message).getVideo());
                                messageToDisplay = new MediaView(player);
                                messageToDisplay.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                                    player.play();
                                    event.consume();
                                });
                            } else {
                                System.err.println("Invalid Type");
                            }
                        }
                        return client.getHistory();
                    }
                };
                return null;
            }
        };

    }

    public void updateMessageList() {
        if (!updateServiceStarted) {
            System.out.println(updateService.getState());
            System.out.println(Thread.currentThread());
            updateService.start();
            System.out.println("Update Service started");
            updateServiceStarted = true;
        } else {
            updateService.restart();
        }

    }

    public void addClient(ClackClient client) {
        this.client = client;
    }
}
