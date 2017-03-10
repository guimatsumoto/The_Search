/**
 * Main function, used mainly for tests.
 * Later on, we're going to implement a visual interface.
 *
 * @authors  MATSUMOTO Guilherme, PETRY Gabriel
 * @version 1.0
 * @since   2017-01-21
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
        Dictionary dict = new Dictionary("english_dictionary.txt", "big.txt");
        long elapsedTime = (System.nanoTime() - start);
        System.out.println("Time to initialize dictionary: " + elapsedTime);

        //Set<Match> matches = dict.search("pasworf", 3);
        //for (Iterator<Match> it = matches.iterator(); it.hasNext(); ){
        //    Match m = it.next();
        //    System.out.println("word: " + m.getMatch() + " - dist: " + m.getDist());
        //}


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
