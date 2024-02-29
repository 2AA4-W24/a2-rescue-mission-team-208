package ca.mcmaster.se2aa4.island.team208;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Decider {

    private final Logger logger = LogManager.getLogger();

    private final List<JSONObject> decisionQueue;
    private final List<JSONObject> results;

    private Drone drone;
    private RescueAreaMap map;
    private int currentStep = 0; //next step that needs to be executed

    public Decider(Drone drone, RescueAreaMap map) {
        this.results = new ArrayList<>();
        this.decisionQueue = new ArrayList<>();
        this.drone=drone;
        this.map=map;
    }

    public JSONObject getNextStep(){
        JSONObject step;
        if (currentStep >= decisionQueue.size()) {
            this.setNextDecision();
        }
        step=decisionQueue.get(currentStep);
        currentStep++;
        logger.info("** Decision: {}", step.toString());
        return step;
    }

    public void addResult(JSONObject result, int step){
        results.add(result);
    }

    public int getCurrentStep() {
        return currentStep;
    }

    //function needs to eventually reach "STOP" and stay there
    //function may generate more than one step
    private void setNextDecision(){
        JSONObject decision = new JSONObject();

        boolean stopped=false;
        try{
            if(!decisionQueue.get(currentStep -1).equals(new JSONObject())){
                stopped = decisionQueue.get(currentStep -1).get("action").equals("stop");
            }
        }catch(IndexOutOfBoundsException | JSONException ignored){}//temporary fix
        if (!stopped) {

            //decision generation logic
            double random = Math.random();

            if (random > .5) {
                this.decide(Action.FLY);
            } else if (random > .2) {
                this.decide(Action.SCAN);
            } else {
                this.decide(Action.ECHO_FRONT);
            }
        } else {
            this.decide(Action.STOP);
        }

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
    public void decide(Action action){
        JSONObject step = new JSONObject();

        step.put("action", action.toString());
        switch(action){
            case TURN_LEFT,ECHO_LEFT -> {
                step.put("parameters", new JSONObject("heading",Direction.getLeft(drone.getDirection()).toString()));
            }
            case TURN_RIGHT,ECHO_RIGHT -> {
                step.put("parameters", Direction.getRight(drone.getDirection()).toString());
            }
            case ECHO_FRONT -> {
                step.put("parameters", drone.getDirection().toString());
            }

            default->{

            }
        }
        //logger.info("** Decision: {}", step.toString());
        decisionQueue.add(step);
    }

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
