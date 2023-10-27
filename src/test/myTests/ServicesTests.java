package myTests;

import dataAccess.DataAccessException;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.server;
import service.AuthService;
import service.LoginRequest;
import service.LoginResponse;

import java.util.HashMap;

public class ServicesTests {
    //private MemoryDatabase db = new MemoryDatabase();
    private AuthService authService = new AuthService();

    @BeforeEach
    public void setup() throws DataAccessException {
        server.db.clear();
    }

    @Test
    @DisplayName("Login User")
    public void loginSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        server.db.writeUser(user);

        Assertions.assertNotEquals(new HashMap<>(), server.db.getUsers());
        User founduser = server.db.readUser(user.username());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");

        Assertions.assertEquals((new LoginResponse("user", "12")).toString(), (authService.TestLogin(new LoginRequest("user", "password", "user.password@gmail.com"), "12")).toString());
    }

//    @Test
//    @DisplayName("Add duplicate")
//    public void addUserFail() throws DataAccessException {
//        User user = new User("user", "password", "user.password@gmail.com");
//        db.writeUser(user);
//
//        Assertions.assertThrows(DataAccessException.class, () -> db.writeUser(user));
//    }
//
//    @Test
//    @DisplayName("Add and Get Game")
//    public void getAddGameSuccess() throws DataAccessException {
//        ChessGame chessGame = new main.Game();
//        Game game = new model.Game("game1", chessGame, "whiteUser", "blackUser", 123);
//        db.writeGame(game);
//
//        Game foundGame = db.readGame(game.gameID());
//
//        Assertions.assertNotEquals(new HashMap<>(), db.getGames());
//        Assertions.assertEquals(game.toString(), foundGame.toString(),
//                "Wrong Game Found");
//    }
//
//    @Test
//    @DisplayName("List All Games")
//    public void listAllGames() throws DataAccessException {
//        ChessGame chessGame = new main.Game();
//        Game game = new model.Game("game0", chessGame, "whiteUser", "blackUser", 123);
//        Game game1 = new model.Game("game1", chessGame, "whiteUser", "blackUser", 1234);
//        Game game2 = new model.Game("game2", chessGame, "whiteUser", "blackUser", 12345);
//        Game game3 = new model.Game("game3", chessGame, "whiteUser", "blackUser", 123456);
//        db.writeGame(game);
//        db.writeGame(game1);
//        db.writeGame(game2);
//        db.writeGame(game3);
//
//        ArrayList<Game> foundGames = db.readAllGame();
//
//        Assertions.assertNotEquals(new HashMap<>(), db.getGames());
//        Assertions.assertEquals(4, foundGames.size(),
//                "Wrong Games Found");
//    }
//
//    @Test
//    @DisplayName("Add and Get Auth")
//    public void getAddAuthSuccess() throws DataAccessException {
//        AuthToken authtoken = new AuthToken("yes", "user");
//        db.writeAuth(authtoken);
//
//        AuthToken foundAuth = db.readAuth(authtoken);
//
//        Assertions.assertNotEquals(new HashMap<>(), db.getAuths());
//        Assertions.assertEquals(authtoken, foundAuth,
//                "Wrong Auth Found");
//    }
}
