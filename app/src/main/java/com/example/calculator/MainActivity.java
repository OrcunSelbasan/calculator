package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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

    private String stateInput = "", stateCalculations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignElements();
        assignOnClickListenerToNumbers();
        assignOnClickListenerToCleaners();
        assignOnClickListenerToBackspace();
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


}