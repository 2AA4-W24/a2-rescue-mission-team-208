package ca.mcmaster.se2aa4.island.team208;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Report {

    private final Logger logger = LogManager.getLogger();
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
