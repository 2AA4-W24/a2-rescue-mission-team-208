package ca.mcmaster.se2aa4.island.team208;

public enum Action {
    FLY("fly"),
    TURN_RIGHT("heading"),
    TURN_LEFT("heading"),
    ECHO_RIGHT("echo"),
    ECHO_LEFT("echo"),
    ECHO_FRONT("echo"),
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
