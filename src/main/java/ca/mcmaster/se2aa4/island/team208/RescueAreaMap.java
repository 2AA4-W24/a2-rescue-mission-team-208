package ca.mcmaster.se2aa4.island.team208;
import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class RescueAreaMap{
    private int maxDistanceBeforeMIA;
    public void updateFromEchoResult(JSONObject echoResult) {
        // Interpret echo results and update the map accordingly
        try{
            int range = echoResult.getJSONObject("extras").getInt("range");
            String found = echoResult.getJSONObject("extras").getString("found");

            if ("OUT_OF_RANGE".equals(found)) {
                this.maxDistanceBeforeMIA = range;
            }
        }catch (JSONException ignored){}//temporary fix

        // ... handle other cases
    }

    public int getMaxDistanceBeforeMIA() {
        return maxDistanceBeforeMIA;
    }

}