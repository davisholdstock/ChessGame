package model;

/**
 * Creates a User with a username, a password, and an email
 *
 * @param username for the User
 * @param password for the User
 * @param email    for the User
 */
public record User(String username, String password, String email) {
    @Override
    public String toString() {
        return username;
    }
}
