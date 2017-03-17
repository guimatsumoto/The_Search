/**
 * Main function, used mainly for tests.
 * Later on, we're going to implement a visual interface.
 *
 * @authors  MATSUMOTO Guilherme, PETRY Gabriel
 * @version 1.0
 * @since   2017-01-21
 */

/**
 * Note to self: To correct and improve accuracy we need to:
 * done 1) fix the dictionary, that right now contains lots of wrog words (without apostrophes)
 * done 2) also we need to evaluate more texts, because right now the frequencies are all fucked up // not entirely necessary, will disable further verification
 *      3) we need to find a way to better evaluate words with apostrophes, what I mean is, we need to find a way to considerer we will as the same as we'll
 *      4) need also to correct letter capitalization, specially with "I" because it generates further errors.
 *      5) create a self generated dictionary that includes frequencies and updates itself when users make searches
 */

import com.thesearch.dictionary_manager.Dictionary;
import com.thesearch.dictionary_manager.Match;

import java.util.Set;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class main_search {
    public static void main(String[] args){
        long start = System.nanoTime();
        Dictionary dict = new Dictionary("english.txt", "big.txt");
        long elapsedTime = (System.nanoTime() - start);
        System.out.println("Time to initialize dictionary: " + elapsedTime);

        Set<Match> matches = dict.search("wom't", 2);
        for (Iterator<Match> it = matches.iterator(); it.hasNext(); ){
            Match m = it.next();
            System.out.println("word: " + m.getMatch() + " - dist: " + m.getDist() + " - freq: " + m.getFreq());
        }


        BufferedReader br = null;

        try{
            br = new BufferedReader(new InputStreamReader(System.in));
            String query = "";

            while (true) {
                System.out.println("Type [/Q] to quit or type a query to search: ");
                query = br.readLine();
                System.out.println();
                if (query.equals("[/Q]"))
                    break;
                else {
                    String prop = dict.correctQuery(query);
                    System.out.println("Did you mean: " + prop + "?");
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if (br != null){
                try{
                    br.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }


        //start = System.nanoTime();
        //Dictionary.calculateFrequencies("big.txt");
        //elapsedTime = (System.nanoTime() - start);
        //System.out.println("Time to calculate occurances: " + elapsedTime);


    }
}
