package ca.mcmaster.se2aa4.island.team208;
import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class ResultTest {

    @Test
    void testResultParsingAndProperties() {
        // Example JSON string input
        String jsonInput = "{\"cost\":5, \"status\":\"SUCCESS\", \"extras\":{\"detail\":\"example\"}, \"step\":1}";
        Result result = new Result(jsonInput, 1);

        // Verification
        assertEquals(5, result.getCost(), "Cost should be correctly parsed and returned.");
        assertEquals("SUCCESS", result.getStatus(), "Status should be correctly parsed and returned.");
        
        // Check the extra_info JSON object
        assertEquals("example", result.getExtraInfo().getString("detail"), "Extra info should contain the correct 'detail' value.");
        
        // Verify step is correctly initialized
        assertEquals(1, result.getStep(), "Step should be correctly initialized and returned.");

        // Ensure the response string is correctly formatted
        JSONObject expectedResponse = new JSONObject(jsonInput);
        assertEquals(expectedResponse.toString(2), result.getResponseString(), "Response string should match the expected JSON format.");
    }

}