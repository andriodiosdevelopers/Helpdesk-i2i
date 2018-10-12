package helpdesk.i2i.com.ifazidesk.datamodel.issue;

import com.google.gson.annotations.SerializedName;

public class IssueItem {

	@SerializedName("CategoryID")
	private int categoryID;

	@SerializedName("ResolutionTime")
	private String resolutionTime;

	@SerializedName("PriorityName")
	private String priorityName;

	@SerializedName("CategoryDescrition")
	private String categoryDescrition;

	@SerializedName("CategoryName")
	private String categoryName;

	@SerializedName("PriorityDescription")
	private String priorityDescription;

	@SerializedName("ResponseTime")
	private String responseTime;

	@SerializedName("DepartmentID")
	private int departmentID;

	@SerializedName("PriorityID")
	private int priorityID;

	@SerializedName("CategoryLogoFileName")
	private String categoryLogoFileName;

	public void setCategoryID(int categoryID){
		this.categoryID = categoryID;
	}

	public int getCategoryID(){
		return categoryID;
	}

	public void setResolutionTime(String resolutionTime){
		this.resolutionTime = resolutionTime;
	}

	public String getResolutionTime(){
		return resolutionTime;
	}

	public void setPriorityName(String priorityName){
		this.priorityName = priorityName;
	}

	public String getPriorityName(){
		return priorityName;
	}

	public void setCategoryDescrition(String categoryDescrition){
		this.categoryDescrition = categoryDescrition;
	}

	public String getCategoryDescrition(){
		return categoryDescrition;
	}

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public void setPriorityDescription(String priorityDescription){
		this.priorityDescription = priorityDescription;
	}

	public String getPriorityDescription(){
		return priorityDescription;
	}

	public void setResponseTime(String responseTime){
		this.responseTime = responseTime;
	}

	public String getResponseTime(){
		return responseTime;
	}

	public void setDepartmentID(int departmentID){
		this.departmentID = departmentID;
	}

	public int getDepartmentID(){
		return departmentID;
	}

	public void setPriorityID(int priorityID){
		this.priorityID = priorityID;
	}

	public int getPriorityID(){
		return priorityID;
	}

	public void setCategoryLogoFileName(String categoryLogoFileName){
		this.categoryLogoFileName = categoryLogoFileName;
	}

	public String getCategoryLogoFileName(){
		return categoryLogoFileName;
	}

	@Override
 	public String toString(){
		return 
			"IssueItem{" +
			"categoryID = '" + categoryID + '\'' + 
			",resolutionTime = '" + resolutionTime + '\'' + 
			",priorityName = '" + priorityName + '\'' + 
			",categoryDescrition = '" + categoryDescrition + '\'' + 
			",categoryName = '" + categoryName + '\'' + 
			",priorityDescription = '" + priorityDescription + '\'' + 
			",responseTime = '" + responseTime + '\'' + 
			",departmentID = '" + departmentID + '\'' + 
			",priorityID = '" + priorityID + '\'' + 
			",categoryLogoFileName = '" + categoryLogoFileName + '\'' + 
			"}";
		}
}