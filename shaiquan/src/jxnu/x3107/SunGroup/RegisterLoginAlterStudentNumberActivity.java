package jxnu.x3107.SunGroup;

import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.n433.x3107.SunGroup.UrlService;
import jxnu.x3107.utils.Utils;


import android.app.Activity;
import android.content.Intent;
/*
 * 学号更改密码
 */
public class RegisterLoginAlterStudentNumberActivity extends Activity implements OnClickListener{

	private ImageView ivAlterRLBack;
	private EditText etAlterRLSNumber;
	private EditText etAlterRLPWord;
	private EditText etAlterRLUPWord;
	private Button btnAlterRL;

	private ImageView passwordTypeSNumber;//显示隐藏密码
	private ImageView passwordTypeUPWord;//显示隐藏密码
	private boolean isShowSNumber = false;//密码状态
	private boolean isShowUPWord = false;//密码状态

	/*private List<UserInfo> userInfoList;
	private UserInfoDataHelper userInfoDHelper;
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_login_alter_student_number);

		initView();

	}


	private void initView() {

		ivAlterRLBack = (ImageView) findViewById(R.id.alter_register_login_left_register_login_iv_back);
		etAlterRLSNumber = (EditText) findViewById(R.id.alter_login_user_register_login_edit);
		etAlterRLPWord = (EditText) findViewById(R.id.alter_login_student_number_passwd_register_login_edit);
		etAlterRLUPWord = (EditText) findViewById(R.id.alter_login_passwd_register_login_edit);
		btnAlterRL = (Button) findViewById(R.id.alter_log_rl_regist_register_login_btn);
		passwordTypeSNumber = (ImageView) findViewById(R.id.alter_password_register_login_student_type);
		passwordTypeUPWord = (ImageView) findViewById(R.id.alter_password_register_login_user_type);

		ivAlterRLBack.setOnClickListener(this);
		btnAlterRL.setOnClickListener(this);
		passwordTypeSNumber.setOnClickListener(this);
		passwordTypeUPWord.setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK ) { 

			Intent intentBack = new Intent(RegisterLoginAlterStudentNumberActivity.this,RegisterLoginActivity.class);
			startActivity(intentBack);
			finish();

			return false; 
		} 

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.alter_register_login_left_register_login_iv_back:

			Intent intentBack = new Intent(RegisterLoginAlterStudentNumberActivity.this,RegisterLoginActivity.class);
			startActivity(intentBack);
			finish();

			break;
		case R.id.alter_log_rl_regist_register_login_btn:


			String sNumber = etAlterRLSNumber.getText().toString().trim()+"";
			String sPWord = etAlterRLPWord.getText().toString().trim()+"";
			String password = etAlterRLUPWord.getText().toString().trim()+"";
			if (etAlterRLPWord.getText().toString().trim().equals("") ||
					etAlterRLSNumber.getText().toString().trim().equals("")||
					etAlterRLUPWord.getText().toString().trim().equals("")) {
				Utils.showToast(getApplicationContext(), "有信息未填写");
				return;

			}

			UrlService urlService = new UrlService(getApplicationContext());
			if(urlService.alPassword(1, sNumber, password,sPWord)){
				Intent intentAlter = new Intent(RegisterLoginAlterStudentNumberActivity.this,RegisterLoginActivity.class);
				startActivity(intentAlter);
				finish();
			}


			break;
		case R.id.alter_password_register_login_student_type:
			if (! isShowSNumber) {

				etAlterRLPWord.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				isShowSNumber = true;
				return;
			}
			if (isShowSNumber) {
				etAlterRLPWord.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
				isShowSNumber = false;
				return;
			}

			break;
		case R.id.alter_password_register_login_user_type:

			if (! isShowUPWord) {

				etAlterRLUPWord.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				isShowUPWord = true;
				return;
			}
			if (isShowUPWord) {
				etAlterRLUPWord.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
				isShowUPWord = false;
				return;
			}
			break;

		default:
			break;
		}
	}


}
