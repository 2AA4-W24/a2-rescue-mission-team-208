package ca.mcmaster.se2aa4.island.team208.ActionFactory;


import ca.mcmaster.se2aa4.island.team208.Enums.Action;
import ca.mcmaster.se2aa4.island.team208.Interpreters.RadarInterpreter;
import ca.mcmaster.se2aa4.island.team208.Interpreters.ScanInterpreter;

import java.util.List;

public class ScanIslandEchoNorthActions implements ActionFactory {
    private Action northEcho;
    private Action northTurn;
    public ScanIslandEchoNorthActions(Action southEcho, Action southTurn) {
        this.northEcho = southEcho;
        this.northTurn = southTurn;
    }
    @Override
    public void addActions(List<Action> decisionQueue, RadarInterpreter radarInterpreter, ScanInterpreter scanInterpreter) {
        if (radarInterpreter.getFound().equals("GROUND")) {
            decisionQueue.add(Action.FLY);
            if (radarInterpreter.getRange() == 0) decisionQueue.add(Action.SCAN);
            decisionQueue.add(this.northEcho);
        } else {
            decisionQueue.add(this.northTurn);
            decisionQueue.add(this.northTurn);
            decisionQueue.add(Action.ECHO_FRONT);
        }
    }
    @Override
    public boolean lastAction () {
        return false;
    }
}
