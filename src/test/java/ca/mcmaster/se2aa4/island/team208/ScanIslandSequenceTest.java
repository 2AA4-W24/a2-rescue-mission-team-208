package ca.mcmaster.se2aa4.island.team208;
import org.json.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class ScanIslandSequenceTest {

    private ScanIslandSequence sequence;
    private List<Action> decisionQueue;
    private List<Result> results;
    private Drone drone;
    private RadarInterpreter radar;
    private ScanInterpreter scanner;
    private IslandMap map;

    @BeforeEach
    void setUp() {
        // Assume a way to initialize the drone facing South through its constructor or initialization method
        JSONObject droneInfo = new JSONObject("{\"heading\":\"S\", \"budget\":1000}");
        drone = new Drone();
        drone.initializeDrone(droneInfo, 0, 0); 

        results = new ArrayList<>();
        radar = new RadarInterpreter(); 
        scanner = new ScanInterpreter(); 
        map = new IslandMap(new Position(0, 0));

        sequence = new ScanIslandSequence(drone, radar, scanner, map);
        decisionQueue = new ArrayList<>();
    }

    @Test
    void testInitialDecisionFacingSouth() {
        sequence.generateNextActions(decisionQueue);
        assertFalse(decisionQueue.isEmpty(), "Decision queue should not be empty after the first decision.");
        assertEquals(Action.ECHO_FRONT, decisionQueue.get(0), "The first action should be ECHO_FRONT when starting facing South.");
    }

    void testReactionToScanFindingCreek() {
        // Simulate scan result indicating a creek was found
        String scanJson = "{\"cost\":1, \"status\":\"OK\", \"extras\":{\"creeks\":[\"creek-123\"], \"biomes\":[], \"sites\":[]}}";
        Result scanResult = new Result(scanJson,Action.SCAN);
        results.add(scanResult);
    
        // Assuming drone is facing the direction required by the test at the start
        sequence.generateNextActions(decisionQueue);
        
        assertTrue(decisionQueue.contains(Action.ECHO_FRONT), "Decision queue should include ECHO_FRONT after finding a creek in the scan.");
    }

}
