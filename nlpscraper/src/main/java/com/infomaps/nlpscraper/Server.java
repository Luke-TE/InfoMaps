package server;

import edu.stanford.nlp.simple.Sentence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class Server {

    public static void main(String[] args) {
        int port = 2323;
        ServerSocket socket;
        Parser parser = new Parser();

        Sentence sent = new Sentence("This is a sentence.");
        List<String> nertags = sent.nerTags();
        String firstPOStag = sent.posTag(0);

//        try {
//
//            socket = new ServerSocket(port);
//            Socket clientSocket = socket.accept();
//
//            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//
//
//            String inputLine;
//
//            while ((inputLine = in.readLine()) != null) {
//                System.out.println("Message received from client: " + inputLine);
//                System.out.println("Returning message...");
//                out.println(parser.parse(inputLine));
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException("Ded");
//        }
        parser.parse("this");
    }
}
