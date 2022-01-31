package eportfolio;

import javax.swing.JTextArea;

/**
 * represents an investment that is a stock
 * has specialized methods pertaining to selling, compared to mutual funds
 */
public class Stock extends Investment {
    //delcare instance variables
    private static final double COMMISSION_FEE = 9.99;
    private int commissionFeeCounter;

    //default constructor for Stock class, calls another constructor passing empty string as symbol parameter
    public Stock() throws Exception {
        this("");
    }

    /**
     * constructor for Stock class, calls parent class's constructor
     * @param symbol string containing ticker symbol of the stock
     */
    public Stock(String symbol) throws Exception {
        super(symbol);
        commissionFeeCounter = 0;
    }

    /**
     * method calculatse book value after purchase of stock
     * bookvalue is additive when buying more of stock
     * @param qty quantity of stock bought
     * @param price price of stock bought
     */
    @Override
    public void setBookValue(double qty, double price) {
        setBookValue(getBookValue() + (qty * price + COMMISSION_FEE));
        commissionFeeCounter++;
    }

    /**
     * method updates the book value, subtracting the book value of the sold shares
     * @param sellBookValue book value of sold shares
     */
    protected void setBookValueAfterSell(double sellBookValue) {
        bookValue -= sellBookValue;
    }

    /**
     * method updates the quantity and book value after selling, and calculates payment received
     * @param priceInput string representation of selling price of investment
     * @param quantityInput string representation of quantity of investment sold
     * @param feedbackTextArea JTextArea to output feedback to
     * @return 1 if some of investment successfully sold, 0 if investment sold off (no remaining quantity), -1 if selling quantity exceeds owned quantity
     */
    @Override
    public int sell(String priceInput, String quantityInput, JTextArea feedbackTextArea) throws Exception {
        double price = Double.parseDouble(priceInput);
        int quantity = Integer.parseInt(quantityInput);
        //check if quantity to sell exceeds quantity owned before proceeding
        if (quantity > getQty()) return -1;
        //not fully sold off - update quantity, price and book value
        try {
            setPrice(price);
            setQty(getQty() - quantity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        //if stock not being fully sold off then get the book value of the sell off and subtract from current book value to get updated book value
        double sellBookValue = 0.0;
        if ((getQty() + quantity) > quantity) {
            sellBookValue = getSellBookValue(quantity);
            setBookValueAfterSell(sellBookValue);
        }
        //print updated stock information
        feedbackTextArea.setText("Sell completed...\n\n-----Updated Investment Details-----\n" + toString());
        //calculate and print payment received from selling
        calculatePayment(price, quantity, sellBookValue, feedbackTextArea);
        //check if all shares being sold
        if (quantity == (getQty() + quantity)) return 0;
        return 1;
    }

    /**
     * method calculates the payment received after selling a stock
     * @param price selling price of stock
     * @param quantity quantity of stock sold
     */
    private void calculatePayment(double price, int quantity, double sellBookValue, JTextArea feedbackTextArea) {
        double payment = quantity * price - COMMISSION_FEE;
        String str = feedbackTextArea.getText();
        if (quantity == (getQty() + quantity)) {
            feedbackTextArea.setText(str + "\n\nAfter selling " + quantity + " shares of " + getSymbol() + " at $" + getPrice() + ", you have completely sold off the investment and will receive a payment of $" + String.format("%.2f", payment));
        } else {
            feedbackTextArea.setText(str + "\n\nAfter selling " + quantity + " shares of " + getSymbol() + " at $" + getPrice() + ", you will receive a payment of $" + String.format("%.2f", payment));
        }
        str = feedbackTextArea.getText();
        double gain = calculateGainFromSell(payment, sellBookValue);
        if (gain >= 0) {
            feedbackTextArea.setText(str + " (" + String.format("%.2f", gain) + " gain).");
        } else {
            feedbackTextArea.setText(str + " (" + String.format("%.2f", gain) + " loss).");
        }
    }

    /**
     * method calculates the hypothetical gain using the current price and quantity
     * @return hypothetical gain as a double
     */
    @Override
    public double calculateHypotheticalGain() {
        double gain = (getQty() * getPrice() - (COMMISSION_FEE * commissionFeeCounter)) - getBookValue();
        return gain;
    }

    /**
     * method compares the attributes of two objects
     * @param otherObject stock object to compare attributes to
     * @return true if both objects have matching class and attributes, otherwise false
     */
    @Override
    public boolean equals(Object otherObject) {
        //check for null object
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        Stock s = (Stock)otherObject;
        return (getSymbol().equals(s.getSymbol()) && getName().equals(s.getName()) && (getPrice() == s.getPrice()) && (getQty() == s.getQty()) && (getBookValue() == s.getBookValue()));
    }
}
