package com.sireph.technics.utils;

import com.sireph.technics.models.Central;
import com.sireph.technics.models.Hospital;
import com.sireph.technics.models.Occurrence;
import com.sireph.technics.models.OccurrenceState;
import com.sireph.technics.models.Team;
import com.sireph.technics.models.Technician;
import com.sireph.technics.models.Victim;
import com.sireph.technics.models._BaseModel;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RestApi {
    private static final String server_address = "http://192.168.1.65:8000/api/";
    //private static final String server_address = "http://127.0.0.1:8000/api/";
    //private static final String server_address = BuildConfig.API_SERVER + "api/";

    private static HttpURLConnection getFromApi(String endPoint, String token) throws IOException {
        URL api = new URL(server_address + endPoint);

        HttpURLConnection connection;
        connection = (HttpURLConnection) api.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Token " + token);

        return connection;
    }

    private static HttpURLConnection postToApi(String endPoint, String token, String data) throws IOException {
        URL api = new URL(server_address + endPoint);

        HttpURLConnection connection;
        connection = (HttpURLConnection) api.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Token " + token);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        OutputStream os = connection.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
        osw.write(data);
        osw.flush();
        osw.close();
        os.close();

        return connection;
    }

    private static HttpURLConnection putToApi(String endPoint, String token, String data) throws IOException {
        URL api = new URL(server_address + endPoint);

        HttpURLConnection connection;
        connection = (HttpURLConnection) api.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Authorization", "Token " + token);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        OutputStream os = connection.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
        osw.write(data);
        osw.flush();
        osw.close();
        os.close();

        return connection;
    }

    private static JSONObject readResponse(HttpURLConnection connection) throws IOException, JSONException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String output;
        StringBuilder response = new StringBuilder();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        String responseText = response.toString();
        return new JSONObject(responseText);
    }

    private static JSONArray readListResponse(HttpURLConnection connection) throws IOException, JSONException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String output;
        StringBuilder response = new StringBuilder();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        String responseText = response.toString();
        return new JSONArray(responseText);
    }

    private static _BaseModel postObject(String token, String endPoint, _BaseModel object) throws JSONException, IOException {
        HttpURLConnection connection = postToApi(endPoint, token, object.toJson().toString());

        connection.connect();

        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_CREATED) {
            JSONObject response = readResponse(connection);
            object.setId(response.getInt("id"));
            return object;
        }
        return null;
    }

    private static _BaseModel putObject(String token, String endPoint, _BaseModel object) throws JSONException, IOException {
        HttpURLConnection connection = putToApi(endPoint, token, object.toJson().toString());

        connection.connect();

        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            return object;
        }
        return null;
    }

    // login/
    public static JSONObject login(String username, String password) throws Exception {
        JSONObject auth = new JSONObject();
        auth.put("username", username);
        auth.put("password", password);

        URL api = new URL(server_address + "login/");
        HttpURLConnection connection;

        connection = (HttpURLConnection) api.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        OutputStream os = connection.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
        osw.write(auth.toString());
        osw.flush();
        osw.close();
        os.close();

        connection.connect();

        int code = connection.getResponseCode();

        switch (code) {
            case HttpURLConnection.HTTP_OK:
                return readResponse(connection);
            case HttpURLConnection.HTTP_NOT_FOUND:
                throw new Exception("Technician not found");
            case HttpURLConnection.HTTP_BAD_REQUEST:
                throw new Exception("Password incorrect");
            default:
                throw new Exception("Failed to Login");
        }
    }

    // logout/
    public static void logout(String token) throws IOException {
        getFromApi("logout/", token).connect();
    }

    // technician/by_token/
    public static Technician getTechnician(String token) throws IOException, JSONException {
        HttpURLConnection connection = getFromApi("technician/by_token/", token);
        connection.connect();
        JSONObject response = readResponse(connection);
        return new Technician(response);
    }

    // technician/<id>/team/
    public static Team getTechnicianTeam(String token, Technician technician) throws IOException, JSONException {
        HttpURLConnection connection = getFromApi("technician/" + technician.getId().toString() + "/team/", token);
        connection.connect();

        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            JSONObject response = readResponse(connection);
            return new Team(response, technician);
        } else {
            return null;
        }
    }

    // technician/<id>/occurrence/
    public static Occurrence getActiveOccurrence(String token, Technician technician, Team team) throws IOException, JSONException {
        HttpURLConnection connection = getFromApi("technician/" + technician.getId().toString() + "/occurrence/", token);
        connection.connect();

        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            JSONObject response = readResponse(connection);
            return new Occurrence(response, team, technician);
        } else {
            return null;
        }
    }

    // technician/<id>/occurrences/
    public static List<Occurrence> getTechnicianOccurrences(String token, Technician technician, Team team, Occurrence activeOccurrence) throws IOException, JSONException {
        HttpURLConnection connection = getFromApi("technician/" + technician.getId().toString() + "/occurrences/", token);
        connection.connect();

        List<Occurrence> occurrences = new ArrayList<>();

        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            JSONArray response = readListResponse(connection);
            for (int i = 0; i < response.length(); i++) {
                if (activeOccurrence == null || activeOccurrence.getId() != response.getJSONObject(i).optInt("id", 0)) {
                    occurrences.add(new Occurrence(response.getJSONObject(i), team, technician));
                }
            }
        }
        return occurrences;
    }

    // team/<id>/occurrences/
    public static List<Occurrence> getTeamOccurrences(String token, Technician technician, Team team) throws IOException, JSONException {
        HttpURLConnection connection = getFromApi("team/" + team.getId().toString() + "/occurrences/", token);
        connection.connect();

        List<Occurrence> occurrences = new ArrayList<>();

        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            JSONArray response = readListResponse(connection);
            for (int i = 0; i < response.length(); i++) {
                if (!response.getJSONObject(i).getBoolean("active")) {
                    occurrences.add(getOccurrence(token, response.getJSONObject(i).getInt("id"), technician, team));
                }
            }
        }
        return occurrences;
    }

    // occurrences/<id>/
    public static Occurrence getOccurrence(String token, int id, Technician technician, Team team) throws IOException, JSONException {
        HttpURLConnection connection = getFromApi("occurrences/" + id + "/", token);
        connection.connect();

        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            JSONObject response = readResponse(connection);
            return new Occurrence(response, team, technician);
        }
        return null;
    }

    // teams/
    public static Team postTeam(String token, Team team) throws JSONException, IOException {
        return (Team) postObject(token, "teams/", team);
    }

    // teams/<id>/
    public static Team putTeam(String token, Team team) throws JSONException, IOException {
        HttpURLConnection connection = postToApi("teams/" + team.getId() + "/", token, team.toJson().toString());

        connection.connect();

        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            return team;
        }
        return null;
    }

    // centrals/<id>/technicians/
    public static List<Technician> getTechnicians(String token, Technician technician, Central central) throws IOException, JSONException {
        HttpURLConnection connection = getFromApi("centrals/" + central.getId().toString() + "/technicians/", token);
        connection.connect();

        List<Technician> technicians = new ArrayList<>();

        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            JSONArray response = readListResponse(connection);
            for (int i = 0; i < response.length(); i++) {
                if (technician.getId() != response.getJSONObject(i).getInt("id")) {
                    technicians.add(new Technician(response.getJSONObject(i), central));
                }
            }
        }
        return technicians;
    }

    // hospitals/
    public static List<Hospital> getHospitals(String token) throws IOException, JSONException {
        HttpURLConnection connection = getFromApi("hospitals/", token);
        connection.connect();

        List<Hospital> hospitals = new ArrayList<>();

        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            JSONArray response = readListResponse(connection);
            for (int i = 0; i < response.length(); i++) {
                hospitals.add(new Hospital(response.getJSONObject(i)));
            }
        }
        return hospitals;
    }

    // occurrences/<id>/states/
    public static OccurrenceState postOccurrenceState(String token, Occurrence occurrence, OccurrenceState state) throws JSONException, IOException {
        return (OccurrenceState) postObject(token, "occurrences/" + occurrence.getId() + "/states/", state);
    }

    // occurrences/{occurrence_id}/victims/
    public static Victim postOccurrenceVictim(String token, Occurrence occurrence, Victim victim) throws JSONException, IOException {
        return (Victim) postObject(token, "occurrences/" + occurrence.getId() + "/victim/", victim);
    }

    // victims/<id>/
    public static Victim putVictim(String token, Victim victim) throws JSONException, IOException {
        return (Victim) putObject(token, "victims/" + victim.getId() + "/", victim);
    }

    // victims/<id>/evaluations/
    public static Evaluation postVictimEvaluation(String token, Victim victim, Evaluation evaluation) throws JSONException, IOException {
        return (Evaluation) postObject(token, "victims/" + victim.getId() + "/evaluations/", evaluation);
    }

    // victims/<id>/pharmacies/
    public static Pharmacy postVictimPharmacy(String token, Victim victim, Pharmacy pharmacy) throws JSONException, IOException {
        return (Pharmacy) postObject(token, "victims/" + victim.getId() + "/pharmacies/", pharmacy);
    }

    // victims/<id>/procedure_circulation/
    public static ProcedureCirculation postProcedureCirculation(String token, Victim victim, ProcedureCirculation procedureCirculation) throws JSONException, IOException {
        if (procedureCirculation.getId() == null) {
            return (ProcedureCirculation) postObject(token, "victims/" + victim.getId() + "/procedure_circulation/", procedureCirculation);
        } else {
            return (ProcedureCirculation) putObject(token, "victims/" + victim.getId() + "/procedure_circulation/", procedureCirculation);
        }
    }

    // victims/<id>/procedure_protocol/
    public static ProcedureProtocol postProcedureProtocol(String token, Victim victim, ProcedureProtocol procedureProtocol) throws JSONException, IOException {
        if (procedureProtocol.getId() == null) {
            return (ProcedureProtocol) postObject(token, "victims/" + victim.getId() + "/procedure_protocol/", procedureProtocol);
        } else {
            return (ProcedureProtocol) putObject(token, "victims/" + victim.getId() + "/procedure_protocol/", procedureProtocol);
        }
    }

    // victims/<id>/procedure_rcp/
    public static ProcedureRCP postProcedureRCP(String token, Victim victim, ProcedureRCP procedureRCP) throws JSONException, IOException {
        if (procedureRCP.getId() == null) {
            return (ProcedureRCP) postObject(token, "victims/" + victim.getId() + "/procedure_rcp/", procedureRCP);
        } else {
            return (ProcedureRCP) putObject(token, "victims/" + victim.getId() + "/procedure_rcp/", procedureRCP);
        }
    }

    // victims/<id>/procedure_scale/
    public static ProcedureScale postProcedureScale(String token, Victim victim, ProcedureScale procedureScale) throws JSONException, IOException {
        if (procedureScale.getId() == null) {
            return (ProcedureScale) postObject(token, "victims/" + victim.getId() + "/procedure_scale/", procedureScale);
        } else {
            return (ProcedureScale) putObject(token, "victims/" + victim.getId() + "/procedure_scale/", procedureScale);
        }
    }

    // victims/<id>/procedure_ventilation/
    public static ProcedureVentilation postProcedureVentilation(String token, Victim victim, ProcedureVentilation procedureVentilation) throws JSONException, IOException {
        if (procedureVentilation.getId() == null) {
            return (ProcedureVentilation) postObject(token, "victims/" + victim.getId() + "/procedure_ventilation/", procedureVentilation);
        } else {
            return (ProcedureVentilation) putObject(token, "victims/" + victim.getId() + "/procedure_ventilation/", procedureVentilation);
        }
    }

    // victims/<id>/symptom/
    public static Symptom postSymptom(String token, Victim victim, Symptom symptom) throws JSONException, IOException {
        if (symptom.getId() == null) {
            return (Symptom) postObject(token, "victims/" + victim.getId() + "/symptom/", symptom);
        } else {
            return (Symptom) putObject(token, "victims/" + victim.getId() + "/symptom/", symptom);
        }
    }

    //PUT
    ///technician/{technician_id}/occurrence/
    //
    //POST ?
    ///occurrence/
    //
    //POST ?
    ///occurrences/{occurrence_id}/
    //
    //PUT ?
    ///teams/{team_id}/
    //
    //POST ?
    ///teams/{team_id}/occurrences/
}
