package handler;

import com.google.gson.Gson;
import response.ClearResponse;
import service.*;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Handles the Testing services
 */
public class ClearDBHandler implements Route {
    Gson gson;

    public ClearDBHandler() {
        gson = new Gson();
    }

    /**
     * Handles the clearing of all the Data
     *
     * @return
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        TestingService service = new TestingService();
        ClearResponse res = service.clearData();
        response.status(res.getSTATUS_CODE());
        return gson.toJson(res);
    }
}
