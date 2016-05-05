package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    private HashSet<String> wordset = new HashSet<>();
    private ArrayList<String> wordlist = new ArrayList<>();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();
    private HashMap<Integer, ArrayList<String>> sizeToWord = new HashMap<>();
    private int wordlength = DEFAULT_WORD_LENGTH;


    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();

            wordset.add(word);
            wordlist.add(word);
            if (sizeToWord.get(word.length()) == null) {
                sizeToWord.put(word.length(), new ArrayList<String>());
                sizeToWord.get(word.length()).add(word);
            }
            else
                sizeToWord.get(word.length()).add(word);

            String sortedword = Sort(word);
            if(lettersToWord.get(sortedword)!= null){

                lettersToWord.get(sortedword).add(word);
            }
            else{
                ArrayList<String> values = new ArrayList<>();
                values.add(word);
                lettersToWord.put(sortedword , values);
            }

        }
    }

    public boolean isGoodWord(String word, String base) {

        if(wordset.contains(word)&& !(word.contains(base)))
            return true;
        else
            return false;

    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for(char i='a';i<='z';i++){
            String temp = word + i;
            String newstring = Sort(temp);

            if(lettersToWord.get(newstring) !=null){
                result.addAll(lettersToWord.get(newstring));
            }

        }
        return result;
    }

    public String pickGoodStarterWord() {

        ArrayList<String> array = new ArrayList<>();
        array=sizeToWord.get(wordlength);
        int i = random.nextInt((array.size()));
        while(getAnagramsWithOneMoreLetter(array.get(i)).size() <= MIN_NUM_ANAGRAMS){

            i = random.nextInt((array.size()));

        }
         if (wordlength == 6)
             wordlength=DEFAULT_WORD_LENGTH;
         else
            wordlength++;
        return array.get(i);
    }


    public  String Sort(String sort){
        char[] arr =sort.toCharArray();
        Arrays.sort(arr);
        String str = new String(arr);
        return str;
    }
}
