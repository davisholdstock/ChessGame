package handler;

import service.AuthService;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Handles the Authentication services
 */
public class AuthHandler {
    public AuthHandler() {
    }

    /**
     * Handles logging the user in
     *
     * @param request  to be logged in
     * @param response Login response
     * @return
     */
    public Route Login(Request request, Response response) {
        //LoginRequest request = (LoginRequest) gson.fromJson(reqData, LoginRequest.class);
        AuthService service = new AuthService();
        //LoginResponse result = service.Login(request);
        return null;//gson.toJson(result);
    }

    /**
     * Handles logging the user out
     *
     * @param request  to be logged out
     * @param response Logout response
     * @return
     */
    public Route Logout(Request request, Response response) {
        //LogoutRequest request = (LoginRequest) gson.fromJson(reqData, LogoutRequest.class);
        AuthService service = new AuthService();
        //LogoutResponse result = service.Logout(request);
        return null;//gson.toJson(result);
    }

}
