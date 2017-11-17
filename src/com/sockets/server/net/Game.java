package com.sockets.server.net;

import com.sockets.server.model.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class is runnable and consists of a run method which runs the hangman game on the server side.
 * The constructor accepts a Socket from the client and run the thread on that socket.
 */
public class Game extends Thread {

    private Socket clientSocket;
    private BufferedReader receiveFromClient;
    private PrintWriter sendToClient;
    private int score = 0;
    private Word word;

    public Game(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        setupStreams();
        play();
    }

    private void setupStreams(){
        try{
            boolean autoFlush = true;
            sendToClient = new PrintWriter(clientSocket.getOutputStream(), autoFlush);
            receiveFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        }
    }

    private void play() {
        try {
            String hangmanWord = hangmanWord().toUpperCase();
            System.out.println(hangmanWord);
            int numberOfguesses = hangmanWord.length();
            String dashedWord = word.dash(hangmanWord);
            StringBuilder currentWord = new StringBuilder(dashedWord);
            sendToClient.println("------- WELCOME TO HANGMAN -------");
            sendToClient.println("  ------- Guess the word -------");
            sendToClient.println("Remaining guesses: " + numberOfguesses);
            sendToClient.println(dashedWord);
            while (true) {
                String guess = receiveFromClient.readLine().toUpperCase();
                if (guess.equalsIgnoreCase("quit")) {
                    quit();
                }
                if (guess.length() == 1) {
                    if (hangmanWord.contains(guess)) {                               //Guessed for a letter
                        currentWord = guess(hangmanWord, currentWord, guess);
                    } else {
                        sendToClient.println("Letter " + guess + " is not in the word");
                        numberOfguesses--;
                    }
                    if (hangmanWord.equals(currentWord.toString())) {
                        sendToClient.println("You are correct");
                        score++;
                        playAgain();
                    }
                } else if (guess.length() > 1) {
                    if (guess(hangmanWord, guess)) {                                 //Guessed for a word
                        sendToClient.println("You are correct");
                        score++;
                        playAgain();
                    } else
                        sendToClient.println("Wrong answer, try again");
                        numberOfguesses--;
                } else {
                    sendToClient.println("Guess a word or a letter");
                }
                if (numberOfguesses == 0) {
                    sendToClient.println("You lost =(");
                    sendToClient.println("The correct answer was " + hangmanWord);
                    score--;
                    playAgain();
                }
                sendToClient.println("Remaining guesses: " + numberOfguesses);
                sendToClient.println(currentWord.toString());
            }
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }

    private void playAgain() throws IOException {
        sendToClient.println("Your score: " + score);
        sendToClient.println("Do you want to play again? Yes/No");
        String answer = receiveFromClient.readLine();
        switch(answer.toLowerCase()) {
            case "yes" : play();
            case "y" : play();
            case "no" : quit();
            case "n" : quit();
            default:
                sendToClient.println("Type yes or no");
                playAgain();
        }
    }

    private void quit() throws IOException{
        sendToClient.println("Thanks for playing");
        sendToClient.println("quit");
        System.out.println("Connection to " + clientSocket.getInetAddress().getHostName() + " closed");
        sendToClient.close();
        receiveFromClient.close();
        clientSocket.close();
    }

    private String hangmanWord(){
        word = new Word();
        return word.getWord();
    }

    private StringBuilder guess(String hangmanWord, StringBuilder currentWord, String guess) {

        char[] myCharArray = currentWord.toString().toCharArray();
        char letter = guess.charAt(0);
        for (int i = 0; i < hangmanWord.length(); i++) {
            if (hangmanWord.charAt(i) == letter) {
                myCharArray[i] = letter;
            }
        }
        return new StringBuilder(String.valueOf(myCharArray));
    }

    private boolean guess(String hangmanWord, String guess) {
        return hangmanWord.equals(guess);
    }
}
