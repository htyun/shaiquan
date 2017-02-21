package jxnu.n433.x3107.SunGroup;

/*
 * 
 * 学校信息表
 * （学校id-主键，学校名称，学校所在省，学校所在市，学校所在区（县））
 * 
 * */

public class School {
	public static final String SCHOOLID = "schoolID";//学校ID
	public static final String SCHOOLNAME = "schoolName";//学校名称
	public static final String SCHOOLPROVINCE = "schoolProvince";//学校所在省
	public static final String SCHOOLCITY = "schoolCity";//学校所在市
	public static final String SCHOOLAREA = "schoolArea";//学校所在区（县）
	public static final String SCHLLOPW = "schoolPw";//学号密码
	public static final String SCHOOLNUMBER = "schoolNumber";//学号
	
	private int schoolID;
	private String schoolName;
	private String schoolProvince;
	private String schoolCity;
	private String schoolArea;
	private String schoolPw;
	private String schoolNumber;
	
	public String getSchoolPw() {
		return schoolPw;
	}
	public void setSchoolPw(String schoolPw) {
		this.schoolPw = schoolPw;
	}
	public void setSchoolNumber(String schoolNumber) {
		this.schoolNumber = schoolNumber;
	}
	public String getSchoolNumber() {
		return schoolNumber;
	}
	
	public int getSchoolID() {
		return schoolID;
	}
	public void setSchoolID(int schoolID) {
		this.schoolID = schoolID;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolProvince() {
		return schoolProvince;
	}
	public void setSchoolProvince(String schoolProvince) {
		this.schoolProvince = schoolProvince;
	}
	public String getSchoolCity() {
		return schoolCity;
	}
	public void setSchoolCity(String schoolCity) {
		this.schoolCity = schoolCity;
	}
	public String getSchoolArea() {
		return schoolArea;
	}
	public void setSchoolArea(String schoolArea) {
		this.schoolArea = schoolArea;
	}
	
	
	
}
