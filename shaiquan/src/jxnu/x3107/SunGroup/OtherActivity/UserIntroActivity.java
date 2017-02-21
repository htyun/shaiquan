package jxnu.x3107.SunGroup.OtherActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.n433.x3107.SunGroup.SGUrlService;
import jxnu.n433.x3107.SunGroup.UrlService;
import jxnu.n433.x3107.SunGroup.User;
import jxnu.x3107.SunGroup.MainActivity;
import jxnu.x3107.utils.Utils;

public class UserIntroActivity extends Activity implements OnClickListener{

	private ImageView leftPersonalback;

	private ImageView leftPersonalIViewHead;//头像

	public TextView pName;//昵称
	public TextView pSexs;//性别
	public TextView pSite;//地址
	public TextView pIntro;//简介
	public TextView pSchool;//学校
	public TextView pBirthday;//生日
	public TextView pQQ;//QQ
	public TextView pMailBox;//邮箱
	public TextView pPhoneNumber;
	private ImageView menu;//取消关注

	private String followstuNo;

	private UrlService urlService;
	private User user;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_personal_follow_info);

		Intent intent= getIntent();
		followstuNo = intent.getStringExtra("followstuNo");

		urlService = new UrlService(getApplicationContext());
		user = new User();
		user = urlService.getUserInfoOther(followstuNo);

		initView();
		initViewShow();


	}

	private void initViewShow() {

	/*	Picasso.with(getApplicationContext())
		.load()
		.placeholder(R.drawable.ic_launcher)
		.into(leftPersonalIViewHead);*/
		String imgUrl = UrlService.USERIMGURL + user.getUserLogo();
		
		
		Picasso.with(getApplicationContext())
		.load(imgUrl)
		.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
//         .networkPolicy(NetworkPolicy.NO_CACHE)
		.into(leftPersonalIViewHead);

		pName.setText(user.getUserName()+"");
		pSexs.setText(user.getUserSex()+"");
		pSite.setText(user.getUserCity()+"");
		pIntro.setText(user.getIntroduction()+"");
		pSchool.setText(user.getStuName()+"");
		pBirthday.setText(user.getBirth()+"");
		pQQ.setText(user.getQq()+"");
		pMailBox.setText(user.getUserEmail()+"");
		pPhoneNumber.setText(user.getUserTel()+"");



	}

	private void initView() {
		leftPersonalback = (ImageView) findViewById(R.id.personal_left_iv_back_personal_follow);

		leftPersonalIViewHead = (ImageView) findViewById(R.id.zdy_image_personal_follow);		
		pName = (TextView) findViewById(R.id.personal_left_tv_name_personal_follow);
		pSexs = (TextView) findViewById(R.id.sexs_personal_follow);
		pSite = (TextView) findViewById(R.id.sites_personal_follow);
		pIntro = (TextView) findViewById(R.id.intros_personal_follow);
		pSchool = (TextView) findViewById(R.id.school_personal_follow);
		pBirthday = (TextView) findViewById(R.id.birthday_personal_follow);
		pQQ = (TextView) findViewById(R.id.qq_personal_follow);
		pMailBox = (TextView) findViewById(R.id.mailbox_personal_follow);
		pPhoneNumber = (TextView) findViewById(R.id.phonenumber_personal_follow);
		menu = (ImageView) findViewById(R.id.personal_left_iv_menu_personal_follow);

		leftPersonalback.setOnClickListener(this);
		menu.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.personal_left_iv_back_personal_follow:

			Intent intent = new Intent(UserIntroActivity.this,MainActivity.class);
			startActivity(intent);
			finish();


			break;

		case R.id.personal_left_iv_menu_personal_follow:

			SGUrlService sgUrlService = new SGUrlService(getApplicationContext());
			if(sgUrlService.delFollow(user.getStuNo())){
				Utils.showToast(getApplicationContext(), "取消关注 ");
			}
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(UserIntroActivity.this,MainActivity.class);
			startActivity(intent);
			finish();


		}

		return super.onKeyDown(keyCode, event);
	}


}
