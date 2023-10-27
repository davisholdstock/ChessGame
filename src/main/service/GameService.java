package service;

import dataAccess.DataAccess;
import dataAccess.MemoryDatabase;
import model.Game;

import java.util.ArrayList;

/**
 * Defines services that can be preformed on a Game object
 */
public class GameService {
    public DataAccess db;

    public GameService() {
        db = new MemoryDatabase();
    }

    /**
     * Creates a new Game
     *
     * @param request of the game being created
     * @return the attempted creating game response
     */
    public CreateGameResponse newGame(CreateGameRequest request) {
        //if ()
        try {

        } catch (Exception e) {
            e.printStackTrace();
            return new CreateGameResponse("Error: description");
        }
        return null;
    }

    /**
     * Joins a User to a Game
     *
     * @param request of the game to join
     * @return the attempted joining game response
     */
    public JoinGameResponse joinGame(JoinGameRequest request) {
        return null;
    }

    /**
     * Lists all the saved games
     *
     * @return the attempted listing games response
     */
    public ListGamesResponse listGames() {
        try {
            ArrayList<Game> games = new ArrayList<>();
            return new ListGamesResponse(games);
        } catch (Exception e) {
            e.printStackTrace();
            return new ListGamesResponse("Error: description");
        }
    }
}
