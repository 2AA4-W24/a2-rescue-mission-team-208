package ca.mcmaster.se2aa4.island.team208.ExplorerComponents.DecisionFacade;

import ca.mcmaster.se2aa4.island.team208.Enums.Action;
import ca.mcmaster.se2aa4.island.team208.Enums.Direction;
import ca.mcmaster.se2aa4.island.team208.MapTools.Drone;
import org.json.JSONObject;

public class RightActionBuilder implements ActionBuilder {
    public JSONObject buildAction(Action action, Drone drone) {
        JSONObject step = new JSONObject();
        step.put("action", action.toString());
        step.put("parameters", new JSONObject().put("direction", Direction.getRight(drone.getDirection()).toString()));
        return step;
    }
}
