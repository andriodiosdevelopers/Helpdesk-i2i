package helpdesk.i2i.com.ifazidesk.getset;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Login extends RealmObject{

	@SerializedName("GroupName")
	@Expose
	private String groupName;

	@SerializedName("EmailID")
	@Expose
	private String emailID;

	@SerializedName("IsAdmin")
	@Expose
	private boolean isAdmin;

	@SerializedName("CompanyID")
	@Expose
	private int companyID;

	@SerializedName("Message")
	@Expose
	private String message;

	@SerializedName("ProductId")
	@Expose
	private int productId;

	@SerializedName("RoleID")
	@Expose
	private int roleID;

	@SerializedName("GroupID")
	@Expose
	private int groupID;

	@SerializedName("isController")
	@Expose
	private boolean isController;

	@SerializedName("CompanyName")
	@Expose
	private String companyName;

	@SerializedName("ContactNo")
	@Expose
	private String contactNo;

	@SerializedName("LanguageCode")
	@Expose
	private String languageCode;

	@SerializedName("Language")
	@Expose
	private String language;

	@SerializedName("companydetails")
	@Expose
	private RealmList<CompanydetailsItem> companydetails;

	@SerializedName("locationdetails")
	@Expose
	private RealmList<LocationdetailsItem> locationdetails;

	@SerializedName("buildingdetails")
	@Expose
	private RealmList<BuildingdetailsItem> buildingdetails;

	@SerializedName("floordetails")
	@Expose
	private RealmList<FloordetailsItem> floordetails;

	@SerializedName("wingdetails")
	@Expose
	private RealmList<WingdetailsItem> wingdetails;

	@PrimaryKey
	@SerializedName("UserID")
	@Expose
	private int userID;

	@SerializedName("isServiceEngineer")
	@Expose
	private boolean isServiceEngineer;

	@SerializedName("Status")
	@Expose
	private boolean status;

	@SerializedName("isEmployee")
	@Expose
	private boolean isEmployee;

	@SerializedName("FirstName")
	@Expose
	private String firstName;


	@SerializedName("isResTimeReq")
	@Expose
	private boolean isResTimeReq;

	@SerializedName("logopath")
	@Expose
	private String logopath;


	public void setGroupName(String groupName){
		this.groupName = groupName;
	}

	public String getGroupName(){
		return groupName;
	}

	public void setEmailID(String emailID){
		this.emailID = emailID;
	}

	public String getEmailID(){
		return emailID;
	}

	public void setIsAdmin(boolean isAdmin){
		this.isAdmin = isAdmin;
	}

	public boolean isIsAdmin(){
		return isAdmin;
	}

	public void setCompanyID(int companyID){
		this.companyID = companyID;
	}

	public int getCompanyID(){
		return companyID;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	public int getProductId(){
		return productId;
	}

	public void setRoleID(int roleID){
		this.roleID = roleID;
	}

	public int getRoleID(){
		return roleID;
	}

	public void setGroupID(int groupID){
		this.groupID = groupID;
	}

	public int getGroupID(){
		return groupID;
	}

	public void setIsController(boolean isController){
		this.isController = isController;
	}

	public boolean isIsController(){
		return isController;
	}

	public void setCompanydetails(RealmList<CompanydetailsItem> companydetails){
		this.companydetails = companydetails;
	}

	public RealmList<CompanydetailsItem> getCompanydetails(){
		return companydetails;
	}

	public void setLocationdetails(RealmList<LocationdetailsItem> locationdetails){
		this.locationdetails = locationdetails;
	}

	public RealmList<LocationdetailsItem> getLocationdetails(){
		return locationdetails;
	}
	public void setBuildingdetails(RealmList<BuildingdetailsItem> buildingdetails){
		this.buildingdetails = buildingdetails;
	}

	public RealmList<BuildingdetailsItem> getBuildingdetails(){
		return buildingdetails;
	}

	public void setFloordetails(RealmList<FloordetailsItem> floordetails){
		this.floordetails = floordetails;
	}

	public RealmList<FloordetailsItem> getFloordetails(){
		return floordetails;
	}

	public void setWingdetails(RealmList<WingdetailsItem> wingdetails){
		this.wingdetails = wingdetails;
	}

	public RealmList<WingdetailsItem> getWingdetails(){
		return wingdetails;
	}


	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}

	public String getCompanyName(){
		return companyName;
	}

	public void setContactNo(String contactNo){
		this.contactNo = contactNo;
	}

	public String getContactNo(){
		return contactNo;
	}

	public void setLanguageCode(String languageCode){
		this.languageCode = languageCode;
	}

	public String getLanguageCode(){
		return languageCode;
	}

	public void setLanguage(String language){
		this.language = language;
	}

	public String getLanguage(){
		return language;
	}




	public void setUserID(int userID){
		this.userID = userID;
	}

	public int getUserID(){
		return userID;
	}

	public void setIsServiceEngineer(boolean isServiceEngineer){
		this.isServiceEngineer = isServiceEngineer;
	}

	public boolean isIsServiceEngineer(){
		return isServiceEngineer;
	}



	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public void setIsEmployee(boolean isEmployee){
		this.isEmployee = isEmployee;
	}

	public boolean isIsEmployee(){
		return isEmployee;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}



	public void setIsResTimeReq(boolean isResTimeReq){
		this.isResTimeReq = isResTimeReq;
	}

	public boolean isIsResTimeReq(){
		return isResTimeReq;
	}

	public void setLogopath(String logopath){
		this.logopath = logopath;
	}

	public String getLogopath(){
		return logopath;
	}

	@Override
 	public String toString(){
		return
			"Login{" +
			"groupName = '" + groupName + '\'' +
			",emailID = '" + emailID + '\'' +
			",isAdmin = '" + isAdmin + '\'' +
			",companyID = '" + companyID + '\'' +
			",message = '" + message + '\'' +
			",productId = '" + productId + '\'' +
			",roleID = '" + roleID + '\'' +
			",groupID = '" + groupID + '\'' +
			",isController = '" + isController + '\'' +
			",locationdetails = '" + locationdetails + '\'' +
			",floordetails = '" + floordetails + '\'' +
			",companyName = '" + companyName + '\'' +
			",contactNo = '" + contactNo + '\'' +
			",languageCode = '" + languageCode + '\'' +
			",language = '" + language + '\'' +
			",buildingdetails = '" + buildingdetails + '\'' +
			",userID = '" + userID + '\'' +
			",isServiceEngineer = '" + isServiceEngineer + '\'' +
			",wingdetails = '" + wingdetails + '\'' +
			",status = '" + status + '\'' +
			",isEmployee = '" + isEmployee + '\'' +
			",firstName = '" + firstName + '\'' +
			",companydetails = '" + companydetails + '\'' +
			",isResTimeReq = '" + isResTimeReq + '\'' +
			",logopath = '" + logopath + '\'' +
			"}";
		}
}