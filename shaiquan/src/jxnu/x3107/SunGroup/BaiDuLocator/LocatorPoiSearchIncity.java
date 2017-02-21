package jxnu.x3107.SunGroup.BaiDuLocator;


import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.x3107.Fragment.HomePageFragment;
import jxnu.x3107.Fragment.View.Logger;
import jxnu.x3107.SunGroup.LocatorActivity;
import jxnu.x3107.SunGroup.MainActivity;
import jxnu.x3107.SunGroup.WelComeActivity;
import jxnu.x3107.utils.Utils;

/**
 * 城市内搜索
 * 
 * @author h
 * 
 */
public class LocatorPoiSearchIncity extends BaseActivity implements OnClickListener{
	private PoiSearch poiSearch;
	private int currentPageIndex = 0;
	//	private final static String CITY = "中国";
	private String keyword;


	private SharedPreferences sPreferences;

	//	public static String locatorCity;
	//	public static String locatorKeyWord;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		sPreferences = getSharedPreferences(WelComeActivity.USERNAME,0);

		Intent intent = getIntent();
		keyword = intent.getStringExtra("keyword")+"";

		Logger.e(keyword+"keyword");

		poiSearch = PoiSearch.newInstance();
		poiSearch.setOnGetPoiSearchResultListener(new MyListener());
		search();

		locatorBack.setOnClickListener(this);

	}

	private void search() {
		PoiCitySearchOption cityOption = new PoiCitySearchOption();
		cityOption.city("中国");
		cityOption.keyword(keyword);
		cityOption.pageNum(currentPageIndex);
		poiSearch.searchInCity(cityOption);
	}

	class MyListener implements OnGetPoiSearchResultListener {

		@Override
		public void onGetPoiDetailResult(PoiDetailResult result) {

		}

		@Override
		public void onGetPoiResult(PoiResult result) {
			if (result == null
					|| SearchResult.ERRORNO.RESULT_NOT_FOUND == result.error) {
				Utils.showToast(getApplicationContext(), "未搜索到结果");
				return;
			}
			String text = "共" + result.getTotalPageNum() + "页，共"
					+ result.getTotalPoiNum() + "条，当前第"
					+ result.getCurrentPageNum() + 1 + "页，当前页"
					+ result.getCurrentPageCapacity() + "条";

			Utils.showToast(getApplicationContext(), text);
			baiduMap.clear();// 清空地图所有的 Overlay 覆盖物以及 InfoWindow
			PoiOverlay overlay = new MyPoiOverlay(baiduMap);// 搜索poi的覆盖物
			baiduMap.setOnMarkerClickListener(overlay);// 把事件分发给overlay，overlay才能处理点击事件
			overlay.setData(result);// 设置结果

			overlay.addToMap();// 把搜索的结果添加到地图中
			overlay.zoomToSpan();// 缩放地图，使所有Overlay都在合适的视野内 注：
			// 该方法只对Marker类型的overlay有效


		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_1){
			currentPageIndex++;
			search();
		}
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intentBack = new Intent(LocatorPoiSearchIncity.this,LocatorActivity.class);
			startActivity(intentBack);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap arg0) {
			super(arg0);
		}

		@Override
		public boolean onPoiClick(int index) {
			PoiResult poiResult = getPoiResult();
			PoiInfo poiInfo = poiResult.getAllPoi().get(index);// 得到点击的那个poi信息
			String text = poiInfo.name + "," + poiInfo.address;
			//			Toast.makeText(getApplicationContext(), text, 0).show();
			HomePageFragment.setStrLocator(text);

			Editor editor = sPreferences.edit();
			editor.putString("locator", text);
			editor.commit();


			Intent intent = new Intent(LocatorPoiSearchIncity.this,MainActivity.class);
			startActivity(intent);
			finish();

			return super.onPoiClick(index);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.locator_bai_du_left_iv_back:

			Intent intentBack = new Intent(LocatorPoiSearchIncity.this,LocatorActivity.class);
			startActivity(intentBack);
			finish();

			break;

		default:
			break;
		}
	}
	
	
}
