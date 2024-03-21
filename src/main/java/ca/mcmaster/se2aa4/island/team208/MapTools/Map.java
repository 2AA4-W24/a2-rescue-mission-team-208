package ca.mcmaster.se2aa4.island.team208.MapTools;

import ca.mcmaster.se2aa4.island.team208.Records.Creek;
import ca.mcmaster.se2aa4.island.team208.Records.Site;

import java.util.List;

public interface Map {

    void addCreek(Creek creek);
    void setSite(Site site);
    List<Creek> getCreeks();
    Site getSite();
    Creek getClosestCreek();

}
