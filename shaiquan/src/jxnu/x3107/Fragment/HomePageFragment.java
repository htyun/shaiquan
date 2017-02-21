package jxnu.x3107.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import jxnu.n433.x3107.SunGroup.Goods;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.n433.x3107.SunGroup.UrlService;
import jxnu.x3107.Adapter.GirdViewAdapter;
import jxnu.x3107.Fragment.View.HGridView;
import jxnu.x3107.Fragment.View.Logger;
import jxnu.x3107.Fragment.View.PullDownElasticImp;
import jxnu.x3107.Fragment.View.PullDownScrollView;
import jxnu.x3107.Fragment.View.PullDownScrollView.RefreshListener;
import jxnu.x3107.SunGroup.LocatorActivity;
import jxnu.x3107.SunGroup.SeekActivity;
import jxnu.x3107.SunGroup.WelComeActivity;
import jxnu.x3107.SunGroup.OtherActivity.HomeGoodsGVActivity;
import jxnu.x3107.SunGroup.OtherActivity.HomeMenuClassActivity;
import jxnu.x3107.bean.Ad;
import jxnu.x3107.utils.Utils;

public class HomePageFragment extends Fragment implements RefreshListener,OnClickListener{


	private ViewPager viewPager;

	private TextView tvHomeNumber,tvHomeNumberRight;//广告条数字显示

	private View homepageLayout;
	private ImageView ivSeek;
	private TextView tvHomeLocator;


	private List<Ad> imageList=new ArrayList<Ad>();


	private boolean isRunning = false;



	//商品列表
	private HGridView hotGridView;
	private HGridView catGridView;
	private RelativeLayout layoutMenu1,layoutMenu2,layoutMenu3,layoutMenu4;//菜单


	private PullDownScrollView mPullDownScrollView; 


	private SharedPreferences sPreferences;
	private List<Goods> glList;
	private UrlService uService;
	private long cTime;
//	private List<Goods> gListCat;


	public static String strLocator;//静态方法获取定位信息
	public static String getStrLocator() {
		return strLocator;
	}
	public static void setStrLocator(String strLocator) {
		HomePageFragment.strLocator = strLocator;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		homepageLayout=(ViewGroup) inflater.inflate(R.layout.homepage_layout, container,false);

		sPreferences = getActivity().getSharedPreferences(WelComeActivity.USERNAME,0);
		uService = new UrlService(getActivity());
		glList = new ArrayList<Goods>();
		glList = uService.getGoodsInfo();


		initPhoneNumber();

		initView();

		initListener();//广告条滚动

		initData();//准备数据

		initLocator();//定位

		initSeek();//搜索
		//		下拉刷新
		initPullDown();

		initLayoutMenu();//菜单


		//物品界面
		initCommodity();

		isRunning = true;
		handler.sendEmptyMessageDelayed(0, 4000);


		return homepageLayout;
	}




	private void initPhoneNumber() {

	}

	private void initView() {
		viewPager=(ViewPager) homepageLayout.findViewById(R.id.homeviewPage);
		//		pointGroup=(LinearLayout) homepageLayout.findViewById(R.id.dot_layout);
	}



	@SuppressWarnings("deprecation")
	private void initListener() {
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				//				Log.e("Activity", "position"+arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}
	/**
	 * 判断是否自动滚动
	 */
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {

			//让viewPager 滑动到下一页
			viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
			if(isRunning){
				handler.sendEmptyMessageDelayed(0, 4000);
			}
		};
	};

	private void initData() {
		imageList.add(new Ad(R.drawable.shouji, null));
		imageList.add(new Ad(R.drawable.yingpan, null));
		imageList.add(new Ad(R.drawable.chujiaquan, null));
		imageList.add(new Ad(R.drawable.peijian, null));


		viewPager.setAdapter(new MyPagerAdapter());
	}

	//	广告条
	class MyPagerAdapter extends PagerAdapter{
		/**
		 * 返回多少page
		 */
		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}
		/**
		 * true: 表示不去创建，使用缓存  false:去重新创建
		 * view： 当前滑动的view
		 * object：将要进入的新创建的view，由instantiateItem方法创建
		 */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}
		/**
		 * 类似于BaseAdapger的getView方法
		 * 用了将数据设置给view
		 * 由于它最多就3个界面，不需要viewHolder
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view=View.inflate(getActivity(), R.layout.adapter_ad, null);
			ImageView imageView=(ImageView) view.findViewById(R.id.image);
			tvHomeNumber = (TextView) homepageLayout.findViewById(R.id.home_tv_number_ho);
			tvHomeNumberRight = (TextView) homepageLayout.findViewById(R.id.home_tv_number_ho_right);

			Ad ad=imageList.get(position%imageList.size());
			imageView.setImageResource(ad.getIconResId());

			tvHomeNumber.setText(((position%imageList.size())+1)+"");
			tvHomeNumberRight.setText(" / "+imageList.size());

			container.addView(view);//一定不能少，将view加入到viewPager中

			return view;
		}
		/**
		 * 销毁page
		 * position： 当前需要消耗第几个page
		 * object:当前需要消耗的page
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
	//图片滚动


	private void initLocator() {//定位

		tvHomeLocator=(TextView) homepageLayout.findViewById(R.id.tv_home_locator);

		String baiduSite = sPreferences.getString("locator", "");

		if (baiduSite.equals("null") || baiduSite == null || baiduSite.equals("")
				) {
			tvHomeLocator.setText("江西师范大学软件学院");
		}else {
			tvHomeLocator.setText(baiduSite);
		}






		tvHomeLocator.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent=new Intent(getActivity(),LocatorActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		});

	}


	private void initSeek() {
		ivSeek=(ImageView) homepageLayout.findViewById(R.id.iv_home1);
		ivSeek.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(),SeekActivity.class));
				getActivity().finish();
			}
		});

	}

	/*
	 * 下拉刷新
	 * */
	private void initPullDown() {

		mPullDownScrollView = (PullDownScrollView) homepageLayout.findViewById(R.id.refresh_root);  
		mPullDownScrollView.setRefreshListener( this);  
		mPullDownScrollView.setPullDownElastic(new PullDownElasticImp(getActivity()));
		/*
		long enterTime = System.currentTimeMillis();
		glList = new ArrayList<Goods>();
		glList = uService.getGoodsInfo();
		long leaveTime = System.currentTimeMillis();  //这是获取毫秒数
		cTime = leaveTime - enterTime;*/


	}
	//下拉刷新时间
	@Override
	public void onRefresh(PullDownScrollView view) {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				mPullDownScrollView.finishRefresh("最后刷新："+getCurrentTime());
				long enterTime = System.currentTimeMillis();

				glList = new ArrayList<Goods>();
				glList = uService.getGoodsInfo();

				long leaveTime = System.currentTimeMillis();  //这是获取毫秒数
				cTime = leaveTime - enterTime;
				initCommodity();
			}
		}, cTime);
	}
	//下拉刷新时间格式
	@SuppressLint("SimpleDateFormat")
	private String getCurrentTime(){
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}



	/*
	 * 菜单点击
	 * */

	private void initLayoutMenu() {
		layoutMenu1 = (RelativeLayout) homepageLayout.findViewById(R.id.home_layout_main_menu_1);
		layoutMenu2 = (RelativeLayout) homepageLayout.findViewById(R.id.home_layout_main_menu_2);
		layoutMenu3 = (RelativeLayout) homepageLayout.findViewById(R.id.home_layout_main_menu_3);
		layoutMenu4 = (RelativeLayout) homepageLayout.findViewById(R.id.home_layout_main_menu_4);


		layoutMenu1.setOnClickListener(this);
		layoutMenu2.setOnClickListener(this);
		layoutMenu3.setOnClickListener(this);
		layoutMenu4.setOnClickListener(this);
	}




	/*
	 * 商品列表
	 * */
	private void initCommodity() {
		
		hotGridView = (HGridView) homepageLayout.findViewById(R.id.home_commodity_hotgridview);
		catGridView = (HGridView) homepageLayout.findViewById(R.id.home_commodity_catgridview);
		

		glList = new ArrayList<Goods>();
		glList = uService.getGoodsInfo();
		

		if (glList == null) {
			Utils.showToast(getActivity(), "无法获得数据");
			hotGridView.setAdapter(new GirdViewAdapter(getActivity(),null));
			catGridView.setAdapter(new GirdViewAdapter(getActivity(),null));
		}else {



			if (glList.size() == 0) {
				Logger.e(glList.size()+"glList.size");
			}else {
				Logger.e(glList.size()+"glList.size");
				
				hotGridView.setAdapter(new GirdViewAdapter(getActivity(),glList));
				hotGridView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						Intent intent = new Intent(getActivity(),HomeGoodsGVActivity.class);
						intent.putExtra("int","hotGridView:"+arg2);
						intent.putExtra("goodsName", ""+glList.get(arg2).getGoodName());
						intent.putExtra("studentNumber", ""+glList.get(arg2).getReleaserID());
						intent.putExtra("releaseTime", ""+glList.get(arg2).getReleaseTime());
						startActivity(intent);
						getActivity().finish();
						Logger.d(arg2+"");

					}
				});




				if (glList.size() == 0) {
					Logger.e(glList.size()+"glList.size");
				}else {
					Logger.e(glList.size()+"glList.size");

				/*	int a[] = new int[glList.size()+1];
					int count = 0;
					gListCat = new ArrayList<Goods>();
					int n = 0;
					if(glList.size() >= 2){
						n = 2;
					}else {
						n = glList.size();
					}
					
					while (count < n) {
						int result=(int)((Math.random()*glList.size())-1);
						boolean flag = true;
						for(int j = 0; j < glList.size(); j++){
							if (a[j] == result) {
								flag = false;
								break;
							}
						}
						if (flag) {
							a[count] = result;
							gListCat.add(glList.get(result));
							count++;
						}
					}*/



					catGridView.setAdapter(new GirdViewAdapter(getActivity(),glList));
					catGridView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
							Intent intent = new Intent(getActivity(),HomeGoodsGVActivity.class);
							intent.putExtra("int","hotGridView:"+arg2);
							intent.putExtra("goodsName", ""+glList.get(arg2).getGoodName());
							intent.putExtra("studentNumber", ""+glList.get(arg2).getReleaserID());
							intent.putExtra("releaseTime", ""+glList.get(arg2).getReleaseTime());
							startActivity(intent);
							getActivity().finish();
							Logger.d(arg2+"");
						}
					});
				}
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_layout_main_menu_1:

			Utils.showToast(getActivity(), "生活");
			Intent intent1 = new Intent(getActivity(),HomeMenuClassActivity.class);

			Editor editor1 = sPreferences.edit();
			editor1.putString("menu", "生活");
			editor1.commit();

			startActivity(intent1);
			getActivity().finish();

			break;
		case R.id.home_layout_main_menu_2:

			Utils.showToast(getActivity(), "学习");
			Intent intent2 = new Intent(getActivity(),HomeMenuClassActivity.class);
			Editor editor2 = sPreferences.edit();
			editor2.putString("menu", "学习");
			editor2.commit();
			startActivity(intent2);
			getActivity().finish();
			break;
		case R.id.home_layout_main_menu_3:

			Utils.showToast(getActivity(), "数码");
			Intent intent3 = new Intent(getActivity(),HomeMenuClassActivity.class);
			Editor editor3 = sPreferences.edit();
			editor3.putString("menu", "数码");
			editor3.commit();
			startActivity(intent3);
			getActivity().finish();
			break;
		case R.id.home_layout_main_menu_4:

			Utils.showToast(getActivity(), "其他");
			Intent intent4 = new Intent(getActivity(),HomeMenuClassActivity.class);
			Editor editor4 = sPreferences.edit();
			editor4.putString("menu", "其他");
			editor4.commit();
			startActivity(intent4);
			getActivity().finish();
			break;

		default:
			break;
		}
	}



}
