package healthcare.aura.websocketserver.model;

import java.util.ArrayList;
import java.util.List;

/*
 A class used to deserialize data incoming from the mobile app.

 Example of Json file sent :
    {
      "user": "d8273433-9f75-4d81-81c3-ee47c6334178",
      "type": "RrInterval",
      "device_address": "89d16533-e19d-4af8-a9ad-d759dbe0d5a1",
      "data": [
        "1900-02-01T01:01:01.000 0",
        "1901-02-01T01:01:01.000 100",
        "1902-02-01T01:01:01.000 200"
        ]
    }
* */
public class SensorData {
    private String user; // User id field
    private String device_address; // Device id field
    private String type; // Sensor type field
    private String[] data; // Data points

    SensorData() {}

    public String getUser() {
        return this.user;
    }

    public String getDeviceAddress() {
        return this.device_address;
    }

    public String getSensorType() {
        return this.type;
    }

    public String[] getData() {
        return this.data;
    }

    private String getFirstTimestampCleaned() {
        return this
                .data[0]
                .split(" ", 2)[0]
                .replaceAll(":", "")
                .replace(".", "");
    }

    public String generateFileName() {
        String delimiter = "_";
        String fileExtension = ".json";
        List<String> fileNameElementsList = new ArrayList<>();
        fileNameElementsList.add(this.user);
        fileNameElementsList.add(this.type);
        fileNameElementsList.add(this.getFirstTimestampCleaned());
        return String.join(delimiter, fileNameElementsList) + fileExtension;
    }
}
