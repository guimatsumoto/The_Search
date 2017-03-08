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

public class main_search {
    public static void main(String[] args){
        long start = System.nanoTime();
        Dictionary Dict = new Dictionary("english_dictionary.txt", "big.txt");
        long elapsedTime = (System.nanoTime() - start);
        System.out.println("Time to initialize dictionary: " + elapsedTime);

        //Set<Match> matches = Dict.search("ornamnetal", 3);
        //for (Iterator<Match> it = matches.iterator(); it.hasNext(); ){
        //    Match m = it.next();
        //    System.out.println("word: " + m.getMatch() + " - dist: " + m.getDist());
        //}

        //start = System.nanoTime();
        //Dictionary.calculateFrequencies("big.txt");
        //elapsedTime = (System.nanoTime() - start);
        //System.out.println("Time to calculate occurances: " + elapsedTime);
    }
}
