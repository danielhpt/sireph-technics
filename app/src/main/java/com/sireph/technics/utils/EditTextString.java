package com.sireph.technics.utils;

import android.widget.EditText;

import java.util.Objects;

public class EditTextString {
    public static void editTextString(EditText text, String s, boolean isActive) {
        if (!Objects.equals(s, "")) {
            text.setText(s);
            text.setEnabled(false);
        }
        if (!isActive) {
            text.setEnabled(false);
        }
    }
}
