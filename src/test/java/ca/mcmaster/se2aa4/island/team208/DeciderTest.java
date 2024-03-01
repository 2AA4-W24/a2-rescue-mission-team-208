package ca.mcmaster.se2aa4.island.team208;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class DeciderTest {

    private Decider decider;

    @BeforeEach
    public void init(){
        decider = new Decider(new Drone(), new Radar());
    }

    @Test
    public void test() {

    }


}
