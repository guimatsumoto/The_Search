package com.thesearch.appGui;

import com.thesearch.dictionary_manager.Dictionary;
import com.thesearch.dictionary_manager.Match;
import com.thesearch.dictionary_manager.Suggestion;
import com.thesearch.webDealer.googleExtractor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by guilhermematsumoto on 15/04/17.
 */
public class searchGui {
    private JPanel mainPanel;
    private JTextField queryTextField;
    private JPanel QueryPanel;
    private JPanel SuggestionsPanel;
    private JPanel googleSuggestionPanel;
    private JPanel mySuggestionPanel;
    private JButton searchButton;
    private JLabel mySuggestionLabel;
    private JLabel googleSuggestionLabel;
    private JTextPane googleSuggestionTextPanel;
    private JTextPane ourSuggestionTextPanel;

    private Dictionary _dict;
    private googleExtractor _extractor;

    public searchGui() {
        _dict = new Dictionary("english.txt", "big.txt");
        _extractor = new googleExtractor();

        /**
         * at every button click we perform a dictionary correction function, as well as a google suggestion extraction.
         */
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = queryTextField.getText();
                if (!(query.equals(""))) {
                    _extractor.generateURL(query);
                    Suggestion prop = _dict.correctQuery(query);
                    String googleSugg = _extractor.extractSuggestion();
                    if (prop.getChanges())
                        ourSuggestionTextPanel.setText(prop.getSugg());
                    else
                        ourSuggestionTextPanel.setText("We believe your query is correct!");
                    if ((googleSugg != ""))
                        googleSuggestionTextPanel.setText(googleSugg);
                    else
                        googleSuggestionTextPanel.setText("Google believes your query is correct!");
                }
            }
        });

        /**
         * Since intuitively we expect that an [ENTER] pressed in a textField will actionate the button after it, we set that an anction in the
         * textField will simulate a click on the searchButton.
         */
        queryTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchButton.doClick();
            }
        });
    }

    public static void main(String[] args) {
        //visual interface initialization
        JFrame frame = new JFrame("IN204 search engine");
        frame.setContentPane(new searchGui().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
