package ca.mcmaster.se2aa4.island.team208.ActionFactory;


import ca.mcmaster.se2aa4.island.team208.Enums.Action;

import ca.mcmaster.se2aa4.island.team208.Interpreters.RadarInterpreter;
import ca.mcmaster.se2aa4.island.team208.Interpreters.ScanInterpreter;

import java.util.List;

public class ScanIslandScanFlyActions implements ActionFactory {
    @Override
    public void addActions(List<Action> decisionQueue, RadarInterpreter radarInterpreter, ScanInterpreter scanInterpreter) {
        if (!scanInterpreter.isOceanOnly()) {
            decisionQueue.add(Action.FLY);
            decisionQueue.add(Action.SCAN);
        } else {
            decisionQueue.add(Action.ECHO_FRONT);
        }
    }
    @Override
    public boolean lastAction () {
        return false;
    }
}
