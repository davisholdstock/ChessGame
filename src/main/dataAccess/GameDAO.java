package dataAccess;

import model.Game;

import java.util.ArrayList;

public interface GameDAO {
    /**
     * Clears all the data in the database
     *
     * @throws DataAccessException for database or sql query violations
     */
    void clear() throws DataAccessException;

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
}
