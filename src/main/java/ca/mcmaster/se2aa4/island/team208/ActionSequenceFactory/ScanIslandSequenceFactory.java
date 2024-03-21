package ca.mcmaster.se2aa4.island.team208.ActionSequenceFactory;

import ca.mcmaster.se2aa4.island.team208.ActionFactory.*;
import ca.mcmaster.se2aa4.island.team208.Enums.Action;
import ca.mcmaster.se2aa4.island.team208.Enums.Direction;
import ca.mcmaster.se2aa4.island.team208.Interpreters.RadarInterpreter;
import ca.mcmaster.se2aa4.island.team208.Interpreters.ScanInterpreter;
import ca.mcmaster.se2aa4.island.team208.MapTools.Drone;
import ca.mcmaster.se2aa4.island.team208.MapTools.IslandMap;
import org.junit.platform.commons.PreconditionViolationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScanIslandSequenceFactory implements ActionSequenceFactory {
    private Drone drone;
    private RadarInterpreter radar;
    private ScanInterpreter scanner;
    private IslandMap map;

    private boolean started;
    private boolean completed;
    private ScanIslandScanFlyActions scanSeq;
    private ScanIslandEchoFrontActions echoFrontSeq;


    //this scans all parts of Island South-East of position of first call to "setNextDecision(List <Action>)"
    //must be facing south
    public ScanIslandSequenceFactory(Drone drone, RadarInterpreter radar, ScanInterpreter scanner, IslandMap map){
        this.drone = drone;
        this.radar = radar;
        this.scanner = scanner;
        this.map = map;
        this.scanSeq = new ScanIslandScanFlyActions(this.map, this.drone);
        this.echoFrontSeq = new ScanIslandEchoFrontActions(this.map, this.drone);
        this.started=false;
        this.completed=false;
    }

    @Override
    public void generateNextActions(List<Action> decisionQueue) {
        if(this.started && !this.completed) {
            this.scanSeq.updateMap(this.map);
            this.scanSeq.updateDrone(this.drone);
            this.echoFrontSeq.updateMap(this.map);
            this.echoFrontSeq.updateDrone(this.drone);
            Map<Action, ActionFactory> mapActionSeq = new HashMap<>();
            mapActionSeq.put(Action.SCAN, this.scanSeq);
            mapActionSeq.put(Action.FLY, this.scanSeq);
            mapActionSeq.put(Action.ECHO_FRONT, this.echoFrontSeq);
            mapActionSeq.put(this.echoFrontSeq.getSouthEcho(),
                    new ScanIslandEchoSouthActions(this.echoFrontSeq.getSouthEcho(),
                            this.echoFrontSeq.getSouthTurn()));
            mapActionSeq.put(this.echoFrontSeq.getNorthEcho(),
                    new ScanIslandEchoNorthActions(this.echoFrontSeq.getNorthEcho(),
                            this.echoFrontSeq.getNorthTurn()));
            mapActionSeq.get(decisionQueue.get(decisionQueue.size() - 1))
                    .addActions(decisionQueue, this.radar, this.scanner);
            this.map = this.scanSeq.getUpdatedMap();
            this.completed = this.echoFrontSeq.lastAction();
        } else{
            if(!this.drone.getDirection().equals(Direction.S)){
                throw new PreconditionViolationException("Drone must be facing South before calling setNextDecision() for the first time.");
            }
            else{
                this.started = true;
                decisionQueue.add(Action.SCAN);
            }
        }

    }

    @Override
    public boolean hasCompleted() {
        return this.completed;
    }
}
