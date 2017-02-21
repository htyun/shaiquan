package jxnu.x3107.SunGroup.OtherActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import jxnu.n433.x3107.SunGroup.Goods;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.n433.x3107.SunGroup.SGUrlService;
import jxnu.n433.x3107.SunGroup.UrlService;
import jxnu.n433.x3107.SunGroup.User;
import jxnu.x3107.SunGroup.MainActivity;
import jxnu.x3107.SunGroup.WelComeActivity;
import jxnu.x3107.utils.Utils;

public class HomeGoodsGVActivity extends Activity implements OnClickListener{

	private ImageView ivBack;
	private ImageView ivAttention;
	private TextView tvGoodsName;
	private ImageView ivGoodsImage;
	private TextView tvPrice;
	private TextView tvGoodsIntro;


	private LinearLayout llUserIntro;
	private ImageView userImg;
	private TextView userName;
	private TextView userQQ;
	private TextView userEmail;
	private TextView userTel;



	private String goodsName;
	private String studentNumber;
	private String releaseTime;

	private User user;
	private Goods goods;
	private UrlService urlService;
	private SGUrlService sgUrlService;
	private SharedPreferences sPreferences;
	private Bitmap btPersonalHead;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home_goods_gv);

		Intent intent= getIntent();
		goodsName = intent.getStringExtra("goodsName");
		studentNumber = intent.getStringExtra("studentNumber");
		releaseTime = intent.getStringExtra("releaseTime");

		sPreferences = getSharedPreferences(WelComeActivity.USERNAME, 0);

		user = new User();
		goods = new Goods();
		urlService = new UrlService(getApplicationContext());
		sgUrlService = new SGUrlService(getApplicationContext());
		user = urlService.getUserInfoOther(studentNumber);
		goods = urlService.getOlGoodsInfo(goodsName,studentNumber,releaseTime);

		initView();

		initShowIntro();

	}


	private void initView() {
		ivBack = (ImageView) findViewById(R.id.home_goods_gv_activity_iv_back);

		ivAttention = (ImageView) findViewById(R.id.home_goods_gv_activity_iv_user_name_shopping);
		tvGoodsName = (TextView) findViewById(R.id.home_goods_gv_activity_tv_goods_name);
		ivGoodsImage = (ImageView) findViewById(R.id.home_goods_gv_activity_iv_goods_image);
		tvPrice = (TextView) findViewById(R.id.home_goods_gv_activity_tv_goods_price);
		tvGoodsIntro = (TextView) findViewById(R.id.home_goods_gv_activity_tv_goods_intro);



		userImg = (ImageView) findViewById(R.id.home_goods_gv_activity_linlayout_user_intro_myself_news_userPhoto);
		userName = (TextView) findViewById(R.id.home_goods_gv_activity_linlayout_user_intro_myself_news_share_user_pohot);
		userQQ = (TextView) findViewById(R.id.home_goods_gv_activity_linlayout_user_intro_myself_news_date);
		userTel = (TextView) findViewById(R.id.home_goods_gv_activity_linlayout_user_intro_myself_news_share_text);
		userEmail = (TextView) findViewById(R.id.home_goods_gv_activity_linlayout_user_intro_myself_news_detailed);

		llUserIntro = (LinearLayout) findViewById(R.id.home_goods_gv_activity_linlayout_user_intro);

		ivBack.setOnClickListener(this);
		ivAttention.setOnClickListener(this);
		llUserIntro.setOnClickListener(this);
	}

	private void initShowIntro() {





		if (goods.getGoodState() == 1) {
			
			tvGoodsName.setText(goods.getGoodName()+"");
		}else {
			tvGoodsName.setText(goods.getGoodName()+"(该物品已经失效)");
		}
		tvPrice.setText("价格:￥"+goods.getPrice()+"");
		tvGoodsIntro.setText("商品介绍:"+goods.getGoodIntroduction()+"");

		String imgUrl = UrlService.USERIMGURL + "goods/" + goods.getGoodLogo();

		//		Picasso.with(getApplicationContext()).load().placeholder(R.drawable.ic_launcher).into(ivGoodsImage);


		Picasso.with(getApplicationContext())
		.load(imgUrl)
		.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
		//         .networkPolicy(NetworkPolicy.NO_CACHE)
		.into(ivGoodsImage);

		if (sPreferences.getString("uN", "").equals("") || sPreferences.getString("uN", "") == null || sPreferences.getString("uN", "").equals("null")) {
			llUserIntro.setVisibility(View.GONE);
			llUserIntro.setEnabled(false);
		}else {
			llUserIntro.setVisibility(View.VISIBLE);
			llUserIntro.setEnabled(true);

			Picasso.with(getApplicationContext())
			.load(UrlService.USERIMGURL + user.getUserLogo())
			.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
			//	         .networkPolicy(NetworkPolicy.NO_CACHE)
			.into(userImg);
			userName.setText("User:" + user.getUserName());
			userQQ.setText("QQ:" + user.getQq());
			userTel.setText("Tel:" +user.getUserTel());
			userEmail.setText("Email:" +user.getUserEmail());

		}


	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_goods_gv_activity_iv_back:

			if(sPreferences.getInt("goodsGva",0) == 123){

				Intent intentBack = new Intent(HomeGoodsGVActivity.this,MainActivity.class);
				startActivity(intentBack);
				finish();

			}else if(sPreferences.getInt("goodsGva",0) == 456){
				Intent intentBack = new Intent(HomeGoodsGVActivity.this,HomeMenuClassActivity.class);
				startActivity(intentBack);
				finish();
			}
			// 先判断是否已经回收
			if(btPersonalHead != null && !btPersonalHead.isRecycled()){ 
				// 回收并且置为null
				btPersonalHead.recycle(); 
				btPersonalHead = null; 
			} 
			System.gc();

			break;
		case R.id.home_goods_gv_activity_iv_user_name_shopping:



			if (sPreferences.getString("uN", "").equals("")) {
				Utils.showToast(getApplicationContext(), "未登录账号，无法收藏");
				return;
			}
			user = new User();
			user = urlService.getUserInfoOther(sPreferences.getString("uN", ""));
			if (studentNumber.equals(user.getStuNo())) {
				Utils.showToast(getApplicationContext(), "无法收藏自己发布的！！");
				return;
			}

			if (sgUrlService.addGoodsType(user.getStuNo(),studentNumber,releaseTime)) {

				Utils.showToast(getApplicationContext(), "收藏成功");
			}else {
				Utils.showToast(getApplicationContext(), "收藏失败(您可能已经收藏该物品)");
			}
			break;
		case R.id.home_goods_gv_activity_linlayout_user_intro:



			if (sPreferences.getString("uN", "").equals("")) {
				Utils.showToast(getApplicationContext(), "未登录账号，无法关注");
				return;
			}
			user = new User();
			user = urlService.getUserInfoOther(sPreferences.getString("uN", ""));
			if (studentNumber.equals(user.getStuNo())) {
				Utils.showToast(getApplicationContext(), "无法关注自己");
				return;
			}

			if(sgUrlService.addFollow(user.getStuNo(),studentNumber)){
				Utils.showToast(getApplicationContext(), "关注用户成功");
			}else {
				Utils.showToast(getApplicationContext(), "关注用户失败(您可能已经关注该用户)");
			}
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if(sPreferences.getInt("goodsGva",0) == 123){

				Intent intentBack = new Intent(HomeGoodsGVActivity.this,MainActivity.class);
				startActivity(intentBack);
				finish();

			}else if (sPreferences.getInt("goodsGva",0) == 456) {

				Intent intentBack = new Intent(HomeGoodsGVActivity.this,HomeMenuClassActivity.class);
				startActivity(intentBack);
				finish();
			}

			// 先判断是否已经回收
			if(btPersonalHead != null && !btPersonalHead.isRecycled()){ 
				// 回收并且置为null
				btPersonalHead.recycle(); 
				btPersonalHead = null; 
			} 
			System.gc();

			return false;
		}

		return super.onKeyDown(keyCode, event);
	}


}
