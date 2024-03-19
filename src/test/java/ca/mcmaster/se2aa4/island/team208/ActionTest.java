package ca.mcmaster.se2aa4.island.team208;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ActionTest {

    @Test
    public void testActionEnumToString() {
        assertEquals("fly", Action.FLY.toString(), "FLY action string mismatch");
        assertEquals("heading", Action.TURN_RIGHT.toString(), "TURN_RIGHT action string mismatch");
        assertEquals("heading", Action.TURN_LEFT.toString(), "TURN_LEFT action string mismatch");
        assertEquals("echo", Action.ECHO_RIGHT.toString(), "ECHO_RIGHT action string mismatch");
        assertEquals("echo", Action.ECHO_LEFT.toString(), "ECHO_LEFT action string mismatch");
        assertEquals("echo", Action.ECHO_FRONT.toString(), "ECHO_FRONT action string mismatch");
        assertEquals("scan", Action.SCAN.toString(), "SCAN action string mismatch");
        assertEquals("stop", Action.STOP.toString(), "STOP action string mismatch");
        assertEquals("land", Action.LAND.toString(), "LAND action string mismatch");
    }
}