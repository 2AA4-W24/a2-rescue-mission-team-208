package ca.mcmaster.se2aa4.island.team208;

import org.json.JSONObject;

public class Drone {
    private Direction direction;
    private int battery;
    private Position position;

    public void initializeDrone(JSONObject info, int x, int y){
        this.direction = Direction.parseDirection(info.getString("heading"));
        this.battery = info.getInt("budget");
        this.position = new Position(x, y);
    }

    public int getX() {
        return this.position.getX();
    }
    public int getY() {
        return this.position.getY();
    }
    public Position copyPosition(){return new Position(this.getX(), this.getY());}

    public Direction getDirection(){
        return this.direction;
    }

    public int getBattery(){
        return this.battery;
    }

    public void processResults(Action action, Result lastResult) {
        this.battery -= lastResult.getCost();
        int[]vector = Direction.getDirectionVector(this.direction);

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

}
