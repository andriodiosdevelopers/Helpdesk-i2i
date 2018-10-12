package helpdesk.i2i.com.ifazidesk.datamodel.assignee;

import com.google.gson.annotations.SerializedName;

public class LstproviderItem{

	@SerializedName("name")
	private String name;

	@SerializedName("userid")
	private int userid;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setUserid(int userid){
		this.userid = userid;
	}

	public int getUserid(){
		return userid;
	}

	@Override
 	public String toString(){
		return 
			"LstproviderItem{" + 
			"name = '" + name + '\'' + 
			",userid = '" + userid + '\'' + 
			"}";
		}
}