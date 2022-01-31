package eportfolio;

import javax.swing.JTextArea;

/**
 * represents an investment that is a mutual fund
 * has specialized methods pertaining to selling, compared to stocks
 */
public class MutualFund extends Investment {
    //declare instance variables
    private static final double REDEMPTION_FEE = 45.00;
    private int redemptionFeeCounter;

    //default constructor for MutualFund class, calls another constructor passing empty string as symbol parameter
    public MutualFund() throws Exception {
        this("");
    }

    /**
     * constructor for MutualFund class, calls parent class's constructor
     * @param symbol string containing ticker symbol of the mutual fund
     */
    public MutualFund(String symbol) throws Exception {
        super(symbol);
        redemptionFeeCounter = 0;
    }

    /**
     * method calculatse book value after purchase of mutual fund
     * bookvalue is additive when buying more of mutual fund
     * @param qty quantity of mutual fund bought
     * @param price price of mutual fund bought
     */
    @Override
    public void setBookValue(double qty, double price) {
        setBookValue(getBookValue() + (qty * price));
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
        Double price = Double.parseDouble(priceInput);
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
        //print updated mutual fund information
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
        double payment = (quantity * price) - REDEMPTION_FEE;
        redemptionFeeCounter++;
        String str = feedbackTextArea.getText();
        if (quantity == (getQty() + quantity)) {
            feedbackTextArea.setText(str + "\n\nAfter selling " + quantity + " shares of " + getSymbol() + " at $" + getPrice() + ", you have completely sold off the investment and will receive a payment of $" + String.format("%.2f", payment));
        } else {
            feedbackTextArea.append(str + "\n\nAfter selling " + quantity + " shares of " + getSymbol() + " at $" + getPrice() + ", you will receive a payment of $" + String.format("%.2f", payment));
        }
        str = feedbackTextArea.getText();
        double gain = calculateGainFromSell(payment, sellBookValue);
        if (gain >= 0) {
            feedbackTextArea.append(str + " (" + String.format("%.2f", gain) + " gain).");
        } else {
            feedbackTextArea.append(str + " (" + String.format("%.2f", gain) + " loss).");
        }
    }

    /**
     * method calculates the hypothetical gain using the current price and quantity
     * @return hypothetical gain as a double
     */
    @Override
    public double calculateHypotheticalGain() {
        double gain = 0.0;
        //if no sell offs yet then set redemptionFeeCounter to 1 temporarily to account for redemption fee when selling
        if (redemptionFeeCounter == 1) {
            gain = getQty() * getPrice() - (REDEMPTION_FEE * 1) - getBookValue();
        } else {
            gain = getQty() * getPrice() - (REDEMPTION_FEE * redemptionFeeCounter) - getBookValue();
        }
        return gain;
    }
    
    /**
     * method compares the attributes of two objects
     * @param otherObject mutualFund object to compare attributes to
     * @return true if both objects have matching class and attributes, otherwise false
     */
    @Override
    public boolean equals(Object otherObject) {
        //check for null object
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        MutualFund mf = (MutualFund)otherObject;
        return (getSymbol().equals(mf.getSymbol()) && getName().equals(mf.getName()) && (getPrice() == mf.getPrice()) && (getQty() == mf.getQty()) && (getBookValue() == mf.getBookValue()));
    }
}