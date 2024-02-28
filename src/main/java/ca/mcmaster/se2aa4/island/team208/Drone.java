package ca.mcmaster.se2aa4.island.team208;
import java.io.StringReader;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;


public class Drone {
    private String direction;
    private int battery;

    public void initializeDrone(JSONObject info){
        this.direction = info.getString("heading");
        this.battery = info.getInt("budget");

    }

    public String getDirection(){
        return this.direction;

    }
    /*
    public String echo(String direction) throws Exception {
        Direction d = Direction.valueOf(direction.toUpperCase());

        //echo in direction behind the drone
        if(d.equals(Directions.getOpposite(Direction.valueOf(this.direction.toUpperCase())))){
            throw new Exception("Drone MIA");
        }


        return "";
    }

     */

    public int getBattery(){
        return this.battery;
    }

    public void setDirection(String newDirection){
        this.direction = newDirection;

    }

    public void setBattery(int newBattery){
        this.battery = newBattery;
    }
    





}
