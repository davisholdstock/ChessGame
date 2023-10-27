package handler;

import com.google.gson.Gson;
import service.AuthService;
import service.LogoutResponse;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Handles the Authentication services
 */
public class LogoutHandler implements Route {
    Gson gson;

    public LogoutHandler() {
        gson = new Gson();
    }

    /**
     * Handles logging the user out
     *
     * @param request  to be logged out
     * @param response Logout response
     * @return
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        AuthService service = new AuthService();
        LogoutResponse res = service.Logout(request.headers().toString());
        response.status(res.getSTATUS_CODE());
        return gson.toJson(res);
    }
}
