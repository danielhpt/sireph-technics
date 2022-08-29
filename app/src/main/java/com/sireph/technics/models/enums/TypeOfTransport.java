package com.sireph.technics.models.enums;

import org.json.JSONException;
import org.json.JSONObject;

public enum TypeOfTransport {
    PRIMARY(1, "Primário"),
    SECONDARY(2, "Secundário"),
    NO_TRANSPORT(3, "Não Transporte");

    private final int id;
    private final String type_of_transport;

    TypeOfTransport(int id, String type_of_transport) {
        this.id = id;
        this.type_of_transport = type_of_transport;
    }

    public static TypeOfTransport fromId(int id) {
        switch (id){
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
        json.put("type_of_transport", this.type_of_transport);
        return json;
    }

    public int getId() {
        return id;
    }

    public String getType_of_transport() {
        return type_of_transport;
    }
}
