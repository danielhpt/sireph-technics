package com.sireph.technics.models.procedures;

import com.sireph.technics.models._BaseModel;
import com.sireph.technics.models.date.Time;

import org.json.JSONException;
import org.json.JSONObject;

public class Pharmacy extends _BaseModel {
    private Time time;
    private String pharmacy;
    private String dose;
    private String route;
    private String adverse_effect;

    public Pharmacy(JSONObject json) throws JSONException {
        super(json);
        this.time = Time.fromJson(json,"time");
        this.pharmacy = json.getString("pharmacy");
        this.dose = json.getString("dose");
        this.route = json.getString("route");
        this.adverse_effect = json.getString("adverse_effect");
    }

    public Pharmacy(Time time, String pharmacy, String dose, String route, String adverse_effect) {
        this.time = time;
        this.pharmacy = pharmacy;
        this.dose = dose;
        this.route = route;
        this.adverse_effect = adverse_effect;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("time", this.time.toString());
        json.put("pharmacy", this.pharmacy);
        json.put("dose", this.dose);
        json.put("route", this.route);
        json.put("adverse_effect", this.adverse_effect);
        return json;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getAdverse_effect() {
        return adverse_effect;
    }

    public void setAdverse_effect(String adverse_effect) {
        this.adverse_effect = adverse_effect;
    }
}
