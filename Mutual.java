package Investment;

public class Mutual extends Investment{
    public final double compen = 45;

    /**
     * Mehtod to create a Mutual Fund array List's element
     * @param symbol symbol of the mutual fund
     * @param name name of the mutual fund
     * @param quantity quantity of the mutual fund
     * @param price price of the mutual fund
     * @param bookValue bookvalue of the mutual fund
     */
    public Mutual(String symbol, String name, int quantity, double price, double bookValue) throws customException{
        super(symbol, name, quantity, price, bookValue);
    }
    /**
     * Checks if it is equal to the components of the mutual fund
     * @param I stock that is getting compared
     * @return returns true if it matches false when it does not
     */
    @Override
    public boolean equals(Object I) {
        if (!(I instanceof Mutual)) return false;
        else {
            Mutual i = (Mutual) I;
            if(!(this.getName().equals(i.getName())))return false;
            if (!(this.getSymbol().equals(i.getSymbol()))) return false;
            if(!(this.getQuantity()==(i.getQuantity())))return false;
            if(!(this.getPrice() == (i.getPrice())))return false;
            if(!(this.getBookValue() == (i.getBookValue())))return false;
            return true;
        }
    }

    /**
     * prints out the Array List
            * @return print statement for the ArrayLIst
            */
    @Override
    public String toString(){
        return super.toString();
    }
    @Override
    public String toFile() {
        return "M#" + super.toFile();
    }

    /**
     * Method that calculates the bookvalue
     * @param quantity quantity of the mutual fund
     * @param price price of the fund
     * @return book value of the fund
     */
    public double computeBookValue(int quantity, double price){
        return (quantity*price);
    }
    /**
     * computes the getgain of the Mutual fund Investment
     * @param quantity quantity of the mutual fund
     * @param price price of the mutual fund
     * @param bookValue bookvalue of the mutual fund
     * @return computed getGain of the mutual fund
     */
    public double computeGetGain(int quantity, double price, double bookValue){return (quantity * price - compen) - bookValue;}

    public double computePayment(int quantity, int oldQuantity, double bookValue){
        double newBook = 0;
        int diff = oldQuantity - quantity;
        newBook = ((((double)diff/oldQuantity)*bookValue) - compen);
        return newBook;
    }
}
