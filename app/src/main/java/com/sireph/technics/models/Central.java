package com.sireph.technics.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Central extends _BaseModel {
    private final String designation;
    private final String address;
    private final String area_of_action;
    private final int contact;
    private final boolean is_administrative;

    public Central(JSONObject json) throws JSONException {
        super(json);
        this.designation = json.getString("designation");
        this.address = json.getString("address");
        this.area_of_action = json.getString("area_of_action");
        this.contact = json.getInt("contact");
        this.is_administrative = json.getBoolean("is_administrative");
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("designation", this.designation);
        json.put("address", this.address);
        json.put("area_of_action", this.area_of_action);
        json.put("contact", this.contact);
        json.put("is_administrative", this.is_administrative);
        return json;
    }

    public String getDesignation() {
        return designation;
    }

    public String getAddress() {
        return address;
    }

    public String getArea_of_action() {
        return area_of_action;
    }

    public int getContact() {
        return contact;
    }

    public boolean isAdministrative() {
        return is_administrative;
    }
}
