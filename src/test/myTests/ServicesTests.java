package myTests;

import dataAccess.DataAccessException;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.server;
import service.*;

import java.util.HashMap;

public class ServicesTests {
    //private MemoryDatabase db = new MemoryDatabase();
    private AuthService authService = new AuthService();
    private TestingService testingService = new TestingService();
    private UserService userService = new UserService();

    @BeforeEach
    public void setup() throws DataAccessException {
        server.db.clear();
    }

    @Test
    @DisplayName("Clear Data")
    public void clearDataSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        User user1 = new User("user1", "password", "user.password@gmail.com");
        User user2 = new User("user2", "password", "user.password@gmail.com");
        User user3 = new User("user3", "password", "user.password@gmail.com");
        server.db.writeUser(user);
        server.db.writeUser(user1);
        server.db.writeUser(user2);
        server.db.writeUser(user3);

        Assertions.assertNotEquals(new HashMap<>(), server.db.getUsers());
        Assertions.assertNotEquals((new LogoutResponse("Error: description")).toString(), (testingService.clearData()).toString());
        Assertions.assertEquals(new HashMap<>(), server.db.getUsers());
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

        Assertions.assertEquals((new LoginResponse("user", "12")).toString(),
                (authService.TestLogin(new LoginRequest("user", "password", "user.password@gmail.com"), "12")).toString());
    }

    @Test
    @DisplayName("Logout User")
    public void logoutSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        server.db.writeUser(user);

        Assertions.assertNotEquals(new HashMap<>(), server.db.getUsers());
        User founduser = server.db.readUser(user.username());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");

        Assertions.assertEquals((new LoginResponse("user", "12")).toString(),
                (authService.TestLogin(new LoginRequest("user", "password", "user.password@gmail.com"), "12")).toString());

        Assertions.assertNotEquals((new LogoutResponse("Error: unauthorized")).toString(), (authService.Logout("user")).toString());
        Assertions.assertEquals(new HashMap<>(), server.db.getUsers());
    }

    @Test
    @DisplayName("Register User")
    public void registerUserSuccess() throws DataAccessException {
        Assertions.assertNotEquals((new RegisterResponse("Error: Unable to Register user")).toString(),
                (userService.registerUser(new RegisterRequest("user", "password", "user.password@gmail.com"))).toString());
        User user = new User("user", "password", "user.password@gmail.com");
        //server.db.writeUser(user);

        Assertions.assertNotEquals(new HashMap<>(), server.db.getUsers());
        User founduser = server.db.readUser(user.username());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");

    }
}
