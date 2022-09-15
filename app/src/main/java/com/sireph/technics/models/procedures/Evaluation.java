package com.sireph.technics.models.procedures;

import static com.sireph.technics.utils.ValueFromJson.boolFromJson;
import static com.sireph.technics.utils.ValueFromJson.doubleFromJson;
import static com.sireph.technics.utils.ValueFromJson.intFromJson;

import androidx.annotation.NonNull;

import com.sireph.technics.models.date.DateTime;
import com.sireph.technics.models.enums.AVDS;
import com.sireph.technics.models.enums.Pupils;
import com.sireph.technics.models.enums.Skin;
import com.sireph.technics.table.components.Cell;
import com.sireph.technics.utils.statics.Flag;
import com.sireph.technics.utils.statics.TypeOfJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Evaluation extends _BaseTableModel<Evaluation> {
    private DateTime hours;
    private AVDS avds;
    private GlasgowScale glasgowScale;
    private Integer ventilation;
    private Integer spo2;
    private Integer o2;
    private Integer etco2;
    private Integer pulse;
    private boolean ecg;
    private Skin skin;
    private Double temperature;
    private Integer systolic_blood_pressure;
    private Integer diastolic_blood_pressure;
    private Pupils pupils;
    private Integer pain;
    private Integer glycemia;
    private Integer news;

    public Evaluation(JSONObject json) throws JSONException {
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
        if (!json.isNull("glasgow_scale")) {
            this.glasgowScale = new GlasgowScale(json.getJSONObject("glasgow_scale"));
        } else {
            this.glasgowScale = new GlasgowScale();
        }
    }

    public Evaluation(DateTime hours, AVDS avds, Integer vent, Integer spo2, Integer o2sup, Integer etco2, Integer pulse, Integer systolic,
                      Integer diastolic, Integer pain, Integer glycemia, Integer news, boolean ecg, Skin skin, Double temperature, Pupils pupils,
                      GlasgowScale glasgowScale) {
        this.hours = hours;
        this.avds = avds;
        this.ventilation = vent;
        this.spo2 = spo2;
        this.o2 = o2sup;
        this.etco2 = etco2;
        this.pulse = pulse;
        this.systolic_blood_pressure = systolic;
        this.diastolic_blood_pressure = diastolic;
        this.pain = pain;
        this.glycemia = glycemia;
        this.news = news;
        this.ecg = ecg;
        this.skin = skin;
        this.temperature = temperature;
        this.pupils = pupils;
        this.glasgowScale = glasgowScale;
    }

    @NonNull
    @Override
    public List<Cell> toCellList() {
        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell(hours == null ? "" : hours.format("HH:mm")));
        cells.add(new Cell(avds == null ? "" : avds.toString()));
        cells.add(new Cell(glasgowScale == null ? "" : Integer.toString(glasgowScale.getTotal())));
        cells.add(new Cell(ventilation == null ? "" : ventilation.toString()));
        cells.add(new Cell(spo2 == null ? "" : spo2.toString()));
        cells.add(new Cell(o2 == null ? "" : o2.toString()));
        cells.add(new Cell(pulse == null ? "" : pulse.toString()));
        cells.add(new Cell(skin == null ? "" : skin.toString()));
        cells.add(new Cell(temperature == null ? "" : temperature.toString()));
        cells.add(new Cell(systolic_blood_pressure == null ? "" : systolic_blood_pressure.toString()));
        cells.add(new Cell(diastolic_blood_pressure == null ? "" : diastolic_blood_pressure.toString()));
        cells.add(new Cell(pupils == null ? "" : pupils.toString()));
        cells.add(new Cell(pain == null ? "" : pain.toString()));
        cells.add(new Cell(glycemia == null ? "" : glycemia.toString()));
        cells.add(new Cell(ecg ? "X" : ""));
        cells.add(new Cell(etco2 == null ? "" : etco2.toString()));
        cells.add(new Cell(news == null ? "" : news.toString()));
        return cells;
    }

    @Override
    public JSONObject toJson(@NonNull TypeOfJson type) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("hours", this.hours.toString());

        json.put("avds", this.avds == AVDS.EMPTY ? JSONObject.NULL : this.avds.toString());
        json.put("skin", this.skin == Skin.EMPTY ? JSONObject.NULL : this.skin.toString());
        json.put("pupils", this.pupils == Pupils.EMPTY ? JSONObject.NULL : this.pupils.toString());

        json.put("glasgow_scale", this.glasgowScale == null ? JSONObject.NULL : this.glasgowScale.toJson(type));

        json.put("ventilation", this.ventilation == null ? JSONObject.NULL : ventilation);
        json.put("spo2", this.spo2 == null ? JSONObject.NULL : spo2);
        json.put("o2", this.o2 == null ? JSONObject.NULL : o2);
        json.put("etco2", this.etco2 == null ? JSONObject.NULL : etco2);
        json.put("pulse", this.pulse == null ? JSONObject.NULL : pulse);
        json.put("ecg", this.ecg);
        json.put("temperature", this.temperature == null ? JSONObject.NULL : temperature);
        json.put("systolic_blood_pressure", this.systolic_blood_pressure == null ? JSONObject.NULL : systolic_blood_pressure);
        json.put("diastolic_blood_pressure", this.diastolic_blood_pressure == null ? JSONObject.NULL : diastolic_blood_pressure);
        json.put("pain", this.pain == null ? JSONObject.NULL : pain);
        json.put("glycemia", this.glycemia == null ? JSONObject.NULL : glycemia);
        json.put("news", this.news == null ? JSONObject.NULL : news);
        return json;
    }

    @Override
    public ArrayList<Flag> update(@NonNull Evaluation updated) {
        return null;
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

    public boolean getEcg() {
        return ecg;
    }

    public void setEcg(boolean ecg) {
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
