package com.thesearch.appGui;

import com.thesearch.dictionary_manager.Dictionary;
import com.thesearch.dictionary_manager.Match;
import com.thesearch.dictionary_manager.Suggestion;
import com.thesearch.webDealer.googleExtractor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

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
    private JTextPane ourNumberofSuggestionTextPanel;
    private JTextPane googleNumberOfResultsTextPanel;
    private JLabel myNumberOfResultsPanel;
    private JLabel googleNumberOfResultsPanel;
    private JPanel menuPanel;
    private JPanel namePanel;
    private JLabel nameLabel;

    private JMenuBar mainMenuBar;
    private JMenu file, dict;
    private JMenuItem helpMeMenuItem, exitMenuItem, createFreqDictMenuItem, selectDictMenuItem;

    private Dictionary _dict;
    private googleExtractor _extractor;

    public searchGui() {
        _dict = new Dictionary("english.txt", "big.txt");
        _extractor = new googleExtractor();

        menuPanel.setLayout(new BorderLayout());

        mainMenuBar = new JMenuBar();

        file = new JMenu("File");
        dict = new JMenu("Dictionary");

        helpMeMenuItem = new JMenuItem("Help me");
        //file.add(helpMeMenuItem);
        exitMenuItem = new JMenuItem("Exit");
        file.add(exitMenuItem);
        createFreqDictMenuItem = new JMenuItem("Create new frequency dictionary");
        dict.add(createFreqDictMenuItem);
        selectDictMenuItem = new JMenuItem("Select working dictionary");
        dict.add(selectDictMenuItem);

        mainMenuBar.add("fileMenu", file);
        //mainMenuBar.add("dictMenu", dict);
        menuPanel.add(mainMenuBar);

        helpMeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

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

                    int myResults, googleResults;
                    myResults = _extractor.extractNumberOfResults(prop.getSugg());
                    ourNumberofSuggestionTextPanel.setText("We found approximately " + NumberFormat.getNumberInstance(Locale.US).format(myResults) + " results.");
                    if (googleSugg != "")
                        googleResults = _extractor.extractNumberOfResults(googleSugg);
                    else
                        googleResults = _extractor.extractNumberOfResults(query);
                    googleNumberOfResultsTextPanel.setText("Google found approximately " + NumberFormat.getNumberInstance(Locale.US).format(googleResults) + " results.");

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
        frame.setLocationRelativeTo(null); //centering the window
        frame.setVisible(true);

    }
}
