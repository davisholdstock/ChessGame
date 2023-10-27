package service;

/**
 * Returns a response from POST on the /session endpoint
 */
public class LoginResponse {
    String message;
    String authToken;
    String username;
    int STATUS_CODE;

    /**
     * Creates a valid Login response
     *
     * @param username  of the user
     * @param authToken for the user
     */
    public LoginResponse(String username, String authToken) {
        this.username = username;
        this.authToken = authToken;
        this.STATUS_CODE = 200;
    }

    /**
     * Creates an error Login response
     *
     * @param message from the server
     */
    public LoginResponse(String message, int STATUS_CODE) {
        this.message = message;
        this.STATUS_CODE = STATUS_CODE;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "message='" + message + '\'' +
                ", authToken='" + authToken + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public int getSTATUS_CODE() {
        return STATUS_CODE;
    }

    public void setSTATUS_CODE(int STATUS_CODE) {
        this.STATUS_CODE = STATUS_CODE;
    }
}
