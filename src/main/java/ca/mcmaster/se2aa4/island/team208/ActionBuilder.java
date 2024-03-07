package ca.mcmaster.se2aa4.island.team208;

import java.util.List;
import java.util.ArrayList;

public class ActionBuilder {
    public static List<Action> actionsNTimes (Action [] actions, int n){
        ArrayList <Action> result = new ArrayList<>();
        for (int i=0; i<n; i++){
            result.addAll(List.of(actions));
        }
        return result;
    }
}
