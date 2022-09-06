package com.sireph.technics.models;

import static com.sireph.technics.utils.ValueFromJson.boolFromJson;
import static com.sireph.technics.utils.ValueFromJson.intFromJson;
import static com.sireph.technics.utils.ValueFromJson.stringFromJson;

import org.json.JSONException;
import org.json.JSONObject;

public class Central extends _BaseModel {
    private final String designation;
    private final String address;
    private final String area_of_action;
    private final Integer contact;
    private final Boolean is_administrative;

    public Central(JSONObject json) {
        super(json);
        this.designation = stringFromJson(json, "designation", "");
        this.address = stringFromJson(json, "address", "");
        this.area_of_action = stringFromJson(json, "area_of_action", "");
        this.contact = intFromJson(json, "contact", null);
        this.is_administrative = boolFromJson(json, "is_administrative", false);
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
}
