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

/**
 * Created by guilhermematsumoto on 20/02/17.
 */
public class Dictionary {
    private BkTree _dict = new BkTree();
    final static Charset ENCODING = StandardCharsets.UTF_8;

    public Dictionary(String FileName){
        Path path = Paths.get("src/com/thesearch/dictionary_manager", FileName);
        BufferedReader br = null;
        try{
            br = Files.newBufferedReader(path, ENCODING);
            String Line = null;
            while((Line = br.readLine()) != null){
                _dict.add(Line);
            }
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

    public static final class Match{
        private final String _match;
        private final int _dist;

        public Match(String word, int distance){
            if (word == null) throw new NullPointerException();
            if (distance < 0) throw new IllegalArgumentException("Distance maximale doit etre positif");

            this._match = word;
            this._dist = distance;
        }

        public String getMatch(){
            return _match;
        }

        public int getDist(){
            return _dist;
        }


    }

}
