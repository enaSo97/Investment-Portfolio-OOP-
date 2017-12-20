package Investment;

abstract public class Investment {
        private String symbol;
        private String name;
        private int quantity;
        private double price;
        private double bookValue;

        /**
         * Mehtod to create a Stock array List's element
         * @param symbol symbol of the Investment
         * @param name name of the Investment
         * @param quantity quantity of the Investment
         * @param price price of the Investment
         * @param bookValue bookvalue of the Investment
         */
        public Investment(String symbol, String name, int quantity, double price, double bookValue) throws customException {
            //create an exceptions
            if (symbol.equals("")){
                throw new customException("Symbol must be non empty.\n");
            }
            else if(name.equals("")){
                throw new customException("Name must be non empty.\n");
            }
            else if (quantity <= 0){
                throw new customException("Quantity must be non empty or greater than 0");
            }
            else if (price <= 0){
                throw new customException("Price must be non empty or greater than 0");
            }

            this.symbol = symbol;
            this.name = name;
            this.quantity = quantity;
            this.price = price;
            this.bookValue = bookValue;
        }

        /**
         * Gets the symbol
         * @return symbol
         */
        public String getSymbol() {
            return symbol;
        }

        /**
         * Sets the symbol
         * @param symbol symbol that is going to be setted
         * @return symbol
         */
        public String setSymbol(String symbol) {
            return this.symbol = symbol;
        }

        /**
         * Gets the name
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the name
         * @param name name is that is going to be setted
         * @return name
         */
        public String setName(String name) {
            return this.name = name;
        }

        /**
         * Gets quantity
         * @return quantity
         */
        public int getQuantity() {
            return quantity;
        }

        /**
         * Sets the quantity
         * @param quantity quantity that is going to be setted
         * @return quantity
         */
        public int setQuantity(int quantity) {
            return this.quantity = quantity;
        }

        /**
         * gets The price
         * @return price
         */
        public double getPrice() {
            return price;
        }

        /**
         * Sets the pirce
         * @param price price that is going to be setted
         * @return price
         */
        public double setPrice(double price) {
            return this.price = price;
        }

        /**
         * Gets the book value
         * @return book value
         */
        public double getBookValue() {
            return bookValue;
        }

        /**
         * Sets the book value
         * @param bookValue book value that is going to be setted
         * @return book value
         */
        public double setBookValue(double bookValue) {
            return this.bookValue = bookValue;
        }

        /**
         * Checks if it is equal to the components of the stock
         * @param I stock that is getting compared
         * @return returns true if it matches false when it does not
         */
        @Override
        public boolean equals(Object I) {
            if (!(I instanceof Investment)) return false;
            else {
                Investment i = (Investment) I;
                //if(!(this.getName().equals(i.getName())))return false;
                if (!(this.getSymbol().equals(i.getSymbol()))) return false;
                // if(!(this.getQuantity()==(i.getQuantity())))return false;
                // if(!(this.getPrice() == (i.getPrice())))return false;
                // if(!(this.getBookValue() == (i.getBookValue())))return false;
                return true;
            }
        }

        /**
         * prints out the Array List
         * @return print statement for the ArrayLIst
         */
        @Override
        public String toString() {
            return "\nSymbol: " + this.symbol + "\nName: " + this.name + "\nQuantity: " + this.quantity + "\nPrice: " + this.price + "\nBook Value: " + this.bookValue  + "\n";
        }

    //@Override

    /**
     * format to load in file
     * @return the string to load in file
     */
    public String toFile() {
        return this.symbol + "#" + this.name + "#" + this.quantity + "#" + this.price + "#" + this.bookValue;
    }
    /**
     * Method that calculates the bookvalue
     * @param quantity quantity of the stock
     * @param price price of the stock
     * @return bookValue of the stock
     */
    abstract public double computeBookValue(int quantity, double price);

    abstract public double computeGetGain(int quantity, double price, double bookValue);

    abstract public double computePayment(int quantity, int oldQuantity, double bookValue);

    public class customException extends Exception{
        public customException(String error){
            super(error);
        }
    }

}
