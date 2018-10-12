package helpdesk.i2i.com.ifazidesk.datamodel.ticketdetails;

import com.google.gson.annotations.SerializedName;

public class TicketDetailsResponse {

	@SerializedName("Status")
	private boolean status;

	@SerializedName("Message")
	private String message;

	@SerializedName("ticketdetails")
	private TicketDetailsData ticketDetailsData;

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

	public void setTicketDetailsData(TicketDetailsData ticketDetailsData){
		this.ticketDetailsData = ticketDetailsData;
	}

	public TicketDetailsData getTicketDetailsData(){
		return ticketDetailsData;
	}

	@Override
 	public String toString(){
		return 
			"TicketDetailsResponse{" +
			"status = '" + status + '\'' + 
			",message = '" + message + '\'' + 
			",ticketDetailsData = '" + ticketDetailsData + '\'' +
			"}";
		}
}