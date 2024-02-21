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

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));
        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);
    }

    @Override
    public String takeDecision() {
        if (decider.getPrevDecision() == "fly") {
            decider.decide("stop");
        } else {
            decider.decide("fly");
        }
        return decider.getJsonDecision();
    }

    @Override
    public void acknowledgeResults(String s) {
        results = new Results(s);
        logger.info("** Response received:\n"+ results.getResponse());
        logger.info("The cost of the action was {}", results.getCost());
        logger.info("The status of the drone is {}", results.getStatus());
        logger.info("Additional information received: {}", results.getExtraInfo());
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

}
