package com.sireph.technics.models.procedures;

import static com.sireph.technics.utils.ValueFromJson.stringFromJson;

import com.sireph.technics.models._BaseModel;
import com.sireph.technics.models.date.DateTime;
import com.sireph.technics.utils.statics.Flag;
import com.sireph.technics.utils.statics.TypeOfJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class Pharmacy extends _BaseModel<Pharmacy> {
    private DateTime time;
    private String pharmacy;
    private String dose;
    private String route;
    private String adverse_effect;

    public Pharmacy(JSONObject json) {
        super(json);
        this.time = DateTime.fromJson(json, "time");
        this.pharmacy = stringFromJson(json, "pharmacy", "");
        this.dose = stringFromJson(json, "dose", "");
        this.route = stringFromJson(json, "route", "");
        this.adverse_effect = stringFromJson(json, "adverse_effect", "");
    }

    public Pharmacy(DateTime time, String pharmacy, String dose, String route, String adverse_effect) {
        this.time = time;
        this.pharmacy = pharmacy;
        this.dose = dose;
        this.route = route;
        this.adverse_effect = adverse_effect;
    }

    @Override
    public ArrayList<Flag> update(Pharmacy updated) {
        return null;
    }

    @Override
    public JSONObject toJson(TypeOfJson type) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("time", this.time == null ? JSONObject.NULL : time.toString());

        json.put("pharmacy", Objects.equals(pharmacy, "") ? JSONObject.NULL : pharmacy);
        json.put("dose", Objects.equals(dose, "") ? JSONObject.NULL : dose);
        json.put("route", Objects.equals(route, "") ? JSONObject.NULL : route);
        json.put("adverse_effect", Objects.equals(adverse_effect, "") ? JSONObject.NULL : adverse_effect);
        return json;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
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
