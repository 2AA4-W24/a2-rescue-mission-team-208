package ca.mcmaster.se2aa4.island.team208;


import java.util.List;

public interface ActionSequence {

    void setNextDecision(List<Action>decisionQueue);
    boolean hasCompleted();
}
