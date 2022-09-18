package com.sireph.technics.utils;

import android.widget.EditText;

import com.sireph.technics.R;

public class EditTextString {
    public static void editTextString(EditText text, String s, boolean isActive) {
        if (s != null && !s.equals("")) {
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
                value = Double.parseDouble(s.replace(',', '.'));
            } catch (NumberFormatException e) {
                text.setError(text.getContext().getString(R.string.invalid_value));
            }
        }
        return value;
    }
}
