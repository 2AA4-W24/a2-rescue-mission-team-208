package ca.mcmaster.se2aa4.island.team208.ActionFactory;

import ca.mcmaster.se2aa4.island.team208.MapTools.Drone;
import ca.mcmaster.se2aa4.island.team208.Enums.Action;
import ca.mcmaster.se2aa4.island.team208.MapTools.IslandMap;
import ca.mcmaster.se2aa4.island.team208.Interpreters.RadarInterpreter;
import ca.mcmaster.se2aa4.island.team208.Interpreters.ScanInterpreter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ScanIslandEchoFrontActions implements ActionFactory {
    private IslandMap map;
    private boolean completed;
    private Drone drone;
    private boolean secondFly;
    private Action northTurn;
    private Action southTurn;
    private Action northEcho;
    private Action southEcho;

    private Logger logger = LogManager.getLogger();
    public ScanIslandEchoFrontActions (IslandMap map, Drone drone) {
        this.map = map;
        this.drone = drone;
        this.secondFly = false;
        this.completed = false;
        this.northTurn = Action.TURN_RIGHT;
        this.southTurn = Action.TURN_LEFT;
        this.northEcho = Action.ECHO_RIGHT;
        this.southEcho = Action.ECHO_LEFT;
    }
    public void updateMap(IslandMap map) {
        this.map = map;
    }
    public void updateDrone(Drone drone) {
        this.drone = drone;
    }
    public Action getNorthTurn() {
        return this.northTurn;
    }
    public Action getSouthTurn() {
        return this.southTurn;
    }
    public Action getNorthEcho() {
        return this.northEcho;
    }
    public Action getSouthEcho() {
        return this.southEcho;
    }
    @Override
    public void addActions(List<Action> decisionQueue, RadarInterpreter radarInterpreter, ScanInterpreter scanInterpreter) {
        if ((decisionQueue.get(decisionQueue.size() - 3) == this.northTurn
                || decisionQueue.get(decisionQueue.size() - 3) == this.southTurn)
                && radarInterpreter.getFound().equals("OUT_OF_RANGE")) {
            if (!this.secondFly) {
                this.northTurn = Action.TURN_LEFT;
                this.southTurn = Action.TURN_RIGHT;
                this.northEcho = Action.ECHO_LEFT;
                this.southEcho = Action.ECHO_RIGHT;

                switch(this.drone.getDirection()) {
                    case N -> {
                        decisionQueue.add(this.northTurn);
                        decisionQueue.add(Action.FLY);
                        decisionQueue.add(this.northTurn);
                        decisionQueue.add(this.northTurn);
                        decisionQueue.add(this.northTurn);
                        decisionQueue.add(this.northEcho);
                    }
                    case S -> {
                        decisionQueue.add(this.southTurn);
                        decisionQueue.add(Action.FLY);
                        decisionQueue.add(this.southTurn);
                        decisionQueue.add(this.southTurn);
                        decisionQueue.add(this.southTurn);
                        decisionQueue.add(this.southEcho);
                    }
                }
                this.secondFly = true;
            } else {
                decisionQueue.add(Action.SCAN);
                this.completed=true;
            }

        } else if (radarInterpreter.getFound().equals("GROUND")) {
            for (int i = 0; i < radarInterpreter.getRange(); i++) decisionQueue.add(Action.FLY);
            decisionQueue.add(Action.SCAN);
            decisionQueue.add(Action.FLY);
            decisionQueue.add(Action.SCAN);
        } else {
            switch (this.drone.getDirection()) {
                case S -> decisionQueue.add(this.southEcho);
                case N -> decisionQueue.add(this.northEcho);
            }
        }
    }

    @Override
    public boolean lastAction () {
        return this.completed;
    }

}
