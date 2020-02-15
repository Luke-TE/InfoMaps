package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class FrontEndClient {

    public static void main(String[] args) {

        int port = 2323;
        String hostname = "127.0.0.1";
        String message = "subject";

        try {
            Socket socket = new Socket(hostname, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            System.out.println("Sending message to server");
            out.println(message);

            System.out.println("Waiting for response...");
            System.out.println("Received response: " + in.readLine());

        } catch (IOException e) {
            throw new RuntimeException("Cry");
        }

    }
}
