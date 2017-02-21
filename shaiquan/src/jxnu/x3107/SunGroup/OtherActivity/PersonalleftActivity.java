package jxnu.x3107.SunGroup.OtherActivity;



import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import jxnu.n433.x3107.SunGroup.UrlService;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.n433.x3107.SunGroup.User;
import jxnu.x3107.Fragment.View.RoundImageView;
import jxnu.x3107.SunGroup.MainActivity;
import jxnu.x3107.SunGroup.WelComeActivity;
import jxnu.x3107.SunGroup.OtherActivity.View.LeftBtnClickListener;
import jxnu.x3107.SunGroup.OtherActivity.View.PersonalleftDialog;
import jxnu.x3107.utils.Utils;

public class PersonalleftActivity extends Activity implements OnClickListener{

	private ImageView leftPersonalback;

	private RoundImageView leftPersonalIViewHead;//头像
	private RelativeLayout Head;

	public TextView pName;//昵称
	public TextView pSexs;//性别
	public TextView pSite;//地址
	public TextView pIntro;//简介
	public TextView pSchool;//学校
	public TextView pBirthday;//生日
	public TextView pQQ;//QQ
	public TextView pMailBox;//邮箱
	public TextView pPhoneNumber;
	public RelativeLayout personalSexs,
	personalSite,
	personalIntro,
	personalSchool,
	personalBirthday,
	personalQQ,
	personalMailBox,
	personalPhomeNumber;//信息点击修改

	private DatePickerDialog dialogBirthday;
	private int  year,monthofyear,dayofMonth;
	private   String textBirthday;




	private Context mContext;

	private SharedPreferences sPreferences;


	//	private Bitmap btPersonalHead;

	private User user;
	private UrlService gurl ;


	@Override
	protected void onCreate(Bundle savedInstanceState) {



		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_personal_left);



		mContext = this;



		sPreferences = getSharedPreferences(WelComeActivity.USERNAME,0);
		//获取服务器user信息
		user = new User();
		gurl = new UrlService(getApplicationContext());
		user = gurl.getUserInfoOther(sPreferences.getString("uN", ""));

		initTitle();

		initImageViewHead();//头像



		initReativeLayout();//修改资料
		initReativeLayoutFirst();

	}


	private void initTitle() {
		leftPersonalback = (ImageView) findViewById(R.id.personal_left_iv_back_personal);
		leftPersonalback.setOnClickListener(this);

	}
	//头像
	private void initImageViewHead() {


		leftPersonalIViewHead = (RoundImageView) findViewById(R.id.zdy_image_personal);
		Head = (RelativeLayout) findViewById(R.id.personal_left_iv_head_personal);

		//		String strImage = null;
		//
		//		strImage = user.getUserLogo()+"";

		//			btPersonalHead = BitmapFactory.decodeFile(strImage);
		user = gurl.getUserInfoOther(sPreferences.getString("uN", ""));
		String imgUrl = UrlService.USERIMGURL + user.getUserLogo();
		System.out.println(imgUrl);

		//		Picasso.with(getApplicationContext()).load(imgUrl).placeholder(R.drawable.ic_launcher).into(leftPersonalIViewHead);

		Picasso.with(getApplicationContext())
		.load(imgUrl)
		.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
//         .networkPolicy(NetworkPolicy.NO_CACHE)
		.into(leftPersonalIViewHead);


		Head.setOnClickListener(this);


	}

	private void initReativeLayout() {//修改资料

		pName = (TextView) findViewById(R.id.personal_left_tv_name_personal);
		pSexs = (TextView) findViewById(R.id.sexs_personal);
		pSite = (TextView) findViewById(R.id.sites_personal);
		pIntro = (TextView) findViewById(R.id.intros_personal);
		pSchool = (TextView) findViewById(R.id.school_personal);
		pBirthday = (TextView) findViewById(R.id.birthday_personal);
		pQQ = (TextView) findViewById(R.id.qq_personal);
		pMailBox = (TextView) findViewById(R.id.mailbox_personal);
		pPhoneNumber = (TextView) findViewById(R.id.phonenumber_personal);
		personalSexs = (RelativeLayout) findViewById(R.id.personal_left_layout_sexs_personal);
		personalSite = (RelativeLayout) findViewById(R.id.personal_left_layout_site_personal);
		personalIntro = (RelativeLayout) findViewById(R.id.personal_left_layout_intro_personal);
		personalSchool = (RelativeLayout) findViewById(R.id.personal_left_layout_school_personal);
		personalBirthday = (RelativeLayout) findViewById(R.id.personal_left_layout_birthday_personal);
		personalQQ = (RelativeLayout) findViewById(R.id.personal_left_layout_qq_personal);
		personalMailBox = (RelativeLayout) findViewById(R.id.personal_left_layout_mailbox_personal);
		personalPhomeNumber = (RelativeLayout) findViewById(R.id.personal_left_layout_phonenumber_personal);

		pName.setOnClickListener(this);
		personalSexs.setOnClickListener(this);
		personalSite.setOnClickListener(this);
		personalIntro.setOnClickListener(this);
		personalSchool.setOnClickListener(this);
		personalBirthday.setOnClickListener(this);
		personalQQ.setOnClickListener(this);
		personalMailBox.setOnClickListener(this);
		personalPhomeNumber.setOnClickListener(this);





	}
	private void initReativeLayoutFirst() {
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
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.personal_left_iv_head_personal:
			intent.setClass(PersonalleftActivity.this,PictureHeadActivity.class);
			startActivity(intent);
			finish();


			/*			// 先判断是否已经回收
			if(btPersonalHead != null && !btPersonalHead.isRecycled()){ 
				// 回收并且置为null
				btPersonalHead.recycle(); 
				btPersonalHead = null; 
			} */
			System.gc();
			break;
		case R.id.personal_left_iv_back_personal:
			intent.setClass(PersonalleftActivity.this,MainActivity.class);
			startActivity(intent);
			finish();

			/*// 先判断是否已经回收
			if(btPersonalHead != null && !btPersonalHead.isRecycled()){ 
				// 回收并且置为null
				btPersonalHead.recycle(); 
				btPersonalHead = null; 
			} */
			System.gc();

			break;
		case R.id.personal_left_tv_name_personal:

			dialogOut("编辑用户名(12字内)", user.getUserName(), 1);


			break;
		case R.id.personal_left_layout_sexs_personal:


			dialogOut("编辑性别(男或女)", user.getUserSex(), 2);


			break;
		case R.id.personal_left_layout_site_personal:


			dialogOut("编辑地址(省市县/区)", user.getUserCity(), 3);
			break;
		case R.id.personal_left_layout_intro_personal:

			dialogOut("编辑简介(30字内)", user.getIntroduction(), 4);


			break;
		case R.id.personal_left_layout_school_personal:

			dialogOut("编辑学校", user.getStuName(), 5);
			break;
		case R.id.personal_left_layout_birthday_personal:




			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
			monthofyear = calendar.get(Calendar.MONTH);
			dayofMonth = calendar.get(Calendar.DAY_OF_MONTH);
			dialogBirthday = new DatePickerDialog(this, new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					int month = monthOfYear+1;
					textBirthday = year+"-"+month+"-"+dayOfMonth+"";

					if(gurl.alterUserInfo(sPreferences.getString("uN", ""),4, textBirthday)){

						//获取服务器user信息
						user = new User();
						gurl = new UrlService(getApplicationContext());
						user = gurl.getUserInfoOther(sPreferences.getString("uN", ""));

						pBirthday.setText(user.getBirth()+"");
					}else {
						return;
					}

					//					pBirthday.setText(user.getBirth()+"");
				}
			}, year, monthofyear, dayofMonth);

			dialogBirthday.show();



			break;
		case R.id.personal_left_layout_qq_personal:

			dialogOut("编辑QQ", user.getQq(), 7);


			break;
		case R.id.personal_left_layout_mailbox_personal:

			dialogOut("编辑邮箱", user.getUserEmail(), 8);


			break;
		case R.id.personal_left_layout_phonenumber_personal:



			Intent intentPNumber = new Intent(PersonalleftActivity.this,PersonalleftAlterPhoneActivity.class);
			startActivity(intentPNumber);
			finish();

			/*
			// 先判断是否已经回收
			if(btPersonalHead != null && !btPersonalHead.isRecycled()){ 
				// 回收并且置为null
				btPersonalHead.recycle(); 
				btPersonalHead = null; 
			} */
			System.gc();
			break;
		default:
			break;
		}
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK ) { 
			Intent intent = new Intent();
			intent.setClass(PersonalleftActivity.this,MainActivity.class);
			startActivity(intent);
			finish();


			/*			// 先判断是否已经回收
			if(btPersonalHead != null && !btPersonalHead.isRecycled()){ 
				// 回收并且置为null
				btPersonalHead.recycle(); 
				btPersonalHead = null; 
			} */
			System.gc();
			return false; 
		} 

		return super.onKeyDown(keyCode, event);
	}

	public class RightBtnClickListener implements OnClickListener{

		private PersonalleftDialog dialog;
		private int n;
		private Context mContext;

		public RightBtnClickListener(Context mContext,PersonalleftDialog dialog,int n) {
			super();
			this.mContext = mContext;
			this.dialog = dialog;
			this.n = n;
		}

		@Override
		public void onClick(View v) {
			//			Utils.showToast(mContext, "点中了右边的按钮!");

			switch (n) {
			case 1: 
				if(dialog.getDialogContent().equals("") || dialog.getDialogContent() == null){

					Utils.showToast(getApplicationContext(), "请输入用户名");

					return;
				}
				

				
				if(gurl.alterUserInfo(sPreferences.getString("uN", ""), 1, dialog.getDialogContent())){

				/*	Editor editor = sPreferences.edit();
					editor.putString("uN", dialog.getDialogContent());
					editor.commit();*/


					//获取服务器user信息
					user = new User();
					gurl = new UrlService(getApplicationContext());
					user = gurl.getUserInfoOther(sPreferences.getString("uN", ""));



					pName.setText(user.getUserName()+"");

				}else {
					return;
				}


				dialog.dismiss();
				break;
			case 2:
				if (dialog.getDialogContent().equals("男")||dialog.getDialogContent().equals("女")) {
					if(gurl.alterUserInfo(sPreferences.getString("uN", ""), 2, dialog.getDialogContent())){

						//获取服务器user信息
						user = new User();
						gurl = new UrlService(getApplicationContext());
						user = gurl.getUserInfoOther(sPreferences.getString("uN", ""));

						pSexs.setText(user.getUserSex()+"");
					}else {
						return;
					}
					dialog.dismiss();

				}else {
					Utils.showToast(mContext, "输入错误");
				}
				break;
			case 3:

				if (dialog.getDialogContent().equals("") || dialog.getDialogContent() == null){
					Utils.showToast(mContext, "输入错误");
					return;
				}
				if(gurl.alterUserInfo(sPreferences.getString("uN", ""), 11, dialog.getDialogContent())){

					//获取服务器user信息
					user = new User();
					gurl = new UrlService(getApplicationContext());
					user = gurl.getUserInfoOther(sPreferences.getString("uN", ""));

					pSite.setText(user.getUserCity()+"");
				}else {
					return;
				}
				dialog.dismiss();
				break;
			case 4:
				if (dialog.getDialogContent().equals("") || dialog.getDialogContent() == null){
					Utils.showToast(mContext, "输入错误");
					return;
				}
				if(gurl.alterUserInfo(sPreferences.getString("uN", ""), 3, dialog.getDialogContent())){

					//获取服务器user信息
					user = new User();
					gurl = new UrlService(getApplicationContext());
					user = gurl.getUserInfoOther(sPreferences.getString("uN", ""));

					pIntro.setText(user.getIntroduction()+"");
				}else {
					return;
				}
				dialog.dismiss();

				break;
			case 5:
				if (dialog.getDialogContent().equals("") || dialog.getDialogContent() == null){
					Utils.showToast(mContext, "输入错误");
					return;
				}
				if(gurl.alterUserInfo(sPreferences.getString("uN", ""), 10, dialog.getDialogContent())){

					//获取服务器user信息
					user = new User();
					gurl = new UrlService(getApplicationContext());
					user = gurl.getUserInfoOther(sPreferences.getString("uN", ""));

					pSchool.setText(user.getStuName()+"");
				}else {
					return;
				}
				dialog.dismiss();
				break;
			case 6:
				if (dialog.getDialogContent().equals("") || dialog.getDialogContent() == null){
					Utils.showToast(mContext, "输入错误");
					return;
				}
				if(gurl.alterUserInfo(sPreferences.getString("uN", ""), 4, dialog.getDialogContent())){

					//获取服务器user信息
					user = new User();
					gurl = new UrlService(getApplicationContext());
					user = gurl.getUserInfoOther(sPreferences.getString("uN", ""));

					pBirthday.setText(user.getBirth()+"");
				}else {
					return;
				}
				dialog.dismiss();
				break;
			case 7:
				if (dialog.getDialogContent().equals("") || dialog.getDialogContent() == null){
					Utils.showToast(mContext, "输入错误");
					return;
				}
				if(gurl.alterUserInfo(sPreferences.getString("uN", ""), 5, dialog.getDialogContent())){

					//获取服务器user信息
					user = new User();
					gurl = new UrlService(getApplicationContext());
					user = gurl.getUserInfoOther(sPreferences.getString("uN", ""));

					pQQ.setText(user.getQq()+"");
				}else {
					return;
				}
				dialog.dismiss();
				break;
			case 8:

				String mailbox = dialog.getDialogContent();

				if (dialog.getDialogContent().equals("") || dialog.getDialogContent() == null){
					Utils.showToast(mContext, "输入错误");
					return;
				}
				if (isMailBox(mailbox)) {

					if(gurl.alterUserInfo(sPreferences.getString("uN", ""),6, dialog.getDialogContent())){

						//获取服务器user信息
						user = new User();
						gurl = new UrlService(getApplicationContext());
						user = gurl.getUserInfoOther(sPreferences.getString("uN", ""));

						pMailBox.setText(user.getUserEmail()+"");
					}else {
						return;
					}

					dialog.dismiss();
				}else {
					Utils.showToast(getApplicationContext(), "输入错误");
				}
				break;
			case 9:
				pPhoneNumber.setText(user.getUserTel()+"");
				break;
			default:
				break;
			}


		}

	}

	/*
	 * 验证邮箱
	 * */
	private boolean isMailBox(String mail){
		Pattern pattern = Pattern.compile("^[A-Za-z0-9][\\w\\._]*[a-zA-Z0-9]+@[A-Za-z0-9-_]+\\.([A-Za-z]{2,4})");
		Matcher matcher = pattern.matcher(mail);
		return matcher.matches();
	}


	//dialogOut修改
	private void dialogOut(String title,String userInfo,int n){

		PersonalleftDialog dialogOut;

		dialogOut = new PersonalleftDialog(mContext);
		dialogOut.setTitleInCenter();
		dialogOut.setDialogTitle(title);
		dialogOut.setDialogTitleSize(R.dimen.x16);
		dialogOut.setDialogTitleBacColor(R.color.bisque);

		dialogOut.setDialogContent(userInfo+"");

		dialogOut.setLeftBtnListener(new LeftBtnClickListener(mContext,dialogOut,n));
		dialogOut.setRightBtnListener(new RightBtnClickListener(mContext,dialogOut,n));
		dialogOut.show();

	}





}
