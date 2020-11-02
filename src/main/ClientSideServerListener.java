package src.main;

public class ClientSideServerListener implements Runnable{
    private ClackClient client;
    private ClackClient checkClient;

    ClientSideServerListener(ClackClient client) { this.client = client; }

    @Override
    public void run() {
        while(!client.getConnectionStatus()) {
            client.receiveData();
            client.printData();
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println("interrupted");
            }
        }
        notifyAll();
    }

}
