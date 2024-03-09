package ca.mcmaster.se2aa4.island.team208;

import org.json.JSONObject;

public class Drone {
    private Direction direction;
    private int battery;
    private Position position;

    public void initializeDrone(JSONObject info, double x, double y){
        this.direction = Direction.parseDirection(info.getString("heading"));
        this.battery = info.getInt("budget");
        this.position = new Position(x, y);
    }

    public double getX() {
        return this.position.getX();
    }

    public double getY() {
        return this.position.getY();
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
