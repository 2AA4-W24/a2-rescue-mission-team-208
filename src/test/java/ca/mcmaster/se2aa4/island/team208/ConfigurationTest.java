package ca.mcmaster.se2aa4.island.team208;

import ca.mcmaster.se2aa4.island.team208.ExplorerComponents.Configuration;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


public class ConfigurationTest {

    @Test
    public void testValidConfiguration() {
        
        String testJson = "{\"heading\":\"N\", \"budget\":1000}";
        Configuration config = new Configuration(testJson);
        
        // Verifying that the direction and battery information are parsed correctly
        assertEquals("N", config.getDirection(), "Direction should be N");
        assertEquals(Integer.valueOf(1000), config.getBattery(), "Battery budget should be 1000");
    }
    

    // Additional test cases can be added as needed
}
    

