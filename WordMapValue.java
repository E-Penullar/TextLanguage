/**
 * 
 */
//package edu.ics211.h12;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class WordMapValue {
  int totalFrequency;
  List<FollowingWord> followingWords;
  
  WordMapValue(){
    this.totalFrequency = 0;
    this.followingWords = new ArrayList<>();
  }
  void addFollowingWord(String word) {
    this.totalFrequency++;
    for(FollowingWord fw :this.followingWords) {
      if(fw.word.equals(word)) {
        fw.frequency++;
        return;
      }
    }
    this.followingWords.add(new FollowingWord(word,1));
  }

}
