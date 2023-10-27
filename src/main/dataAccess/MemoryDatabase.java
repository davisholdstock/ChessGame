package dataAccess;

import model.AuthToken;
import model.Game;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MemoryDatabase implements DataAccess {
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, String> auths = new HashMap<>();
    private final Map<Integer, Game> games = new HashMap<>();

    @Override
    public void clear() {
        users.clear();
        games.clear();
        auths.clear();
    }

    @Override
    public User writeUser(User user) throws DataAccessException {
        if (users.get(user.username()) == null) {
            users.put(user.username(), user);
            return user;
        }
        throw new DataAccessException("Duplicate User");
    }

    @Override
    public User readUser(String username) throws DataAccessException {
        if (users.get(username) != null) {
            return users.get(username);
        }
        throw new DataAccessException("No User Found");
    }

    @Override
    public void removeUser(User user) {
        if (users.get(user.username()) != null)
            users.remove(user.username(), user);
    }

    @Override
    public Game writeGame(Game game) throws DataAccessException {
        if (games.get(game.gameID()) == null) {
            games.put(game.gameID(), game);
            return game;
        }
        throw new DataAccessException("Duplicate Game");
    }

    @Override
    public Game readGame(int gameID) throws DataAccessException {
        if (games.get(gameID) != null) {
            return games.get(gameID);
        }
        throw new DataAccessException("No Game Found");
    }

    @Override
    public ArrayList<Game> readAllGame() {
        ArrayList<Game> listGames = new ArrayList<>();
        Iterator gameIterator = games.entrySet().iterator();
        while (gameIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) gameIterator.next();
            listGames.add((Game) mapElement.getValue());
        }
        return listGames;
    }

    @Override
    public Game updateGame(int gameID, Game newGame) throws DataAccessException {
        if (games.get(gameID) != null) {
            Game updatedGame = new Game(newGame.gameName(), newGame.game(), newGame.whiteUsername(), newGame.blackUsername(), newGame.gameID());
            removeGame(gameID);
            writeGame(updatedGame);
            return updatedGame;
        }
        throw new DataAccessException("Game Does Not Exist");
    }

    @Override
    public void removeGame(int gameID) {
        if (games.get(gameID) != null)
            games.remove(gameID, games.get(gameID));
    }

    @Override
    public AuthToken writeAuth(AuthToken authtoken) throws DataAccessException {
        if (users.get(authtoken.username()) != null) {
            auths.put(authtoken.username(), authtoken.authToken());
            return authtoken;
        }
        throw new DataAccessException("User does not exist");
    }

    @Override
    public AuthToken readAuth(String auth) throws DataAccessException {
        if (auths.containsValue(auth)) {
            Iterator authIterator = auths.entrySet().iterator();
            while (authIterator.hasNext()) {
                Map.Entry mapElement = (Map.Entry) authIterator.next();
                if (mapElement.getValue().equals(auth))
                    return new AuthToken(mapElement.getValue().toString(), mapElement.getKey().toString());
            }
        }
        throw new DataAccessException("No Authorization Found");
    }

    @Override
    public void removeAuth(AuthToken authtoken) {
        if (auths.get(authtoken.username()) != null)
            auths.remove(authtoken.username(), authtoken.authToken());
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public Map<String, String> getAuths() {
        return auths;
    }

    public Map<Integer, Game> getGames() {
        return games;
    }
}
