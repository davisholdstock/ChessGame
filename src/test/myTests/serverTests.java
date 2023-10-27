package myTests;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.MemoryDatabase;
import model.Game;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;
import passoffTests.obfuscatedTestClasses.TestServerFacade;

public class serverTests {
    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_UNAUTHORIZED = 401;
    private static final int HTTP_FORBIDDEN = 403;
    private static TestServerFacade serverFacade;
    public DataAccess db;

    @BeforeAll
    public static void init() {
        serverFacade = new TestServerFacade("localhost", TestFactory.getServerPort());
    }

    @Test
    @DisplayName("Clear DB")
    public void successClearDB() throws DataAccessException {
        db = new MemoryDatabase();
        db.writeUser(new User("Davis", "password", "thisismyemail@gmail.com"));
        db.writeGame(new Game("game", new main.Game(), "white", "black", 1));
        //serverFacade.clear();
        //Assertions.assertNotNull(loginResult.authToken, "Response did not return authentication String");
    }
}
