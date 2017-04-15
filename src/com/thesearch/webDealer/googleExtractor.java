package com.thesearch.webDealer;

import java.io.IOException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by guilhermematsumoto on 15/04/17.
 * This class is used to maintain the url to search the requested query and the google suggestion to correct that query.
 */
public class googleExtractor {
    private String _url;
    private String _suggestion;

    public googleExtractor(){
        _url = "";
        _suggestion = "";
    }

    public void generateURL(String query){
        String[] words = query.split("[^\\p{L}0-9']+");
        _url = "https://www.google.com/search?q=";
        int i = 0;
        _url += words[i];
        for (i = 1; i < words.length; ++i){
            _url += "+" + words[i];
        }
    }

    public void setSugg(String sugg){
        _suggestion = sugg;
    }

    public String getURL(){
        return _url;
    }

    public String getSugg(){
        return _suggestion;
    }

    /**
     *
     * @return the google suggestion to the query.
     */
    public String extractSuggestion(){
        String body = "";
        try {
            Document doc = Jsoup.connect(_url).get();
            if ((doc.select("a.spell").first() == null) || !(doc.select("a.spell").first().hasText()) || (doc.select("a.spell").first().text() == "") || (doc.select("a.spell").first().text() == null))
                return "";
            Element link = doc.select("a.spell").first(); //gets the content of the tag of interest.
            //System.out.println(link.outerHtml());
            body = link.text();
        }catch(IOException e){
            System.out.println("La connexion google a echoue");
        }

        return body;
    }
}
