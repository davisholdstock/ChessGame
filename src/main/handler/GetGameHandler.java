package handler;

import com.google.gson.Gson;
import requests.ListGameRequest;
import response.ListGameResponse;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetGameHandler implements Route {
    Gson gson;

    public GetGameHandler() {
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
            return gson.toJson(new ListGameResponse("Error: bad request", 400));
        }
        ListGameRequest req = (ListGameRequest) gson.fromJson(request.body(), ListGameRequest.class);
        if (req.getGameID() == null) {
            response.status(400);
            return gson.toJson(new ListGameResponse("Error: bad request", 400));
        }
        GameService service = new GameService();
        ListGameResponse res = service.listOneGame(req, request.headers("Authorization"));
        response.status(res.getSTATUS_CODE());
        return gson.toJson(res);
    }
}
