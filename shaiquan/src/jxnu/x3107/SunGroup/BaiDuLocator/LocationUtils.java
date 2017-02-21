package jxnu.x3107.SunGroup.BaiDuLocator;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import android.app.Application;
import android.content.Intent;
import jxnu.x3107.SunGroup.LocatorActivity;

public class LocationUtils extends Application{
	
	public  LocationClient mLocationClient = null;
	public  GeofenceClient mGeofenceClient;
	public  MyLocationListenner myListener = new MyLocationListenner();
	public static String TAG = "LocationUtils";
	
	private static LocationUtils mInstance = null;
	
	@Override
	public void onCreate() {
		
		mInstance =  this;
		mLocationClient = new LocationClient(this);
		
		mLocationClient.setAK("Uowa1H04e60u0VCblEe7wap4");
		mLocationClient.registerLocationListener(myListener);
		mGeofenceClient = new GeofenceClient(this);
		
		super.onCreate();
	}
 
	public static LocationUtils getInstance(){
		return mInstance;
	}
	

	/**
	 * 停止定位
	 */
	public void stopLocationClient()
	{
		if (mLocationClient != null && mLocationClient.isStarted())
		{
			mLocationClient.stop();
		} 
	}
	

	/**
	 * 发起定位
	 */
	public void requestLocationInfo()
	{
		setLocationOption();
		
		if (mLocationClient != null && !mLocationClient.isStarted())
		{
			mLocationClient.start();
		}

		if (mLocationClient != null && mLocationClient.isStarted())
		{
			mLocationClient.requestLocation();
		} 
	}
	

	/**
	 *  设置相关参数
	 */
	private void setLocationOption()
	{
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
//		option.setServiceName("com.baidu.location.service_v2.9");
		option.setPoiExtraInfo(true);
		option.setAddrType("all");
		option.setPoiNumber(10);
		option.disableCache(true);
		mLocationClient.setLocOption(option);
	}
	
	/**
	 * 监听函数，有更新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener
	{
		@Override
		public void onReceiveLocation(BDLocation location)
		{
			if (location == null)
			{
				sendBroadCast("定位失败!");
				return;
			}
			sendBroadCast(location.getAddrStr());
		}

		public void onReceivePoi(BDLocation poiLocation)
		{
			if (poiLocation == null)
			{
				sendBroadCast("定位失败!");
				return;
			}
			sendBroadCast(poiLocation.getAddrStr());
		}
		
	}
	
	/**
	 * 得到发送广播
	 * @param address
	 */
	public void sendBroadCast(String address)
	{
		stopLocationClient();
		
		Intent intent = new Intent(LocatorActivity.LOCATION_BCR);
		intent.putExtra("address", address);
		sendBroadcast(intent);
	}
}
