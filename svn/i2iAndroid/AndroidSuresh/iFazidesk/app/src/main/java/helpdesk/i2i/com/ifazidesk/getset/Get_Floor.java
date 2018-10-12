package helpdesk.i2i.com.ifazidesk.getset;

public class Get_Floor {

    private String FloorName;
    private String FloorShortName;
    private String FloorID;

	
	/*
	 [
		{
			"FloorID":15,
			"FloorName":"Ferns Icon",
			"FloorShortName":"FI"
		}
	 ]
	 */


    //FloorName
    public String getFloorName() {
        return FloorName;
    }

    public String setFloorName(String FloorName) {
        return this.FloorName = FloorName;
    }


    //FloorShortName
    public String getFloorShortName() {
        return FloorShortName;
    }

    public String setFloorShortName(String FloorShortName) {
        return this.FloorShortName = FloorShortName;
    }

    //FloorID
    public String getFloorID() {
        return FloorID;
    }

    public String setFloorID(String FloorID) {
        return this.FloorID = FloorID;
    }


}
