package ca.mcmaster.se2aa4.island.team208.MapTools;

import ca.mcmaster.se2aa4.island.team208.Records.Creek;
import ca.mcmaster.se2aa4.island.team208.Records.Site;

import java.util.ArrayList;

public class IslandMap implements Map {
    private final ArrayList<Creek> creeks;
    private final Position start;
    private Creek closestCreek;
    private double distance;
    private Site site;

    public IslandMap(Position startReference) {
        this.creeks = new ArrayList<>();
        this.site = null;
        this.closestCreek = null;
        this.start = startReference;
        this.distance = Double.POSITIVE_INFINITY;
    }

    @Override
    public void addCreek(Creek creek) {
        this.creeks.add(creek);
    }

    @Override
    public void setSite(Site site) {
        this.site = site;
    }

    @Override
    public ArrayList<Creek> getCreeks() {
        return this.creeks;
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    @Override
    public Creek getClosestCreek() {
        if (this.site != null && !(this.creeks.isEmpty())) {
            for (Creek creek : this.creeks) {
                double newDistance = Position.getDistance(creek.pos(), this.site.pos());
                if (distance > newDistance) {
                    distance = newDistance;
                    this.closestCreek = creek;
                }
            }
        }
        return this.closestCreek;
    }

    public Position getStart() {
        return this.start;
    }
}
