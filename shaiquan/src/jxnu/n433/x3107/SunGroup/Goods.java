package jxnu.n433.x3107.SunGroup;

/*
 * 
 * （2）商品信息
 * （商品id—主键，
 * 用户id，
 * 种类id，
 * 商品名称，
 * 商品图片，
 * 商品简介，
 * 商品状态，
 * 历史由来，
 * 发布时间）
 * 
 * */

public class Goods {
	public static final String  GOODID = "goodID";//商品ID
	public static final String  RELEASERID = "releaserID";//发布者ID
	public static final String  GOODNAME = "goodName";//商品名称
	public static final String  GOODLOGO = "goodLogo";//商品图片
	public static final String  PRICE = "price";//商品价格
	public static final String  GOODINTRODUCTION = "goodIntroduction";//商品简介
	public static final String  RELEASETIME = "releaseTime";//发布时间
	public static final String  FROMWHERE = "fromWhere";//商品由来
	public static final String  GOODSTATE = "goodState";//商品状态（1-在售、0-已售、-1-已下架）
	public static final String FROMSCHOOLID = "fromSchoolID";
	public static final String GOODCLASSIFY = "goodClassify";//商品分类(A生活B学习C数码D其他)
	public static final String TIME = "time";
	
	private int goodID;
	private String releaserID;
	private String goodName;
	private String goodLogo;
	private double price;
	private String goodIntroduction;
	private String releaseTime;
	private String fromWhere;
	private int goodState;
	private String fromSchoolID;
	private String goodClassify;
	private String time;
	
	public int getGoodID() {
		return goodID;
	}
	public void setGoodID(int goodID) {
		this.goodID = goodID;
	}
	public String getReleaserID() {
		return releaserID;
	}
	public void setReleaserID(String releaserID) {
		this.releaserID = releaserID;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getGoodLogo() {
		return goodLogo;
	}
	public void setGoodLogo(String goodLogo) {
		this.goodLogo = goodLogo;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getGoodIntroduction() {
		return goodIntroduction;
	}
	public void setGoodIntroduction(String goodIntroduction) {
		this.goodIntroduction = goodIntroduction;
	}
	public String getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getFromWhere() {
		return fromWhere;
	}
	public void setFromWhere(String fromWhere) {
		this.fromWhere = fromWhere;
	}
	public int getGoodState() {
		return goodState;
	}
	public void setGoodState(int goodState) {
		this.goodState = goodState;
	}
	public String getFromSchoolID() {
		return fromSchoolID;
	}
	public void setFromSchoolID(String fromSchoolID) {
		this.fromSchoolID = fromSchoolID;
	}
	public String getGoodClassify() {
		return goodClassify;
	}
	public void setGoodClassify(String goodClassify) {
		this.goodClassify = goodClassify;
	}
	public String getTime() {
		return time;
	}public void setTime(String time) {
		this.time = time;
	}
	
}
