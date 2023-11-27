package myTests;

import chess.ChessGame;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.SQLDatabase;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.LoginRequest;
import requests.RegisterRequest;
import response.*;
import server.ServerFacade;

import java.util.ArrayList;

public class SeverFacadeTests {
    private final ServerFacade server = new ServerFacade("http://localhost:8080");
    private final DataAccess db = new SQLDatabase();

    @BeforeEach
    public void setup() {
        server.clear();
    }

    @Test
    @DisplayName("Register User")
    public void registerUserSuccess() throws DataAccessException {
        Assertions.assertNotEquals((new RegisterResponse("Error: description", 500)).toString(),
                (server.addUser(new RegisterRequest("user", "password", "user.password@gmail.com"))).toString());
        User user = new User("user", "password", "user.password@gmail.com");

        Assertions.assertNotEquals(new ArrayList<>(), db.getUsers());
        User founduser = db.readUser(user.username());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");
    }

    @Test
    @DisplayName("Register Duplicate User")
    public void registerUserFail() throws DataAccessException {
        Assertions.assertNotEquals((new RegisterResponse("Error: already taken", 403)).toString(),
                (server.addUser(new RegisterRequest("user", "password", "user.password@gmail.com"))).toString());
        Assertions.assertEquals(new RegisterResponse("Bad Request", 500),
                (server.addUser(new RegisterRequest("user", "password", "user.password@gmail.com"))));
        User user = new User("user", "password", "user.password@gmail.com");
        Assertions.assertNotEquals(new ArrayList<>(), db.getUsers());
        User founduser = db.readUser(user.username());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");
    }

    @Test
    @DisplayName("Logout User")
    public void logoutSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = server.addUser(new RegisterRequest(user.username(), user.password(), user.email()));

        Assertions.assertNotEquals(new ArrayList<>(), db.getUsers());
        User founduser = db.readUser(user.username());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");

        Assertions.assertNotEquals((new LogoutResponse("Error: unauthorized", 401)).toString(), (server.logout(res.getAuthToken())).toString());
        Assertions.assertEquals(new ArrayList<>(), db.getAuths());
    }

    @Test
    @DisplayName("Logout Unauthorized User")
    public void logoutUnauthorized() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = server.addUser(new RegisterRequest(user.username(), user.password(), user.email()));

        Assertions.assertNotEquals(new ArrayList<>(), db.getUsers());
        User founduser = db.readUser(user.username());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");

        Assertions.assertEquals(new LogoutResponse("Unauthorized", 401), server.logout("fake auth"));
        Assertions.assertNotEquals(new ArrayList<>(), db.getAuths());
    }

    @Test
    @DisplayName("Login User")
    public void loginSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = server.addUser(new RegisterRequest(user.username(), user.password(), user.email()));

        Assertions.assertNotEquals(new ArrayList<>(), db.getUsers());
        User founduser = db.readUser(user.username());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");

        Assertions.assertNotEquals((new LogoutResponse("Error: unauthorized", 401)).toString(), (server.logout(res.getAuthToken())).toString());
        Assertions.assertEquals(new ArrayList<>(), db.getAuths());

        Assertions.assertNotEquals((new LoginResponse("Error: unauthorized", 401)).toString(), (server.login(new LoginRequest(user.username(),
                user.password()))).toString());
        Assertions.assertNotEquals(new ArrayList<>(), db.getAuths());
    }

    @Test
    @DisplayName("Login Invalid User")
    public void loginInvalidUser() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = server.addUser(new RegisterRequest(user.username(), user.password(), user.email()));

        Assertions.assertNotEquals(new ArrayList<>(), db.getUsers());
        User founduser = db.readUser(user.username());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");

        Assertions.assertNotEquals((new LogoutResponse("Error: unauthorized", 401)).toString(), (server.logout(res.getAuthToken())).toString());
        Assertions.assertEquals(new ArrayList<>(), db.getAuths());

        Assertions.assertEquals(new LoginResponse("Bad Login", 500), server.login(new LoginRequest(user.username(),
                "fake password")));
        Assertions.assertEquals(new ArrayList<>(), db.getAuths());
    }

    @Test
    @DisplayName("Create Game")
    public void createGameSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = server.addUser(new RegisterRequest(user.username(), user.password(), user.email()));


        Assertions.assertNotEquals((new CreateGameResponse("Error: description", 500)).toString(),
                (server.createGame(res.getAuthToken(), new CreateGameRequest("game"))).toString());
        Assertions.assertNotEquals(new ArrayList<>(), db.getGames());
    }

    @Test
    @DisplayName("Create Game Unauthorized")
    public void createGameUnauthorized() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = server.addUser(new RegisterRequest(user.username(), user.password(), user.email()));


        Assertions.assertEquals(new CreateGameResponse("Unauthorized", 401),
                server.createGame("fake auth", new CreateGameRequest("game")));
        Assertions.assertEquals(new ArrayList<>(), db.getGames());
    }

    @Test
    @DisplayName("List All Games")
    public void listGamesSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = server.addUser(new RegisterRequest(user.username(), user.password(), user.email()));

        Assertions.assertNotEquals((new CreateGameResponse("Error: description", 500)).toString(),
                (server.createGame(res.getAuthToken(), new CreateGameRequest("game"))).toString());
        Assertions.assertNotEquals((new CreateGameResponse("Error: description", 500)).toString(),
                (server.createGame(res.getAuthToken(), new CreateGameRequest("game"))).toString());
        Assertions.assertNotEquals((new CreateGameResponse("Error: description", 500)).toString(),
                (server.createGame(res.getAuthToken(), new CreateGameRequest("game"))).toString());
        Assertions.assertNotEquals((new CreateGameResponse("Error: description", 500)).toString(),
                (server.createGame(res.getAuthToken(), new CreateGameRequest("game"))).toString());

        Assertions.assertNotEquals(new ArrayList<>(), db.getGames());

        Assertions.assertNotEquals(new ListGamesResponse("Unauthorized", 401),
                server.listGames(res.getAuthToken()));
    }

    @Test
    @DisplayName("List All Games Unauthorized")
    public void listGamesUnauthorized() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = server.addUser(new RegisterRequest(user.username(), user.password(), user.email()));

        Assertions.assertNotEquals((new CreateGameResponse("Error: description", 500)).toString(),
                (server.createGame(res.getAuthToken(), new CreateGameRequest("game"))).toString());
        Assertions.assertNotEquals((new CreateGameResponse("Error: description", 500)).toString(),
                (server.createGame(res.getAuthToken(), new CreateGameRequest("game"))).toString());
        Assertions.assertNotEquals((new CreateGameResponse("Error: description", 500)).toString(),
                (server.createGame(res.getAuthToken(), new CreateGameRequest("game"))).toString());
        Assertions.assertNotEquals((new CreateGameResponse("Error: description", 500)).toString(),
                (server.createGame(res.getAuthToken(), new CreateGameRequest("game"))).toString());

        Assertions.assertNotEquals(new ArrayList<>(), db.getGames());

        Assertions.assertEquals(new ListGamesResponse("Unauthorized", 401),
                server.listGames("fake auth"));
    }

    @Test
    @DisplayName("Join Game")
    public void joinGameSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = server.addUser(new RegisterRequest(user.username(), user.password(), user.email()));

        CreateGameResponse gameResponse = server.createGame(res.getAuthToken(), new CreateGameRequest("game"));
        Assertions.assertNotEquals(new ArrayList<>(), db.getGames());

        Assertions.assertNotEquals((new JoinGameResponse("Error: unauthorized", 401)).toString(),
                (server.joinGame(res.getAuthToken(), new JoinGameRequest(ChessGame.TeamColor.WHITE, gameResponse.getGameID()))).toString());
    }

    @Test
    @DisplayName("Join Game Unauthorized")
    public void joinGameUnauthorized() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = server.addUser(new RegisterRequest(user.username(), user.password(), user.email()));

        CreateGameResponse gameResponse = server.createGame(res.getAuthToken(), new CreateGameRequest("game"));
        Assertions.assertNotEquals(new ArrayList<>(), db.getGames());

        Assertions.assertEquals(new JoinGameResponse("Unauthorized", 401),
                server.joinGame("fake auth", new JoinGameRequest(ChessGame.TeamColor.WHITE, gameResponse.getGameID())));
    }
}
