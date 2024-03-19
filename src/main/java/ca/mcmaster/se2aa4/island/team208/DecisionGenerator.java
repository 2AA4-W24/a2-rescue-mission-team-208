package ca.mcmaster.se2aa4.island.team208;

public interface DecisionGenerator {
    void initialize(String setup, int xStart, int yStart);
    String getNextStep();
    void processResults(String result);
    String getReport();
}
