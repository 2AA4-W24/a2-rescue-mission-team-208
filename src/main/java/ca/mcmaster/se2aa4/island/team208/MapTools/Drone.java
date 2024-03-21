package ca.mcmaster.se2aa4.island.team208.MapTools;

import ca.mcmaster.se2aa4.island.team208.Enums.Action;
import ca.mcmaster.se2aa4.island.team208.Enums.Direction;
import ca.mcmaster.se2aa4.island.team208.ExplorerComponents.Decision.Result;
import org.json.JSONObject;

public class Drone {
    private boolean initialized;
    private Direction direction;
    private int battery;
    private Position position;

    private final String uninitializedMessage;
    public Drone(){
        this.initialized=false;
        this.uninitializedMessage ="Drone must be initialized first.";
    }

    public void initializeDrone(JSONObject info, int x, int y){
        this.direction = Direction.parseDirection(info.getString("heading"));
        this.battery = info.getInt("budget");
        this.position = new Position(x, y);
        this.initialized=true;
    }

    public int getX() {
        if(!this.initialized){
            throw new NullPointerException(uninitializedMessage);
        }
        return this.position.getX();
    }
    public int getY() {
        if(!this.initialized){
            throw new NullPointerException(uninitializedMessage);
        }
        return this.position.getY();
    }
    public Direction getDirection(){
        if(!this.initialized){
            throw new NullPointerException(uninitializedMessage);
        }
        return this.direction;
    }
    public int getBattery(){
        if(!this.initialized){
            throw new NullPointerException(uninitializedMessage);
        }
        return this.battery;
    }

    public void processResults(Result lastResult) {
        if(!this.initialized){
            throw new NullPointerException(uninitializedMessage);
        }
        this.battery -= lastResult.getCost();
        int[]vector = Direction.getDirectionVector(this.direction);
        Action action = lastResult.getAction();

        switch(action){
            case TURN_LEFT -> {
                this.direction = Direction.getLeft(this.direction);
                int[]newVector = Direction.getDirectionVector(this.direction);

                this.position.changeByOffset(vector[0],vector[1]);
                this.position.changeByOffset(newVector[0],newVector[1]);
            }
            case TURN_RIGHT -> {
                this.direction = Direction.getRight(this.direction);
                int[]newVector = Direction.getDirectionVector(this.direction);

                this.position.changeByOffset(vector[0],vector[1]);
                this.position.changeByOffset(newVector[0],newVector[1]);
            }
            case FLY -> this.position.changeByOffset(vector[0],vector[1]);
        }
    }

    public int getStopCost() {
        if(!this.initialized){
            throw new NullPointerException(uninitializedMessage);
        }
        //value arrived at after multiple tests
        return 20;
    }
}
