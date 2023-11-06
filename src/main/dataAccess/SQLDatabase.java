package dataAccess;

import main.Board;
import model.AuthToken;
import model.Game;
import model.User;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
                       whiteUsername VARCHAR(15),
                       blackUsername VARCHAR(15),
                       PRIMARY KEY (gameID),
                       INDEX(gameName))
                    """;
            try (var createTableStatement = conn.prepareStatement(createGameTable)) {
                createTableStatement.executeUpdate();
            }

            var createAuthTable = """
                       CREATE TABLE IF NOT EXISTS authorizations (
                       username VARCHAR(15),
                       authToken CHAR(36),
                       PRIMARY KEY (username),
                       INDEX(authToken))
                    """;
            try (var createTableStatement = conn.prepareStatement(createAuthTable)) {
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
        if (readUser(user.username()) != null)
            throw new DataAccessException("Duplicate User");
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
        try (var conn = db.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE username = ?")) {
                preparedStatement.setString(1, username);
                var resultSet = preparedStatement.executeQuery();

                List<User> data = new ArrayList<>();
                while (resultSet.next()) {
                    String usernameresult = resultSet.getString("username");
                    String passwordresult = resultSet.getString("password");
                    String emailresult = resultSet.getString("email");
                    User newUser = new User(usernameresult, passwordresult, emailresult);
                    data.add(newUser);
                }

                if (data.size() == 1) {
                    return data.get(0);
                } else if (data.size() == 0) {
                    return null;
                }
                throw new RuntimeException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("Connection failed in readUser()");
        }
    }

    @Override
    public void removeUser(User user) {
        // FIXME: Have not tested
        try (var conn = db.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM users WHERE username = ?")) {
                preparedStatement.setString(1, user.username());
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Game writeGame(Game game) throws DataAccessException {
        // FIXME: Have not tested
        try (var conn = db.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO games (gameName) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, game.gameName());
                preparedStatement.executeUpdate();

                var resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    // Do something with the ID
                }
                return game;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("Connection failed in writeGame()");
        }
    }

    @Override
    public Game readGame(int gameID) throws DataAccessException {
        // FIXME: Read One Game
        try (var conn = db.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM games WHERE gameID = ?")) {
                preparedStatement.setString(1, String.valueOf(gameID));
                var resultSet = preparedStatement.executeQuery();

                List<Game> data = new ArrayList<>();
                while (resultSet.next()) {
                    String gameNameResult = resultSet.getString("gameName");
                    main.Game gameResult = resolveGame(resultSet.getString("game"));
                    String whitUserResult = resultSet.getString("whiteUsername");
                    String blackUserResult = resultSet.getString("blackUsername");
                    Game newGame = new Game(gameNameResult, gameResult, whitUserResult, blackUserResult, gameID);
                    data.add(newGame);
                }

                if (data.size() == 1) {
                    return data.get(0);
                }
                throw new DataAccessException("No Game Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("Connection failed in readGame()");
        }
    }

    private main.Game resolveGame(String game) {
        // FIXME: Resolve Game from a string
        main.Game tempGame = new main.Game();
        tempGame.setBoard(new Board());
        return tempGame;
    }

    @Override
    public ArrayList<Game> readAllGame() {
        // FIXME: Read All Games
        return null;
    }

    @Override
    public Game updateGame(int gameID, Game newGame) throws DataAccessException {
        // FIXME: Update Game
        return null;
    }

    @Override
    public void removeGame(int gameID) {
        // FIXME: Have not tested
        try (var conn = db.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM games WHERE gameID = ?")) {
                preparedStatement.setString(1, String.valueOf(gameID));
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public AuthToken writeAuth(AuthToken authtoken) throws DataAccessException {
        try (var conn = db.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO authorizations (username, authToken) VALUES (?, ?)")) {
                preparedStatement.setString(1, authtoken.username());
                preparedStatement.setString(2, authtoken.authToken());
                preparedStatement.executeUpdate();

                return authtoken;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("Connection failed in writeUser()");
        }
    }

    @Override
    public AuthToken readAuth(String username) throws DataAccessException {
        // FIXME: Have not tested
        try (var conn = db.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM authorizations WHERE username = ?")) {
                preparedStatement.setString(1, username);
                var resultSet = preparedStatement.executeQuery();

                List<AuthToken> data = new ArrayList<>();
                while (resultSet.next()) {
                    String usernameresult = resultSet.getString("username");
                    String authTokenresult = resultSet.getString("authToken");
                    AuthToken newAuth = new AuthToken(usernameresult, authTokenresult);
                    data.add(newAuth);
                }

                if (!(data.isEmpty())) {
                    return data.get(0);
                }
                throw new DataAccessException("No Authorization Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("Connection failed in readUser()");
        }
//        return null;
    }

    @Override
    public void removeAuth(AuthToken authtoken) {
        // FIXME: Have not tested
        try (var conn = db.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("UPDATE users SET authToken = ? WHERE username = ?")) {
                preparedStatement.setString(1, null);
                preparedStatement.setString(2, authtoken.username());
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
