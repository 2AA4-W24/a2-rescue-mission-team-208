package ca.mcmaster.se2aa4.island.team208;

import java.util.List;

public interface Map {

    void addCreek(Creek creek);
    void setSite(Site site);
    List<Creek> getCreeks();
    Site getSite();
    Creek getClosestCreek();

}
