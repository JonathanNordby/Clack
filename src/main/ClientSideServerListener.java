package src.main;

public class ClientSideServerListener implements Runnable{

    private ClackClient client;

    ClientSideServerListener(ClackClient client) { this.client = client; }

    @Override
    public void run() {
        while(!client.getConnectionStatus()) {
            //System.out.println("We made it");
            client.receiveData();
            //System.out.println("Data received");
            client.printData();
            //  try {
            //     wait();
            // } catch (InterruptedException e) {
            //      System.err.println("interrupted");
            //   }
        }
    }
}