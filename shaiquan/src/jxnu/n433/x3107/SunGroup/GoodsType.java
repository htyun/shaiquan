package jxnu.n433.x3107.SunGroup;

/*
 * 
 * 商品种类表
 * （种类id-主键，
 * 种类名称，
 * 种类信息）
 * 
 */

public class GoodsType {

	public static final String _ID = "_id";
	public static final String  RELEASETIME = "releaseTime";//发布时间
	public static final String  RELEASERID = "releaserID";//发布者ID
	public static final String USERID = "userID";//收藏者
	
	private int _id;
	private String releaserID;
	private String releaseTime;
	private String userID;
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getReleaserID() {
		return releaserID;
	}
	public void setReleaserID(String releaserID) {
		this.releaserID = releaserID;
	}
	public String getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	
	
}
