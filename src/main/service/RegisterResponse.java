package service;

/**
 * Returns a response to POST at the /user endpoint
 */
public class RegisterResponse {
    String message;
    String username;
    String authToken;

    /**
     * Creates a valid register response
     *
     * @param username of the user
     */
    public RegisterResponse(String username, String authToken) {
        this.username = username;
        this.authToken = authToken;
    }

    /**
     * Creates an error register response
     *
     * @param message from the server
     */
    public RegisterResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
