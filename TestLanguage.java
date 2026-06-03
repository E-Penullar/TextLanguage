/**
 * 
 */
//package edu.ics211.h12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Testing the Language Model that takes the words from provided text files and creates new sentences.
 * 
 * @author Erika Penullar
 */
public class TestLanguage {

  /**
   * @param args
   */
  HashMap<String, WordMapValue> trainingSet = new HashMap<>();

  public TestLanguage() {
    this.trainingSet = new HashMap<String, WordMapValue> (20);
  }


  /*
   * Scanning the provided text files in order to determine the words
   */
  public void processTrainingSet(String[] args) throws FileNotFoundException {
    for (String files : args) {
      try {
        File fileName = new java.io.File(files);
        Scanner scanner = new Scanner(fileName);
        String previousWord = null;

        while (scanner.hasNext()) {
          String currentWord = scanner.next().toLowerCase();

          if (previousWord != null) {
            WordMapValue value = this.trainingSet.get(previousWord);

            if (value == null) {
              value = new WordMapValue();
              trainingSet.put(previousWord, value);
            }
            value.addFollowingWord(currentWord);
          }
          previousWord = currentWord;

        }
        scanner.close();

      } catch (FileNotFoundException e) {
        System.err.println("Error: File not found - " + files);
      }
    }
  }


  /*
   * Generate new text, provided with a starting word and the word count
   */
  public String generateText(String standard, int wordCount) {
    StringBuilder generatedText = new StringBuilder(standard);
    String currentWord = standard;

    String seperator = " ";
    String nextWord = null;

    System.out.println("\n===== Selection of Words =====\n");
    
    for (int i = 1; i < wordCount; i++) {
      
      WordMapValue value = trainingSet.get(currentWord);
      if (value == null || value.followingWords.isEmpty()) {
        break;

      }

      double randomFrequency = Math.random() * value.totalFrequency;
      
      for (FollowingWord fw : value.followingWords) {
        randomFrequency -= fw.frequency;
        if (randomFrequency <= 0) {
          System.out.println("Selected word: " + fw.word);
          nextWord = fw.word;
          break;
        }
      }
      if (nextWord == null) {
        break;
      }
      seperator = i % 20 == 0 ? "\n" : " ";
      generatedText.append(seperator).append(nextWord);
      currentWord = nextWord;
    }
    System.out.println("\n===== End of Selection =====\n");
    return generatedText.toString();
  }


  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Please provide file names as command-line arguments.");
      return;
    }

    TestLanguage testLanguage = new TestLanguage();
    try {
      testLanguage.processTrainingSet(args);
      System.out.println("Training Set Processed Sucessfully...");
      String generatedText = testLanguage.generateText("the", 300);
      System.out.println("Generating New Text... \n\n==========\n\n" + generatedText);
    } catch (FileNotFoundException e) {
      System.out.println("File not Found: " + e.getMessage());
    }

  }

}
