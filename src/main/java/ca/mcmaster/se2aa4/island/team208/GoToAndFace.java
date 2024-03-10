package ca.mcmaster.se2aa4.island.team208;

import java.util.ArrayList;
import java.util.List;

public class GoToAndFace implements ActionSequence{
    private Position destination;
    private Direction direction;
    private Drone drone;

    //This class makes no assumptions on reachability of destination and direction
    //If a specified destination is unreachable, drone will go MIA
    public GoToAndFace (Position destination, Direction direction, Drone drone){
        this.destination=destination;
        this.direction=direction;
        this.drone=drone;
    }


    @Override
    public void setNextDecision(List<Action> decisionQueue) {
        ArrayList<Action> instructions = new ArrayList<>();



        decisionQueue.addAll(instructions);
    }

    @Override
    public boolean hasCompleted() {
        return true;
    }
}
