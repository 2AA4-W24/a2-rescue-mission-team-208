package ca.mcmaster.se2aa4.island.team208;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.StringReader;

public class Results {

    private final Logger logger = LogManager.getLogger();
    private JSONObject response;
    private String response_str;
    private Integer cost;
    private String status;
    private String action;
    private JSONObject extra_info;

    public Results(String s) {
        response = new JSONObject(new JSONTokener(new StringReader(s)));
        response_str = response.toString(2);
        cost = response.getInt("cost");
        status = response.getString("status");
        extra_info = response.getJSONObject("extras");
    }
    public JSONObject getResponse(){
        return response;
    }

    public String getResponseString() {
        return response_str;
    }

    public Integer getCost() {
        return cost;
    }

    public String getStatus() {
        return status;
    }

    public JSONObject getExtraInfo() {
        return extra_info;
    }

    public void printResults() {
        logger.info("** Response received:\n"+ response_str);
        logger.info("The cost of the action was {}", cost);
        logger.info("The status of the drone is {}", status);
        logger.info("Additional information received: {}", extra_info);
    }

    
}
