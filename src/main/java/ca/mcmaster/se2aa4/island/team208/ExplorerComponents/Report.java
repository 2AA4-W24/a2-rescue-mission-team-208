package ca.mcmaster.se2aa4.island.team208.ExplorerComponents;

import ca.mcmaster.se2aa4.island.team208.Records.Creek;

public class Report {

    private final Creek closestCreek;

    public Report(Creek creek) {
        this.closestCreek = creek;
    }

    public Creek getClosestCreek() {
        return this.closestCreek;
    }

    public String toString() {
        return "The closest creek is: " + this.closestCreek.uuid();
    }

}
