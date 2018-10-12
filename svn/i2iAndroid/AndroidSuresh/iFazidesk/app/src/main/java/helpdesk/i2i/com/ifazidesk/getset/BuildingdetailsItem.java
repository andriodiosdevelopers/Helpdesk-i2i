package helpdesk.i2i.com.ifazidesk.getset;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


public class BuildingdetailsItem extends RealmObject{

	@SerializedName("Building")
	private String building;

	@SerializedName("LocationId")
	private int locationId;

	@SerializedName("BuildingShortName")
	private String buildingShortName;

	@SerializedName("BuildingID")
	private int buildingID;

	public void setBuilding(String building){
		this.building = building;
	}

	public String getBuilding(){
		return building;
	}

	public void setLocationId(int locationId){
		this.locationId = locationId;
	}

	public int getLocationId(){
		return locationId;
	}

	public void setBuildingShortName(String buildingShortName){
		this.buildingShortName = buildingShortName;
	}

	public String getBuildingShortName(){
		return buildingShortName;
	}

	public void setBuildingID(int buildingID){
		this.buildingID = buildingID;
	}

	public int getBuildingID(){
		return buildingID;
	}

	@Override
 	public String toString(){
		return 
			"BuildingdetailsItem{" + 
			"building = '" + building + '\'' + 
			",locationId = '" + locationId + '\'' + 
			",buildingShortName = '" + buildingShortName + '\'' + 
			",buildingID = '" + buildingID + '\'' + 
			"}";
		}
}