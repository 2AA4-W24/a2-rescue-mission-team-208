package ca.mcmaster.se2aa4.island.team208;

import ca.mcmaster.se2aa4.island.team208.ExplorerComponents.Decision.Decider;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.json.*;

import java.io.StringReader;

public class DeciderTest {

    private Decider decider;

    @BeforeEach
    void setUp() {
        decider = new Decider();

        // Setup for Drone with an initial position and direction
        JSONObject droneInfo = new JSONObject("{\"heading\":\"E\", \"budget\":1000}");
        this.decider.initialize(droneInfo.toString(),0,0);

    }

    @Test
    void testInitialDecision() {

        JSONObject decision = new JSONObject(new JSONTokener(new StringReader(decider.getNextStep())));

        assertEquals("echo", decision.getString("action"), "Initial action should be ECHO_FRONT.");
        assertEquals("E", decision.getJSONObject("parameters").getString("direction"), "Initial echo direction should be East.");
    }

    
}