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
    private static final String pathToTrainHam = "\\train/ham";
   //private static final String pathToTrainHam2 = "/resources/data/train/ham2";
    private static final String pathToTrainSpam = "\\train/spam";
    private static final String pathToTestHam = "\\test/ham";
    private static final String pathToTestSpam = "\\test/spam";
    public TreeMap<String, Double>spamProbability;


    public List<TestFile> trainAndTest(File mainDirectory) throws FileNotFoundException {
//        TODO: main method of loading the directories and files, training and testing the model
        //ArrayList that will be returned
        ArrayList<TestFile> resutls = new ArrayList<>();

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

        //All files paths
        String mainPath = mainDirectory.getAbsolutePath();
        File trainHamFile = new File(mainPath + pathToTrainHam);
        //File trainHamFile2 = new File(mainPath + pathToTrainHam2);
        File trainSpamFile = new File(mainPath + pathToTrainSpam);
        File testHamFile = new File(mainPath + pathToTestHam);
        File testSpamFile = new File(mainPath + pathToTestSpam);

        File[] trainHamFiles = trainHamFile.listFiles();
        File[] trainSpamFiles = trainSpamFile.listFiles();
        File[] testHamFiles = testHamFile.listFiles();
        File[] testSpanFiles = testSpamFile.listFiles();

        //probability word get reapeted in Spam
        TreeMap<String, Double> probabilityOfSpamWord = new TreeMap<>();
        //probability word get repeated in Ham
        TreeMap<String, Double> probabilityOfHamWord = new TreeMap<>();
        //The total probability
        TreeMap<String, Double> probabilityOfSpam = new TreeMap<>();
        //Set of all Words in Spam and Ham
        Set<String> spamW = trainSpamFreq.keySet();
        Set<String> hamW = trainHamFreq.keySet();

        //Looping for all file within trainHamFiles and training them
        for(File file: trainHamFiles) {
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
        for(File file: trainSpamFiles) {
            //Repeat what was done for the trainHam but for Spam this time
            wordFreqSpam = Helper.wordCounter(file);
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

        for(String spam: spamW){
            probabilityOfSpamWord.put(spam, Double.valueOf(trainSpamFreq.get(spam)) / trainSpamCount);
        }
        for (String ham: hamW){
            probabilityOfHamWord.put(ham, Double.valueOf(trainHamFreq.get(ham)) / trainHamCount);
        }

        //Create a new hashset of strings containing every word using a hashset so that every entry is unique
        //and never repeat.
        HashSet<String> words = new HashSet<>( spamW );
        words.addAll(hamW);

        for (String word : words){
            //Initialize them to 0.0 as their real value will be assigned later.
            double spamProbability = 0.0;
            double hamProbability = 0.0;
            double probability = 0.0;

            //Checks to see if the word matches with one of the words on the TreeMap and assigns it value to
            //the spamProbability
            if(probabilityOfSpamWord.containsKey(word)){
                spamProbability = probabilityOfSpamWord.get(word);
            }
            if(probabilityOfHamWord.containsKey(word)){
                hamProbability = probabilityOfHamWord.get(word);
            }

            probability = spamProbability / (spamProbability + hamProbability);
            probabilityOfSpam.put(word, probability);

        }

        //Training is done and now is time to TEst

        for(File file: testHamFiles){
            //Same process as before
            TreeMap<String, Integer> wFreq = Helper.wordCounter(file);
            Set<String> letters = wFreq.keySet();
            //n variable from the formula
            double n = 0.0;
            double probability = 0.0;
            for(String letter: letters) {
                if (spamProbability.containsKey(letter)) {
                    if(spamProbability.get(letter) != 0){
                        //+= needed as it is the sum of all
                        n += Math.log(1 - spamProbability.get(letter)) - Math.log(spamProbability.get(letter));
                    }
                }

            }
            probability = 1 / (1 + Math.pow(Math.E,n));
            TestFile testFile = new TestFile(file.getName(), probability, "ham");
            resutls.add(testFile);

        }

        for (File file: testSpanFiles){
            TreeMap<String, Integer> wFreq = Helper.wordCounter(file);
            Set<String> letters = wFreq.keySet();
            //n variable from the formula
            double n = 0.0;
            double probability = 0.0;
            for(String letter: letters) {
                if (spamProbability.containsKey(letter)) {
                    if(spamProbability.get(letter) != 0){
                        //+= needed as it is the sum of all
                        n += Math.log(1 - spamProbability.get(letter)) - Math.log(spamProbability.get(letter));
                    }
                }
            }
            probability = 1 / (1 + Math.pow(Math.E,n));
            TestFile testFile = new TestFile(file.getName(), probability, "spam");
            resutls.add(testFile);

        }

        return resutls;
    }


}

