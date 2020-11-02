package main;

public class ClientSideServerListener implements Runnable{
    private ClackClient client;

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
        notify();
    }
}