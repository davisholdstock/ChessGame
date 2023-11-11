package myTests.DBtests;

import dataAccess.DataAccessException;
import model.AuthToken;
import model.Game;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.server;
import service.AuthService;
import service.GameService;
import service.TestingService;
import service.UserService;

import java.util.ArrayList;
import java.util.Collections;

public class MethodTests {
    //    public static DataAccess db = new SQLDatabase();
    private AuthService authService = new AuthService();
    private TestingService testingService = new TestingService();
    private UserService userService = new UserService();
    private GameService gameService = new GameService();

    @BeforeEach
    public void setup() throws DataAccessException {
        server.db.clear();
    }

    @Test
    @DisplayName("Write User")
    public void writeUserSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        server.db.writeUser(user);

        User founduser = server.db.readUser(user.username());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");
    }

    @Test
    @DisplayName("Write Duplicate User")
    public void writeUserFail() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        server.db.writeUser(user);
        Assertions.assertThrows(DataAccessException.class, () -> server.db.writeUser(user));

        User founduser = server.db.readUser(user.username());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");
    }

    @Test
    @DisplayName("Read User")
    public void readUserSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        server.db.writeUser(user);

        User founduser = server.db.readUser(user.username());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Found the wrond user");
    }

    @Test
    @DisplayName("Read User Does not exist")
    public void readUserFail() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        User user1 = new User("user1", "password1", "user1.password1@gmail.com");
        server.db.writeUser(user);

        User founduser = server.db.readUser(user1.username());
        Assertions.assertEquals(null, founduser,
                "User Found");
    }

    @Test
    @DisplayName("Remove User")
    public void removeUserSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        server.db.writeUser(user);
        server.db.removeUser(user);

        User founduser = server.db.readUser(user.username());
        Assertions.assertEquals(null, founduser,
                "Remove user failed");
    }

    @Test
    @DisplayName("Remove Wrong User")
    public void removeUserFail() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        User user1 = new User("user1", "password1", "user1.password1@gmail.com");
        server.db.writeUser(user);
        server.db.removeUser(user1);

        User founduser = server.db.readUser(user.username());
        Assertions.assertNotEquals(null, founduser,
                "Remove user successful");
    }

    @Test
    @DisplayName("Write Auth")
    public void writeAuthSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        AuthToken auth = new AuthToken("Authorized", user.username());
        server.db.writeAuth(auth);

        AuthToken foundAuth = server.db.readAuth("Authorized");
        Assertions.assertEquals(auth.toString(), foundAuth.toString(),
                "Wrong Auth Found");
    }

    @Test
    @DisplayName("Write Auth Fail")
    public void writeAuthFail() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        AuthToken auth = new AuthToken(null, user.username());
        Assertions.assertThrows(DataAccessException.class, () -> server.db.writeAuth(auth));
    }

    @Test
    @DisplayName("Read Auth")
    public void readAuthSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        AuthToken auth = new AuthToken("Authorized", user.username());
        server.db.writeAuth(auth);

        AuthToken foundAuth = server.db.readAuth("Authorized");
        Assertions.assertEquals(auth.toString(), foundAuth.toString(),
                "Found the wrong user");
    }

    @Test
    @DisplayName("Read Auth Does not exist")
    public void readAuthFail() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        AuthToken auth = new AuthToken("Authorized", user.username());
        server.db.writeAuth(auth);

        Assertions.assertThrows(DataAccessException.class, () -> server.db.readAuth("Fake Auth"));
    }

    @Test
    @DisplayName("Remove Auth")
    public void removeAuthSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        AuthToken auth = new AuthToken("Authorized", user.username());
        server.db.writeAuth(auth);
        server.db.removeAuth(auth);

        Assertions.assertThrows(DataAccessException.class, () -> server.db.readAuth("Authorized"));
    }

    @Test
    @DisplayName("Remove Wrong Auth")
    public void removeAuthFail() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        AuthToken auth = new AuthToken("Authorized", user.username());
        AuthToken auth1 = new AuthToken("Fake Auth", "Fake User");
        server.db.writeAuth(auth);
        server.db.removeAuth(auth1);

        AuthToken foundAuth = server.db.readAuth("Authorized");
        Assertions.assertEquals(auth.toString(), foundAuth.toString(),
                "Remove Auth Succeeded");
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("Write Game")
    public void writeGameSuccess() throws DataAccessException {
        Game game = server.db.writeGame("game");

        Game foundGame = server.db.readGame(game.gameID());
        Assertions.assertEquals(game.toString(), foundGame.toString(),
                "Wrong Game Found");
    }

    @Test
    @DisplayName("Write Game Fail")
    public void writeGameFail() throws DataAccessException {
        Assertions.assertThrows(DataAccessException.class, () -> server.db.writeGame(null));
    }

    @Test
    @DisplayName("Read Game")
    public void readGameSuccess() throws DataAccessException {
        Game game = server.db.writeGame("game");

        Game foundGame = server.db.readGame(game.gameID());
        Assertions.assertEquals(game.toString(), foundGame.toString(),
                "Wrong Game Found");
    }

    @Test
    @DisplayName("Read Game Does not exist")
    public void readGameFail() throws DataAccessException {
        Game game = server.db.writeGame("game");

        Assertions.assertThrows(DataAccessException.class, () -> server.db.readGame(0));
    }

    @Test
    @DisplayName("Read All Game")
    public void readAllGameSuccess() throws DataAccessException {
        Game game = server.db.writeGame("game");
        Game game1 = server.db.writeGame("game1");
        Game game2 = server.db.writeGame("game2");
        ArrayList<String> games = new ArrayList<>();
        games.add(game.toString());
        games.add(game1.toString());
        games.add(game2.toString());
        Collections.sort(games);

        ArrayList<Game> foundGames = server.db.readAllGame();
        ArrayList<String> foundGamesStrings = new ArrayList<>();
        for (var g : foundGames) {
            foundGamesStrings.add(g.toString());
        }
        Collections.sort(foundGamesStrings);
        Assertions.assertTrue(games.equals(foundGamesStrings),
                "Wrong Games Found");
    }

    @Test
    @DisplayName("Read All Games Wrong")
    public void readAllGameFail() throws DataAccessException {
        Game game = server.db.writeGame("game");
        Game game1 = server.db.writeGame("game1");
        Game game2 = server.db.writeGame("game2");
        ArrayList<String> games = new ArrayList<>();
        games.add(game.toString());
        games.add(game1.toString());
        games.add(game2.toString());
        games.add((new Game("test", new main.Game(), "This should", "fail", 32)).toString());
        Collections.sort(games);

        ArrayList<Game> foundGames = server.db.readAllGame();
        ArrayList<String> foundGamesStrings = new ArrayList<>();
        for (var g : foundGames) {
            foundGamesStrings.add(g.toString());
        }
        Collections.sort(foundGamesStrings);
        Assertions.assertFalse(games.equals(foundGamesStrings),
                "Right Games Found");
    }

    @Test
    @DisplayName("Remove Auth")
    public void removeGameSuccess() throws DataAccessException {
        Game game = server.db.writeGame("game");
        server.db.removeGame(game.gameID());

        Assertions.assertThrows(DataAccessException.class, () -> server.db.readGame(game.gameID()));
    }

    @Test
    @DisplayName("Remove Wrong Game")
    public void removeGameFail() throws DataAccessException {
        Game game = server.db.writeGame("game");
        server.db.removeGame(0);

        Game foundGame = server.db.readGame(game.gameID());
        Assertions.assertEquals(game.toString(), foundGame.toString(),
                "Game Not Removed");
    }

    @Test
    @DisplayName("Clear DB")
    public void clearSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        User user1 = new User("user1", "password1", "user.password@gmail.com");
        User user2 = new User("user2", "password2", "user.password@gmail.com");
        User user3 = new User("user3", "password3", "user.password@gmail.com");
        server.db.writeUser(user);
        server.db.writeUser(user1);
        server.db.writeUser(user2);
        server.db.writeUser(user3);
        AuthToken auth = new AuthToken("Authorized", user.username());
        server.db.writeAuth(auth);

        server.db.clear();

        User founduser = server.db.readUser(user.username());
        Assertions.assertEquals(null, founduser,
                "Remove user failed");
        Assertions.assertThrows(DataAccessException.class, () -> server.db.readAuth("Authorized"));
    }

    @Test
    @DisplayName("Update Game")
    public void updateGameSuccess() throws DataAccessException {
        Game game = server.db.writeGame("game");
        Game game1 = new Game("updatedGame", new main.Game("rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1"), "wUser", null, 1);
        Game updatedGame = server.db.updateGame(game.gameID(), game1);
        Game foundGame = server.db.readGame(game.gameID());

        Assertions.assertEquals(updatedGame.game().fenNotation(), foundGame.game().fenNotation(),
                "Wrong Game Found");
    }

    @Test
    @DisplayName("Update Game Fail")
    public void updateGameFail() throws DataAccessException {
        Game game = server.db.writeGame("game");
        Assertions.assertThrows(DataAccessException.class, () -> server.db.updateGame(0, new Game("updated", new main.Game(), "white", null, 1)));
    }
}
