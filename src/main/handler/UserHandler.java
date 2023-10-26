package handler;

import com.google.gson.Gson;
import service.RegisterRequest;
import service.RegisterResponse;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;


/**
 * Handles the User service
 */
public class UserHandler implements Route {
    Gson gson;

    public UserHandler() {
        gson = new Gson();
    }

    /**
     * Handles registering a new user
     *
     * @param request  of the new user to be registered
     * @param response the response from the registration
     * @return
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        RegisterRequest req = (RegisterRequest) gson.fromJson(request.body(), RegisterRequest.class);
        UserService service = new UserService();
        RegisterResponse res = service.registerUser(req);
        return gson.toJson(res);
    }
}
