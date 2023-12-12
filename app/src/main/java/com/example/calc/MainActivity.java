package com.example.calc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.mariuszgromada.math.mxparser.Expression;

public class MainActivity extends AppCompatActivity {
    private TextView calculations, input;
    private ImageButton backspace;
    private Button percentage, clearEntry, clear, divideByX,
            square, squareRoot, divide, multiply,
            subtract, add, changeSign, dot, equals,
            one, two, three, four,
            five, six, seven, eight,
            nine, zero;
    private Button[] numberButtons;
    private Button[] operationButtons;
    public static String errorMessage = "";
    private Toast toast;
    private CalculationBuilder calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize toast for error messages and CalculationBuilder instance
        this.toast = Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
        this.calc = new CalculationBuilder(this.toast);
        // Assign UI elements
        assignElements();
        // Assign click listeners to number buttons
        assignOnClickListenerToNumbers();
        // Assign click listeners to cleaner buttons (clear, clear entry)
        assignOnClickListenerToCleaners();
        // Assign click listener to backspace button
        assignOnClickListenerToBackspace();
        // Assign click listeners to operation buttons
        assignOnClickListenerToOperation();
        // Assign click listener to equals button
        assignOnClickListenerToEquals();
        // Assign click listener to dot button
        assignOnClickListenerToDot();
        // Assign click listener to change sign button
        assignOnClickListenerToChangeSign();
    }

    // Method to assign UI elements to corresponding variables
    private void assignElements() {
        // Assign TextViews and Buttons to corresponding variables
        // (calculated result, user input, operators, etc.)
        this.calculations = findViewById(R.id.calculations);
        this.input = findViewById(R.id.input);
        this.backspace = findViewById(R.id.backspace);
        this.percentage = findViewById(R.id.percentage);
        this.clearEntry = findViewById(R.id.clearEntry);
        this.clear = findViewById(R.id.clear);
        this.divideByX = findViewById(R.id.divideByX);
        this.divide = findViewById(R.id.divide);
        this.square = findViewById(R.id.square);
        this.squareRoot = findViewById(R.id.squareRoot);
        this.multiply = findViewById(R.id.multiply);
        this.subtract = findViewById(R.id.subtract);
        this.add = findViewById(R.id.add);
        this.changeSign = findViewById(R.id.changeSign);
        this.dot = findViewById(R.id.dot);
        this.equals = findViewById(R.id.equals);
        this.one = findViewById(R.id.one);
        this.two = findViewById(R.id.two);
        this.three = findViewById(R.id.three);
        this.four = findViewById(R.id.four);
        this.five = findViewById(R.id.five);
        this.six = findViewById(R.id.six);
        this.seven = findViewById(R.id.seven);
        this.eight = findViewById(R.id.eight);
        this.nine = findViewById(R.id.nine);
        this.zero = findViewById(R.id.zero);

        // Create arrays for number buttons and operation buttons
        this.numberButtons = new Button[]{zero, one, two, three, four,
                five, six, seven, eight, nine};

        this.operationButtons = new Button[]{
                percentage, divideByX,
                square, squareRoot, divide, multiply,
                subtract, add
        };
    }

    // Method to assign click listeners to number buttons
    private void assignOnClickListenerToNumbers() {
        // Loop through each number button and assign a click listener
        // to append the clicked number to the user input
        if (this.numberButtons != null && this.numberButtons.length == 10) {
            // Loop through each number button
            for (int index = 0; index < this.numberButtons.length; index++) {
                // Get the resource ID of the current number button
                int id = this.numberButtons[index].getId();
                // Create a final variable for the current index to be used inside the click listener
                int finalIndex = index;

                // Find the view with the given ID and set a click listener
                findViewById(id).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String number = numberButtons[finalIndex].getText().toString();
                        calc.pushInput(input, number, null);
                    }
                });
            }
        }
    }

    // Method to assign click listeners to cleaner buttons (clear, clear entry
    private void assignOnClickListenerToCleaners() {
        // Assign click listener to clear entry button to clear the last entry
        // Assign click listener to clear button to clear all entries
        this.clearEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            // Inside the click listener, invoke the 'clearInput' method of the 'calc' (CalculationBuilder) instance
            // to clear the last input, and set 'previousOp' to -1 (clear the previous operation state)
            public void onClick(View v) {
                calc.clearInput(input);
                calc.previousOp = -1;
            }
        });
        // Inside the click listener, invoke the 'clearInput' method of the 'calc' (CalculationBuilder) instance
        // to clear all inputs, clear the 'calculations' TextView, and set 'previousOp' to -1
        // (clear the previous operation state)
        this.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calc.clearInput(input);
                calculations.setText("");
                calc.previousOp = -1;
            }
        });
    }

    // Method to assign click listener to backspace button
    private void assignOnClickListenerToBackspace() {
        // Assign click listener to backspace button to remove the last character
        // from the user input
        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calc.popInput(input);
            }
        });
    }

    // Method to assign click listeners to operation buttons
    private void assignOnClickListenerToOperation() {
        // Loop through each operation button and assign a click listener
        // to handle the corresponding operation (addition, subtraction, etc.)
        if (this.operationButtons != null && this.operationButtons.length == 8) {
            for (Button button : this.operationButtons) {
                // Loop through each operation button in the array
                // Get the tag associated with the current operation button
                String tag = (String) button.getTag();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Inside the click listener, invoke the 'pushInput' method of the 'calc' (CalculationBuilder) instance
                        // to handle the clicked operation, and update both the user input and calculations TextView
                        calc.pushInput(input, tag, calculations);
                    }
                });
            }
        }
    }
    // Method to assign click listener to dot button
    private void assignOnClickListenerToDot() {
        // Assign click listener to dot button to append a decimal point to the user input
        this.dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calc.pushInput(input, ".", null);
            }
        });
    }
    // Method to assign click listener to change sign button
    private void assignOnClickListenerToChangeSign() {
        // Assign click listener to change sign button to toggle between
        // positive and negative values in the user input
        this.changeSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = calc.getInput();
                // Check if the user input is 0 or empty; if so, do nothing
                if (val == "0" || val == "") {
                    return;
                } else if (val.startsWith("-") && val.length() >= 2) {
                    // If the input is already negative, remove the negative sign
                    calc.setInput(val.substring(1));
                } else if (!val.startsWith("-")) {
                    // If the input is positive, add a negative sign
                    String newVal = "(-" + val + ")";
                    calc.setInput(newVal);
                }
                // Update the user input TextView with the modified input
                input.setText(calc.getInput());
            }
        });
    }
    // Method to assign click listener to equals button
    private void assignOnClickListenerToEquals() {
        // Assign click listener to equals button to evaluate the user input
        // and display the result in the calculations TextView
        this.equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calc.getCalc().length() > 0) {
                    // Get the calculation string and replace "x" with "*" and "\u221A" with "sqrt"
                    String calcString = calc.getCalc();
                    calcString = calcString.replaceAll("x", "*")
                            .replaceAll("\u221A", "sqrt");
                    
                    // Check if the calculation string is not empty
                    if (calcString.length() > 0) {
                        // Do nothing if the last characters are ")" or "^"
                        if (")".equals(getCharFromLast(calcString, 1)) || "^".equals(getCharFromLast(calcString, 2))){
                            // Do nothing if the last characters are ")" or "^"
                        } else {
                            // Append the current input to the calculation string
                            calcString += calc.getInput();
                        }
                    }
                    // Log the modified calculation string for debugging
                    Log.i("CalcStr", calcString);
                    // Create a new Expression object using the modified calculation string
                    Expression exp = new Expression(calcString);
                    // Log the result of the expression for debugging
                    Log.i("CalcStr Res", Double.toString(exp.calculate()));

                    // Check if the result has a decimal point
                    boolean hasDouble = false;
                    for (int i = 0; i < calcString.length(); i++) {
                        if (calcString.charAt(i) == '.') {
                            hasDouble = true;
                        }
                    }
                    // Calculate the result
                    double result = exp.calculate();
                    // Check if the result is an integer -no decimal part)
                    if (result % 1 > 0.0000001) {
                        hasDouble = true;
                    }
                    // Display the result in the calculations TextView
                    if (!hasDouble) {
                        int n = (int) result;
                        calculations.setText(Integer.toString(n));
                    } else {
                        calculations.setText(Double.toString(result));
                    }
                    // Reset the user input, calculation string, and input TextView to 0
                    calc.setInput("0");
                    calc.setCalc("");
                    input.setText("0");
                }
            }
        });
    }
    // Utility method to retrieve a character from the end of a string
    private String getCharFromLast(String str, int i) {
        // Check if the index from the end is within the bounds of the string
        if (str.length() - i > -1) {
            // Return the character at the specified position from the end of the string
            return Character.toString(str.charAt(str.length() - i));
        }
        // Return an empty string if the index is out of bounds
        return "";
    }
}