package com.sireph.technics.models.enums;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public enum TypeOfTransport implements Serializable {
    PRIMARY(1, "Primário"),
    SECONDARY(2, "Secundário"),
    NO_TRANSPORT(3, "Não Transporte");

    private final int id;
    private final String value;

    TypeOfTransport(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public static TypeOfTransport fromJson(JSONObject json) {
        if (json.isNull("type_of_transport")) {
            return null;
        } else {
            int id;
            try {
                id = json.getInt("type_of_transport");
            } catch (JSONException e) {
                try {
                    id = json.getJSONObject("type_of_transport").getInt("id");
                } catch (JSONException ex) {
                    return null;
                }
            }
            return fromId(id);
        }
    }

    public static TypeOfTransport fromId(int id) {
        switch (id) {
            case 1:
                return PRIMARY;
            case 2:
                return SECONDARY;
            case 3:
                return NO_TRANSPORT;
            default:
                throw new IllegalArgumentException();
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("type_of_transport", this.value);
        return json;
    }

    public int getId() {
        return id;
    }

    @NonNull
    @Override
    public String toString() {
        return value;
    }
}
