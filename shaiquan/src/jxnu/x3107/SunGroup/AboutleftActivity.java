package jxnu.x3107.SunGroup;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import jxnu.n433.x3107.SunGroup.R;
import android.app.Activity;
import android.content.Intent;

public class AboutleftActivity extends Activity implements OnClickListener{

	private ImageView ivBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_aboutleft);

		ivBack = (ImageView) findViewById(R.id.about_left_activity_iv_back);
		ivBack.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.about_left_activity_iv_back:

			Intent intentBack = new Intent(AboutleftActivity.this,MainActivity.class);
			startActivity(intentBack);
			finish();

			break;

		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intentBack = new Intent(AboutleftActivity.this,MainActivity.class);
			startActivity(intentBack);
			finish();
			
		}
		
		return super.onKeyDown(keyCode, event);
	}
}
