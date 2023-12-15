package ui.client;

import chess.*;
import requests.*;
import response.*;
import server.ServerFacade;
import ui.client.websocket.NotificationHandler;
import ui.client.websocket.WebSocketFacade;
import webSocketMessages.ResponseException;

import java.util.Arrays;

public class ChessClient {
    private final ServerFacade server;
    private final NotificationHandler notificationHandler;
    private WebSocketFacade ws;
    String authToken = "";
    String username = "";
    private State state = State.LOGGED_OUT;
    private String serverUrl;
    ChessGame.TeamColor teamColor = null;
    int gameID = 0;

    public ChessClient(String serverUrl, NotificationHandler notificationHandler) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.notificationHandler = notificationHandler;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> login(params);
                case "logout" -> logout();
                case "register" -> registerUser(params);
                case "create" -> createGame(params);
                case "list" -> listGames();
                case "join" -> joinGame(params);
                case "observe" -> observeGame(params);
                case "leave" -> leaveGame();
                case "resign" -> resignGame();
                case "redraw" -> redrawBoard();
                case "move" -> movePiece(params);
                case "highlight" -> highlightMoves(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception | ResponseException e) {
            return e.getMessage();
        }
    }

    public String login(String... params) throws ResponseException {
        if (params.length == 2) {
            assertSignedOut();
            var username = params[0];
            var password = params[1];
            LoginRequest user = new LoginRequest(username, password);
            LoginResponse loginResponse = server.login(user);
            if (loginResponse.getSTATUS_CODE() == 200) {
                state = State.LOGGED_IN;
                this.username = username;
                authToken = loginResponse.getAuthToken();
                return String.format("You signed in as %s.\n", username);
            }
            return "" + loginResponse.getSTATUS_CODE();
        }
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD>");
    }

    public String logout() throws ResponseException {
        assertSignedIn();
        LogoutResponse logoutResponse = server.logout(authToken);
        if (logoutResponse.getSTATUS_CODE() == 200) {
            state = State.LOGGED_OUT;
            this.username = null;
            return "Bye!\n";
        }
        return "" + logoutResponse.getSTATUS_CODE();
    }

    public String registerUser(String... params) throws ResponseException {
        if (params.length == 3) {
            assertSignedOut();
            var username = params[0];
            var password = params[1];
            var email = params[2];
            RegisterRequest user = new RegisterRequest(username, password, email);
            RegisterResponse registerResponse = server.addUser(user);
            state = State.LOGGED_IN;
            if (registerResponse.getSTATUS_CODE() == 200) {
                authToken = registerResponse.getAuthToken();
                this.username = username;
                return String.format("Welcome %s!\n", username);
            }
            return "" + registerResponse.getSTATUS_CODE();
        }
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD> <EMAIL>");
    }

    public String createGame(String... params) throws ResponseException {
        assertSignedIn();
        if (params.length == 1) {
            var gameName = params[0];
            CreateGameRequest game = new CreateGameRequest(gameName);
            CreateGameResponse createGameResponse = server.createGame(authToken, game);
            if (createGameResponse.getSTATUS_CODE() == 200) {
                int gameID = createGameResponse.getGameID();
                ChessGame game1 = new Game();
                game1.getBoard().printFancy(teamColor);
                return String.format("Game created %s!\n", gameID);
            }
        }
        throw new ResponseException(400, "Expected: <GAMENAME>");
    }

    public String listGames() throws ResponseException {
        assertSignedIn();
        ListGamesResponse listGamesResponse = server.listGames(authToken);
        if (listGamesResponse.getSTATUS_CODE() == 200) {
            return listGamesResponse.toString();
        }
        return "" + listGamesResponse.getSTATUS_CODE();
    }

    public String joinGame(String... params) throws ResponseException {
        assertSignedIn();
        if (params.length == 1) {
            int gameID = Integer.parseInt(params[0]);
            JoinGameRequest joinGameRequest = new JoinGameRequest(null, gameID);
            JoinGameResponse joinGameResponse = server.joinGame(authToken, joinGameRequest);
            if (joinGameResponse.getSTATUS_CODE() == 200) {
                // add websockets
                ws = new WebSocketFacade(serverUrl, notificationHandler);
                ws.enterGame(username, null);
                this.teamColor = null;
                this.gameID = gameID;

                ListGamesResponse listGamesResponse = server.listGames(authToken);
                var games = listGamesResponse.getGames();
                ChessGame game1 = new Game(games.get(gameID - 1).getFenNotation());
                game1.getBoard().printFancy(teamColor);
                state = State.IN_GAME;
                return String.format("Game joined %s!\n", gameID);
            }
        } else if (params.length == 2) {
            int gameID = Integer.parseInt(params[0]);
            ChessGame.TeamColor color = ChessGame.TeamColor.valueOf(params[1].toUpperCase());
            JoinGameRequest joinGameRequest = new JoinGameRequest(color, gameID);
            JoinGameResponse joinGameResponse = server.joinGame(authToken, joinGameRequest);
            if (joinGameResponse.getSTATUS_CODE() == 200) {
                // add websockets
                ws = new WebSocketFacade(serverUrl, notificationHandler);
                ws.enterGame(username, color);
                this.teamColor = color;
                this.gameID = gameID;

                ListGamesResponse listGamesResponse = server.listGames(authToken);
                var games = listGamesResponse.getGames();
                ChessGame game1 = new Game(games.get(gameID - 1).getFenNotation());
                game1.getBoard().printFancy(color);
                state = State.IN_GAME;
                return String.format("Game joined %s!\n", gameID);
            }
        }
        throw new ResponseException(400, "Expected: <ID> [WHITE|BLACK|<empty>]");
    }

    public String observeGame(String... params) throws ResponseException {
        assertSignedIn();
        if (params.length == 1) {
            int gameID = Integer.parseInt(params[0]);
            ListGamesResponse listGamesResponse = server.listGames(authToken);
            var games = listGamesResponse.getGames();
            ChessGame game1 = new Game(games.get(gameID - 1).getFenNotation());
            game1.getBoard().printFancy(teamColor);
            return "observe";
        }
        throw new ResponseException(400, "Expected: <ID>");
    }

    private String leaveGame() throws ResponseException {
        assertInGame();
        ws.leaveGame(username, this.teamColor);
        state = State.LOGGED_IN;
        this.teamColor = null;
        this.gameID = 0;
        help();
        return "You left the game!";
    }

    private String resignGame() throws ResponseException {
        assertInGame();
        ws.resignGame(username, this.teamColor);
        return "You lost!\n";
    }

    private String redrawBoard() throws ResponseException {
        assertInGame();
        ListGamesResponse listGamesResponse = server.listGames(authToken);
        var games = listGamesResponse.getGames();
        ChessGame game1 = new Game(games.get(this.gameID - 1).getFenNotation());
        game1.getBoard().printFancy(this.teamColor);
        return "Updated Board";
    }

    private String movePiece(String... params) throws ResponseException {
        try {
            if (params.length == 2) {
                assertInGame();
                String initialPosition = params[0];
                String finalPosition = params[1];
                ListGamesResponse listGamesResponse = server.listGames(authToken);
                var games = listGamesResponse.getGames();
                ChessGame game1 = new Game(games.get(this.gameID - 1).getFenNotation());
                game1.makeMove(new Move(new Position(initialPosition), new Position(finalPosition), null));
                return "Moved " + initialPosition + " to " + finalPosition;
            } else {
                throw new RuntimeException();
            }
        } catch (RuntimeException | InvalidMoveException ex) {
            throw new ResponseException(400, "Expected: <INITIAL POSITION> <FINAL POSITION>");
        }
    }

    private String highlightMoves(String... params) throws ResponseException {
        return null;
    }

    public State getState() {
        return state;
    }

    public String help() {
        if (state == State.LOGGED_OUT) {
            return """
                        register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                        login <USERNAME> <PASSWORD>
                        quit - playing chess
                        help - with possible commands
                    """;
        } else if (state == State.IN_GAME) {
            return """
                        redraw - redraws the chess board
                        move <INITIAL POSITION> <FINAL POSITION> - moves a piece based on chess board position
                        highlight <POSITION> - highlights the legal moves for a piece based on chess board position
                        resign - resigns from the game
                        leave - leaves the game
                        help - with possible commands
                    """;
        }
        return """
                    create <NAME> - a game
                    list - games
                    join <ID> [WHITE|BLACK|<empty>] - a game
                    observe <ID> - a game
                    logout - when you are done
                    quit - playing chess
                    help - with possible commands
                """;
    }

    private void assertSignedIn() throws ResponseException {
        if (state != State.LOGGED_IN) {
            throw new ResponseException(400, "You must sign in\n");
        }
    }

    private void assertSignedOut() throws ResponseException {
        if (state != State.LOGGED_OUT) {
            throw new ResponseException(400, "You must sign out\n");
        }
    }

    private void assertInGame() throws ResponseException {
        if (state != State.IN_GAME) {
            throw new ResponseException(400, "You must join a game\n");
        }
    }
}
