package ca.mcmaster.se2aa4.island.team208.ExplorerComponents.DecisionFacade;

import ca.mcmaster.se2aa4.island.team208.Enums.Action;
import ca.mcmaster.se2aa4.island.team208.MapTools.Drone;
import org.json.JSONObject;

public interface ActionBuilder {
    JSONObject buildAction(Action action, Drone drone);
}
