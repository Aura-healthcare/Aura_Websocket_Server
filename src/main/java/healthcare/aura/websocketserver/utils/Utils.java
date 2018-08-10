package healthcare.aura.websocketserver.utils;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.io.File;

public class Utils {
    public static final String CONFIG_FILE_OPTION_NAME = "c";
    public static final String DEBUG_FLAG_NAME = "d";

    public static void setupLocalFS(String folderToCreate) {
        File workFolder = new File(folderToCreate);
        if (!workFolder.exists()) workFolder.mkdirs();
    }

    public static Options setupOptions() {
        Options options = new Options();
        final Option configFileOption = Option.builder(CONFIG_FILE_OPTION_NAME)
                .longOpt("configfile")
                .desc("use given config file")
                .hasArg(true)
                .argName("path/to/configFile")
                .required(true)
                .build();

        options.addOption(configFileOption);
        options.addOption(DEBUG_FLAG_NAME, false, "debug mode");

        return options;
    }

}
