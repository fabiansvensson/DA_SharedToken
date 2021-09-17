import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;

public class PolledServer {
    public static ArrayList<Socket> sockets = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serversocket = new ServerSocket(4321)) {
            while(true) {
                System.out.println("Waiting for new connections...");
                Socket socket = serversocket.accept();
                sockets.add(socket);
                new Thread() {
                    public void run() {
                        try {
                            Socket sckt = socket;
                            System.out.println("New socket at port: " + sckt.getPort());
                            ObjectInputStream ois = new ObjectInputStream(sckt.getInputStream());
                            ObjectOutputStream oos = new ObjectOutputStream(sckt.getOutputStream());
                            System.out.println("test");
                            while (true) {
                                try {
                                    System.out.println("Waiting to read...");
                                    String string = (String)ois.readObject();
                                    if(string.equals("REQ")) {
                                        oos.writeObject("Cool");
                                    }
                                    Thread.sleep(1000);
                                } catch (InterruptedException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
