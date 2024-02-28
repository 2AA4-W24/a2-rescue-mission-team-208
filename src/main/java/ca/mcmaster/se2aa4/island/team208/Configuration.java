package ca.mcmaster.se2aa4.island.team208;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.StringReader;

public class Configuration {
    private final Logger logger = LogManager.getLogger();
    private JSONObject info;
    private String direction = "";
    private Integer battery;

    public Configuration (String input) {
        this.info = new JSONObject(new JSONTokener(new StringReader(input)));
        this.direction = info.getString("HEADING");
        this.battery = info.getInt("budget");
    }

    public JSONObject getInfo() {
        return this.info;
    }
    public String getDirection() {
        return this.direction;
    }
    public Integer getBattery() {
        return this.battery;
    }

    public void printStatus() {
        logger.info("** Initializing the Exploration Command Center");
        logger.info("** Initialization info:\n {}", this.info.toString(2));
        logger.info("The drone is facing {}", this.direction);
        logger.info("Battery level is {}", this.battery);
    }
}
