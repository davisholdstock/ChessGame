package dataAccess;

import model.AuthToken;
import model.Game;
import model.User;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

public class SQLDatabase implements DataAccess {
    private final Database db = new Database();

    public SQLDatabase() {
        createTables();
    }

    public void createTables() {
        try (var conn = db.getConnection()) {
            var createUserTable = """
                       CREATE TABLE IF NOT EXISTS users (
                       id INT NOT NULL AUTO_INCREMENT,
                       username VARCHAR(15) NOT NULL,
                       password VARCHAR(30) NOT NULL,
                       email VARCHAR(75) NOT NULL,
                       authToken CHAR(36),
                       PRIMARY KEY (id),
                       INDEX(username))
                    """;
            try (var createTableStatement = conn.prepareStatement(createUserTable)) {
                createTableStatement.executeUpdate();
            }

            var createGameTable = """
                       CREATE TABLE IF NOT EXISTS games (
                       gameID INT NOT NULL AUTO_INCREMENT,
                       gameName VARCHAR(30) NOT NULL,
                       whiteUsername VARCHAR(15) NOT NULL,
                       blackUsername VARCHAR(15) NOT NULL,
                       PRIMARY KEY (gameID),
                       INDEX(gameName))
                    """;
            try (var createTableStatement = conn.prepareStatement(createGameTable)) {
                createTableStatement.executeUpdate();
            }
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() throws DataAccessException {
        try (var conn = db.getConnection()) {
            try (var createDbStatement = conn.prepareStatement(
                    "DROP DATABASE " + Database.DB_NAME)) {
                createDbStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("Connection failed in clear()");
        }

        createTables();
    }

    @Override
    public User writeUser(User user) throws DataAccessException {
        // FIXME: You can write multiple users
        try (var conn = db.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO users (username, password, email) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, user.username());
                preparedStatement.setString(2, user.password());
                preparedStatement.setString(3, user.email());
                preparedStatement.executeUpdate();

                var resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    // Do something with the ID
                }
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("Connection failed in writeUser()");
        }
    }

    @Override
    public User readUser(String username) throws DataAccessException {
        return null;
    }

    @Override
    public void removeUser(User user) {

    }

    @Override
    public Game writeGame(Game game) throws DataAccessException {
        return null;
    }

    @Override
    public Game readGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public ArrayList<Game> readAllGame() {
        return null;
    }

    @Override
    public Game updateGame(int gameID, Game newGame) throws DataAccessException {
        return null;
    }

    @Override
    public void removeGame(int gameID) {

    }

    @Override
    public AuthToken writeAuth(AuthToken authtoken) throws DataAccessException {
        try (var conn = db.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("UPDATE users SET authToken = ? WHERE username = ?")) {
                preparedStatement.setString(1, authtoken.authToken());
                preparedStatement.setString(2, authtoken.username());
                preparedStatement.executeUpdate();

                return authtoken;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("Connection failed in writeAuth()");
        }
    }

    @Override
    public AuthToken readAuth(String username) throws DataAccessException {
        return null;
    }

    @Override
    public void removeAuth(AuthToken authtoken) {

    }

    @Override
    public Map<String, User> getUsers() {
        return null;
    }

    @Override
    public Map<String, String> getAuths() {
        return null;
    }

    @Override
    public Map<Integer, Game> getGames() {
        return null;
    }
}
