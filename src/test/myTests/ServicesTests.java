package myTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
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
import server.server;
import service.*;

import java.util.ArrayList;

public class ServicesTests {
    private final AuthService authService = new AuthService();
    private final TestingService testingService = new TestingService();
    private final UserService userService = new UserService();
    private final GameService gameService = new GameService();

    @BeforeEach
    public void setup() throws DataAccessException {
        server.db.clear();
    }

    @Test
    @DisplayName("Register User")
    public void registerUserSuccess() throws DataAccessException {
        Assertions.assertNotEquals((new RegisterResponse("Error: description", 500)).toString(),
                (userService.registerUser(new RegisterRequest("user", "password", "user.password@gmail.com"))).toString());
        User user = new User("user", "password", "user.password@gmail.com");

        Assertions.assertNotEquals(new ArrayList<>(), server.db.getUsers());
        User founduser = server.db.readUser(user.username());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");

    }

    @Test
    @DisplayName("Register Duplicate User")
    public void registerUserFail() throws DataAccessException {
        Assertions.assertNotEquals((new RegisterResponse("Error: already taken", 403)).toString(),
                (userService.registerUser(new RegisterRequest("user", "password", "user.password@gmail.com"))).toString());
        Assertions.assertEquals((new RegisterResponse("Error: already taken", 403)).toString(),
                (userService.registerUser(new RegisterRequest("user", "password", "user.password@gmail.com"))).toString());
        User user = new User("user", "password", "user.password@gmail.com");

        Assertions.assertNotEquals(new ArrayList<>(), server.db.getUsers());
        User founduser = server.db.readUser(user.username());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");

    }

    @Test
    @DisplayName("Clear Data")
    public void clearDataSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        Assertions.assertNotEquals((new RegisterResponse("Error: description", 500)).toString(),
                (userService.registerUser(new RegisterRequest(user.username(), user.password(), user.email()))).toString());
        User user1 = new User("user1", "password", "user.password@gmail.com");
        Assertions.assertNotEquals((new RegisterResponse("Error: description", 500)).toString(),
                (userService.registerUser(new RegisterRequest(user1.username(), user1.password(), user1.email()))).toString());
        User user2 = new User("user2", "password", "user.password@gmail.com");
        Assertions.assertNotEquals((new RegisterResponse("Error: description", 500)).toString(),
                (userService.registerUser(new RegisterRequest(user2.username(), user2.password(), user2.email()))).toString());
        User user3 = new User("user3", "password", "user.password@gmail.com");
        Assertions.assertNotEquals((new RegisterResponse("Error: description", 500)).toString(),
                (userService.registerUser(new RegisterRequest(user3.username(), user3.password(), user3.email()))).toString());

        Assertions.assertNotEquals(new ArrayList<>(), server.db.getUsers());
        Assertions.assertNotEquals((new LogoutResponse("Error: description", 500)).toString(), (testingService.clearData()).toString());
        Assertions.assertEquals(new ArrayList<>(), server.db.getUsers());
    }

    @Test
    @DisplayName("Logout User")
    public void logoutSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = userService.registerUser(new RegisterRequest(user.username(), user.password(), user.email()));

        Assertions.assertNotEquals(new ArrayList<>(), server.db.getUsers());
        User founduser = server.db.readUser(user.username());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");

        Assertions.assertNotEquals((new LogoutResponse("Error: unauthorized", 401)).toString(), (authService.Logout(res.getAuthToken())).toString());
        Assertions.assertEquals(new ArrayList<>(), server.db.getAuths());
    }

    @Test
    @DisplayName("Logout Unauthorized User")
    public void logoutUnauthorized() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = userService.registerUser(new RegisterRequest(user.username(), user.password(), user.email()));

        Assertions.assertNotEquals(new ArrayList<>(), server.db.getUsers());
        User founduser = server.db.readUser(user.username());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");

        Assertions.assertEquals((new LogoutResponse("Error: unauthorized", 401)).toString(), (authService.Logout("fake auth")).toString());
        Assertions.assertNotEquals(new ArrayList<>(), server.db.getAuths());
    }

    @Test
    @DisplayName("Login User")
    public void loginSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = userService.registerUser(new RegisterRequest(user.username(), user.password(), user.email()));

        Assertions.assertNotEquals(new ArrayList<>(), server.db.getUsers());
        User founduser = server.db.readUser(user.username());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");

        Assertions.assertNotEquals((new LogoutResponse("Error: unauthorized", 401)).toString(), (authService.Logout(res.getAuthToken())).toString());
        Assertions.assertEquals(new ArrayList<>(), server.db.getAuths());

        Assertions.assertNotEquals((new LoginResponse("Error: unauthorized", 401)).toString(), (authService.Login(new LoginRequest(user.username(),
                user.password()))).toString());
        Assertions.assertNotEquals(new ArrayList<>(), server.db.getAuths());
    }

    @Test
    @DisplayName("Login Invalid User")
    public void loginInvalidUser() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = userService.registerUser(new RegisterRequest(user.username(), user.password(), user.email()));

        Assertions.assertNotEquals(new ArrayList<>(), server.db.getUsers());
        User founduser = server.db.readUser(user.username());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");

        Assertions.assertNotEquals((new LogoutResponse("Error: unauthorized", 401)).toString(), (authService.Logout(res.getAuthToken())).toString());
        Assertions.assertEquals(new ArrayList<>(), server.db.getAuths());

        Assertions.assertEquals((new LoginResponse("Error: unauthorized", 401)).toString(), (authService.Login(new LoginRequest(user.username(),
                "fake password"))).toString());
        Assertions.assertEquals(new ArrayList<>(), server.db.getAuths());
    }

    @Test
    @DisplayName("Create Game")
    public void createGameSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = userService.registerUser(new RegisterRequest(user.username(), user.password(), user.email()));


        Assertions.assertNotEquals((new CreateGameResponse("Error: description", 500)).toString(),
                (gameService.newGame(new CreateGameRequest("game"), res.getAuthToken())).toString());
        Assertions.assertNotEquals(new ArrayList<>(), server.db.getGames());
    }

    @Test
    @DisplayName("Create Game Unauthorized")
    public void createGameUnauthorized() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = userService.registerUser(new RegisterRequest(user.username(), user.password(), user.email()));


        Assertions.assertEquals((new CreateGameResponse("Error: unauthorized", 401)).toString(),
                (gameService.newGame(new CreateGameRequest("game"), "fake auth")).toString());
        Assertions.assertEquals(new ArrayList<>(), server.db.getGames());
    }

    @Test
    @DisplayName("List All Games")
    public void listGamesSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = userService.registerUser(new RegisterRequest(user.username(), user.password(), user.email()));

        Assertions.assertNotEquals((new CreateGameResponse("Error: description", 500)).toString(),
                (gameService.newGame(new CreateGameRequest("game"), res.getAuthToken())).toString());
        Assertions.assertNotEquals((new CreateGameResponse("Error: description", 500)).toString(),
                (gameService.newGame(new CreateGameRequest("game"), res.getAuthToken())).toString());
        Assertions.assertNotEquals((new CreateGameResponse("Error: description", 500)).toString(),
                (gameService.newGame(new CreateGameRequest("game"), res.getAuthToken())).toString());
        Assertions.assertNotEquals((new CreateGameResponse("Error: description", 500)).toString(),
                (gameService.newGame(new CreateGameRequest("game"), res.getAuthToken())).toString());
        Assertions.assertNotEquals(new ArrayList<>(), server.db.getGames());

        Assertions.assertNotEquals((new ListGamesResponse("Error: unauthorized", 401)).toString(),
                (gameService.listGames(res.getAuthToken())).toString());
    }

    @Test
    @DisplayName("List All Games Unauthorized")
    public void listGamesUnauthorized() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = userService.registerUser(new RegisterRequest(user.username(), user.password(), user.email()));

        Assertions.assertNotEquals((new CreateGameResponse("Error: description", 500)).toString(),
                (gameService.newGame(new CreateGameRequest("game"), res.getAuthToken())).toString());
        Assertions.assertNotEquals((new CreateGameResponse("Error: description", 500)).toString(),
                (gameService.newGame(new CreateGameRequest("game"), res.getAuthToken())).toString());
        Assertions.assertNotEquals((new CreateGameResponse("Error: description", 500)).toString(),
                (gameService.newGame(new CreateGameRequest("game"), res.getAuthToken())).toString());
        Assertions.assertNotEquals((new CreateGameResponse("Error: description", 500)).toString(),
                (gameService.newGame(new CreateGameRequest("game"), res.getAuthToken())).toString());
        Assertions.assertNotEquals(new ArrayList<>(), server.db.getGames());

        Assertions.assertEquals((new ListGamesResponse("Error: unauthorized", 401)).toString(),
                (gameService.listGames("fake auth")).toString());
    }

    @Test
    @DisplayName("Join Game")
    public void joinGameSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = userService.registerUser(new RegisterRequest(user.username(), user.password(), user.email()));

        CreateGameResponse gameResponse = gameService.newGame(new CreateGameRequest("game"), res.getAuthToken());
        Assertions.assertNotEquals(new ArrayList<>(), server.db.getGames());

        Assertions.assertNotEquals((new JoinGameResponse("Error: unauthorized", 401)).toString(),
                (gameService.joinGame(new JoinGameRequest(ChessGame.TeamColor.WHITE, gameResponse.getGameID()), res.getAuthToken())).toString());
    }

    @Test
    @DisplayName("Join Game Unauthorized")
    public void joinGameUnauthorized() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        RegisterResponse res = userService.registerUser(new RegisterRequest(user.username(), user.password(), user.email()));

        CreateGameResponse gameResponse = gameService.newGame(new CreateGameRequest("game"), res.getAuthToken());
        Assertions.assertNotEquals(new ArrayList<>(), server.db.getGames());

        Assertions.assertEquals((new JoinGameResponse("Error: unauthorized", 401)).toString(),
                (gameService.joinGame(new JoinGameRequest(ChessGame.TeamColor.WHITE, gameResponse.getGameID()), "fake auth")).toString());
    }
}
