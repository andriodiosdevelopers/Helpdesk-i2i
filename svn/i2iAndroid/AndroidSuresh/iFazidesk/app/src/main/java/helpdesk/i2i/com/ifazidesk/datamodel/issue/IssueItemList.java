package helpdesk.i2i.com.ifazidesk.datamodel.issue;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class IssueItemList {

	@SerializedName("Status")
	private boolean status;

	@SerializedName("lstIssue")
	private List<IssueItem> lstIssue;

	@SerializedName("Message")
	private String message;

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public void setLstIssue(List<IssueItem> lstIssue){
		this.lstIssue = lstIssue;
	}

	public List<IssueItem> getLstIssue(){
		return lstIssue;
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
			"IssueItemList{" +
			"status = '" + status + '\'' + 
			",lstIssue = '" + lstIssue + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}