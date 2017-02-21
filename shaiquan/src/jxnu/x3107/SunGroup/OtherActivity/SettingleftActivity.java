package jxnu.x3107.SunGroup.OtherActivity;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Window;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.x3107.Adapter.LocatorRecordAdapter;
import jxnu.x3107.Adapter.SeekRecordAdapter;
import jxnu.x3107.SunGroup.MainActivity;
import jxnu.x3107.SunGroup.RegisterActivity;
import jxnu.x3107.SunGroup.WelComeActivity;
import jxnu.x3107.utils.Utils;

public class SettingleftActivity extends Activity implements OnClickListener{

	private RelativeLayout rlSettingPassword;
	private RelativeLayout rlExit;
	private RelativeLayout rlClean;
	
	private SeekRecordAdapter seekRecordAdapter;//清理搜索记录
	private LocatorRecordAdapter locatorRecordAdapter;//清理定位记录
	
	private ImageView settingBack;
	
	
	private SharedPreferences sPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting_left);

		sPreferences = getSharedPreferences(WelComeActivity.USERNAME,0);
		
		initView();

	}

	private void initView() {
		rlSettingPassword = (RelativeLayout) findViewById(R.id.setting_left_layout_login_password);
		rlExit = (RelativeLayout) findViewById(R.id.setting_left_layout_exit_login);
		rlClean = (RelativeLayout) findViewById(R.id.setting_left_layout_login_clean);
		settingBack = (ImageView) findViewById(R.id.setting_left_iv_back);

		rlSettingPassword.setOnClickListener(this);
		rlExit.setOnClickListener(this);
		rlClean.setOnClickListener(this);
		settingBack.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting_left_layout_login_password:

			Intent intentPassword = new Intent(SettingleftActivity.this,SettingAlterPasswordActivity.class);
			startActivity(intentPassword);
			finish();

			break;
		case R.id.setting_left_layout_login_clean:
			
			Editor editor = sPreferences.edit();
			editor.putString("locator", "");
			editor.commit();
			
			seekRecordAdapter = new SeekRecordAdapter(this, -1,(OnClickListener) this);
			locatorRecordAdapter = new LocatorRecordAdapter(this, -1,(OnClickListener) this);
			seekRecordAdapter.initRecordHistory(1);
			locatorRecordAdapter.initRecordHistory(1);
			
			Utils.showToast(getApplicationContext(), "清理成功");
			
			
			break;
		case R.id.setting_left_layout_exit_login:
			
			Editor editor1 = sPreferences.edit();
			editor1.putString("uN", "");
			editor1.commit();
			
			Intent intentExit = new Intent(SettingleftActivity.this,RegisterActivity.class);
			startActivity(intentExit);
			finish();
			sPreferences.edit().clear().commit();
			Utils.showToast(getApplicationContext(), "退出成功！！");
			
			break;
			
		case R.id.setting_left_iv_back:
			
			Intent intentBack = new Intent(SettingleftActivity.this,MainActivity.class);
			startActivity(intentBack);
			finish();
			
			break;

		default:
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intentBack = new Intent(SettingleftActivity.this,MainActivity.class);
			startActivity(intentBack);
			finish();
			return false;
		}
		
		return super.onKeyDown(keyCode, event);
	}

}
