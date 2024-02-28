package ca.mcmaster.se2aa4.island.team208;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.util.ArrayList;

public class Decider {

    private final Logger logger = LogManager.getLogger();
    private JSONObject decision = new JSONObject();
    private ArrayList<String> previousDecisions = new ArrayList<>();

    private int count = 0;

    public Decider() {
    }

    //function needs to eventually reach "stop" and stay there
    public void setNextDecision(){
        previousDecisions.add(decision.toString());

        JSONObject decision = new JSONObject();
        double random = Math.random();

        //decision generation logic
        if (this.decision.get("action")!="stop" && random > .5) {
            this.decide(Action.fly);
        }
        else if(random<=.5 &&random>.2){
            this.decide(Action.scan);
        }
        else {
            this.decide(Action.stop);
        }


        this.decision =decision;
    }
    /*
    public void decide(String curr_decision) {

        this.decision = curr_decision;
        decision.put("action", this.decision); // we stop the exploration immediately

        if(curr_decision == "fly"){
            count+=1;
        }
        decision.put("count",count);
        logger.info("action times: "+count);
        logger.info("** Decision: {}", decision.toString());

    }

     */
    public void decide(Action action, JSONObject additional){
        decision = new JSONObject();
        decision.put("action", action.toString());

        for(String keys: additional.keySet()){
            decision.put(keys,additional.get(keys));
        }

        logger.info("** Decision: {}", decision.toString());
    }

    public void decide(Action action){
        decision = new JSONObject();
        decision.put("action", action.toString());


        logger.info("** Decision: {}", decision.toString());
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

    public String getDecision() {
        return decision.toString();
    }
}
