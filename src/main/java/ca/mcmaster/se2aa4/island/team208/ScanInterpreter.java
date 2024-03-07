package ca.mcmaster.se2aa4.island.team208;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScanInterpreter {
    private Result lastScan;
    private JSONArray creeks;
    private JSONArray biomes;
    private JSONArray sites;

    public ScanInterpreter(){
        this.lastScan=null;
        this.creeks=null;
        this.biomes=null;
        this.sites=null;
    }
    public void saveScan(Result scan){
        this.lastScan=scan;

        try{
            JSONObject extras=this.lastScan.getExtraInfo();

            this.creeks = extras.getJSONArray("creeks");
            this.biomes = extras.getJSONArray("biomes");
            this.sites = extras.getJSONArray("sites");

        }catch (JSONException e){
            throw new IllegalArgumentException("Argument must be an Scan Result as a JSONObject.");
        }
    }

    public JSONArray getCreeks() {
        if(this.lastScan!=null){
            return creeks;
        }
        else{
            throw new NullPointerException("No Scan has been saved yet.");
        }
    }

    public JSONArray getBiomes() {
        if(this.lastScan!=null){
            return biomes;
        }
        else{
            throw new NullPointerException("No Scan has been saved yet.");
        }
    }

    public JSONArray getSites() {
        if(this.lastScan!=null){
            return sites;
        }
        else{
            throw new NullPointerException("No Scan has been saved yet.");
        }
    }
}
