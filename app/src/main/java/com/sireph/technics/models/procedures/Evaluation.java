package com.sireph.technics.models.procedures;

import com.sireph.technics.models._BaseModel;
import com.sireph.technics.models.date.DateTime;

import org.json.JSONException;
import org.json.JSONObject;

public class Evaluation extends _BaseModel {
    private DateTime hours;
    private int avds;
    private int ventilation;
    private int spo2;
    private int o2;
    private int etco2;
    private int pulse;
    private boolean ecg;
    private String skin;
    private float temperature;
    private int systolic_blood_pressure;
    private int diastolic_blood_pressure;
    private String pupils;
    private int pain;
    private int glycemia;
    private int news;

    public Evaluation(JSONObject json) throws JSONException {
        super(json);
        this.hours = DateTime.fromJson(json, "hours");
        this.avds = json.getInt("avds");
        this.ventilation = json.getInt("ventilation");
        this.spo2 = json.getInt("spo2");
        this.o2 = json.getInt("o2");
        this.etco2 = json.getInt("etco2");
        this.pulse = json.getInt("pulse");
        this.ecg = json.getBoolean("ecg");
        this.skin = json.getString("skin");
        this.temperature = (float) json.getDouble("temperature");
        this.systolic_blood_pressure = json.getInt("systolic_blood_pressure");
        this.diastolic_blood_pressure = json.getInt("diastolic_blood_pressure");
        this.pupils = json.getString("pupils");
        this.pain = json.getInt("pain");
        this.glycemia = json.getInt("glycemia");
        this.news = json.getInt("news");
    }

    public Evaluation(DateTime hours, int avds, int ventilation, int spo2, int o2, int etco2, int pulse, boolean ecg, String skin, float temperature,
                      int systolic_blood_pressure, int diastolic_blood_pressure, String pupils, int pain, int glycemia, int news) {
        this.hours = hours;
        this.avds = avds;
        this.ventilation = ventilation;
        this.spo2 = spo2;
        this.o2 = o2;
        this.etco2 = etco2;
        this.pulse = pulse;
        this.ecg = ecg;
        this.skin = skin;
        this.temperature = temperature;
        this.systolic_blood_pressure = systolic_blood_pressure;
        this.diastolic_blood_pressure = diastolic_blood_pressure;
        this.pupils = pupils;
        this.pain = pain;
        this.glycemia = glycemia;
        this.news = news;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("hours", this.hours.toString());
        json.put("avds", this.avds);
        json.put("ventilation", this.ventilation);
        json.put("spo2", this.spo2);
        json.put("o2", this.o2);
        json.put("etco2", this.etco2);
        json.put("pulse", this.pulse);
        json.put("ecg", this.ecg);
        json.put("skin", this.skin);
        json.put("temperature", this.temperature);
        json.put("systolic_blood_pressure", this.systolic_blood_pressure);
        json.put("diastolic_blood_pressure", this.diastolic_blood_pressure);
        json.put("pupils", this.pupils);
        json.put("pain", this.pain);
        json.put("glycemia", this.glycemia);
        json.put("news", this.news);
        return json;
    }

    public DateTime getHours() {
        return hours;
    }

    public void setHours(DateTime hours) {
        this.hours = hours;
    }

    public int getAvds() {
        return avds;
    }

    public void setAvds(int avds) {
        this.avds = avds;
    }

    public int getVentilation() {
        return ventilation;
    }

    public void setVentilation(int ventilation) {
        this.ventilation = ventilation;
    }

    public int getSpo2() {
        return spo2;
    }

    public void setSpo2(int spo2) {
        this.spo2 = spo2;
    }

    public int getO2() {
        return o2;
    }

    public void setO2(int o2) {
        this.o2 = o2;
    }

    public int getEtco2() {
        return etco2;
    }

    public void setEtco2(int etco2) {
        this.etco2 = etco2;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public boolean isEcg() {
        return ecg;
    }

    public void setEcg(boolean ecg) {
        this.ecg = ecg;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int getSystolic_blood_pressure() {
        return systolic_blood_pressure;
    }

    public void setSystolic_blood_pressure(int systolic_blood_pressure) {
        this.systolic_blood_pressure = systolic_blood_pressure;
    }

    public int getDiastolic_blood_pressure() {
        return diastolic_blood_pressure;
    }

    public void setDiastolic_blood_pressure(int diastolic_blood_pressure) {
        this.diastolic_blood_pressure = diastolic_blood_pressure;
    }

    public String getPupils() {
        return pupils;
    }

    public void setPupils(String pupils) {
        this.pupils = pupils;
    }

    public int getPain() {
        return pain;
    }

    public void setPain(int pain) {
        this.pain = pain;
    }

    public int getGlycemia() {
        return glycemia;
    }

    public void setGlycemia(int glycemia) {
        this.glycemia = glycemia;
    }

    public int getNews() {
        return news;
    }

    public void setNews(int news) {
        this.news = news;
    }
}
