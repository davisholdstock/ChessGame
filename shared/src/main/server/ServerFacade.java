package server;

import com.google.gson.Gson;
import model.ModelSerializer;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.LoginRequest;
import requests.RegisterRequest;
import response.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }


    public RegisterResponse addUser(RegisterRequest user) {
        var path = "/user";
        return this.makeRequest("POST", path, user, null, RegisterResponse.class);
    }

    public LoginResponse login(LoginRequest user) {
        var path = "/session";
        return this.makeRequest("POST", path, user, null, LoginResponse.class);
    }

    public LogoutResponse logout(String authToken) {
        var path = "/session";
        try {
            return this.makeRequest("DELETE", path, null, authToken, LogoutResponse.class);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public CreateGameResponse createGame(String authToken, CreateGameRequest game) {
        var path = "/game";
        return this.makeRequest("POST", path, game, authToken, CreateGameResponse.class);
    }

    public ListGamesResponse listGames(String authToken) {
        var path = "/game";
        return this.makeRequest("GET", path, null, authToken, ListGamesResponse.class);
    }

    public JoinGameResponse joinGame(String authToken, JoinGameRequest request) {
        var path = "/game";
        return this.makeRequest("PUT", path, request, authToken, JoinGameResponse.class);
    }

    public ClearResponse clear() {
        var path = "/db";
        return this.makeRequest("DELETE", path, null, null, ClearResponse.class);
    }

    private <T> T makeRequest(String method, String path, Object request, String authToken, Class<T> responseClass) {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            http.setDoInput(true);

            http.addRequestProperty("Accept", "application/json");
            if (authToken != null)
                http.addRequestProperty("Authorization", authToken);
            writeBody(request, http);

            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new RuntimeException();
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = ModelSerializer.deserialize(reader, responseClass);
                }
            }
        }
        return response;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}