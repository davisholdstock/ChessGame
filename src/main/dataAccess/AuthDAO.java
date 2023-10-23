package dataAccess;

import model.Authtoken;

public interface AuthDAO {
    /**
     * Clears all the data in the database
     *
     * @throws DataAccessException for database or sql query violations
     */
    void clear() throws DataAccessException;

    /**
     * Saves the Auth token
     *
     * @param authtoken
     * @return
     * @throws DataAccessException
     */
    Authtoken writeAuth(Authtoken authtoken) throws DataAccessException;

    /**
     * Find an Auth token out of the saved tokens
     *
     * @param authtoken
     * @return
     * @throws DataAccessException
     */
    Authtoken readAuth(Authtoken authtoken) throws DataAccessException;

    /**
     * Remove an Auth token
     *
     * @param authtoken
     */
    void removeAuth(Authtoken authtoken);
}
