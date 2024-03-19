package ca.mcmaster.se2aa4.island.team208;
import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
public class ResultTest {

    @Test
    void testResultParsingAndProperties() {
        // Example JSON string input
        String jsonInput = "{\"cost\":5, \"status\":\"SUCCESS\", \"extras\":{\"detail\":\"example\"}, \"step\":1}";
        Result result = new Result(jsonInput, Action.FLY);

        // Verification
        assertEquals(5, result.getCost(), "Cost should be correctly parsed and returned.");
        assertEquals("SUCCESS", result.getStatus(), "Status should be correctly parsed and returned.");
        
        // Check the extra_info JSON object
        assertEquals("example", result.getExtras().getString("detail"), "Extra info should contain the correct 'detail' value.");


        // Ensure the response string is correctly formatted
        JSONObject expectedResponse = new JSONObject(jsonInput);
        assertEquals(expectedResponse.toString(2), result.toString(), "Response string should match the expected JSON format.");
    }

}