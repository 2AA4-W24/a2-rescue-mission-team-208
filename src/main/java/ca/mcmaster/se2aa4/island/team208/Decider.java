package ca.mcmaster.se2aa4.island.team208;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Decider {

    private String decision;
    private final Logger logger = LogManager.getLogger();
    private JSONObject json_decision = new JSONObject();
    private int count = 0;

    public Decider() {
        this.decision = "";
    }
    public void decide(String curr_decision) {

        this.decision = curr_decision;
        json_decision.put("action", this.decision); // we stop the exploration immediately

        if(curr_decision == "fly"){
            count+=1;
        }
        json_decision.put("count",count);
        logger.info("action times: "+count);
        logger.info("** Decision: {}", json_decision.toString());

    }
    public void decide(String decision, int count, JSONObject parameters){
        json_decision.put("action",decision);
        if (count>0)json_decision.put("count",count);
        if(parameters!=null)json_decision.put("parameters",parameters);
        logger.info("** Decision: {}", json_decision.toString());

    }


    public void sendEcho(String direction){
        this.decision = "echo";
        JSONObject parameter = new JSONObject();
        parameter.put("direction", direction);
        json_decision.put("action", this.decision);
        json_decision.put("parameters", parameter);
        logger.info("Decision: {}",json_decision.toString());

    }

    public String getJsonDecision() {
        return json_decision.toString();
    }
}
