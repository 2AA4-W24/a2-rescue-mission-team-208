package ca.mcmaster.se2aa4.island.team208;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Decider decider = new Decider();
    private Results results;
    private int count = 0;
    private Drone drone = new Drone();
    private RescueAreaMap areaMap = new RescueAreaMap();

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));
        drone.initializeDrone(info);

        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);
    }

    @Override
    public String takeDecision(){
        /*if (decider.getPrevDecision() == "fly") {
            decider.decide("stop");
        } else {
            decider.decide("fly");
        }*/
        int maxDistance = areaMap.getMaxDistanceBeforeMIA();
        String[] echoList = echoList(drone);

        switch(this.count){
            case 0 :
            decider.sendEcho(echoList[0]);
            break;
            case 1 :
            logger.info("For direction: "+echoList[this.count-1]+"The drone can fly " + maxDistance + " units forward before going MIA for falling out of the radio range.");
            decider.sendEcho(echoList[1]);
            break;
            case 2 :
            logger.info("For direction: "+echoList[this.count-1]+"The drone can fly " + maxDistance + " units forward before going MIA for falling out of the radio range.");
            decider.sendEcho(echoList[2]);
            break;
            case 3 :
            logger.info("For direction: "+echoList[this.count-1]+"The drone can fly " + maxDistance + " units forward before going MIA for falling out of the radio range.");
            decider.decide("stop");
            break;

        }
        count++;
        return decider.getJsonDecision();
    }

    @Override
    public void acknowledgeResults(String s) {
        results = new Results(s);
        JSONObject Extras = results.getExtraInfo();
        if (Extras != null || Extras.getString("found").equals("OUT_OF_RANGE")) {
            // Assuming your echo result is in the same format as the action
            areaMap.updateFromEchoResult(results.getResponse());
        }
    
        logger.info("** Response received:\n"+ results.getResponseString());
        logger.info("The cost of the action was {}", results.getCost());
        logger.info("The status of the drone is {}", results.getStatus());
        logger.info("Additional information received: {}", results.getExtraInfo());
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

    //echoList method returns a string array including 3 directions which can be echoed due to the facing direction.
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

}
