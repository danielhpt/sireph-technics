package com.sireph.technics.utils;

import androidx.annotation.NonNull;

import com.sireph.technics.BuildConfig;

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

public class RestApi {
    //private final String server_address = "http://127.0.0.1:8000/";
    private static final String server_address = BuildConfig.API_SERVER + "api/";

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

    private static HttpURLConnection deleteFromApi(String endPoint, String token) throws IOException {
        URL api = new URL(server_address + endPoint);

        HttpURLConnection connection;
        connection = (HttpURLConnection) api.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Authorization", "Token " + token);

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

    // token/
    @NonNull
    public static String getToken(String username, String password) throws Exception {
        JSONObject auth=new JSONObject();
        auth.put("username", username);
        auth.put("password", password);

        URL api = new URL(server_address + "api/token/");
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

        if(code == HttpURLConnection.HTTP_OK){
            return readResponse(connection).getString("token");
        } else {
            throw new Exception("Failed to get Token.");
        }
    }
}
