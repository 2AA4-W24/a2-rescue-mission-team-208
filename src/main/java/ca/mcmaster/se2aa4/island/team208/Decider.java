package ca.mcmaster.se2aa4.island.team208;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Decider {

    private String decision;
    private final Logger logger = LogManager.getLogger();
    private JSONObject json_decision = new JSONObject();

    public Decider() {
        this.decision = "";
    }

    public void decide(String curr_decision) {

        this.decision = curr_decision;
        json_decision.put("action", this.decision); // we stop the exploration immediately
        logger.info("** Decision: {}", json_decision.toString());

    }

    public String getPrevDecision() {
        return this.decision;
    }

    public String getJsonDecision() {
        return json_decision.toString();
    }
}
