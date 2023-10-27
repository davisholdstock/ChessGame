package handler;

import com.google.gson.Gson;
import service.AuthService;
import service.LoginRequest;
import service.LoginResponse;
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
        LoginRequest req = (LoginRequest) gson.fromJson(request.body(), LoginRequest.class);
        AuthService service = new AuthService();
        LoginResponse res = service.Login(req);
        response.status(res.getSTATUS_CODE());
        return gson.toJson(res);
    }
}