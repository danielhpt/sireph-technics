package com.sireph.technics.models;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Victim extends _BaseModel {
    private String name;
    private LocalDateTime birthdate;
    private int age;
    private String gender;
    private String identity_number;
    private String address;
    private String circumstances;
    private String disease_history;
    private String allergies;
    private String last_meal;
    private LocalDateTime last_meal_time;
    private String usual_medication;
    private String risk_situation;
    private boolean medical_followup;
    private LocalDateTime hospital_checkin_date;
    private int episode_number;
    private String comments;
    private String type_of_emergency;

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
        this.name = json.getString("name");
        this.birthdate = LocalDateTime.parse(json.getString("birthdate"));
        this.age = json.getInt("age");
        this.gender = json.getString("gender");
        this.identity_number = json.getString("identity_number");
        this.address = json.getString("address");
        this.circumstances = json.getString("circumstances");
        this.disease_history = json.getString("disease_history");
        this.allergies = json.getString("allergies");
        this.last_meal = json.getString("last_meal");
        this.last_meal_time = LocalDateTime.parse(json.getString("last_meal_time"));
        this.episode_number = json.getInt("episode_number");
        this.usual_medication = json.getString("usual_medication");
        this.risk_situation = json.getString("risk_situation");
        this.medical_followup = json.getBoolean("medical_followup");
        this.hospital_checkin_date = LocalDateTime.parse(json.getString("hospital_checkin_date"));
        this.comments = json.getString("comments");
        this.type_of_emergency = json.getString("type_of_emergency");

        int type_of_transport_id;
        try {
            type_of_transport_id = json.getJSONObject("type_of_transport").getInt("id");
        } catch (JSONException e) {
            type_of_transport_id = json.getInt("type_of_transport");
        }
        this.type_of_transport = TypeOfTransport.fromId(type_of_transport_id);
        int non_transport_reason_id;
        try {
            non_transport_reason_id = json.getJSONObject("non_transport_reason").getInt("id");
        } catch (JSONException e) {
            non_transport_reason_id = json.getInt("non_transport_reason");
        }
        this.non_transport_reason = NonTransportReason.fromId(non_transport_reason_id);

        this.hospital = new Hospital(json.getJSONObject("hospital"));

        this.evaluations = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("evaluations").length(); i++) {
            this.evaluations.add(new Evaluation(json.getJSONArray("evaluations").getJSONObject(i)));
        }
        this.pharmacies = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("pharmacies").length(); i++) {
            this.pharmacies.add(new Pharmacy(json.getJSONArray("pharmacies").getJSONObject(i)));
        }

        this.symptom = new Symptom(json.getJSONObject("symptom"));
        this.procedureRCP = new ProcedureRCP(json.getJSONObject("procedure_rcp"));
        this.procedureVentilation = new ProcedureVentilation(json.getJSONObject("procedure_ventilation"));
        this.procedureProtocol = new ProcedureProtocol(json.getJSONObject("procedure_protocol"));
        this.procedureCirculation = new ProcedureCirculation(json.getJSONObject("procedure_circulation"));
        this.procedureScale = new ProcedureScale(json.getJSONObject("procedure_scale"));
    }

    public Victim() {
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("name", this.name);
        json.put("birthdate", this.birthdate.toString());
        json.put("age", this.age);
        json.put("gender", this.gender);
        json.put("identity_number", this.identity_number);
        json.put("address", this.address);
        json.put("circumstances", this.circumstances);
        json.put("disease_history", this.disease_history);
        json.put("allergies", this.allergies);
        json.put("last_meal", this.last_meal);
        json.put("last_meal_time", this.last_meal_time.toString());
        json.put("usual_medication", this.usual_medication);
        json.put("risk_situation", this.risk_situation);
        json.put("medical_followup", this.medical_followup);
        json.put("hospital_checkin_date", this.hospital_checkin_date.toString());
        json.put("episode_number", this.episode_number);
        json.put("comments", this.comments);
        json.put("type_of_emergency", this.type_of_emergency);

        json.put("type_of_transport", this.type_of_transport.toJson());
        json.put("non_transport_reason", this.non_transport_reason.toJson());

        json.put("hospital", this.hospital.toJson());

        JSONArray evaluations = new JSONArray();
        for (int i = 0; i < this.evaluations.size(); i++) {
            evaluations.put(this.evaluations.get(i).toJson());
        }
        json.put("evaluations", evaluations);
        JSONArray pharmacies = new JSONArray();
        for (int i = 0; i < this.pharmacies.size(); i++) {
            pharmacies.put(this.pharmacies.get(i).toJson());
        }
        json.put("pharmacies", pharmacies);

        json.put("symptom", this.symptom.toJson());
        json.put("procedure_rcp", this.procedureRCP.toJson());
        json.put("procedure_ventilation", this.procedureVentilation.toJson());
        json.put("procedure_protocol", this.procedureProtocol.toJson());
        json.put("procedure_circulation", this.procedureCirculation.toJson());
        json.put("procedure_scale", this.procedureScale.toJson());
        return json;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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

    public LocalDateTime getLast_meal_time() {
        return last_meal_time;
    }

    public void setLast_meal_time(LocalDateTime last_meal_time) {
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

    public boolean isMedical_followup() {
        return medical_followup;
    }

    public void setMedical_followup(boolean medical_followup) {
        this.medical_followup = medical_followup;
    }

    public LocalDateTime getHospital_checkin_date() {
        return hospital_checkin_date;
    }

    public void setHospital_checkin_date(LocalDateTime hospital_checkin_date) {
        this.hospital_checkin_date = hospital_checkin_date;
    }

    public int getEpisode_number() {
        return episode_number;
    }

    public void setEpisode_number(int episode_number) {
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

    public List<Pharmacy> getPharmacies() {
        return pharmacies;
    }

    public void setPharmacies(List<Pharmacy> pharmacies) {
        this.pharmacies = pharmacies;
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
