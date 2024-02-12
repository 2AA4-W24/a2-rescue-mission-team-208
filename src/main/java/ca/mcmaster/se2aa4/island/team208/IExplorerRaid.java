package ca.mcmaster.se2aa4.island.team208;
public interface IExplorerRaid {
    void initialize(String s);
    String takeDecision();
    void acknowledgeResults(String s);
    String deliverFinalReport();
}