package com.sockets.client.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SendClientDataToServer extends Thread {

    private Socket s;
    private Scanner userInput;
    private PrintWriter sendToServer;


    public SendClientDataToServer(Socket s){
        this.s = s;
    }

    public void run(){
        try{
            boolean autoFlush = true;
            userInput = new Scanner(System.in);
            sendToServer = new PrintWriter(s.getOutputStream(), autoFlush);
            while (true) {
                String guess = userInput.next();
                sendToServer.println(guess);
                if (guess.equals("quit")) {
                    sendToServer.close();
                }
            }
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }

    }

}
