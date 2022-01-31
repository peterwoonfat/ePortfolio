Name: Peter Woon-Fat
Student ID: 1048220
Assignment: CIS2430 Assignment 3
Program Files: Portfolio.java, Stock.java, MutualFund.java, Investment.java
Portfolio.java is the file with the main to be run.


Usage:
Compiled Using: "javac -d . *.java"
Run Using: "java eportfolio.Portfolio"


Program Description:
The program mimicks a portfolio with the ability to add/remove/update stocks and mutual funds. A file name to load data from and save data to can be specified in the command line when executing the program (ie. running the program using java eportfolio.Portfolio [filename.txt]).
There is a command loop with the option to sell, buy, update prices, calculate hypothetical gain, and search for specific existing investments.
When buying, the user is prompted to input the investment's type (stock/mutual fund), ticker symbol, name (if new investment), buying quantity, and buying price. The information is then stored in the arraylist of the corresponding type or updated in the object with the corresponding ticker symbol.
When selling, the user is prompted to input the investment's ticker symbol, selling quantity, and selling price. The payment received and the gain is calculated and the investment's information is updated.
When updating prices, the user is prompted for the updated price of each owned investment, updating the object's price.
When calculating the hypothetical gain, the gain of each investment is calculated as if we were to completely sell off all shares with the current quantity and price. Then each investment's gain is summed together to get the total gain across all stocks and mutual funds held, without modifying any information.
When searching for a specific existing investment, the user is prompted to input search criteria for the ticker symbol, keywords for the name, and a price range (all of which are optional). All investments matching the search criteria are printed out - if no search criteria is entered then all investments are printed.
The current iteration of the assignment builds on the backend developed in the previous two assignments, using the java swing and awt packages to create a graphical user interface. The interface is broken into 4 different panels to better group the GUI components. A panel at the top of the window contains the buttons used for choosing the operation the user wants to perform (buy, sell, update, get gain, search). The center panel changes depending on the operation the selected operation as some operations require certain input fields and some do not - the input fields that are not relevant to the operation have their visibility set to off until they are required. The panel on the center right contains the required buttons which similar to the input fields, have their visibility set to off when the selected operation does not require them. Finally, the panel at the bottom contains a JTextArea where feedback is outputted to, whether it be error messages, search results, operation confirmations, etc.


Further Improvement:
To improve efficiency the updateMap method should be changed to modify the existing keywordsMap instead of recreating a new one without the removed investments.
Some condition checks and feedback input displayed through updating the message panel at the bottom of the window, could be moved to methods they pertain to.
GUI components could be better spaced and aligned.


Assumptions/Limitations:
No feedback given when searching when no keywords are given but a symbol nonmatching symbol is given.
It is assumed the file format will be as specified as in the assignment2.pdf example.


Test Plan:
Whether or not a file name was entered at the command line, and if it was if it was handled properly, was tested with 4 cases
    1) a file name was not entered
    2) a file name was entered with valid data
    3) a file name was entered with invalid data
    4) a file name was entered with no data
When taking user input for a command, 3 cases were tested to see if the correct command was carried out or the input loop displayed appropriate feedback and looped again
    1) a valid command was entered
    2) a valid shorthand for a command was entered
    3) an invalid command/shorthand for a command was entered
When buying an investment, 3 cases were tested - whether the correct prompts displayed and information was correctly stored
    1) if the investment is a stock
    2) if the investment is a mutual fund
    3) if neither stock nor mutual fund was entered (invalid input)
When selling an investment, 3 cases were tested - whether the ticker symbol entered matches an existing stock or not and whether the quantity is valid
    1) if ticker symbol entered matches an existing stock and the quantity to sell is greater than existing quantity
    2) if ticker symbol entered matches an existing stock and the quantity to sell is less than or equal to existing quantity
    3) if ticker symbol entered does not match an existing stock
When updating investments, 2 cases were tested to see whether the correct feedback was given
    1) if user wants to update investment prices and the user currently owns investments
    2) if the user wants to update investment prices and the user currently does not own investments
When getting input for search parameters, 9 cases were tested to see if input was correctly handled
    1) tested for varying number of search parameters entered (1, 2, or all 3)
    2) tested for matching ticker symbol entered
    3) tested for non-mathcing ticker symbol entered
    4) tested for matching keywords entered
    5) tested for non-matching keywords entered
    6) tested for price range formatted X.X-
    7) tested for price range formatted -X.X
    8) tested for price range formatted X.X-X.X
    9) tested for price range not following any of the previous 3 formats
When searching for an investment, 5 cases were tested to see if search parameters were properly filtering investments
    1) tested for when no search parameters were entered
    2) tested for when one search parameter was entered
    3) tested for when two search parameters were entered
    4) tested for when there was an investment matching all the search parameters entered
    5) tested for when there was no investment matching all the search parameters entered
When switching between operations (buy, sell, etc) or after finishing an operation, 3 cases were tested if the GUI was properly adjusted
    1) tested for when switching between different operations if the proper JTextFields and their associated JLabels were showing and cleared
    2) tested for after completing an operation, the JTextFields were cleared so the user could perform the operation again
    3) tested for after performing an unsuccessful operation due to an error, the JTextFields were cleared so the user could try again
When performing an operation and required input fields were not filled, 3 cases were tested to see if this would be properly handled by providing a feedback message and letting user try again
    1) tested for when input was given for some but not all required input fields
    2) tested for when no input was given when there were required input fields
    3) tested for when no input was given when there were no required input fields