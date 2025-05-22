import javax.swing.*;
//Imports all classes from the Swing package

import java.awt.*;
//Imports all classes from the Abstract Window Toolkit package, providing basic GUI components

import java.awt.event.*;  // Provides event handling classes and interfaces, such as KeyListener

import java.util.ArrayList;  // Imports the ArrayList class from the java.util package.
//ArrayList is a resizable array, part of Java's Collection Framework, used to store a list of objects dynamically.


/*
 * A full-featured calculator using Java Swing.
 * Features: basic operations, decimal, delete, clear, and calculation history.
 */


public class CalculatorApp extends JFrame implements ActionListener{
    //extends JFrame: Inherits from JFrame, making this class a window that can display a graphical user interface.
    //implements ActionListener: The class agrees to handle action events (like button clicks) using the actionPerformed() method.


    JTextField displayField;
    //A single-line text field to display numbers and results. Users don't type into it directly—it's updated by button presses.

    JTextArea historyArea;
    //A multi-line text area used to display the history of calculations.


    JScrollPane historyScroll;
    //A scrollable container for historyArea, allowing the user to scroll through long histories.

    ArrayList<String> history = new ArrayList<>();
    //Stores a list of previous calculations (e.g., "2 + 2 = 4"). It's dynamic, so you can add/remove items anytime.


    JButton[] numberButtons = new JButton[10];
    //An array of buttons for digits 0 through 9.

    JButton addButton, subButton, mulButton, divButton;
    //Arithmetic Operator Buttons

    JButton decButton, equButton, delButton, clrButton;
    //Decimal, Result equal, Delete, Clear buttons respectively

    JButton clearHistoryButton;
    //Button that clears the calculation history.

    JPanel buttonPanel, historyPanel;
    //buttonPanel holds calculator buttons in grid layout
    //historyPanel for organizing history display

    Font font = new Font("Arial", Font.BOLD, 18);
    //Create font object to define font family & style and size of text in UI components

    double num1 = 0, num2 = 0, result = 0;
    // Variables for calculation, 1st and 2nd number, then result applied

    char operator;
    //store arithmetic operation chosen by user

    public CalculatorApp() {
       // Constructor of CalculatorApp class
        //Automatically called when you create a new instance of the calculator using new CalculatorApp();.

        setTitle("Calculator");
        //Set title of window appearing the window's title bar

        setSize(500, 600);
        //Sets the size of the window (width × height in pixels).

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Specifies what should happen when the user clicks the X (close) button on the window.

        setLayout(null);
        //Disables the default layout manager by setting it to null;
        // You must manually set the position and size of every UI component using setBounds(x, y, width, height).

        setResizable(false);
        //Prevents the user from resizing the window.

        //Display field Setup
        displayField = new JTextField();
        //Creates a new text field (JTextField) which is used to show the current number being typed or the result of a calculation.

        displayField.setBounds(30, 20, 430, 50);
        //Sets the position and size of the text field manually (since layout is null).

        displayField.setFont(font);
        //Applies the Font object defined earlier (Arial, Bold, 18pt) to make the text large and clear.

        displayField.setEditable(false);
        //Makes the text field read-only;Only button presses (e.g. "1", "+") can change the content, no typing extra content

        add(displayField);
        //Adds the displayField to the calculator window (JFrame) so it becomes visible when the app runs.Adds the displayField to the calculator window (JFrame) so it becomes visible when the app runs.

        // Initialize buttons
        initButtons(); // Method; Initializes all calculator buttons and their actions

        // Button panel (calculator UI)
        buttonPanel = new JPanel();
        // Creates a container to hold all the buttons


        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        //Organizes buttons in a 5×4 grid with spacing; 5 rows, 4 columns, 10 px horizontal & vertical spacing between buttons

        buttonPanel.setBounds(30, 90, 430, 250);
        //Places and sizes the button panel in the window

        // Add buttons to the panel
        addButtonsToPanel();
        //Adds each button to the grid layout in order

        add(buttonPanel);
        //Displays the panel in the main calculator window

        // History Area
        historyArea = new JTextArea();
        //Creates a JTextArea, a multi-line text box; Used here to display previous calculations

        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        //Sets the font of the history text.

        historyArea.setEditable(false);
        //Prevents the user from typing or changing the history manually.

        historyScroll = new JScrollPane(historyArea);
        //Wraps the JTextArea in a JScrollPane; adds scrollbars if the history gets too long to fit.

        historyScroll.setBounds(30, 350, 430, 140);
        //Positions and sizes the scrollable history panel: x = 30, y = 350 → position on the screen;width = 430, height = 140 → size of the scrollable box

        add(historyScroll);
        //Adds the scrollable history area to the window so it becomes visible.

        // Clear History Button
        clearHistoryButton = new JButton("Clear History");
        //Creates a button labeled “Clear History”.

        clearHistoryButton.setBounds(180, 500, 140, 30);
        //Positions the button on the screen below the history box.

        clearHistoryButton.setFont(new Font("Arial", Font.PLAIN, 14));
        //Sets the text style for the button label.


        //BELOW: Adds a click event for the button.
        clearHistoryButton.addActionListener(e -> {
            history.clear();  //removes all past calculations from the ArrayList<String> history.
            updateHistoryDisplay(); //updates the JTextArea to reflect the cleared history.
        });
        add(clearHistoryButton);//Adds the “Clear History” button to the window.

        setVisible(true);
        //Shows the entire calculator window; must be called at the end of the constructor for the window to appear on screen.

    }

    /*
     * Initializes all calculator buttons and assigns event listeners.
     */


    //BELOW: Declares a private method called initButtons; set up the calculator buttons, including digits and operators.
    private void initButtons() {
        for (int i = 0; i <= 9; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            //Creates a new button with the label equal to the digit i
            //String.valueOf(i) converts the integer to a string — e.g., 1 becomes "1".

            numberButtons[i].addActionListener (this);
            //Adds an action listener to each button.
            //this refers to the current object (an instance of CalculatorApp), which implements ActionListener.

            numberButtons[i].setFont(font);
            //Sets the font style and size for each button using the font object:
        }


        //BELOW: Initialize operator and function buttons
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("X");  // delete last char
        clrButton = new JButton("C");  // clear field


        //BELOW: Grouping items into array
        /*
        Useful for iterating through them later to set common properties, like:
           Adding action listeners
           Setting the font
         */
        JButton[] opButtons = {
                addButton, subButton, mulButton, divButton,
                decButton, equButton, delButton, clrButton
        };

        //BELOW: Iterates over every JButton in the array opButtons.
        for (JButton btn : opButtons) {
            btn.addActionListener(this);
            //Adds an action listener to the button so it responds to user clicks.
            //this refers to the current CalculatorApp object, which implements ActionListener.

            btn.setFont(font);
            //Sets the font style and size of the button's text using the predefined font:

        }
    }

    /*
     * Adds calculator buttons in order to the panel.
     */


    private void addButtonsToPanel()
    //Its job is to add buttons (numbers and operations) to the calculator’s button grid (buttonPanel).
    // arranging and adding all the calculator buttons to the buttonPanel in a specific order that resembles a physical calculator layout.

    {

        /*
        Uses this layout, defined earlier:
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
         */
        //Row 1:(7 8 9 +)
        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(addButton);

        //Row 2:(4 5 6 -)
        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(subButton);

        //Row 3: (1 2 3 *)
        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(mulButton);

        //Row 4: (. 0 = /)
        buttonPanel.add(decButton);
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(equButton);
        buttonPanel.add(divButton);

        //Row 5: (X C)
        buttonPanel.add(delButton);
        buttonPanel.add(clrButton);
    }

    /*
     * Handles all button actions.
     */

    @Override //Overrides the method from ActionListener.

    //BELOW: event handler for all button clicks in the calculator. It determines what to do based on which button is clicked.
    // ActionEvent e contains info about which button was clicked

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i <= 9; i++) {
            if (e.getSource() == numberButtons[i]) {
                displayField.setText(displayField.getText().concat(String.valueOf(i)));
                return;
            }
        }
        /* NUMBER BUTTONS
        Checks if a number button was clicked.
        Appends the number to the display.
        Uses concat to join the digits (e.g., pressing 1 then 2 gives "12").
        return exits early to avoid checking other conditions.
         */


        if (e.getSource() == decButton) {
            if (!displayField.getText().contains(".")) {
                displayField.setText(displayField.getText().concat("."));
            }
        }

        /* DECIMAL
        Allows adding only one decimal point.
        Prevents invalid input like 12.3.4.
         */

        if (e.getSource() == addButton || e.getSource() == subButton ||
                e.getSource() == mulButton || e.getSource() == divButton) {
            try {
                num1 = Double.parseDouble(displayField.getText());
                operator = ((JButton) e.getSource()).getText().charAt(0);
                displayField.setText(displayField.getText() + operator);  // Show operator inline
            } catch (NumberFormatException ex) {
                displayField.setText("Error");
            }
        }


        /* ARITHMETIC
        When an operator is clicked:
        num1 is parsed from the current text in the display. This is the first operand.
        The operator (+, -, *, or /) is extracted from the button label.
        The operator is appended to the display so that the user sees the full expression (e.g., 7+).
        If the display content is not a valid number (e.g., it's empty or contains invalid characters), a NumberFormatException is caught, and the display shows "Error".
         */


        if (e.getSource() == equButton) {
            try {
                String text = displayField.getText();
                int opIndex = text.indexOf(operator);
                if (opIndex != -1 && opIndex < text.length() - 1) {
                    String secondNumberStr = text.substring(opIndex + 1);
                    num2 = Double.parseDouble(secondNumberStr);

                    switch (operator) {
                        case '+': result = num1 + num2; break;
                        case '-': result = num1 - num2; break;
                        case '*': result = num1 * num2; break;
                        case '/':
                            if (num2 == 0) {
                                displayField.setText("Cannot divide by 0");
                                return;
                            }
                            result = num1 / num2;
                            break;
                    }

                    String calc = num1 + " " + operator + " " + num2 + " = " + result;
                    displayField.setText(String.valueOf(result));
                    history.add(calc);
                    updateHistoryDisplay();
                    num1 = result;
                } else {
                    displayField.setText("Error");
                }
            } catch (Exception ex) {
                displayField.setText("Error");
            }
        }


        /*EQUALS
        Retrieves the current expression (e.g., "7+7") from the display.
        Identifies the operator's position in the string.
        Extracts the second operand from the string using substring.
        Parses num2 and performs the appropriate arithmetic operation using a switch.
        Displays the result.
        Adds the full expression and result to the history (e.g., "7 + 7 = 14.0").
        Sets num1 to the result so that users can chain operations if desired.
        If the operator is not found or there's no second operand, display "Error"; Catches general exceptions to avoid crashes.
         */

        if (e.getSource() == delButton) {
            String current = displayField.getText();
            if (!current.isEmpty()) {
                displayField.setText(current.substring(0, current.length() - 1));
            }
        }

        /* DELETE
        Deletes the last character from the display (like a backspace).
        Only works if there's something to delete.
         */

        if (e.getSource() == clrButton) {
            displayField.setText("");
            num1 = num2 = result = 0;
            operator = '\0';
        }
    }

    /* CLEAR
    Clears the display.
    Resets stored values (num1, num2, result, and operator) to default.
     */


    /*
     * Updates the history text area with latest calculations.
     */


    //BELOW: Updates history display area with list of previous calculations
    private void updateHistoryDisplay() {

        StringBuilder sb = new StringBuilder();
        //Efficiently builds a long string from multiple entries.

        for (String entry : history)
        // Loops through all entries stored in the history list (e.g., "2 + 3 = 5").
        {
            sb.append(entry).append("\n");
            //Adds each entry to the string builder, followed by a newline so each appears on a new line.

        }
        historyArea.setText(sb.toString());
        //Updates the JTextArea (historyArea) with the complete history string.
    }

    public static void main(String[] args) {
        new CalculatorApp();
        /*
         Creates an instance of the calculator window.
         Calls the constructor CalculatorApp() which sets up the GUI, buttons, and functionality.
         Also makes the window visible using setVisible(true) in the constructor.
         */
    }


}
