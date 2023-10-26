package server;

import handler.AuthHandler;
import handler.GameHandler;
import handler.TestingHandler;
import handler.UserHandler;
import spark.Spark;

/**
 * Creates the server
 */
public class server {
    public static void main(String[] args) {
        new server().run();
    }

    /**
     * Runs the server
     */
    void run() {
        Spark.externalStaticFileLocation("web");
        Spark.port(8080);

        Spark.get("/hello", (req, res) -> "Hello!"); /* this is for testing */

        Spark.delete("/db", (req, res) -> (new TestingHandler()).handle(req, res));

        Spark.post("/user", (req, res) -> (new UserHandler()).handle(req, res));

        Spark.post("/session", (req, res) -> (new AuthHandler()).Login(req, res));

        Spark.delete("/session", (req, res) -> (new AuthHandler()).Logout(req, res));

        Spark.get("/game", (req, res) -> (new GameHandler()).listGames(req, res));

        Spark.post("/game", (req, res) -> (new GameHandler()).newGame(req, res));

        Spark.put("/game", (req, res) -> (new GameHandler()).joinGame(req, res));
    }
}
