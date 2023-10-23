package service;

import main.Game;

import java.util.ArrayList;

/**
 * Returns a response from GET on the /game endpoint
 *
 * @param message  from the server
 * @param gameList of saved Games
 */
public record ListGamesResponse(String message, ArrayList<Game> gameList) {
}
