package ca.mcmaster.se2aa4.island.team208;
import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DroneTest {

    private Drone drone;

    @BeforeEach
    void setUp() {
        drone = new Drone();
        // Initializing the drone with JSON object information
        JSONObject droneInfo = new JSONObject("{\"heading\":\"E\", \"budget\":1000}");
        drone.initializeDrone(droneInfo, 0, 0);
    }

    @Test
    void testInitialization() {
        assertEquals(Direction.E, drone.getDirection(), "Direction should be East after initialization.");
        assertEquals(1000, drone.getBattery(), "Battery should be 1000 after initialization.");
        assertEquals(0, drone.getX(), "Initial X position should be 0.");
        assertEquals(0, drone.getY(), "Initial Y position should be 0.");
    }

    @Test
    void testFlyAndUpdateBattery() {
        String flyJson = "{\"cost\":10, \"status\":\"OK\", \"extras\":{}}";
        Result flyResult = new Result(flyJson,Action.FLY); // Using step count 1 for simplicity

        drone.processResults(flyResult);
        
        assertEquals(1000 - flyResult.getCost(), drone.getBattery(), "Battery should decrease by the cost of flying.");
        assertEquals(1, drone.getX(), "Drone should move 1 unit East when flying.");
        assertEquals(0, drone.getY(), "Y position should remain unchanged when flying East.");
    }

    @Test
    void testTurnRight() {
        String turnJson = "{\"cost\":5, \"status\":\"OK\", \"extras\":{}}";
        Result turnRightResult = new Result(turnJson,Action.FLY);

        drone.processResults(turnRightResult);

        assertEquals(Direction.S, drone.getDirection(), "Direction should be South after turning right from East.");
    }

    @Test
    void testTurnLeft() {
        String turnJson = "{\"cost\":5, \"status\":\"OK\", \"extras\":{}}";
        Result turnLeftResult = new Result(turnJson, Action.TURN_LEFT);

        drone.processResults(turnLeftResult);

        assertEquals(Direction.N, drone.getDirection(), "Direction should be North after turning left from East.");
    }
}