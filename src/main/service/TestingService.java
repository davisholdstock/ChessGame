package service;

import server.server;

/**
 * A service that defines methods that helps in testing
 */
public class TestingService {

    public TestingService() {
    }

    /**
     * clears all the saved data
     * for testing purposes
     *
     * @return the attempted clearing data response
     */
    public ClearResponse clearData() {
        try {
            server.db.clear();
            return new ClearResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return new ClearResponse("Error: description");
        }
    }
}
