package ui.client;

import model.User;
import response.RegisterResponse;
import server.ServerFacade;
import ui.client.websocket.WebSocketFacade;

import java.util.Arrays;

public class ChessClient {
    private String visitorName = null;
    private final ServerFacade server;
    private final String serverUrl;
    private WebSocketFacade ws;
    private State state = State.SIGNED_OUT;

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
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
                case "create" -> createGame();
                case "list" -> listGames();
                case "join" -> joinGame();
                case "observe" -> observeGame();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String login(String... params) {
//        if (params.length >= 1) {
//            state = State.SIGNED_IN;
//            visitorName = String.join("-", params);
//            ws = new WebSocketFacade(serverUrl);
//            ws.enterPetShop(visitorName);
//            return String.format("You signed in as %s.", visitorName);
//        }
//        return null;
        return "login";
    }

    public String logout() {
//        assertSignedIn();
//        ws.leavePetShop(visitorName);
//        ws = null;
//        state = State.SIGNEDOUT;
//        return String.format("%s left the shop", visitorName);
        return "logout";
    }

    public String registerUser(String... params) {
        if (params.length == 3) {
            var username = params[0];
            var password = params[1];
            var email = params[2];
            User user = new User(username, password, email);
            RegisterResponse registerResponse = server.addUser(user);
            return "registration worked";
        }
//        throw new ResponseException(400, "Expected: <name> <CAT|DOG|FROG> [<friend name>]*");
        return "register";
    }

    public String createGame(String... params) {
//        assertSignedIn();
//        if (params.length == 1) {
//            var id = Integer.parseInt(params[0]);
//            var pet = getPet(id);
//            if (pet != null) {
//                server.deletePet(id);
//                return String.format("%s says %s", pet.name(), pet.sound());
//            }
//        }
//        throw new ResponseException(400, "Expected: <pet id>");
        return "create";
    }

    public String listGames() {
//        assertSignedIn();
//        var pets = server.listPets();
//        var result = new StringBuilder();
//        var gson = new Gson();
//        for (var pet : pets) {
//            result.append(gson.toJson(pet)).append('\n');
//        }
//        return result.toString();
        return "list";
    }

    public String joinGame() {
//        assertSignedIn();
//        var buffer = new StringBuilder();
//        for (var pet : server.listPets()) {
//            buffer.append(String.format("%s says %s%n", pet.name(), pet.sound()));
//        }
//
//        server.deleteAllPets();
//        return buffer.toString();
        return "join";
    }

    public String observeGame() {
//        for (var pet : server.listPets()) {
//            if (pet.id() == id) {
//                return pet;
//            }
//        }
//        return null;
        return "observe";
    }

    public State getState() {
        return state;
    }

    public String help() {
        if (state == State.SIGNED_OUT) {
            return """
                    - register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                    - login <USERNAME> <PASSWORD>
                    - quit - playing chess
                    - help - with possible commands
                    """;
        }
        return """
                - create <NAME> - a game
                - list - games
                - join <ID> [WHITE|BLACK|<empty>] - a game
                - observe <ID> - a game
                - logout - when you are done
                - quit - playing chess
                - help - with possible commands
                """;
    }

//    private void assertSignedIn() throws ResponseException {
//        if (state == State.SIGNEDOUT) {
//            throw new ResponseException(400, "You must sign in");
//        }
//    }
}
