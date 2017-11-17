package com.sockets.client.controller;

import com.sockets.client.net.ClientSocket;

public class Controller {

    private ClientSocket cs;

    public Controller(ClientSocket cs){
        this.cs = cs;
    }

    public void startGame(){
        cs.connect();
    }

    public void guess(String guess){

    }


}
