package jxnu.x3107.SunGroup;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import jxnu.n433.x3107.SunGroup.R;

public class RegisterActivity extends Activity implements OnClickListener{

	private Button btnLogIn;
	private Button btnRegist;
	private SharedPreferences sPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		sPreferences = getSharedPreferences(WelComeActivity.USERNAME,0);
		Editor editor1 = sPreferences.edit();
		editor1.putInt("mainFragment", 0);
		editor1.commit();
		
		initView();
	}

	private void initView() {
		btnLogIn = (Button) findViewById(R.id.register_log_rl_login_btn);
		btnRegist = (Button) findViewById(R.id.register_log_rl_regist_btn);
		
		btnLogIn.setOnClickListener(this);
		btnRegist.setOnClickListener(this);
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register_log_rl_login_btn:
			
			Intent intentLogin = new Intent(RegisterActivity.this,RegisterLoginActivity.class);
			startActivity(intentLogin);
			finish();
			
			break;
		case R.id.register_log_rl_regist_btn:
			//注册
			Intent intentRegister = new Intent(RegisterActivity.this,RegisterRegisterActivity.class);
			startActivity(intentRegister);
			finish();
			break;

		default:
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intentLogin = new Intent(RegisterActivity.this,MainActivity.class);
			startActivity(intentLogin);
			finish();
			return false;
		}
		
		
		return super.onKeyDown(keyCode, event);
	}

}
