package ca.mcmaster.se2aa4.island.team208;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.platform.commons.PreconditionViolationException;

import java.util.List;


public class FindIslandBoundariesSequence implements ActionSequence {
    private final Logger logger = LogManager.getLogger();
    private final List<Result> results;
    private final Drone drone;
    private final IslandMap map;

    private boolean hasStarted;
    private RadarInterpreter radar;
    private int distanceTillMIA;

    private Position topLeft;
    private Position bottomRight;
    private int numRightTurns;
    private final int START_STEP;
    private boolean seenIsland;
    private boolean completed;

    //Assumes drone is at the North-West corner facing East
    public FindIslandBoundariesSequence(List<Result> results, Drone drone, IslandMap map, RadarInterpreter radar, int START_STEP) {
        this.results=results;
        this.drone = drone;
        this.map=map;
        this.radar = radar;

        this.START_STEP = START_STEP;
        this.hasStarted = false;
        this.completed = false;
        this.seenIsland = false;
        this.topLeft = new Position((int)Double.POSITIVE_INFINITY,(int)-Double.POSITIVE_INFINITY);
        this.bottomRight = new Position((int)-Double.POSITIVE_INFINITY,(int)Double.POSITIVE_INFINITY);
    }

    @Override
    public void setNextDecision(List<Action> decisionQueue) {
        int stepCount = this.results.get(results.size()-1).getStep()-START_STEP;
        //checks pre-conditions
        if(
                !this.hasStarted &&
                (this.drone.getX()>this.map.getStart().getX()+1
                || this.drone.getY()>this.map.getStart().getX()+1
                || !this.drone.getDirection().equals(Direction.E))
        )
        {
            throw new PreconditionViolationException(
                    "Drone must be on the North-West corner facing East before " +
                            "invoking \"setNextDecision()\" for the first time."
            );
        }
        this.hasStarted = true;

        //decision-making logic
        if(stepCount==0){
            decisionQueue.add(Action.ECHO_FRONT);
        }
        else if(numRightTurns >= 4){
            this.completed = true;
            this.map.setTopLeft(topLeft);
            this.map.setBottomRight(bottomRight);
            decisionQueue.add(Action.SCAN);
        }
        else{
            if(decisionQueue.get(stepCount-1).equals(Action.ECHO_FRONT)){
                this.distanceTillMIA = this.radar.getRange();
                decisionQueue.add(Action.FLY);
                this.distanceTillMIA--;
            }
            else{
                if(decisionQueue.get(stepCount-1).equals(Action.ECHO_RIGHT)){
                    if(this.radar.getFound().equals(RadarInterpreter.OUT_OF_RANGE)){
                        if(seenIsland || distanceTillMIA<=1){
                            decisionQueue.add(Action.TURN_RIGHT);
                            decisionQueue.add(Action.ECHO_FRONT);
                            seenIsland = false;
                            numRightTurns++;
                        }
                        else{
                            decisionQueue.add(Action.FLY);
                            distanceTillMIA--;
                            decisionQueue.add(Action.ECHO_RIGHT);
                        }
                    }
                    else if(this.radar.getFound().equals(RadarInterpreter.GROUND)){
                        Position ground = this.drone.copyPosition();
                        int [] vectorRight = Direction.getDirectionVector(Direction.getRight(this.drone.getDirection()));
                        int range = this.radar.getRange();
                        ground.changeByOffset(vectorRight[0]*range,vectorRight[1]*range);
                        updateBoundaries(ground);
                        this.seenIsland=true;

                        logger.info("New ground position data: " + ground);

                        decisionQueue.add(Action.FLY);
                        distanceTillMIA--;
                    }
                    else{
                        decisionQueue.add(Action.ECHO_RIGHT);
                    }
                }
                else{
                    decisionQueue.add(Action.ECHO_RIGHT);
                }
            }
        }



    }

    private void updateBoundaries(Position groundPosition){
        topLeft.setPosition(Math.min(topLeft.getX(), groundPosition.getX()),
                            Math.max(topLeft.getY(), groundPosition.getY()));
        bottomRight.setPosition(Math.max(bottomRight.getX(), groundPosition.getX()),
                                Math.min(bottomRight.getY(), groundPosition.getY()));
    }

    @Override
    public boolean hasCompleted() {
        return this.completed;
    }
}
