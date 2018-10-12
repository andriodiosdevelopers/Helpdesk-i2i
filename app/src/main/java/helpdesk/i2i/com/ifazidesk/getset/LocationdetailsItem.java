package helpdesk.i2i.com.ifazidesk.getset;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


public class LocationdetailsItem extends RealmObject {

	@SerializedName("CompanyId")
	private int companyId;

	@SerializedName("LocationId")
	private int locationId;

	@SerializedName("Location")
	private String location;

	public void setCompanyId(int companyId){
		this.companyId = companyId;
	}

	public int getCompanyId(){
		return companyId;
	}

	public void setLocationId(int locationId){
		this.locationId = locationId;
	}

	public int getLocationId(){
		return locationId;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}

	@Override
 	public String toString(){
		return 
			"LocationdetailsItem{" + 
			"companyId = '" + companyId + '\'' + 
			",locationId = '" + locationId + '\'' + 
			",location = '" + location + '\'' + 
			"}";
		}
}