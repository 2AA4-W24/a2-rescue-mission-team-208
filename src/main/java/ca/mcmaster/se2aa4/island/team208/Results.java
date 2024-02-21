package ca.mcmaster.se2aa4.island.team208;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.StringReader;

public class Results {

    private JSONObject response;

    public Results(String s) {
        response = new JSONObject(new JSONTokener(new StringReader(s)));
    }

    public String getResponse() {
        return response.toString(2);
    }

    public Integer getCost() {
        return response.getInt("cost");
    }

    public String getStatus() {
        return response.getString("status");
    }

    public JSONObject getExtraInfo() {
        return response.getJSONObject("extras");
    }

}
