package ca.mcmaster.se2aa4.island.team208;
import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RadarInterpreterTest {

    private RadarInterpreter radarInterpreter;

    @BeforeEach
    void setUp() {
        radarInterpreter = new RadarInterpreter();
    }

    @Test
    void testInitialValues() {
        assertThrows(NullPointerException.class, radarInterpreter::getRange);
        assertThrows(NullPointerException.class, radarInterpreter::getFound);
    }

    @Test
    void testSaveEchoResultWithGround() {
        // Simulate a successful echo result indicating ground found within range
        String resultJson = "{\"cost\":1, \"status\":\"OK\", \"extras\":{\"range\":5, \"found\":\"GROUND\"}}";
        Result echoResult = new Result(resultJson, 1);
        radarInterpreter.saveEchoResult(echoResult);

        assertEquals(5, radarInterpreter.getRange(), "Range should be 5 after processing echo result.");
        assertEquals("GROUND", radarInterpreter.getFound(), "Found should be GROUND after processing echo result.");
    }

    @Test
    void testSaveEchoResultOutOfRange() {
        // Simulate an echo result indicating out of range
        String resultJson = "{\"cost\":1, \"status\":\"OK\", \"extras\":{\"range\":0, \"found\":\"OUT_OF_RANGE\"}}";
        Result echoResult = new Result(resultJson, 1);
        radarInterpreter.saveEchoResult(echoResult);

        assertEquals(0, radarInterpreter.getRange(), "Range should be 0 for OUT_OF_RANGE result.");
        assertEquals("OUT_OF_RANGE", radarInterpreter.getFound(), "Found should be OUT_OF_RANGE after processing echo result.");
    }

}
