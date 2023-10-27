package handler;

import com.google.gson.Gson;
import service.CreateGameResponse;
import service.GameService;
import service.JoinGameRequest;
import service.JoinGameResponse;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoinGameHandler implements Route {
    Gson gson;

    public JoinGameHandler() {
        gson = new Gson();
    }

    /**
     * Handles the joining of a Game
     *
     * @param request  for the Game to join
     * @param response The response from joining a Game
     * @return
     */
    public Object handle(Request request, Response response) throws Exception {
        if (request.body().isEmpty())
            return gson.toJson(new CreateGameResponse("Error: bad request", 400));
        JoinGameRequest req = (JoinGameRequest) gson.fromJson(request.body(), JoinGameRequest.class);
        //req.setAuthToken((AuthToken) gson.fromJson(request.headers(), AuthToken.class));
        GameService service = new GameService();
        JoinGameResponse res = service.joinGame(req);
        response.status(res.getSTATUS_CODE());
        return gson.toJson(res);
    }
}
