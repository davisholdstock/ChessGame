package dataAccess;

import model.Authtoken;
import model.Game;
import model.User;

import java.util.ArrayList;

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

    void removeUser(User user);

    /**
     * Saves the Game
     *
     * @param game to be saved
     * @return the game that was saved
     */
    Game writeGame(Game game) throws DataAccessException;

    /**
     * Find a single game out of the saved games
     *
     * @param gameID of the game you are trying to find
     * @return the game that was being searched for
     */
    Game readGame(int gameID) throws DataAccessException;

    /**
     * Retrieves all the saved games
     *
     * @return all the saved games
     */
    ArrayList<Game> readAllGame();

    /**
     * Updates the string name of a game
     *
     * @param gameID  of the game you want to change the name of
     * @param newName of the desired game
     * @return the game being searched, showing the updated name
     */
    Game updateGame(int gameID, String newName) throws DataAccessException;

    /**
     * Removes a single game from where it is saved
     *
     * @param gameID of the desired game to be destroyed
     */
    void removeGame(int gameID);

    Authtoken writeAuth(Authtoken authtoken) throws DataAccessException;

    Authtoken readAuth(Authtoken authtoken) throws DataAccessException;

    void removeAuth(Authtoken authtoken);
}
