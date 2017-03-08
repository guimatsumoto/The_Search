/**
 * BkTree class, main data structure to store the words
 * of the english dictionary.
 * In a first moment stores only the words with their variations,
 * e.g. verbs conjugation, capital letter initialization...
 * Later, stores word frequencies as well.
 *
 *
 * @authors  MATSUMOTO Guilherme, PETRY Gabriel
 * @version 1.0
 * @since   2017-01-21
 */

package com.thesearch.dictionary_manager;
import java.util.Map;
import java.util.HashMap;

public final class BkTree {

    private Node _root;

    public BkTree(){
        this._root = null;
    }

    public void add(String element){
        if (element == null) throw new NullPointerException();

        if (this._root == null)
            this._root = new Node(element);
        else{
            Node node = this._root;
            while(!node.getElement().equals(element)){
                int dist = levDist(node.getElement(), element);
                Node parent = node;
                node = parent.getChildrenNode(dist);
                if (node == null){
                    node = new Node(element);
                    parent.childrenNode.put(dist, node);
                    break;
                }
            }
        }
    }

    protected void setFreq(String word, Double freq){
        if (word == null) throw new NullPointerException();
        if (freq == null) throw new NullPointerException();

        Node node = this._root;
        while(!node.getElement().equals(word)){
            int dist = levDist(node.getElement(), word);
            Node parent = node;
            node = parent.getChildrenNode(dist);
            if (node == null){
                break;
            }
        }
        if (node != null) {
            node.setFrequency(freq);
            //System.out.println(node.getElement() + " -> " + node.getFrequency());
        }

        node = this._root;
        while(!node.getElement().toLowerCase().equals(word)){
            int dist = levDist(node.getElement(), word);
            Node parent = node;
            node = parent.getChildrenNode(dist);
            if (node == null){
                break;
            }
        }
        if (node != null){
            node.setFrequency(freq);
        }

    }

    public Node getRoot(){
        return this._root;
    }


    static int min(int a, int b, int c){
        return Math.min(Math.min(a,b), c);
    }

    static int levDist(CharSequence a, CharSequence b){
        int[][] dist = new int [a.length() + 1][b.length() + 1];
        for (int i = 0; i < a.length() + 1; i++)
            dist[i][0] = i;
        for (int j = 0; j < b.length() + 1; j++)
            dist[0][j] = j;
        for (int i = 1; i < a.length() + 1; i++)
            for (int j = 1; j < b.length() + 1; j++)
                dist[i][j] = min(dist[i - 1][j] + 1, dist[i][j - 1] + 1, dist[i - 1][j - 1] + ((a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1));

        return dist[a.length()][b.length()];
    }

}

/*
class Node
 */
final class Node{
    private String _element;
    private double _frequency;
    final Map<Integer, Node> childrenNode = new HashMap<>();

    public Node(String e){
        if (e == null) throw new NullPointerException();
        this._element = e;
        this._frequency = 0.0;
    }

    public String getElement(){
        return this._element;
    }

    public double getFrequency() {return this._frequency;}

    public void setFrequency(double freq) {this._frequency = freq;}

    public Node getChildrenNode(Integer dist){
        return childrenNode.get(dist);
    }


}
    /*
    fin class Node
     */