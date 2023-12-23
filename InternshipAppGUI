package com.internship;

import javax.swing.*;

import org.jsoup.nodes.Element;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InternshipAppGUI {
    private JFrame frame;
    private JTextArea textArea;
    public JTextField keywordTextField;
    private JButton searchButton;

    private JButton indeedButton;
    private JButton handshakeButton;
    private JButton linkedInButton;
    private String url;
    private String keyword;
    private String site;

    public InternshipAppGUI() {
        frame = new JFrame("Job Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        textArea = new JTextArea();
        textArea.setEditable(false);
        site = "";

        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        keywordTextField = new JTextField();
        keywordTextField.setPreferredSize(new Dimension(200, 15));
        searchButton = new JButton("Search");

        indeedButton = new JButton("Indeed");
        handshakeButton = new JButton("Handshake");
        linkedInButton = new JButton("LinkedIn");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyword = keywordTextField.getText();
                if(site.length() == 0 || keyword.length() == 0) {
                    textArea.setText("Please enter a search term AND select a website before searching!");
                } else {
                    performSearch(site);
                }
            }
        });

        indeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                url = "https://www.indeed.com/jobs?q=";
                site = "Indeed";
            }
        });

        handshakeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                url = "https://uw.joinhandshake.com/stu/postings?query=";
                site = "Handshake";
            }
        });

        linkedInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                url = "https://www.linkedin.com/jobs/search/?keywords=";
                site = "LinkedIn";
            }
        });

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Keyword: "));
        inputPanel.add(keywordTextField);
        inputPanel.add(searchButton);
        inputPanel.add(indeedButton);
        inputPanel.add(handshakeButton);
        inputPanel.add(linkedInButton);

        frame.getContentPane().add(inputPanel, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    private void performSearch(String source) {
        String word = keywordTextField.getText().trim();
        for(int i = 0; i < word.length() - 1; i++) {
            if(word.charAt(i) == ' ') {
                word = word.substring(0, i) + "+" + word.substring(i + 1);
            }
        }
        textArea.setText("Searching for '" + keywordTextField.getText().trim() + "' on " + source + " at link " + url + word);
        List<PositionOpportunity> positions = Scraper.scrape(url + word);
        String output = "";
        for(PositionOpportunity jobs : positions) {
            output += jobs.toString() + "\n";
        }
        textArea.setText(output);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InternshipAppGUI();
            }
        });
    }
}
