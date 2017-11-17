package com.sockets.client.start;

import com.sockets.client.controller.Controller;
import com.sockets.client.net.ClientSocket;
import com.sockets.client.view.View;


/**
 * Starts the client side for the hangman game.
 */
public class Main {

    public static void main(String[] args) {
        ClientSocket cs = new ClientSocket();
        Controller cont = new Controller(cs);
        new View(cont);
    }
}
