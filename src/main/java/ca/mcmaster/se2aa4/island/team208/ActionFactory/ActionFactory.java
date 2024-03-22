package ca.mcmaster.se2aa4.island.team208.ActionFactory;

import ca.mcmaster.se2aa4.island.team208.Enums.Action;
import ca.mcmaster.se2aa4.island.team208.Interpreters.RadarInterpreter;
import ca.mcmaster.se2aa4.island.team208.Interpreters.ScanInterpreter;

import java.util.List;

public interface ActionFactory {
    void addActions(List<Action> decisionQueue, RadarInterpreter radarInterpreter, ScanInterpreter scanInterpreter);
    boolean lastAction();
}
