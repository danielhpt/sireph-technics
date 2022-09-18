package com.sireph.technics.models.victim.evaluation;

import static com.sireph.technics.utils.ValueFromJson.intFromJson;

import androidx.annotation.NonNull;

import com.sireph.technics.models._BaseModel;
import com.sireph.technics.utils.statics.Flag;
import com.sireph.technics.utils.statics.TypeOfJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GlasgowScale extends _BaseModel<GlasgowScale> {
    private Integer eyes;
    private Integer verbal;
    private Integer motor;
    private Integer total;

    public GlasgowScale(JSONObject json) {
        this.eyes = intFromJson(json, "eyes", null);
        this.verbal = intFromJson(json, "verbal", null);
        this.motor = intFromJson(json, "motor", null);
        updateTotal();
    }

    public GlasgowScale(Integer eyes, Integer verbal, Integer motor) {
        this.eyes = eyes;
        this.verbal = verbal;
        this.motor = motor;
        updateTotal();
    }

    public GlasgowScale() {
        this.eyes = null;
        this.verbal = null;
        this.motor = null;
    }

    @Override
    public JSONObject toJson(@NonNull TypeOfJson type) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("eyes", this.eyes == null ? JSONObject.NULL : eyes);
        json.put("verbal", this.verbal == null ? JSONObject.NULL : verbal);
        json.put("motor", this.motor == null ? JSONObject.NULL : motor);
        json.put("total", this.total == null ? JSONObject.NULL : total);
        return json;
    }

    @Override
    public ArrayList<Flag> update(@NonNull GlasgowScale updated) {
        return null;
    }

    public Integer getEyes() {
        return eyes;
    }

    public void setEyes(Integer eyes) {
        this.eyes = eyes;
        updateTotal();
    }

    public Integer getVerbal() {
        return verbal;
    }

    public void setVerbal(Integer verbal) {
        this.verbal = verbal;
        updateTotal();
    }

    public Integer getMotor() {
        return motor;
    }

    public void setMotor(Integer motor) {
        this.motor = motor;
        updateTotal();
    }

    public Integer getTotal() {
        return total;
    }

    public String toCellData() {
        if (total == null) return "";
        else return total + " (" + eyes + "," + verbal + "," + motor + ")";
    }

    private void updateTotal() {
        if (this.eyes == null || this.verbal == null || this.motor == null) {
            this.total = null;
        } else {
            this.total = this.eyes + this.verbal + this.motor;
        }
    }
}
