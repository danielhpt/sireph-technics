package com.sireph.technics.models.procedures;

import static com.sireph.technics.utils.ValueFromJson.doubleFromJson;
import static com.sireph.technics.utils.ValueFromJson.intFromJson;
import static com.sireph.technics.utils.ValueFromJson.stringFromJson;

import com.sireph.technics.models._BaseModel;
import com.sireph.technics.models.enums.BurnDegree;
import com.sireph.technics.models.enums.TypeOfInjury;
import com.sireph.technics.utils.statics.Flag;
import com.sireph.technics.utils.statics.TypeOfJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Symptom extends _BaseModel<Symptom> {
    private List<Trauma> traumas;
    private String comments;
    private double total_burn_area;

    public Symptom(JSONObject json) throws JSONException {
        this.id = intFromJson(json, "victim", -1);
        this.comments = stringFromJson(json, "comments", "");
        this.total_burn_area = doubleFromJson(json, "total_burn_area", 0.0);

        this.traumas = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("traumas").length(); i++) {
            this.traumas.add(new Trauma(json.getJSONArray("traumas").getJSONObject(i)));
        }
    }

    public Symptom() {
        super();
        this.comments = "";
        this.total_burn_area = 0.0;
        this.traumas = new ArrayList<>();
    }

    @Override
    public ArrayList<Flag> update(Symptom updated) {
        ArrayList<Flag> flags = new ArrayList<>();
        if (this.id == null && updated.id != null) {
            this.id = updated.id;
        }
        if (this.traumas.size() < updated.traumas.size()) {
            this.traumas = updated.traumas;
            flags.add(Flag.ADDED_TRAUMA);
        }
        if (!Objects.equals(this.comments, updated.comments)) {
            this.comments = updated.comments;
            flags.add(Flag.UPDATED_SYMPTOM);
        }
        if (!Objects.equals(this.total_burn_area, updated.total_burn_area)) {
            this.total_burn_area = updated.total_burn_area;
            flags.add(Flag.UPDATED_SYMPTOM);
        }
        return flags;
    }

    @Override
    public JSONObject toJson(TypeOfJson type) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("comments", this.comments);
        json.put("total_burn_area", this.total_burn_area);
        JSONArray traumas = new JSONArray();
        for (int i = 0; i < this.traumas.size(); i++) {
            traumas.put(this.traumas.get(i).toJson(type));
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

    public double getTotal_burn_area() {
        return total_burn_area;
    }

    public void setTotal_burn_area(double total_burn_area) {
        this.total_burn_area = total_burn_area;
    }

    public void addTrauma(Trauma trauma) {
        this.traumas.add(trauma);
        if (id == null) {
            id = -1;
        }
        if (trauma.getType_of_injury() == TypeOfInjury.BURN && (trauma.getBurn_degree() == BurnDegree.G2 || trauma.getBurn_degree() == BurnDegree.G3)) {
            this.total_burn_area += trauma.getBody_part().getArea();
        }
    }

    public List<Trauma> getTraumas() {
        return traumas;
    }
}
