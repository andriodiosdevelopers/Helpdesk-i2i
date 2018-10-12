package helpdesk.i2i.com.ifazidesk.datamodel.department;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DepartmentList {

	@SerializedName("Status")
	private boolean status;

	@SerializedName("Message")
	private String message;

	@SerializedName("lstDepartment")
	private List<DepartmentItem> lstDepartment;

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

	public void setLstDepartment(List<DepartmentItem> lstDepartment){
		this.lstDepartment = lstDepartment;
	}

	public List<DepartmentItem> getLstDepartment(){
		return lstDepartment;
	}

	@Override
 	public String toString(){
		return 
			"DepartmentList{" +
			"status = '" + status + '\'' + 
			",message = '" + message + '\'' + 
			",lstDepartment = '" + lstDepartment + '\'' + 
			"}";
		}
}