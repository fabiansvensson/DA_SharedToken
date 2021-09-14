import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) {
        ArrayList<ServerThread> threadList = new ArrayList<>();
        try (ServerSocket serversocket = new ServerSocket(4321)){
            while(true) {
                System.out.println("Waiting for new connections...");
                Socket socket = serversocket.accept();
                System.out.println("New Socket accepted: " + socket.toString());
                ServerThread serverThread = new ServerThread(socket, threadList);
                //starting the thread
                threadList.add(serverThread);
                serverThread.start();

                //get all the list of currently running thread

            }
        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }
    }
}  