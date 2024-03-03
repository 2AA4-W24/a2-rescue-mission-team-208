package ca.mcmaster.se2aa4.island.team208;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.util.*;

public class Decider {

    private final Logger logger = LogManager.getLogger();
    private final List<Action> decisionQueue;
    private final List<Results> results;
    private Drone drone;
    private Radar radar;
    private int currentStep = 0; // next step that needs to be executed
    private int stepLogic = 0; // inner step count
    private boolean startFindingGroundForWidth = true;// the first step, keep flying and find the ground signal from
                                                      // echoing
    private boolean startMeasuringWidth = false;
    private boolean startMeasuringLength = false;
    private boolean needUturn = false;
    private boolean needUturn1 = false;
    private boolean startFindingGroundForLength = false;
    private boolean startScanArea = false;
    private int locationWidth;
    private int locationLength;
    private int turningDir = 0;
    private int islandWidth = 0; // the width of island
    private int islandLength = 0; // the length of island
    private int count = 0;
    private boolean teststop = false;

    public Decider(Drone drone, Radar radar) {
        this.results = new ArrayList<>();
        this.decisionQueue = new ArrayList<>();
        this.drone = drone;
        this.radar = radar;
        this.currentStep = 0;
        this.decisionQueue.add(Action.ECHO_FRONT);
    }

    /*public JSONObject getNextStep() {
        JSONObject step;
        if (this.currentStep >= decisionQueue.size()) {
            this.setNextDecision();
        }
        step = this.translateDecision(decisionQueue.get(this.currentStep));
        this.currentStep++;
        logger.info("** Decision: {}", step.toString());
        logger.info("current step is " + currentStep + ", size of array is " + this.decisionQueue.size());
        return step;
    }*/
    public JSONObject getNextStep() {
        int previousSize;
        do {
            previousSize = decisionQueue.size();
            this.setNextDecision();
        } while (decisionQueue.size() == previousSize);
    
        if (this.currentStep < decisionQueue.size()) {
            JSONObject step = this.translateDecision(decisionQueue.get(this.currentStep));
            this.currentStep++;
            logger.info("** Decision: {}", step.toString());
            logger.info("current step is " + currentStep + ", size of the decision queue is " + this.decisionQueue.size());
            return step;
        } else {
            logger.error("Expected to have new decisions in the queue, but found none at current step: " + currentStep);
            return null;
        }
    }
    
    

    public void addResult(Results result) {
        this.results.add(result);
    }

    // function needs to eventually reach "STOP" and stay there
    // function may generate more than one step with each call
    private void setNextDecision() {
        
        
        

        // First step, keep flying and echoing right until we find a ground signal from
        // echoing
        if (this.startFindingGroundForWidth == true) {
            logger.info("FindingGroundForWidth");
            // after echoing, if it finds a ground, then goes to measuring process, if it
            // doesn't find ground, then change stepLogic to 1.
            switch (this.stepLogic) {

                case 0:
                    this.decisionQueue.add(Action.ECHO_RIGHT);
                    this.stepLogic = 1;
                    break;

                case 1:
                    // check if there is ground feedback from last echo
                    if (this.radar.getFound().equals("GROUND")) {
                        this.startFindingGroundForWidth = false;
                        this.startMeasuringWidth = true;
                        this.islandWidth++;
                    } else {
                        this.decisionQueue.add(Action.FLY);
                    }
                    this.stepLogic = 0;

                    break;

                default:
                    this.decisionQueue.add(Action.STOP);
                    break;

            }
        }

        if (this.startMeasuringWidth == true) {
            logger.info("MeasuringWidth");
            switch (this.stepLogic) {
                case 0:
                    if (this.radar.getFound().equals("OUT_OF_RANGE")) {
                        this.decisionQueue.add(Action.ECHO_FRONT);
                        logger.info("Ground not found");
                        logger.info("the width of island is " + this.islandWidth);
                        this.locationWidth = this.islandWidth;
                        this.startMeasuringWidth = false;
                        this.needUturn = true;

                    } else {
                        logger.info("Found ground");
                        this.decisionQueue.add(Action.FLY);
                        this.islandWidth++;
                        this.stepLogic = 1;

                    }
                    break;

                case 1:
                    this.decisionQueue.add(Action.ECHO_RIGHT);
                    this.stepLogic = 0;
                    break;

                default:
                    this.decisionQueue.add(Action.STOP);
                    break;
            }
        }
        if (this.needUturn == true) {
            logger.info("uturning");
            switch (this.stepLogic) {
                case 0:
                    this.decisionQueue.add(Action.TURN_RIGHT);
                    this.stepLogic++;
                    break;
                
                case 1:
                    this.startFindingGroundForLength = true;
                    this.needUturn = false;
                    this.stepLogic = 0;
                    logger.info("uturn success!");
                    break;
                default:
                    this.decisionQueue.add(Action.STOP);
                    break;
            }
        }
        if (this.startFindingGroundForLength == true) {
            logger.info("FindingGroundForLength");
            switch (this.stepLogic) {
                case 0:
                    this.decisionQueue.add(Action.ECHO_RIGHT);
                    this.stepLogic = 1;
                    break;
                case 1:
                    if (this.radar.getFound().equals("GROUND")) {
                        this.startFindingGroundForLength = false;
                        this.startMeasuringLength = true;
                        this.islandLength++;
                        logger.info("end start finding ground for length");
                    } else {
                        this.decisionQueue.add(Action.FLY);
                    }
                    this.stepLogic = 0;
                    break;
                default:
                    this.decisionQueue.add(Action.STOP);
                    break;
            }
        }
        if (this.startMeasuringLength == true) {
            logger.info("MeasuringLength");
            switch (this.stepLogic) {
                case 0:
                    if (this.radar.getFound().equals("OUT_OF_RANGE")) {
                        logger.info("GROUND NOT FOUND");
                        logger.info("the length of island is " + this.islandLength);
                        this.locationLength = this.islandLength;
                        this.stepLogic = 0;
                        this.startMeasuringLength = false;
                        this.needUturn1 = true;

                    } else {
                        logger.info("GROUND FOUND");
                        this.islandLength++;
                        this.stepLogic = 1;
                        this.decisionQueue.add(Action.FLY);
                        

                    }
                    break;

                case 1:
                    this.decisionQueue.add(Action.ECHO_RIGHT);
                    this.stepLogic = 0;
                    break;

                

                default:
                    this.decisionQueue.add(Action.STOP);
                    break;
            }
        }
        if(this.needUturn1==true){
            switch(this.stepLogic){
                case 0:
                    this.decisionQueue.add(Action.TURN_RIGHT);
                    this.stepLogic = 1;
                    break;

                case 1:
                    this.decisionQueue.add(Action.TURN_RIGHT);
                    this.stepLogic = 2;
                    break;

                case 2:
                    this.decisionQueue.add(Action.FLY);
                    this.stepLogic = 3;
                    break;
                case 3:
                    this.locationLength--;
                    this.locationWidth--;
                    this.stepLogic = 0;
                    this.needUturn1 = false;
                    this.startScanArea = true;
                    break;
            }
        }
        if (this.startScanArea == true) {
            int limit = this.islandWidth*2-2;
            
            logger.info("Scanning Area, step logic now is "+this.stepLogic);
            switch (this.stepLogic) {
                case 0:
                if(this.count > limit){
                    this.decisionQueue.add(Action.STOP);
                    break;
                }
                    logger.info("location length and width: "+this.locationLength + " " +this.locationWidth);
                    this.decisionQueue.add(Action.SCAN);
                    this.stepLogic = 1;
                    break;
                case 1:
                    if (this.radar.getCreeks()==null) {
                        if (this.locationLength == 0) {
                            logger.info("the location length now is "+this.locationLength);
                            logger.info("setting step logic to 2");
                            
                            this.stepLogic = 2;
                            logger.info("Scanning Area, step logic now is "+this.stepLogic);
                        } else {
                            this.decisionQueue.add(Action.FLY);
                            this.locationLength--;
                            this.stepLogic = 0;
                        }

                    } else {
                        this.stepLogic = 99;
                    }
                    break;
                case 2:
                if(this.locationWidth == -1 ){
                    logger.info(this.locationWidth);
                    this.stepLogic = 4;
                    break;
                }
                if(this.locationWidth == 0){
                    this.stepLogic = 10;
                    break;
                }
                    if (this.turningDir % 2 == 0) {
                        this.decisionQueue.add(Action.TURN_LEFT);
                    } else {
                        this.decisionQueue.add(Action.TURN_RIGHT);
                    }
                    this.locationLength = this.islandLength-1;
                    this.locationWidth-=2;
                    this.stepLogic = 3;
                    this.count +=2;
                    break;
                
                case 3:
                    logger.info("size of q "+this.decisionQueue.size()+" current step is "+this.currentStep);
                    if (this.turningDir % 2 == 0) {
                        this.decisionQueue.add(Action.TURN_LEFT);
                    } else {
                        this.decisionQueue.add(Action.TURN_RIGHT);
                    }
                    this.turningDir++;
                    this.stepLogic = 0;
                    break;
                case 4:
                    this.decisionQueue.add(Action.TURN_LEFT);
                    this.stepLogic = 5;
                    break;
                case 5:
                    this.decisionQueue.add(Action.TURN_RIGHT);
                    this.stepLogic = 6;
                    break;
                case 6:
                this.decisionQueue.add(Action.TURN_RIGHT);
                this.stepLogic = 7;
                break;
                case 7:
                this.decisionQueue.add(Action.FLY);
                this.stepLogic = 8;
                break;
                case 8:
                this.decisionQueue.add(Action.TURN_RIGHT);
                this.stepLogic = 77;
                break;
                case 9:
                this.decisionQueue.add(Action.FLY);
                this.locationWidth = this.islandWidth;
                this.locationLength = this.islandLength -1;
                this.stepLogic = 0;
                break;
                case 10:
                    this.decisionQueue.add(Action.TURN_RIGHT);
                    this.stepLogic = 11;
                    break;
                case 11:
                    this.decisionQueue.add(Action.TURN_LEFT);
                    this.stepLogic = 12;
                    break;
                case 12:
                this.decisionQueue.add(Action.TURN_LEFT);
                this.stepLogic = 13;
                break;
                case 13:
                this.decisionQueue.add(Action.FLY);
                this.stepLogic = 14;
                break;
                case 14:
                this.decisionQueue.add(Action.TURN_LEFT);
                this.stepLogic = 78;
                break;
                case 15:
                this.decisionQueue.add(Action.FLY);
                this.locationWidth = this.islandWidth;
                this.locationLength = this.islandLength -1;
                this.stepLogic = 0;
                break;
                case 77:
                this.decisionQueue.add(Action.FLY);
                this.stepLogic = 9;
                break;
                case 78:
                this.decisionQueue.add(Action.FLY);
                this.stepLogic = 15;
                break;
                case 99:
                    logger.info("Creeks Found!");
                    this.decisionQueue.add(Action.STOP);

            }
        }
        

    }

    // this is done when decision is about to be performed
    public JSONObject translateDecision(Action action) {
        JSONObject step = new JSONObject();

        step.put("action", action.toString());
        switch (action) {
            case TURN_LEFT -> {
                step.put("parameters",
                        new JSONObject().put("direction", Direction.getLeft(drone.getDirection()).toString()));
                // updating drone should be in acknowledgingResults
                this.drone.setDirection(Direction.getLeft(drone.getDirection()));
            }
            case TURN_RIGHT -> {
                step.put("parameters",
                        new JSONObject().put("direction", Direction.getRight(drone.getDirection()).toString()));
                this.drone.setDirection(Direction.getRight(drone.getDirection()));
            }
            case ECHO_LEFT -> {
                step.put("parameters",
                        new JSONObject().put("direction", Direction.getLeft(drone.getDirection()).toString()));
            }
            case ECHO_RIGHT -> {
                step.put("parameters",
                        new JSONObject().put("direction", Direction.getRight(drone.getDirection()).toString()));
            }
            case ECHO_FRONT -> {
                step.put("parameters", new JSONObject().put("direction", drone.getDirection().toString()));
            }

            default -> {

            }
        }
        // logger.info("** Decision: {}", step.toString());
        return step;
    }

}
