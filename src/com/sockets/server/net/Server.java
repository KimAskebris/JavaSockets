package com.sockets.server.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket server;
    private Socket clientSocket;
    private static final int PORT = 6000;

    public void runServer() throws IOException {
        server = new ServerSocket(PORT);
        System.out.println("Running on port: " + PORT);
        while (true) {
            clientSocket = server.accept();
            System.out.println("Connected to " + clientSocket.getInetAddress().getHostName());
            new Game(clientSocket).start();
        }
    }
}
