package jxnu.n433.x3107.SunGroup;
/*关注*/
public class Follow {
	public static final String _ID = "_id";
	public static final String  RELEASERID = "releaserID";//发布者ID
	public static final String USERID = "userID";//收藏者
	
	private String releaserID;
	private String userID;
	private int _id;
	
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
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
}
