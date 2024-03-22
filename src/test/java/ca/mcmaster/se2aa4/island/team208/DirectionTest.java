package ca.mcmaster.se2aa4.island.team208;

import ca.mcmaster.se2aa4.island.team208.Enums.Direction;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    @BeforeEach
    public void init(){

    }

    @Test
    public void staticMethodTests() {
        assertEquals(Direction.getRight(Direction.W), Direction.getLeft(Direction.E));
        assertEquals(Direction.N,Direction.getOpposite(Direction.S));
        assertEquals(Direction.E,Direction.getOpposite(Direction.W));
    }


}
