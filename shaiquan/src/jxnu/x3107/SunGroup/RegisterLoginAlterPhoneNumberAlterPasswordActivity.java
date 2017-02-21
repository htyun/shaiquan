package jxnu.x3107.SunGroup;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.n433.x3107.SunGroup.UrlService;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

public class RegisterLoginAlterPhoneNumberAlterPasswordActivity extends Activity implements OnClickListener{

	private ImageView ivBack;
	public  EditText etNew;
	private Button btnOk;
	public  String strPhone;
	public SharedPreferences sPreferences;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_login_alter_phone_number_alter_password);

		sPreferences = getSharedPreferences(WelComeActivity.USERNAME,0);
		
		Intent intent = getIntent();
		strPhone = intent.getStringExtra("phoneStr");
		
		initView();
		
	}

	private void initView() {
		ivBack = (ImageView) findViewById(R.id.alter_register_login_left_register_phone_number_new_password_login_iv_back); 
		etNew = (EditText) findViewById(R.id.alter_login_phone_number_register_register_phone_number_new_password_edit); 
		btnOk = (Button) findViewById(R.id.alter_log_rl_regist_phone_number_register_register_phone_number_new_password_btn_code); 

		ivBack.setOnClickListener(this);
		btnOk.setOnClickListener(this);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK ) { 

			Intent intentBack = new Intent(RegisterLoginAlterPhoneNumberAlterPasswordActivity.this,RegisterLoginAlterPhoneNumberActivity.class);
			startActivity(intentBack);
			finish();

			return false; 
		} 

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.alter_register_login_left_register_phone_number_new_password_login_iv_back:

			Intent intentBack = new Intent(RegisterLoginAlterPhoneNumberAlterPasswordActivity.this,RegisterLoginAlterPhoneNumberActivity.class);
			startActivity(intentBack);
			finish();

			break;
		case R.id.alter_log_rl_regist_phone_number_register_register_phone_number_new_password_btn_code:
			
			UrlService uService = new UrlService(getApplicationContext());
			uService.alPassword(2,strPhone , etNew.getText().toString().trim(), "");

			break;

		default:
			break;
		}
	}


}
