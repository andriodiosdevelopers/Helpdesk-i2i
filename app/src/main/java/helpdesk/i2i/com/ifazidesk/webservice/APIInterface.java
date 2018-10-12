package helpdesk.i2i.com.ifazidesk.webservice;
import helpdesk.i2i.com.ifazidesk.datamodel.assignee.AssigneeResponse;
import helpdesk.i2i.com.ifazidesk.datamodel.dashboard.DashboardData;
import helpdesk.i2i.com.ifazidesk.datamodel.department.DepartmentList;
import helpdesk.i2i.com.ifazidesk.datamodel.issue.IssueItemList;
import helpdesk.i2i.com.ifazidesk.datamodel.status.StatusResponse;
import helpdesk.i2i.com.ifazidesk.datamodel.ticketdetails.TicketDetailsResponse;
import helpdesk.i2i.com.ifazidesk.getset.Login;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface APIInterface {
    @GET("GetLoginDetails")
    Call<Login> GetLoginDetails(@Query("username") String username, @Query("password") String password);

    @GET("GetDashboardDetails_Company")
    Call<DashboardData> GetDashboardDetails(@Query("userid") String userid, @Query("TicketDate") String TicketDate, @Query("enddate") String enddate,
                                            @Query("companyid") String companyid, @Query("locationid") String locationid);

    @GET("GetDepartmentDetails")
    Call<DepartmentList> GetDepartmentDetails(@Query("Companyid") String companyid);

    @GET("GetIssueDetails")
    Call<IssueItemList> GetIssueDetails(@Query("Companyid") String companyid, @Query("DepartmentId") String DepartmentId);

    @GET("GetTicketHistory")
    Call<TicketDetailsResponse> GetTicketHistory(@Query("complaintId") String complaintId);

    @GET("GetMappedStatus_RoleMapping")
    Call<StatusResponse> GetStatus(@Query("statusId") String statusId,@Query("companyid") String companyid,@Query("roleid") String roleid);

    @GET("GetAssigneeDetails")
    Call<AssigneeResponse> GetAssignee(@Query("complaintId") String complaintId);


    @GET("GetServiceEngineerTickets_Company")
    Call<DashboardData> GetServiceEngineerTickets_Company(@Query("userid") String userid, @Query("TicketDate") String TicketDate, @Query("enddate") String enddate,
                                            @Query("companyid") String companyid, @Query("locationid") String locationid);
    //@GET("movie/{id}")
    //Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}

