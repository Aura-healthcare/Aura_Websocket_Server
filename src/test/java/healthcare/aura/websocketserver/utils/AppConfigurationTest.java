package healthcare.aura.websocketserver.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.apache.commons.configuration2.ex.ConfigurationException;

import static org.junit.Assert.*;

public class AppConfigurationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void loadConfigurationShouldFailWithIncompleteConfigFile() throws Exception {
        // Given
        AppConfiguration testConfig = new AppConfiguration();
        String invalidConfigFilePath = getClass()
                .getClassLoader()
                .getResource("invalid_wsserver.properties")
                .getFile();

        // Expected Exception
        thrown.expect(ConfigurationException.class);
        thrown.expectMessage("Missing field : server.port in config file - Aborting !");

        // When
        testConfig.loadConfiguration(invalidConfigFilePath);
    }

    @Test
    public void loadConfigurationShouldLoadConfigFileProperly() throws Exception {
        // Given
        AppConfiguration testConfig = new AppConfiguration();
        String properConfigFilePath = getClass()
                .getClassLoader()
                .getResource("proper_wsserver.properties")
                .getFile();

        // When
        testConfig.loadConfiguration(properConfigFilePath);

        // Then
        assertEquals("/example/", testConfig.getDataPath());
        assertEquals(1234, testConfig.getServerPort());
    }
}