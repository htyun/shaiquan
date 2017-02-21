package jxnu.x3107.SunGroup;

import android.os.Bundle;
import android.text.InputType;
import android.view.Window;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import jxnu.n433.x3107.SunGroup.UrlService;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.x3107.Fragment.View.Logger;
import jxnu.x3107.bean.Code;
import jxnu.x3107.utils.Utils;


import android.app.Activity;
import android.content.Intent;

public class RegisterRegisterActivity extends Activity implements OnClickListener{

	private Button btnRegisterrlnext;
	private ImageView ivBack;
	//输入注册信息
	private EditText etUserName;
	private EditText etUserPassword;
	private EditText etStudentNumber;
	private EditText etStudentPassword;
	private EditText etSchool;
	//随机验证码
	private EditText etVerification;
	private ImageView ivVerification;
	private Button btnVerification;
	private String getCode = null;

	private ImageView passwordTypeUser;//显示隐藏密码
	private ImageView passwordTypeStudent;//显示隐藏密码
	private boolean isShowUser = false;//密码状态
	private boolean isShowStudent = false;//密码状态


	private UrlService gurl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_register);

		gurl  = new UrlService(getApplicationContext());
		
		initUserInfo();//注册信息
		initView();
		initVerification();

	}



	private void initVerification() {
		ivVerification.setImageBitmap(Code.getInstance().getBitmap());

		getCode = Code.getInstance().getCode();//获取显示的验证码
		Logger.e(getCode+"");
		btnVerification.setOnClickListener(this);
	}

	private void initView() {

		ivBack = (ImageView) findViewById(R.id.register_register_login_left_iv_back);
		btnRegisterrlnext = (Button) findViewById(R.id.register_register_log_rl_regist_btn_next);

		passwordTypeUser = (ImageView) findViewById(R.id.user_password_type);
		passwordTypeStudent = (ImageView) findViewById(R.id.student_number_password_type);

		ivBack.setOnClickListener(this);
		btnRegisterrlnext.setOnClickListener(this);

		passwordTypeUser.setOnClickListener(this);
		passwordTypeStudent.setOnClickListener(this);



	}

	private void initUserInfo() {
		etUserName = (EditText) findViewById(R.id.register_login_username_edit);
		etUserPassword = (EditText) findViewById(R.id.register_login_userpassword_edit);
		etStudentNumber = (EditText) findViewById(R.id.register_login_studentnumber_edit);
		etStudentPassword = (EditText) findViewById(R.id.register_login_studentnbpassword_edit);
		etSchool = (EditText) findViewById(R.id.register_login_school_edit);

		etVerification = (EditText) findViewById(R.id.register_login_Verification_edit);
		ivVerification = (ImageView) findViewById(R.id.vc_image_register_login_Verification_edit);
		btnVerification = (Button) findViewById(R.id.vc_shuaixi_register_login_Verification_edit);


	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK ) { 

			Intent intentBack =new  Intent(RegisterRegisterActivity.this,RegisterActivity.class);
			startActivity(intentBack);
			finish();
			return false; 
		} 

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register_register_login_left_iv_back:
			Intent intentBack =new  Intent(RegisterRegisterActivity.this,RegisterActivity.class);
			startActivity(intentBack);
			finish();
			break;
		case R.id.register_register_log_rl_regist_btn_next:

			//			userInfoList = new ArrayList<UserInfo>();
			//			UserInfo userInfo = new UserInfo();

			String userName = etUserName.getText().toString().trim();
			String userPassword = etUserPassword.getText().toString().trim();
			String studentNumber = etStudentNumber.getText().toString().trim();
			String studentPassword = etStudentPassword.getText().toString().trim();
			String school = etSchool.getText().toString().trim();
			String code = etVerification.getText().toString().trim();

			if (userName.equals("") || userPassword.equals("") || studentNumber.equals("") || studentPassword.equals("") || school.equals("") || code.equals("")) {
				Utils.showToast(getApplicationContext(), "请填写完整信息");
				return ;
			}
			
			if (!code.equals(getCode)) {
				Utils.showToast(getApplicationContext(), "验证码错误");
				ivVerification.setImageBitmap(Code.getInstance().getBitmap());
				getCode=Code.getInstance().getCode();
				return;
			}
			
			

			

			if(gurl.regToServer(userName,userPassword,studentNumber,studentPassword,school)){
				Intent intentBtnRegisternext = new Intent(RegisterRegisterActivity.this,RegisterLoginActivity.class);

				startActivity(intentBtnRegisternext);
				finish();
			}


			break;

		case R.id.user_password_type:

			if (! isShowUser) {

				etUserPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				isShowUser = true;
				return;
			}
			if (isShowUser) {
				etUserPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
				isShowUser = false;
				return;
			}

			break;
		case R.id.student_number_password_type:

			if (! isShowStudent) {

				etStudentPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				isShowStudent = true;
				return;
			}
			if (isShowStudent) {
				etStudentPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
				isShowStudent = false;
				return;
			}

			break;

		case R.id.vc_shuaixi_register_login_Verification_edit:

			ivVerification.setImageBitmap(Code.getInstance().getBitmap());
			getCode=Code.getInstance().getCode();

			break;

		default:
			break;
		}
	}


	


}
