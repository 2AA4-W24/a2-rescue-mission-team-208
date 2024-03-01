package ca.mcmaster.se2aa4.island.team208;
import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class Radar{
    private int range;
    private String found;
    public void updateFromEchoResult(JSONObject echoResult) {
        // Interpret echo results and update the map accordingly
        try{
            this.range = echoResult.getJSONObject("extras").getInt("range");
            this.found = echoResult.getJSONObject("extras").getString("found");
        }catch (JSONException ignored){}//temporary fix
        // ... handle other cases
    }

    public int getRange() {
        return this.range;
    }

    public String getFound() {
        return this.found;
    }

}