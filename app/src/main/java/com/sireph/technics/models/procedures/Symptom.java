package com.sireph.technics.models.procedures;

import static com.sireph.technics.utils.ValueFromJson.doubleFromJson;
import static com.sireph.technics.utils.ValueFromJson.stringFromJson;

import com.sireph.technics.models._BaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Symptom extends _BaseModel {
    private String comments;
    private Double total_burn_area;
    private final List<Trauma> traumas;

    public Symptom(JSONObject json) throws JSONException {
        super(json);
        this.comments = stringFromJson(json, "comments", "");
        this.total_burn_area = doubleFromJson(json, "total_burn_area", null);

        this.traumas = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("traumas").length(); i++) {
            this.traumas.add(new Trauma(json.getJSONArray("traumas").getJSONObject(i)));
        }
    }

    public Symptom() {
        this.comments = "";
        this.total_burn_area = null;
        this.traumas = new ArrayList<>();
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("comments", this.comments);
        json.put("total_burn_area", this.total_burn_area);
        JSONArray traumas = new JSONArray();
        for (int i = 0; i < this.traumas.size(); i++) {
            traumas.put(this.traumas.get(i).toJson());
        }
        json.put("traumas", traumas);
        return json;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Double getTotal_burn_area() {
        return total_burn_area;
    }

    public void setTotal_burn_area(Double total_burn_area) {
        this.total_burn_area = total_burn_area;
    }

    public void addTrauma(Trauma trauma) {
        this.traumas.add(trauma);
    }

    public List<Trauma> getTraumas() {
        return traumas;
    }
}
