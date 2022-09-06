package com.sireph.technics.models.procedures;

import static com.sireph.technics.utils.ValueFromJson.boolFromJson;
import static com.sireph.technics.utils.ValueFromJson.doubleFromJson;
import static com.sireph.technics.utils.ValueFromJson.intFromJson;
import static com.sireph.technics.utils.ValueFromJson.stringFromJson;

import com.sireph.technics.models._BaseModel;
import com.sireph.technics.models.date.DateTime;
import com.sireph.technics.models.enums.AVDS;
import com.sireph.technics.models.enums.Pupils;
import com.sireph.technics.models.enums.Skin;

import org.json.JSONException;
import org.json.JSONObject;

public class Evaluation extends _BaseModel {
    private DateTime hours;
    private AVDS avds;
    private Integer ventilation;
    private Integer spo2;
    private Integer o2;
    private Integer etco2;
    private Integer pulse;
    private Integer systolic_blood_pressure;
    private Integer diastolic_blood_pressure;
    private Integer pain;
    private Integer glycemia;
    private Integer news;
    private Boolean ecg;
    private Skin skin;
    private Double temperature;
    private Pupils pupils;
    private GlasgowScale glasgowScale;

    public Evaluation(JSONObject json) {
        super(json);
        this.hours = DateTime.fromJson(json, "hours");
        this.avds = AVDS.fromJson(json);
        this.ventilation = intFromJson(json, "ventilation", null);
        this.spo2 = intFromJson(json, "spo2", null);
        this.o2 = intFromJson(json, "o2", null);
        this.etco2 = intFromJson(json, "etco2", null);
        this.pulse = intFromJson(json, "pulse", null);
        this.ecg = boolFromJson(json, "ecg", false);
        this.skin = Skin.fromJson(json);
        this.temperature = doubleFromJson(json, "temperature", null);
        this.systolic_blood_pressure = intFromJson(json, "systolic_blood_pressure", null);
        this.diastolic_blood_pressure = intFromJson(json, "diastolic_blood_pressure", null);
        this.pupils = Pupils.fromJson(json);
        this.pain = intFromJson(json, "pain", null);
        this.glycemia = intFromJson(json, "glycemia", null);
        this.news = intFromJson(json, "news", null);
        this.glasgowScale = new GlasgowScale(json);
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("hours", this.hours.toString());
        json.put("avds", this.avds.toString());
        json.put("ventilation", this.ventilation);
        json.put("spo2", this.spo2);
        json.put("o2", this.o2);
        json.put("etco2", this.etco2);
        json.put("pulse", this.pulse);
        json.put("ecg", this.ecg);
        json.put("skin", this.skin.toString());
        json.put("temperature", this.temperature);
        json.put("systolic_blood_pressure", this.systolic_blood_pressure);
        json.put("diastolic_blood_pressure", this.diastolic_blood_pressure);
        json.put("pupils", this.pupils.toString());
        json.put("pain", this.pain);
        json.put("glycemia", this.glycemia);
        json.put("news", this.news);
        json.put("glasgow_scale", this.glasgowScale.toJson());
        return json;
    }

    public DateTime getHours() {
        return hours;
    }

    public void setHours(DateTime hours) {
        this.hours = hours;
    }

    public AVDS getAvds() {
        return avds;
    }

    public void setAvds(AVDS avds) {
        this.avds = avds;
    }

    public Integer getVentilation() {
        return ventilation;
    }

    public void setVentilation(Integer ventilation) {
        this.ventilation = ventilation;
    }

    public Integer getSpo2() {
        return spo2;
    }

    public void setSpo2(Integer spo2) {
        this.spo2 = spo2;
    }

    public Integer getO2() {
        return o2;
    }

    public void setO2(Integer o2) {
        this.o2 = o2;
    }

    public Integer getEtco2() {
        return etco2;
    }

    public void setEtco2(Integer etco2) {
        this.etco2 = etco2;
    }

    public Integer getPulse() {
        return pulse;
    }

    public void setPulse(Integer pulse) {
        this.pulse = pulse;
    }

    public Integer getSystolic_blood_pressure() {
        return systolic_blood_pressure;
    }

    public void setSystolic_blood_pressure(Integer systolic_blood_pressure) {
        this.systolic_blood_pressure = systolic_blood_pressure;
    }

    public Integer getDiastolic_blood_pressure() {
        return diastolic_blood_pressure;
    }

    public void setDiastolic_blood_pressure(Integer diastolic_blood_pressure) {
        this.diastolic_blood_pressure = diastolic_blood_pressure;
    }

    public Integer getPain() {
        return pain;
    }

    public void setPain(Integer pain) {
        this.pain = pain;
    }

    public Integer getGlycemia() {
        return glycemia;
    }

    public void setGlycemia(Integer glycemia) {
        this.glycemia = glycemia;
    }

    public Integer getNews() {
        return news;
    }

    public void setNews(Integer news) {
        this.news = news;
    }

    public Boolean getEcg() {
        return ecg;
    }

    public void setEcg(Boolean ecg) {
        this.ecg = ecg;
    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Pupils getPupils() {
        return pupils;
    }

    public void setPupils(Pupils pupils) {
        this.pupils = pupils;
    }

    public GlasgowScale getGlasgowScale() {
        return glasgowScale;
    }

    public void setGlasgowScale(GlasgowScale glasgowScale) {
        this.glasgowScale = glasgowScale;
    }
}
