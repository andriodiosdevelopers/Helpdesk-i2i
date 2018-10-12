package helpdesk.i2i.com.ifazidesk.getset;

public class Get_Wing {

    private String WingName;
    private String WingShortName;
    private String WingID;

	
	/*
	 [
		{
			"WingID":15,
			"WingName":"Ferns Icon",
			"WingShortName":"FI"
		}
	 ]
	 */


    //WingName
    public String getWingName() {
        return WingName;
    }

    public String setWingName(String WingName) {
        return this.WingName = WingName;
    }


    //WingShortName
    public String getWingShortName() {
        return WingShortName;
    }

    public String setWingShortName(String WingShortName) {
        return this.WingShortName = WingShortName;
    }

    //WingID
    public String getWingID() {
        return WingID;
    }

    public String setWingID(String WingID) {
        return this.WingID = WingID;
    }


}
