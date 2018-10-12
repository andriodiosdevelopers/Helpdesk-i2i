package helpdesk.i2i.com.ifazidesk.webservice;

/**
 * Created by SSk_A on 07-09-2016.
 */

import android.content.Context;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Enumeration;
import java.util.Hashtable;

public class NetworkCall {
    String SOAPNameSpace = "http://tempuri.org/";
    String SOAPUrl = "http://i2isoftwares.com/JLLHelpDeskWebService/JLLHelpdeskapiDetails.asmx";
    //String SOAPUrl = "http://13.228.143.85/helpdeskapi/JLLHelpdeskapiDetails.asmx";
    int networkTimeOut = 90 * 1000;// 90 seconds
    Context mContext;

    public NetworkCall(Context context) {
        mContext = context;
    }

    // Get SOAP Action Name
    public String getSoapAction(String method) {
        return "\"" + SOAPNameSpace + method + "\"";
    }

    public String postDataToSOAPService(Hashtable<String, String> hashTable,
                                        String methodName) throws Exception {

        //Create a SOAP Object.
        SoapObject request = new SoapObject(SOAPNameSpace, methodName);

        //HashTable Enumeration for passing number of params along with there keys
        Enumeration<String> enumer = hashTable.keys();
        while (enumer.hasMoreElements()) {
            String key = (String) enumer.nextElement();
            request.addProperty(key, hashTable.get(key));
        }

        //Create a serializable Object.
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        //SOAP is implemented in dotNet true/false.
        envelope.dotNet = true;

        //Set request data into envelope and send request using HttpTransport
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAPUrl);
        androidHttpTransport.call(getSoapAction(methodName), envelope);
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        Log.i("DashboardData",response+" response:"+response.toString());
        String data = response.toString();
        //Get DashboardData Data in form of SOAPObject
        //SoapObject result = (SoapObject) envelope.bodyIn;


        //Free all the object after use for better performance
        request = null;
        envelope = null;
        androidHttpTransport = null;

        //Return Result DashboardData Data
        return response.toString();
    }


}