package jxnu.x3107.SunGroup.BaiDuLocator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import jxnu.n433.x3107.SunGroup.R;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

public class BaseActivity extends Activity {
	protected BaiduMap baiduMap;
	protected MapView mapview;
	protected double latitude = 39.542637;// 纬度
	protected double longitude = 116.292334;// 经度
	protected LatLng hmPos = new LatLng(latitude, longitude);
	
	public ImageView locatorBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		initManager();

		setContentView(R.layout.activity_locator_bai_du);

		init();
	}
	private void initManager() {
		SDKInitializer.initialize(getApplicationContext()); // 不能传递Activity，必须是全局Context
	}

	private void init() {
		// 设置地图级别（V2.X 3-19 V1.X 3-18）
		// ① 修改了文件的格式 优化了空间的使用（北京 110M 15M）
		// ② 增加了级别 3D效果（18 19）
		
		locatorBack = (ImageView) findViewById(R.id.locator_bai_du_left_iv_back);

		mapview = (MapView) findViewById(R.id.mapview_id);

		baiduMap = mapview.getMap();
		// BaiduMap: 管理具体的某一个MapView ： 旋转，移动，缩放，事件。。

		// 描述地图状态将要发生的变化 使用工厂类MapStatusUpdateFactory创建
		MapStatusUpdate mapstatusUpdate = MapStatusUpdateFactory.zoomTo(15);// 默认的级别12
		// 设置缩放级别
		baiduMap.setMapStatus(mapstatusUpdate);

		// LatLng latlng = new LatLng(arg0, arg1);// 坐标 经纬度 参数1 纬度 参数2 经度
//		MapStatusUpdate mapstatusUpdatePoint = MapStatusUpdateFactory
//				.newLatLng(hmPos);
		// 设置中心点 默认是天安门
//		baiduMap.setMapStatus(mapstatusUpdatePoint);

		// mapview.showZoomControls(false);// 默认是true 显示缩放按钮
		//
		// mapview.showScaleControl(false);// 默认是true 显示标尺
	}
	@Override
	protected void onDestroy() {
		mapview.onDestroy();
		super.onDestroy();
	}
	@Override
	protected void onResume() {
		mapview.onResume();
		super.onResume();
	}

	@Override
	protected void onPause() {
		mapview.onPause();
		super.onPause();
	}
}
