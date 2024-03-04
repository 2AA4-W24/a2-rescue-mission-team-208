package ca.mcmaster.se2aa4.island.team208;

import org.json.JSONException;
import org.json.JSONObject;


public class RadarInterpreter {
    private JSONObject lastEcho;
    private int cost;
    private int range;
    private String found;
    private Direction lastEchoDirection;

    public RadarInterpreter(){
        this.lastEcho = new JSONObject();
        this.cost=0;
        this.range=-1; //-1 symbolizes no echo being performed
        this.found="";
    }
    public void saveEchoResult(JSONObject echoResult) {
        this.lastEcho = echoResult;

        // Interpret echo results and update the map accordingly
        try{
            JSONObject extras=echoResult.getJSONObject("extras");
            this.cost = echoResult.getInt("cost");
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

    public int getCost() {
        if(this.cost==0){
            throw new NullPointerException("No Echo has been saved yet.");
        }
        else{
            return this.cost;
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