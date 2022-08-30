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

        String first_name1;
        try {
            first_name1 = json.getString("first_name");
        } catch (JSONException e) {
            first_name1 = null;
        }
        this.first_name = first_name1;

        String last_name1;
        try {
            last_name1 = json.getString("last_name");
        } catch (JSONException e) {
            last_name1 = null;
        }
        this.last_name = last_name1;

        String email1;
        try {
            email1 = json.getString("email");
        } catch (JSONException e) {
            email1 = null;
        }
        this.email = email1;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        if (this.id != null) {
            json.put("id", this.id);
        }
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
