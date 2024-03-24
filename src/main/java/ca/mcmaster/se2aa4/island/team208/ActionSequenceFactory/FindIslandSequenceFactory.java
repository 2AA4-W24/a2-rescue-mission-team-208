package ca.mcmaster.se2aa4.island.team208.ActionSequenceFactory;

import ca.mcmaster.se2aa4.island.team208.ActionFactory.*;
import ca.mcmaster.se2aa4.island.team208.Enums.Action;
import ca.mcmaster.se2aa4.island.team208.Interpreters.RadarInterpreter;
import ca.mcmaster.se2aa4.island.team208.ExplorerComponents.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindIslandSequenceFactory implements ActionSequenceFactory {
    private RadarInterpreter radarInterpreter;
    private boolean completed;
    private final static Map<Action, ActionFactory> mapActionSeq = new HashMap<>();


    public FindIslandSequenceFactory(RadarInterpreter radar){
        this.radarInterpreter=radar;
        this.completed=false;
        mapActionSeq.put(Action.FLY, new FindIslandFlyActions());
        mapActionSeq.put(Action.ECHO_RIGHT, new FindIslandEchoRightActions());
        mapActionSeq.put(Action.TURN_RIGHT, new FindIslandTurnRightActions());
        mapActionSeq.put(Action.ECHO_FRONT, new FindIslandEchoFrontActions());
    }
    @Override
    public void generateNextActions(List<Action> decisionQueue) {
        if (decisionQueue.size() - 1 == 0) {
            decisionQueue.add(Action.ECHO_RIGHT);
            return;
        }
        mapActionSeq.get(decisionQueue.get(decisionQueue.size() - 1))
                .addActions(decisionQueue, this.radarInterpreter, null);
        this.completed = mapActionSeq.get(decisionQueue.get(decisionQueue.size() - 1))
                .lastAction();
    }

    @Override
    public boolean hasCompleted() {
        return this.completed;
    }
}
