package ca.mcmaster.se2aa4.island.team208;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    private Position position;

    @BeforeEach
    void setUp() {
        position = new Position(0, 0); // Starting position for each test
    }

    @Test
    void testInitialPosition() {
        assertEquals(0, position.getX(), "Initial X coordinate should be 0.");
        assertEquals(0, position.getY(), "Initial Y coordinate should be 0.");
    }

    @Test
    void testChangeByOffset() {
        position.changeByOffset(5, -3);
        assertEquals(5, position.getX(), "X coordinate after change should be 5.");
        assertEquals(-3, position.getY(), "Y coordinate after change should be -3.");
    }

    @Test
    void testSetPosition() {
        position.setPosition(10, 10);
        assertEquals(10, position.getX(), "X coordinate after setting should be 10.");
        assertEquals(10, position.getY(), "Y coordinate after setting should be 10.");
    }

    @Test
    void testDistance(){
        assertEquals(5,Position.getDistance(position,new Position(3,4)),0.5);
    }

    @Test
    void testToString() {
        assertEquals("(0,0)", position.toString(), "String representation of initial position should be (0,0).");
        position.setPosition(1, 2);
        assertEquals("(1,2)", position.toString(), "String representation after setting position to (1,2) should be (1,2).");
    }
}