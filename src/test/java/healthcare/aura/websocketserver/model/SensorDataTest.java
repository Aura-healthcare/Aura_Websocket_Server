package healthcare.aura.websocketserver.model;

import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.*;

public class SensorDataTest {

    @Test
    public void generateFileNameShouldGenerateProperFileName() throws Exception {
        // Given
        String testJsonString = "{" +
                "'user':'BenSawyer'," +
                "'type':'LungSignal'," +
                "'device_address':'89d16533-e19d-4af8-a9ad-d759dbe0d5a1'," +
                "'data':['2033-06-09T17:21:38.527 0','2033-06-09T17:31:38.638 0']" +
                "}";
        Gson gson = new Gson();
        SensorData testSensorData = gson.fromJson(testJsonString, SensorData.class);

        // When
        String testFileName = testSensorData.generateFileName();

        // Then
        assertEquals(
                "BenSawyer_LungSignal_2033-06-09T172138527.json",
                testFileName);
    }

}