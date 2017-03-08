/**
 * Created by guilhermematsumoto on 20/02/17.
 */
import com.thesearch.dictionary_manager.Dictionary;
import com.thesearch.dictionary_manager.Match;

import java.util.Set;
import java.util.*;

public class main_search {
    public static void main(String[] args){
        long start = System.nanoTime();
        Dictionary Dict = new Dictionary("english_dictionary.txt");
        long elapsedTime = (System.nanoTime() - start);
        System.out.println("Time to initialize dictionary: " + elapsedTime);
        Set<Match> matches = Dict.search("ornamnetal", 3);
        for (Iterator<Match> it = matches.iterator(); it.hasNext(); ){
            Match m = it.next();
            System.out.println("word: " + m.getMatch() + " - dist: " + m.getDist());
        }
    }
}
