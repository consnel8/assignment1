package com.spamdetector.util;

import com.spamdetector.domain.TestFile;

import javax.swing.tree.TreeNode;
import java.io.*;
import java.util.*;
import com.spamdetector.util.Helper;

//import static com.spamdetector.util.Helper.wordCounter;


/**
 * TODO: This class will be implemented by you
 * You may create more methods to help you organize you strategy and make you code more readable
 */
public class SpamDetector {
    private static final String pathToTrainHam = "/resources/data/train/ham";
    private static final String pathToTrainHam2 = "/resources/data/train/ham2";
    private static final String pathToTrainSpam = "/resources/data/train/spam";
    public TreeMap<String, Double>spamProbability;


    public List<TestFile> trainAndTest(File mainDirectory) throws FileNotFoundException {
//        TODO: main method of loading the directories and files, training and testing the model

        //The counter for Ham and Spam
        int trainHamCount = 0;
        int trainSpamCount = 0;

        //frequency map trainHamFreq. Contains a map of words and the number of files containing
        // that word in the spam folder.
        TreeMap<String, Integer> trainHamFreq = new TreeMap<>();

        //Frequency map trainSpamFreq. Contains a map of words and the number of files containing
        // that word in the ham folder.
        TreeMap<String, Integer> trainSpamFreq = new TreeMap<>();

        //
        TreeMap<String, Integer> wordFreqHam = new TreeMap<>();
        TreeMap<String, Integer> wordFreqSpam = new TreeMap<>();

        String mainPath = mainDirectory.getAbsolutePath();
        File trainHamFile = new File(mainPath + pathToTrainHam);
        File trainHamFile2 = new File(mainPath + pathToTrainHam2);
        File trainSpamFile = new File(mainPath + pathToTrainSpam);

        File[] trainHamFiles = new File[2];
        trainHamFiles[0] = trainHamFile;
        trainHamFiles[1] = trainHamFile2;

        //Looping for all file within trainHamFiles and training them
        for(File file: trainHamFiles){
            //Passing over the treemap from the helper class
            wordFreqHam = Helper.wordCounter(file);

            //Creating a set that will hold all the words from wordFreq map.
            //We initially thought of using a list, however, we decided against it as list allows for
            //duplication whilst Sets don't.
            Set<String> words = wordFreqHam.keySet();

            //Looping for every word in the set of words. We are essentially repeating what we did on the
            //helper class but for the individual TreeMaps of trainHamFreq.
            for(String word: words) {
                if (trainHamFreq.containsKey(word)) {
                    trainHamFreq.put(word, trainHamFreq.get(word) + 1);
                } else {
                    trainHamFreq.put(word, 1);
                }
            }
            trainHamCount++;
        }

        //Training the one trainSpamFile, we encapsulated it, so we could create another "words" variable.
        {
            //Repeat what was done for the trainHam but for Spam this time
            wordFreqSpam = Helper.wordCounter(trainSpamFile);
            Set<String> words = wordFreqSpam.keySet();

            for(String word: words) {
                if (trainHamFreq.containsKey(word)) {
                    trainHamFreq.put(word, trainHamFreq.get(word) + 1);
                } else {
                    trainHamFreq.put(word, 1);
                }
            }
            trainSpamCount++;

        }

        //probability word get reapeted in Spam
        TreeMap<String, Double> probabilityOfSpamWord = new TreeMap<>();
        //probability word get repeated in Ham
        TreeMap<String, Double> probabilityOfHamWord = new TreeMap<>();
        //Variable for the equation of probability of the word replica in Ham
        TreeMap<String, Double> probabilityOfReplicaHam = new TreeMap<>();
        //
        TreeMap<String, Double> probabilityOfReplicaSpam = new TreeMap<>();
        //
        TreeMap<String, Double> probabilityOfSpam = new TreeMap<>();
        //Set of all Words in Spam and Ham
        Set<String> spamW = trainSpamFreq.keySet();
        Set<String> hamW = trainHamFreq.keySet();

        for(String spam: spamW){
            probabilityOfSpamWord.put(spam, Double.valueOf(trainSpamFreq.get(spam)) / trainSpamCount);
        }
        for (String ham: hamW){
            probabilityOfHamWord.put(ham, Double.valueOf(trainHamFreq.get(ham)) / trainHamCount);
        }



        return new ArrayList<TestFile>();
    }


}

