package helpdesk.i2i.com.ifazidesk.getset;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


public class FloordetailsItem extends RealmObject {

	@SerializedName("Floor")
	private String floor;

	@SerializedName("FloorShortName")
	private String floorShortName;

	@SerializedName("FloorID")
	private int floorID;

	@SerializedName("BuildingId")
	private int buildingId;

	public void setFloor(String floor){
		this.floor = floor;
	}

	public String getFloor(){
		return floor;
	}

	public void setFloorShortName(String floorShortName){
		this.floorShortName = floorShortName;
	}

	public String getFloorShortName(){
		return floorShortName;
	}

	public void setFloorID(int floorID){
		this.floorID = floorID;
	}

	public int getFloorID(){
		return floorID;
	}

	public void setBuildingId(int buildingId){
		this.buildingId = buildingId;
	}

	public int getBuildingId(){
		return buildingId;
	}

	@Override
 	public String toString(){
		return 
			"FloordetailsItem{" + 
			"floor = '" + floor + '\'' + 
			",floorShortName = '" + floorShortName + '\'' + 
			",floorID = '" + floorID + '\'' + 
			",buildingId = '" + buildingId + '\'' + 
			"}";
		}
}