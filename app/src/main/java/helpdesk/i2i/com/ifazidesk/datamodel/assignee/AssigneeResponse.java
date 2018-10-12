package helpdesk.i2i.com.ifazidesk.datamodel.assignee;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AssigneeResponse {

	@SerializedName("Status")
	private boolean status;

	@SerializedName("lstprovider")
	private List<LstproviderItem> lstprovider;

	@SerializedName("lstassignee")
	private List<LstassigneeItem> lstassignee;

	@SerializedName("Message")
	private String message;

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public void setLstprovider(List<LstproviderItem> lstprovider){
		this.lstprovider = lstprovider;
	}

	public List<LstproviderItem> getLstprovider(){
		return lstprovider;
	}

	public void setLstassignee(List<LstassigneeItem> lstassignee){
		this.lstassignee = lstassignee;
	}

	public List<LstassigneeItem> getLstassignee(){
		return lstassignee;
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
			"Response{" + 
			"status = '" + status + '\'' + 
			",lstprovider = '" + lstprovider + '\'' + 
			",lstassignee = '" + lstassignee + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}