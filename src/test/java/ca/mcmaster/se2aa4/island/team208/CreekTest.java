package ca.mcmaster.se2aa4.island.team208;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CreekTest {

    @Test
    public void testCreekProperties() {
        Position pos = new Position(10, 20);
        String uuid = "creek-123";

        Creek creek = new Creek(uuid, pos);

        // Test that the UUID and position
        assertEquals(uuid, creek.uuid(), "The UUID should match the one provided at creation");
        assertEquals(pos, creek.pos(), "The position should match the one provided at creation");
    }
}