package src.main;

/**
 * @author Sephen Miner <br> Jonathan Nordby
 */
public class ClientSideServerListener implements Runnable {

    private ClackClient client;

    /**
     * Creates a ClientSideServerListener object
     * @param client the Client that the object is to be bound to
     */
    ClientSideServerListener(ClackClient client) { this.client = client; }

    @Override
    public void run() {
        while(!client.getConnectionStatus()) {
            client.receiveData();
            client.printData();
        }
    }
}