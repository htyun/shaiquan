package jxnu.x3107.SunGroup;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.widget.TextView;
import jxnu.n433.x3107.SunGroup.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/*
 * 欢迎界面
 * */
public class WelComeActivity extends Activity {

	public  static final  String USERNAME = "userName";

	private TextView tvTime;
	private TimeCount timeCount;
	private SharedPreferences sPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wel_come);
		
		sPreferences = getSharedPreferences(USERNAME, 0);
		Editor editor1 = sPreferences.edit();
		editor1.putInt("mainFragment", 0);
		editor1.commit();

		tvTime = (TextView) findViewById(R.id.tvTime);
		timeCount = new TimeCount(4000, 1000);
		timeCount.start();

	}
	
	
	class TimeCount extends CountDownTimer{

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
			
		}

		@Override
		public void onFinish() {
			
			if (sPreferences.getString("uN", "").equals("") || sPreferences.getString("uN", "") == null) {

				Intent intentMain =new  Intent(WelComeActivity.this,MainActivity.class);
				startActivity(intentMain);
				finish();

			}else {
				Intent intentMain =new  Intent(WelComeActivity.this,MainActivity.class);
				startActivity(intentMain);
				finish();
			}
		}

		@Override
		public void onTick(long millisUntilFinished) {
			tvTime.setText(millisUntilFinished/1000 + " 秒");
		}
		
	}


}
