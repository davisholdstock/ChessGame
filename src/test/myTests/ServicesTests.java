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
import service.LogoutResponse;

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

    @Test
    @DisplayName("Logout User")
    public void logoutSuccess() throws DataAccessException {
        User user = new User("user", "password", "user.password@gmail.com");
        server.db.writeUser(user);

        Assertions.assertNotEquals(new HashMap<>(), server.db.getUsers());
        User founduser = server.db.readUser(user.username());
        Assertions.assertEquals(user.toString(), founduser.toString(),
                "Wrong User Found");

        Assertions.assertEquals((new LoginResponse("user", "12")).toString(), (authService.TestLogin(new LoginRequest("user", "password", "user.password@gmail.com"), "12")).toString());

        Assertions.assertNotEquals((new LogoutResponse("Error: unauthorized")).toString(), (authService.Logout("user")).toString());
        Assertions.assertEquals(new HashMap<>(), server.db.getUsers());
    }
}
