package dataAccess;

import main.Board;
import model.AuthToken;
import model.Game;
import model.User;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
                       gameBoard VARCHAR(255) NOT NULL,
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
                       PRIMARY KEY (authToken),
                       INDEX(username))
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
                } else if (data.isEmpty()) {
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
    public Game writeGame(String gameName) throws DataAccessException {
        // FIXME: Need to serialize main.Game
        try (var conn = db.getConnection()) {
            main.Game newGame = new main.Game();
            String gameString = newGame.fenNotation();
            try (var preparedStatement = conn.prepareStatement("INSERT INTO games (gameName, gameBoard) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, gameName);
                preparedStatement.setString(2, gameString);
                preparedStatement.executeUpdate();

                var resultSet = preparedStatement.getGeneratedKeys();
                int id = 0;
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                } else {
                    throw new DataAccessException("Invalid request");
                }
                return new Game(gameName, newGame, null, null, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("Connection failed in writeGame()");
        }
    }

    @Override
    public Game readGame(int gameID) throws DataAccessException {
        // FIXME: Have not tested
        try (var conn = db.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM games WHERE gameID = ?")) {
                preparedStatement.setString(1, String.valueOf(gameID));
                var resultSet = preparedStatement.executeQuery();

                List<Game> data = new ArrayList<>();
                while (resultSet.next()) {
                    String gameNameResult = resultSet.getString("gameName");
//                    String gameResult = resultSet.getString("gameBoard");
                    main.Game gameResult = new main.Game(resultSet.getString("gameBoard"));
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
    public ArrayList<Game> readAllGame() throws DataAccessException {
        // FIXME: Needs to resolve game from string
        try (var conn = db.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM games")) {
                var resultSet = preparedStatement.executeQuery();

                ArrayList<Game> data = new ArrayList<>();
                while (resultSet.next()) {
                    String gameNameResult = resultSet.getString("gameName");
                    String gameResult = resultSet.getString("gameBoard");
//                    main.Game gameResult = resolveGame(resultSet.getString("game"));
                    String whitUserResult = resultSet.getString("whiteUsername");
                    String blackUserResult = resultSet.getString("blackUsername");
                    int gameIDResult = resultSet.getInt(1);
                    Game newGame = new Game(gameNameResult, new main.Game(), whitUserResult, blackUserResult, gameIDResult);
                    data.add(newGame);
                }

                return data;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("Connection failed in readAllGames()");
        }
    }

    @Override
    public Game updateGame(int gameID, Game newGame) throws DataAccessException {
        // FIXME: Update Game
        Game updatedGame = new Game(newGame.gameName(), newGame.game(), newGame.whiteUsername(), newGame.blackUsername(), newGame.gameID());
        try (var conn = db.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("UPDATE games SET gameBoard = ?, whiteUsername = ?, blackUsername = ? WHERE gameID = ?")) {
                preparedStatement.setString(1, "newGame.toString()");
                preparedStatement.setString(2, newGame.whiteUsername());
                preparedStatement.setString(3, newGame.blackUsername());
                preparedStatement.setInt(4, gameID);
                var resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new DataAccessException("bla bla bla");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("something");
        }
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
    public AuthToken readAuth(String authToken) throws DataAccessException {
        try (var conn = db.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM authorizations WHERE authToken = ?")) {
                preparedStatement.setString(1, authToken);
                var resultSet = preparedStatement.executeQuery();

                List<AuthToken> data = new ArrayList<>();
                while (resultSet.next()) {
                    String usernameresult = resultSet.getString("username");
                    String authTokenresult = resultSet.getString("authToken");
                    AuthToken newAuth = new AuthToken(authTokenresult, usernameresult);
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
    }

    @Override
    public void removeAuth(AuthToken authtoken) {
        try (var conn = db.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM authorizations WHERE username = ?")) {
                preparedStatement.setString(1, authtoken.username());
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<User> getUsers() throws DataAccessException {
        // FIXME: Needs to resolve game from string
        try (var conn = db.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM users")) {
                var resultSet = preparedStatement.executeQuery();

                ArrayList<User> data = new ArrayList<>();
                while (resultSet.next()) {
                    String usernameResult = resultSet.getString("username");
                    String passwordResult = resultSet.getString("password");
                    String emailResult = resultSet.getString("email");
                    User newUser = new User(usernameResult, passwordResult, emailResult);
                    data.add(newUser);
                }

                return data;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("Connection failed in readAllGames()");
        }
    }

    @Override
    public ArrayList<AuthToken> getAuths() throws DataAccessException {
        // FIXME: Needs to resolve game from string
        try (var conn = db.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM authorizations")) {
                var resultSet = preparedStatement.executeQuery();

                ArrayList<AuthToken> data = new ArrayList<>();
                while (resultSet.next()) {
                    String authTokenResult = resultSet.getString("authToken");
                    String usernameResult = resultSet.getString("username");
                    AuthToken newAuth = new AuthToken(authTokenResult, usernameResult);
                    data.add(newAuth);
                }

                return data;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("Connection failed in readAllGames()");
        }
    }

    @Override
    public ArrayList<Game> getGames() throws DataAccessException {
        // FIXME: Needs to resolve game from string
        try (var conn = db.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM games")) {
                var resultSet = preparedStatement.executeQuery();

                ArrayList<Game> data = new ArrayList<>();
                while (resultSet.next()) {
                    String gameNameResult = resultSet.getString("gameName");
                    String gameResult = resultSet.getString("gameBoard");
//                    main.Game gameResult = resolveGame(resultSet.getString("game"));
                    String whitUserResult = resultSet.getString("whiteUsername");
                    String blackUserResult = resultSet.getString("blackUsername");
                    int gameIDResult = resultSet.getInt(1);
                    Game newGame = new Game(gameNameResult, new main.Game(), whitUserResult, blackUserResult, gameIDResult);
                    data.add(newGame);
                }

                return data;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("Connection failed in readAllGames()");
        }
    }
}
