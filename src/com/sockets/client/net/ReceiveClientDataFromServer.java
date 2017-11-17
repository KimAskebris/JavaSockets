package com.sockets.client.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiveClientDataFromServer extends Thread {


    private Socket socket;
    private BufferedReader readFromServer;


    public ReceiveClientDataFromServer(Socket socket){
        this.socket = socket;
    }

    public void run() {
        try {
            readFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;
            while ((message = readFromServer.readLine()) != null) {
                if (message.equalsIgnoreCase("quit")){
                    readFromServer.close();
                    socket.close();
                    System.exit(0);
                }
                System.out.println(message);
            }
        } catch (IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }
}