package ca.mcmaster.se2aa4.island.team208.ActionFactory;


import ca.mcmaster.se2aa4.island.team208.Enums.Action;
import ca.mcmaster.se2aa4.island.team208.Interpreters.RadarInterpreter;
import ca.mcmaster.se2aa4.island.team208.Interpreters.ScanInterpreter;

import java.util.List;

public class FindIslandEchoFrontActions implements ActionFactory {
    @Override
    public void addActions(List<Action> decisionQueue, RadarInterpreter radarInterpreter, ScanInterpreter scanInterpreter) {
        for (int i = 0; i < radarInterpreter.getRange(); i++) decisionQueue.add(Action.FLY);
    }
    @Override
    public boolean lastAction () {
        return true;
    }
}
