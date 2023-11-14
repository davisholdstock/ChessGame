package handler;

import com.google.gson.Gson;
import service.AuthService;
import requests.LoginRequest;
import response.LoginResponse;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginHandler implements Route {
    Gson gson;

    public LoginHandler() {
        gson = new Gson();
    }

    /**
     * Handles logging the user in
     *
     * @param request  to be logged in
     * @param response Login response
     * @return
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        if (request.body() == null) {
            response.status(400);
            return gson.toJson(new LoginResponse("Error: bad request", 400));
        }
        LoginRequest req = (LoginRequest) gson.fromJson(request.body(), LoginRequest.class);
        if (req.getUsername() == null || req.getPassword() == null) {
            response.status(400);
            return gson.toJson(new LoginResponse("Error: bad request", 400));
        }
        AuthService service = new AuthService();
        LoginResponse res = service.Login(req);
        response.status(res.getSTATUS_CODE());
        return gson.toJson(res);
    }
}