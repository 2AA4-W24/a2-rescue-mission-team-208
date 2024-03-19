package ca.mcmaster.se2aa4.island.team208;

import java.util.List;

public class FindIslandSequence implements ActionSequence{
    private RadarInterpreter radarInterpreter;
    private List<Result>results;
    private boolean completed;


    public FindIslandSequence(RadarInterpreter radar, List<Result>results){
        this.radarInterpreter=radar;
        this.results = results;
        this.completed=false;
    }
    @Override
    public void generateNextActions(List<Action> decisionQueue) {
        decisionQueue.add(Action.SCAN);
        if (this.results.size() - 1 == 0 || decisionQueue.get(this.results.size() - 1) == Action.FLY) {
            decisionQueue.add(Action.ECHO_RIGHT);
        } else if (decisionQueue.get(this.results.size() - 1) == Action.ECHO_RIGHT) {
            if (this.radarInterpreter.getFound().equals("GROUND")) {
                decisionQueue.add(Action.TURN_RIGHT);
            }
        } else if (decisionQueue.get(this.results.size() - 1) == Action.TURN_RIGHT) {
            decisionQueue.add(Action.ECHO_FRONT);
        } else if (decisionQueue.get(this.results.size() - 1) == Action.ECHO_FRONT) {
            for (int i = 0; i < this.radarInterpreter.getRange(); i++) {
                decisionQueue.add(Action.FLY);
            }
            decisionQueue.add(Action.SCAN);
            this.completed = true;
        } else {
            decisionQueue.add(Action.FLY);
        }
    }

    @Override
    public boolean hasCompleted() {
        return this.completed;
    }
}
