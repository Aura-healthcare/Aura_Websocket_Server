package healthcare.aura.websocketserver.utils;

import healthcare.aura.websocketserver.AuraWebSocketServer;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppConfiguration {
    private static final Logger LOGGER = LogManager.getLogger(AuraWebSocketServer.class);
    private Configurations configurations;
    private Configuration config;
    private static final String DATA_PATH_PROP_FIELD_NAME = "data.path";
    private static final String SERVER_PORT_FIELD_NAME = "server.port";
    private static final List<String> NECESSERY_FIELDS = new ArrayList<>();

    static {
        NECESSERY_FIELDS.add(DATA_PATH_PROP_FIELD_NAME);
        NECESSERY_FIELDS.add(SERVER_PORT_FIELD_NAME);
    }

    public AppConfiguration() {
        configurations = new Configurations();
    }

    public void loadConfiguration(String configFileName) throws ConfigurationException {
        File configFile = new File(configFileName);
        this.config = configurations.properties(configFile);
        checkConfiguration();
    }

    private void checkConfiguration() throws ConfigurationException {
        for (String field : NECESSERY_FIELDS) {
            if (!config.containsKey(field))
                throw new ConfigurationException("Missing field : " + field + " in config file - Aborting !");
        }
    }

    public void printAllParams() {
        Iterator<String> keys = config.getKeys();
        while (keys.hasNext()) {
            String key = keys.next();
            LOGGER.info("{} : {}" , key, config.getProperty(key));
        }
    }

    public String getDataPath() {
        return config.getString(DATA_PATH_PROP_FIELD_NAME);
    }

    public int getServerPort() {
        return config.getInt(SERVER_PORT_FIELD_NAME);
    }
}
