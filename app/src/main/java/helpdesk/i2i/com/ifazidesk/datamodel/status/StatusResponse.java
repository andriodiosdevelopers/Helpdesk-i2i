package helpdesk.i2i.com.ifazidesk.datamodel.status;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class StatusResponse {

	@SerializedName("statusdetails")
	private List<StatusData> statusdetails;

	@SerializedName("Status")
	private boolean status;

	@SerializedName("Message")
	private String message;

	public void setStatusdetails(List<StatusData> statusdetails){
		this.statusdetails = statusdetails;
	}

	public List<StatusData> getStatusdetails(){
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

	@Override
 	public String toString(){
		return 
			"AssigneeResponse{" +
			"statusdetails = '" + statusdetails + '\'' + 
			",status = '" + status + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}