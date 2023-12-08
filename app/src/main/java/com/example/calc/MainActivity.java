package com.example.calc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    private Boolean isLastCalculationOperator = false,
            hasDouble = false, isEqualed = false, hasOp = false;
    private String stateInput = "";
    private String calculationResult = "";
    private Expression expression;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignElements();
        assignOnClickListenerToNumbers();
        assignOnClickListenerToCleaners();
        assignOnClickListenerToBackspace();
        assignOnClickListenerToOperation();
        assignOnClickListenerToEquals();
    }

    private void assignElements() {
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

        this.numberButtons = new Button[]{zero, one, two, three, four,
                five, six, seven, eight, nine};

        this.operationButtons = new Button[]{
                percentage, divideByX,
                square, squareRoot, divide, multiply,
                subtract, add
        };
    }

    private void assignOnClickListenerToNumbers() {
        if (this.numberButtons != null && this.numberButtons.length == 10) {
            for (int index = 0; index < this.numberButtons.length; index++) {
                int id = this.numberButtons[index].getId();
                int finalIndex = index;
                findViewById(id).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stateInput += finalIndex;
                        input.setText(stateInput);
                    }
                });
            }
        }
    }

    private void assignOnClickListenerToCleaners() {
        this.clearEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText("0");
                stateInput = "";
            }
        });
        this.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText("0");
                calculations.setText("");
                stateInput = "";
            }
        });
    }

    private void assignOnClickListenerToBackspace() {
        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentState = input.getText().toString();
                if (currentState == "" || currentState.length() == 1) {
                    input.setText("0");
                } else {
                    int end = currentState.length() - 1;
                    String newState = currentState.substring(0, end);
                    input.setText(newState);
                }
            }
        });
    }

    private void assignOnClickListenerToOperation() {
        if (this.operationButtons != null && this.operationButtons.length == 8) {
            for (Button button : this.operationButtons) {
                String tag = (String) button.getTag();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String currentCalculation = calculations.getText().toString();
                        String currentInput = input.getText().toString();
                        switch (tag) {
                            case "percentage":
                                if (isEqualed) {
                                    resetEqualed();
                                    updateCalculation("%", true);
                                } else {
                                    updateCalculation("%", false);
                                }
                                break;
                            case "divideByX":
                                // code block
                                break;
                            case "square":
                                // code block
                                break;
                            case "squareRoot":
                                // code block
                                break;
                            case "divide":
                                if (isEqualed) {
                                    resetEqualed();
                                    updateCalculation("÷", true);
                                } else {
                                    updateCalculation("÷", false);
                                }
                                break;
                            case "multiply":
                                if (isEqualed) {
                                    resetEqualed();
                                    updateCalculation("x", true);
                                } else {
                                    updateCalculation("x", false);
                                }
                                break;
                            case "subtract":
                                if (isEqualed) {
                                    resetEqualed();
                                    updateCalculation("-", true);
                                } else {
                                    updateCalculation("-", false);
                                }
                                break;
                            case "add":
                                if (isEqualed) {
                                    resetEqualed();
                                    updateCalculation("+", true);
                                } else {
                                    updateCalculation("+", false);
                                }
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Unknown operation!", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        }
    }

    private void assignOnClickListenerToDot() {
    }

    private void assignOnClickListenerToChangeSign() {
    }

    private void assignOnClickListenerToEquals() {
        this.equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCalculation("=", false);
                String currentCalculation = calculations.getText().toString();
                if (expression != null) {
                    String finalCalculation;
                    if (hasDouble) {
                        double result = expression.calculate();
                        finalCalculation = currentCalculation + " " + result;
                        calculationResult = String.valueOf(result);
                    } else {
                        int result = (int) expression.calculate();
                        finalCalculation = currentCalculation + " " + result;
                        calculationResult = String.valueOf(result);
                    }

                    calculations.setText(finalCalculation);

                    isEqualed = true;
                }
            }
        });
    }

    private void updateCalculation(String operation, boolean reverse) {
        String currentCalculation = calculations.getText().toString();
        String currentInput = input.getText().toString();
        if (operation == "=") {
            String preparedExpression = currentCalculation.replaceAll("÷", "/").replaceAll("x", "*");
            this.expression = new Expression(preparedExpression + " " + currentInput);
        } else {

        }
        if (reverse == true) {
            calculations.setText(currentCalculation + " " + operation + " " + currentInput);
            input.setText("0");
            stateInput = "";
            isLastCalculationOperator = false;
        } else {
            calculations.setText(currentCalculation + " " + currentInput + " " + operation + " ");
            input.setText("0");
            stateInput = "";
            isLastCalculationOperator = true;
        }
    }

    private void resetEqualed() {
        if (calculationResult != null) {
            calculations.setText(calculationResult);

            isEqualed = false;
        }
    }
}