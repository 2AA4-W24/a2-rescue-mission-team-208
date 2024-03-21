package ca.mcmaster.se2aa4.island.team208;

import ca.mcmaster.se2aa4.island.team208.ExplorerComponents.Decision.Decider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private final Decider decider;


    public Explorer(){
        this.decider = new Decider();
    }

    @Override
    public void initialize(String s) {
        this.decider.initialize(s,0,0);
    }

    @Override
    public String takeDecision(){
        String decision = decider.getNextStep();
        logger.info("Decision: " + decision);
        return decision;
    }

    @Override
    public void acknowledgeResults(String s) {
        this.decider.processResults(s);
    }

    @Override
    public String deliverFinalReport() {
        return this.decider.getReport();
    }

}
