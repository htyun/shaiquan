package jxnu.x3107.SunGroup;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import jxnu.n433.x3107.SunGroup.R;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class FirstLoginActivity extends Activity implements OnPageChangeListener,OnTouchListener{

	private ViewPager mViewPager;
	private View view1, view2,view3;
	private List<View> list;
	public boolean flag = false;
	private LinearLayout pointLLayout;
	private ImageView[] imgs;
	private int count;
	private int currentItem;
	private int lastX = 0;// 获得当前X坐标

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_first_login);


		if (isNetworkAvailable(FirstLoginActivity.this))
		{
			Toast.makeText(getApplicationContext(), "当前有可用网络！", Toast.LENGTH_LONG).show();
		}
		else
		{
			Toast.makeText(getApplicationContext(), "当前没有可用网络！", Toast.LENGTH_LONG).show();
		}


		pointLLayout = (LinearLayout) findViewById(R.id.First_llayout);
		count = pointLLayout.getChildCount();
		// Log.d("aaaa", "" + count);
		imgs = new ImageView[count];
		for (int i = 0; i < count; i++) {
			imgs[i] = (ImageView) pointLLayout.getChildAt(i);
			imgs[i].setEnabled(true);
			imgs[i].setTag(i);
		}
		currentItem = 0;
		imgs[currentItem].setEnabled(false);
		mViewPager = (ViewPager) findViewById(R.id.First_viewPager);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setOnTouchListener(this);
		LayoutInflater inflater = LayoutInflater.from(FirstLoginActivity.this);
		list = new ArrayList<View>();
		SharedPreferences sp = getSharedPreferences("loding", 0);
		flag = sp.getBoolean("loding_flag", false);
		if (!flag) {
			initpage(inflater);
		} else {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					
					Intent intent = new Intent(FirstLoginActivity.this, WelComeActivity.class);
					startActivity(intent);
					finish();
				}
			}, 0);
		}

	}
	/**
	 * 检查当前网络是否可用
	 * 
	 * @param context
	 * @return
	 */

	public boolean isNetworkAvailable(Activity activity)
	{
		Context context = activity.getApplicationContext();
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null)
		{
			return false;
		}
		else
		{
			// 获取NetworkInfo对象
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if (networkInfo != null && networkInfo.length > 0)
			{
				for (int i = 0; i < networkInfo.length; i++)
				{
					System.out.println(i + "===状态===" + networkInfo[i].getState());
					System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
					// 判断当前网络状态是否为连接状态
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	public void initpage(LayoutInflater flater) {
		view1 = flater.inflate(R.layout.activity_first_loding, null);
		view1.setBackgroundResource(R.drawable.shouji);
		view2 = flater.inflate(R.layout.activity_first_loding, null);
		view2.setBackgroundResource(R.drawable.yingpan);
		view3 = flater.inflate(R.layout.activity_first_loding, null);
		view3.setBackgroundResource(R.drawable.life_style);
		list.add(view1);
		list.add(view2);
		list.add(view3);
		mViewPager.setAdapter(pager);
	}

	private void setcurrentPoint(int position) {
		if (position < 0 || position > count - 1 || currentItem == position) {
			return;
		}
		imgs[currentItem].setEnabled(true);
		imgs[position].setEnabled(false);
		currentItem = position;
	}


	PagerAdapter pager = new PagerAdapter() {

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			
			return list.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			
			container.removeView(list.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			
			container.addView(list.get(position));

			return list.get(position);
		}
	};


	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#
	 * onPageScrollStateChanged(int)
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled
	 * (int, float, int)
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected
	 * (int)
	 */
	@Override
	public void onPageSelected(int arg0) {
		
		setcurrentPoint(arg0);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 * android.view.MotionEvent)
	 */
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		
		switch (arg1.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = (int) arg1.getX();
			break;

		case MotionEvent.ACTION_UP:
			if (!flag) {
				if ((lastX - arg1.getX() > 100)
						&& (mViewPager.getCurrentItem() == mViewPager
						.getAdapter().getCount() - 1)) {// 从最后一页向右滑动
					new Handler().postDelayed(new Runnable() {
						public void run() {
							SharedPreferences sp = getSharedPreferences(
									"loding", 0);
							SharedPreferences.Editor et = sp.edit();
							et.putBoolean("loding_flag", true);
							et.commit();
							Intent intent = new Intent(FirstLoginActivity.this,
									WelComeActivity.class);
							startActivity(intent);
							finish();
						};
					}, 0);
				}
			}
		}
		return false;
	}


}
