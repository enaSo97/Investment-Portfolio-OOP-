package Investment;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Stock extends Investment{
    public final double compen = 9.99;
    private String symbol;
    private String name;
    private int quantity;
    private double price;
    private double bookValue;

    /**
     * Mehtod to create a Stock array List's element
     * @param symbol symbol of the stock
     * @param name name of the stock
     * @param quantity quantity of the stock
     * @param price price of the stock
     * @param bookValue bookvalue of the stock
     */
    public Stock(String symbol, String name, int quantity, double price, double bookValue) throws customException {

        super(symbol, name, quantity, price, bookValue);
    }

    /**
     * prints out the Array List
     * @return print statement for the ArrayLIst
     */
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String toFile()  {
        return "S#" + super.toFile();
    }
    /**
     * Checks if it is equal to the components of the stock
     * @param I stock that is getting compared
     * @return returns true if it matches false when it does not
     */
    @Override
    public boolean equals(Object I) {
        if (!(I instanceof Stock)) return false;
        else {
            Stock i = (Stock) I;
            if(!(this.getName().equals(i.getName())))return false;
            if (!(this.getSymbol().equals(i.getSymbol()))) return false;
             if(!(this.getQuantity()==(i.getQuantity())))return false;
             if(!(this.getPrice() == (i.getPrice())))return false;
             if(!(this.getBookValue() == (i.getBookValue())))return false;
            return true;
        }
    }
    /**
     * Method that calculates the bookvalue
     * @param quantity quantity of the stock
     * @param price price of the stock
     * @return bookValue of the stock
     */
    public double computeBookValue(int quantity, double price) {
        return (quantity * price) + compen;
    }

    /**
     * computes the getgain of the stock Investment
     * @param quantity quantity of the stock
     * @param price price of the stock
     * @param bookValue bookvalue of the stock
     * @return computed getGain of the stck
     */
    public double computeGetGain(int quantity, double price, double bookValue){return (quantity * price - compen) - bookValue;}

    public double computePayment(int quantity, int oldQuantity, double bookValue){
        double newBook = 0;
        int diff = oldQuantity - quantity;
        newBook = ((((double)diff/oldQuantity)*bookValue) - compen);
        return newBook;
    }

}
