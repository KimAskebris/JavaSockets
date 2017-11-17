package com.sockets.server.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Word {

    private static final String projectDir = System.getProperty("user.dir");
    private static final String gameType = "countries";
    private static final String path = projectDir + "\\games\\" + gameType + ".txt";
    private List<String> words;

    public Word() {

    }

    public String getWord(){
        getAllWords();
        Random randomGenerator = new Random();
        return words.get(randomGenerator.nextInt(words.size()));
    }


    public String dash(String word){
        String dashedWord = word.replaceAll(".", "_");
        return dashedWord;
    }

    private void getAllWords() {
        words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            System.out.println("Caught IOException " + e.getMessage() );
        }
    }

}
