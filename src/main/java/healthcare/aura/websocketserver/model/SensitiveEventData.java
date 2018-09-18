package healthcare.aura.websocketserver.model;

import java.util.ArrayList;

/**A class used to deserialize seizure data incoming from the mobile app.

Example of Json file sent :
   [
    {
      "mIntensity":"Big",
      "mSensitiveEventTimestamp":"2018-09-18T08:45:00.000",
      "mTimestamp":"2018-09-18T11:45:53.449",
      "mType":"SensitiveEvent",
      "mUser":"e2dcb9bf-31dd-4fe6-b1ca-92b4b9ee834a",
      "mUuid":"1c01bde0-6989-4cb3-9231-59e30ac617e5",
      "mAdditionalInformation":
        {
           "high_stress_episode_value":"no",
           "comments":"test",
           "alcohol_consumption":"few_drinks",
           "treatment_observance":"missing_some",
           "new_treatment":"no",
           "having_fever":"no",
           "quality_of_sleep":"few_wake_ups",
           "late_sleep":"no"
        }
    },
    { ... },
  ]
* */

public class SensitiveEventData {
    private String mUuid; // Uuid
    private String mUser; // User id field
    private String mType; // Event type
    private String mIntensity; // Event intensity
    private String mTimestamp; // Event recording timestamp
    private String mSensitiveEventTimestamp; // Sensitive event timestamp

    private static final String SENSITIVE_DATA_PREFIX_FILENAME = "SensitiveEvent";

    public SensitiveEventData() {}

    public String getUuid(){
      return mUuid;
    }

    public String getUser(){
      return mUser;
    }

    public String getType(){
      return mType;
    }

    public String getIntensity(){
      return mIntensity;
    }

    public String getTimestamp(){
      return mTimestamp;
    }

    public String getSensitiveEventTimestamp(){
      return mSensitiveEventTimestamp;
    }

    private String getTimestampCleaned() {
        return this
                .mTimestamp
                .replaceAll(":", "")
                .replace(".", "");
    }

    public String generateFileName(){
      String delimiter = "_";
      String fileExtension = ".json";
      ArrayList<String> fileNameElementsList = new ArrayList<>();
      fileNameElementsList.add(this.getUser());
      fileNameElementsList.add(SENSITIVE_DATA_PREFIX_FILENAME);
      fileNameElementsList.add(this.getTimestampCleaned());
      return String.join(delimiter, fileNameElementsList) + fileExtension;
    }
}
