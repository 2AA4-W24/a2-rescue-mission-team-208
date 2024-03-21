package ca.mcmaster.se2aa4.island.team208.ActionFactory;


import ca.mcmaster.se2aa4.island.team208.Enums.Action;

import ca.mcmaster.se2aa4.island.team208.Interpreters.RadarInterpreter;
import ca.mcmaster.se2aa4.island.team208.Interpreters.ScanInterpreter;
import ca.mcmaster.se2aa4.island.team208.MapTools.Drone;
import ca.mcmaster.se2aa4.island.team208.MapTools.IslandMap;
import ca.mcmaster.se2aa4.island.team208.MapTools.Position;
import ca.mcmaster.se2aa4.island.team208.Records.Creek;
import ca.mcmaster.se2aa4.island.team208.Records.Site;
import org.json.JSONArray;

import java.util.List;

public class ScanIslandScanFlyActions implements ActionFactory {
    private IslandMap map;
    private Drone drone;
    public ScanIslandScanFlyActions (IslandMap map, Drone drone) {
        this.map = map;
        this.drone = drone;
    }
    public void updateMap(IslandMap map){
        this.map = map;
    }
    public void updateDrone(Drone drone) {
        this.drone = drone;
    }
    public IslandMap getUpdatedMap(){
        return this.map;
    }
    @Override
    public void addActions(List<Action> decisionQueue, RadarInterpreter radarInterpreter, ScanInterpreter scanInterpreter) {
        JSONArray creeks = scanInterpreter.getCreeks();
        JSONArray sites = scanInterpreter.getSites();
        if (!scanInterpreter.isOceanOnly()) {
            decisionQueue.add(Action.FLY);
            decisionQueue.add(Action.SCAN);
        } else {
            decisionQueue.add(Action.ECHO_FRONT);
        }

        if (!creeks.isEmpty()) {
            this.map.addCreek(new Creek(creeks.get(0).toString(),
                    new Position(this.drone.getX(), this.drone.getY())));
        }
        if (!sites.isEmpty()) {
            this.map.setSite(new Site(sites.get(0).toString(),
                    new Position(this.drone.getX(), this.drone.getY())));
        }
    }
    @Override
    public boolean lastAction () {
        return false;
    }
}
