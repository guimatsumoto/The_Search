/**
 * Dictionary class, used to store and search words,
 * based on distance to query word and its frequency
 * in the english language
 *
 *
 * @authors  MATSUMOTO Guilherme, PETRY Gabriel
 * @version 1.0
 * @since   2017-01-21
 */

package com.thesearch.dictionary_manager;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import static java.lang.String.format;
import static java.lang.Math.max;

import static com.thesearch.dictionary_manager.BkTree.levDist;
import com.thesearch.dictionary_manager.Match;

public class Dictionary {
    private BkTree _dict = new BkTree();
    final static Charset ENCODING = StandardCharsets.UTF_8;

    public Dictionary(String FileName, String FreqFile) {
        Path path = Paths.get("src/com/thesearch/dictionary_manager", FileName);
        BufferedReader br = null;
        /**
         * First part of this function, simply stores all the different words,
         * all of which are initialized with frequency zero.
         */
        try {
            br = Files.newBufferedReader(path, ENCODING);
            String Line = null;
            while ((Line = br.readLine()) != null) {
                _dict.add(Line);
            }
        } catch (IOException e) {
            System.out.println("La lecture de l'archive a echoue");
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                System.out.println("La fermeture de l'archive a echoue");
            }
        }

        /**
         * Now we will calculate the word frequencies
         * and assign them to the respective words in the BkTree
         */
        HashMap<String, Double> freqs = calculateFrequencies(FreqFile);
        Iterator it = freqs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Double> pair = (Map.Entry) it.next();
            _dict.setFreq(pair.getKey(), pair.getValue());
        }
    }

    public static HashMap<String, Double> calculateFrequencies(String FreqFile){
        Path path = Paths.get("src/com/thesearch/dictionary_manager", FreqFile);
        BufferedReader br = null;
        HashMap<String, Double> FreqMap = new HashMap<>();
        int WordCount = 0;
        String[] LineWords;
        try{
            br = Files.newBufferedReader(path, ENCODING);
            String Line = null;
            while((Line = br.readLine()) != null){
                LineWords = Line.split("\\W+");
                WordCount += LineWords.length;
                for(int i = 0; i < LineWords.length; ++i){
                    /**
                     * Treating a few english contractions, the rest of them would require
                     * a semantic analysis, which we will probably do later
                     */
                    switch (LineWords[i]){
                        case "ll":  LineWords[i] = "will";
                                    break;
                        case "m":   LineWords[i] = "am";
                                    break;
                        case "ve":   LineWords[i] = "have";
                            break;
                        case "re":   LineWords[i] = "are";
                            break;
                        case "t":   LineWords[i] = "not";
                            break;
                    }

                    if (FreqMap.containsKey(LineWords[i]))
                        FreqMap.put(LineWords[i], FreqMap.get(LineWords[i]) + 1.0);
                    else
                        FreqMap.put(LineWords[i], 1.0);
                }
            }
            System.out.println("Unique word count: " + FreqMap.size()    );
        }catch(IOException e){
            System.out.println("La lecture de l'archive a echoue");
        } finally {
            try{
                if (br != null)
                    br.close();
            }catch(IOException e){
                System.out.println("La fermeture de l'archive a echoue");
            }
        }

        /**
         * After having the occurence list, we are going to change the
         * occurance count in the HashMap, to a relative frequency.
         */
        Iterator it = FreqMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Double> pair = (Map.Entry)it.next();
            //System.out.println(pair.getKey() + " -> " + pair.getValue());
            FreqMap.put(pair.getKey(), pair.getValue()/(double)WordCount);
            //System.out.println(pair.getKey() + " -> " + FreqMap.get(pair.getKey()));
        }
        return FreqMap;
    }

    public BkTree getTree(){
        return this._dict;
    }

    public Set<Match> search(String word, int maxDist){
        if (word == null) throw new NullPointerException();
        if (maxDist < 0) throw new IllegalArgumentException("Distance maximale doit etre positif");

        Set<Match> matches = new HashSet<>();

        Queue<Node> queue = new ArrayDeque<>();
        queue.add(_dict.getRoot());

        while (!queue.isEmpty()){
            Node node = queue.remove();
            String mot = node.getElement();
            int distance = levDist(mot, word);

            if (distance < 0) throw new IllegalArgumentException(format("Distance (%d) entre les mots (%s) et (%s)", distance, word, mot));
            if (distance <= maxDist) matches.add(new Match(mot, distance));

            int distSearchMin = max(distance - maxDist, 0);
            int distSearchMax = distance + maxDist;

            for (int distSearch = distSearchMin; distSearch <= distSearchMax; ++distSearch){
                Node child = node.getChildrenNode(distSearch);
                if (child != null)
                    queue.add(child);
            }

        }

        return matches;
    }

}
