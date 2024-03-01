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
    private int currentStep; //next step that needs to be executed

    public Decider(Drone drone, Radar radar) {
        this.results = new ArrayList<>();
        this.decisionQueue = new ArrayList<>();
        this.drone=drone;
        this.radar=radar;
        this.currentStep=0;
        this.decisionQueue.add(Action.ECHO_FRONT);
    }

    public JSONObject getNextStep(){
        JSONObject step;
        if (this.currentStep >= decisionQueue.size()) {
            this.setNextDecision();
        }
        step=this.generateDecision(decisionQueue.get(this.currentStep));
        this.currentStep++;
        logger.info("** Decision: {}", step.toString());
        return step;
    }

    public void addResult(Results result){
        this.results.add(result);
    }

    //function needs to eventually reach "STOP" and stay there
    //function may generate more than one step with each call
    private void setNextDecision(){
        this.decisionQueue.add(Action.SCAN);
        if (this.currentStep - 1 == 0 || this.decisionQueue.get(this.currentStep - 1) == Action.FLY) {
            this.decisionQueue.add(Action.ECHO_RIGHT);
        } else if (this.decisionQueue.get(this.currentStep - 1) == Action.ECHO_RIGHT) {
            if (this.radar.getFound().equals("GROUND")) {
                this.decisionQueue.add(Action.TURN_RIGHT);
            }
            this.decisionQueue.add(Action.ECHO_FRONT);
        } else if (this.decisionQueue.get(this.currentStep - 1) == Action.ECHO_FRONT){
            if (this.radar.getRange() == 0) {
                this.decisionQueue.add(Action.STOP);
            } else {
                this.decisionQueue.add(Action.FLY);
            }
        }
//        int flyAmount = (currentStep > 0) ? this.radar.getMaxDistanceBeforeMIA() : -1;
//
//        for(int i=0; i<flyAmount;i++){
//            this.decisionQueue.add(Action.FLY);
//            this.decisionQueue.add(Action.SCAN);
//        }
//        if(flyAmount==0){
//            this.decisionQueue.add(Action.SCAN);
//            this.decisionQueue.add(Action.STOP);
//        } else {
//            this.decisionQueue.add(Action.ECHO_FRONT);
//        }

        /*
        try{
            if(!decisionQueue.get(currentStep -1).equals(new JSONObject())){
                stopped = this.translateDecision(decisionQueue.get(currentStep -1)).get("action").equals("stop");
            }
        }catch(IndexOutOfBoundsException | JSONException ignored){}//temporary fix
        if (!stopped) {

            //decision generation logic
            double random = Math.random();

            if (random > .5) {
                this.decisionQueue.add(Action.FLY);
            } else if (random > .2) {
                this.decisionQueue.add(Action.SCAN);
            } else {
                this.decisionQueue.add(Action.ECHO_FRONT);
            }
        } else {
            this.decisionQueue.add(Action.STOP);
        }


         */
    }


    //this is done when decision is about to be performed
    public JSONObject generateDecision(Action action){
        JSONObject step = new JSONObject();

        step.put("action", action.toString());
        switch(action){
            case TURN_LEFT -> {
                step.put("parameters", new JSONObject().put("direction",Direction.getLeft(drone.getDirection()).toString()));
                //updating drone should be in acknowledgingResults
                this.drone.setDirection(Direction.getLeft(drone.getDirection()));
            }
            case TURN_RIGHT -> {
                step.put("parameters", new JSONObject().put("direction",Direction.getRight(drone.getDirection()).toString()));
                this.drone.setDirection(Direction.getRight(drone.getDirection()));
            }
            case ECHO_LEFT -> {
                step.put("parameters", new JSONObject().put("direction",Direction.getLeft(drone.getDirection()).toString()));
            }
            case ECHO_RIGHT -> {
                step.put("parameters", new JSONObject().put("direction",Direction.getRight(drone.getDirection()).toString()));
            }
            case ECHO_FRONT -> {
                step.put("parameters", new JSONObject().put("direction",drone.getDirection().toString()));
            }

            default->{

            }
        }
        //logger.info("** Decision: {}", step.toString());
        return step;
    }


/*
    public void decide(String curr_decision) {

        this.decision = curr_decision;
        decision.put("action", this.decision); // we STOP the exploration immediately

        if(curr_decision == "FLY"){
            currentStep+=1;
        }
        decision.put("currentStep",currentStep);
        logger.info("action times: "+currentStep);
        logger.info("** Decision: {}", decision.toString());

    }

     */

    /*
    public void decide(Action action, JSONObject additional){
        JSONObject step = new JSONObject();
        step.put("action", action.toString());

        for(String keys: additional.keySet()){
            step.put(keys,additional.get(keys));
        }

        //logger.info("** Decision: {}", step.toString());
        decisionQueue.add(step);
    }

     */
    /*
    public void sendEcho(String direction){
        this.decision = "echo";
        JSONObject parameter = new JSONObject();
        parameter.put("direction", direction);
        decision.put("action", this.decision);
        decision.put("parameters", parameter);
        logger.info("Decision: {}", decision.toString());

    }

     */
}
