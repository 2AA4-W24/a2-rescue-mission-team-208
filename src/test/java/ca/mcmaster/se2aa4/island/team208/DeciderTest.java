package ca.mcmaster.se2aa4.island.team208;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.json.*;

public class DeciderTest {

    private Decider decider;
    private Drone drone;

    @BeforeEach
    void setUp() {
        // Setup for Drone with an initial position and direction
        JSONObject droneInfo = new JSONObject("{\"heading\":\"E\", \"budget\":1000}");
        drone = new Drone();
        drone.initializeDrone(droneInfo, 0, 0);
        
        decider = new Decider(drone);
    }

    @Test
    void testInitialDecision() {
        Result initialResult = new Result("{\"status\":\"OK\", \"cost\":0, \"extras\":{}}", 0);
        JSONObject decision = decider.getNextStep(initialResult);

        assertEquals("echo", decision.getString("action"), "Initial action should be ECHO_FRONT.");
        assertEquals("E", decision.getJSONObject("parameters").getString("direction"), "Initial echo direction should be East.");
    }

    
}