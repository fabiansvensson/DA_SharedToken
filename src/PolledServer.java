import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class PolledServer {
    public static ArrayList<Socket> sockets = new ArrayList<>();
    private static Integer token;

    private static synchronized void updateToken(Integer newToken, int port) {
        token = newToken;
        System.out.println("Got new token value from " +port+": " + token);
    }

    public static void main(String[] args) {
        token = ThreadLocalRandom.current().nextInt(0,  30);
        System.out.println("Token initial value is: " + token);
        try (ServerSocket serversocket = new ServerSocket(4321)) {
            while(true) {
                System.out.println("Waiting for new connections...");
                Socket socket = serversocket.accept();
                sockets.add(socket);
                new Thread() {
                    public void run() {
                        try {
                            int port = socket.getPort();
                            System.out.println("New socket at port: " + port);
                            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                            String string = "";
                            while (!string.equals("EXIT")) {
                                try {
                                    string = (String)ois.readObject();
                                    if(string.equals("REQ")) {
                                        Thread.sleep(1000);
                                        oos.writeObject(token);
                                        updateToken((Integer)ois.readObject(), port);
                                    }
                                } catch (InterruptedException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                            ois.close();
                            oos.close();
                            socket.close();
                            System.out.println("Closed socket connection at port: " + port);
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
