package ca.mcmaster.se2aa4.island.team208;

import java.util.ArrayList;

public interface Map {

    void addCreek(Creek creek);
    void setSite(Site site);
    ArrayList<Creek> getCreeks();
    Site getSite();
    Creek getClosestCreek();

}
