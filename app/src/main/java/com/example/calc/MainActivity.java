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
    private Expression expression;
    private CalculationBuilder calc = new CalculationBuilder();

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
        assignOnClickListenerToDot();
        assignOnClickListenerToChangeSign();
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
                        String number = numberButtons[finalIndex].getText().toString();
                        calc.pushInput(input, number);
                    }
                });
            }
        }
    }

    private void assignOnClickListenerToCleaners() {
        this.clearEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calc.clearInput(input);
            }
        });
        this.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calc.clearInput(input);
            }
        });
    }

    private void assignOnClickListenerToBackspace() {
        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calc.popInput(input);
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
                        switch (tag) {
                            case "percentage":
                                break;
                            case "divideByX":
                                break;
                            case "square":
                                break;
                            case "squareRoot":
                                break;
                            case "divide":
                                break;
                            case "multiply":
                                break;
                            case "subtract":
                                break;
                            case "add":
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Unknown operation!", Toast.LENGTH_SHORT)
                                        .show();
                        }
                    }

                });
            }
        }
    }

    private void assignOnClickListenerToDot() {
        this.dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calc.pushInput(input, ".");
            }
        });
    }

    private void assignOnClickListenerToChangeSign() {
        this.changeSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void assignOnClickListenerToEquals() {
        this.equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}