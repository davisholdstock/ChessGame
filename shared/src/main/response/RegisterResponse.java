package response;

import java.util.Objects;

/**
 * Returns a response to POST at the /user endpoint
 */
public class RegisterResponse {
    String message;
    String username;
    String authToken;
    int STATUS_CODE;

    /**
     * Creates a valid register response
     *
     * @param username of the user
     */
    public RegisterResponse(String username, String authToken) {
        this.username = username;
        this.authToken = authToken;
        this.STATUS_CODE = 200;
    }

    /**
     * Creates an error register response
     *
     * @param message from the server
     */
    public RegisterResponse(String message, int STATUS_CODE) {
        this.message = message;
        this.STATUS_CODE = STATUS_CODE;
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

    public int getSTATUS_CODE() {
        return STATUS_CODE;
    }

    public void setSTATUS_CODE(int STATUS_CODE) {
        this.STATUS_CODE = STATUS_CODE;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "message='" + message + '\'' +
                ", username='" + username + '\'' +
                ", authToken='" + authToken + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterResponse that = (RegisterResponse) o;
        return STATUS_CODE == that.STATUS_CODE && Objects.equals(message, that.message) && Objects.equals(username, that.username) && Objects.equals(authToken, that.authToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, username, authToken, STATUS_CODE);
    }
}
