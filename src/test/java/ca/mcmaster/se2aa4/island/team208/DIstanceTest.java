package ca.mcmaster.se2aa4.island.team208;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class DIstanceTest {
    
    public class DistanceTest {

    @Test
    public void testDistanceCalculation() {
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(3, 4);

        Distance distance = new Distance(pos1, pos2);
        
        // The expected distance is sqrt(3^2+4^2)=5
        assertEquals(5, distance.getDistance(), 0.01, "The distance between (0,0) and (3,4) should be 5.");
    }

    @Test
    public void testDistanceWithNegativeCoordinates() {
        Position pos1 = new Position(-1, -2);
        Position pos2 = new Position(-4, -6);

        Distance distance = new Distance(pos1, pos2);
        
        double expectedDistance = Math.sqrt(Math.pow((-4 + 1), 2) + Math.pow((-6 + 2), 2));
        assertEquals(expectedDistance, distance.getDistance(), 0.01, "The distance calculation with negative coordinates is incorrect.");
    }

}
}
