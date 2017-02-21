package jxnu.x3107.SunGroup.OtherActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Window;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import jxnu.n433.x3107.SunGroup.UrlService;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.x3107.SunGroup.WelComeActivity;
import jxnu.x3107.utils.Utils;

public class PersonalleftAlterPhoneActivity extends Activity implements OnClickListener{

	private ImageView ivNextBack;
	private EditText etPhoneNumberRegister;//号码
	private Button btnCode;

	private EditText etCode;//号码
	private Button btnSubmit;


	private SharedPreferences sPreferences;

	private static String APPKEY ="c6fa75d8abec";
	private static String APPSECRET = "e65029f8751e3667cf8848e227cc3932";

	private UrlService gurl;

	@SuppressLint("HandlerLeak")
	/*
	 *  填写从短信SDK应用后台注册得到的APPKEY
	 */

	/*
	 * 填写从短信SDK应用后台注册得到的APPSECRET
	 */

	Handler mHandler=new Handler(){

		public void handleMessage(Message msg) {

			super.handleMessage(msg);
			int event=msg.arg1;
			int result=msg.arg2;
			Object data=msg.obj;

			if(result==SMSSDK.RESULT_COMPLETE){
				/*
				 * 操作成功后
				 */
				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
					Toast.makeText(getApplicationContext(), "验证码正确", Toast.LENGTH_SHORT).show();
					if (gurl.alterUserInfo(sPreferences.getString("uN", ""), 7, etPNumber)) {

						Intent intentCode = new Intent(PersonalleftAlterPhoneActivity.this,PersonalleftActivity.class);
						startActivity(intentCode);
						finish();
					}


				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
					Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
				}
			}else{
				/*
				 * 电话号码错误或者验证码错误
				 */
				Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_SHORT).show();
				((Throwable)data).printStackTrace();
			}
		};

	};
	private String etPNumber;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_personal_left_alter_phone);

		sPreferences = getSharedPreferences(WelComeActivity.USERNAME, 0);



		initView();




		initSMSSDK();

	}



	private void initView() {
		ivNextBack = (ImageView) findViewById(R.id.register_register_login_left_iv_next_back);
		etPhoneNumberRegister = (EditText) findViewById(R.id.alter_login_phone_number_register_edit);
		btnCode = (Button) findViewById(R.id.alter_log_rl_regist_phone_number_register_btn_code);

		etCode = (EditText) findViewById(R.id.alter_login_phone_number_code_register_edit);
		btnSubmit = (Button) findViewById(R.id.alter_log_rl_regist_phone_number_register_code_btn_code);

		btnSubmit.setOnClickListener(this);

		ivNextBack.setOnClickListener(this);
		btnCode.setOnClickListener(this);


	}
	private void initSMSSDK() {
		SMSSDK.initSDK(this, APPKEY, APPSECRET);
		EventHandler eh=new EventHandler(){

			@Override
			public void afterEvent(int event, int result, Object data) {
				super.afterEvent(event, result, data);
				Message msg=new Message();
				msg.arg1=event;
				msg.arg2=result;
				msg.obj=data;
				mHandler.sendMessage(msg);
			}
		};
		SMSSDK.registerEventHandler(eh);
	}
	@Override
	public void onClick(View v) {
		etPNumber = etPhoneNumberRegister.getText().toString().trim();

		switch (v.getId()) {
		case R.id.register_register_login_left_iv_next_back:

			Intent intentBack = new Intent(PersonalleftAlterPhoneActivity.this,PersonalleftActivity.class);
			startActivity(intentBack);
			finish();
			break;
		case R.id.alter_log_rl_regist_phone_number_register_btn_code:


			//获取验证码
			if(!TextUtils.isEmpty(etPNumber)){
				SMSSDK.getVerificationCode("86", etPNumber);
			}else{
				Utils.showToast(getApplicationContext(), "电话号码不能为空");
				return;
			}


			break;
		case R.id.alter_log_rl_regist_phone_number_register_code_btn_code:

			if(etPNumber.equals("")){

				Utils.showToast(getApplicationContext(), "电话号码不能为空");
				return;
			}


			//验证验证码
			if(!TextUtils.isEmpty(etCode.getText().toString())){
				SMSSDK.submitVerificationCode("86", etPNumber, etCode.getText().toString());




			}else{

				Utils.showToast(getApplicationContext(), "验证码不能为空");
				return;
			}
			break;
		default:
			break;
		}
	}
	/**/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK ) { 
			Intent intentBack = new Intent(PersonalleftAlterPhoneActivity.this,PersonalleftActivity.class);
			startActivity(intentBack);
			finish();
			return false; 
		} 

		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		SMSSDK.unregisterAllEventHandler();
	}


}
