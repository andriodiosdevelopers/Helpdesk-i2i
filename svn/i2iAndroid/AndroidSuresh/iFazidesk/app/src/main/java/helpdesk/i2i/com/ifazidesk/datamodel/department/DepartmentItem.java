package helpdesk.i2i.com.ifazidesk.datamodel.department;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DepartmentItem implements Serializable {

	@SerializedName("DepartmentLogoFileName")
	private String departmentLogoFileName;

	@SerializedName("DepartmentName")
	private String departmentName;

	@SerializedName("DepartmentID")
	private int departmentID;

	@SerializedName("DepartmentDescription")
	private String departmentDescription;

	public void setDepartmentLogoFileName(String departmentLogoFileName){
		this.departmentLogoFileName = departmentLogoFileName;
	}

	public String getDepartmentLogoFileName(){
		return departmentLogoFileName;
	}

	public void setDepartmentName(String departmentName){
		this.departmentName = departmentName;
	}

	public String getDepartmentName(){
		return departmentName;
	}

	public void setDepartmentID(int departmentID){
		this.departmentID = departmentID;
	}

	public int getDepartmentID(){
		return departmentID;
	}

	public void setDepartmentDescription(String departmentDescription){
		this.departmentDescription = departmentDescription;
	}

	public String getDepartmentDescription(){
		return departmentDescription;
	}

	@Override
 	public String toString(){
		return 
			"DepartmentItem{" +
			"departmentLogoFileName = '" + departmentLogoFileName + '\'' + 
			",departmentName = '" + departmentName + '\'' + 
			",departmentID = '" + departmentID + '\'' + 
			",departmentDescription = '" + departmentDescription + '\'' + 
			"}";
		}
}