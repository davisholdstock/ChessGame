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

public class dataAccessTests {
    private MemoryDatabase db = new MemoryDatabase();

    @BeforeEach
    public void setup() {
        db.clear();
    }

    @Test
    @DisplayName("Add and Get User")
    public void getAddUser() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        db.writeUser(user);

        User founduser = db.readUser(user.username());

        Assertions.assertNotNull(db);
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");
    }

    @Test
    @DisplayName("Add and Get Game")
    public void getAddGame() throws DataAccessException {
        ChessGame chessGame = new main.Game();
        Game game = new model.Game("game1", chessGame, "whiteUser", "blackUser", 123);
        db.writeGame(game);

        Game foundGame = db.readGame(game.gameID());

        Assertions.assertNotNull(db);
        Assertions.assertEquals(game.toString(), foundGame.toString(),
                "Wrong Game Found");
    }

    @Test
    @DisplayName("Add and Get Auth")
    public void getAddAuth() throws DataAccessException {
        AuthToken authtoken = new AuthToken("yes", "user");
        db.writeAuth(authtoken);

        AuthToken foundAuth = db.readAuth(authtoken);

        Assertions.assertNotNull(db);
        Assertions.assertEquals(authtoken, foundAuth,
                "Wrong Auth Found");
    }
}
