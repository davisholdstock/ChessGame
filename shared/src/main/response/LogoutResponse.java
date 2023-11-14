package response;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogoutResponse that = (LogoutResponse) o;
        return STATUS_CODE == that.STATUS_CODE && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, STATUS_CODE);
    }
}
