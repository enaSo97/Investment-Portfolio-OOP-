"# CIS-2430" 
Ena So, yso02, 0961375, University of Guelph

To run the program, configure the run environment with command line argument, it will use it to read or create the file
for the program, if you don't configure command line argument it will end the program gracefully

Limitation of the Program
- Looking at the GUI interface, we can't tell if the file has successfully loaded when we open the GUI window.
- We would have to check the output box to check.

Possible improvements of this program
- When we are selling the investment from the portfolio we are prompting for symbol, quantity, and price, because we
we don't have the library that set's the price. We could create a library that sets the price of the investment when
user buys the investment and use the set price when we are selling the stock

test plan:
In update panel
- Updating the price when the investment list is empty // it wil throw an exception and print out error message in the message box
- in update panel Trying to click prev or next when it is disabled // it won't do anything
- updating the price to 0 // it will throw an exception asking for price again
- updating the price to negative // it will throw an exception asing for price again
- update the price and keep clicking until it reaches the end of the list

BUY Panel
- click stock in the combo box
- leave all the text field empty // it will throw exception saying, symbol must be non empty
- after filling the symbol text field  click buy again
- it will throw exception for name
- enter characters in the price and quantity, 0 or negative value it will throw exception asking for input again
- now fill it up all the textfields with valid input and buy stock. Buy "TD" stock.
["TD" stock.  'Ena So' '34' '$34.5']

- click mutual stock in the combo box
- fill up the all the textfields with valid input
- buy "CIBC" mutual fund.
["CIBC" mutual fund "Ena" "50" "$300"]

- when they buy the investment it will display it in the message box

Sell panel
- leave the sell panel empty and click sell // it will throw exception and ask for it again
- put invalid inputs in the text field // error message will be displayed in the message box
- Sell the stock that does not exist in the investment list // error message displayed
- Set the quantity or price to negative // error message displayed
- Sell all the ["TD" stock it wil say you sold all your stock

Get Gain panel
- it will display the total get gain in the text field and individual getgain in the bottom message box
- numbers are rounded up to 2 decimal places

Search Panel
- enter symbol that does not exist // it won't display anything in the message box
- leave all the text field empty
- it will display all investment in the list
- search for specific price <enter same price in low and high> // it will display matching investment
- enter key word that exist in the multiple stock  'Ena'// it will print out TD stock and CIBC stock
