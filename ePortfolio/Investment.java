package eportfolio;

import javax.swing.JTextArea;

/**
 * represents an investment
 * an investment can be a stock or a mutual fund
 */
public abstract class Investment {
    //declare instance variables
    private String symbol;
    private String name;
    private int qty;
    private double price;
    protected double bookValue;

    //default constructor for Investment class, calls another constructor passing empty string as symbol parameter
    public Investment() throws Exception {
        this("");
    }

    /**
     * constructor for Investment class, initializes Investment object with passed ticker symbol
     * @param symbol string containing ticker symbol of the investment
     * @throws Exception empty symbol string passed
     */
    public Investment(String symbol) throws Exception {
        setBookValue(0.0);
        //try to set Investment's ticker symbol
        try {
            setSymbol(symbol);
        } catch (Exception e) {
            //exception thrown because symbol string is empty, output exception feedback
            System.out.println(e.getMessage());
        }
    }

    /**
     * copy constructor, creates new instance of passed Investment with identical parameters
     * @param anInvestment the Investment object to be copied
     * @throws Exception invalid Investment passed or invalid Investment attributes
     */
    public Investment(Investment anInvestment) throws Exception {
        //check if valid object passed
        if (anInvestment == null) throw new Exception ("Error creating Investment copy - invalid Investment object passed.");
        setSymbol(anInvestment.getSymbol());
        setName(anInvestment.getName());
        setPrice(anInvestment.getPrice());
        setQty(anInvestment.getQty());
        setBookValue(anInvestment.getBookValue());
    }

    /**
     * mutator method for investment symbol
     * @param symbol ticker symbol of the new investment to add to portfolio
     * @throws Exception when no symbol is provided
     */
    public void setSymbol(String symbol) throws Exception {
        if (symbol == null) {
            throw new Exception ("Error initializing Investment object - invalid symbol.");
        }
        this.symbol = symbol;
    }

    /**
     * accessor method for investment symbol
     * @return the investment symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * mutator method for investment name
     * @param name name of new investment to add to portfolio
     * @throws Exception when no name is provided
     */
    public void setName(String name) throws Exception {
        if (name == null || name.isEmpty()) throw new Exception("Invalid input - this field can not be left blank.");
        this.name = name;
    }

    /**
     * accessor method for investment name
     * @return the investment name
     */
    public String getName() {
        return name;
    }

    /**
     * method calculates book value after selling shares of investment
     * quantity is always updated before book value is calculated
     * @param qtySold the quantity to be sold
     * @return calculates the updated book value after selling shares
     */
    protected double getSellBookValue(int qtySold) {
        double oldQty = getQty() + qtySold;
        return (qtySold / oldQty * getBookValue());
    }

    /**
     * accessor method for current book value of investment
     * @return current book value of investment
     */
    public double getBookValue() {
        return bookValue;
    }

    /**
     * method sets the book value
     * @param bookValue book value of investment
     */
    public void setBookValue(double bookValue) {
        this.bookValue = bookValue;
    }

    /**
     * mutator method for investment quantity
     * @param qty current quantity of investment held in the portfolio
     * @throws Exception when quantity is less than 0
     */
    public void setQty(int qty) throws Exception {
        if (qty < 0) throw new Exception("Invalid input - please enter a quantity greater than or equal to zero.");
        this.qty = qty;
    }

    /**
     * accessor method for investment quantity
     * @return current quantity of investment held in the portfolio
     */
    public int getQty() {
        return qty;
    }

    /**
     * mutator method for investment price
     * @param price current price of the investment
     * @throws Exception when price is less than 0
     */
    public void setPrice(double price) throws Exception {
        if (price < 0.0) throw new Exception("Invalid input - Please enter a price greater than or equal to zero.");
        this.price = price;
    }

    /**
     * accessor method for investment price
     * @return current price of investment
     */
    public double getPrice() {
        return price;
    }

    /**
     * method calculates the gain from selling a portion of an investment
     * @param payment amount of money user receives from selling
     * @return the gain from selling a portion of a holding
     */
    protected double calculateGainFromSell(double payment, double sellBookValue) {
        return (payment - sellBookValue);
    }

    /**
     * method returns an int denoting if the investment is a stock or mutual fund

     * @return string containing "stock" if investment is stock, otherwise string containing "mutualfund"
     */
    protected String getInvestmentType() {
        if (this instanceof Stock) return "stock";
        if (this instanceof MutualFund) return "mutualfund";
        return null;
    }

    /**
     * method creates a string description with all attributes of Stock object
     * @return a string description of stock attributes
     */
    @Override
    public String toString() {
        String typeStr = "Investment Type:\t" + getInvestmentType() + "\n";
        String symbolStr = "Ticker Symbol:\t\t" + getSymbol() + "\n";
        String nameStr = "Name:\t\t" + getName() + "\n";
        String qtyStr = "Quantity:\t\t" + getQty() + "\n";
        String priceStr = "Price:\t\t$" + String.format("%.2f", getPrice()) + "\n";
        String bookValueStr = "Book Value:\t\t$" + String.format("%.2f", getBookValue()) + "\n"; 
        return typeStr + symbolStr + nameStr + qtyStr + priceStr + bookValueStr;
    }

    /**
     * method compiles the investment's attributes into a string
     * @return a string formatted to write to a file for storage
     */
    public String getFileOutputString() {
        String typeStr = "type = \"" + getInvestmentType() + "\"\n";
        String symbolStr = "symbol = \"" + getSymbol() + "\"\n";
        String nameStr = "name = \"" + getName() + "\"\n";
        String qtyStr = "quantity = \"" + getQty() + "\"\n";
        String priceStr = "price = \"" + String.format("%.2f", getPrice()) + "\"\n";
        String bookValueStr = "bookValue = \"" + String.format("%.2f", getBookValue()) + "\"\n"; 
        return typeStr + symbolStr + nameStr + qtyStr + priceStr + bookValueStr;
    }

    /**
     * method compares the attributes of two objects
     * @param otherObject ualFund object to compare attributes to
     * @return true if both objects have matching class and attributes, otherwise false
     */
    @Override
    public boolean equals(Object otherObject) {
        //check for null object
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        Investment i = (Investment)otherObject;
        return (getSymbol().equals(i.getSymbol()) && getName().equals(i.getName()) && (getPrice() == i.getPrice()) && (getQty() == i.getQty()) && (getBookValue() == i.getBookValue()));
    }

    //declare abstract classes with specific implementations in Stock and MutualFund classes
    public abstract int sell(String priceInput, String quantityInput, JTextArea feedbackTextArea) throws Exception;
    public abstract void setBookValue(double qty, double price);
    public abstract double calculateHypotheticalGain();
}