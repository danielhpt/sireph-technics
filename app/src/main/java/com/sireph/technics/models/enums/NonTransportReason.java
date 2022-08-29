package com.sireph.technics.models.enums;

import org.json.JSONException;
import org.json.JSONObject;

public enum NonTransportReason {
    ABANDONED(1, "Abandonou o local"),
    MEDIC(2, "Decisão médica"),
    DEATH(3, "Morte"),
    REFUSED_SIGNED(4, "Recusou e assinou"),
    REFUSED_NOT_SIGNED(5, "Recusou e não assinou"),
    DEACTIVATION(6, "Desativação");

    private final int id;
    private final String non_transport_reason;

    NonTransportReason(int id, String non_transport_reason) {
        this.id = id;
        this.non_transport_reason = non_transport_reason;
    }

    public static NonTransportReason fromId(int id){
        switch (id){
            case 1:
                return ABANDONED;
            case 2:
                return MEDIC;
            case 3:
                return DEATH;
            case 4:
                return REFUSED_SIGNED;
            case 5:
                return REFUSED_NOT_SIGNED;
            case 6:
                return DEACTIVATION;
            default:
                throw new IllegalArgumentException();
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("non_transport_reason", this.non_transport_reason);
        return json;
    }

    public int getId() {
        return id;
    }

    public String getNon_transport_reason() {
        return non_transport_reason;
    }
}
