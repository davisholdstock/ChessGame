package service;

/**
 * Returns a response from PUT on the /game endpoint
 */
public class JoinGameResponse {
    String message;
    int STATUS_CODE;

    /**
     * Creates a valid join game response
     */
    public JoinGameResponse() {
        this.STATUS_CODE = 200;
    }

    /**
     * Creates an error join game response
     *
     * @param message from the server
     */
    public JoinGameResponse(String message, int STATUS_CODE) {
        this.message = message;
        this.STATUS_CODE = STATUS_CODE;
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

    public int getSTATUS_CODE() {
        return STATUS_CODE;
    }

    public void setSTATUS_CODE(int STATUS_CODE) {
        this.STATUS_CODE = STATUS_CODE;
    }
}
