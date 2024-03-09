package ca.mcmaster.se2aa4.island.team208;

import java.util.ArrayList;

public class IslandMap implements Map{
    private ArrayList<Creek> creeks;
    private Site site;

    public IslandMap() {
        this.creeks = new ArrayList<>();
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

}
