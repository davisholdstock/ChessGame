package handler;

import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Handles the Game services
 */
public class GameHandler {
    public GameHandler() {
    }

    /**
     * Handles the creation of a new Game
     *
     * @param request  for the new Game
     * @param response The response from creating a new Game
     * @return
     */
    public Route newGame(Request request, Response response) {
        GameService service = new GameService();
        return null;
    }

    /**
     * Handles the joining of a Game
     *
     * @param request  for the Game to join
     * @param response The response from joining a Game
     * @return
     */
    public Route joinGame(Request request, Response response) {
        GameService service = new GameService();
        return null;
    }

    /**
     * Handles listing of the Games
     *
     * @param request
     * @param response the response from listing the Games
     * @return
     */
    public Route listGames(Request request, Response response) {
        GameService service = new GameService();
        return null;
    }
}
