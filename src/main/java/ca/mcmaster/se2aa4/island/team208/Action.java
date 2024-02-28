package ca.mcmaster.se2aa4.island.team208;

public enum Action {
    FLY("fly"),
    HEADING("heading"),
    ECHO("echo"),
    SCAN("scan"),
    STOP("stop"),
    LAND("land");

    private String action_string;

    Action(String a){
        this.action_string = a;
    }

    public String toString() {
        return this.action_string;
    }

}
