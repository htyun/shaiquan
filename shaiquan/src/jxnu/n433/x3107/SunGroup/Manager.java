package jxnu.n433.x3107.SunGroup;

/*
 * 
 * 管理员信息表（管理员id-主键，密码，邮箱，头像）
 * 
 * */

public class Manager {
	public static final String MANAGERID = "managerID";//管理员ID
	public static final String MANAGERPW = "managerPw";//管理员密码
	public static final String MANAGERNAME = "managerName";//管理员名称
	public static final String MANAGEREMAIL = "managerEmail";//管理员邮箱
	public static final String MANAGERLOGO = "managerLogo";//管理员头像
	
	private int managerID;
	private String managerPw;
	private String managerName;
	private String managerEmail;
	private String managerLogo;
	
	public int getManagerID() {
		return managerID;
	}
	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}
	public String getManagerPw() {
		return managerPw;
	}
	public void setManagerPw(String managerPw) {
		this.managerPw = managerPw;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerEmail() {
		return managerEmail;
	}
	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}
	public String getManagerLogo() {
		return managerLogo;
	}
	public void setManagerLogo(String managerLogo) {
		this.managerLogo = managerLogo;
	}
	
	
}
