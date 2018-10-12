package helpdesk.i2i.com.ifazidesk.datamodel.dashboard;

import com.google.gson.annotations.SerializedName;

public class TicketdetailsItem {

	@SerializedName("Company")
	private String company;

	@SerializedName("Category")
	private String category;

	@SerializedName("ImageLink")
	private String imageLink;

	@SerializedName("CompanyID")
	private int companyID;

	@SerializedName("isonHold")
	private boolean isonHold;

	@SerializedName("IsClosed")
	private boolean isClosed;

	@SerializedName("building")
	private String building;

	@SerializedName("RequestDate")
	private String requestDate;

	@SerializedName("TicketNo")
	private String ticketNo;

	@SerializedName("Statusid")
	private int statusid;

	@SerializedName("ResponseTime")
	private String responseTime;

	@SerializedName("RequestorName")
	private String requestorName;

	@SerializedName("floor")
	private String floor;

	@SerializedName("Status")
	private String status;

	@SerializedName("StatusCount")
	private int statusCount;

	@SerializedName("Priority")
	private String priority;

	@SerializedName("TicketResponseTime")
	private String ticketResponseTime;

	@SerializedName("lsthistory")
	private Object lsthistory;

	@SerializedName("RequestTime")
	private String requestTime;

	@SerializedName("ColorCode")
	private String colorCode;

	@SerializedName("StatusShortName")
	private String statusShortName;

	@SerializedName("Subject")
	private String subject;

	@SerializedName("AssignedTo")
	private String assignedTo;

	@SerializedName("FileType")
	private String fileType;

	@SerializedName("locationID")
	private int locationID;

	@SerializedName("ComplaintId")
	private int complaintId;

	@SerializedName("location")
	private String location;

	@SerializedName("wing")
	private String wing;

	public void setCompany(String company){
		this.company = company;
	}

	public String getCompany(){
		return company;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return category;
	}

	public void setImageLink(String imageLink){
		this.imageLink = imageLink;
	}

	public String getImageLink(){
		return imageLink;
	}

	public void setCompanyID(int companyID){
		this.companyID = companyID;
	}

	public int getCompanyID(){
		return companyID;
	}

	public void setIsonHold(boolean isonHold){
		this.isonHold = isonHold;
	}

	public boolean isIsonHold(){
		return isonHold;
	}

	public void setIsClosed(boolean isClosed){
		this.isClosed = isClosed;
	}

	public boolean isIsClosed(){
		return isClosed;
	}

	public void setBuilding(String building){
		this.building = building;
	}

	public String getBuilding(){
		return building;
	}

	public void setRequestDate(String requestDate){
		this.requestDate = requestDate;
	}

	public String getRequestDate(){
		return requestDate;
	}

	public void setTicketNo(String ticketNo){
		this.ticketNo = ticketNo;
	}

	public String getTicketNo(){
		return ticketNo;
	}

	public void setStatusid(int statusid){
		this.statusid = statusid;
	}

	public int getStatusid(){
		return statusid;
	}

	public void setResponseTime(String responseTime){
		this.responseTime = responseTime;
	}

	public String getResponseTime(){
		return responseTime;
	}

	public void setRequestorName(String requestorName){
		this.requestorName = requestorName;
	}

	public String getRequestorName(){
		return requestorName;
	}

	public void setFloor(String floor){
		this.floor = floor;
	}

	public String getFloor(){
		return floor;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setStatusCount(int statusCount){
		this.statusCount = statusCount;
	}

	public int getStatusCount(){
		return statusCount;
	}

	public void setPriority(String priority){
		this.priority = priority;
	}

	public String getPriority(){
		return priority;
	}

	public void setTicketResponseTime(String ticketResponseTime){
		this.ticketResponseTime = ticketResponseTime;
	}

	public String getTicketResponseTime(){
		return ticketResponseTime;
	}

	public void setLsthistory(Object lsthistory){
		this.lsthistory = lsthistory;
	}

	public Object getLsthistory(){
		return lsthistory;
	}

	public void setRequestTime(String requestTime){
		this.requestTime = requestTime;
	}

	public String getRequestTime(){
		return requestTime;
	}

	public void setColorCode(String colorCode){
		this.colorCode = colorCode;
	}

	public String getColorCode(){
		return colorCode;
	}

	public void setStatusShortName(String statusShortName){
		this.statusShortName = statusShortName;
	}

	public String getStatusShortName(){
		return statusShortName;
	}

	public void setSubject(String subject){
		this.subject = subject;
	}

	public String getSubject(){
		return subject;
	}

	public void setAssignedTo(String assignedTo){
		this.assignedTo = assignedTo;
	}

	public String getAssignedTo(){
		return assignedTo;
	}

	public void setFileType(String fileType){
		this.fileType = fileType;
	}

	public String getFileType(){
		return fileType;
	}

	public void setLocationID(int locationID){
		this.locationID = locationID;
	}

	public int getLocationID(){
		return locationID;
	}

	public void setComplaintId(int complaintId){
		this.complaintId = complaintId;
	}

	public int getComplaintId(){
		return complaintId;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}

	public void setWing(String wing){
		this.wing = wing;
	}

	public String getWing(){
		return wing;
	}





	@Override
 	public String toString(){
		return 
			"TicketdetailsItem{" + 
			"company = '" + company + '\'' + 
			",category = '" + category + '\'' + 
			",imageLink = '" + imageLink + '\'' + 
			",companyID = '" + companyID + '\'' + 
			",isonHold = '" + isonHold + '\'' + 
			",isClosed = '" + isClosed + '\'' + 
			",building = '" + building + '\'' + 
			",requestDate = '" + requestDate + '\'' + 
			",ticketNo = '" + ticketNo + '\'' + 
			",statusid = '" + statusid + '\'' + 
			",responseTime = '" + responseTime + '\'' + 
			",requestorName = '" + requestorName + '\'' + 
			",floor = '" + floor + '\'' + 
			",status = '" + status + '\'' + 
			",statusCount = '" + statusCount + '\'' + 
			",priority = '" + priority + '\'' + 
			",ticketResponseTime = '" + ticketResponseTime + '\'' + 
			",lsthistory = '" + lsthistory + '\'' + 
			",requestTime = '" + requestTime + '\'' + 
			",colorCode = '" + colorCode + '\'' + 
			",statusShortName = '" + statusShortName + '\'' + 
			",subject = '" + subject + '\'' + 
			",assignedTo = '" + assignedTo + '\'' + 
			",fileType = '" + fileType + '\'' + 
			",locationID = '" + locationID + '\'' + 
			",complaintId = '" + complaintId + '\'' + 
			",location = '" + location + '\'' + 
			",wing = '" + wing + '\'' + 
			"}";
		}
}