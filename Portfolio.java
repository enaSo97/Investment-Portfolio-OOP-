package Investment;

import com.sun.deploy.util.SearchPath;
import com.sun.org.apache.xpath.internal.SourceTree;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.*;
import java.lang.String;
import Investment.Investment.customException;
import java.util.zip.CheckedInputStream;


public class Portfolio extends JFrame {
    ArrayList<Investment> Invest = new ArrayList<Investment>();
    HashMap<String, ArrayList<Integer>> index = new HashMap<String, ArrayList<Integer>>();
    Scanner s = new Scanner(System.in);
    Main main = new Main();

    private JFrame optionFrame;
    private JPanel welcome;
    private JMenu menu;
    private JTextArea Message;
    private JPanel Main = new JPanel();
    private JPanel BuyPanel = new JPanel();
    private JPanel Top = new JPanel();
    private JPanel Bottom = new JPanel();
    private JPanel SellPanel = new JPanel();
    private JPanel UpdatePanel = new JPanel();
    private JPanel GetGainPanel = new JPanel();
    private JPanel searchPanel = new JPanel();
    private String SorM = "Stock";
    private int userQuant = 0;
    private double userPrice = 0;
    private double userBook = 0;
    private int globalIndex = 0;
    private JTextArea MessageField = new JTextArea();
    private JTextField displayTotal = new JTextField();
    private JTextField inputSymbol = new JTextField();
    private JTextField inputName = new JTextField();
    private JTextField inputPrice = new JTextField();
    private JButton Prev = new JButton("Prev");
    private JButton Next = new JButton("Next");
    private Investment temp;
    private Stock stockTemp;
    private Mutual mutualTemp;

    /**
     * this method will create GUI for Buying stock
     * user will input symbol, name, quantity, price
     * reset button will empty the text field, buy button will try to buy the stock
     * when stock does not exist it will create new stock to the list
     * when stock already exist, wit will buy more of existing stock and update the quantity, price and bookValue
     * @return BuyPanel for Buy GUI
     */
    public JPanel BuyGui(){
        final JPanel Top = new JPanel();
        final JPanel Bottom = new JPanel();
        BuyPanel.setLayout(new GridLayout(2,1));
        Top.setLayout(new GridLayout(1,2));
        Bottom.setLayout(new BorderLayout());
        final JPanel BigTopLeft = new JPanel(new BorderLayout());
        final JPanel TopLeft = new JPanel(new GridLayout(5,5));
        final JPanel TopRight = new JPanel(new GridLayout(2,1));
        String[] Investments = {"Stock", "Mutual Fund"};
        final JComboBox typeBox = new JComboBox(Investments);
        final JLabel Title = new JLabel("Buying an investment");
        final JLabel Type = new JLabel("Type");
        final JLabel Symbol = new JLabel("Symbol");
        final JLabel Name = new JLabel("Name");
        final JLabel Quantity = new JLabel("Quantity");
        final JLabel Price = new JLabel("Price");
        final JTextField inputSymbol = new JTextField();
        final JTextField inputName = new JTextField();
        final JTextField inputQuantity = new JTextField();
        final JTextField inputPrice = new JTextField();
        final JPanel buttonPanel1 = new JPanel();
        final JPanel buttonPanel2 = new JPanel();
        final JButton Reset = new JButton("Reset");
        final JButton Buy = new JButton("Buy");
        final JLabel Message = new JLabel("Messages");
        JTextArea MessageField = new JTextArea();
        MessageField.setEditable(false);
        final JScrollPane scroll = new JScrollPane(MessageField);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        typeBox.setSelectedIndex(0);

        BigTopLeft.add(Title, BorderLayout.NORTH);
        BigTopLeft.add(TopLeft, BorderLayout.CENTER);
        TopLeft.add(Type);
        TopLeft.add(typeBox);
        TopLeft.add(Symbol);
        TopLeft.add(inputSymbol);
        TopLeft.add(Name);
        TopLeft.add(inputName);
        TopLeft.add(Quantity);
        TopLeft.add(inputQuantity);
        TopLeft.add(Price);
        TopLeft.add(inputPrice);
        buttonPanel1.add(Reset);
        TopRight.add(buttonPanel1);
        buttonPanel2.add(Buy);
        TopRight.add(buttonPanel2);

        typeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(typeBox.getSelectedIndex() == 0){
                    SorM = "Stock";
                }
                else if(typeBox.getSelectedIndex() == 1){
                    SorM = "Mutual";
                }
            }
        });


        Reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputSymbol.setText("");
                inputName.setText("");
                inputQuantity.setText("");
                inputPrice.setText("");
            }
        });
        Buy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SorM.equals("Stock")) {
                    try{
                        userQuant = Integer.parseInt(inputQuantity.getText());
                    }
                    catch(NumberFormatException t){
                    }
                    try{
                        userPrice = Double.parseDouble(inputPrice.getText());
                    }
                    catch(NumberFormatException t){
                    }
                    int j = checkExist(inputSymbol.getText());
                    if (j != -1) {
                        MessageField.append("Stock already exist in your portfolio, Buying more of existing stock\n");
                        MessageField.append(addToExistingStock(inputSymbol.getText(), inputName.getText(), userQuant, userPrice, j));
                        MessageField.append(Invest.get(j).toString());
                    } else {
                        MessageField.append(createNewStock(inputSymbol.getText(), inputName.getText(), userQuant, userPrice));
                        if(Invest.size() > 0) {
                            MessageField.append(Invest.get(Invest.size() - 1).toString());
                        }
                    }
                } else if (SorM.equals("Mutual")) {
                    userQuant = Integer.parseInt(inputQuantity.getText());
                    userPrice = Double.parseDouble(inputPrice.getText());

                    int j = checkExist(inputSymbol.getText());
                    if (j != -1) {
                        MessageField.append("Mutual fund already exist in your portfolio, Buying more of existing Mutual fund\n");
                        MessageField.append(addToExistingMutual(inputSymbol.getText(), inputName.getText(), userQuant, userPrice, j));
                        MessageField.append(Invest.get(j).toString());

                    } else {
                        MessageField.append(createNewMutual(inputSymbol.getText(), inputName.getText(), userQuant, userPrice));
                        if(Invest.size() > 0) {
                            MessageField.append(Invest.get(Invest.size() - 1).toString());
                        }
                    }
                }
            }
        });

        Top.add(BigTopLeft);
        Top.add(TopRight);

        Bottom.add(Message, BorderLayout.NORTH);
        Bottom.add(scroll);

        BuyPanel.add(Top);
        BuyPanel.add(Bottom);
        BuyPanel.setVisible(true);

        return BuyPanel;
    }

    /**
     * this method will create GUI for Selling a stock
     * user enter symbol, quantity, and price of the stock they want to to sell
     * clicking sell button will sell the price and clicking reset button will reset the text field
     * @return Sell panel for sell GUI
     */
    public JPanel SellGUi(){
        final JPanel Top = new JPanel();
        final JPanel Bottom = new JPanel();
        SellPanel.setLayout(new GridLayout(2,1));
        Top.setLayout(new GridLayout(1,2));
        Bottom.setLayout(new BorderLayout());
        final JPanel BigTopLeft = new JPanel(new BorderLayout());
        final JPanel TopLeft = new JPanel(new GridLayout(5,5));
        final JPanel TopRight = new JPanel(new GridLayout(2,1));
        final JLabel Title = new JLabel("Selling an investment");
        final JLabel Symbol = new JLabel("Symbol");
        final JLabel Quantity = new JLabel("Quantity");
        final JLabel Price = new JLabel("Price");
        final JTextField inputSymbol = new JTextField();
        final JTextField inputQuantity = new JTextField();
        final JTextField inputPrice = new JTextField();
        final JPanel buttonPanel1 = new JPanel();
        final JPanel buttonPanel2 = new JPanel();
        final JButton Reset = new JButton("Reset");
        final JButton Sell = new JButton("Sell");
        final JLabel Message = new JLabel("Messages");
        JTextArea MessageField = new JTextArea();
        MessageField.setEditable(false);
        final JScrollPane scroll = new JScrollPane(MessageField);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        BigTopLeft.add(Title, BorderLayout.NORTH);
        BigTopLeft.add(TopLeft, BorderLayout.CENTER);
        TopLeft.add(Symbol);
        TopLeft.add(inputSymbol);
        TopLeft.add(Quantity);
        TopLeft.add(inputQuantity);
        TopLeft.add(Price);
        TopLeft.add(inputPrice);
        buttonPanel1.add(Reset);
        TopRight.add(buttonPanel1);
        buttonPanel2.add(Sell);
        TopRight.add(buttonPanel2);
        Reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputSymbol.setText("");
                inputQuantity.setText("");
                inputPrice.setText("");
            }
        });

        Sell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int flag;
                int quantity = 0;
                double price = 0;
                if (!inputQuantity.getText().isEmpty()) {
                    try {
                        quantity = Integer.parseInt(inputQuantity.getText());
                    }catch(NumberFormatException t){
                    }
                }
                if (!inputPrice.getText().isEmpty()) {
                    try {
                        price = Double.parseDouble(inputPrice.getText());
                    }catch(NumberFormatException t){
                    }
                }

                if (!inputSymbol.getText().isEmpty()) {
                    int index = checkExist(inputSymbol.getText());
                    if (index != -1) {
                        int origin = Invest.get(index).getQuantity();
                        int diff = origin - quantity;
                        if (diff < 0) {
                            MessageField.append("You are trying to sell more than you have, enter quantity again\n");
                        } else if (diff >= 0) {
                            try {
                                sellInvestment(inputSymbol.getText(), quantity, price);
                                if (diff == 0) {
                                    MessageField.append("You sold all your stock for " + inputSymbol.getText() + " stock\n");
                                }else{
                                    MessageField.append("Successfully sold!\n");
                                    MessageField.append(Invest.get(index).toString());
                                }
                            } catch (customException t) {
                                MessageField.append(t.getMessage());
                            }
                        }
                    }else{
                        MessageField.append("Stock not found in the portfolio\n");
                    }
                }
                else{
                    MessageField.append("You cannot leave Symbol empty, enter something\n");
                }
            }

        });

        Top.add(BigTopLeft);
        Top.add(TopRight);

        Bottom.add(Message, BorderLayout.NORTH);
        Bottom.add(scroll);

        SellPanel.add(Top);
        SellPanel.add(Bottom);
        SellPanel.setVisible(true);

        return SellPanel;
    }

    /**
     *This method creates panel for Update GUI
     * it will allow user to go through all the stock they bought and update the price
     * when they press prev it will go to the previous stock
     * when they press next it will go to the next stock
     * when they press save, it will update the price
     * @return Panel for update GUI
     */
    public JPanel UpdateGui(){
        final JPanel Top = new JPanel();
        final JPanel Bottom = new JPanel();
        UpdatePanel.setLayout(new GridLayout(2,1));
        Top.setLayout(new GridLayout(1,2));
        Bottom.setLayout(new BorderLayout());
        final JPanel BigTopLeft = new JPanel(new BorderLayout());
        final JPanel TopLeft = new JPanel(new GridLayout(5,5));
        final JPanel TopRight = new JPanel(new GridLayout(3,1));
        final JLabel Title = new JLabel("Updating an investment");
        final JLabel Symbol = new JLabel("Symbol");
        final JLabel Name = new JLabel("Name");
        final JLabel Price = new JLabel("Price");
        inputSymbol.setEditable(false);
        inputName.setEditable(false);
        final JPanel buttonPanel1 = new JPanel();
        final JPanel buttonPanel2 = new JPanel();
        final JPanel buttonPanel3  = new JPanel();
        final JButton Save = new JButton("Save");
        final JLabel Message = new JLabel("Messages");
        JTextArea MessageField = new JTextArea();
        MessageField.setEditable(false);
        final JScrollPane scroll = new JScrollPane(MessageField);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        BigTopLeft.add(Title, BorderLayout.NORTH);
        BigTopLeft.add(TopLeft, BorderLayout.CENTER);
        TopLeft.add(Symbol);
        TopLeft.add(inputSymbol);
        TopLeft.add(Name);
        TopLeft.add(inputName);
        TopLeft.add(Price);
        TopLeft.add(inputPrice);
        buttonPanel1.add(Prev);
        TopRight.add(buttonPanel1);
        buttonPanel2.add(Next);
        TopRight.add(buttonPanel2);
        buttonPanel3.add(Save);
        TopRight.add(buttonPanel3);
            Prev.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Next.setEnabled(true);
                    globalIndex--;
                    if (globalIndex == 0) {
                        Prev.setEnabled(false);
                    }
                    inputSymbol.setText(Invest.get(globalIndex).getSymbol());
                    inputName.setText(Invest.get(globalIndex).getName());
                }
            });
            Next.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Prev.setEnabled(true);
                    globalIndex++;
                    if (globalIndex == Invest.size() - 1) {
                        Next.setEnabled(false);
                    }
                    inputSymbol.setText(Invest.get(globalIndex).getSymbol());
                    inputName.setText(Invest.get(globalIndex).getName());
                    String toText = Double.toString(Invest.get(globalIndex).getPrice());
                    inputPrice.setText(toText);
                }
            });
        Save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Invest.size() > 0) {
                    int index = checkExist(inputSymbol.getText());
                    if (inputPrice.getText().equals("0")) {
                        MessageField.append("You cannot update price to 0\n");
                    } else if (inputPrice.getText().isEmpty()) {
                        MessageField.append("You cannot leave the price empty\n");
                    } else {
                        try {
                            userPrice = Double.parseDouble(inputPrice.getText());
                            Invest.get(index).setPrice(userPrice);
                            MessageField.append(Invest.get(index).toString());
                        } catch (NumberFormatException t) {
                            MessageField.append("Must enter number, try again\n");
                        }
                    }
                }else{
                    MessageField.append("You cannot save price when portfolio is empty\n");
                }
            }
        });

        Top.add(BigTopLeft);
        Top.add(TopRight);

        Bottom.add(Message, BorderLayout.NORTH);
        Bottom.add(scroll);

        UpdatePanel.add(Top);
        UpdatePanel.add(Bottom);
        UpdatePanel.setVisible(true);

        return UpdatePanel;
    }

    /**
     * this method will create GUI for Get Gain.
     * it wil calculate all the individual gain of the investment and display them in the text area at the bottom
     * also it will calculate the total get gain of the investment and display it in the text field!
     * @return panel for Get Gain GUI
     */
    public JPanel GetGainGui(){
        final JPanel Top = new JPanel();
        final JPanel Bottom = new JPanel();
        GetGainPanel.setLayout(new GridLayout(2,1));
        Top.setLayout(new GridLayout(1,2));
        Bottom.setLayout(new BorderLayout());
        final JPanel BigTopLeft = new JPanel(new BorderLayout());
        final JPanel TopLeft = new JPanel(new GridLayout(1,1));
        final JPanel TopRight = new JPanel(new GridLayout(2,1));
        final JLabel Title = new JLabel("Getting total gain");
        final JLabel GetGain = new JLabel("       Total gain");
        displayTotal.setEditable(false);
        displayTotal.setSize(30,5);
        final JLabel Message = new JLabel("Individual gain");
        MessageField.setEditable(false);
        final JScrollPane scroll = new JScrollPane(MessageField);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        BigTopLeft.add(Title, BorderLayout.NORTH);
        BigTopLeft.add(TopLeft, BorderLayout.CENTER);
        TopLeft.add(GetGain);
        TopLeft.add(displayTotal);

        Top.add(BigTopLeft);
        Top.add(TopRight);

        Bottom.add(Message, BorderLayout.NORTH);
        Bottom.add(scroll);

        GetGainPanel.add(Top);
        GetGainPanel.add(Bottom);
        GetGainPanel.setVisible(true);

        return GetGainPanel;
    }

    /**
     * this method will create GUI for search.
     * user will put input in the text field then will press buttons.
     * when reset button is clicked it will clear the text field
     * when search button is clicked then it will call all the search functions and it will search for the investment corrensponding to user input
     * it will print out all the matching investments
     * @return panel for search gui
     */
    public JPanel SearchGui(){
        final JPanel Top = new JPanel();
        final JPanel Bottom = new JPanel();
        searchPanel.setLayout(new GridLayout(2,1));
        Top.setLayout(new GridLayout(1,2));
        Bottom.setLayout(new BorderLayout());
        final JPanel BigTopLeft = new JPanel(new BorderLayout());
        final JPanel TopLeft = new JPanel(new GridLayout(4,4));
        final JPanel TopRight = new JPanel(new GridLayout(2,1));
        final JLabel Title = new JLabel("Searching an investment");
        final JLabel Symbol = new JLabel("Symbol");
        final JLabel keyWord = new JLabel("Name\nKeyWords");
        final JLabel LPrice = new JLabel("Low Price");
        final JLabel HPrice = new JLabel("High Price");
        final JTextField inputSymbol = new JTextField();
        final JTextField inputKeyWord = new JTextField();
        final JTextField inputLowPrice = new JTextField();
        final JTextField inputHighPrice = new JTextField();
        final JPanel buttonPanel1 = new JPanel();
        final JPanel buttonPanel2 = new JPanel();
        final JButton Reset = new JButton("Reset");
        final JButton Search = new JButton("Search");
        final JLabel Message = new JLabel("Search Results");
        JTextArea MessageField = new JTextArea();
        MessageField.setEditable(false);
        final JScrollPane scroll = new JScrollPane(MessageField);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        BigTopLeft.add(Title, BorderLayout.NORTH);
        BigTopLeft.add(TopLeft, BorderLayout.CENTER);
        TopLeft.add(Symbol);
        TopLeft.add(inputSymbol);
        TopLeft.add(keyWord);
        TopLeft.add(inputKeyWord);
        TopLeft.add(LPrice);
        TopLeft.add(inputLowPrice);
        TopLeft.add(HPrice);
        TopLeft.add(inputHighPrice);
        buttonPanel1.add(Reset);
        TopRight.add(buttonPanel1);
        buttonPanel2.add(Search);
        TopRight.add(buttonPanel2);

        Reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputSymbol.setText("");
                inputKeyWord.setText("");
                inputLowPrice.setText("");
                inputHighPrice.setText("");
            }
        });
        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int set = 0;
                if (inputSymbol.getText().length() == 0 && inputKeyWord.getText().length() == 0 && inputHighPrice.getText().length() == 0 && inputLowPrice.getText().length()==0){
                    MessageField.append(Invest.toString());
                }
                else {
                    for (int i = 0; i < Invest.size(); i++) {
                        int Sym = forStockSymbol(inputSymbol.getText(), i);
                        int key = forStockKeyWord(inputKeyWord.getText(), i);
                        double LowPrice = 0;
                        double HighPrice = 0;
                        if (!inputHighPrice.getText().isEmpty() && !inputLowPrice.getText().isEmpty()) {
                            try {
                                LowPrice = Double.parseDouble(inputLowPrice.getText());
                            } catch (NumberFormatException w) {
                                MessageField.setText("You must enter Number, try again\n");
                            }
                            try {
                                HighPrice = Double.parseDouble(inputHighPrice.getText());
                            } catch (NumberFormatException w) {
                                MessageField.setText("You must enter Number, try again\n");
                            }
                        }
                        if (LowPrice > HighPrice) {
                            MessageField.append("Low price cannot be greater than High Price, enter again");
                        } else {
                            int range = forStockRange(LowPrice, HighPrice, i);

                            if (Sym == 1 && key == 1 && range == 1) {
                                MessageField.append(Invest.get(i).toString());
                            } else if (Sym == 0 && key == 1 && range == 1) {
                                MessageField.append(Invest.get(i).toString());
                            } else if (Sym == 1 && key == 1 && range == 0) {
                                MessageField.append(Invest.get(i).toString());
                            } else if (Sym == 1 && key == 0 && range == 1) {
                                MessageField.append(Invest.get(i).toString());
                            } else if (Sym == 1 && inputKeyWord.getText().isEmpty() && inputHighPrice.getText().isEmpty() && inputLowPrice.getText().isEmpty()) {
                                MessageField.append(Invest.get(i).toString());
                            } else if (inputSymbol.getText().isEmpty() && key == 1 && inputHighPrice.getText().isEmpty() && inputLowPrice.getText().isEmpty()) {
                                MessageField.append(Invest.get(i).toString());
                            } else if (inputSymbol.getText().isEmpty() && inputKeyWord.getText().isEmpty() && range == 1) {
                                MessageField.append(Invest.get(i).toString());
                            }
                        }
                    }
                }
            }
        });

        Top.add(BigTopLeft);
        Top.add(TopRight);

        Bottom.add(Message, BorderLayout.NORTH);
        Bottom.add(scroll);

        searchPanel.add(Top);
        searchPanel.add(Bottom);
        searchPanel.setVisible(true);

        return searchPanel;
    }

    /**
     * this run method will create JFrame for the program simulation.
     * it will use cardlayout to create multiple panels to display in one JFRame depending on the users choice of menutiem
     * adds welcome panel, buy panel, sell panel, update panel, getgain panel, and search panel to the main Panel and add toJFrame
     * it will show corresponding panel depending on the user choice.
     */
    public void run() {
        CardLayout c1 = new CardLayout();
        Main.setLayout(c1);
        optionFrame = new JFrame("Investment portfolio");
        optionFrame.setSize(500,400);
        optionFrame.setLayout(new CardLayout());

        optionFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fileSave(main.getFileName());
                System.exit(0);
            }
        });

        MenuBar menu = new MenuBar();
        JMenu choose = new JMenu("Commands");
        JMenuItem BuyMenu = new JMenuItem("Buy");
        JMenuItem SellMenu = new JMenuItem("Sell");
        JMenuItem UpdateMenu = new JMenuItem("Update");
        JMenuItem getGainMenu = new JMenuItem("Get Gain");
        JMenuItem SearchMenu = new JMenuItem("Search");
        JMenuItem QuitMenu = new JMenuItem("Quit");
        choose.add(BuyMenu);
        choose.add(SellMenu);
        choose.add(UpdateMenu);
        choose.add(getGainMenu);
        choose.add(SearchMenu);
        choose.add(QuitMenu);

        menu.add(choose);
        optionFrame.setJMenuBar(menu);

        JPanel msg = new JPanel();
        add(msg, BorderLayout.CENTER);
        Message = new JTextArea();
        Message.setLineWrap(true);
        Message.setWrapStyleWord(true);
        Message.setText("\n\nWelcome to Investment Portfolio.\n\n\nChoose a command form the 'commands' menu to buy or sell an investment, update prices for all investments, " +
                "get gain for the portfolio, search for relevant investment, or quit the program");
        Message.setEditable(false);
        add(Message, BorderLayout.SOUTH);
        optionFrame.add(Main);

        optionFrame.setVisible(true);

        Main.add(Message, "Main");
        BuyPanel = BuyGui();
        Main.add(BuyPanel,"Buy");
        SellPanel = SellGUi();
        Main.add(SellPanel, "Sell");
        UpdatePanel = UpdateGui();
        Main.add(UpdatePanel, "Update");
        GetGainPanel = GetGainGui();
        Main.add(GetGainPanel, "GetGain");
        searchPanel = SearchGui();
        Main.add(searchPanel, "Search");

        c1.show(Main,"Main");
        Main.setVisible(true);

        BuyMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                c1.show(Main, "Buy");
            }
        });
        SellMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c1.show(Main, "Sell");
            }
        });
        UpdateMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Invest.size() > 0){
                    inputSymbol.setText(Invest.get(0).getSymbol());
                    inputName.setText(Invest.get(0).getName());
                    String toText = Double.toString(Invest.get(0).getPrice());
                    inputPrice.setText(toText);
                }
                globalIndex = checkExist(inputSymbol.getText());
                if (globalIndex == 0) {
                    Prev.setEnabled(false);
                    if (Invest.size() == 1){
                        Next.setEnabled(false);
                    }
                    if (Invest.size() > 1){
                        Next.setEnabled(true);
                    }
                }if (globalIndex == -1){
                    Prev.setEnabled(false);
                    Next.setEnabled(false);
                }
                c1.show(Main, "Update");
            }
        });
        getGainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double getGain = 0;
                String individual = "";
                for (int i = 0; i < Invest.size(); i++) {
                    getGain = getGain +  (Invest.get(i)).computeGetGain(Invest.get(i).getQuantity(), Invest.get(i).getPrice(), Invest.get(i).getBookValue());
                    double one = Invest.get(i).computeGetGain(Invest.get(i).getQuantity(), Invest.get(i).getPrice(), Invest.get(i).getBookValue());
                    getGain = Math.ceil(getGain * 100)/100;
                    one = Math.ceil(one * 100)/100;
                    individual = Double.toString(one);
                    MessageField.append("Individual gain for " + Invest.get(i).getSymbol() + ": " +individual + "\n");
                }
                String toText = Double.toString(getGain);
                displayTotal.setText(toText);
                c1.show(Main, "GetGain");
            }
        });
        SearchMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c1.show(Main, "Search");
            }
        });
        QuitMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileSave(main.getFileName());
                System.exit(0);
            }
        });

    }
    /**
     * Method to create a new stoch when there is no stock that matches user input's symbol
     *
     * @param symbol   Symbol of stock that user is trying to buy
     * @param name     name of stock that user is trying to buy
     * @param quantity quantity of stock that user is trying to buy
     * @param price    price of stock that user is tyring to buy
     */
    public String createNewStock(String symbol, String name, int quantity, double price) {
        String error = "";
        try {
            Stock addStock = new Stock(symbol, name, quantity, price, 0.0);
            double bookValue = addStock.computeBookValue(quantity, price);
            addStock.setBookValue(bookValue);
            Invest.add(addStock);
            Hashing(Invest.get(Invest.size() - 1));
            System.out.println("Successfully created new stock\n");
        }catch(Investment.customException e){
            error = error + e.getMessage();
        }
        return error;
    }

    /**
     * Mehtod to create a new mutual fund when there is no mutual fund that matches user input's symbol
     *
     * @param symbol   Symbol of mutual fund that user is trying to buy
     * @param name     name of mutual that user is trying to buy
     * @param quantity quantity of mutual fund that user is trying to buy
     * @param price    price of mutual fund th a user is trying to buy
     */
    public String createNewMutual(String symbol, String name, int quantity, double price) {
        String error = "";
        try {
            Mutual addMutual = new Mutual(symbol, name, quantity, price, 0.0);
            double bookValue = addMutual.computeBookValue(quantity, price);
            addMutual.setBookValue(bookValue);
            Invest.add(addMutual);
            Hashing(Invest.get(Invest.size() - 1));
        }catch(Investment.customException e){
            error = error + e.getMessage();
        }
        return error;
    }

    /**
     * Method to update the stock when user is trying to buy a stock that already exist
     *
     * @param symbol   symbol of the stock
     * @param name     name of the stock
     * @param quantity quantity of  the stock
     * @param price    price of the stock
     * @param i        i is the index of the stock that already exist and getting updated
     */
    public String addToExistingStock(String symbol, String name, int quantity, double price, int i) {
        String error = "";
        try {
            Stock newStock = new Stock(symbol, name, quantity, price, 0.0);
            double newBookValue = newStock.computeBookValue(quantity, price);
            newStock.setQuantity(quantity + Invest.get(i).getQuantity());
            newStock.setPrice(price);
            newStock.setBookValue(newBookValue);
            Invest.set(i, newStock);
            Hashing(Invest.get(Invest.size() - 1));
            System.out.println(i);
        }catch(Investment.customException e){
            error = error + e.getMessage();
        }
        return error;
    }

    /**
     * Method to update the mutual fund when user is trying to buy a stock that already exist
     *
     * @param symbol   symbol of the mutual fund
     * @param name     name of the mutual fund
     * @param quantity quantity of  the mutual fund
     * @param price    price of the stock mutual fund
     * @param i        i is the index of the mutual fund that already exist and getting updated
     */
    public String addToExistingMutual(String symbol, String name, int quantity, double price, int i) {
        String error = "";
        try {
            Mutual newMutual = new Mutual(symbol, name, quantity, price, 0.0);
            double newBookValue = newMutual.computeBookValue(quantity, price);
            newMutual.setBookValue(newBookValue);
            Invest.set(i, newMutual);
            Hashing(Invest.get(Invest.size() - 1));
            System.out.println(i);
        }catch(Investment.customException e){
            error = error + e.getMessage();
        }
        return error;
    }

    /**
     * Method to check if symbol of mutual fund or stock that user is entering already exists in the portfolio
     *
     //* @param comp mutual fund or stock that user is trying to buy or sell
     * @return returns index of the mutual fund that exist or return 100 if there is none
     */
    public int checkExist(String symbol) {
        for (int i = 0; i < Invest.size(); i++) {
                String currentSymbol = Invest.get(i).getSymbol();
                if (symbol.equalsIgnoreCase(currentSymbol)) {
                    return i;
                }
        }
        return -1;
    }

    /**
     * this method will sell the investment that user choose and remove form the investment arrays list if they sold all of there stock or mutual dund
     * @param Symbol the symbol of investment that user choose to sel
     * @param quantity the quantity of the investment that user choose to sell
     * @param userPrice the price of the investment that user wants to sell
     * @return sold investment that user has chosen
     */
    public void sellInvestment (String Symbol, int quantity, double userPrice) throws customException{
        if (Symbol.equals("")){
            throw new customException("Symbol must be non empty.\n");
        }
        else if (quantity <= 0){
            throw new customException("Quantity must be non empty or greater than 0\n");
        }
        else if (userPrice <= 0){
            throw new customException("Price must be non empty or greater than 0\n");
        }
        if (checkExist(Symbol) != -1) {
                for (int i = 0; i < Invest.size(); i++) {
                        if (Invest.get(i).getSymbol().equalsIgnoreCase(Symbol)) {
                            int origin = Invest.get(i).getQuantity();
                            int diff = origin - quantity;
                            double originBook = Invest.get(i).getBookValue();
                            double newBookValue = Invest.get(i).computePayment(quantity, origin, originBook);
                            Invest.get(i).setQuantity(diff);
                            Invest.get(i).setPrice(userPrice);
                            Invest.get(i).setBookValue(newBookValue);
                            if (diff == 0) {
                                Invest.remove(i);
                                HashRemove();
                            }
                    }
                }
            }
    }
    /**
     *  Method to check if the symbol that user entered matches the symbol of the stock that is in the list
     * @param symbol symbol user entered
     * @param index index of the stock that is getting checked
     * @return returns 1 if it matches 0 if it does not
     */
    public int forStockSymbol(String symbol, int index) {
        if (Invest.get(index).getSymbol().equalsIgnoreCase(symbol)) {
            return 1;
        }
        else if (symbol.length() == 0) {
            return 0;
        }
        return 0;
    }
    /**
     * MEthod to check if the keyword user entered is a substring of the stock that is in the list
     * @param keyWord keyworkd user entered
     * @return returns 1 if it is a substring 0 if it is not
     */
    public int forStockKeyWord(String keyWord, int temp) {
        int h = 0;
        int n = 0;
        int dum = 0;
        String delim = " ";
        String[] dummy = keyWord.split(delim);
        String[] compare = Invest.get(temp).getName().split(delim);
        Set<HashMap.Entry<String, ArrayList<Integer>>> entry = index.entrySet();
        Iterator<HashMap.Entry<String, ArrayList<Integer>>> it = entry.iterator();
        while(it.hasNext()){
            HashMap.Entry<String, ArrayList<Integer>> enter = it.next();
            if (enter.getValue().equals(temp)){
                compare[n] = enter.getKey();
                n++;
            }
        }
        dum = dummy.length;

        if (dummy.length > compare.length){
            dum = compare.length;
            return 0;
        }
        else{
            for (int i = 0; i < dummy.length; i++) {
                for (int j = 0; j < compare.length; j++) {
                    if (dummy[i].equalsIgnoreCase(compare[j])) {
                        h++;
                    }
                }
            }
        }
        if(h >= dum){
            return 1;
        }
        return 0;
    }
    /**
     *Method to check if the price range or price that user entered mathces the price of the stock
     //* @param priceRange price range or specific price that they want to search for
     * @param index indes of stock that is getting checked
     * @return returns 1 if there is the match 0 if there is not
     */
    public int forStockRange(double low, double high, int index) {
        double price = 0;
            if (low == high) {
                price = high;
                if (Invest.get(index).getPrice() == price) {
                    return 1;
                }
            }
            else {
                if (Invest.get(index).getPrice() >= low && Invest.get(index).getPrice() <= high) {
                    return 1;
                }
            }
        return 0;
    }

    /**
     * this method will create a file and write all the stocks and mutual fund in to the file
     * @param file this is the file name that will be created as
     */
    public void fileSave(String file){
        BufferedWriter writer;
        try{
            writer = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < Invest.size(); i++){
                writer.write(Invest.get(i).toFile());
                writer.newLine();
            }
            writer.close();
        }catch(IOException e){
            System.out.println("Failed to write to" + file);
        }
    }

    /**
     * this method will read in the file and save all the stock and mutual fund in to the investment array list.
     * @param file file name that is going to be read and added in to the investment array list
     */
    public void fileLoad(String file){
        double price = 0;
        int quantity = 0;
        double book = 0;
        String error = "";
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while(line != null){
                String[] array = line.split("#");
                if (array[0].equals("S")){
                    quantity = Integer.parseInt(array[3]);
                    price = Double.parseDouble(array[4]);
                    book = Double.parseDouble(array[5]);
                    try {
                        Stock newStock = new Stock(array[1], array[2], quantity, price, book);
                        Invest.add(newStock);
                        Hashing(Invest.get(Invest.size() - 1));
                    }catch(Investment.customException e){
                        error = e.getMessage();
                    }
                }
                else if (array[0].equals("M")){
                    quantity = Integer.parseInt(array[3]);
                    price = Double.parseDouble(array[4]);
                    book = Double.parseDouble(array[5]);
                    try {
                        Mutual newMutual = new Mutual(array[1], array[2], quantity, price, book);
                        Invest.add(newMutual);
                        Hashing(Invest.get(Invest.size() - 1));
                    }catch(Investment.customException e){
                        error = e.getMessage();
                    }
                }
                line = reader.readLine();
            }
            System.out.println("Printing loaded Books from the inventory");
            System.out.println(Invest.toString());
            System.out.println("-----------------------------------------");

        }catch(IOException e){
            System.out.println("Failed to read the file");
        }
    }

    /**
     * this method will take in a investment hash the name of the investment and put it in to the hashmap
     * @param temp investment that is going to be hashed
     */
    public void Hashing(Investment temp) {
        String[] map = temp.getName().split(" ");
        for (int i = 0; i < map.length; i++) {
            if (index.containsKey(map[i])) {
                index.get(map[i]).add(Invest.size() - 1);
            } else {
                    ArrayList<Integer> variable = new ArrayList<Integer>();
                    variable.add(Invest.size() - 1);
                    index.put(map[i], variable);
            }
        }
    }

    /**
     * this method wil remove the investment that is removed from the array list from the hashMap
     */
    public void HashRemove(){
        String[] keys;
        index.clear();
        for (int i = 0; i < Invest.size(); i++){
            keys = Invest.get(i).getName().split(" ");
            for (int n = 0; n < keys.length; n++) {
                if (index.containsKey(keys[n])) {
                    index.get(keys[n]).add(Invest.size());//if not size + 1
                } else {
                    for (int j = 0; j < keys.length; j++) {
                        ArrayList<Integer> variable = new ArrayList<Integer>();
                        variable.add(i);
                        index.put(keys[j], variable);
                    }
                }
            }
        }
    }

    /**
     * this class is made to create custom exceptions taking in the error messages
     */
    public class customException extends Exception{
        public customException(String error){
            super(error);
        }
    }
    }