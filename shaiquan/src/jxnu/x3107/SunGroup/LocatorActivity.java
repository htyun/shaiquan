package jxnu.x3107.SunGroup;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.x3107.Adapter.LocatorRecordAdapter;
import jxnu.x3107.Fragment.HomePageFragment;
import jxnu.x3107.SunGroup.BaiDuLocator.LocationUtils;
import jxnu.x3107.SunGroup.BaiDuLocator.LocatorPoiSearchIncity;
import jxnu.x3107.bean.LocatorAnDSeek;
import jxnu.x3107.utils.Utils;

public class LocatorActivity extends Activity implements OnClickListener{


	private ImageView ivBack_lo;
	private ImageView iv_Locator_tijiao;
	private TextView et_locator_other_lo;
	private ListView mlv_LocatorRecord;
	private  Button Locator_clrean;


	private RelativeLayout rlLocatorAuto;
	private TextView tvAutoLocator;
	public static String LOCATION_BCR = "location_bcr";
	private BroadcastReceiver broadcastReceiver;


	public static String TAG = "LocTestDemo";

	private LocatorRecordAdapter mLocatorRecordAdapter;

	public  static final  String LOCATOR_SEEK_T = "sl_Record";

	private SharedPreferences sPreferences;

	public static String strLocator;//静态方法获取定位信息
	public static String getStrLocator() {
		return strLocator;
	}
	public static void setStrLocator(String strLocator) {
		HomePageFragment.strLocator = strLocator;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_locator);

		sPreferences = getSharedPreferences(WelComeActivity.USERNAME, 0);

		initView();
		initTitle();//头

		//自动定位
		initialize();
		initAutoLocator();

		initLocatorTiJao();//提交搜索定位
		initRecord();//记录
		initLocatorClean();//清除记录


	}


	private void initView() {
		Locator_clrean = (Button) View.inflate(getApplicationContext(), R.layout.locator_seek_button, null);
		et_locator_other_lo = (EditText) findViewById(R.id.et_locator_other_lo);
	}

	private void initTitle() {
		ivBack_lo=(ImageView) findViewById(R.id.iv_back_lo);
		ivBack_lo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(LocatorActivity.this,MainActivity.class);
				//				startActivityFromFragment(homepagerFragment, intent, 0);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK ) { 
			Intent intent=new Intent(LocatorActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
			return false; 
		} 

		return super.onKeyDown(keyCode, event);
	}

	private void initialize()
	{
		registerBroadCastReceiver();
	}
	private void initAutoLocator() {//自动定位

		rlLocatorAuto = (RelativeLayout) findViewById(R.id.locator_auto_rlayout);
		tvAutoLocator = (TextView) findViewById(R.id.locator_auto_rlayout_tv);

		rlLocatorAuto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Utils.showToast(getApplicationContext(), "点击定位定位");
				LocationUtils.getInstance().requestLocationInfo();
			}
		});
	}
	/**
	 * 注册一个广播，监听定位结果
	 */
	private void registerBroadCastReceiver()
	{
		broadcastReceiver = new BroadcastReceiver()
		{
			@Override
			public void onReceive(Context context, Intent intent)
			{
				String address = intent.getStringExtra("address");
				Editor editor = sPreferences.edit();
				editor.putString("locator", address);
				editor.commit();
				
				tvAutoLocator.setText(address);
			}
		};
		IntentFilter intentToReceiveFilter = new IntentFilter();
		intentToReceiveFilter.addAction(LOCATION_BCR);
		registerReceiver(broadcastReceiver, intentToReceiveFilter);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}


	private void initLocatorTiJao() {//搜索
		iv_Locator_tijiao = (ImageView) findViewById(R.id.iv_locator_tijiao);

		iv_Locator_tijiao.setOnClickListener(this);

	}
	//记录
	private void initRecord() {




		mlv_LocatorRecord = (ListView) findViewById(R.id.lv_locator_record);

		mlv_LocatorRecord.addFooterView(Locator_clrean);

		mLocatorRecordAdapter = new LocatorRecordAdapter(this, -1,(OnClickListener) this);


		mlv_LocatorRecord.setAdapter(mLocatorRecordAdapter);
		mlv_LocatorRecord.setOverScrollMode(View.OVER_SCROLL_NEVER);//消除渐变色
		mlv_LocatorRecord.setDivider(null);
		mlv_LocatorRecord.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				LocatorAnDSeek data = (LocatorAnDSeek) mLocatorRecordAdapter.getItem(arg2);
				et_locator_other_lo.setText(data.getLocator());
				//				miv_SeekOther.performClick();
			}
		});


		et_locator_other_lo.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mLocatorRecordAdapter.performFiltering(s);
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}

		});
	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.iv_locator_tijiao) {//搜索按钮
			saveSeekHistory();
			mLocatorRecordAdapter.initRecordHistory();


			String keyword = et_locator_other_lo.getText().toString().trim();

			Intent intent=new Intent(LocatorActivity.this,LocatorPoiSearchIncity.class);
			//			SDKInitializer.initialize(getApplicationContext()); // 不能传递Activity，必须是全局Context

			intent.putExtra("keyword", keyword);

			startActivity(intent);

			//				String strLocator;
			//				strLocator = et_locator_other_lo.getText().toString().trim();
			//				HomePageFragment.setStrLocator(strLocator);
			finish();


		} else {//"+"号按钮
			LocatorAnDSeek data = (LocatorAnDSeek) v.getTag();
			et_locator_other_lo.setText(data.getLocator());
		}


	}



	private void saveSeekHistory() {//保存历史记录
		String text = et_locator_other_lo.getText().toString().trim();
		if (text.length() < 1) {
			return;
		}
		SharedPreferences sp = getSharedPreferences(LOCATOR_SEEK_T, 0);
		String longhistory = sp.getString(LOCATOR_SEEK_T, "");
		String[] tmpHistory = longhistory.split(",");
		ArrayList<String> history = new ArrayList<String>(
				Arrays.asList(tmpHistory));
		if (history.size() > 0) {
			int i;
			for (i = 0; i < history.size(); i++) {
				if (text.contains(history.get(i))) {
					history.remove(i);
					break;
				}
			}
			history.add(0, text);
		}
		if (history.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < history.size(); i++) {
				sb.append(history.get(i) + ",");
			}
			sp.edit().putString(LOCATOR_SEEK_T, sb.toString()).commit();
		} else {
			sp.edit().putString(LOCATOR_SEEK_T, text + ",").commit();
		}
	}
	//记录


	//清除记录
	private void initLocatorClean() {
		Locator_clrean.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mLocatorRecordAdapter.initRecordHistory(1);
			}
		});
	}


}
