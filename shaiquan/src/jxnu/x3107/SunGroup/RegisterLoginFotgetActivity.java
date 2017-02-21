package jxnu.x3107.SunGroup;

import android.os.Bundle;
import android.view.Window;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import jxnu.n433.x3107.SunGroup.R;
import android.app.Activity;
import android.content.Intent;
public class RegisterLoginFotgetActivity extends Activity implements OnClickListener{

	private ImageView ivBack;
	private Button btnPNumber;
	private Button btnSNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_login_fotget);

		initView();
	}

	private void initView() {
		ivBack = (ImageView) findViewById(R.id.alter_register_login_left_register_login_iv_forget_back);
		btnPNumber = (Button) findViewById(R.id.alter_log_rl_regist_register_login_forget_phone_number_btn);
		btnSNumber = (Button) findViewById(R.id.alter_log_rl_regist_register_login_forget_student_number_btn);

		ivBack.setOnClickListener(this);
		btnPNumber.setOnClickListener(this);
		btnSNumber.setOnClickListener(this);

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK ) { 

			Intent intentBack = new Intent(RegisterLoginFotgetActivity.this,RegisterLoginActivity.class);
			startActivity(intentBack);
			finish();
			return false; 
		} 

		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.alter_register_login_left_register_login_iv_forget_back:

			Intent intentBack = new Intent(RegisterLoginFotgetActivity.this,RegisterLoginActivity.class);
			startActivity(intentBack);
			finish();

			break;
		case R.id.alter_log_rl_regist_register_login_forget_phone_number_btn:

			Intent intentPNumber = new Intent(RegisterLoginFotgetActivity.this,RegisterLoginAlterPhoneNumberActivity.class);
			startActivity(intentPNumber);
			finish();

			break;
		case R.id.alter_log_rl_regist_register_login_forget_student_number_btn:

			Intent intentSNumber = new Intent(RegisterLoginFotgetActivity.this,RegisterLoginAlterStudentNumberActivity.class);
			startActivity(intentSNumber);
			finish();

			break;

		default:
			break;
		}
	}


}
