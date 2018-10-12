package helpdesk.i2i.com.ifazidesk.webservice;

import android.app.Activity;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebService extends Activity {

    private static String NAMESPACE = "http://tempuri.org/";


    private static String URL = "http://i2isoftwares.com/JLLHelpDeskWebService/JLLHelpdeskapiDetails.asmx";
    //private static String URL = "http://13.228.143.85/helpdeskapi/JLLHelpdeskapiDetails.asmx";

    //CheckLoginDetails
    private static String METHOD_LOGIN = "GetLoginDetails";
    private static String SOAP_LOGIN = "http://tempuri.org/GetLoginDetails";

    //GetBarCodeDetails
    private static String METHOD_BARCODE = "GetBarCodeDetails";
    private static String SOAP_BARCODE = "http://tempuri.org/GetBarCodeDetails";


    //GetIssueDept
    private static String METHOD_ISSUESDEPT = "GetDepartmentDetails";
    private static String SOAP_ISSUESDEPT = "http://tempuri.org/GetDepartmentDetails";

    //GetIssueLocation
    private static String METHOD_ISSUESLOCATION = "GetLocationDetails";
    private static String SOAP_ISSUESLOCATION = "http://tempuri.org/GetLocationDetails";

    //GetIssueBuilding
    private static String METHOD_ISSUESBUILDING = "GetBuildingDetails";
    private static String SOAP_ISSUESBUILDING = "http://tempuri.org/GetBuildingDetails";

    //GetIssueFloor
    private static String METHOD_ISSUESFLOOR = "GetFloorDetails";
    private static String SOAP_ISSUESFLOOR = "http://tempuri.org/GetFloorDetails";

    //GetIssueWing
    private static String METHOD_ISSUESWING = "GetWingDetails";
    private static String SOAP_ISSUESWING = "http://tempuri.org/GetWingDetails";

    //GetIssueDetails
    private static String METHOD_ISSUES = "GetIssueDetails";
    private static String SOAP_ISSUES = "http://tempuri.org/GetIssueDetails";

   /* //InsertCompliants
    private static String METHOD_ISSUESINSERT = "InsertCompliants";
    private static String SOAP_ISSUESINSERT = "http://tempuri.org/InsertCompliants";*/
    //InsertCompliantsNew
    private static String METHOD_ISSUESINSERT = "InsertCompliantsNew";
    private static String SOAP_ISSUESINSERT = "http://tempuri.org/InsertCompliantsNew";

    //GetRequestType
    private static String METHOD_REQUEST_TYPE = "GetRequestType";
    private static String SOAP_REQUEST_TYPE = "http://tempuri.org/GetRequestType";


    private static String METHOD_Mail = "SendMailMessage";
    private static String SOAP_Mail = "http://tempuri.org/SendMailMessage";


    //CheckLoginDetails
    public static String invokeLoginWS(String username, String password) {
        String company = "";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_LOGIN);

        PropertyInfo unamePI = new PropertyInfo();
        PropertyInfo passPI = new PropertyInfo();

        unamePI.setName("username");
        unamePI.setValue(username);
        unamePI.setType(String.class);
        request.addProperty(unamePI);

        passPI.setName("password");
        passPI.setValue(password);
        passPI.setType(String.class);
        request.addProperty(passPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        Log.i("Login data sent", "" + username + " " + password);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_LOGIN, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            company = response.toString();
            Log.i("Login OutPut", "" + company);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return company;
    }

    public static String GetDetailsByBarCode(int CompanyID, String Barcode) {
        String company = "";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_BARCODE);

        PropertyInfo companyIDPI = new PropertyInfo();
        PropertyInfo barcodePI = new PropertyInfo();

        companyIDPI.setName("CompanyID");
        companyIDPI.setValue(CompanyID);
        companyIDPI.setType(String.class);
        request.addProperty(companyIDPI);

        barcodePI.setName("Barcode");
        barcodePI.setValue(Barcode);
        barcodePI.setType(String.class);
        request.addProperty(barcodePI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        Log.i("Bar Code sent", "Company ID:" + CompanyID + " Barcode:" + Barcode);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_BARCODE, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            company = response.toString();
            Log.i("Login OutPut", "" + company);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return company;
    }


    public static String GetRequestType(int companyid, int roleid) {
        String request_type = "";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_REQUEST_TYPE);

        PropertyInfo companyIDPI = new PropertyInfo();
        PropertyInfo roleIDPI = new PropertyInfo();

        companyIDPI.setName("companyid");
        companyIDPI.setValue(companyid);
        companyIDPI.setType(String.class);
        request.addProperty(companyIDPI);

        roleIDPI.setName("roleid");
        roleIDPI.setValue(roleid);
        roleIDPI.setType(String.class);
        request.addProperty(roleIDPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        Log.i("COMP/ROLE ID sent", "companyid:" + companyid + " roleid:" + roleid);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_REQUEST_TYPE, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            request_type = response.toString();
            Log.i("Issues OutPut", "" + request_type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return request_type;
    }

    public static String GetIssues(int CompanyID, int DepartmentID) {
        String company = "";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_ISSUES);

        PropertyInfo companyIDPI = new PropertyInfo();
        PropertyInfo deptIDPI = new PropertyInfo();

        companyIDPI.setName("CompanyID");
        companyIDPI.setValue(CompanyID);
        companyIDPI.setType(String.class);
        request.addProperty(companyIDPI);

        deptIDPI.setName("DepartmentID");
        deptIDPI.setValue(DepartmentID);
        deptIDPI.setType(String.class);
        request.addProperty(deptIDPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        Log.i("COMP/DEPT ID sent", "CompanyID:" + CompanyID + " DeptID:" + DepartmentID);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ISSUES, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            company = response.toString();
            Log.i("Issues OutPut", "" + company);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return company;
    }

    public static String GetDeptIssues(int CompanyID) {
        String company = "";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_ISSUESDEPT);

        PropertyInfo companyIDPI = new PropertyInfo();

        companyIDPI.setName("CompanyID");
        companyIDPI.setValue(CompanyID);
        companyIDPI.setType(String.class);
        request.addProperty(companyIDPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        Log.i("COMP", "CompanyID:" + CompanyID);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ISSUESDEPT, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            company = response.toString();
            Log.i("Dept OutPut", "" + company);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return company;
    }


    public static String GetLocationIssues(int CompanyID) {
        String location = "";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_ISSUESLOCATION);

        PropertyInfo companyIDPI = new PropertyInfo();

        companyIDPI.setName("CompanyID");
        companyIDPI.setValue(CompanyID);
        companyIDPI.setType(String.class);
        request.addProperty(companyIDPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        Log.i("COMP/DEPT ID sent", "CompanyID:" + CompanyID);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ISSUESLOCATION, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            location = response.toString();
            Log.i("Issues Location OutPut", "" + location);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }


    public static String GetBuildingIssues(int CompanyID, int LocationID) {
        String building = "";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_ISSUESBUILDING);

        PropertyInfo companyIDPI = new PropertyInfo();
        PropertyInfo locationIDPI = new PropertyInfo();

        companyIDPI.setName("CompanyID");
        companyIDPI.setValue(CompanyID);
        companyIDPI.setType(String.class);
        request.addProperty(companyIDPI);

        locationIDPI.setName("LocationID");
        locationIDPI.setValue(LocationID);
        locationIDPI.setType(String.class);
        request.addProperty(locationIDPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        Log.i("COMP/LOC ID sent", "CompanyID:" + CompanyID + " LocID:" + LocationID);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ISSUESBUILDING, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            building = response.toString();
            Log.i("Issues Building OutPut", "" + building);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return building;
    }


    public static String GetFloorIssues(int CompanyID, int BuildingID) {
        String floor = "";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_ISSUESFLOOR);

        PropertyInfo companyIDPI = new PropertyInfo();
        PropertyInfo buildingIDPI = new PropertyInfo();

        companyIDPI.setName("CompanyID");
        companyIDPI.setValue(CompanyID);
        companyIDPI.setType(String.class);
        request.addProperty(companyIDPI);

        buildingIDPI.setName("BuildingID");
        buildingIDPI.setValue(BuildingID);
        buildingIDPI.setType(String.class);
        request.addProperty(buildingIDPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        Log.i("COMP/DEPT ID sent", "CompanyID:" + CompanyID + " BuildingID:" + BuildingID);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ISSUESFLOOR, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            floor = response.toString();
            Log.i("Issues Floor OutPut", "" + floor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return floor;
    }


    public static String GetWingIssues(int CompanyID, int FloorID) {
        String wing = "";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_ISSUESWING);

        PropertyInfo companyIDPI = new PropertyInfo();
        PropertyInfo floorIDPI = new PropertyInfo();

        companyIDPI.setName("CompanyID");
        companyIDPI.setValue(CompanyID);
        companyIDPI.setType(String.class);
        request.addProperty(companyIDPI);

        floorIDPI.setName("FloorID");
        floorIDPI.setValue(FloorID);
        floorIDPI.setType(String.class);
        request.addProperty(floorIDPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        Log.i("COMP/DEPT ID sent", "CompanyID:" + CompanyID + " FloorID:" + FloorID);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ISSUESWING, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            wing = response.toString();
            Log.i("Issues Wing OutPut", "" + wing);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wing;
    }
    /*
	 <CompanyID>long</CompanyID>
     <GroupID>long</GroupID>
     <LocationID>long</LocationID>
     <BuildingID>long</BuildingID>
     <FloorID>long</FloorID>
     <WingID>long</WingID>
     <DepartmentID>long</DepartmentID>
     <IssueID>long</IssueID>
     <Description>string</Description>
     <PriorityID>long</PriorityID>
     <ResponseTime>string</ResponseTime>
     <UserID>long</UserID>
     <UserMailID>string</UserMailID>
     <UserMobileNo>string</UserMobileNo>
     <UserName>string</UserName>
	 */
	
	 /* 
	  <CompanyID>long</CompanyID>
      <LocationID>long</LocationID>
      <BuildingID>long</BuildingID>
      <FloorID>long</FloorID>
      <WingID>long</WingID>
      <DepartmentID>long</DepartmentID>
      <IssueID>long</IssueID>
      <Description>string</Description>
      <PriorityID>long</PriorityID>
      <ResponseTime>string</ResponseTime>
      <UserID>long</UserID>
      <RequestType>int</RequestType>*/

    public static String SumbitIssues(int CompanyID, int GroupID,
                                      int LocationID, int BuildingID,
                                      int FloorID, int WingID,
                                      int DepartmentID, int IssueID,
                                      String Description, int PriorityID,
                                      String ResponseTime, int UserID,
                                      int RequestType, String RequesterName) {
        String company = "";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_ISSUESINSERT);

        PropertyInfo companyIDPI = new PropertyInfo();
        PropertyInfo groupIDPI = new PropertyInfo();
        PropertyInfo locationIDPI = new PropertyInfo();
        PropertyInfo buildingIDPI = new PropertyInfo();
        PropertyInfo floorIDPI = new PropertyInfo();
        PropertyInfo wingIDPI = new PropertyInfo();
        PropertyInfo deptIDPI = new PropertyInfo();
        PropertyInfo issueIDPI = new PropertyInfo();
        PropertyInfo descriptionPI = new PropertyInfo();
        PropertyInfo priorityIDPI = new PropertyInfo();
        PropertyInfo responsetimePI = new PropertyInfo();
        PropertyInfo userIDPI = new PropertyInfo();
        PropertyInfo RTIDPI = new PropertyInfo();
        PropertyInfo RequesterName_PI = new PropertyInfo();
		/*PropertyInfo usermailidPI = new PropertyInfo();
		PropertyInfo usermoblnoPI = new PropertyInfo();
		PropertyInfo usernamePI = new PropertyInfo();*/

        RequesterName_PI.setName("RequesterName");
        RequesterName_PI.setValue(RequesterName);
        RequesterName_PI.setType(String.class);
        request.addProperty(RequesterName_PI);

        RTIDPI.setName("RequestType");
        RTIDPI.setValue(RequestType);
        RTIDPI.setType(Integer.class);
        request.addProperty(RTIDPI);

        companyIDPI.setName("CompanyID");
        companyIDPI.setValue(CompanyID);
        companyIDPI.setType(String.class);
        request.addProperty(companyIDPI);

        groupIDPI.setName("GroupID");
        groupIDPI.setValue(GroupID);
        groupIDPI.setType(String.class);
        request.addProperty(groupIDPI);

        locationIDPI.setName("LocationID");
        locationIDPI.setValue(LocationID);
        locationIDPI.setType(String.class);
        request.addProperty(locationIDPI);

        buildingIDPI.setName("BuildingID");
        buildingIDPI.setValue(BuildingID);
        buildingIDPI.setType(String.class);
        request.addProperty(buildingIDPI);

        floorIDPI.setName("FloorID");
        floorIDPI.setValue(FloorID);
        floorIDPI.setType(String.class);
        request.addProperty(floorIDPI);

        wingIDPI.setName("WingID");
        wingIDPI.setValue(WingID);
        wingIDPI.setType(String.class);
        request.addProperty(wingIDPI);

        deptIDPI.setName("DepartmentID");
        deptIDPI.setValue(DepartmentID);
        deptIDPI.setType(String.class);
        request.addProperty(deptIDPI);

        issueIDPI.setName("IssueID");
        issueIDPI.setValue(IssueID);
        issueIDPI.setType(String.class);
        request.addProperty(issueIDPI);

        descriptionPI.setName("Description");
        descriptionPI.setValue(Description);
        descriptionPI.setType(String.class);
        request.addProperty(descriptionPI);

        priorityIDPI.setName("PriorityID");
        priorityIDPI.setValue(PriorityID);
        priorityIDPI.setType(String.class);
        request.addProperty(priorityIDPI);

        responsetimePI.setName("ResponseTime");
        responsetimePI.setValue(ResponseTime);
        responsetimePI.setType(String.class);
        request.addProperty(responsetimePI);

        //UserID
        userIDPI.setName("UserID");
        userIDPI.setValue(UserID);
        userIDPI.setType(String.class);
        request.addProperty(userIDPI);
		
		/*//UserMailID
		usermailidPI.setName("UserMailID");
		usermailidPI.setValue(UserMailID);
		usermailidPI.setType(String.class);
		request.addProperty(usermailidPI);
		
		//UserMobileNo
		usermoblnoPI.setName("UserMobileNo");
		usermoblnoPI.setValue(UserMobileNo);
		usermoblnoPI.setType(String.class);
		request.addProperty(usermoblnoPI);
		
		
		//UserName
		usernamePI.setName("UserName");
		usernamePI.setValue(UserName);
		usernamePI.setType(String.class);
		request.addProperty(usernamePI);*/


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        Log.i("ISSUE sent", "CompanyID:" + CompanyID +
                " GroupID:" + GroupID +
                " LocationID:" + LocationID +
                " BuildingID:" + BuildingID +
                " FloorID:" + FloorID +
                " WingID:" + WingID +
                " DeptID:" + DepartmentID +
                " IssueID:" + IssueID +
                " Description:" + Description +
                " PriorityID:" + PriorityID +
                " ResponseTime:" + ResponseTime +
                " UserID:" + UserID +
                " RequestType:" + RequestType);
		
		  /*int CompanyID, int GroupID,
		  int LocationID, int BuildingID,
		  int FloorID, int WingID,
		  int DepartmentID, int IssueID,
		  String Description, int PriorityID,
		  String ResponseTime, int UserID,
		  String UserMailID, String UserMobileNo,
		  String UserName*/


        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ISSUESINSERT, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            company = response.toString();
            Log.i("Issues Submit OutPut", "" + company);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return company;
    }
	 
	/*<from>string</from>
    <to>string</to>
    <bcc>string</bcc>
    <cc>string</cc>
    <subject>string</subject>
    <body>string</body>
    <AttachmentPath>string</AttachmentPath>
    <UserName>string</UserName>
    <Password>string</Password>
    <SMTPAddress>string</SMTPAddress>
    <SMTPPort>string</SMTPPort>
    <ToolName>string</ToolName>*/
    
	/*public static String mailWebservice(String from, String to,
										String bcc, String cc,
										String subject,String body, 
										String AttachmentPath, String UserName, 
										String Password, String SMTPAddress, 
										String SMTPPort, String ToolName
										)
	{
		String company = "";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_Mail);

		PropertyInfo fromPI = new PropertyInfo();
		PropertyInfo toPI = new PropertyInfo();
		PropertyInfo bccPI = new PropertyInfo();
		PropertyInfo ccPI = new PropertyInfo();		
		PropertyInfo subjectPI = new PropertyInfo();
		PropertyInfo bodyPI = new PropertyInfo();		
		PropertyInfo attachmentPI = new PropertyInfo();		
		PropertyInfo unamePI = new PropertyInfo();
		PropertyInfo passPI = new PropertyInfo();
		PropertyInfo smtpaddPI = new PropertyInfo();
		PropertyInfo smtpportPI = new PropertyInfo();
		PropertyInfo toolnamePI = new PropertyInfo();
		

		fromPI.setName("from");
		fromPI.setValue(from);
		fromPI.setType(String.class);
		request.addProperty(fromPI);
		

		toPI.setName("to");
		toPI.setValue(to);
		toPI.setType(String.class);
		request.addProperty(toPI);

		bccPI.setName("bcc");
		bccPI.setValue(bcc);
		bccPI.setType(String.class);
		request.addProperty(bccPI);

		ccPI.setName("cc");
		ccPI.setValue(cc);
		ccPI.setType(String.class);
		request.addProperty(ccPI);

		subjectPI.setName("subject");
		subjectPI.setValue(subject);
		subjectPI.setType(String.class);
		request.addProperty(subjectPI);
		
		bodyPI.setName("body");
		bodyPI.setValue(body);
		bodyPI.setType(String.class);
		request.addProperty(bodyPI);

		attachmentPI.setName("AttachmentPath");
		attachmentPI.setValue(AttachmentPath);
		attachmentPI.setType(String.class);
		request.addProperty(attachmentPI);

		unamePI.setName("UserName");
		unamePI.setValue(UserName);
		unamePI.setType(String.class);
		request.addProperty(unamePI);

		passPI.setName("Password");
		passPI.setValue(Password);
		passPI.setType(String.class);
		request.addProperty(passPI);

		
		smtpaddPI.setName("SMTPAddress");
		smtpaddPI.setValue(SMTPAddress);
		smtpaddPI.setType(String.class);
		request.addProperty(smtpaddPI);
		
		smtpportPI.setName("SMTPPort");
		smtpportPI.setValue(SMTPPort);
		smtpportPI.setType(String.class);
		request.addProperty(smtpportPI);
		
		toolnamePI.setName("ToolName");
		toolnamePI.setValue(ToolName);
		toolnamePI.setType(String.class);
		request.addProperty(toolnamePI);
		
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		
		String from, String to,
		String bcc, String cc,
		String subject,String body, 
		String AttachmentPath, String UserName, 
		String Password, String SMTPAddress, 
		String SMTPPort, String ToolName
		Log.i("Mail data sent",""
				+"From : "+from+" To: "+to
				+" BCC : "+bcc+" CC: "+cc
				+" Subject : "+subject+" Body: "+body
				+" AP : "+AttachmentPath+" UserName: "+UserName
				+" Password : "+Password+" SMTPADD : "+SMTPAddress
				+" smtp port : "+SMTPPort+" ToolName : "+ToolName
				);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(MailService);

		try {
			androidHttpTransport.call(SOAP_Mail, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			company = response.toString();
			Log.i("Login OutPut",""+company);	
		} catch (Exception e) {
			e.printStackTrace();
		}

		return company;
	}	*/
}
