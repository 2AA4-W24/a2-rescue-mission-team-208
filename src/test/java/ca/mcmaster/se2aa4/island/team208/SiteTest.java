package ca.mcmaster.se2aa4.island.team208;

import ca.mcmaster.se2aa4.island.team208.MapTools.Position;
import ca.mcmaster.se2aa4.island.team208.Records.Site;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class SiteTest {

    @Test
    public void testSiteProperties() {
        
        Position pos = new Position(10, 20);
        String uuid = "site-123";

        Site site = new Site(uuid, pos);

        // Test that the UUID and position are correctly assigned and retrieved
        assertEquals(uuid, site.uuid(), "The UUID should match the one provided at creation");
        assertEquals(pos, site.pos(), "The position should match the one provided at creation");
    }
}