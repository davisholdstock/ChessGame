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
public class RegisterUserHandler implements Route {
    Gson gson;

    public RegisterUserHandler() {
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
        if (request.body().isEmpty() || req.getUsername() == null || req.getPassword() == null || req.getEmail() == null)
            return gson.toJson(new RegisterResponse("Error: bad request", 400));
        UserService service = new UserService();
        RegisterResponse res = service.registerUser(req);
        response.status(res.getSTATUS_CODE());
        return gson.toJson(res);
    }
}
