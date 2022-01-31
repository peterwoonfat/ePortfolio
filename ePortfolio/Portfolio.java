package eportfolio;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class Portfolio extends JFrame {
    //initialize arraylists to hold stock and mutual funds respectively
    private static List<Investment> investmentList = new ArrayList<>();

    //initialize hashmap, keys = keywords found in investment names, values = indexes of investment's containing the key in its name
    private static Map<String, ArrayList<Integer>> keywordsMap = new HashMap<>();

    //initialize a counter to keep track of current index in investmentList when updating investments
    private int updateIndex = 0;

    //declare GUI components
    JPanel mainPanel, introPanel, commandsPanel, infoPanel, buttonsPanel, messagePanel, commandLabelPanel;
    JPanel symbolPanel, namePanel, quantityPanel, pricePanel, totalGainPanel, keywordsPanel, lPricePanel, hPricePanel, typePanel;
    JPanel resetPanel, buyPanel, sellPanel, prevPanel, nextPanel, savePanel, searchPanel;
    JButton resetBtn, buyBtn, sellBtn, prevBtn, nextBtn, saveBtn, searchBtn;
    JButton buyCommandBtn, sellCommandBtn, updateCommandBtn, getGainCommandBtn, searchCommandBtn, quitCommandBtn;
    JComboBox<String> investmentTypeField;
    JTextField symbolField, nameField, quantityField, priceField, totalGainField, keywordsField, lPriceField, hPriceField;
    JLabel symbolLabel, nameLabel, quantityLabel, priceLabel, totalGainLabel, keywordsLabel, lPriceLabel, hPriceLabel, typeLabel;
    JTextArea introTextArea, feedbackTextArea;
    JLabel messagePanelLabel, commandLabel;
    JScrollPane scrollText;
    Font introFont, titleLabelFont, regularLabelFont, feedbackFont;

    //Portfolio constructor used to set up GUI components
    public Portfolio(String[] args, boolean validFile) {
        //initialize font
        introFont = new Font("Courier", Font.PLAIN, 20);
        titleLabelFont = new Font("Courier", Font.BOLD, 20);
        regularLabelFont = new Font("Monospace", Font.PLAIN, 16);
        feedbackFont = new Font("Monospace", Font.PLAIN, 20);


        //initialize panels
        mainPanel = new JPanel(new BorderLayout());
        introPanel = new JPanel();
        introPanel.setLayout(new BoxLayout(introPanel, BoxLayout.Y_AXIS));
        commandsPanel = new JPanel(new GridLayout(1, 0, 20, 5));
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        messagePanel = new JPanel();
        messagePanel.setLayout((new BoxLayout(messagePanel, BoxLayout.Y_AXIS)));

        setContentPane(mainPanel);
        mainPanel.add(commandsPanel, BorderLayout.NORTH);
        mainPanel.add(introPanel, BorderLayout.CENTER);
        introPanel.setVisible(true);
        JPanel[] panelArray = {mainPanel, introPanel, infoPanel, buttonsPanel, messagePanel};

        //initialize JLabels for introPanel
        introTextArea = new JTextArea("\n\n\tWelcome to ePortfolio\n\n\n\n\tChoose a command from the \"Commands\" menu to buy or sell\n\tan investment, update prices for all investments, get gain for\n\tthe portfolio, search for relevant investments, or quit the program.");
        introTextArea.setLineWrap(true);
        introTextArea.setEditable(false);
        introTextArea.setMargin(new Insets(50, 0, 30, 0));
        introTextArea.setFont(introFont);
        introPanel.add(introTextArea);
        Border introBorder = BorderFactory.createLineBorder(Color.BLACK);
        introTextArea.setBorder(BorderFactory.createCompoundBorder(introBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        //initialize a JPanel with JMenu and an accompanying JLabel in infoPanel for choosing investment type when buying an investment
        typePanel = new JPanel();
        String[] investmentTypes = {"Stock", "Mutual Fund"};
        investmentTypeField = new JComboBox<String>(investmentTypes);
        investmentTypeField.setPreferredSize(new Dimension(130, 27));
        investmentTypeField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //do nothing
            }
        });
        typeLabel = new JLabel("Type");
        typeLabel.setLabelFor(investmentTypeField);
        typeLabel.setPreferredSize(new Dimension(120, 25));
        typeLabel.setFont(regularLabelFont);
        typePanel.add(typeLabel);
        typePanel.add(investmentTypeField);

        //initialize JTextFields and JLabels in infoPanel
        symbolField = new JTextField();
        symbolField.setPreferredSize(new Dimension(200, 27));
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 27));
        quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(200, 27));
        priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(200, 27));
        totalGainField = new JTextField();
        totalGainField.setPreferredSize(new Dimension(200, 27));
        totalGainField.setEditable(false);
        keywordsField = new JTextField();
        keywordsField.setPreferredSize(new Dimension(200, 27));
        lPriceField = new JTextField();
        lPriceField.setPreferredSize(new Dimension(200, 27));
        hPriceField = new JTextField();
        hPriceField.setPreferredSize(new Dimension(200, 27));
        symbolLabel = new JLabel("Symbol");
        symbolLabel.setLabelFor(symbolField);
        symbolLabel.setPreferredSize(new Dimension(140, 25));
        symbolLabel.setFont(regularLabelFont);
        nameLabel = new JLabel("Name");
        nameLabel.setLabelFor(nameField);
        nameLabel.setPreferredSize(new Dimension(140, 25));
        nameLabel.setFont(regularLabelFont);
        quantityLabel = new JLabel("Quantity");
        quantityLabel.setLabelFor(quantityField);
        quantityLabel.setPreferredSize(new Dimension(140, 25));
        quantityLabel.setFont(regularLabelFont);
        priceLabel = new JLabel("Price");
        priceLabel.setLabelFor(priceField);
        priceLabel.setPreferredSize(new Dimension(140, 25));
        priceLabel.setFont(regularLabelFont);
        totalGainLabel = new JLabel("Total Gain");
        totalGainLabel.setLabelFor(totalGainField);
        totalGainLabel.setPreferredSize(new Dimension(140, 25));
        totalGainLabel.setFont(regularLabelFont);
        keywordsLabel = new JLabel("Name Keywords");
        keywordsLabel.setLabelFor(keywordsField);
        keywordsLabel.setPreferredSize(new Dimension(140, 25));
        keywordsLabel.setFont(regularLabelFont);
        lPriceLabel = new JLabel("Low Price");
        lPriceLabel.setLabelFor(lPriceField);
        lPriceLabel.setPreferredSize(new Dimension(140, 25));
        lPriceLabel.setFont(regularLabelFont);
        hPriceLabel = new JLabel("High Price");
        hPriceLabel.setLabelFor(hPriceField);
        hPriceLabel.setPreferredSize(new Dimension(140, 25));
        hPriceLabel.setFont(regularLabelFont);
        commandLabel = new JLabel();
        commandLabel.setPreferredSize(new Dimension(400, 40));
        commandLabel.setVerticalAlignment(JLabel.TOP);
        commandLabel.setFont(titleLabelFont);
        commandLabelPanel = new JPanel();
        commandLabelPanel.add(commandLabel);
        infoPanel.add(commandLabelPanel);
        JTextField[] fieldArray = {symbolField, nameField, quantityField, priceField, totalGainField, keywordsField, lPriceField, hPriceField};

        //initialize JLabel and JTextArea for feedback in messagePanel
        messagePanelLabel = new JLabel("Messages");
        messagePanelLabel.setFont(titleLabelFont);
        messagePanel.add(messagePanelLabel);
        feedbackTextArea = new JTextArea();
        feedbackTextArea.setEditable(false);
        feedbackTextArea.setFont(feedbackFont);
        scrollText = new JScrollPane(feedbackTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollText.setPreferredSize(new Dimension (800, 300));
        scrollText.setEnabled(true);
        messagePanel.add(scrollText);

        //initialize containers for each JLabel and JtextField pair in infoPanel
        symbolPanel = new JPanel();
        symbolPanel.add(symbolLabel);
        symbolPanel.add(symbolField);
        namePanel = new JPanel();
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        quantityPanel = new JPanel();
        quantityPanel.add(quantityLabel);
        quantityPanel.add(quantityField);
        pricePanel = new JPanel();
        pricePanel.add(priceLabel);
        pricePanel.add(priceField);
        totalGainPanel = new JPanel();
        totalGainPanel.add(totalGainLabel);
        totalGainPanel.add(totalGainField);
        keywordsPanel = new JPanel();
        keywordsPanel.add(keywordsLabel);
        keywordsPanel.add(keywordsField);
        lPricePanel = new JPanel();
        lPricePanel.add(lPriceLabel);
        lPricePanel.add(lPriceField);
        hPricePanel = new JPanel();
        hPricePanel.add(hPriceLabel);
        hPricePanel.add(hPriceField);
        JPanel[] infoPanels = {typePanel, symbolPanel, namePanel, quantityPanel, pricePanel, totalGainPanel, keywordsPanel, lPricePanel, hPricePanel};
        for (JPanel panel: infoPanels) {
            infoPanel.add(panel);
        }

        //initialize JButtons in their own panels in commandPanel
        resetBtn = new JButton("Reset");
        resetBtn.setPreferredSize(new Dimension(85, 30));
        resetBtn.setToolTipText("Clear all input fields");
        resetBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //clear input fields
                clearTextFields(fieldArray);
            }
        });
        resetPanel = new JPanel();
        resetPanel.add(resetBtn);
        buyBtn = new JButton("Buy");
        buyBtn.setPreferredSize(new Dimension(85, 30));
        buyBtn.setToolTipText("Add a new investment to your portfolio or add to an existing investment");
        buyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check for required input
                if (symbolField.getText().isEmpty() || nameField.getText().isEmpty() || priceField.getText().isEmpty() || quantityField.getText().isEmpty()) {
                    //not all required input given, send error message
                    feedbackTextArea.setText("Error - input required for investment type, symbol, name, price, and quantity.");
                    return;
                }
                //check if user already owns investment
                int index = isExistingInvestment(symbolField.getText());
                if (index >= 0) {
                    //add to an existing investment
                    if (setInvestmentQty(index) && setInvestmentPrice(index)) {
                        calculateInvestmentBookValue(index);
                        feedbackTextArea.setText("Buy completed...\n" + quantityField.getText() + " shares of " + nameField.getText() + " successfully bought.\n\nUpdated Investment Details:\n" + investmentList.get(index).toString());
                    }
                } else {
                    //new investment - add new Investment object to investmentList and map it
                    if (addInvestment()) {
                        calculateInvestmentBookValue(investmentList.size() - 1);
                        feedbackTextArea.setText("Buy completed...\n" + quantityField.getText() + " shares of " + nameField.getText() + " successfully bought.\n\nUpdated Investment Details:\n" + investmentList.get(investmentList.size() - 1).toString());
                        updateMap(investmentList.size() - 1);
                    }
                }
                //clear input fields
                clearTextFields(fieldArray);
            }
        });
        buyPanel = new JPanel();
        buyPanel.add(buyBtn);
        sellBtn = new JButton("Sell");
        sellBtn.setPreferredSize(new Dimension(85, 30));
        sellBtn.setToolTipText("Sell shares of an existing investment");
        sellBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check for required input
                if (symbolField.getText().isEmpty() || priceField.getText().isEmpty() || quantityField.getText().isEmpty()) {
                    //not all required input given, send error message
                    feedbackTextArea.setText("Error - input required for investment symbol, price, and quantity.");
                    return;
                }
                //check if user already owns investment
                int index = isExistingInvestment(symbolField.getText());
                if (index >= 0) {
                    try {
                        int sellResult = investmentList.get(index).sell(priceField.getText(), quantityField.getText(), feedbackTextArea);
                        if (sellResult < 0) {
                            //error while selling
                            feedbackTextArea.setText("Error selling " + symbolField.getText() + ". Quantity to sell exceeds owned quantity.");
                        } else if (sellResult == 0) {
                            //investment completely sold off, remove from arraylist and hashmap
                            unmapInvestment(index);
                            investmentList.remove(index);
                        }
                    } catch (Exception ex) {
                        feedbackTextArea.setText("Error selling " + symbolField.getText() + "\n" + ex.getMessage());
                    }
                } else {
                    //could not find investment user wants to sell in investmentList
                    feedbackTextArea.setText("You own no holdings matching the ticker symbol " + symbolField.getText() + ".");
                }
                //clear input fields
                clearTextFields(fieldArray);
            }
        });
        sellPanel = new JPanel();
        sellPanel.add(sellBtn);
        prevBtn = new JButton("Prev");
        prevBtn.setPreferredSize(new Dimension(85, 30));
        prevBtn.setToolTipText("View previous investment");
        prevBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (updateIndex > 0 && investmentList.size() > 0) {
                    updateIndex--;
                    //clear price input field for previous investment and feedbackTextArea
                    priceField.setText("");
                    feedbackTextArea.setText("");
                    //if user owns another investment, display previous investment name and symbol
                    symbolField.setText(investmentList.get(updateIndex).getSymbol());
                    nameField.setText(investmentList.get(updateIndex).getName());
                }
                //if at beginning of investmentList then disable prevBtn
                if (updateIndex == 0) {
                    prevBtn.setEnabled(false);
                } else {
                    prevBtn.setEnabled(true);
                }
                if (updateIndex != investmentList.size() - 1) nextBtn.setEnabled(true);
            }
        });
        prevPanel = new JPanel();
        prevPanel.add(prevBtn);
        nextBtn = new JButton("Next");
        nextBtn.setPreferredSize(new Dimension(85, 30));
        nextBtn.setToolTipText("View next investment");
        nextBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (updateIndex < investmentList.size() - 1 && investmentList.size() > 0) {
                    updateIndex++;
                    //clear price input field for next investment and feedbackTextArea
                    priceField.setText("");
                    feedbackTextArea.setText("");
                    //if user owns another investment, display next investment name and symbol
                    symbolField.setText(investmentList.get(updateIndex).getSymbol());
                    nameField.setText(investmentList.get(updateIndex).getName());
                }
                if (updateIndex == investmentList.size() - 1) {
                    nextBtn.setEnabled(false);
                } else {
                    nextBtn.setEnabled(true);
                }
                if (updateIndex != 0) prevBtn.setEnabled(true);
            }
        });
        nextPanel = new JPanel();
        nextPanel.add(nextBtn);
        saveBtn = new JButton("Save");
        saveBtn.setPreferredSize(new Dimension(85, 30));
        saveBtn.setToolTipText("Save updated investment's information");
        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check for required input
                if (priceField.getText().isEmpty()) {
                    //not all required input given, send error message
                    feedbackTextArea.setText("Error - input required for investment price.");
                    return;
                }
                updatePrices(updateIndex);
            }
        });
        savePanel = new JPanel();
        savePanel.add(saveBtn);
        searchBtn = new JButton("Search");
        searchBtn.setPreferredSize(new Dimension(85, 30));
        searchBtn.setToolTipText("Look for investments matching specified search parameters");
        searchBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                feedbackTextArea.setText("");
                String tickerSymbol = symbolField.getText();
                String[] keywords = getKeywords();
                double[] priceRange = getPriceRange();
                if (tickerSymbol.isEmpty() && keywords[0].isEmpty() && priceRange == null) {
                    //no search parameters entered - print all current investments
                    displayAll();
                } else {
                    //search through arraylists, comparing existing holdings with the search criteria, printing any that match
                    searchInvestments(tickerSymbol, keywords, priceRange);
                }
            }
        });
        searchPanel = new JPanel();
        searchPanel.add(searchBtn);
        JPanel[] buttonPanels = {resetPanel, buyPanel, sellPanel, prevPanel, nextPanel, savePanel, searchPanel};
        for (JPanel panel: buttonPanels) {
            buttonsPanel.add(panel);
        }

        //initialize JButtons in commandPanel
        Insets commandBtnMargins = new Insets(5, 40, 5, 40);
        buyCommandBtn = new JButton("Buy");
        buyCommandBtn.setToolTipText("Buy an investment");
        buyCommandBtn.setMargin(commandBtnMargins);
        buyCommandBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                feedbackTextArea.setText("");
                enableUI(panelArray);
                int[][] indexArray = {{0, 1, 2, 3, 4}, {0, 1}};
                hideInputFields(infoPanels, indexArray[0]);
                hideButtons(buttonPanels, indexArray[1]);
                symbolField.setEditable(true);
                nameField.setEditable(true);
                messagePanelLabel.setText("Messages");
                commandLabel.setText("Buying an investment");
                //clear input fields
                clearTextFields(fieldArray);
            }
        });
        sellCommandBtn = new JButton("Sell");
        sellCommandBtn .setToolTipText("Sell an investment");
        sellCommandBtn.setMargin(commandBtnMargins);
        sellCommandBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                feedbackTextArea.setText("");
                enableUI(panelArray);
                int[][] indexArray = {{1, 3, 4}, {0, 2}};
                hideInputFields(infoPanels, indexArray[0]);
                hideButtons(buttonPanels, indexArray[1]);
                symbolField.setEditable(true);
                nameField.setEditable(true);
                messagePanelLabel.setText("Messages");
                commandLabel.setText("Selling an investment");
                //clear input fields
                clearTextFields(fieldArray);
            }
        });
        updateCommandBtn = new JButton("Update");
        updateCommandBtn.setToolTipText("Update investments");
        updateCommandBtn.setMargin(commandBtnMargins);
        updateCommandBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateIndex = 0;
                feedbackTextArea.setText("");
                enableUI(panelArray);
                int[][] indexArray = {{1, 2, 4}, {3, 4, 5}};
                hideInputFields(infoPanels, indexArray[0]);
                hideButtons(buttonPanels, indexArray[1]);
                messagePanelLabel.setText("Messages");
                commandLabel.setText("Updating investments");
                //clear input fields
                clearTextFields(fieldArray);
                //check conditions for enabling/disabling prevBtn and nextBtn
                if (investmentList.size() == 0) {
                    prevBtn.setEnabled(false);
                    nextBtn.setEnabled(false);
                } else {
                    prevBtn.setEnabled(true);
                    nextBtn.setEnabled(true);
                }
                if (updateIndex == 0) prevBtn.setEnabled(false);
                if (updateIndex == investmentList.size() - 1) nextBtn.setEnabled(false);
                if (investmentList.size() > 0) {
                    //if user owns investments display investment name and symbol
                    symbolField.setText(investmentList.get(updateIndex).getSymbol());
                    symbolField.setEditable(false);
                    nameField.setText(investmentList.get(updateIndex).getName());
                    nameField.setEditable(false);
                }
            }
        });
        getGainCommandBtn = new JButton("Get Gain");
        getGainCommandBtn.setToolTipText("Calculate hypothetical gain");
        getGainCommandBtn.setMargin(commandBtnMargins);
        getGainCommandBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                feedbackTextArea.setText("");
                enableUI(panelArray);
                int[][] indexArray = {{5}, {}};
                hideInputFields(infoPanels, indexArray[0]);
                hideButtons(buttonPanels, indexArray[1]);
                messagePanelLabel.setText("Individual Gains");
                commandLabel.setText("Getting total gain");
                //clear input fields
                clearTextFields(fieldArray);
                
                //display total gain in totalGainField and individual gains in feedbackTextField
                double totalGain = getGains();
                //check if no investments owned, (-11111.11 is arbitrary value denoting no holdings owned)
                if (totalGain == -11111.11) {
                    feedbackTextArea.setText("Could not calculate total gain - you currently own no investments.");
                } else {
                    //investments owned, display gain
                    totalGainField.setText(String.format("%.2f", totalGain));
                }
            }
        });
        searchCommandBtn = new JButton("Search");
        searchCommandBtn.setToolTipText("Search for investments");
        searchCommandBtn.setMargin(commandBtnMargins);
        searchCommandBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                feedbackTextArea.setText("");
                enableUI(panelArray);
                int[][] indexArray = {{1, 6, 7, 8}, {0, 6}};
                hideInputFields(infoPanels, indexArray[0]);
                hideButtons(buttonPanels, indexArray[1]);
                symbolField.setEditable(true);
                messagePanelLabel.setText("Search Results");
                commandLabel.setText("Searching Investments");
                //clear input fields
                clearTextFields(fieldArray);
            }
        });
        quitCommandBtn = new JButton("Quit");
        quitCommandBtn.setToolTipText("Exit the program");
        quitCommandBtn.setMargin(commandBtnMargins);
        quitCommandBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "eportfolio", JOptionPane.YES_NO_OPTION);
                if (exit == JOptionPane.YES_OPTION) {
                    //save portfolio to file specified at command line
                    if (args.length > 0 && validFile == true) {
                        saveData(args[0]);
                    }
                    System.exit(0); //forcibly end program
                }
            }
        });
        commandsPanel.add(buyCommandBtn);
        commandsPanel.add(sellCommandBtn);
        commandsPanel.add(updateCommandBtn);
        commandsPanel.add(getGainCommandBtn);
        commandsPanel.add(searchCommandBtn);
        commandsPanel.add(quitCommandBtn);
        Border commandsPanelBorder = BorderFactory.createLineBorder(Color.BLACK);
        commandsPanel.setBorder(commandsPanelBorder);
        commandsPanel.setBounds(900, 150, 300, 500);

        //set properties of JFrame
        setTitle("ePortfolio");
        setResizable(false);
        setPreferredSize(new Dimension(1200,700));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new CloseButton());
    }

    //main containing command loop
    public static void main (String[] args) {
        boolean validFile = false;
        //load data from file specified at command line
        if (args.length > 0) {
            validFile = loadData(args[0]);
            //add saved investments to hashmap
            loadMap();
        }

        new Portfolio(args, validFile);
    }

    /**
     * method adds a new investment to investmentList and calls methods to initialize its attributes
     * @return true if new investment successfully added, otherwise false
     */
    private boolean addInvestment() {
        //new investment - get investment name, quantity, and price
        if (investmentTypeField.getSelectedItem().equals("Stock")) {
            //add a new stock to the portfolio
            try {
                investmentList.add(new Stock(symbolField.getText()));
                setInvestmentName(investmentList.size() -1);
                setInvestmentPrice(investmentList.size() - 1);
                setInvestmentQty(investmentList.size() - 1);
            } catch (Exception e) {
                feedbackTextArea.setText("Error adding stock\n" + e.getMessage());
                return false;
            }
        } else {
            //add a new mutual fund to the portfolio
            try {
                investmentList.add(new MutualFund(symbolField.getText()));
                setInvestmentName(investmentList.size() -1);
                setInvestmentPrice(investmentList.size() - 1);
                setInvestmentQty(investmentList.size() - 1);
            } catch (Exception e) {
                feedbackTextArea.setText("Error adding mutual fund\n" + e.getMessage());
                return false;
            }
        }
        feedbackTextArea.setText("Buy completed\nShares of " + nameField.getText() + " successfully bought.\n\nNew Investment Details:\n" + investmentList.get(investmentList.size() - 1).toString());
        return true;
    }

    /**
     * method gets user input for the investment's name
     * @param index of investment in investmentList, -1 if list is empty
     * @return true if investment name successfully set, otherwise false
     */
    private boolean setInvestmentName(int index) {
        //check if user owns any investments
        if (index == -1) {
            feedbackTextArea.setText("Error - no investments owned.");
            return false;
        }
        //try to set set the Investment's name, throwing an exception and looping again for invalid input
        try {
            investmentList.get(index).setName(nameField.getText());
        } catch (Exception e) {
            feedbackTextArea.setText(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * method gets optional search criteria keywords for investment name
     * @return an array of keywords or an array with just an empty string
     */
    private String[] getKeywords() {
        String keywords = keywordsField.getText();
        keywords = keywords.toLowerCase();
        return keywords.split("\\s+");
    }

    /**
     * method gets user input for the optional price range search criteria
     * @return array of doubles respresenting the price range, or null if no price range input
     */
    public double[] getPriceRange() {
        String lowPrice = lPriceField.getText();
        String highPrice = hPriceField.getText();
        //check if user does not want to enter a price range
        if (lowPrice.isEmpty() && highPrice.isEmpty()) return null;
        double[] priceRangeArray = new double[3];
        if (lowPrice.isEmpty() && highPrice.isEmpty()) {
            priceRangeArray[0] = 0.0; //first index lets withinPriceRange method know the search criteria (no price range entered)
            priceRangeArray[1] = 0.0;
            priceRangeArray[2] = 0.0;
        } else {
            if (lowPrice.isEmpty() && !highPrice.isEmpty()) {
                //user wants price less than or equal to
                priceRangeArray[0] = 1.0; //first index lets withinPriceRange method know the search criteria (less than or equal to)
                priceRangeArray[1] = 0.0;
                priceRangeArray[2] = Double.parseDouble(highPrice);
            } else if (!lowPrice.isEmpty() && highPrice.isEmpty()) {
                //user wants price greater than or equal to
                priceRangeArray[0] = 2.0; //first index lets withinPriceRange method know the search criteria (greater than or equal to)
                priceRangeArray[1] = Double.parseDouble(lowPrice);
                priceRangeArray[2] = 0.0;
            } else {
                //user wants price within specified range
                priceRangeArray[0] = 3.0; //first index lets withinPriceRange method know the search criteria (within specified range)
                priceRangeArray[1] = Double.parseDouble(lowPrice);
                priceRangeArray[2] = Double.parseDouble(highPrice);
            }
        }
        return priceRangeArray;
    }

    /**
     * method updates user input quantity of investment at index in investmentList
     * @param index index of the existing investment in the arraylist, -1 if list is empty
     * @return true if quantity successfully set, otherwise false
     */
    private boolean setInvestmentQty(int index) {
        //check if user owns any investments
        if (index == -1) {
            feedbackTextArea.setText("Error - no investments owned.");
            return false;
        }
        //get current quantity
        int currentQty = investmentList.get(index).getQty();
        int qty = Integer.parseInt(quantityField.getText());
        //add to current quantity and check if valid quantity
        try {
            investmentList.get(index).setQty(currentQty + qty);
        } catch (Exception e) {
            //output feedback for invalid input
            feedbackTextArea.setText(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * method updates user input price of investment at index in investmentList
     * @param index index of the existing Investment in the arraylist, -1 if list is empty
     * @param input string representation of investment price
     * @return true if price successfully set, otherwise false
     */
    private boolean setInvestmentPrice(int index) {
        //check if user owns any investments
        if (index == -1) {
            feedbackTextArea.setText("Error - no investments owned.");
            return false;
        }
        double price = Double.parseDouble(priceField.getText());
        //update investment price and check for valid price
        try {
            investmentList.get(index).setPrice(price);
        } catch (Exception e) {
            //output feedback for invalid input
            feedbackTextArea.setText(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * method calculates the Investment's book value
     * @param index index of the existing Investment in the arraylist
     * @param qtyInput user input for investment quantity
     * @param priceInout user input for investment price
     */
    private void calculateInvestmentBookValue(int index) {
        double qty = Double.parseDouble(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());
        investmentList.get(index).setBookValue(qty, price);
    }

    /**
     * method loops through the investment arraylist to check if user already holding the investment
     * @param holdingList the arraylist of Investment objects
     * @param tickerSymbol ticker symbol of the investment
     * @return returns index of the existing holding in its respective arraylist, otherwise returns -1 if new holding
     */
    private static int isExistingInvestment(String tickerSymbol) {
        //check if list is empty
        if (investmentList.size() <= 0) return -1;
        //loop through list and compare symbol with existing investments
        for (int i = 0; i < investmentList.size(); i++) {
            if (compareSymbols(investmentList.get(i).getSymbol(), tickerSymbol)) return i;
        }
        return -1;
    }

    /**
     * method loops through arraylist of stocks and prompts user for input to update prices
     * @param holdingList arraylist of stocks
     * @return true if successfully updated stock prices, false if no stocks to update
     */
    private void updatePrices(int index) {
        //check if there are any owned investments to update prices for
        if (investmentList.size() <= 0) {
            feedbackTextArea.setText("Error - you currently own no investments.");
            return;
        }
        try {
            investmentList.get(index).setPrice(Double.parseDouble(priceField.getText()));
            feedbackTextArea.setText("Updated price for " + investmentList.get(index).getName() + " (" + investmentList.get(index).getSymbol() + ") to $" + priceField.getText() + ".\n\n-----Updated Investment Details-----\n" + investmentList.get(index).toString());
        } catch (Exception e) {
            //error setting price
            feedbackTextArea.setText(e.getMessage());
        }
    }

    /**
     * method sums the gains of all investments held
     * @return total gain of all investments held, or -11111.11 if no investments owned
     */
    private double getGains() {
        if (investmentList.size() == 0) {
            return -11111.11;
        }
        double gain = 0.0;
        String str = "-----Individual investment hypothetical gains-----\n";
        for (Investment i: investmentList) {
            double individualGain = i.calculateHypotheticalGain();
            str = str +"\n" + i.getName() + "(" + i.getSymbol() + ") hypothetical gain = $" + String.format("%.2f", individualGain);
            gain += individualGain;
        }
        feedbackTextArea.setText(str);
        return gain;
    }

    /**
     * method searches through investments contained in arraylists mapped to keywords
     * @param symbol ticker symbol to search for
     * @param keywords keywords to search for in investment name
     * @param priceRange price range to search for
     */
    private void searchInvestments(String symbol, String[] keywords, double[] priceRange) {
        //check if any stocks owned
        if (investmentList.size() == 0) {
            feedbackTextArea.setText("No investments currently owned.");
            return;
        }

        //filter out keywords that are not in hashmap (no current investment's name contains that keyword)
        List<String> filteredKeywords = new ArrayList<>();
        for (String k: keywords) {
            if (keywordsMap.containsKey(k)) filteredKeywords.add(k);
        }

        if (keywords[0].isEmpty()) {
            //no keywords entered - try to match symbol and price range to all investments
            for (int i = 0; i < investmentList.size(); i++) {
                evaluateSearchParams(i, symbol, priceRange);
            }
        } else if (filteredKeywords.size() == 1) {
            //only 1 keyword entered - try to match ticker symbol and price range for all investments containing the keyword
            for (Integer index: keywordsMap.get(filteredKeywords.get(0))) {
                evaluateSearchParams(index, symbol, priceRange);
            }
        } else if (filteredKeywords.size() > 1 ) {
            //multiple keywords entered, find common indexes between arraylists mapped to each keyword
            //loop through each index in the arraylist of the first keyword and see it is contained in the arraylists of the other keywords
            int matches = 1;
            for (Integer i: keywordsMap.get(filteredKeywords.get(0))) {
                for (int j = 1; j < filteredKeywords.size(); j++) {
                    if (keywordsMap.get(filteredKeywords.get(j)).contains(i)) {
                        matches++;
                    } else {
                        continue;
                    }
                }
                if (matches == filteredKeywords.size()) {
                    //investment at index i contains all specified keywords, check ticker symbol and price range
                    evaluateSearchParams(i, symbol, priceRange);
                }
                matches = 1;
            }
        } else {
            feedbackTextArea.setText("No investments matching the specified search parameters were found.");
        }
    }

    /**
     * method checks investments' symbol and price range, and prints details for investments matching these parameters
     * @param index index of investment containing all specified keywords
     * @param symbol user specified ticker symbol to search for
     * @param priceRange user specified price range to search for
     */
    private void evaluateSearchParams(int index, String symbol, double[] priceRange) {
        //initialize search criteria booleans, default to true in case no search criteria entered
        boolean symbolMatch = true;
        boolean priceRangeMatch = true;
        Investment tmpInvestment = investmentList.get(index);
        //if ticker symbol entered then compare ticker symbol
        if (!symbol.isEmpty()) {
            symbolMatch = compareSymbols(tmpInvestment.getSymbol(), symbol);
        }
        //if price range entered then check if investment's price is within the range
        if (priceRange != null) {
            priceRangeMatch = withinPriceRange(tmpInvestment.getPrice(), priceRange);
        }
        //if all search criteria met then print investment information
        if (symbolMatch && priceRangeMatch) {
            String str = feedbackTextArea.getText();
            feedbackTextArea.setText(str + "\nMatch found! Printing investment information...\n" + tmpInvestment.toString());
        }
    }

    /**
     * method prints out each existing investment's string description
     */
    private void displayAll() {
        feedbackTextArea.setText("No search parameters entered - displaying all current investments:\n");
        for (Investment i: investmentList) {
            String str = feedbackTextArea.getText();
            feedbackTextArea.setText(str + "\n" + i.toString());
        }
    }

    /**
     * method compares ticker symbols
     * @param symbol1 the first ticker symbol the compare
     * @param symbol2 the second ticker symbol to compare
     * @return true if the ticker symols are the same, otherwise false
     */
    private static boolean compareSymbols(String symbol1, String symbol2) {
        return symbol1.equalsIgnoreCase(symbol2);
    }

    /**
     * method checks if price is within price range
     * @param price price of investment
     * @param priceRange price range criteria
     * @return true if investment price is within price range, otherwise false
     */
    private static boolean withinPriceRange(double price, double[] priceRange) {
        if (priceRange[0] == 1.0) {
            //check if price is less than or equal to specified price
            if (price <= priceRange[2]) return true;
        } else if (priceRange[0] == 2.0) {
            //check if price is greater than or equal to specified price
            if (price >= priceRange[1]) return true;
        } else if (priceRange[0] == 3.0) {
            //check if price is between the 2 specified prices
            if ((price >= priceRange[1]) && (price <= priceRange[2])) return true;
        }
        return false;
    }

    /**
     * method reads through input file line by line and loads the data into investmentList
     * @param filename name of input file, specified in command line
     * @return true if all data successfully read from file, otherwise false
     */
    private static boolean loadData(String filename) {
        //attempt to open input stream to file specified in command line
        Scanner inputStream = null;
        try {
            inputStream = new Scanner(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            //file specified could not be found, continue executing program without loading any preexisting data
            JOptionPane.showMessageDialog(null, "File " + filename + " could not be found or could not be opened.", "ERROR OPENING FILE", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        //declare variables for attributes of Investment object
        String type = "", symbol = "", name = "";
        int quantity = 0;
        double price = 0.0, bookValue = 0.0;
        //read from file and parse data line by line
        String str;
        int counter = 0;
        while (inputStream.hasNextLine()) {
            str = inputStream.nextLine();
            //check for newline character or blank line
            if (str.isEmpty() || str.equals("\n")) {
                //skip processing this line
                continue;
            }
            //split string on quotation marks
            String[] splitStr = str.split("\"");
            if (counter == 0) {
                type = splitStr[1];
                counter++;
            } else if (counter == 1) {
                symbol = splitStr[1];
                counter++;
            } else if (counter == 2) {
                name = splitStr[1];
                counter++;
            } else if (counter == 3) {
                quantity = Integer.parseInt(splitStr[1]);
                counter++;
            } else if (counter == 4) {
                price = Double.parseDouble(splitStr[1]);
                counter++;
            } else if (counter == 5) {
                bookValue = Double.parseDouble(splitStr[1]);
                //last attribute - add investment to list, update its attributes and reset counter for next investment
                if (type.equalsIgnoreCase("stock")) {
                    try {
                        investmentList.add(new Stock(symbol));
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR READING FILE", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                } else {
                    try {
                        investmentList.add(new MutualFund(symbol));
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR READING FILE", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
                try {
                    investmentList.get(investmentList.size() - 1).setName(name);
                    investmentList.get(investmentList.size() - 1).setQty(quantity);
                    investmentList.get(investmentList.size() - 1).setPrice(price);
                    investmentList.get(investmentList.size() - 1).setBookValue(bookValue);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR READING FILE", JOptionPane.ERROR_MESSAGE);
                } finally {
                    counter = 0;
                }
            }
        }
        inputStream.close();
        JOptionPane.showMessageDialog(null, "Data from " + filename + " successfully loaded.", "DATA SUCCESSFULLY LOADED", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    /**
     * method loads keywords from saved investments into keywordsMap
     */
    private static void loadMap() {
        //loop through investmentList and call updateMap method for each investment
        for (int i = 0; i < investmentList.size(); i++) {
            updateMap(i);
        }
    }

    /**
     * method adds a new investment to keywordsMap
     * @param index index of newInvestment in investmentList
     */
    private static void updateMap(int index) {
        //split investment name into array of lowercase strings (delimited by whitespace)
        String[] keywords = investmentList.get(index).getName().toLowerCase().split("\\s+");
        //loop through array of keywords
        for (String s: keywords) {
            if (!keywordsMap.containsKey(s)) {
                //new keyword - initialize arraylist to hold indexes of investments associated with key
                keywordsMap.put(s, new ArrayList<Integer>());
            }
            //add index of investment to arraylist
            keywordsMap.get(s).add(index);
        }
    }

    private void unmapInvestment(int index) {
        //get keywords in investment name
        String[] keywords = investmentList.get(index).getName().toLowerCase().split("\\s+");
        //unmap the investment index from the keyword in keywordsMap
        for (String keyword: keywords) {
            if (keywordsMap.containsKey(keyword)) {
                for (int i = 0; i < keywordsMap.get(keyword).size(); i++) {
                    if (keywordsMap.get(keyword).get(i) == index) {
                        keywordsMap.get(keyword).remove(i);
                        break;
                    }
                }
            }
        }
    }

    /**
     * method saves current portfolio data to specified text file
     * @param filename name of file to save data to
     */
    private static void saveData(String filename) {
        //attempt to open input stream to file specified in command line
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileOutputStream(filename));
            for (Investment i: investmentList) {
                outputStream.println(i.getFileOutputString());
            }
            JOptionPane.showMessageDialog(null, "Saving portfolio and exiting program","DATA SUCCESSFULLY SAVED", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            //specified file could not be found, continue executing program without loading any preexisting data
            JOptionPane.showMessageDialog(null, "File " + filename + " could not be found or could not be opened. Data was unable to be saved.", "ERROR SAVING DATA", JOptionPane.ERROR_MESSAGE);
        } finally {
            outputStream.close();
        }
    }

    /**
     * method makes only the relevant JTextAreas and JLabels in the inputPanel visible
     * @param inputFieldArray list of JTextFields in inputPanel
     * @param panels array of JPanels with a JLabel and JTextFields
     */
    private void hideInputFields(JPanel[] panels, int[] indexes) {
        for (int i = 0; i < panels.length; i++) {
            panels[i].setVisible(false);
            for (int j = 0; j < indexes.length; j++) {
                if (i == indexes[j]) {
                    panels[i].setVisible(true);
                }
            }
        }
    }

    /**
     * method makes only the relevant JButtons visible in the buttonsPanel
     * @param buttons array of JButtons in the buttonsPanel
     * @param indexes array of ints representing the indexes of the JButtons to keep visible
     */
    private void hideButtons(JPanel[] buttonsPanels, int[] indexes) {
        if (indexes.length > 0) {
            //keep some buttons visible
            for (int i = 0; i < buttonsPanels.length; i++) {
                buttonsPanels[i].setVisible(false);
                for (int j = 0; j < indexes.length; j++) {
                    if (i == indexes[j]) {
                        buttonsPanels[i].setVisible(true);
                    }
                }
            }
        } else {
            //hide all buttons
            for (JPanel panels: buttonsPanels) {
                panels.setVisible(false);
            }
        }
    }

    /**
     * method checks if the introPanel is visible, if not visible then it is set to invisible and other JPanels in the array are set to visible
     * @param panels array of JPanels that are subpanels of mainPanel
     */
    private void enableUI(JPanel[] panels) {
        //check if intro panel is hidden, should be hidden after first command button pressed
        if (!panels[1].isVisible()) return;
        //intro panel not hidden - set it to invisible and set all other subpanels of mainPanel to visible
        panels[1].setVisible(false);
        panels[0].remove(panels[1]);
        panels[0].add(panels[2], BorderLayout.CENTER);
        panels[2].setVisible(true);
        panels[0].add(panels[3], BorderLayout.EAST);
        panels[3].setVisible(true);
        panels[0].add(panels[4], BorderLayout.SOUTH);
        panels[4].setVisible(true);
    }

    /**
     * method clears the text in the JTextFields and JComboBox used for user input
     * @param inputFields array of JTextFields to clear
     */
    private void clearTextFields(JTextField[] inputFields) {
        investmentTypeField.setSelectedItem("Stock");
        for (int i = 0; i < inputFields.length; i++) {
            inputFields[i].setText("");
        }
    }

    /**
     * inner class handling WindowEvent for closing the app window
     * prompt user to make sure they wish to exit
     */
    private class CloseButton extends WindowAdapter {
        public void windowClosing (WindowEvent e) {
            //double check with user that they wish to close the app
            int exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "eportfolio", JOptionPane.YES_NO_OPTION);
            if (exit == JOptionPane.YES_OPTION) {
                System.exit(0); //forcibly end program
            }
        }
    }
}