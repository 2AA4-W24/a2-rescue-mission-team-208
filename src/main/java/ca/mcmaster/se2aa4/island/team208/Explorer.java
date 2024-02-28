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
    private Drone drone = new Drone();
    private RescueAreaMap areaMap = new RescueAreaMap();

    @Override
    public void initialize(String s) {
        Configuration config = new Configuration(s);
        drone.initializeDrone(config.getInfo());
        config.printStatus();
    }

    @Override
    public String takeDecision(){
//        if (decider.getDecision() == "FLY") {
//            decider.decide(Action.STOP);
//        } else {
//            decider.decide(Action.FLY);
//        }
        /*
        int maxDistance = areaMap.getMaxDistanceBeforeMIA();
        String[] echoList = echoList(drone);

        switch(this.count){
            case 0 :
            decider.sendEcho(echoList[0]);
            break;
            case 1 :
            logger.info("For direction: "+echoList[this.count-1]+"The drone can FLY " + maxDistance + " units forward before going MIA for falling out of the radio range.");
            decider.sendEcho(echoList[1]);
            break;
            case 2 :
            logger.info("For direction: "+echoList[this.count-1]+"The drone can FLY " + maxDistance + " units forward before going MIA for falling out of the radio range.");
            decider.sendEcho(echoList[2]);
            break;
            case 3 :
            logger.info("For direction: "+echoList[this.count-1]+"The drone can FLY " + maxDistance + " units forward before going MIA for falling out of the radio range.");
            decider.decide("STOP");
            break;

        }
        count++;


         */
        /*
        //Fly till the end
        if(count==-1){
            decider.sendEcho(drone.getDirection());
            count++;
        }
        else if(count%3==0){
            if(flyCount<areaMap.getMaxDistanceBeforeMIA()){
                decider.decide(Action.FLY,10,new JSONObject().put("direction",drone.getDirection()));
                flyCount+=10;
            }
            else{
                decider.decide(Action.STOP,0,new JSONObject().put("direction",drone.getDirection()));
            }
            count++;
        }
        else if(count%3==1){
            if(flyCount<areaMap.getMaxDistanceBeforeMIA()){
                decider.decide(Action.SCAN,0,null);

            }
            else{
                decider.decide(Action.STOP,0,new JSONObject().put("direction",drone.getDirection()));
            }
            count++;
        }
        else{
            if(flyCount>=areaMap.getMaxDistanceBeforeMIA()){
                decider.decide(Action.STOP,0,new JSONObject().put("direction",drone.getDirection()));
            }
            count++;

        }

         */

        decider.setNextDecision();
        logger.info("JSON Decision: "+decider.getDecision());

        return decider.getDecision();
    }

    @Override
    public void acknowledgeResults(String s) {
        results = new Results(s);
        JSONObject Extras = results.getExtraInfo();
        if (Extras != null || Extras.getString("found").equals("OUT_OF_RANGE")) {
            // Assuming your echo result is in the same format as the action
            areaMap.updateFromEchoResult(results.getResponse());
        }
        results.printResults();
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
