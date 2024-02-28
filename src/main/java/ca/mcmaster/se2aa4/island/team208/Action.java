package ca.mcmaster.se2aa4.island.team208;

public enum Action {
    fly("fly"),
    heading("heading"),
    echo("echo"),
    scan("scan"),
    stop("stop"),
    land("land");

    private String action_string;

    Action(String a){
        this.action_string = a;
    }

    public String toString() {
        return this.action_string;
    }

}
