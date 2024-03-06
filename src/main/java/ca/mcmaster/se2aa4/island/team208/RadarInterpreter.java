package ca.mcmaster.se2aa4.island.team208;

import org.json.JSONException;
import org.json.JSONObject;


public class RadarInterpreter {
    private Result lastEcho;
    private int range;
    private String found;
    private Direction lastEchoDirection;

    public RadarInterpreter(){
        this.lastEcho = null;
        this.range=-1; //-1 symbolizes no echo being performed
        this.found="";
    }
    public void saveEchoResult(Result echoResult) {
        this.lastEcho = echoResult;

        // Interpret echo results and update the map accordingly
        try{
            JSONObject extras=echoResult.getExtraInfo();
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