package helpdesk.i2i.com.ifazidesk.getset;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


public class CompanydetailsItem extends RealmObject{

	@SerializedName("Company")
	private String company;

	@SerializedName("CompanyId")
	private int companyId;

	public void setCompany(String company){
		this.company = company;
	}

	public String getCompany(){
		return company;
	}

	public void setCompanyId(int companyId){
		this.companyId = companyId;
	}

	public int getCompanyId(){
		return companyId;
	}

	@Override
 	public String toString(){
		return 
			"CompanydetailsItem{" + 
			"company = '" + company + '\'' + 
			",companyId = '" + companyId + '\'' + 
			"}";
		}
}