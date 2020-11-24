package main;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * @author Stephen Miner <br> Jonathan Nordby
 */
public class ClientSideServerListener {

    private ClackClient client;

    /**
     * Creates a ClientSideServerListener object
     * @param client the Client that the object is to be bound to
     */
    ClientSideServerListener(ClackClient client) { this.client = client; }

//    @Override
//    public synchronized void run() {
//
//
//        while(!client.getConnectionStatus()) {
//            if (!client.hasGuiInit) {
////                System.out.println("GUI not init");
//            } else {
//                System.out.println("GUI IS INIT");
//                client.receiveData();
//
//            }
//        }
//    }

    Service listenerService = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Object call() throws Exception {
                    while (!client.getConnectionStatus()) {
                        client.receiveData();
                    }

                    return null;
                }
            };
        }
    };
}