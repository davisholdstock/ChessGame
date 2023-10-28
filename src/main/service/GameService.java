package service;

import chess.ChessGame;
import dataAccess.DataAccessException;
import model.Game;
import server.server;

import java.util.ArrayList;

/**
 * Defines services that can be preformed on a Game object
 */
public class GameService {

    public GameService() {
    }

    /**
     * Creates a new Game
     *
     * @param request of the game being created
     * @return the attempted creating game response
     */
    public CreateGameResponse newGame(CreateGameRequest request, String authToken) {
        //if ()
        Game game = new Game(request.getGameName(), new main.Game(), "", "", (int) (Math.random() * 1000));
        try {
            try {
                server.db.readAuth(authToken);
            } catch (Exception e) {
                return new CreateGameResponse("Error: unauthorized", 401);
            }
            server.db.writeGame(game);
            return new CreateGameResponse(game.gameID());
        } catch (Exception e) {
            e.printStackTrace();
            return new CreateGameResponse("Error: description", 500);
        }
    }

    /**
     * Creates a new Game for testing only
     *
     * @param request of the game being created
     * @return the attempted creating game response
     */
    public CreateGameResponse testNewGame(CreateGameRequest request, int gameID) { //Still need to get Authorization
        Game game = new Game(request.getGameName(), new main.Game(), "", "", gameID);
        try {
            server.db.writeGame(game);
            return new CreateGameResponse(game.gameID());
        } catch (Exception e) {
            e.printStackTrace();
            return new CreateGameResponse("Error: description", 500);
        }
    }

    /**
     * Joins a User to a Game
     *
     * @param request of the game to join
     * @return the attempted joining game response
     */
    public JoinGameResponse joinGame(JoinGameRequest request, String authToken) throws DataAccessException {
        Game game = server.db.readGame(request.getGameID());
        try {
            try {
                server.db.readAuth(authToken);
            } catch (Exception e) {
                return new JoinGameResponse("Error: unauthorized", 401);
            }
            if (request.getPlayerColor() == null) {

            } else if (request.getPlayerColor() == ChessGame.TeamColor.WHITE) {
                if (!game.whiteUsername().isEmpty())
                    return new JoinGameResponse("Error: already taken", 403);
                server.db.updateGame(request.getGameID(), new Game(game.gameName(), game.game(), request.getAuthToken().username(), game.blackUsername(), game.gameID()));
            } else if (request.getPlayerColor() == ChessGame.TeamColor.BLACK) {
                if (!game.blackUsername().isEmpty())
                    return new JoinGameResponse("Error: already taken", 403);
                server.db.updateGame(request.getGameID(), new model.Game(game.gameName(), game.game(), game.whiteUsername(), request.getAuthToken().username(), game.gameID()));
            }
            return new JoinGameResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return new JoinGameResponse("Error: description", 500);
        }
    }///

    /**
     * Lists all the saved games
     *
     * @return the attempted listing games response
     */
    public ListGamesResponse listGames(String authToken) {
        try {
            try {
                server.db.readAuth(authToken);
            } catch (Exception e) {
                return new ListGamesResponse("Error: unauthorized", 401);
            }
            ArrayList<Game> games = new ArrayList<>();
            games = server.db.readAllGame();
            return new ListGamesResponse(games);
        } catch (Exception e) {
            e.printStackTrace();
            return new ListGamesResponse("Error: description", 500);
        }
    }
}
