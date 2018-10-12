package helpdesk.i2i.com.ifazidesk.datamodel.dashboard;

import com.google.gson.annotations.SerializedName;

public class StatusdetailsItem{

	@SerializedName("tickets")
	private int tickets;

	@SerializedName("statusid")
	private int statusid;

	@SerializedName("isTotal")
	private boolean isTotal;

	@SerializedName("colorcode")
	private String colorcode;

	@SerializedName("ShortName")
	private String shortName;

	@SerializedName("isonHold")
	private boolean isonHold;

	@SerializedName("status")
	private String status;

	public void setTickets(int tickets){
		this.tickets = tickets;
	}

	public int getTickets(){
		return tickets;
	}

	public void setStatusid(int statusid){
		this.statusid = statusid;
	}

	public int getStatusid(){
		return statusid;
	}

	public void setIsTotal(boolean isTotal){
		this.isTotal = isTotal;
	}

	public boolean isIsTotal(){
		return isTotal;
	}

	public void setColorcode(String colorcode){
		this.colorcode = colorcode;
	}

	public String getColorcode(){
		return colorcode;
	}

	public void setShortName(String shortName){
		this.shortName = shortName;
	}

	public String getShortName(){
		return shortName;
	}

	public void setIsonHold(boolean isonHold){
		this.isonHold = isonHold;
	}

	public boolean isIsonHold(){
		return isonHold;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
	public String toString(){
		return
				"StatusData{" +
						"tickets = '" + tickets + '\'' +
						",statusid = '" + statusid + '\'' +
						",isTotal = '" + isTotal + '\'' +
						",colorcode = '" + colorcode + '\'' +
						",shortName = '" + shortName + '\'' +
						",isonHold = '" + isonHold + '\'' +
						",status = '" + status + '\'' +
						"}";
	}
}