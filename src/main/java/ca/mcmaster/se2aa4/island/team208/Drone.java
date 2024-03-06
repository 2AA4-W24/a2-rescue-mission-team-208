package ca.mcmaster.se2aa4.island.team208;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Drone {
    private Direction direction;
    private int battery;

    public void initializeDrone(JSONObject info){
        this.direction = Direction.parseDirection(info.getString("heading"));
        this.battery = info.getInt("budget");

    }

    public Direction getDirection(){
        return this.direction;
    }

    public int getBattery(){
        return this.battery;
    }

    public void processResults(Action action, Result lastResult) {
        this.battery -= lastResult.getCost();
        switch(action){
            case TURN_LEFT -> this.direction = Direction.getLeft(this.direction);
            case TURN_RIGHT -> this.direction = Direction.getRight(this.direction);
        }
    }



}
