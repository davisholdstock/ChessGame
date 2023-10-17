package service;

import model.Authtoken;

/**
 * Defines what services can be preformed on a session object
 */
public class AuthService {
    public AuthService() {
    }

    /**
     * Logs in a user
     *
     * @param username of the User trying to log in
     * @param password of the User trying to log in
     * @return an AuthToken if the User successfully logs in
     */
    public Authtoken Login(String username, String password) {
        return null;
    }

    /**
     * Logs a User out of the system
     *
     * @param username of the User to be logged out
     */
    public void Logout(String username) {
    }
}
