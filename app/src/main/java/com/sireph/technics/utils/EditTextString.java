package com.sireph.technics.utils;

import android.widget.EditText;

import com.sireph.technics.R;

import java.util.Objects;

public class EditTextString {
    public static void editTextString(EditText text, String s, boolean isActive) {
        if (!Objects.equals(s, "")) {
            text.setText(s);
        }
        text.setEnabled(isActive);
    }

    public static Integer getInt(EditText text) {
        String s = text.getText().toString();
        Integer value = null;
        if (!s.equals("")) {
            try {
                value = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                text.setError(text.getContext().getString(R.string.invalid_value));
            }
        }
        return value;
    }

    public static Double getDouble(EditText text) {
        String s = text.getText().toString();
        Double value = null;
        if (!s.equals("")) {
            try {
                value = Double.parseDouble(s);
            } catch (NumberFormatException e) {
                text.setError(text.getContext().getString(R.string.invalid_value));
            }
        }
        return value;
    }
}
