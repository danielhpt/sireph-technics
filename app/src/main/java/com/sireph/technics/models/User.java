package com.sireph.technics.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User extends _BaseModel {
    private final String username;
    private final String first_name;
    private final String last_name;
    private final String email;

    public User(JSONObject json) throws JSONException {
        super(json);
        this.username = json.getString("username");

        this.first_name = json.optString("first_name", null);
        this.last_name = json.optString("last_name", null);
        this.email = json.optString("email", null);
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("username", this.username);
        json.put("first_name", this.first_name);
        json.put("last_name", this.last_name);
        json.put("email", this.email);
        return json;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        if (first_name == null) {
            return username;
        } else if (last_name == null) {
            return first_name;
        } else {
            return first_name + " " + last_name;
        }
    }

    public String getEmail() {
        if (email == null) {
            return username;
        }
        return email;
    }
}
