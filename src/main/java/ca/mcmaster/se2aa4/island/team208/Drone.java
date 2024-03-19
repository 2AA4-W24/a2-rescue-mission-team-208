package ca.mcmaster.se2aa4.island.team208;

import org.json.JSONObject;
import org.junit.platform.commons.PreconditionViolationException;

public class Drone {
    private boolean initialized;
    private Direction direction;
    private int battery;
    private Position position;

    private final int STOP_COST = 20; //value arrived at after multiple tests
    public Drone(){
        this.initialized=false;
    }

    public void initializeDrone(JSONObject info, int x, int y){
        this.direction = Direction.parseDirection(info.getString("heading"));
        this.battery = info.getInt("budget");
        this.position = new Position(x, y);
        this.initialized=true;
    }

    public int getX() {
        if(!this.initialized){
            throw new NullPointerException("Drone must be initialized first.");
        }
        return this.position.getX();
    }
    public int getY() {
        if(!this.initialized){
            throw new NullPointerException("Drone must be initialized first.");
        }
        return this.position.getY();
    }
    public Direction getDirection(){
        if(!this.initialized){
            throw new NullPointerException("Drone must be initialized first.");
        }
        return this.direction;
    }
    public int getBattery(){
        if(!this.initialized){
            throw new NullPointerException("Drone must be initialized first.");
        }
        return this.battery;
    }

    public void processResults(Result lastResult) {
        if(!this.initialized){
            throw new NullPointerException("Drone must be initialized first.");
        }
        this.battery -= lastResult.getCost();
        int[]vector = Direction.getDirectionVector(this.direction);
        Action action = lastResult.getAction();

        switch(action){
            case TURN_LEFT -> {
                this.direction = Direction.getLeft(this.direction);
                int[]new_vector = Direction.getDirectionVector(this.direction);

                this.position.changeByOffset(vector[0],vector[1]);
                this.position.changeByOffset(new_vector[0],new_vector[1]);
            }
            case TURN_RIGHT -> {
                this.direction = Direction.getRight(this.direction);
                int[]new_vector = Direction.getDirectionVector(this.direction);

                this.position.changeByOffset(vector[0],vector[1]);
                this.position.changeByOffset(new_vector[0],new_vector[1]);
            }
            case FLY -> {
                this.position.changeByOffset(vector[0],vector[1]);
            }
        }
    }

    public int getStopCost() {
        if(!this.initialized){
            throw new NullPointerException("Drone must be initialized first.");
        }
        return this.STOP_COST;
    }
}
