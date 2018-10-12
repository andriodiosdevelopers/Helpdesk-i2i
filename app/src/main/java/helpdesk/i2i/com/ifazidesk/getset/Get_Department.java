package helpdesk.i2i.com.ifazidesk.getset;

public class Get_Department {

    private String DeptName;
    private String DeptID;
    private String DeptDesc;
    private String Image;


    //DeptName
    public String getDeptName() {
        return DeptName;
    }

    public String setDeptName(String DeptName) {
        return this.DeptName = DeptName;
    }

    //Image
    public String getImage() {
        return Image;
    }

    public String setImage(String Image) {
        return this.Image = Image;
    }

    //DeptID
    public String getDeptID() {
        return DeptID;
    }

    public String setDeptID(String DeptID) {
        return this.DeptID = DeptID;
    }

    //DeptDesc
    public String getDeptDesc() {
        return DeptDesc;
    }

    public String setDeptDesc(String DeptDesc) {
        return this.DeptDesc = DeptDesc;
    }


}
