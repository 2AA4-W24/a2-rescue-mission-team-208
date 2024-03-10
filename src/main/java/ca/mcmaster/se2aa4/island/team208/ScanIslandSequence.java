package ca.mcmaster.se2aa4.island.team208;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.platform.commons.PreconditionViolationException;

import java.util.ArrayList;
import java.util.List;

public class ScanIslandSequence implements ActionSequence{
    private List<Result> results;
    private Drone drone;
    private RadarInterpreter radar;
    private ScanInterpreter scanner;

    private boolean started;
    private boolean secondFlyOver;
    private boolean completed;
    private int numTurns;
    private boolean lastScanEmpty;

    private Logger logger = LogManager.getLogger();

    //this scans all parts of Island South-East of position of first call to "setNextDecision(List <Action>)"
    //must be facing south
    public ScanIslandSequence(List<Result> results, Drone drone, RadarInterpreter radar, ScanInterpreter scanner){
        this.results = results;
        this.drone = drone;
        this.radar = radar;
        this.scanner = scanner;

        this.started=false;
        this.secondFlyOver=false;
        this.completed=false;
        this.numTurns=0;
        this.lastScanEmpty=false;
    }

    @Override
    public void setNextDecision(List<Action> decisionQueue) {
        logger.info("Started: "+this.started);
        logger.info("Second Fly Over: "+this.secondFlyOver);
        logger.info("Last Scan Empty: "+this.lastScanEmpty);
        logger.info("Number of turns: "+this.numTurns);
        logger.info("Completed: "+this.completed);

        if(this.started && !this.completed){
            switch(decisionQueue.get(decisionQueue.size()-1)){
                //need to find ground
                case ECHO_FRONT -> {
                    ArrayList<Action>flyActions = getFlyToGround(radar);

                    //no ground found in front of drone
                    if(!this.radar.getFound().equals(RadarInterpreter.GROUND)){
                        switch (this.drone.getDirection()){
                            //facing south
                            case S-> {
                                //drone is on second fly over
                                if(secondFlyOver){
                                    if(numTurns>=0){
                                        decisionQueue.add(Action.TURN_RIGHT);
                                        decisionQueue.add(Action.TURN_RIGHT);
                                        decisionQueue.add(Action.ECHO_FRONT);
                                        this.numTurns--;
                                    }
                                    else{
                                        this.completed=true;
                                        decisionQueue.add(Action.SCAN);
                                    }
                                }
                                //drone still on first fly over
                                else{
                                    //North-East edge of map facing South
                                    if(lastScanEmpty){
                                        decisionQueue.add(Action.TURN_LEFT);
                                        decisionQueue.add(Action.TURN_LEFT);
                                        decisionQueue.add(Action.TURN_LEFT);
                                        decisionQueue.add(Action.FLY);
                                        decisionQueue.add(Action.TURN_LEFT);
                                        decisionQueue.add(Action.ECHO_FRONT);
                                        this.secondFlyOver= true;
                                    }
                                    else{
                                        decisionQueue.add(Action.TURN_LEFT);
                                        decisionQueue.add(Action.TURN_LEFT);
                                        this.numTurns++;
                                        decisionQueue.add(Action.ECHO_FRONT);
                                        this.lastScanEmpty=true;
                                    }
                                }
                            }
                            case N->{
                                if(secondFlyOver){
                                    if(numTurns>=0){
                                        decisionQueue.add(Action.TURN_LEFT);
                                        decisionQueue.add(Action.TURN_LEFT);
                                        decisionQueue.add(Action.ECHO_FRONT);
                                        this.numTurns--;
                                    }
                                    else{
                                        this.completed=true;
                                        decisionQueue.add(Action.SCAN);
                                    }
                                }
                                else{
                                    if(lastScanEmpty){
                                        decisionQueue.add(Action.TURN_RIGHT);
                                        decisionQueue.add(Action.TURN_RIGHT);
                                        decisionQueue.add(Action.TURN_RIGHT);
                                        decisionQueue.add(Action.FLY);
                                        decisionQueue.add(Action.TURN_RIGHT);
                                        decisionQueue.add(Action.ECHO_FRONT);
                                        this.secondFlyOver=true;
                                    }
                                    else{
                                        decisionQueue.add(Action.TURN_RIGHT);
                                        decisionQueue.add(Action.TURN_RIGHT);
                                        this.numTurns++;
                                        decisionQueue.add(Action.ECHO_FRONT);
                                        this.lastScanEmpty=true;
                                    }
                                }
                            }
                        }
                    }
                    else{
                        decisionQueue.addAll(flyActions);
                        decisionQueue.add(Action.SCAN);
                        this.lastScanEmpty = false;
                    }

                }
                //was on ground last scan
                case SCAN -> {
                    if(this.scanner.isOceanOnly()){
                        decisionQueue.add(Action.ECHO_FRONT);
                    }
                    else{
                        decisionQueue.add(Action.FLY);
                        decisionQueue.add(Action.SCAN);
                    }
                }
            }
        }
        else{
            if(!this.drone.getDirection().equals(Direction.S)){
                throw new PreconditionViolationException("Drone must be facing South before calling setNextDecision() for the first time.");
            }
            else{
                this.started = true;
                decisionQueue.add(Action.ECHO_FRONT);
            }
        }
    }

    private ArrayList<Action> getFlyToGround(RadarInterpreter radar){
        ArrayList<Action> steps = new ArrayList<>();
        if(radar.getFound().equals(RadarInterpreter.GROUND)){
            for(int i =0; i<=radar.getRange(); i++){
                steps.add(Action.FLY);
            }
            steps.add(Action.SCAN);
        }
        return steps;

    }

    @Override
    public boolean hasCompleted() {
        return this.completed;
    }
}
