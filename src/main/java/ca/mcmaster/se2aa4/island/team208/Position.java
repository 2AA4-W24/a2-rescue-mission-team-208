package ca.mcmaster.se2aa4.island.team208;

public record Position(double x, double y) {
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public String getPosition() {
        return "(" + x + "," + y + ")";
    }
}
