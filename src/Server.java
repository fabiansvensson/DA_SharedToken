import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

    private static AtomicInteger token;

    public static void main(String[] args) {
        Queue<ServerThread> threadList = new LinkedList<>();

        /*Runnable helloRunnable = new Runnable() {
            public void run() {
                if(threadList.size() > 0) {
                    //System.out.println("Giving token");
                    ServerThread tokenHandler = threadList.remove();
                    tokenHandler.giveToken(token);
                    token++;
                }
            }
        };*/

        //ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        //executor.scheduleAtFixedRate(helloRunnable, 1, 2, TimeUnit.SECONDS);

        //token = ThreadLocalRandom.current().nextInt(0,  30);
        System.out.println("Token initial value: " + token);

        try (ServerSocket serversocket = new ServerSocket(4321)){
            while(true) {
                System.out.println("Waiting for new connections...");
                Socket socket = serversocket.accept();
                System.out.println("New Socket accepted: " + socket.toString() + " port: " + socket.getPort());
                ServerThread serverThread = new ServerThread(socket, threadList, token);
                //starting the thread
                threadList.add(serverThread);
                serverThread.start();
            }
        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }
    }
}  