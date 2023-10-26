package service;

/**
 * Defines what services can be preformed on a session object
 */
public class AuthService {
    public AuthService() {
    }

    /**
     * Logs a user in
     *
     * @param request for the login
     * @return the attempted login response
     */
    public LoginResponse Login(LoginRequest request) {
        return null;
    }

    /**
     * Logs a User out of the system
     *
     * @param username of the User to be logged out
     * @return the attempted Logout response
     */
    public LogoutResponse Logout(String username) {
        return null;
    }
}
