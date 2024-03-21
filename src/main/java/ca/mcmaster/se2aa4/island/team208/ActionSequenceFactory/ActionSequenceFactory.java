package ca.mcmaster.se2aa4.island.team208.ActionSequenceFactory;

import ca.mcmaster.se2aa4.island.team208.Enums.Action;

import java.util.List;

public interface ActionSequenceFactory {
    void generateNextActions(List<Action>decisionQueue);
    boolean hasCompleted();
}
