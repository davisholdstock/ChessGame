package handler;

import com.google.gson.Gson;
import requests.JoinGameRequest;
import response.JoinGameResponse;
import service.GameService;
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
        if (request.body() == null) {
            response.status(400);
            return gson.toJson(new JoinGameResponse("Error: bad request", 400));
        }
        JoinGameRequest req = (JoinGameRequest) gson.fromJson(request.body(), JoinGameRequest.class);
        if (req.getGameID() == null) {
            response.status(400);
            return gson.toJson(new JoinGameResponse("Error: bad request", 400));
        }
        GameService service = new GameService();
        JoinGameResponse res = service.joinGame(req, request.headers("Authorization"));
        response.status(res.getSTATUS_CODE());
        return gson.toJson(res);
    }
}
