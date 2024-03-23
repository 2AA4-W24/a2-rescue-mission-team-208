package ca.mcmaster.se2aa4.island.team208.ExplorerComponents;

import ca.mcmaster.se2aa4.island.team208.Enums.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.StringReader;

public class Result {
    private final Logger logger = LogManager.getLogger();

    private final Action action;
    private final JSONObject response;
    private final Integer cost;
    private final String status;
    private final JSONObject extras;


    public Result(String response, Action lastAction) throws IllegalArgumentException{
        this.action=lastAction;
        try{
            this.response = new JSONObject(new JSONTokener(new StringReader(response)));
        }
        catch(JSONException e){
            throw new IllegalArgumentException("Input must be a String in JSON format.");
        }
        this.cost = this.response.getInt("cost");
        this.status = this.response.getString("status");
        this.extras = this.response.getJSONObject("extras");
    }

    public Action getAction() {
        return action;
    }

    public JSONObject getResponse(){
        return response;
    }

    public String toString() {
        return response.toString();
    }

    public Integer getCost() {
        return cost;
    }

    public String getStatus() {
        return status;
    }

    public JSONObject getExtras() {
        return extras;
    }

    public void printResults() {
        logger.info("** Response received:\n"+ this.toString());
        logger.info("The cost of the action was {}", cost);
        logger.info("The status of the drone is {}", status);
        logger.info("Additional information received: {}", extras);
    }

}
