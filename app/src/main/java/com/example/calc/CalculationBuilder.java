package com.example.calc;

import android.util.Log;
import android.widget.TextView;

public class CalculationBuilder {
    private String input;
    private Boolean isDotUsed = false;

    public CalculationBuilder() {
        this.input = "0";
    }

    public CalculationBuilder(String input) throws Exception {
        if (input == ""){
            throw new Exception("Invalid initializer input: Initial input cannot be 0!");
        } else {
            this.input = input;
        }
    }

    public void pushInput(TextView input, String value) {
        String currentInputType = getType(this.input);
        String currentValueType = getType(value);
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
            if (Integer.valueOf(this.input) == 0 && Integer.valueOf(value)  == 0) {
                this.input = value;
            } else if (Integer.valueOf(this.input) == 0 && Integer.valueOf(value)  > 0) {
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
        input.setText("0");
    }

    public String getType(String value) {
        boolean isI = isInt(value);
        boolean isD = isDouble(value);

        if (isD) {
            return "double";
        } else if (isI) {
            return "int";
        }
        return "";
    }

    private boolean isInt(String value) {
        boolean type = false;
        try {
            int i = Integer.valueOf(value);
            type = true;

        } catch (Exception e) {

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

        }
        return type;
    }
}
