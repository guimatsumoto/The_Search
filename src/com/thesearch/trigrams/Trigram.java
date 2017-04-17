package com.thesearch.trigrams;

/**
 * Created by guilhermematsumoto on 17/04/17.
 */
public class Trigram {
    private String _first;
    private String _second;
    private String _third;
    private double _freq;

    public Trigram(String f, String s, String t, double freq){
        this.setTrigram(f, s, t);
        this.setFreq(freq);
    }

    public boolean equals(Trigram t){
        return true;
    }

    public String getFirst(){
        return _first;
    }

    public String getSecond(){
        return _second;
    }

    public String getThird(){
        return _third;
    }

    public double getFreq(){
        return _freq;
    }

    public void setTrigram(String f, String s, String t){
        _first = f;
        _second = s;
        _third = t;
    }

    public void setFreq(double f){
        _freq = f;
    }
}
