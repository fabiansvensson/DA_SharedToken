import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;


public class ServerThread extends Thread {
    private Socket socket;
    private int socketPort;
    private PrintWriter output;
    private AtomicInteger token;
    Queue<ServerThread> threadList = new LinkedList<>();

    public ServerThread(Socket socket, Queue<ServerThread> tl, AtomicInteger token) {
        this.socket = socket;
        socketPort = socket.getPort();
        threadList = tl;
        this.token = token;
    }

    @Override
    public void run() {
        try {
            //Reading the input from Client
            BufferedReader input = new BufferedReader( new InputStreamReader(socket.getInputStream()));

            //returning the output to the client : true statement is to flush the buffer otherwise
            //we have to do it manually
            output = new PrintWriter(socket.getOutputStream(),true);

            //inifite loop for server
            while(true) {
                String outputString = input.readLine();
                //if user types exit command
                if(outputString.equals("REQ")) {
                    threadList.add(this);
                } else {
                    System.out.println("Server received " + outputString + " from port " + socketPort);
                }
                //output.println("Server says " + outputString);
            }


        } catch (Exception e) {
            System.out.println("Error occured " +e.getStackTrace());
        }
    }

    public void giveToken(int token) {
        System.out.println("Gives token");
        output.println(token);
    }
}