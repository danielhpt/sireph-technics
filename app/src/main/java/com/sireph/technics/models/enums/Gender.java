package com.sireph.technics.models.enums;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public enum Gender implements Serializable {
    M("M", "Masculino"),
    F("F", "Feminino"),
    O("O", "Outro"),
    ND("ND", "Não definido"),
    EMPTY("", "");

    private final String abbreviation;
    private final String value;

    Gender(String abbreviation, String value) {
        this.abbreviation = abbreviation;
        this.value = value;
    }

    public static Gender fromJson(JSONObject json) {
        if (json.isNull("gender")) {
            return EMPTY;
        } else {
            String value;
            try {
                value = json.getString("gender");
            } catch (JSONException e) {
                return EMPTY;
            }
            return fromValue(value);
        }
    }

    public static Gender fromValue(String value) {
        if (value == null || value.equals("") || value.equals("null")) {
            return EMPTY;
        }
        switch (value.toUpperCase().substring(0, 1)) {
            case "M":
                return M;
            case "F":
                return F;
            case "O":
                return O;
            case "N":
                return ND;
            default:
                throw new IllegalArgumentException();
        }
    }

    @NonNull
    @Override
    public String toString() {
        return abbreviation;
    }

    public String getValue() {
        return value;
    }
}
