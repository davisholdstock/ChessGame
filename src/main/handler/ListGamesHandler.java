package handler;

import com.google.gson.Gson;
import service.GameService;
import service.ListGamesResponse;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Handles the Game services
 */
public class ListGamesHandler implements Route {
    Gson gson;

    public ListGamesHandler() {
        gson = new Gson();
    }

    /**
     * Handles listing of the Games
     *
     * @param request
     * @param response the response from listing the Games
     * @return
     */
    public Object handle(Request request, Response response) throws Exception {
        GameService service = new GameService();
        ListGamesResponse res = service.listGames();
        response.status(res.getSTATUS_CODE());
        return gson.toJson(res);
    }
}
