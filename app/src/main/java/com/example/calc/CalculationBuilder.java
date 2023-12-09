package com.example.calc;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CalculationBuilder {
    private String input;
    private Toast toast;
    private String calculation = "";
    public int previousOp = -1;
    private Boolean isDouble = false;

    public CalculationBuilder() {
        this.input = "0";
    }

    public CalculationBuilder(Toast toast) {
        this.input = "0";
        this.toast = toast;
    }


    public CalculationBuilder(String input) throws Exception {
        if (input == "") {
            throw new Exception("Invalid initializer input: Initial input cannot be 0!");
        } else {
            this.input = input;
        }
    }

    public String getCalc() {
        return this.calculation;
    }

    public void setCalc(String calc) {
        this.calculation = calc;
    }

    public String getInput() {
        return this.input;
    }

    public void setInput(String val) {
        this.input = val;
    }


    public void pushInput(TextView input, String value, TextView calc) {
        if (handleOperation(value)) {
            if (this.calculation.charAt(this.calculation.length() - 2) == '.') {
                calc.setText("");
                input.setText("0");
                this.input = "0";
                this.calculation = "";
                toast.setText("Please don't do that again :(");
                toast.show();
                return;
            }
            if (this.calculation.length() > 30) {
                toast.setText("Max 30 characters allowed!");
                toast.show();
                this.input = "0";
                this.calculation = "";
                input.setText("0");
                calc.setText("");
            }
            calc.setText(this.calculation);
            input.setText("0");
            return;
        }

        if (this.input.length() > 15) {
            toast.setText("Max 15 characters allowed!");
            toast.show();
            return;
        }

        String currentValueType = getType(value);
        String currentInputType = getType(this.input);

        // Log.i("CurrentType", currentType);
        Log.i("CurrentType", value);
        // Handle dot
        if (value == ".") {
            if (currentInputType == "int") {
                this.input = this.input + value;
            } else {
                return;
            }
        }


        if (currentInputType == "int" && currentValueType != "") {
            // Current input is 0, do not allow 00, 000, etc.
            if (Integer.valueOf(this.input) == 0 && Integer.valueOf(value) == 0) {
                this.input = value;
            } else if (Integer.valueOf(this.input) == 0 && Integer.valueOf(value) > 0) {
                // Current input is 0, upcoming value is not zero,
                //  therefore replace current with upcoming value
                this.input = value;
            } else {
                this.input = this.input + value;
            }
        } else if (currentInputType == "double" && currentValueType == "int") {
            this.input = this.input + value;
        }

        // Current input is going to be double
        if (this.input.endsWith(".") && value != ".") {
            this.input = this.input + value;
        }

        input.setText(this.input);
    }

    public void popInput(TextView input) {
        int len = this.input.length();

        if (len <= 1) {
            this.input = "0";
        } else {
            String newInput = this.input.substring(0, len - 1);
            Log.i("Backspace", newInput);
            Log.i("Backspace endswith", Boolean.toString(newInput.endsWith(".")));
            if (newInput.endsWith(".")) {
                this.input = newInput.substring(0, newInput.length() - 1);
            } else {
                this.input = newInput;
            }
        }
        input.setText(this.input);
    }

    public void clearInput(TextView input) {
        this.input = "0";
        this.calculation = "";
        input.setText("0");
    }

    public String getType(String value) {
        if (value == ".") {
            return "";
        }

        boolean isI = false;
        boolean isD = isDouble(value);

        if (!isD) {
            isI = isInt(value);
        }

        if (isD) {
            isDouble = true;
            return "double";
        } else if (isI) {
            return "int";
        }
        return "";
    }

    private void saveAndShow(String op) {
        if (previousOp == 0) {
            this.calculation = this.calculation + op + this.input;
            this.input = "0";
        } else {
            this.input += op;
            this.calculation += this.input;
            this.input = "0";
            this.previousOp = -1;
        }
    }

    private boolean handleOperation(String tag) {
        switch (tag) {
            case "percentage":
                saveAndShow("%");
                return true;
            case "divideByX":
                String x = "";
                if (this.calculation == "") {
                    x = "(" + this.calculation + this.input + ")";
                } else {
                    if (!isBasicOp(getLastChar())) {
                        x = "(" + this.calculation + ")";
                    } else {
                        x = "(" + this.calculation + this.input + ")";
                    }
                }
                this.calculation = "1÷" + x;
                this.input = "0";
                this.previousOp = 0;
                return true;
            case "square":
                String xsq = "";
                if (this.calculation == "") {
                    xsq = "(" + this.input + ")^2";
                } else {
                    if (!isBasicOp(getLastChar())) {
                        xsq = "(" + this.calculation + ")^2";
                    } else {
                        xsq = "(" + this.calculation + this.input + ")^2";
                    }
                }
                this.calculation = xsq;
                this.input = "0";
                this.previousOp = 0;
                return true;
            case "squareRoot":
                String xsqr = "";
                if (this.calculation == "") {
                    xsqr = "\u221A(" + this.input + ")";
                } else {
                    if (!isBasicOp(getLastChar())) {
                        xsqr = "\u221A(" + this.calculation + ")";
                    } else {
                        xsqr = "\u221A(" + this.calculation + this.input + ")";
                    }
                }
                this.calculation = xsqr;
                this.input = "0";
                this.previousOp = 0;
                return true;
            case "divide":
                saveAndShow("÷");
                return true;
            case "multiply":
                saveAndShow("x");
                return true;
            case "subtract":
                saveAndShow("-");
                return true;
            case "add":
                saveAndShow("+");
                return true;
            default:
                try {
                    getType(tag);
                } catch (Exception e) {
                    toast.setText("Unknown operation!");
                    toast.show();
                }
        }
        return false;
    }

    private boolean isInt(String value) {
        boolean type = false;
        try {
            int i = Integer.valueOf(value);
            type = true;

        } catch (Exception e) {
            toast.setText("Invalid input");
            toast.show();
        }
        return type;
    }

    private boolean isDouble(String value) {
        boolean type = false;
        try {
            if (Double.valueOf(value) instanceof Double && value.contains(".")) {
                type = true;
            }
        } catch (Exception e) {
            toast.setText("Invalid Input");
            toast.show();
        }
        return type;
    }

    private boolean isBasicOp(String lastChar) {
        if ("%÷x-+.".contains(lastChar)){
            return true;
        }
        return false;
    }

    private String getLastChar() {
        if (this.calculation.length() > 0) {
            return Character.toString(this.calculation.charAt(this.calculation.length() - 1));
        }
        return "";
    }
}
