package jxnu.x3107.SunGroup;

import android.os.Bundle;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.x3107.Fragment.HomePageFragment;
import jxnu.x3107.Fragment.MessageFragment;
import jxnu.x3107.Fragment.MySelfFragment;
import jxnu.x3107.Fragment.ShoppingFragment;
import jxnu.x3107.utils.Utils;
import android.view.KeyEvent;

public class MainActivity extends Activity implements android.view.View.OnClickListener{

	//点击返回退出
	private long mExitTime;

	//	首页fragment
	private  HomePageFragment homepageFragment;
	//	购物车fragment
	private ShoppingFragment shoppingFragment;
	//	消息fragment
	private MessageFragment messageFragment;
	//	我的fragment
	private MySelfFragment myselfFragment;
	//	首页布局
	private  View homepageLayout;
	//	购物车布局
	private View shoppingLayout;
	//	消息布局
	private View messageLayout;
	//	我的布局
	private View myselfLayout;
	//	首页imageview
	private ImageView homepageImage;
	//	购物车imageview
	private ImageView shoppingImage;
	//	消息imageview
	private ImageView messageImage;
	//	我的imageview
	private ImageView myselfImage;
	//	首页textview】
	private TextView homgpageText;
	//	购物车textview】
	private TextView shoppingText;
	//	消息textview】
	private TextView messageText;
	//	我的textview】
	private TextView myselfText;

	//	 用于对Fragment进行管理
	private FragmentManager fragmentManager;



	private SharedPreferences sPreferences;

	private String isLogin;




	private Bitmap btPersonalHead;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		sPreferences = getSharedPreferences(WelComeActivity.USERNAME,0);
		
		Editor editor1 = sPreferences.edit();
		editor1.putInt("goodsGva", 123);
		editor1.commit();
		
		isLogin = sPreferences.getString("uN", "");
		if(isLogin.equals("")){

			Utils.showToast(getApplicationContext(), "未登录账号");
		}


		//		初始化布局
		initViews();
		fragmentManager=getFragmentManager();
		// 第一次启动时选中第0个fragment
		setTabSelection(sPreferences.getInt("mainFragment",0));
	}






	private void setTabSelection(int i) {

		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);

		Editor editor1 = sPreferences.edit();
		editor1.putInt("mainFragment", i);
		editor1.commit();

		switch (i) {
		case 0:
			// 当点击了首页时，改变控件的图片和文字颜色
			homepageImage.setImageResource(R.drawable.guide_home_on);
			homgpageText.setTextColor(Color.parseColor("#ff3300"));
			if (homepageFragment==null) {
				// 如果为空，则创建一个并添加到界面上
				homepageFragment=new HomePageFragment();
				transaction.add(R.id.content, homepageFragment);
			}else {
				// 如果不为空，则直接将它显示出来
				transaction.show(homepageFragment);
			}
			break;
		case 1:
			// 当点击了首页时，改变控件的图片和文字颜色
			shoppingImage.setImageResource(R.drawable.guide_tfaccount_on);
			shoppingText.setTextColor(Color.parseColor("#ff3300"));
			if (shoppingFragment==null) {
				// 如果为空，则创建一个并添加到界面上
				if (isLogin.equals("")) {
					Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
					startActivity(intent);
					finish();

					// 先判断是否已经回收
					if(btPersonalHead != null && !btPersonalHead.isRecycled()){ 
						// 回收并且置为null
						btPersonalHead.recycle(); 
						btPersonalHead = null; 
					} 
					System.gc();
					return;
				}
				shoppingFragment=new ShoppingFragment();
				transaction.add(R.id.content, shoppingFragment);
			}else {
				// 如果不为空，则直接将它显示出来
				transaction.show(shoppingFragment);
			}
			break;
		case 2:
			// 当点击了首页时，改变控件的图片和文字颜色
			messageImage.setImageResource(R.drawable.detail_fav_light);
			messageText.setTextColor(Color.parseColor("#ff3300"));
			if (messageFragment==null) {
				// 如果为空，则创建一个并添加到界面上
				if (isLogin.equals("")) {
					Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
					startActivity(intent);
					finish();
					// 先判断是否已经回收
					if(btPersonalHead != null && !btPersonalHead.isRecycled()){ 
						// 回收并且置为null
						btPersonalHead.recycle(); 
						btPersonalHead = null; 
					} 
					System.gc();
					return;
				}
				messageFragment=new MessageFragment();
				transaction.add(R.id.content, messageFragment);
			}else {
				// 如果不为空，则直接将它显示出来
				transaction.show(messageFragment);
			}
			break;
		case 3:
			// 当点击了首页时，改变控件的图片和文字颜色
			myselfImage.setImageResource(R.drawable.guide_ww_on);
			myselfText.setTextColor(Color.parseColor("#ff3300"));
			if (myselfFragment==null) {
				// 如果为空，则创建一个并添加到界面上
				if (isLogin.equals("")) {
					Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
					startActivity(intent);
					finish();

					// 先判断是否已经回收
					if(btPersonalHead != null && !btPersonalHead.isRecycled()){ 
						// 回收并且置为null
						btPersonalHead.recycle(); 
						btPersonalHead = null; 
					} 
					System.gc();					
					return;
				}
				myselfFragment=new MySelfFragment();
				transaction.add(R.id.content, myselfFragment);
			}else {
				// 如果不为空，则直接将它显示出来
				transaction.show(myselfFragment);
			}
			break;

		default:
			break;
		}
		transaction.commit();
	}



	//	private void initLocatorSJ() {
	//		
	//		Intent intent =new Intent();
	//		
	//		String  strLocator = getIntent().getExtras().getString("strLocator");
	//		
	//		homepageFragment.setStrLocator(strLocator);
	//	}


	//	  将所有的Fragment都置为隐藏状态。
	//	  用于对Fragment执行操作的事务
	private void hideFragments(FragmentTransaction transaction) {
		if (homepageFragment != null) {
			transaction.hide(homepageFragment);
		}
		if (shoppingFragment != null) {
			transaction.hide(shoppingFragment);
		}
		if (messageFragment != null) {
			transaction.hide(messageFragment);
		}
		if (myselfFragment != null) {
			transaction.hide(myselfFragment);
		}
	}

	//清除掉所有的选中状态。
	private void clearSelection() {
		homepageImage.setImageResource(R.drawable.guide_home_nm);
		homgpageText.setTextColor(Color.parseColor("#82858b"));
		shoppingImage.setImageResource(R.drawable.guide_tfaccount_nm);
		shoppingText.setTextColor(Color.parseColor("#82858b"));
		messageImage.setImageResource(R.drawable.detail_fav_light_gray);
		messageText.setTextColor(Color.parseColor("#82858b"));
		myselfImage.setImageResource(R.drawable.guide_ww_nm);
		myselfText.setTextColor(Color.parseColor("#82858b"));
	}


	private void initViews() {
		homepageLayout=findViewById(R.id.homepage_layout);
		shoppingLayout=findViewById(R.id.shopping_layout);
		messageLayout=findViewById(R.id.message_layout);
		myselfLayout=findViewById(R.id.myself_layout);

		homepageImage=(ImageView) findViewById(R.id.homepage_image);
		shoppingImage=(ImageView) findViewById(R.id.shopping_image);
		messageImage=(ImageView) findViewById(R.id.message_image);
		myselfImage=(ImageView) findViewById(R.id.myself_image);

		homgpageText=(TextView) findViewById(R.id.homepage_text);
		shoppingText=(TextView) findViewById(R.id.shopping_text);
		messageText=(TextView) findViewById(R.id.message_text);
		myselfText=(TextView) findViewById(R.id.myself_text);

		homepageLayout.setOnClickListener(this);
		shoppingLayout.setOnClickListener(this);
		messageLayout.setOnClickListener(this);
		myselfLayout.setOnClickListener(this);
	}




	public boolean dispatchKeyEvent(KeyEvent event) {//点击两次退出

		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN
					&& event.getRepeatCount() == 0) {


				if ((System.currentTimeMillis() - mExitTime) > 2000) {// 如果两次按键时间间隔大于2000毫秒，则不退出
					Utils.showToast(getApplicationContext(), "再按一次退出程序");
					mExitTime = System.currentTimeMillis();//更新
				} else {

					int id = android.os.Process.myPid();
					if (id != 0) {
						android.os.Process.killProcess(id);
					}
				}
				return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.homepage_layout:
			// 当点击了消息tab时，选中第1个tab
			setTabSelection(0);
			break;
		case R.id.shopping_layout:
			// 当点击了联系人tab时，选中第2个tab
			setTabSelection(1);
			break;
		case R.id.message_layout:
			// 当点击了动态tab时，选中第3个tab
			setTabSelection(2);
			break;
		case R.id.myself_layout:
			// 当点击了设置tab时，选中第4个tab
			setTabSelection(3);
			break;
			/*	;*/
		default:
			break;
		}
	}







}
