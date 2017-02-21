package jxnu.x3107.bean;

public class LocatorAnDSeek {

	public String Seek;//搜索记录
	public String Locator;//定位输入记录
	private String id ="";
	
	public String getId() {
		return id;
	}
	
	public LocatorAnDSeek setId(String id) {
		this.id = id;
		return this;
	}
	
	public LocatorAnDSeek setSeek(String seek) {
		this.Seek = seek;
		return this;
	}
	
	public String getSeek() {
		return Seek;
	}
	
	public LocatorAnDSeek setLocator(String locator) {
		this.Locator = locator;
		return this;
	}
	public String getLocator() {
		return Locator;
	}
	
}
