package myTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestModels;


import java.util.Locale;

public class serverTests {
    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_UNAUTHORIZED = 401;
    private static final int HTTP_FORBIDDEN = 403;
    private static TestServerFacade serverFacade;

    @Test
    @Order(1)
    @DisplayName("Normal User Login")
    public void successLogin() {
        TestModels.TestLoginRequest loginRequest = new TestModels.TestLoginRequest();

        TestModels.TestLoginRegisterResult loginResult = serverFacade.login(loginRequest);

        Assertions.assertEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was not 200 OK");
        Assertions.assertTrue(loginResult.success, "Response returned not successful");
        Assertions.assertFalse(
                loginResult.message != null && loginResult.message.toLowerCase(Locale.ROOT).contains("error"),
                "Response returned error message");
        Assertions.assertNotNull(loginResult.authToken, "Response did not return authentication String");
    }
}
