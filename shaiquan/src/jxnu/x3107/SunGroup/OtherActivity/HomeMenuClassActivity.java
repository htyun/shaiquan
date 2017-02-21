package jxnu.x3107.SunGroup.OtherActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import jxnu.n433.x3107.SunGroup.Goods;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.n433.x3107.SunGroup.UrlService;
import jxnu.x3107.Fragment.View.Logger;
import jxnu.x3107.SunGroup.MainActivity;
import jxnu.x3107.SunGroup.WelComeActivity;
import jxnu.x3107.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class HomeMenuClassActivity extends Activity implements OnClickListener{

	private ImageView ivBack;
	private String menuClass;
	private TextView tvMenu;

	private ListView lvMenu;
	private String etText;
	private List<Goods> gList;
	private UrlService urlService;


	public SharedPreferences sPreferences;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home_menu_class);

		sPreferences = getSharedPreferences(WelComeActivity.USERNAME,0);

		Editor editor1 = sPreferences.edit();
		editor1.putInt("goodsGva", 456);
		editor1.commit();


		menuClass = sPreferences.getString("menu", "");

		tvMenu = (TextView) findViewById(R.id.home_menu_activity_menu_tv);
		tvMenu.setText(menuClass+"");

		lvMenu = (ListView) findViewById(R.id.home_menu_activity_lv_menu_class);

		if (menuClass.equals("搜索")) {
			etText = sPreferences.getString("menutext","");
			Logger.e(""+etText);

			initSeek();
		}else {
			initView();
		}
		ivBack = (ImageView) findViewById(R.id.home_menu_activity_iv_back);
		ivBack.setOnClickListener(this);
		//		Utils.showToast(getApplicationContext(), menuClass+"");




	}

	private void initSeek() {

		gList = new ArrayList<Goods>();
		urlService = new UrlService(getApplicationContext());
		gList = urlService.getSeekGoodsInfo(etText);

		if (gList == null) {
			Utils.showToast(getApplicationContext(), "无法获得数据");
		}else {


			MenuAdapter menuAdapter = new MenuAdapter(gList);
			lvMenu.setAdapter(menuAdapter);
			lvMenu.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					Intent intent = new Intent(HomeMenuClassActivity.this,HomeGoodsGVActivity.class);
					intent.putExtra("int","listview:"+arg2);
					intent.putExtra("goodsName", ""+gList.get(arg2).getGoodName());
					intent.putExtra("studentNumber", ""+gList.get(arg2).getReleaserID());
					intent.putExtra("releaseTime", gList.get(arg2).getReleaseTime()+"");
					startActivity(intent);
					finish();
					Logger.d(arg2+"");
				}
			});
		}
	}

	private void initView() {



		gList = new ArrayList<Goods>();
		urlService = new UrlService(getApplicationContext());
		gList = urlService.getClassGoodsInfo(menuClass);


		if (gList == null) {
			Utils.showToast(getApplicationContext(), "无法获得数据");
		}else{

			MenuAdapter menuAdapter = new MenuAdapter(gList);
			lvMenu.setAdapter(menuAdapter);

			lvMenu.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					Intent intent = new Intent(HomeMenuClassActivity.this,HomeGoodsGVActivity.class);
					intent.putExtra("int","listview:"+arg2);
					intent.putExtra("goodsName", ""+gList.get(arg2).getGoodName());
					intent.putExtra("studentNumber", ""+gList.get(arg2).getReleaserID());
					intent.putExtra("releaseTime", gList.get(arg2).getReleaseTime()+"");
					startActivity(intent);
					finish();
					Logger.d(arg2+"");
				}
			});
		}

	}

	private class MenuAdapter extends BaseAdapter{

		List<Goods> gList = new ArrayList<Goods>();

		private LayoutInflater mInflater;

		public MenuAdapter(List<Goods> gList) {
			this.gList = gList;

			mInflater = (LayoutInflater) getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return gList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.home_menu_class_list_view_share, null);

				holder = new ViewHolder();

				holder.ivGoodsImage = (ImageView) convertView.findViewById(R.id.home_menu_class_list_adapter_iv_share_goods_image);
				holder.tvDate = (TextView) convertView.findViewById(R.id.home_menu_class_list_adapter_tv_date);
				holder.tvGoodsIntro = (TextView) convertView.findViewById(R.id.home_menu_class_list_adapter_tv_share_intro);

				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			//			Picasso.with(getApplicationContext()).load(UrlService.USERIMGURL + "goods/" + gList.get(position).getGoodLogo()).placeholder(R.drawable.ic_launcher).into(holder.ivGoodsImage);


			Picasso.with(getApplicationContext())
			.load(UrlService.USERIMGURL + "goods/" + gList.get(position).getGoodLogo())
			.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
//	         .networkPolicy(NetworkPolicy.NO_CACHE)
			.into(holder.ivGoodsImage);

			holder.tvDate.setText(""+gList.get(position).getTime());
			holder.tvGoodsIntro.setText(""+gList.get(position).getGoodIntroduction());

			return convertView;
		}

	}
	static class ViewHolder{
		ImageView ivGoodsImage;
		TextView tvDate;
		TextView tvGoodsIntro;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_menu_activity_iv_back:

			Intent intentBack = new Intent(HomeMenuClassActivity.this,MainActivity.class);
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

			Intent intentBack = new Intent(HomeMenuClassActivity.this,MainActivity.class);
			startActivity(intentBack);
			finish();

		}

		return super.onKeyDown(keyCode, event);
	}


}
