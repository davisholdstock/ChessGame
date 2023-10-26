package service;

/**
 * Returns a response to DELETE at the /db endpoint
 */
public class ClearResponse {
    String message;

    /**
     * Creates a valid clear response
     */
    public ClearResponse() {
    }

    /**
     * Creates an error clear response
     *
     * @param message from the server
     */
    public ClearResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
