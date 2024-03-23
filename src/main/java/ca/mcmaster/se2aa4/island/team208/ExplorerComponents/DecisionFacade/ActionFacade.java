package ca.mcmaster.se2aa4.island.team208.ExplorerComponents.DecisionFacade;

import ca.mcmaster.se2aa4.island.team208.Enums.Action;
import ca.mcmaster.se2aa4.island.team208.MapTools.Drone;
import org.json.JSONObject;

public class ActionFacade {

    public JSONObject createDecision(Action action, Drone drone) {;
        switch (action) {
            case TURN_LEFT, ECHO_LEFT -> {
                return (new LeftActionBuilder()).buildAction(action, drone);
            }
            case TURN_RIGHT, ECHO_RIGHT -> {
                return (new RightActionBuilder()).buildAction(action, drone);
            }
            case ECHO_FRONT -> {
                return (new FrontEchoActionBuilder()).buildAction(action, drone);
            }
            default -> {
                return (new OtherActionsBuilder()).buildAction(action, drone);
            }
        }
    }
}