package com.sireph.technics.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Hospital extends _BaseModel {
    private final String name;
    private final String address;
    private final int capacity;
    private final int current_capacity;
    private final int contact;
    private final String image_url;

    public Hospital(JSONObject json) throws JSONException {
        super(json);
        this.name = json.getString("name");
        this.address = json.getString("address");
        this.capacity = json.getInt("capacity");
        this.current_capacity = json.getInt("current_capacity");
        this.contact = json.getInt("contact");
        this.image_url = json.getString("image_url");
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("name", this.name);
        json.put("address", this.address);
        json.put("capacity", this.capacity);
        json.put("current_capacity", this.current_capacity);
        json.put("contact", this.contact);
        json.put("image_url", this.image_url);
        return json;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
