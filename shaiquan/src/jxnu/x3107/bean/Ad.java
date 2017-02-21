package jxnu.x3107.bean;

public class Ad {

	private int iconResId;
	private String intro;
	
	
	public Ad(int iconResId,String intro) {
		super();
		this.iconResId=iconResId;
		this.intro=intro;
	}
	
	public void setIconResId(int iconResId) {
		this.iconResId = iconResId;
	}
	public int getIconResId() {
		return iconResId;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getIntro() {
		return intro;
	}
	
}
