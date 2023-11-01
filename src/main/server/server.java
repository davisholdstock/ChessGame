package server;

import dataAccess.DataAccess;
import dataAccess.SQLDatabase;
import handler.*;
import spark.Spark;

/**
 * Creates the server
 */
public class server {
    public static DataAccess db = new SQLDatabase();

    public static void main(String[] args) {
        new server().run();
    }

    /**
     * Runs the server
     */
    void run() {
        Spark.externalStaticFileLocation("web");
        Spark.port(8080);

        Spark.delete("/db", (req, res) -> (new ClearDBHandler()).handle(req, res));

        Spark.post("/user", (req, res) -> (new RegisterUserHandler()).handle(req, res));

        Spark.post("/session", (req, res) -> (new LoginHandler()).handle(req, res));

        Spark.delete("/session", (req, res) -> (new LogoutHandler()).handle(req, res));

        Spark.get("/game", (req, res) -> (new ListGamesHandler()).handle(req, res));

        Spark.post("/game", (req, res) -> (new NewGameHandler()).handle(req, res));

        Spark.put("/game", (req, res) -> (new JoinGameHandler()).handle(req, res));
    }
}
