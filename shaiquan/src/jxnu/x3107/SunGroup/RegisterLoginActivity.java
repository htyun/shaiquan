package jxnu.x3107.SunGroup;

import android.os.Bundle;
import android.text.InputType;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.view.Window;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import jxnu.n433.x3107.SunGroup.UrlService;
import jxnu.n433.x3107.SunGroup.User;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.x3107.Fragment.View.Logger;
import jxnu.x3107.utils.Utils;
public class RegisterLoginActivity extends Activity implements OnClickListener{

	private ImageView loginBack;
	private Button btnRegisterLogin;
	private Button btnRegisterForgetPasswd;
	private EditText etLoginUserName;//用户名
	private EditText etLoginPasswd;//密码
	private ImageView passwordType;//显示隐藏密码
	private boolean isShow = false;//密码状态

	private UrlService gurl ;

	

	private SharedPreferences sPreferences;//保存登录状态

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_login);

		gurl = new UrlService(getApplicationContext());
		
		initEditText();
		initView();
		initSPreferences();

	}




	private void initSPreferences() {
		sPreferences = getSharedPreferences(WelComeActivity.USERNAME, 0);

	}


	private void initView() {
		loginBack = (ImageView) findViewById(R.id.register_login_left_iv_back);
		btnRegisterForgetPasswd = (Button) findViewById(R.id.register_forget_passwd);
		btnRegisterLogin = (Button) findViewById(R.id.register_login_login_btn);
		passwordType = (ImageView) findViewById(R.id.login_password_type);

		loginBack.setOnClickListener(this);
		btnRegisterForgetPasswd.setOnClickListener(this);
		btnRegisterLogin.setOnClickListener(this);
		passwordType.setOnClickListener(this);

	}
	private void initEditText() {
		etLoginUserName = (EditText) findViewById(R.id.login_user_edit);
		etLoginPasswd = (EditText) findViewById(R.id.login_passwd_edit);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK ) { 

			Intent intentBack = new Intent(RegisterLoginActivity.this,RegisterActivity.class);
			startActivity(intentBack);
			finish();

			return false; 
		} 

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {



		case R.id.register_login_left_iv_back:
			//返回
			Intent intentBack = new Intent(RegisterLoginActivity.this,RegisterActivity.class);
			startActivity(intentBack);
			finish();
			break;
		case R.id.register_forget_passwd:
			//忘记密码
			btnRegisterForgetPasswd.setTextColor(Color.RED);//文本变成红色
			//			showSelectDialog();
			Intent intentForget = new Intent(RegisterLoginActivity.this,RegisterLoginFotgetActivity.class);
			startActivity(intentForget);
			finish();
			break;
		case R.id.register_login_login_btn:
			//登录

			String etUserName = etLoginUserName.getText().toString().trim();
			String etUserPw = etLoginPasswd.getText().toString().trim();
			if (etUserName.equals("") || etUserName == null || etUserPw.equals("") || etUserPw == null) {
				Utils.showToast(getApplicationContext(), "请输入账号或密码");
				return;
			}

			

			
			if(gurl.logToService(etUserName,etUserPw).equals("userNameY")){
				
				User user = new User();
				UrlService urlService = new UrlService(getApplicationContext());
				user = urlService.getUserInfo(etUserName);
				
				Editor editor = sPreferences.edit();
				editor.putString("uN", user.getStuNo()+"");
				editor.commit();
				
				Logger.e("34dfgsdfgsdfgasdfgsd:" + user.getStuNo());
				
				Intent intentLogin = new Intent(RegisterLoginActivity.this,MainActivity.class);
				
				startActivity(intentLogin);
				finish();
				return;
			}
			if(gurl.logToService(etUserName,etUserPw).equals("stuNoY")){
				
				
				Editor editor = sPreferences.edit();
				editor.putString("uN",etUserName+"");
				editor.commit();
				
				Logger.e("34dfgsdfgsdfgasdfgsd:" + etUserName);
				
				Intent intentLogin = new Intent(RegisterLoginActivity.this,MainActivity.class);
				
				startActivity(intentLogin);
				finish();
				return;
			}
			

			
			Utils.showToast(getApplicationContext(), "账号或密码错误");

			break;
		case R.id.login_password_type:

			if (! isShow) {

				etLoginPasswd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				isShow = true;
				return;
			}
			if (isShow) {
				etLoginPasswd.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
				isShow = false;
				return;
			}


			break;
		case R.id.studentNumber_btn_update:

			Intent intentsNumber = new Intent(RegisterLoginActivity.this,RegisterLoginAlterStudentNumberActivity.class);
			startActivity(intentsNumber);
			finish();

			break;
		case R.id.phoneNumber_btn_update:

			Intent intentpNumber = new Intent(RegisterLoginActivity.this,RegisterLoginAlterPhoneNumberActivity.class);
			startActivity(intentpNumber);
			finish();

			break;

		default:
			break;
		}
	}



	






}
