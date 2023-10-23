package service;

import chess.ChessGame;

/**
 * Requests to join a Game from the server
 *
 * @param playerColor color of the user trying to join a Game
 * @param gameID      of the Game trying to be joined
 */
public record JoinGameRequest(ChessGame.TeamColor playerColor, int gameID) {
}
