package helpdesk.i2i.com.ifazidesk.getset;

public class Get_Issues {

    private String IssueName;
    private String Priority;
    private String PriorityID;
    private String IssueID;
    private String ResponseTime;
    private String ResolutionTime;
    private String Image;


    //IssueName
    public String getIssueName() {
        return IssueName;
    }

    public String setIssueName(String IssueName) {
        return this.IssueName = IssueName;
    }

    //Image
    public String getImage() {
        return Image;
    }

    public String setImage(String Image) {
        return this.Image = Image;
    }

    //IssueId
    public String getIssueID() {
        return IssueID;
    }

    public String setIssueID(String IssueID) {
        return this.IssueID = IssueID;
    }

    //Priority
    public String getPriority() {
        return Priority;
    }

    public String setPriority(String Priority) {
        return this.Priority = Priority;
    }

    //PriorityID
    public String getPriorityID() {
        return PriorityID;
    }

    public String setPriorityID(String PriorityID) {
        return this.PriorityID = PriorityID;
    }

    //ResponseTime
    public String getResponseTime() {
        return ResponseTime;
    }

    public String setResponseTime(String ResponseTime) {
        return this.ResponseTime = ResponseTime;
    }

    //ResolutionTime
    public String getResolutionTime() {
        return ResolutionTime;
    }

    public String setResolutionTime(String ResolutionTime) {
        return this.ResolutionTime = ResolutionTime;
    }


}
