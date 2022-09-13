package com.sireph.technics.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import com.sireph.technics.models.procedures.Trauma;
import com.sireph.technics.utils.statics.TypeOfJson;

import org.jetbrains.annotations.Contract;
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
    //private static final String server_address = BuildConfig.API_SERVER + "api/";

    @NonNull
    private static HttpURLConnection getFromApi(String endPoint, String token) throws IOException {
        URL api = new URL(server_address + endPoint);

        HttpURLConnection connection;
        connection = (HttpURLConnection) api.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Token " + token);

        return connection;
    }

    @NonNull
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

    @NonNull
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

    @NonNull
    @Contract("_ -> new")
    private static JSONObject readResponse(@NonNull HttpURLConnection connection) throws IOException, JSONException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String output;
        StringBuilder response = new StringBuilder();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        String responseText = response.toString();
        return new JSONObject(responseText);
    }

    @NonNull
    @Contract("_ -> new")
    private static JSONArray readListResponse(@NonNull HttpURLConnection connection) throws IOException, JSONException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String output;
        StringBuilder response = new StringBuilder();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        String responseText = response.toString();
        return new JSONArray(responseText);
    }

    @SuppressWarnings("rawtypes")
    @Nullable
    private static <T extends _BaseModel> T postObject(String token, String endPoint, @NonNull T object, TypeOfJson type) throws JSONException, IOException {
        HttpURLConnection connection = postToApi(endPoint, token, object.toJson(type).toString());

        connection.connect();

        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_CREATED) {
            JSONObject response = readResponse(connection);
            object.setId(response.optInt("id", -1));
            return object;
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Nullable
    private static <T extends _BaseModel> T putObject(String token, String endPoint, @NonNull T object, TypeOfJson type) throws JSONException, IOException {
        HttpURLConnection connection = putToApi(endPoint, token, object.toJson(type).toString());

        connection.connect();

        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK || code == HttpURLConnection.HTTP_CREATED) {
            return object;
        }
        return null;
    }

    @SuppressWarnings("all")
    @Nullable
    private static <T extends _BaseModel> T postOrPutObject(String token, String endPoint, @NonNull T object, TypeOfJson type) throws JSONException, IOException {
        if (object.getId() == null) {
            return postObject(token, endPoint, object, type);
        } else {
            return putObject(token, endPoint, object, type);
        }
    }

    // login/
    @NonNull
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

    // centrals/<id>/technicians/
    @NonNull
    public static List<Technician> getTechnicians(String token, int technicianId, @NonNull Central central) throws IOException, JSONException {
        HttpURLConnection connection = getFromApi("centrals/" + central.getId().toString() + "/technicians/", token);
        connection.connect();

        List<Technician> technicians = new ArrayList<>();

        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            JSONArray response = readListResponse(connection);
            for (int i = 0; i < response.length(); i++) {
                if (technicianId != response.getJSONObject(i).getInt("id")) {
                    technicians.add(new Technician(response.getJSONObject(i), central));
                }
            }
        }
        return technicians;
    }

    // hospitals/
    @NonNull
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

    // occurrence/
    public static Occurrence postOccurrence(String token, Occurrence occurrence) throws JSONException, IOException {
        return postObject(token, "occurrence/", occurrence, TypeOfJson.NORMAL);
    }

    // occurrences/<id>/
    @Nullable
    public static Occurrence getOccurrence(String token, int occurrenceId, Technician technician, Team team) throws IOException, JSONException {
        HttpURLConnection connection = getFromApi("occurrences/" + occurrenceId + "/", token);
        connection.connect();

        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            JSONObject response = readResponse(connection);
            return new Occurrence(response, team, technician);
        }
        return null;
    }

    // occurrences/<id>/states/
    public static OccurrenceState postOccurrenceState(String token, int occurrenceId, OccurrenceState state) throws JSONException, IOException {
        return postObject(token, "occurrences/" + occurrenceId + "/states/", state, TypeOfJson.NORMAL);
    }

    // occurrences/{occurrence_id}/victims/
    public static Victim postVictim(String token, int occurrenceId, Victim victim) throws JSONException, IOException {
        return postObject(token, "occurrences/" + occurrenceId + "/victims/", victim, TypeOfJson.NORMAL);
    }

    // teams/
    public static Team postTeam(String token, Team team) throws JSONException, IOException {
        return postObject(token, "teams/", team, TypeOfJson.NORMAL);
    }

    // teams/<id>/
    @Nullable
    public static Team putTeam(String token, @NonNull Team team) throws JSONException, IOException {
        HttpURLConnection connection = putToApi("teams/" + team.getId() + "/", token, team.toJson(TypeOfJson.NORMAL).toString());

        connection.connect();

        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            return team;
        }
        return null;
    }

    // team/<id>/occurrences/
    @NonNull
    public static List<Occurrence> getTeamOccurrences(String token, Technician technician, @NonNull Team team) throws IOException, JSONException {
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

    // technician/by_token/
    @NonNull
    @Contract("_ -> new")
    public static Technician getTechnician(String token) throws IOException, JSONException {
        HttpURLConnection connection = getFromApi("technician/by_token/", token);
        connection.connect();
        JSONObject response = readResponse(connection);
        return new Technician(response);
    }

    // technician/<id>/occurrence/
    @Nullable
    public static Occurrence getActiveOccurrence(String token, @NonNull Technician technician, Team team) throws IOException, JSONException {
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

    // technician/<id>/occurrence/
    public static Occurrence putOccurrence(String token, int technicianId, Occurrence occurrence) throws JSONException, IOException {
        return putObject(token, "technician/" + technicianId + "/occurrence/", occurrence, TypeOfJson.DETAIL);
    }

    // technician/<id>/occurrences/
    @NonNull
    public static List<Occurrence> getOccurrences(String token, @NonNull Technician technician, Team team, Occurrence activeOccurrence) throws IOException, JSONException {
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

    // technician/<id>/team/
    @Nullable
    public static Team getTeam(String token, @NonNull Technician technician) throws IOException, JSONException {
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

    // victims/<id>/
    public static Victim putVictim(String token, @NonNull Victim victim) throws JSONException, IOException {
        return putObject(token, "victims/" + victim.getId() + "/", victim, TypeOfJson.NORMAL);
    }

    // victims/<id>/evaluations/
    public static Evaluation postEvaluation(String token, int victimId, @NonNull Evaluation evaluation) throws JSONException, IOException {
        return postObject(token, "victims/" + victimId + "/evaluations/", evaluation, TypeOfJson.NORMAL);
    }

    // victims/<id>/pharmacies/
    public static Pharmacy postPharmacy(String token, int victimId, @NonNull Pharmacy pharmacy) throws JSONException, IOException {
        return postObject(token, "victims/" + victimId + "/pharmacies/", pharmacy, TypeOfJson.NORMAL);
    }

    // victims/<id>/procedure_circulation/
    public static ProcedureCirculation postProcedureCirculation(String token, int victimId, @NonNull ProcedureCirculation procedureCirculation) throws JSONException, IOException {
        return postOrPutObject(token, "victims/" + victimId + "/procedure_circulation/", procedureCirculation, TypeOfJson.NORMAL);
    }

    // victims/<id>/procedure_protocol/
    public static ProcedureProtocol postProcedureProtocol(String token, int victimId, @NonNull ProcedureProtocol procedureProtocol) throws JSONException, IOException {
        return postOrPutObject(token, "victims/" + victimId + "/procedure_protocol/", procedureProtocol, TypeOfJson.NORMAL);
    }

    // victims/<id>/procedure_rcp/
    public static ProcedureRCP postProcedureRCP(String token, int victimId, @NonNull ProcedureRCP procedureRCP) throws JSONException, IOException {
        return postOrPutObject(token, "victims/" + victimId + "/procedure_rcp/", procedureRCP, TypeOfJson.NORMAL);
    }

    // victims/<id>/procedure_scale/
    public static ProcedureScale postProcedureScale(String token, int victimId, @NonNull ProcedureScale procedureScale) throws JSONException, IOException {
        return postOrPutObject(token, "victims/" + victimId + "/procedure_scale/", procedureScale, TypeOfJson.NORMAL);
    }

    // victims/<id>/procedure_ventilation/
    public static ProcedureVentilation postProcedureVentilation(String token, int victimId, @NonNull ProcedureVentilation procedureVentilation) throws JSONException, IOException {
        return postOrPutObject(token, "victims/" + victimId + "/procedure_ventilation/", procedureVentilation, TypeOfJson.NORMAL);
    }

    // victims/<id>/symptom/
    public static Symptom postSymptom(String token, int victimId, @NonNull Symptom symptom) throws JSONException, IOException {
        return postOrPutObject(token, "victims/" + victimId + "/symptom/", symptom, TypeOfJson.NORMAL);
    }

    // victims/<id>/symptom/traumas/
    public static Trauma postTrauma(String token, int victimId, @NonNull Trauma trauma) throws JSONException, IOException {
        return postObject(token, "victims/" + victimId + "/symptom/traumas/", trauma, TypeOfJson.NORMAL);
    }

    // victims/<id>/transport/
    public static Victim putTransport(String token, @NonNull Victim victim) throws JSONException, IOException {
        return putObject(token, "victims/" + victim.getId() + "/transport/", victim, TypeOfJson.SIMPLE);
    }
}
