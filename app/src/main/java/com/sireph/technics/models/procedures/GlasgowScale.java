package com.sireph.technics.models.procedures;

import static com.sireph.technics.utils.ValueFromJson.intFromJson;

import com.sireph.technics.models._BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class GlasgowScale extends _BaseModel {
    private Integer eyes;
    private Integer verbal;
    private Integer motor;
    private Integer total;

    public GlasgowScale(JSONObject json) {
        super(json);
        this.eyes = intFromJson(json, "eyes", null);
        this.verbal = intFromJson(json, "eyes", null);
        this.motor = intFromJson(json, "eyes", null);
        updateTotal();
    }

    public GlasgowScale(Integer eyes, Integer verbal, Integer motor) {
        this.eyes = eyes;
        this.verbal = verbal;
        this.motor = motor;
        updateTotal();
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("eyes", this.eyes);
        json.put("verbal", this.verbal);
        json.put("motor", this.motor);
        json.put("total", this.total);
        return json;
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

    private void updateTotal() {
        if (this.eyes == null || this.verbal == null || this.motor == null) {
            this.total = null;
        } else {
            this.total = this.eyes + this.verbal + this.motor;
        }
    }
}
