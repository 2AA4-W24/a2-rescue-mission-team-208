package ca.mcmaster.se2aa4.island.team208;
import ca.mcmaster.se2aa4.island.team208.ExplorerComponents.Report;
import ca.mcmaster.se2aa4.island.team208.MapTools.Position;
import ca.mcmaster.se2aa4.island.team208.Records.Creek;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ReportTest {

    @Test
    void testClosestCreekAssignment() {
        Position testPosition = new Position(0, 0);
        Creek testCreek = new Creek("creek-123",testPosition);
        Report report = new Report(testCreek);
        
        assertNotNull(report.getClosestCreek(), "Closest creek should not be null.");
        assertEquals(testCreek, report.getClosestCreek(), "The closest creek should match the one provided at construction.");
        assertEquals(testPosition, report.getClosestCreek().pos(), "The position of the closest creek should match the one provided.");
    }

    @Test
    void testReportContents() {
        Position testPosition = new Position(0, 0);
        Creek testCreek = new Creek("creek-123",testPosition);
        Report report = new Report(testCreek);
        
        // Construct the expected contents string
        String expectedContents = "The closest creek is: creek-123";
        assertEquals(expectedContents, report.toString(), "Report contents should accurately describe the closest creek.");
        assertEquals(testPosition, report.getClosestCreek().pos(), "The position of the closest creek should match the one provided.");
    }
}