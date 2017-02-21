package jxnu.x3107.SunGroup;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Window;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.x3107.utils.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
/*
 * 电话号码更改密码
 */
public class RegisterLoginAlterPhoneNumberActivity extends Activity implements OnClickListener{

	private ImageView ivBack;
	private EditText etPhoneNumberRegister;//号码
	private Button btnCode;

	private EditText etCode;
	private Button btnNext;

	private static String APPKEY ="c6fa75d8abec";
	private static String APPSECRET = "e65029f8751e3667cf8848e227cc3932";


	private String phoneStr;

	/*
	 *  填写从短信SDK应用后台注册得到的APPKEY
	 */

	/*
	 * 填写从短信SDK应用后台注册得到的APPSECRET
	 */

	@SuppressLint("HandlerLeak")
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
					Utils.showToast(getApplicationContext(), "验证码正确");
					Intent intentPNumber = new Intent(RegisterLoginAlterPhoneNumberActivity.this,RegisterLoginAlterPhoneNumberAlterPasswordActivity.class);
					intentPNumber.putExtra("phoneStr", phoneStr);
					startActivity(intentPNumber);
					finish();
				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
					Utils.showToast(getApplicationContext(), "验证码已经发送");
				}
			}else{
				/*
				 * 电话号码错误或者验证码错误
				 */

				((Throwable)data).printStackTrace();
				Utils.showToast(getApplicationContext(), "验证码错误");
			}
		};

	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_login_alter_phone_number);

		initView();
	}

	private void initView() {
		ivBack = (ImageView) findViewById(R.id.alter_register_login_left_register_phone_number_login_iv_back);
		etPhoneNumberRegister = (EditText) findViewById(R.id.alter_login_phone_number_register_register_phone_number_edit);
		btnCode = (Button) findViewById(R.id.alter_log_rl_regist_phone_number_register_register_phone_number_btn_code);
		etCode = (EditText) findViewById(R.id.alter_login_phone_number_code_register_register_phone_number_edit);
		btnNext = (Button) findViewById(R.id.alter_log_rl_regist_phone_number_code_register_register_phone_number_btn_code);


		ivBack.setOnClickListener(this);
		btnCode.setOnClickListener(this);
		btnNext.setOnClickListener(this);

		initSMSSDK();
	}

	private void initSMSSDK() {


		SMSSDK.initSDK(this, APPKEY, APPSECRET);
		EventHandler eh=new EventHandler(){

			@Override
			public void afterEvent(int event, int result, Object data) {
				// TODO Auto-generated method stub
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK ) { 

			Intent intentBack = new Intent(RegisterLoginAlterPhoneNumberActivity.this,RegisterLoginFotgetActivity.class);
			startActivity(intentBack);
			finish();

			return false; 
		} 

		return super.onKeyDown(keyCode, event);
	}

	@SuppressWarnings("unused")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.alter_register_login_left_register_phone_number_login_iv_back:

			Intent intentBack = new Intent(RegisterLoginAlterPhoneNumberActivity.this,RegisterLoginFotgetActivity.class);
			startActivity(intentBack);
			finish();


			break;
		case R.id.alter_log_rl_regist_phone_number_register_register_phone_number_btn_code:

			String etPNumber = etPhoneNumberRegister.getText().toString().trim();
			

			//获取验证码
			if(!TextUtils.isEmpty(etPhoneNumberRegister.getText().toString().trim())){
				SMSSDK.getVerificationCode("86", etPhoneNumberRegister.getText().toString().trim());
				phoneStr=etPhoneNumberRegister.getText().toString();
				
			}else{
				Utils.showToast(getApplicationContext(), "电话号码不能为空");
				return;
			}


			break;
		case R.id.alter_log_rl_regist_phone_number_code_register_register_phone_number_btn_code:

			//验证验证码
			if(!TextUtils.isEmpty(etCode.getText().toString())){
				SMSSDK.submitVerificationCode("86", phoneStr, etCode.getText().toString());



			}else{
				Utils.showToast(getApplicationContext(), "验证码不能为空");
				return;
			}
			break;


		default:
			break;
		}
	}


}
