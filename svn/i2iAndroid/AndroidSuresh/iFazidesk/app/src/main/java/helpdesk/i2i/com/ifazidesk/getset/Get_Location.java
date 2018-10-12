package helpdesk.i2i.com.ifazidesk.getset;

public class Get_Location {

    private String LocationName;
    private String LocationShortName;
    private String LocationID;

		
		/*
		 [
			{
				"LocationID":15,
				"LocationName":"Ferns Icon",
				"LocationShortName":"FI"
			}
		 ]
		 */


    //LocationName
    public String getLocationName() {
        return LocationName;
    }

    public String setLocationName(String LocationName) {
        return this.LocationName = LocationName;
    }


    //LocationShortName
    public String getLocationShortName() {
        return LocationShortName;
    }

    public String setLocationShortName(String LocationShortName) {
        return this.LocationShortName = LocationShortName;
    }

    //LocationID
    public String getLocationID() {
        return LocationID;
    }

    public String setLocationID(String LocationID) {
        return this.LocationID = LocationID;
    }


}
