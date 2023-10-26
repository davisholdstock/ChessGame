package dataAccess;

import model.AuthToken;

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
    AuthToken writeAuth(AuthToken authtoken) throws DataAccessException;

    /**
     * Find an Auth token out of the saved tokens
     *
     * @param authtoken
     * @return
     * @throws DataAccessException
     */
    AuthToken readAuth(AuthToken authtoken) throws DataAccessException;

    /**
     * Remove an Auth token
     *
     * @param authtoken
     */
    void removeAuth(AuthToken authtoken);
}
