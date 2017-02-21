package jxnu.x3107.SunGroup;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.ArrayList;
import java.util.Arrays;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.x3107.Adapter.SeekRecordAdapter;
import jxnu.x3107.SunGroup.OtherActivity.HomeMenuClassActivity;
import jxnu.x3107.bean.LocatorAnDSeek;

public class SeekActivity extends Activity implements OnClickListener {

	private ImageView mivBack;
	private ListView mlv_SeekRecord;
	private TextView met_SeekOther;
	private ImageView miv_SeekOther;
	private Button seek_clean;


	 public SharedPreferences sPreferences;
	private SeekRecordAdapter mRecordAdapter;


	//	lvProvince.setDivider(null);消除listview横线

	public static final String LOCATOR_SEEK = "ls_Record";
	private SharedPreferences sp;
	private String text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_seek);
		
		sPreferences = getSharedPreferences(WelComeActivity.USERNAME,0);

		initView();

		initTitle();

		initRecord();//搜索记录

		initSeekClean();



	}


	private void initView() {
		seek_clean = (Button) View.inflate(getApplicationContext(), R.layout.locator_seek_button, null);		
	}


	private void initTitle() {


		mivBack=(ImageView) findViewById(R.id.iv_back);
		mivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(SeekActivity.this,MainActivity.class);
				//				startActivityFromFragment(homepagerFragment, intent, 0);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK ) { 
			Intent intent=new Intent(SeekActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
			return false; 
		} 


		return super.onKeyDown(keyCode, event);
	}
	/*
	 * 搜索记录
	 * */
	private void initRecord() {//搜索记录




		mlv_SeekRecord = (ListView) findViewById(R.id.lv_seek_record);
		mlv_SeekRecord.addFooterView(seek_clean);

		mRecordAdapter = new SeekRecordAdapter(this, -1,(OnClickListener) this);




		mlv_SeekRecord.setAdapter(mRecordAdapter);
		mlv_SeekRecord.setOverScrollMode(View.OVER_SCROLL_NEVER);//消除渐变色
		mlv_SeekRecord.setDivider(null);
		mlv_SeekRecord.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {




				LocatorAnDSeek data = (LocatorAnDSeek) mRecordAdapter.getItem(arg2);
				met_SeekOther.setText(data.getSeek());
				//				miv_SeekOther.performClick();
			}
		});

		miv_SeekOther = (ImageView) findViewById(R.id.iv_seek_other);
		miv_SeekOther.setOnClickListener( this);
		met_SeekOther = (TextView) findViewById(R.id.et_seek_other);

		met_SeekOther.addTextChangedListener(new TextWatcher() {



			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mRecordAdapter.performFiltering(s);
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
		if (id == R.id.iv_seek_other) {//搜索按钮
			saveSeekHistory();
			mRecordAdapter.initRecordHistory();

			if (v.getId() == R.id.iv_seek_other && v.getId() != R.id.et_seek_other) {
				Intent intent = new Intent(SeekActivity.this,HomeMenuClassActivity.class);
				
				Editor editor1 = sPreferences.edit();
				editor1.putString("menu", "搜索");
				editor1.commit();
				Editor editor2 = sPreferences.edit();
				editor2.putString("menutext", text+"");
				editor2.commit();
				
				startActivity(intent);
				finish();
			}



		} else {//"+"号按钮
			LocatorAnDSeek data = (LocatorAnDSeek) v.getTag();
			met_SeekOther.setText(data.getSeek());
		}


	}

	private void saveSeekHistory() {//保存历史记录
		text = met_SeekOther.getText().toString().trim();
		if (text.length() < 1) {

			return;
		}
		sp = getSharedPreferences(LOCATOR_SEEK, 0);
		String longhistory = sp.getString(LOCATOR_SEEK, "");
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
			sp.edit().putString(LOCATOR_SEEK, sb.toString()).commit();
		} else {
			sp.edit().putString(LOCATOR_SEEK, text + ",").commit();
		}
	}

	/*
	 * 搜索记录
	 * */


	private void initSeekClean() {

		seek_clean.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mRecordAdapter.initRecordHistory(1);
			}
		});



	}


}
