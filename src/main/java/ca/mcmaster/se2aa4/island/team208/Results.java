package ca.mcmaster.se2aa4.island.team208;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.StringReader;

public class Results {

    private JSONObject response;

    public Results(String s) {
        response = new JSONObject(new JSONTokener(new StringReader(s)));
    }
    public JSONObject getResponse(){
        return response;
    }

    public String getResponseString() {
        return response.toString(2);
    }

    public Integer getCost() {
        return response.getInt("cost");
    }

    public String getStatus() {
        return response.getString("status");
    }

    public String getAction(){
        return response.getString("action");
    }

    public JSONObject getExtraInfo() {
        return response.getJSONObject("extras");
    }
    
}
