package ca.mcmaster.se2aa4.island.team208.ActionFactory;


import ca.mcmaster.se2aa4.island.team208.Enums.Action;
import ca.mcmaster.se2aa4.island.team208.Interpreters.RadarInterpreter;
import ca.mcmaster.se2aa4.island.team208.Interpreters.ScanInterpreter;

import java.util.List;

public class FindIslandTurnRightActions implements ActionFactory {
    @Override
    public void addActions(List<Action> decisionQueue, RadarInterpreter radarInterpreter, ScanInterpreter scanInterpreter) {
        decisionQueue.add(Action.ECHO_FRONT);
    }
    @Override
    public boolean lastAction () {
        return false;
    }
}
