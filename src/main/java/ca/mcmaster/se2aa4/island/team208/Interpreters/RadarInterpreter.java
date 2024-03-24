package ca.mcmaster.se2aa4.island.team208.Interpreters;

import ca.mcmaster.se2aa4.island.team208.ExplorerComponents.Result;
import org.json.JSONException;
import org.json.JSONObject;

public class RadarInterpreter {

    private int range;
    private String found;

    public RadarInterpreter(){
        this.range=-1; //-1 symbolizes no echo being performed
        this.found="";
    }
    public void saveEchoResult(Result echoResult) {

        // Interpret echo results and update the map accordingly
        try{
            JSONObject extras=echoResult.getExtras();
            this.range = extras.getInt("range");
            this.found = extras.getString("found");
        }catch (JSONException e){
            throw new IllegalArgumentException("Argument must be an Echo Result as a JSONObject.");
        }
    }

    public int getRange() {
        if(range==-1){
            throw new NullPointerException("No Echo has been saved yet.");
        }
        else{
            return this.range;
        }
    }


    public String getFound() {
        if(this.found.isEmpty()){
            throw new NullPointerException("No Echo has been saved yet.");
        }
        else{
            return this.found;
        }
    }

}