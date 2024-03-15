package ca.mcmaster.se2aa4.island.team208;

import java.util.ArrayList;

public class IslandMap implements Map{
    private final ArrayList<Creek> creeks;
    private final Position start;
    private Creek closestCreek;
    private Site site;
    private Position topLeft;
    private Position bottomRight;

    public IslandMap(Position start) {
        this.creeks = new ArrayList<>();
        this.site = null;
        this.closestCreek = null;
        this.start = start;

        this.topLeft = null;
        this.bottomRight = null;
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
            Distance distance = null;
            for (Creek creek : this.creeks) {
                Distance newDistance = new Distance(creek.pos(), this.site.pos());
                if (distance == null || distance.getDistance() > newDistance.getDistance()) {
                    distance = newDistance;
                    this.closestCreek = creek;
                }
            }
        }
        return this.closestCreek;
    }

    public void setTopLeft(Position topLeft) {
        this.topLeft = topLeft;
    }
    public void setBottomRight(Position bottomRight) {
        this.bottomRight = bottomRight;
    }
    public Position getTopLeft() {
        return topLeft;
    }
    public Position getBottomRight() {
        return bottomRight;
    }

    public Position getStart() {
        return this.start;
    }
}
