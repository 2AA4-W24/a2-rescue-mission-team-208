package ca.mcmaster.se2aa4.island.team208;
import org.json.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScanInterpreterTest {

    private ScanInterpreter scanInterpreter;

    @BeforeEach
    void setUp() {
        scanInterpreter = new ScanInterpreter();
    }

    @Test
    void testSaveScanAndDataRetrieval() {
        // Example Json Object
        String scanJson = "{\"cost\":1, \"status\":\"OK\", \"extras\":{\"creeks\":[\"creek-id1\"], \"biomes\":[\"OCEAN\", \"BEACH\"], \"sites\":[\"site-id1\"]}}";
        Result scanResult = new Result(scanJson, 1);
        scanInterpreter.saveScan(scanResult);

        // Verify that the creeks, biomes, and sites are correctly parsed and stored
        assertEquals(1, scanInterpreter.getCreeks().length(), "There should be one creek stored.");
        assertEquals(2, scanInterpreter.getBiomes().length(), "There should be two biomes stored.");
        assertEquals(1, scanInterpreter.getSites().length(), "There should be one site stored.");
    }

    @Test
    void testIsOceanOnly() {
        // Adjusted to include empty arrays for "creeks" and "sites" to avoid IllegalArgumentException
        String oceanOnlyJson = "{\"cost\":1, \"status\":\"OK\", \"extras\":{\"creeks\":[], \"biomes\":[\"OCEAN\"], \"sites\":[]}}";
        Result oceanOnlyResult = new Result(oceanOnlyJson, 1);
        scanInterpreter.saveScan(oceanOnlyResult);
    
        assertTrue(scanInterpreter.isOceanOnly(), "isOceanOnly should return true when the biome is only OCEAN.");
    }

}