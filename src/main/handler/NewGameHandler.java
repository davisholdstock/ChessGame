package handler;

import com.google.gson.Gson;
import service.*;
import spark.Request;
import spark.Response;
import spark.Route;

public class NewGameHandler implements Route {
    Gson gson;

    public NewGameHandler() {
        gson = new Gson();
    }

    /**
     * Handles the creation of a new Game
     *
     * @param request  for the new Game
     * @param response The response from creating a new Game
     * @return
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        //if (request.headers().toString())
        if (request.body().isEmpty())
            return gson.toJson(new CreateGameResponse("Error: bad request", 400));
        CreateGameRequest req = (CreateGameRequest) gson.fromJson(request.body(), CreateGameRequest.class);
        GameService service = new GameService();
        CreateGameResponse res = service.newGame(req);
        response.status(res.getSTATUS_CODE());
        return gson.toJson(res);
    }
}
