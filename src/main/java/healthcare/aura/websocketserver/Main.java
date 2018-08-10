package healthcare.aura.websocketserver;

/*
 * Copyright (c) 2010-2018 Nathan Rajlich
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */

import healthcare.aura.websocketserver.utils.AppConfiguration;
import healthcare.aura.websocketserver.utils.Utils;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.WebSocketImpl;
import org.apache.commons.cli.*;

import java.net.UnknownHostException;
import java.util.List;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Options options = Utils.setupOptions();
        CommandLineParser parser = new DefaultParser();

        try {
            // Parse parameters
            CommandLine cmd = parser.parse(options, args);
            List<String> unrecognizedOptions = cmd.getArgList();
            if (!unrecognizedOptions.isEmpty()) LOGGER.warn("Unrecognized options : " + cmd.getArgList());

            // Handle debug parameter
            Boolean debugModeActivated = cmd.hasOption(Utils.DEBUG_FLAG_NAME);
            WebSocketImpl.DEBUG = debugModeActivated;
            LOGGER.info("Debug mode activated : " + debugModeActivated);

            // Load config file
            String configFileName = cmd.getOptionValue(Utils.CONFIG_FILE_OPTION_NAME);
            LOGGER.info("Config file : {}", configFileName);
            AppConfiguration config = new AppConfiguration();
            config.loadConfiguration(configFileName);
            LOGGER.info("Printing loaded properties from Config file : ");
            config.printAllParams();

            // Start server
            AuraWebSocketServer server = new AuraWebSocketServer(config.getServerPort(), config.getDataPath());
            server.start();
        } catch (ParseException e) {
            LOGGER.fatal("Could not read arguments properly - Exiting now");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar WebSocketServer-[version].jar", options);
        } catch (ConfigurationException e) {
            LOGGER.fatal("An error occurred with config file...");
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
