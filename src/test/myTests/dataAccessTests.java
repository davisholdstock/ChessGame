package myTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.MemoryDatabase;
import model.AuthToken;
import model.Game;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class dataAccessTests {
    private MemoryDatabase db = new MemoryDatabase();

    @BeforeEach
    public void setup() {
        db.clear();
    }

    @Test
    @DisplayName("Add and Get User")
    public void getAddUserSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        db.writeUser(user);

        User founduser = db.readUser(user.username());

        Assertions.assertNotEquals(new HashMap<>(), db.getUsers());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");
    }

    @Test
    @DisplayName("Add duplicate")
    public void addUserFail() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        db.writeUser(user);

        Assertions.assertThrows(DataAccessException.class, () -> db.writeUser(user));
    }

    @Test
    @DisplayName("Add and Get Game")
    public void getAddGameSuccess() throws DataAccessException {
        ChessGame chessGame = new main.Game();
        Game game = new model.Game("game1", chessGame, "whiteUser", "blackUser", 123);
        db.writeGame(game);

        Game foundGame = db.readGame(game.gameID());

        Assertions.assertNotEquals(new HashMap<>(), db.getGames());
        Assertions.assertEquals(game.toString(), foundGame.toString(),
                "Wrong Game Found");
    }

    @Test
    @DisplayName("List All Games")
    public void listAllGames() throws DataAccessException {
        ChessGame chessGame = new main.Game();
        Game game = new model.Game("game0", chessGame, "whiteUser", "blackUser", 123);
        Game game1 = new model.Game("game1", chessGame, "whiteUser", "blackUser", 1234);
        Game game2 = new model.Game("game2", chessGame, "whiteUser", "blackUser", 12345);
        Game game3 = new model.Game("game3", chessGame, "whiteUser", "blackUser", 123456);
        db.writeGame(game);
        db.writeGame(game1);
        db.writeGame(game2);
        db.writeGame(game3);

        ArrayList<Game> foundGames = db.readAllGame();

        Assertions.assertNotEquals(new HashMap<>(), db.getGames());
        Assertions.assertEquals(4, foundGames.size(),
                "Wrong Games Found");
    }

    @Test
    @DisplayName("Add and Get Auth")
    public void getAddAuthSuccess() throws DataAccessException {
        AuthToken authtoken = new AuthToken("yes", "user");
        db.writeAuth(authtoken);

        AuthToken foundAuth = db.readAuth(authtoken.username());

        Assertions.assertNotEquals(new HashMap<>(), db.getAuths());
        Assertions.assertEquals(authtoken, foundAuth,
                "Wrong Auth Found");
    }
}
