package helpdesk.i2i.com.ifazidesk.datamodel.dashboard;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DashboardData implements Serializable {

	@SerializedName("statusdetails")
	private List<StatusdetailsItem> statusdetails;

	@SerializedName("Status")
	private boolean status;

	@SerializedName("Message")
	private String message;

	@SerializedName("ticketdetails")
	private List<TicketdetailsItem> ticketdetails;

	public void setStatusdetails(List<StatusdetailsItem> statusdetails){
		this.statusdetails = statusdetails;
	}

	public List<StatusdetailsItem> getStatusdetails(){
		return statusdetails;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setTicketdetails(List<TicketdetailsItem> ticketdetails){
		this.ticketdetails = ticketdetails;
	}

	public List<TicketdetailsItem> getTicketdetails(){
		return ticketdetails;
	}

	@Override
 	public String toString(){
		return 
			"DashboardData{" +
			"statusdetails = '" + statusdetails + '\'' + 
			",status = '" + status + '\'' + 
			",message = '" + message + '\'' + 
			",ticketdetails = '" + ticketdetails + '\'' + 
			"}";
		}
}