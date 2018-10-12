package helpdesk.i2i.com.ifazidesk.getset;

public class Get_Building {

    private String BuildingName;
    private String BuildingShortName;
    private String BuildingID;

	
	/*
	 [
		{
			"BuildingID":15,
			"BuildingName":"Ferns Icon",
			"BuildingShortName":"FI"
		}
	 ]
	 */


    //BuildingName
    public String getBuildingName() {
        return BuildingName;
    }

    public String setBuildingName(String BuildingName) {
        return this.BuildingName = BuildingName;
    }


    //BuildingShortName
    public String getBuildingShortName() {
        return BuildingShortName;
    }

    public String setBuildingShortName(String BuildingShortName) {
        return this.BuildingShortName = BuildingShortName;
    }

    //BuildingID
    public String getBuildingID() {
        return BuildingID;
    }

    public String setBuildingID(String BuildingID) {
        return this.BuildingID = BuildingID;
    }


}
