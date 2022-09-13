package com.sireph.technics.models;

import static com.sireph.technics.utils.ValueFromJson.boolFromJson;
import static com.sireph.technics.utils.ValueFromJson.intFromJson;
import static com.sireph.technics.utils.ValueFromJson.stringFromJson;

import com.sireph.technics.models.date.Date;
import com.sireph.technics.models.date.DateTime;
import com.sireph.technics.models.enums.Gender;
import com.sireph.technics.models.enums.NonTransportReason;
import com.sireph.technics.models.enums.TypeOfTransport;
import com.sireph.technics.models.procedures.Evaluation;
import com.sireph.technics.models.procedures.Pharmacy;
import com.sireph.technics.models.procedures.ProcedureCirculation;
import com.sireph.technics.models.procedures.ProcedureProtocol;
import com.sireph.technics.models.procedures.ProcedureRCP;
import com.sireph.technics.models.procedures.ProcedureScale;
import com.sireph.technics.models.procedures.ProcedureVentilation;
import com.sireph.technics.models.procedures.Symptom;
import com.sireph.technics.utils.statics.Flag;
import com.sireph.technics.utils.statics.TypeOfJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Victim extends _BaseModel<Victim> {
    private Integer age, episode_number;
    private String name, identity_number, address, circumstances, disease_history, allergies, last_meal, usual_medication, risk_situation, comments,
            type_of_emergency;
    private boolean medical_followup;
    private Date birthdate;
    private DateTime last_meal_time, hospital_checkin_date;

    private Gender gender;
    private TypeOfTransport type_of_transport;
    private NonTransportReason non_transport_reason;

    private Hospital hospital;

    private List<Evaluation> evaluations;
    private List<Pharmacy> pharmacies;

    private Symptom symptom;
    private ProcedureRCP procedureRCP;
    private ProcedureVentilation procedureVentilation;
    private ProcedureProtocol procedureProtocol;
    private ProcedureCirculation procedureCirculation;
    private ProcedureScale procedureScale;

    public Victim(JSONObject json) throws JSONException {
        super(json);
        this.name = stringFromJson(json, "name", "");
        this.gender = Gender.fromJson(json);
        this.identity_number = stringFromJson(json, "identity_number", "");
        this.address = stringFromJson(json, "address", "");
        this.circumstances = stringFromJson(json, "circumstances", "");
        this.disease_history = stringFromJson(json, "disease_history", "");
        this.allergies = stringFromJson(json, "allergies", "");
        this.last_meal = stringFromJson(json, "last_meal", "");
        this.last_meal_time = DateTime.fromJson(json, "last_meal_time");
        this.usual_medication = stringFromJson(json, "usual_medication", "");
        this.risk_situation = stringFromJson(json, "risk_situation", "");
        this.medical_followup = boolFromJson(json, "medical_followup", false);
        this.hospital_checkin_date = DateTime.fromJson(json, "hospital_checkin_date");
        this.comments = stringFromJson(json, "comments", "");
        this.type_of_emergency = stringFromJson(json, "type_of_emergency", "");
        this.birthdate = Date.fromJson(json, "birthdate");
        if (!json.isNull("age")) {
            this.age = intFromJson(json, "age", null);
        } else {
            this.age = null;
        }
        if (!json.isNull("episode_number")) {
            this.episode_number = intFromJson(json, "episode_number", null);
        } else {
            this.episode_number = null;
        }

        this.type_of_transport = TypeOfTransport.fromJson(json);
        this.non_transport_reason = NonTransportReason.fromJson(json);

        if (!json.isNull("hospital")) {
            this.hospital = new Hospital(json.getJSONObject("hospital"));
        } else {
            this.hospital = null;
        }

        this.evaluations = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("evaluations").length(); i++) {
            this.evaluations.add(new Evaluation(json.getJSONArray("evaluations").getJSONObject(i)));
        }
        this.pharmacies = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("pharmacies").length(); i++) {
            this.pharmacies.add(new Pharmacy(json.getJSONArray("pharmacies").getJSONObject(i)));
        }

        if (!json.isNull("symptom")) {
            this.symptom = new Symptom(json.getJSONObject("symptom"));
        } else {
            this.symptom = new Symptom();
        }
        if (!json.isNull("procedure_rcp")) {
            this.procedureRCP = new ProcedureRCP(json.getJSONObject("procedure_rcp"));
        } else {
            this.procedureRCP = new ProcedureRCP();
        }
        if (!json.isNull("procedure_ventilation")) {
            this.procedureVentilation = new ProcedureVentilation(json.getJSONObject("procedure_ventilation"));
        } else {
            this.procedureVentilation = new ProcedureVentilation();
        }
        if (!json.isNull("procedure_protocol")) {
            this.procedureProtocol = new ProcedureProtocol(json.getJSONObject("procedure_protocol"));
        } else {
            this.procedureProtocol = new ProcedureProtocol();
        }
        if (!json.isNull("procedure_circulation")) {
            this.procedureCirculation = new ProcedureCirculation(json.getJSONObject("procedure_circulation"));
        } else {
            this.procedureCirculation = new ProcedureCirculation();
        }
        if (!json.isNull("procedure_scale")) {
            this.procedureScale = new ProcedureScale(json.getJSONObject("procedure_scale"));
        } else {
            this.procedureScale = new ProcedureScale();
        }
    }

    public Victim() {
        this.evaluations = new ArrayList<>();
        this.pharmacies = new ArrayList<>();
        this.symptom = new Symptom();
        this.procedureRCP = new ProcedureRCP();
        this.procedureVentilation = new ProcedureVentilation();
        this.procedureProtocol = new ProcedureProtocol();
        this.procedureCirculation = new ProcedureCirculation();
        this.procedureScale = new ProcedureScale();
        this.gender = Gender.EMPTY;
    }

    @Override
    public ArrayList<Flag> update(Victim updated) {
        ArrayList<Flag> flags = new ArrayList<>();
        if (this.id == null && updated.id != null) {
            this.id = updated.id;
            flags.add(Flag.UPDATED_VICTIM);
        }
        if (!Objects.equals(this.age, updated.age)) {
            this.age = updated.age;
            flags.add(Flag.UPDATED_VICTIM);
        }
        if (!Objects.equals(this.episode_number, updated.episode_number)) {
            this.episode_number = updated.episode_number;
            flags.add(Flag.UPDATED_TRANSPORT);
        }
        if (!Objects.equals(this.name, updated.name)) {
            this.name = updated.name;
            flags.add(Flag.UPDATED_VICTIM);
        }
        if (!Objects.equals(this.identity_number, updated.identity_number)) {
            this.identity_number = updated.identity_number;
            flags.add(Flag.UPDATED_VICTIM);
        }
        if (!Objects.equals(this.address, updated.address)) {
            this.address = updated.address;
            flags.add(Flag.UPDATED_VICTIM);
        }
        if (!Objects.equals(this.circumstances, updated.circumstances)) {
            this.circumstances = updated.circumstances;
            flags.add(Flag.UPDATED_VICTIM);
        }
        if (!Objects.equals(this.disease_history, updated.disease_history)) {
            this.disease_history = updated.disease_history;
            flags.add(Flag.UPDATED_VICTIM);
        }
        if (!Objects.equals(this.allergies, updated.allergies)) {
            this.allergies = updated.allergies;
            flags.add(Flag.UPDATED_VICTIM);
        }
        if (!Objects.equals(this.last_meal, updated.last_meal)) {
            this.last_meal = updated.last_meal;
            flags.add(Flag.UPDATED_VICTIM);
        }
        if (!Objects.equals(this.usual_medication, updated.usual_medication)) {
            this.usual_medication = updated.usual_medication;
            flags.add(Flag.UPDATED_VICTIM);
        }
        if (!Objects.equals(this.risk_situation, updated.risk_situation)) {
            this.risk_situation = updated.risk_situation;
            flags.add(Flag.UPDATED_VICTIM);
        }
        if (!Objects.equals(this.comments, updated.comments)) {
            this.comments = updated.comments;
            flags.add(Flag.UPDATED_VICTIM);
        }
        if (!Objects.equals(this.type_of_emergency, updated.type_of_emergency)) {
            this.type_of_emergency = updated.type_of_emergency;
            flags.add(Flag.UPDATED_VICTIM);
        }
        if (this.medical_followup != updated.medical_followup) {
            this.medical_followup = updated.medical_followup;
            flags.add(Flag.UPDATED_TRANSPORT);
        }
        if (!Objects.equals(this.birthdate, updated.birthdate)) {
            this.birthdate = updated.birthdate;
            flags.add(Flag.UPDATED_VICTIM);
        }
        if (!Objects.equals(this.last_meal_time, updated.last_meal_time)) {
            this.last_meal_time = updated.last_meal_time;
            flags.add(Flag.UPDATED_VICTIM);
        }
        if (!Objects.equals(this.hospital_checkin_date, updated.hospital_checkin_date)) {
            this.hospital_checkin_date = updated.hospital_checkin_date;
            flags.add(Flag.UPDATED_VICTIM);
        }
        if (this.gender != updated.gender) {
            this.gender = updated.gender;
            flags.add(Flag.UPDATED_VICTIM);
        }
        if (!Objects.equals(this.type_of_transport, updated.type_of_transport)) {
            this.type_of_transport = updated.type_of_transport;
            flags.add(Flag.UPDATED_TRANSPORT);
        }
        if (!Objects.equals(this.non_transport_reason, updated.non_transport_reason)) {
            this.non_transport_reason = updated.non_transport_reason;
            flags.add(Flag.UPDATED_TRANSPORT);
        }
        if (!Objects.equals(this.hospital, updated.hospital)) {
            this.hospital = updated.hospital;
            flags.add(Flag.UPDATED_TRANSPORT);
        }
        if (this.evaluations.size() < updated.evaluations.size()) {
            this.evaluations = updated.evaluations;
            flags.add(Flag.ADDED_EVALUATION);
        }
        if (this.pharmacies.size() < updated.pharmacies.size()) {
            this.pharmacies = updated.pharmacies;
            flags.add(Flag.ADDED_PHARMACY);
        }
        flags.addAll(this.symptom.update(updated.symptom));
        flags.addAll(this.procedureRCP.update(updated.procedureRCP));
        flags.addAll(this.procedureVentilation.update(updated.procedureVentilation));
        flags.addAll(this.procedureProtocol.update(updated.procedureProtocol));
        flags.addAll(this.procedureCirculation.update(updated.procedureCirculation));
        flags.addAll(this.procedureScale.update(updated.procedureScale));
        return flags;
    }

    @Override
    public JSONObject toJson(TypeOfJson type) throws JSONException {
        JSONObject json = new JSONObject();
        switch (type) {
            case DETAIL:
            case NORMAL:
                json.put("name", Objects.equals(name, "") ? JSONObject.NULL : name);
                json.put("identity_number", Objects.equals(identity_number, "") ? JSONObject.NULL : identity_number);
                json.put("address", Objects.equals(address, "") ? JSONObject.NULL : address);
                json.put("circumstances", Objects.equals(circumstances, "") ? JSONObject.NULL : circumstances);
                json.put("disease_history", Objects.equals(disease_history, "") ? JSONObject.NULL : disease_history);
                json.put("allergies", Objects.equals(allergies, "") ? JSONObject.NULL : allergies);
                json.put("last_meal", Objects.equals(last_meal, "") ? JSONObject.NULL : last_meal);
                json.put("usual_medication", Objects.equals(usual_medication, "") ? JSONObject.NULL : usual_medication);
                json.put("risk_situation", Objects.equals(risk_situation, "") ? JSONObject.NULL : risk_situation);
                json.put("comments", Objects.equals(comments, "") ? JSONObject.NULL : comments);
                json.put("type_of_emergency", Objects.equals(type_of_emergency, "") ? JSONObject.NULL : type_of_emergency);

                json.put("birthdate", birthdate == null ? JSONObject.NULL : birthdate.toString());
                json.put("last_meal_time", last_meal_time == null ? JSONObject.NULL : last_meal_time.toString());
                json.put("hospital_checkin_date", hospital_checkin_date == null ? JSONObject.NULL : hospital_checkin_date.toString());

                json.put("gender", gender == Gender.EMPTY ? JSONObject.NULL : gender.getValue());

                json.put("age", age == null ? JSONObject.NULL : age);
            case SIMPLE:
                json.put("medical_followup", medical_followup);
                json.put("episode_number", episode_number == null ? JSONObject.NULL : episode_number);
                json.put("type_of_transport", type_of_transport == null ? JSONObject.NULL : type_of_transport.getId());
                json.put("non_transport_reason", non_transport_reason == null ? JSONObject.NULL : this.non_transport_reason.getId());
                json.put("hospital", hospital == null ? JSONObject.NULL : this.hospital.getId());
                break;
        }
        return json;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getIdentity_number() {
        return identity_number;
    }

    public void setIdentity_number(String identity_number) {
        this.identity_number = identity_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCircumstances() {
        return circumstances;
    }

    public void setCircumstances(String circumstances) {
        this.circumstances = circumstances;
    }

    public String getDisease_history() {
        return disease_history;
    }

    public void setDisease_history(String disease_history) {
        this.disease_history = disease_history;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getLast_meal() {
        return last_meal;
    }

    public void setLast_meal(String last_meal) {
        this.last_meal = last_meal;
    }

    public DateTime getLast_meal_time() {
        return last_meal_time;
    }

    public void setLast_meal_time(DateTime last_meal_time) {
        this.last_meal_time = last_meal_time;
    }

    public String getUsual_medication() {
        return usual_medication;
    }

    public void setUsual_medication(String usual_medication) {
        this.usual_medication = usual_medication;
    }

    public String getRisk_situation() {
        return risk_situation;
    }

    public void setRisk_situation(String risk_situation) {
        this.risk_situation = risk_situation;
    }

    public boolean getMedical_followup() {
        return medical_followup;
    }

    public void setMedical_followup(boolean medical_followup) {
        this.medical_followup = medical_followup;
    }

    public DateTime getHospital_checkin_date() {
        return hospital_checkin_date;
    }

    public void setHospital_checkin_date(DateTime hospital_checkin_date) {
        this.hospital_checkin_date = hospital_checkin_date;
    }

    public Integer getEpisode_number() {
        return episode_number;
    }

    public void setEpisode_number(Integer episode_number) {
        this.episode_number = episode_number;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getType_of_emergency() {
        return type_of_emergency;
    }

    public void setType_of_emergency(String type_of_emergency) {
        this.type_of_emergency = type_of_emergency;
    }

    public TypeOfTransport getType_of_transport() {
        return type_of_transport;
    }

    public void setType_of_transport(TypeOfTransport type_of_transport) {
        this.type_of_transport = type_of_transport;
    }

    public NonTransportReason getNon_transport_reason() {
        return non_transport_reason;
    }

    public void setNon_transport_reason(NonTransportReason non_transport_reason) {
        this.non_transport_reason = non_transport_reason;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public void addEvaluation(Evaluation evaluation) {
        this.evaluations.add(evaluation);
    }

    public List<Pharmacy> getPharmacies() {
        return pharmacies;
    }

    public void setPharmacies(List<Pharmacy> pharmacies) {
        this.pharmacies = pharmacies;
    }

    public void addPharmacy(Pharmacy pharmacy) {
        this.pharmacies.add(pharmacy);
    }

    public Symptom getSymptom() {
        return symptom;
    }

    public void setSymptom(Symptom symptom) {
        this.symptom = symptom;
    }

    public ProcedureRCP getProcedureRCP() {
        return procedureRCP;
    }

    public void setProcedureRCP(ProcedureRCP procedureRCP) {
        this.procedureRCP = procedureRCP;
    }

    public ProcedureVentilation getProcedureVentilation() {
        return procedureVentilation;
    }

    public void setProcedureVentilation(ProcedureVentilation procedureVentilation) {
        this.procedureVentilation = procedureVentilation;
    }

    public ProcedureProtocol getProcedureProtocol() {
        return procedureProtocol;
    }

    public void setProcedureProtocol(ProcedureProtocol procedureProtocol) {
        this.procedureProtocol = procedureProtocol;
    }

    public ProcedureCirculation getProcedureCirculation() {
        return procedureCirculation;
    }

    public void setProcedureCirculation(ProcedureCirculation procedureCirculation) {
        this.procedureCirculation = procedureCirculation;
    }

    public ProcedureScale getProcedureScale() {
        return procedureScale;
    }

    public void setProcedureScale(ProcedureScale procedureScale) {
        this.procedureScale = procedureScale;
    }
}
