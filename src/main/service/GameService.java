package service;

import chess.ChessGame;
import dataAccess.DataAccessException;
import model.Game;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.ListGameRequest;
import response.CreateGameResponse;
import response.JoinGameResponse;
import response.ListGameResponse;
import response.ListGamesResponse;
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
        try {
            try {
                server.db.readAuth(authToken);
            } catch (Exception e) {
                return new CreateGameResponse("Error: unauthorized", 401);
            }
            Game game = server.db.writeGame(request.getGameName());
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
        try {
            Game game = server.db.readGame(request.getGameID());
            try {
                try {
                    server.db.readAuth(authToken);
                } catch (Exception e) {
                    return new JoinGameResponse("Error: unauthorized", 401);
                }
                if (request.getPlayerColor() == null) {

                } else if (request.getPlayerColor() == ChessGame.TeamColor.WHITE) {
                    if (game.whiteUsername() != null)
                        return new JoinGameResponse("Error: already taken", 403);
                    model.Game updatedgame = new model.Game(game.gameName(), game.game(), server.db.readAuth(authToken).username(), game.blackUsername(), game.gameID());
                    server.db.updateGame(request.getGameID(), updatedgame, ChessGame.TeamColor.WHITE);
                } else if (request.getPlayerColor() == ChessGame.TeamColor.BLACK) {
                    if (game.blackUsername() != null)
                        return new JoinGameResponse("Error: already taken", 403);
                    server.db.updateGame(request.getGameID(), new model.Game(game.gameName(), game.game(), game.whiteUsername(), server.db.readAuth(authToken).username(), game.gameID()), ChessGame.TeamColor.BLACK);
                }
                return new JoinGameResponse();
            } catch (Exception e) {
                e.printStackTrace();
                return new JoinGameResponse("Error: description", 500);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JoinGameResponse("Error: bad request", 400);
        }
    }

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

    public ListGameResponse listOneGame(ListGameRequest request, String authToken) {
        try {
            Game game = server.db.readGame(request.getGameID());
            try {
                server.db.readAuth(authToken);
            } catch (Exception e) {
                return new ListGameResponse("Error: unauthorized", 401);
            }
            return new ListGameResponse(game);
        } catch (Exception e) {
            e.printStackTrace();
            return new ListGameResponse("Error: bad request", 400);
        }
    }
}
