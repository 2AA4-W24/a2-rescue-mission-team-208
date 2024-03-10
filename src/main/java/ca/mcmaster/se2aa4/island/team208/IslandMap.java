package ca.mcmaster.se2aa4.island.team208;

import java.util.ArrayList;

public class IslandMap implements Map{
    private final ArrayList<Creek> creeks;
    private final Position start;

    private Site site;
    private Position topLeft;
    private Position bottomRight;

    public IslandMap(Position start) {
        this.creeks = new ArrayList<>();
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
