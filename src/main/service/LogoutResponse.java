package service;

/**
 * Returns a response from DELETE at the /session endpoint
 */
public class LogoutResponse {
    String message;
    int STATUS_CODE;

    /**
     * Creates a valid Logout response
     */
    public LogoutResponse() {
        this.STATUS_CODE = 200;
    }

    /**
     * Creates an error Logout response
     *
     * @param message from the server
     */
    public LogoutResponse(String message, int STATUS_CODE) {
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
        return "LogoutResponse{" +
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
