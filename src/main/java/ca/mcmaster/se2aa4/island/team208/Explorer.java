package ca.mcmaster.se2aa4.island.team208;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Decider decider;
    private Result lastResult;
    private Drone drone;

    public Explorer(){
        this.drone = new Drone();
        this.decider = new Decider(this.drone);
        this.lastResult = null;
    }

    @Override
    public void initialize(String s) {
        Configuration config = new Configuration(s);
        drone.initializeDrone(config.getInfo());
        config.printStatus();
    }

    @Override
    public String takeDecision(){
        String decision = decider.getNextStep(this.lastResult).toString();
        logger.info("Decision: " + decision);

        return decision;
    }

    @Override
    public void acknowledgeResults(String s) {
        this.lastResult = new Result(s);
        this.lastResult.printResults();
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

    //echoList method returns a string array including 3 directions which can be echoed due to the facing direction.
    /*
    public String[] echoList(Drone drone){
        String dir = drone.getDirection();
        String[] result = new String[3];
        switch(dir){
            case "E":
            result = new String[]{"N", "S", "E"};
            break;
            case "W":
            result = new String[]{"W","S","N"};
            break;
            case "N":
            result = new String[]{"E","W","N"};
            break;
            case "S":
            result = new String[]{"E","S","W"};
            break;
            

        }
        return result;
    }

     */

}
