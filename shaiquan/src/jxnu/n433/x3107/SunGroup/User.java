package jxnu.n433.x3107.SunGroup;


/*
 * 
 * （1）用户信息表
 * （用户id—主键，
 * 用户名，
 * 头像，
 * 用户类型，
 * 密码，
 * 学校，
 * 学号，
 * 班级，
 * 性别，
 * 出生日期，
 * 个人简介）
 * 
 * */

public class User {
	public static final String USERID = "userID";//用户ID
	public static final String USERNAME = "userName";//用户名
	public static final String USERPW = "userPw";//密码
	public static final String USERSEX = "userSex";//性别
	public static final String SCHLID = "schlID";//用户所在的学校ID
	public static final String STUNO = "stuNo";//学号
	public static final String USERLOGO = "userLogo";//头像
	public static final String BIRTH = "birth";//出生年月
	public static final String AGE = "age";//年龄
	public static final String USEREMAIL = "userEmail";//邮箱
	public static final String INTRODUCTION = "introduction";//个人简介
	public static final String USERQQ = "userQQ";//qq
	public static final String USERTEL = "userTel";
	public static final String STUNAME = "stuName";
	public static final String USERCITY = "userCity";//地址
	
	private int userID;
	private String userName;
	private String userPw;
	private String userSex;
	private int schlID;
	private String stuNo;
	private String userLogo;
	private String birth;
	private String age;
	private String userEmail;
	private String introduction;
	private String qq;
	private String userTel;
	private String stuName;
	private String userCity;
	
	
	public String getUserCity() {
		return userCity;
	}
	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public int getSchlID() {
		return schlID;
	}
	public void setSchlID(int schlID) {
		this.schlID = schlID;
	}
	public String getStuNo() {
		return stuNo;
	}
	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
	public String getUserLogo() {
		return userLogo;
	}
	public void setUserLogo(String userLogo) {
		this.userLogo = userLogo;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	
	
	
}

