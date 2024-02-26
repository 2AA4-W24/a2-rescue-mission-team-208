package ca.mcmaster.se2aa4.island.team208;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ExampleTest {
    public int x = 0;



    @BeforeEach
    public void init(){
        x = 0;
    }

    @Test
    public void sampleTest() {
        assertTrue(x == 1);
    }


}
