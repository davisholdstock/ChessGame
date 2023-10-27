package service;

/**
 * Returns a response from PUT on the /game endpoint
 */
public class JoinGameResponse {
    String message;

    /**
     * Creates a valid join game response
     */
    public JoinGameResponse() {
    }

    /**
     * Creates an error join game response
     *
     * @param message from the server
     */
    public JoinGameResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "JoinGameResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
