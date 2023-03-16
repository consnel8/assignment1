package com.spamdetector.util;
import java.io.*;
import java.util.*;

public class Helper {

    //This helper function will take in a file and will look into it to find if any words repeat themselves
    //using a Scanner Object. It will return an TreeMap with all the data obtained.
    public static TreeMap<String, Integer> wordCounter(File file) throws FileNotFoundException {
        //TreeMap that will be returned, which counts words and how many times they repeat themselves.
        TreeMap<String, Integer> counter = new TreeMap<>();

        //The scanner object will be used to identify words within the files, using the regex to set the
        //to all but letters.
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("[^a-zA-Z]");

        while (scanner.hasNext()){
            //Initializing a variable that will hold the current word on the scanner.
            String currentWord = scanner.next();
            //We are setting every word to lower case, so they are all uniform which would make it easier to ==
            currentWord = currentWord.toLowerCase();

            if (counter.containsKey(currentWord)){
                //Identifies words and adds to its counter
                counter.put(currentWord, counter.get(currentWord) + 1);
            } else{
                //if the word is not found then we create a new entry that will have the number 1 as its is its
                // first iteration
                counter.put(currentWord, 1);
            }
        }

        //Returns TreeMap Counter.
        return counter;
    }
}
