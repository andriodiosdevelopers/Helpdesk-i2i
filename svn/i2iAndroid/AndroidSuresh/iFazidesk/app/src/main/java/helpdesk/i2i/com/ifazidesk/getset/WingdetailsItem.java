package helpdesk.i2i.com.ifazidesk.getset;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class WingdetailsItem extends RealmObject{

	@SerializedName("Wing")
	private String wing;

	@SerializedName("WingID")
	private int wingID;

	@SerializedName("FloorId")
	private int floorId;

	@SerializedName("WingShortName")
	private String wingShortName;

	public void setWing(String wing){
		this.wing = wing;
	}

	public String getWing(){
		return wing;
	}

	public void setWingID(int wingID){
		this.wingID = wingID;
	}

	public int getWingID(){
		return wingID;
	}

	public void setFloorId(int floorId){
		this.floorId = floorId;
	}

	public int getFloorId(){
		return floorId;
	}

	public void setWingShortName(String wingShortName){
		this.wingShortName = wingShortName;
	}

	public String getWingShortName(){
		return wingShortName;
	}

	@Override
 	public String toString(){
		return 
			"WingdetailsItem{" + 
			"wing = '" + wing + '\'' + 
			",wingID = '" + wingID + '\'' + 
			",floorId = '" + floorId + '\'' + 
			",wingShortName = '" + wingShortName + '\'' + 
			"}";
		}
}