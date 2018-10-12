package helpdesk.i2i.com.ifazidesk.datamodel.ticketdetails;

import com.google.gson.annotations.SerializedName;

public class TicketHistoryItem {

	@SerializedName("Status")
	private String status;

	@SerializedName("Building")
	private String building;

	@SerializedName("Floor")
	private String floor;

	@SerializedName("StatusDate")
	private String statusDate;

	@SerializedName("Wing")
	private String wing;

	@SerializedName("Username")
	private String username;

	@SerializedName("StatusTime")
	private String statusTime;

	@SerializedName("description")
	private String description;

	@SerializedName("location")
	private String location;

	@SerializedName("Colorcode")
	private String colorcode;

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setBuilding(String building){
		this.building = building;
	}

	public String getBuilding(){
		return building;
	}

	public void setFloor(String floor){
		this.floor = floor;
	}

	public String getFloor(){
		return floor;
	}

	public void setStatusDate(String statusDate){
		this.statusDate = statusDate;
	}

	public String getStatusDate(){
		return statusDate;
	}

	public void setWing(String wing){
		this.wing = wing;
	}

	public String getWing(){
		return wing;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setStatusTime(String statusTime){
		this.statusTime = statusTime;
	}

	public String getStatusTime(){
		return statusTime;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}

	public void setColorcode(String colorcode){
		this.colorcode = colorcode;
	}

	public String getColorcode(){
		return colorcode;
	}

	@Override
 	public String toString(){
		return 
			"TicketHistoryItem{" +
			"status = '" + status + '\'' + 
			",building = '" + building + '\'' + 
			",floor = '" + floor + '\'' + 
			",statusDate = '" + statusDate + '\'' + 
			",wing = '" + wing + '\'' + 
			",username = '" + username + '\'' + 
			",statusTime = '" + statusTime + '\'' + 
			",description = '" + description + '\'' + 
			",location = '" + location + '\'' + 
			",colorcode = '" + colorcode + '\'' + 
			"}";
		}
}