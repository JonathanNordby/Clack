package main;

/**
 * @author Stephen Miner <br> Jonathan Nordby
 */
public class ClientSideServerListener implements Runnable {

    private ClackClient client;
    private boolean firstRun = true;

    /**
     * Creates a ClientSideServerListener object
     * @param client the Client that the object is to be bound to
     */
    ClientSideServerListener(ClackClient client) { this.client = client; }

    @Override
    public synchronized void run() {


        while(!client.getConnectionStatus()) {
            if (!client.hasGuiInit) {
                try {
                    System.out.println("GUI not init");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                client.receiveData();

            }
        }
    }
}