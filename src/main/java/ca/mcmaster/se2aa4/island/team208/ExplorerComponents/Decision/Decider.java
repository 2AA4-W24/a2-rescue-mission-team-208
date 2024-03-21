package ca.mcmaster.se2aa4.island.team208.ExplorerComponents.Decision;

import ca.mcmaster.se2aa4.island.team208.ActionSequenceFactory.ActionSequenceFactory;
import ca.mcmaster.se2aa4.island.team208.ActionSequenceFactory.FindIslandSequenceFactory;
import ca.mcmaster.se2aa4.island.team208.ActionSequenceFactory.ScanIslandSequenceFactory;
import ca.mcmaster.se2aa4.island.team208.Enums.Action;
import ca.mcmaster.se2aa4.island.team208.Enums.Direction;
import ca.mcmaster.se2aa4.island.team208.Interpreters.RadarInterpreter;
import ca.mcmaster.se2aa4.island.team208.Interpreters.ScanInterpreter;
import ca.mcmaster.se2aa4.island.team208.MapTools.Drone;
import ca.mcmaster.se2aa4.island.team208.MapTools.IslandMap;
import ca.mcmaster.se2aa4.island.team208.MapTools.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.platform.commons.PreconditionViolationException;

import java.util.*;

/*
    Goes around map mapping out a minimal rectangle containing entire island.
    Starting at top left, goes in interlaced scan pattern scanning island W -> E
    Runs out of battery with only ~25% of island scanned for map10


 */

public class Decider implements DecisionGenerator {

    private final Logger logger = LogManager.getLogger();

    private final Queue<ActionSequenceFactory> stageQueue;
    private final List<Action> decisionQueue;
    private final List<Result> results;

    private final Drone drone;
    private final RadarInterpreter radarInterpreter;
    private final ScanInterpreter scanInterpreter;
    private final IslandMap map;

    private ActionSequenceFactory currentStage;
    private Action lastAction;
    private Integer currentStep; //next step that needs to be executed
    private boolean ready;


    public Decider() {

        this.decisionQueue = new ArrayList<>();
        this.stageQueue = new ArrayDeque<>();
        this.results = new ArrayList<>();

        this.map = new IslandMap(new Position(0,0));
        this.drone=new Drone();

        this.radarInterpreter = new RadarInterpreter();
        this.scanInterpreter = new ScanInterpreter();

        this.currentStep=0;

        this.decisionQueue.add(Action.ECHO_FRONT);
        this.lastAction = this.decisionQueue.get(decisionQueue.size()-1);
        this.selectSearchStages();
        this.ready=true;
    }

    public void initialize(String s, int x, int y) {
        Configuration config = new Configuration(s);
        this.drone.initializeDrone(config.getInfo(), x, y);
        config.printStatus();
    }

    private void selectSearchStages() {
        this.stageQueue.add(new FindIslandSequenceFactory(this.radarInterpreter, this.results));
        this.stageQueue.add(new ScanIslandSequenceFactory(
                this.drone, this.radarInterpreter, this.scanInterpreter, this.map));

        this.currentStage = this.stageQueue.remove();
    }


    public String getNextStep(){
        if (this.currentStep >= decisionQueue.size()) {
            if(this.ready){
                if(this.drone.getBattery()<=this.drone.getStopCost()){
                    return this.computeDecision(Action.STOP).toString();
                }
                else{
                    this.generateNextActions();
                }
            }
            else{
                throw new PreconditionViolationException("Response must be processed before another action can be taken.");

            }
        }
        this.lastAction = this.decisionQueue.get(this.currentStep);
        JSONObject decision=this.computeDecision(this.lastAction);
        this.currentStep++;

        logger.info("** Decision: {}", decision.toString());
        logger.info("Current budget: "+this.drone.getBattery());

        this.ready = false;
        return decision.toString();
    }

    public void processResults(String s) {
        this.results.add(new Result(s, this.lastAction));
        this.drone.processResults(this.results.get(this.currentStep-1));
        switch(this.lastAction){
            case ECHO_FRONT,ECHO_LEFT,ECHO_RIGHT-> this.radarInterpreter.saveEchoResult(this.results.get(currentStep-1));
            case SCAN -> this.scanInterpreter.saveScan(results.get(currentStep-1));
        }
        this.ready=true;
    }

    //function needs to eventually reach "STOP"
    //function may generate more than one step with each call
    private void generateNextActions(){

        //dequeue next stage
        if(currentStage.hasCompleted()){
            this.currentStage=this.stageQueue.poll();
        }

        //exploring has been completed
        if(this.currentStage==null || this.drone.getBattery() < this.drone.getStopCost()){
            this.decisionQueue.add(Action.STOP);
            logger.info("CREEKS: " + this.map.getCreeks());
            logger.info("CLOSEST CREEK: " + this.map.getClosestCreek());
            logger.info("Emergency Site: " + this.map.getSite());
        }

        //current stage has not been completed
        else{
            this.currentStage.generateNextActions(this.decisionQueue);
        }
    }

    //this is done when decision is about to be performed
    private JSONObject computeDecision(Action action){
        JSONObject step = new JSONObject();

        step.put("action", action.toString());
        switch(action){
            case TURN_LEFT, ECHO_LEFT -> {
                step.put("parameters", new JSONObject().put("direction", Direction.getLeft(drone.getDirection()).toString()));
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
    public Action getLastAction() {
        return this.lastAction;
    }
    public String getReport() {
        return (new Report(this.map.getClosestCreek())).toString();
    }
}
