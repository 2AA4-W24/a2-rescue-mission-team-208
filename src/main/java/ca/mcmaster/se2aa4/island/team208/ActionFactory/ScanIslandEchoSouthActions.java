package ca.mcmaster.se2aa4.island.team208.ActionFactory;


import ca.mcmaster.se2aa4.island.team208.Enums.Action;
import ca.mcmaster.se2aa4.island.team208.Interpreters.RadarInterpreter;
import ca.mcmaster.se2aa4.island.team208.Interpreters.ScanInterpreter;


import java.util.List;

public class ScanIslandEchoSouthActions implements ActionFactory {
    private Action southEcho;
    private Action southTurn;
    public ScanIslandEchoSouthActions(Action southEcho, Action southTurn) {
        this.southEcho = southEcho;
        this.southTurn = southTurn;
    }
    @Override
    public void addActions(List<Action> decisionQueue, RadarInterpreter radarInterpreter, ScanInterpreter scanInterpreter) {
        if (radarInterpreter.getFound().equals("GROUND")) {
            decisionQueue.add(Action.FLY);
            if (radarInterpreter.getRange() == 0) decisionQueue.add(Action.SCAN);
            decisionQueue.add(this.southEcho);
        } else {
            decisionQueue.add(this.southTurn);
            decisionQueue.add(this.southTurn);
            decisionQueue.add(Action.ECHO_FRONT);
        }
    }
    @Override
    public boolean lastAction () {
        return false;
    }
}
