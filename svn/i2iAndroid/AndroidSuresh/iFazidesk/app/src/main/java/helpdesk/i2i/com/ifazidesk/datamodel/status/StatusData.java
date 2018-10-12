package helpdesk.i2i.com.ifazidesk.datamodel.status;

import com.google.gson.annotations.SerializedName;

public class StatusData {

	@SerializedName("tickets")
	private int tickets;

	@SerializedName("statusid")
	private int statusid;

	@SerializedName("isTotal")
	private boolean isTotal;

	@SerializedName("colorcode")
	private Object colorcode;

	@SerializedName("ShortName")
	private Object shortName;

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

	public void setColorcode(Object colorcode){
		this.colorcode = colorcode;
	}

	public Object getColorcode(){
		return colorcode;
	}

	public void setShortName(Object shortName){
		this.shortName = shortName;
	}

	public Object getShortName(){
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
			"StatusdetailsItem{" + 
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