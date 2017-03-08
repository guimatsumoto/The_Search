/**
 * Match class, made public.
 * Used to store search results in Dictionary.java
 * and also to receive search results in the main class
 *
 *
 * @authors  MATSUMOTO Guilherme, PETRY Gabriel
 * @version 1.0
 * @since   2017-01-21
 */

package com.thesearch.dictionary_manager;

/**
 * Created by guilhermematsumoto on 07/03/17.
 */
public class Match {
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
