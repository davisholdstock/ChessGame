package service;

/**
 * Returns a response from DELETE at the /session endpoint
 */
public class LogoutResponse {
    String message;

    /**
     * Creates a valid Logout response
     */
    public LogoutResponse() {
    }

    /**
     * Creates an error Logout response
     *
     * @param message from the server
     */
    public LogoutResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
