package jxnu.x3107.SunGroup.OtherActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.Window;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import jxnu.n433.x3107.SunGroup.UrlService;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.n433.x3107.SunGroup.User;
import jxnu.x3107.SunGroup.WelComeActivity;
import jxnu.x3107.utils.Utils;

public class SettingAlterPasswordActivity extends Activity implements OnClickListener{

	private ImageView ivAlterBack;
	private EditText etAlterSNumber;
	private EditText etAlterPWord;
	private Button btnAlter;

	private ImageView passwordType;//显示隐藏密码
	private boolean isShow = false;//密码状态



	private SharedPreferences sPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		

		
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting_alter_password);





		initView();




	}

	private void initView() {
		ivAlterBack = (ImageView) findViewById(R.id.alter_left_iv_back);
		etAlterSNumber = (EditText) findViewById(R.id.alter_login_user_edit);
		etAlterPWord = (EditText) findViewById(R.id.alter_login_passwd_edit);
		btnAlter = (Button) findViewById(R.id.alter_log_rl_regist_btn);

		passwordType = (ImageView) findViewById(R.id.alter_password_type);

		ivAlterBack.setOnClickListener(this);
		btnAlter.setOnClickListener(this);
		passwordType.setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK ) { 

			Intent intentBack = new Intent(SettingAlterPasswordActivity.this,SettingleftActivity.class);
			startActivity(intentBack);
			finish();
			return false; 
		} 

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.alter_left_iv_back:
			Intent intentBack = new Intent(SettingAlterPasswordActivity.this,SettingleftActivity.class);
			startActivity(intentBack);
			finish();
			break;
		case R.id.alter_log_rl_regist_btn:

			sPreferences = getSharedPreferences(WelComeActivity.USERNAME,0);
			
			UrlService gurl = new UrlService(getApplicationContext());
			User user = new User();
			user = gurl.getUserInfoOther(sPreferences.getString("uN",""));

			String stuNo = etAlterSNumber.getText().toString().trim()+"";


			if (etAlterPWord.getText().toString().trim().equals("") ||
					etAlterSNumber.getText().toString().trim().equals("")) {
				Utils.showToast(getApplicationContext(), "有信息未填写");
				return;

			}

			if (stuNo.equals(user.getStuNo().toString())) {
				String password = etAlterPWord.getText().toString().trim()+"";
				gurl.alterUserInfo(sPreferences.getString("uN", ""), 8, password);
			}
			if (!stuNo.equals(user.getStuNo().toString())) {
				Utils.showToast(getApplicationContext(), "学号错误");
				return;
			}
			Intent intentAlter = new Intent(SettingAlterPasswordActivity.this,SettingleftActivity.class);
			startActivity(intentAlter);
			finish();

			break;
		case R.id.alter_password_type:

			if (! isShow) {

				etAlterPWord.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				isShow = true;
				return;
			}
			if (isShow) {
				etAlterPWord.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
				isShow = false;
				return;
			}

			break;

		default:
			break;
		}
	}


}
