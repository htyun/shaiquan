package jxnu.x3107.SunGroup.OtherActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.x3107.SunGroup.MainActivity;
import jxnu.x3107.SunGroup.OtherActivity.Fragment.MerchandiseleftFragmentMain;
import jxnu.x3107.utils.Utils;

public class MerchandiseleftActivity extends FragmentActivity implements OnClickListener{

	private ImageView merchandieBack;
	private FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

	private ImageView ivIssueAdd;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		

		setContentView(R.layout.activity_merchandise_left);


		initView();

		initMerchandiseFragment();
	}


	private void initView() {

		ivIssueAdd = (ImageView) findViewById(R.id.merchandie_left_iv_add_issue);
		ivIssueAdd.setOnClickListener(this);

		merchandieBack = (ImageView) findViewById(R.id.merchandie_left_iv_back);

		merchandieBack.setOnClickListener(this);

	}

	private void initMerchandiseFragment() {
		fragmentTransaction.replace(R.id.merchandie_left_fragmentmain, new MerchandiseleftFragmentMain());
		fragmentTransaction.commit();
	}



	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.merchandie_left_iv_back:

			Intent intentBack = new Intent(MerchandiseleftActivity.this,MainActivity.class);
			startActivity(intentBack);
			finish();

			break;
		case R.id.merchandie_left_iv_add_issue:
			Utils.showToast(getApplicationContext(), "点击了+");
			
			Intent intent = new Intent(MerchandiseleftActivity.this,MerchandiseleftIssueActivity.class);
			intent.putExtra("menu", "");
			startActivity(intent);
			finish();
			
			break;

		default:
			break;
		}

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK ) { 
			Intent intentBack = new Intent(MerchandiseleftActivity.this,MainActivity.class);
			startActivity(intentBack);
			finish();
		} 

		return super.onKeyDown(keyCode, event);
	}

}
