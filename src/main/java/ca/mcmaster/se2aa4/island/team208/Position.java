package ca.mcmaster.se2aa4.island.team208;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y){
        this.x=x;
        this.y=y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void changeByOffset(int x, int y){
        this.x+=x;
        this.y+=y;
    }
    public void setPosition(int x, int y){
        this.x=x;
        this.y=y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public static double getDistance(Position pos1, Position pos2){
        return Math.sqrt(Math.pow(pos2.getX() - pos1.getX(), 2) + Math.pow(pos2.getY() - pos1.getY(), 2));
    }
}
