package ca.mcmaster.se2aa4.island.team208;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.junit.platform.commons.PreconditionViolationException;

import java.util.List;

public class ScanIslandSequence implements ActionSequence{
    private List<Result> results;
    private Drone drone;
    private RadarInterpreter radar;
    private ScanInterpreter scanner;
    private IslandMap map;

    private boolean started;
    private boolean secondFly;
    private boolean completed;
    private Action northTurn;
    private Action southTurn;
    private Action northEcho;
    private Action southEcho;

    private Logger logger = LogManager.getLogger();

    //this scans all parts of Island South-East of position of first call to "setNextDecision(List <Action>)"
    //must be facing south
    public ScanIslandSequence(List<Result> results, Drone drone, RadarInterpreter radar, ScanInterpreter scanner, IslandMap map){
        this.results = results;
        this.drone = drone;
        this.radar = radar;
        this.scanner = scanner;
        this.map = map;
        this.northTurn = Action.TURN_RIGHT;
        this.southTurn = Action.TURN_LEFT;
        this.northEcho = Action.ECHO_RIGHT;
        this.southEcho = Action.ECHO_LEFT;
        this.started=false;
        this.secondFly=false;
        this.completed=false;
    }

    @Override
    public void setNextDecision(List<Action> decisionQueue) {

        if(this.started && !this.completed) {
            if (decisionQueue.get(decisionQueue.size() - 1) == Action.SCAN
                || decisionQueue.get(decisionQueue.size() - 1) == Action.FLY) {
                JSONArray creeks = this.scanner.getCreeks();
                JSONArray sites = this.scanner.getSites();
                decisionQueue.add(Action.ECHO_FRONT);
                if (!creeks.isEmpty()) {
                    this.map.addCreek(new Creek(creeks.get(0).toString()));
                    logger.info("A creek has been found.");
                }
                if (!sites.isEmpty()) {
                    this.map.setSite(new Site(sites.get(0).toString()));
                    logger.info("The site has been found.");
                }
            } else if (decisionQueue.get(decisionQueue.size() - 1) == Action.ECHO_FRONT) {
                if ((decisionQueue.get(decisionQueue.size() - 3) == this.northTurn
                        || decisionQueue.get(decisionQueue.size() - 3) == this.southTurn)
                        && this.radar.getFound().equals("OUT_OF_RANGE")) {
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
                        decisionQueue.add(Action.STOP);
                        logger.info("CREEKS: " + this.map.getCreeks());
                        logger.info("SITE: " + this.map.getSite());
                    }

                } else if (this.radar.getFound().equals("GROUND")) {
                    decisionQueue.add(Action.FLY);
                    if(this.radar.getRange() <= 1) decisionQueue.add(Action.SCAN);
                } else {
                    switch (this.drone.getDirection()) {
                        case S -> decisionQueue.add(this.southEcho);
                        case N -> decisionQueue.add(this.northEcho);
                    }
                }
            } else {
                switch (this.drone.getDirection()) {
                    case S -> {
                        if (this.radar.getFound().equals("GROUND")) {
                            decisionQueue.add(Action.FLY);
                            if (this.radar.getRange() == 0) decisionQueue.add(Action.SCAN);
                            decisionQueue.add(this.southEcho);
                        } else {
                            decisionQueue.add(this.southTurn);
                            decisionQueue.add(this.southTurn);
                            decisionQueue.add(Action.ECHO_FRONT);
                        }
                    }
                    case N -> {
                        if (this.radar.getFound().equals("GROUND")) {
                            decisionQueue.add(Action.FLY);
                            if (this.radar.getRange() == 0) decisionQueue.add(Action.SCAN);
                            decisionQueue.add(this.northEcho);
                        } else {
                            decisionQueue.add(this.northTurn);
                            decisionQueue.add(this.northTurn);
                            decisionQueue.add(Action.ECHO_FRONT);
                        }
                    }
                }
            }
        } else{
            if(!this.drone.getDirection().equals(Direction.S)){
                throw new PreconditionViolationException("Drone must be facing South before calling setNextDecision() for the first time.");
            }
            else{
                this.started = true;
                decisionQueue.add(Action.ECHO_FRONT);
            }
        }

    }

    @Override
    public boolean hasCompleted() {
        return this.completed;
    }
}
