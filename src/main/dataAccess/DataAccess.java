package dataAccess;

import model.User;

/**
 * Represents all operations that may be performed on a database
 */
public interface DataAccess {
    /**
     * Clears all the data in the database
     *
     * @throws DataAccessException for database or sql query violations
     */
    void clear() throws DataAccessException;

    /**
     * Store a new user
     *
     * @param user (with both a username and an ID)
     * @return the new user
     * @throws DataAccessException for database (ex duplicate user creation) or sql query violations
     */
    User writeUser(User user) throws DataAccessException;

    /**
     * Read a previously stored user
     *
     * @param user (with both a username and an ID)
     * @return return the stored data for the user
     * @throws DataAccessException for database (ex no user) or sql query violations
     */
    User readUser(User user) throws DataAccessException;
}
