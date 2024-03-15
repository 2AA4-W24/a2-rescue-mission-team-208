package ca.mcmaster.se2aa4.island.team208;

public class Distance {
    private double pos1_x;
    private double pos1_y;
    private double pos2_x;
    private double pos2_y;
    private double distance;
    public Distance (Position pos1, Position pos2) {
        this.pos1_x = pos1.getX();
        this.pos1_y = pos1.getY();
        this.pos2_x = pos2.getX();
        this.pos2_y = pos2.getY();
        this.distance = Math.sqrt(Math.pow(this.pos2_x - this.pos1_x, 2) + Math.pow(this.pos2_y - this.pos1_y, 2));
    }
    public double getDistance() {
        return this.distance;
    }
}
