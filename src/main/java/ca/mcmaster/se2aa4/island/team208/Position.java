package ca.mcmaster.se2aa4.island.team208;

public record Position(double x, double y) {

    public String getPosition() {
        return "(" + x + "," + y + ")";
    }
}
