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

        this.toast = Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
        this.calc = new CalculationBuilder(this.toast);

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
                        calc.pushInput(input, number, null);
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
                calc.previousOp = -1;
            }
        });
        this.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calc.clearInput(input);
                calculations.setText("");
                calc.previousOp = -1;
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
                        calc.pushInput(input, tag, calculations);
                    }
                });
            }
        }
    }

    private void assignOnClickListenerToDot() {
        this.dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calc.pushInput(input, ".", null);
            }
        });
    }

    private void assignOnClickListenerToChangeSign() {
        this.changeSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = calc.getInput();
                if (val == "0" || val == "") {
                    return;
                } else if (val.startsWith("-") && val.length() >= 2) {
                    calc.setInput(val.substring(1));
                } else if (!val.startsWith("-")) {
                    String newVal = "(-" + val + ")";
                    calc.setInput(newVal);
                }
                input.setText(calc.getInput());
            }
        });
    }

    private void assignOnClickListenerToEquals() {
        this.equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calc.getCalc().length() > 0) {
                    String calcString = calc.getCalc();
                    calcString = calcString.replaceAll("x", "*")
                            .replaceAll("\u221A", "sqrt");
                    calcString += calc.getInput();
                    Log.i("CalcStr", calcString);
                    Expression exp = new Expression(calcString);
                    Log.i("CalcStr Res", Double.toString(exp.calculate()));
                    boolean hasDouble = false;
                    for (int i = 0; i < calcString.length(); i++) {
                        if (calcString.charAt(i) == '.') {
                            hasDouble = true;
                        }
                    }
                    double result = exp.calculate();
                    if (result % 1 > 0.0000001) {
                        hasDouble = true;
                    }
                    if (!hasDouble) {
                        int n = (int) result;
                        input.setText(Integer.toString(n));
                    } else {
                        input.setText(Double.toString(result));
                    }
                    calc.setInput("0");
                    calc.setCalc("");
                    calculations.setText("");
                }
            }
        });
    }
}