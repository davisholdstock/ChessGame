package dataAccess;

import model.AuthToken;
import model.Game;
import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

public class SQLDatabase implements DataAccess {

    public SQLDatabase() throws SQLException {
        try (var conn = getSQLConnection()) {
            try (var createDbStatement = conn.prepareStatement(
                    "CREATE DATABASE IF NOT EXISTS chess_db")) {
                createDbStatement.executeUpdate();
            }

            conn.setCatalog("chess_db");

            var createUserTable = """
                       CREATE TABLE IF NOT EXISTS user (
                       id INT NOT NULL AUTO_INCREMENT,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       authToken VARCHAR(255),
                       PRIMARY KEY (id),
                       INDEX(username))
                    """;

            try (var createTableStatement = conn.prepareStatement(createUserTable)) {
                createTableStatement.executeUpdate();
            }
        }
    }

    Connection getSQLConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306",
                "root",
                "password"
        );
    }

    @Override
    public void clear() throws DataAccessException {
        try (var conn = getSQLConnection()) {
            try (var createDbStatement = conn.prepareStatement(
                    "DROP DATABASE chess_db")) {
                createDbStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("Connection failed in clear()");
        }
    }

    @Override
    public User writeUser(User user) throws DataAccessException {
        try (var conn = getSQLConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO user (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, user.username());
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
        return null;
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
