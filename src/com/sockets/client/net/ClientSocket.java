package com.sockets.client.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientSocket {

    private static final int TIMEOUT_ONE_MINUTE = 60000;
    private static final int TIMEOUT_ONE_HOUR = 3600000;
    private static final int PORT = 6000;
    private static final String WEB_HOST = "83.209.199.88";
    private static final String LOCAL_HOST = "127.0.0.1";
    private Socket socket;


    public void connect(){
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(LOCAL_HOST, PORT), TIMEOUT_ONE_MINUTE);
            socket.setSoTimeout(TIMEOUT_ONE_HOUR);
            sendUserInputToServer();
            receiveServerInputAndSendToClient();
        } catch (IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
        }

    }

   private void sendUserInputToServer(){
        new SendClientDataToServer(socket).start();
    }

   private void receiveServerInputAndSendToClient(){
        new ReceiveClientDataFromServer(socket).start();
    }




}
