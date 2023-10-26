package handler;

import com.google.gson.Gson;
import service.*;
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
        JoinGameRequest req = (JoinGameRequest) gson.fromJson(request.body(), JoinGameRequest.class);
        GameService service = new GameService();
        JoinGameResponse res = service.joinGame(req);
        return gson.toJson(res);
    }
}
