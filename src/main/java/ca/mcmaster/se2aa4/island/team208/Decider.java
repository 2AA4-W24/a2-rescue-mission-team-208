package ca.mcmaster.se2aa4.island.team208;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/*
    Goes around map mapping out a minimal rectangle containing entire island.
    Starting at top left, goes in interlaced scan pattern scanning island W -> E
    Runs out of battery with only ~25% of island scanned for map10


 */

public class Decider {

    private final Logger logger = LogManager.getLogger();
    private final List<Action> decisionQueue;
    private final List<ActionSequence> stagesInOrder;
    private ActionSequence currentStage;

    private final List<Result> results;
    private Drone drone;
    private RadarInterpreter radarInterpreter;
    private ScanInterpreter scanInterpreter;
    private IslandMap map;
    private Integer currentStep; //next step that needs to be executed
    private int searchStage;


    public Decider(Drone drone) {

        this.decisionQueue = new ArrayList<>();
        this.stagesInOrder = new ArrayList<>();
        this.results = new ArrayList<>();

        this.map = new IslandMap(new Position(0,0));
        this.drone=drone;

        this.radarInterpreter = new RadarInterpreter();
        this.scanInterpreter = new ScanInterpreter();

        this.currentStep=0;
        this.searchStage=0;

        this.decisionQueue.add(Action.ECHO_FRONT);
        this.selectSearchStages();

    }

    private void selectSearchStages() {
        this.stagesInOrder.add(new ScanIslandSequence(
                this.results, this.drone, this.radarInterpreter, this.scanInterpreter));
        this.currentStage = this.stagesInOrder.get(0);
    }


    public JSONObject getNextStep(Result lastResult){

        JSONObject step;
        if(this.currentStep>0)this.processResults(lastResult);


        if (this.currentStep >= decisionQueue.size()) {
            this.setNextDecision();
        }

        step=this.generateDecision(decisionQueue.get(this.currentStep));
        this.currentStep++;
        logger.info("** Decision: {}", step.toString());
        logger.info("Current budget: "+this.drone.getBattery());
        if(map.getTopLeft()!=null){
            logger.info("Top Left: " + map.getTopLeft().toString()+", " +
                    "Bottom Right: " + map.getBottomRight().toString());
        }
        return step;
    }

    private void processResults(Result lastResult) {
        this.results.add(lastResult);
        this.drone.processResults(decisionQueue.get(currentStep-1),lastResult);

        switch(this.decisionQueue.get(currentStep-1)){
            case ECHO_FRONT,ECHO_LEFT,ECHO_RIGHT-> this.radarInterpreter.saveEchoResult(this.results.get(currentStep-1));
            case SCAN -> this.scanInterpreter.saveScan(results.get(currentStep-1));
        }
    }

    //function needs to eventually reach "STOP"
    //function may generate more than one step with each call
    private void setNextDecision(){



        /*
        if(!this.currentStage.hasCompleted()){
            this.currentStage.setNextDecision(this.decisionQueue);

        }
        else if(searchStage==-1){
            logger.info("Remaining budget: " + this.drone.getBattery());
            searchStage++;
        }


         */

        if(this.drone.getBattery()<=this.drone.getStopCost()+10){
            this.decisionQueue.add(Action.STOP);
        }

        switch(searchStage){
            case 0 ->{ // Find the Left side of the island

                this.decisionQueue.add(Action.SCAN);
                if (this.currentStep - 1 == 0 || this.decisionQueue.get(this.currentStep - 1) == Action.FLY) {
                    this.decisionQueue.add(Action.ECHO_RIGHT);
                } else if (this.decisionQueue.get(this.currentStep - 1) == Action.ECHO_RIGHT) {
                    if (this.radarInterpreter.getFound().equals("GROUND")) {
                        this.decisionQueue.add(Action.TURN_RIGHT);
                    }
                } else if (this.decisionQueue.get(this.currentStep - 1) == Action.TURN_RIGHT) {
                    this.decisionQueue.add(Action.ECHO_FRONT);
                } else if (this.decisionQueue.get(this.currentStep - 1) == Action.ECHO_FRONT) {
                    for (int i = 0; i < this.radarInterpreter.getRange(); i++) {
                        this.decisionQueue.add(Action.FLY);
                        this.decisionQueue.add(Action.SCAN);
                    }
                    searchStage++;
                } else {
                    this.decisionQueue.add(Action.FLY);
                }

            }
            case 1 ->{
                if(!currentStage.hasCompleted()){
                    this.currentStage.setNextDecision(this.decisionQueue);
                }
                else{
                    this.decisionQueue.add(Action.STOP);
                }
                /*
                if (this.decisionQueue.get(this.currentStep - 1) == Action.SCAN) {
                    JSONArray creeks = this.scanInterpreter.getCreeks();
                    JSONArray sites = this.scanInterpreter.getSites();
                    this.decisionQueue.add(Action.ECHO_FRONT);
                    if(!creeks.isEmpty()){
                        this.map.addCreek(new Creek(creeks.get(0).toString()));
                        logger.info("A creek has been found.");
                    }
                    if(!sites.isEmpty()){
                        this.map.setSite(new Site(sites.get(0).toString()));
                        logger.info("The site has been found.");
                    }
                } else if (this.decisionQueue.get(this.currentStep - 1) == Action.ECHO_FRONT) {
                    if ((this.decisionQueue.get(this.currentStep - 3) == Action.TURN_LEFT
                            || this.decisionQueue.get(this.currentStep - 3) == Action.TURN_RIGHT)
                            && this.radarInterpreter.getFound().equals("OUT_OF_RANGE")) {
                        this.decisionQueue.add(Action.STOP);
                        logger.info("CREEKS: " + this.map.getCreeks());
                        logger.info("SITE: " + this.map.getSite());
                    } else if (this.radarInterpreter.getFound().equals("GROUND")) {
                        this.decisionQueue.add(Action.FLY);
                        this.decisionQueue.add(Action.SCAN);
                    } else {
                        switch(this.drone.getDirection()) {
                            case S -> this.decisionQueue.add(Action.ECHO_LEFT);
                            case N -> this.decisionQueue.add(Action.ECHO_RIGHT);
                        }
                    }
                } else {
                    switch(this.drone.getDirection()){
                        case S -> {
                            if (this.radarInterpreter.getFound().equals("GROUND") && this.radarInterpreter.getRange() <= 1) {
                                this.decisionQueue.add(Action.FLY);
                                this.decisionQueue.add(Action.SCAN);
                                this.decisionQueue.add(Action.ECHO_LEFT);
                            } else {
                                this.decisionQueue.add(Action.TURN_LEFT);
                                this.decisionQueue.add(Action.TURN_LEFT);
                                this.decisionQueue.add(Action.SCAN);
                            }
                        }
                        case N -> {
                            if (this.radarInterpreter.getFound().equals("GROUND") && this.radarInterpreter.getRange() <= 1) {
                                this.decisionQueue.add(Action.FLY);
                                this.decisionQueue.add(Action.SCAN);
                                this.decisionQueue.add(Action.ECHO_RIGHT);
                            } else {
                                this.decisionQueue.add(Action.TURN_RIGHT);
                                this.decisionQueue.add(Action.TURN_RIGHT);
                                this.decisionQueue.add(Action.SCAN);
                            }
                        }
                    }
                }
                */
            }


        }





    }

    //this is done when decision is about to be performed
    private JSONObject generateDecision(Action action){
        JSONObject step = new JSONObject();

        step.put("action", action.toString());
        switch(action){
            case TURN_LEFT, ECHO_LEFT -> {
                step.put("parameters", new JSONObject().put("direction",Direction.getLeft(drone.getDirection()).toString()));

            }
            case TURN_RIGHT, ECHO_RIGHT -> {
                step.put("parameters", new JSONObject().put("direction",Direction.getRight(drone.getDirection()).toString()));
            }
            case ECHO_FRONT -> {
                step.put("parameters", new JSONObject().put("direction",drone.getDirection().toString()));
            }

            default->{

            }
        }
        return step;
    }

    public int getStepCount() {
        return this.currentStep;
    }
}
